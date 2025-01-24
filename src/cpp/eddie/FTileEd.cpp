//---------------------------------------------------------------------------

#include <vcl.h>
#include <stdio.h>
#pragma hdrstop

#include "FTileEd.h"
#include "FName.h"
#include <algorithm>
#include <fstream>
#include <ios>

//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormTileEd *FormTileEd;
//---------------------------------------------------------------------------

__fastcall TFormTileEd::TFormTileEd(TComponent* Owner)
        : TForm(Owner)
{
  mTileSetDat = "";
  mBitmapTile = new Graphics::TBitmap();
  mSelecting = false;
  mGridW = 16;
  mGridH = 16;
}
//---------------------------------------------------------------------------

void TFormTileEd::SetTileSetDat(AnsiString _fileName)
{
  //mTileSetDat = _fileName;
  mBitmapTile->LoadFromFile("data\\tileset.bmp");
}
//---------------------------------------------------------------------------

void TFormTileEd::MakeCombo()
{
  cbTiles->Items->Clear();

  for(unsigned int i = 0; i < mTiles.size(); i++)
  {
    cbTiles->Items->Add(mTiles[i]->GetName().c_str());
  }

  if (cbTiles->Items->Count > 0)
  {
    cbTiles->ItemIndex = 0;
  }
}
//---------------------------------------------------------------------------

void TFormTileEd::MakeCheckUp()
{
  checkUp->State = cbUnchecked;
  checkDown->State = cbUnchecked;
  checkLeft->State = cbUnchecked;
  checkRight->State = cbUnchecked;
  checkLadder->State = cbUnchecked;
  checkHeadLadder->State = cbUnchecked;

  if (cbTiles->ItemIndex == -1 || mTiles.size() <= 0)
  {
    return;
  }

  checkUp->State = (TCheckBoxState)mTiles[cbTiles->ItemIndex]->GetCollision(CLS_UP);
  checkDown->State = (TCheckBoxState)mTiles[cbTiles->ItemIndex]->GetCollision(CLS_DOWN);
  checkLeft->State = (TCheckBoxState)mTiles[cbTiles->ItemIndex]->GetCollision(CLS_LEFT);
  checkRight->State = (TCheckBoxState)mTiles[cbTiles->ItemIndex]->GetCollision(CLS_RIGHT);
  checkLadder->State = (TCheckBoxState)mTiles[cbTiles->ItemIndex]->GetIsLadder();
  checkHeadLadder->State = (TCheckBoxState)mTiles[cbTiles->ItemIndex]->GetIsHeadLadder();
}
//---------------------------------------------------------------------------

void TFormTileEd::DrawTiles()
{
  //paintTile->Canvas->Draw(0, 0, mBitmapTile);
  paintTile->Canvas->Brush->Color = (TColor)0x000000;
  paintTile->Canvas->FillRect(Rect(0, 0, paintTile->Width, paintTile->Height));

  // draw the bitmap on the canvas using the zoom specified by the trackZoom component
  // and panning regarding the scroll values
  paintTile->Canvas->CopyRect(
    Rect(-sbH->Position, -sbV->Position, trackZoom->Position * mBitmapTile->Width - sbH->Position, trackZoom->Position * mBitmapTile->Height - sbV->Position),
    mBitmapTile->Canvas,
    Rect(0, 0, mBitmapTile->Width, mBitmapTile->Height)
  );

  if (cbDrawAll->Checked)
  {
    for(unsigned int i = 0; i < mTiles.size(); i++)
    {
      int lX = mTiles[i]->GetX();
      int lY = mTiles[i]->GetY();
      int lW = mTiles[i]->GetW();
      int lH = mTiles[i]->GetH();
      //TColor lColor = TColor(0xFFFFFF);

      /*
      if (cbTiles->ItemIndex == i)
      {
        lColor = TColor(0x0000FF);
      }
      */

      paintTile->Canvas->Brush->Color = shapeUnselColor->Brush->Color;

      //paintTile->Canvas->FrameRect(Rect(lX, lY, lX + lW, lY + lH));
      paintTile->Canvas->FrameRect(Rect(lX * trackZoom->Position - sbH->Position, lY * trackZoom->Position - sbV->Position, (lX + lW) * trackZoom->Position - sbH->Position, (lY + lH) * trackZoom->Position - sbV->Position));
    }
  }

  // paint selected
  if (cbTiles->ItemIndex != -1)
  {
    int lX = mTiles[cbTiles->ItemIndex]->GetX();
    int lY = mTiles[cbTiles->ItemIndex]->GetY();
    int lW = mTiles[cbTiles->ItemIndex]->GetW();
    int lH = mTiles[cbTiles->ItemIndex]->GetH();

    //TColor lColor = TColor(0x0000FF);
    paintTile->Canvas->Brush->Color = shapeSelColor->Brush->Color;

    //paintTile->Canvas->FrameRect(Rect(lX, lY, lX + lW, lY + lH));
    paintTile->Canvas->FrameRect(Rect(lX * trackZoom->Position - sbH->Position, lY * trackZoom->Position - sbV->Position, (lX + lW) * trackZoom->Position - sbH->Position, (lY + lH) * trackZoom->Position - sbV->Position));

    statusTile->SimpleText = AnsiString(mTiles[cbTiles->ItemIndex]->GetName().c_str()) + ": " + AnsiString(lW) + "x" + AnsiString(lH);
  }
}
//---------------------------------------------------------------------------

void TFormTileEd::DrawGrid()
{
  if (cbDrawGrid->Checked)
  {
    paintTile->Canvas->Pen->Color = shapeGridColor->Brush->Color;

    for(int x = 0; x < paintTile->Width; x += mGridW)
    {
      paintTile->Canvas->MoveTo(x, 0);
      paintTile->Canvas->LineTo(x, paintTile->Height);
    }

    for(int y = 0; y < paintTile->Height; y += mGridH)
    {
      paintTile->Canvas->MoveTo(0, y);
      paintTile->Canvas->LineTo(paintTile->Width, y);
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::FormShow(TObject *Sender)
{
  LoadFromfile("data\\tileset.txt");
  MakeCombo();
  MakeCheckUp();
  DrawTiles();
}
//---------------------------------------------------------------------------

void TFormTileEd::BeginSelection(int X, int Y)
{
  //mStartX = X;
  //mStartY = Y;
  //mCurrentX = X;
  //mCurrentY = Y;
  mStartX = mCurrentX = X - X % trackZoom->Position - sbH->Position % trackZoom->Position;
  mStartY = mCurrentY = Y - Y % trackZoom->Position - sbV->Position % trackZoom->Position;
  mSelecting = true;
}
//---------------------------------------------------------------------------

void TFormTileEd::EndSelection(int X, int Y)
{
  //mCurrentX = X;
  //mCurrentY = Y;
  mCurrentX = X - X % trackZoom->Position + trackZoom->Position - sbH->Position % trackZoom->Position;
  mCurrentY = Y - Y % trackZoom->Position + trackZoom->Position - sbV->Position % trackZoom->Position;

  mSelecting = false;

  CTile* lTile = mTiles[cbTiles->ItemIndex];

  if (mCurrentX < 0)
  {
    mCurrentX = 0;
  }

  if (mCurrentY < 0)
  {
    mCurrentY = 0;
  }

  if (mStartX > mCurrentX)
  {
    int lTmp = mStartX;
    mStartX = mCurrentX;
    mCurrentX = lTmp;
  }

  if (mStartY > mCurrentY)
  {
    int lTmp = mStartY;
    mStartY = mCurrentY;
    mCurrentY = lTmp;
  }

  // recompute coordinates, due to zoom
  mStartX   = (mStartX + sbH->Position) / trackZoom->Position;
  mStartY   = (mStartY + sbV->Position) / trackZoom->Position;
  mCurrentX = (mCurrentX + sbH->Position) / trackZoom->Position;
  mCurrentY = (mCurrentY + sbV->Position) / trackZoom->Position;

  if (mCurrentY > mBitmapTile->Height)
  {
    mCurrentY = mBitmapTile->Height;
  }

  if (mCurrentX > mBitmapTile->Width)
  {
    mCurrentX = mBitmapTile->Width;
  }

  lTile->SetX(mStartX);
  lTile->SetY(mStartY);
  lTile->SetW(mCurrentX - mStartX);
  lTile->SetH(mCurrentY - mStartY);
  SaveToFile("data\\tileset.txt");
  //lTile->SetName("NewTile");
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::btn1x1Click(TObject *Sender)
{
  // create tiles 1x1
  // politely ask before wiping out the tiles list :p
  if (Application->MessageBox("Wipe out tiles list and recreate 1x1 tiles?", "Confirm", MB_YESNO) == IDYES)
  {
    mTiles.erase(mTiles.begin(), mTiles.end());

    // now create 1x1 tiles
    for(int i = 0; i < mBitmapTile->Width / 16; i++)
    {
      for(int j = 0; j < mBitmapTile->Height / 16; j++)
      {
        CTile* lTile = new CTile();

        lTile->SetX(i * 16);
        lTile->SetY(j * 16);
        lTile->SetW(16);
        lTile->SetH(16);
        lTile->SetName("tile");
        //lTile->SetName(std::string("tile ") + std::string(i) + "," + std::string(j));

        mTiles.push_back(lTile);
      }
    }


    MakeCombo();
    MakeCheckUp();
    SaveToFile("data\\tileset.txt");

    paintTile->OnPaint(this);
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::btnAddClick(TObject *Sender)
{
  CTile* lTile = new CTile();

  lTile->SetX(0);
  lTile->SetY(0);
  lTile->SetW(16);
  lTile->SetH(16);
  lTile->SetName("Newtile");

  mTiles.push_back(lTile);

  MakeCombo();
  MakeCheckUp();
  SaveToFile("data\\tileset.txt");

  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------


void __fastcall TFormTileEd::FormDestroy(TObject *Sender)
{
  delete mBitmapTile;
  mTiles.clear();
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::paintTilePaint(TObject *Sender)
{
  DrawTiles();

  DrawGrid();

  if (mSelecting)
  {
    int lSX = mStartX, lSY = mStartY;
    int lCX = mCurrentX, lCY = mCurrentY;

    if (lSX > lCX)
    {
      int lTmp = lSX;
      lSX = lCX;
      lCX = lTmp;
    }

    if (lSY > lCY)
    {
      int lTmp = lSY;
      lSY = lCY;
      lCY = lTmp;
    }

    paintTile->Canvas->Brush->Color = TColor(0x00FFFF);
    paintTile->Canvas->FrameRect(Rect(lSX, lSY, lCX, lCY));
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::cbTilesChange(TObject *Sender)
{
  paintTile->OnPaint(this);

  MakeCheckUp();
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::paintTileMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  if (Button == mbLeft && cbTiles->ItemIndex > -1)
  {
    BeginSelection(X, Y);
    paintTile->OnPaint(this);
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::paintTileMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
  if (mSelecting)
  {
    //mCurrentX = X;
    //mCurrentY = Y;
    mCurrentX = X - X % trackZoom->Position + trackZoom->Position;
    mCurrentY = Y - Y % trackZoom->Position + trackZoom->Position;
    paintTile->OnPaint(this);

    statusTile->SimpleText = AnsiString(abs(mStartX - mCurrentX) / trackZoom->Position + 1) + ", " + AnsiString(abs(mCurrentY - mStartY) / trackZoom->Position + 1);
  }
  else
  {
    statusTile->SimpleText = AnsiString(X) + ", " + AnsiString(Y);
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::paintTileMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  if (mSelecting)
  {
    EndSelection(X, Y);
    paintTile->OnPaint(this);
  }
}
//---------------------------------------------------------------------------

void TFormTileEd::SaveToFile(AnsiString _file)
{
  FILE* lFile;

  lFile = fopen(_file.c_str(), "w+t");

  if (!lFile)
  {
    return;
  }

  for(int i = 0; i < (int)mTiles.size(); i++) 
  {
    CTile* lTile = mTiles[i];

    fprintf(lFile, "%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
       lTile->GetName().c_str(), lTile->GetX(), lTile->GetY(), lTile->GetW(), lTile->GetH(),
       lTile->GetCollision(CLS_UP), lTile->GetCollision(CLS_DOWN), lTile->GetCollision(CLS_LEFT), lTile->GetCollision(CLS_RIGHT), (int)(lTile->GetIsLadder()), (int)(lTile->GetIsHeadLadder())
       );
  }

  fclose(lFile);
}
//---------------------------------------------------------------------------

void TFormTileEd::LoadFromfile(AnsiString _file)
{
  FILE* lFile;

  lFile = fopen(_file.c_str(), "rt");

  if (!lFile)
  {
    return;
  }

  mTiles.clear();

  while(!feof(lFile))
  {
    char lName[200];
    int  lX, lY, lW, lH, lCLeft, lCRight, lCUp, lCDown, lIsLadder, lIsHeadLadder;

    fscanf(lFile, "%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", lName, &lX, &lY, &lW, &lH, &lCUp, &lCDown, &lCLeft, &lCRight, &lIsLadder, &lIsHeadLadder);

    CTile* lTile = new CTile();

    lTile->SetX(lX);
    lTile->SetY(lY);
    lTile->SetW(lW);
    lTile->SetH(lH);
    lTile->SetCollision(CLS_UP, lCUp);
    lTile->SetCollision(CLS_DOWN, lCDown);
    lTile->SetCollision(CLS_LEFT, lCLeft);
    lTile->SetCollision(CLS_RIGHT, lCRight);
    lTile->SetIsLadder((bool)lIsLadder);
    lTile->SetIsHeadLadder((bool)lIsHeadLadder);
    lTile->SetName(std::string(lName));

    mTiles.push_back(lTile);
  }

  fclose(lFile);
}
//---------------------------------------------------------------------------


void __fastcall TFormTileEd::btnDelClick(TObject *Sender)
{
  // delete the selected item
  if (cbTiles->ItemIndex == -1)
  {
    return;
  }

  if (mTiles.size() <= 0)
  {
    return;
  }

  std::vector<CTile*>::iterator lIt = mTiles.begin();

  lIt += cbTiles->ItemIndex - 1;

  mTiles.erase(lIt);

  if (mTiles.size() > 0)
  {
    cbTiles->ItemIndex = 0;
  }

  MakeCombo();
  MakeCheckUp();
  SaveToFile("data\\tileset.txt");

  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::btnRenameClick(TObject *Sender)
{
  if (cbTiles->ItemIndex == -1)
  {
    return;
  }

  if (mTiles.size() <= 0)
  {
    return;
  }

  // pop up an input dialog
  FormName->SetText(mTiles[cbTiles->ItemIndex]->GetName().c_str());

  if (FormName->ShowModal() == mrOk)
  {
    // chaos!
    mTiles[cbTiles->ItemIndex]->SetName(FormName->GetText().c_str());

    // keep the selected tile
    int lIndex = cbTiles->ItemIndex;

    MakeCombo();
    MakeCheckUp();

    // restore the selected tile
    if (lIndex >= 0)
    {
      cbTiles->ItemIndex = lIndex;
    }  

    SaveToFile("data\\tileset.txt");

    paintTile->OnPaint(this);
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::bntClearAllClick(TObject *Sender)
{
  // politely ask before wiping out the tiles list :p
  if (Application->MessageBox("Wipe out tiles list?", "Confirm", MB_YESNO) == IDYES)
  {
    mTiles.erase(mTiles.begin(), mTiles.end());

    MakeCombo();
    MakeCheckUp();
    SaveToFile("data\\tileset.txt");

    paintTile->OnPaint(this);
  }
}
//---------------------------------------------------------------------------

void TFormTileEd::SetupCollisions()
{
  if (cbTiles->ItemIndex == -1)
  {
    return;
  }

  if (mTiles.size() <= 0)
  {
    return;
  }

  mTiles[cbTiles->ItemIndex]->SetCollision(CLS_RIGHT, checkRight->Checked);
  mTiles[cbTiles->ItemIndex]->SetCollision(CLS_DOWN, checkDown->Checked);
  mTiles[cbTiles->ItemIndex]->SetCollision(CLS_LEFT, checkLeft->Checked);
  mTiles[cbTiles->ItemIndex]->SetCollision(CLS_UP, checkUp->Checked);
  mTiles[cbTiles->ItemIndex]->SetIsLadder(checkLadder->Checked);
  mTiles[cbTiles->ItemIndex]->SetIsHeadLadder(checkHeadLadder->Checked);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::cbTilesEnter(TObject *Sender)
{
  // workaround - seems that setting Checked for a TCheckbox generates an OnClick event?
  SetupCollisions();
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::FormHide(TObject *Sender)
{
  SetupCollisions();
  SaveToFile("data\\tileset.txt");
}
//---------------------------------------------------------------------------


void __fastcall TFormTileEd::edGridWExit(TObject *Sender)
{
  // convert value to int
  mGridW = 16;

  try
  {
    mGridW = edGridW->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    mGridW = 16;
  }

  // verificari de limita
  if (mGridW < 2)
  {
    mGridW = 2;
  }

  edGridW->Text = AnsiString(mGridW);

  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::edGridHExit(TObject *Sender)
{
  // convert value to int
  mGridH = 16;

  try
  {
    mGridH = edGridH->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    mGridH = 16;
  }

  // verificari de limita
  if (mGridH < 2)
  {
    mGridH = 2;
  }

  edGridH->Text = AnsiString(mGridH);

  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::cbDrawGridClick(TObject *Sender)
{
  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::shapeGridColorMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  if (Button == mbLeft)
  {
    if (dlgColor->Execute())
    {
      shapeGridColor->Brush->Color = dlgColor->Color;
      paintTile->OnPaint(this);
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::shapeSelColorMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  if (Button == mbLeft)
  {
    if (dlgColor->Execute())
    {
      shapeSelColor->Brush->Color = dlgColor->Color;
      paintTile->OnPaint(this);
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::shapeUnselColorMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  if (Button == mbLeft)
  {
    if (dlgColor->Execute())
    {
      shapeUnselColor->Brush->Color = dlgColor->Color;
      paintTile->OnPaint(this);
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::cbDrawAllClick(TObject *Sender)
{
  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::trackZoomChange(TObject *Sender)
{
  // computescrolllimits
  ComputeScrollLimits();
  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::sbVChange(TObject *Sender)
{
  ComputeScrollLimits();
  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::sbHChange(TObject *Sender)
{
  ComputeScrollLimits();
  paintTile->OnPaint(this);
}
//---------------------------------------------------------------------------

void TFormTileEd::ComputeScrollLimits()
{
  sbH->Max = std::max(sbH->Min + 1, mBitmapTile->Width  * trackZoom->Position - paintTile->Width);
  sbV->Max = std::max(sbV->Min + 1, mBitmapTile->Height * trackZoom->Position - paintTile->Height);
}
//---------------------------------------------------------------------------


void __fastcall TFormTileEd::Export1x1tilesetdata1Click(TObject *Sender)
{
  // export the 1x1 tileset binary data
  dlgSave->FilterIndex = 1;

  if (dlgSave->Execute())
  {
    if (!Export1x1TilesetData(dlgSave->FileName.c_str()))
    {
      Application->MessageBox("Could not save file!", "Warning", MB_OK);
    }
  }
}
//---------------------------------------------------------------------------

bool TFormTileEd::Export1x1TilesetData(std::string _fileName)
{
  // open the file
  std::fstream lFile;

  lFile.open(_fileName.c_str(), std::ios_base::out | std::ios_base::binary | std::ios_base::trunc);//, 0666);

  // exit if file cannot be opened
  if (lFile.fail())
  {
    return false;
  }

  // dump the tile count
  unsigned char lCount = 0;

  for(int i = 0; i < mTiles.size(); i++)
  {
    if (mTiles[i]->IsSignificant())
    {
      lCount++;
    }
  }

  lFile.write(&lCount, 1);

  // dump only the tiles that have collision or ladder collision
  for(int i = 0; i < mTiles.size(); i++)
  {
    CTile* lTile = mTiles[i];

    short int lX = lTile->GetX();
    short int lY = lTile->GetY();

    unsigned char lData = lTile->GetPackedData();

    if (lData != 0)
    {
      lFile.write((char*)(&lX), 2);
      lFile.write((char*)(&lY), 2);
      lFile.write(&lData, 1);
    }
  }

  lFile.close();
  return true;
}
//---------------------------------------------------------------------------

void __fastcall TFormTileEd::Export16bittilesetenginev310120041Click(
      TObject *Sender)
{
  // export the 1x1 tileset binary data
  dlgSave->FilterIndex = 2;

  if (dlgSave->Execute())
  {
    if (!Export16bitTilesetData(dlgSave->FileName.c_str()))
    {
      Application->MessageBox("Could not save file!", "Warning", MB_OK);
    }
  }
}
//---------------------------------------------------------------------------

bool TFormTileEd::Export16bitTilesetData(std::string _fileName)
{
  // open the file
  std::fstream lFile;

  lFile.open(_fileName.c_str(), std::ios_base::out | std::ios_base::binary | std::ios_base::trunc);//, 0666);

  // exit if file cannot be opened
  if (lFile.fail())
  {
    return false;
  }

  // dump the significant tile count
  unsigned char lCount = 0;

  for(int i = 0; i < mTiles.size(); i++)
  {
    if (mTiles[i]->IsSignificant())
    {
      lCount++;
    }
  }

  lFile.write(&lCount, 1);

  // dump only the tiles that have collision or ladder collision
  for(int i = 0; i < mTiles.size(); i++)
  {
    CTile* lTile = mTiles[i];

    //unsigned char lX = lTile->GetX() / 16;
    //unsigned char lY = lTile->GetY() / 16;
    unsigned char lIndex = (unsigned char)i;
    unsigned char lData = lTile->GetPackedData();

    if (lData != 0)
    {
      //lFile.write(&lX, 1);
      //lFile.write(&lY, 1);
      lFile.write(&lIndex, 1);
      lFile.write(&lData, 1);
    }
  }

  // dump the tile (all) count
  unsigned char lAllCount = mTiles.size();

  lFile.write(&lAllCount, 1);

  // compute the pallete for the tileset
  int lPallete[16];
  unsigned char lColorCount = 0;

  // assume the sprite has less or exactly than 16 colors
  // no check on the color count for now
  for(int x = 0 ; x < mBitmapTile->Width; x++)
  {
    for(int y = 0 ; y < mBitmapTile->Height; y++)
    {
      int lColor = (int)mBitmapTile->Canvas->Pixels[x][y];

      bool lColorFound = false;

      for (int i = 0; i < lColorCount; i++)
      {
        if (lPallete[i] == lColor)
        {
          lColorFound = true;
          break;
        }
      }

      if (!lColorFound)
      {
        lPallete[lColorCount++] = lColor;
      }
    }
  }

  lFile.write(&lColorCount, sizeof(char));

  // output the pallete
  for(short int j = 0; j < lColorCount; j++)
  {
    unsigned char lR = (lPallete[j] & 0x0000FF);
    unsigned char lG = (lPallete[j] & 0x00FF00) >> 8;
    unsigned char lB = (lPallete[j] & 0xFF0000) >> 16;

    lFile.write(&lR, sizeof(char));
    lFile.write(&lG, sizeof(char));
    lFile.write(&lB, sizeof(char));
  }


  // now dump the binary data in 4bit form
  for(int i = 0; i < mTiles.size(); i++)
  {
    CTile* lTile = mTiles[i];

    short int lX = lTile->GetX();
    short int lY = lTile->GetY();

    // the color
    unsigned char lOutputColor = 0;
    int lNibbleCount = 0;
    int lBytesWrote = 0;

    for(int y = lY; y < lY + 16; y++)
    {
      for(int x = lX; x < lX + 16; x++)
      {
        int lColor = (int)mBitmapTile->Canvas->Pixels[x][y];

        unsigned char lColorIndex;

        for(lColorIndex = 0; lColorIndex < lColorCount; lColorIndex++)
        {
          if (lPallete[lColorIndex] == lColor)
          {
            break;
          }
        }

        if (lNibbleCount == 0)
        {
          // put the color index as the low nibble
          lOutputColor = lColorIndex;

          lNibbleCount++;
        }
        else
        {
          // put the color index as the high nibble
          lOutputColor |= (lColorIndex << 4);

          // and write the composed to file
          lFile.write(&lOutputColor, sizeof(char));
          lOutputColor = 0;
          lNibbleCount = 0;
          lBytesWrote++;
        }
      }
    }

    if (lNibbleCount == 1)
    {
      lFile.write(&lOutputColor, sizeof(char));
    }
  }

  lFile.close();
  return true;
}
//---------------------------------------------------------------------------

