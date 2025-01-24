//---------------------------------------------------------------------------

#include <vcl.h>
#include <stdio.h>
#pragma hdrstop

#include <algorithm.h>
#include "FMain.h"
#include "FTileEd.h"
#include "FTool.h"
#include "FSize.h"
#include "FPaint.h"
#include "FParams.h"
#include "FDrop.h"
#include "FItemEdit.h"
#include "FLog.h"
#include "FAbout.h"
#include "VerifyCyberNinjaLevel.h"
#include "CfgTxt.h"
//---------------------------------------------------------------------------

#pragma package(smart_init)
#pragma resource "*.dfm"
TMain *Main;
//---------------------------------------------------------------------------

__fastcall TMain::TMain(TComponent* Owner)
        : TForm(Owner)
{
  mMapState = MAP_NONE;
  mLevel = 0;
  mFileHistoryIndex = 0;
  CreateBitmaps();
  mTileSelectionValid = true;
}
//---------------------------------------------------------------------------

void TMain::SaveConfig()
{
  FILE* lFile;

  lFile = fopen("eddie.cfg", "wt");

  if (!lFile)
  {
    return;
  }

  AnsiString lFirstPath[][2]    =
    { { "LOADINITDIR", ExtractFilePath(dlgLoad->FileName) },
      { "SAVEINITDIR", ExtractFilePath(dlgSave->FileName) },
      { "LOADARCHINITDIR", ExtractFilePath(dlgLoadArch->FileName) },
      { "SAVEARCHINITDIR", ExtractFilePath(dlgSaveArch->FileName) }
    };

  AnsiString lFallbackPath[][2] =
    { { "LOADINITDIR", dlgLoad->InitialDir},
      { "SAVEINITDIR", dlgSave->InitialDir},
      { "LOADARCHINITDIR", dlgLoadArch->InitialDir},
      { "SAVEARCHINITDIR", dlgSaveArch->InitialDir}
    };

  // paths for file dialogs
  fprintf(lFile, "[PATHS]\n");

  for(int i = 0; i < 4; i++)
  {
    AnsiString lPath = lFirstPath[i][1];

    if (lPath.IsEmpty())
    {
      lPath = lFallbackPath[i][1];
    }

    fprintf(lFile, "%s=\"%s\"\n", lFirstPath[i][0], lPath.c_str());
  }

  fprintf(lFile, "\n");

  // windows size and position
  fprintf(lFile, "[WIN]\n");

  int lLeft = this->Left, lTop = this->Top, lWidth = this->Width, lHeight = this->Height;
  fprintf(lFile, "WINMAIN=RECT %d %d %d %d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormParams->Left, lTop = FormParams->Top, lWidth = FormParams->Width, lHeight = FormParams->Height;
  fprintf(lFile, "WINPARAMS=RECT %d %d %d %d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormTool->Left, lTop = FormTool->Top, lWidth = FormTool->Width, lHeight = FormTool->Height;
  fprintf(lFile, "WINTOOL=RECT %d %d %d %d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormDrop->Left, lTop = FormDrop->Top, lWidth = FormDrop->Width, lHeight = FormDrop->Height;
  fprintf(lFile, "WINDROP=RECT %d %d %d %d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormPaint->Left, lTop = FormPaint->Top, lWidth = FormPaint->Width, lHeight = FormPaint->Height;
  fprintf(lFile, "WINPAINT=RECT %d %d %d %d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormLog->Left, lTop = FormLog->Top, lWidth = FormLog->Width, lHeight = FormLog->Height;
  fprintf(lFile, "WINLOG=RECT %d %d %d %d\n", lLeft, lTop, lWidth, lHeight);

  fprintf(lFile, "\n");

  // history
  fprintf(lFile, "[HISTORY]\n");

  fprintf(lFile, "HISTORYINDEX=%d\n", mFileHistoryIndex);
  fprintf(lFile, "HISTORYCOUNT=%d\n", mFileHistory.size());

  for(int i = 0; i < mFileHistory.size(); i++)
  {
    fprintf(lFile, "HISTORY%d=\"%s\"\n", i, mFileHistory[i].c_str());
  }

  fprintf(lFile, "\n");

  fclose(lFile);
  /*

  AnsiString lFirstPath[]    = {ExtractFilePath(dlgLoad->FileName), ExtractFilePath(dlgSave->FileName), ExtractFilePath(dlgLoadArch->FileName), ExtractFilePath(dlgSaveArch->FileName)};
  AnsiString lFallbackPath[] = {dlgLoad->InitialDir, dlgSave->InitialDir, dlgLoadArch->InitialDir, dlgSaveArch->InitialDir};

  // load dialog
  for(int i = 0; i < 4; i++)
  {
    AnsiString lPath = lFirstPath[i];

    if (lPath.IsEmpty())
    {
      lPath = lFallbackPath[i];
    }

    fprintf(lFile, "%s\n", lPath.c_str());
  }

  int lLeft = this->Left, lTop = this->Top, lWidth = this->Width, lHeight = this->Height;
  fprintf(lFile, "%d\t%d\t%d\t%d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormParams->Left, lTop = FormParams->Top, lWidth = FormParams->Width, lHeight = FormParams->Height;
  fprintf(lFile, "%d\t%d\t%d\t%d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormTool->Left, lTop = FormTool->Top, lWidth = FormTool->Width, lHeight = FormTool->Height;
  fprintf(lFile, "%d\t%d\t%d\t%d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormDrop->Left, lTop = FormDrop->Top, lWidth = FormDrop->Width, lHeight = FormDrop->Height;
  fprintf(lFile, "%d\t%d\t%d\t%d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormPaint->Left, lTop = FormPaint->Top, lWidth = FormPaint->Width, lHeight = FormPaint->Height;
  fprintf(lFile, "%d\t%d\t%d\t%d\n", lLeft, lTop, lWidth, lHeight);

  lLeft = FormLog->Left, lTop = FormLog->Top, lWidth = FormLog->Width, lHeight = FormLog->Height;
  fprintf(lFile, "%d\t%d\t%d\t%d\n", lLeft, lTop, lWidth, lHeight);

  fprintf(lFile, "%d\n", mFileHistoryIndex);

  for(int i = 0; i < mFileHistory.size(); i++)
  {
    fprintf(lFile, "%s\n", mFileHistory[i].c_str());
  }

  fclose(lFile);
  */
}
//---------------------------------------------------------------------------

void TMain::LoadConfig()
{
  CCfgTxt* cfg = new CCfgTxt();

  cfg->Open("e:\\home\\karg\\lethal metal\\dist\\tools\\eddie.cfg");

  char lDir[1024];
  int  lRect[4];

  if (cfg->GetResource("LOADINITDIR", lDir))
  {
    dlgLoad->InitialDir = AnsiString(lDir);
  }

  if (cfg->GetResource("SAVEINITDIR", lDir))
  {
    dlgSave->InitialDir = AnsiString(lDir);
  }

  if (cfg->GetResource("LOADARCHINITDIR", lDir))
  {
    dlgLoadArch->InitialDir = AnsiString(lDir);
  }

  if (cfg->GetResource("SAVEARCHINITDIR", lDir))
  {
    dlgSaveArch->InitialDir = AnsiString(lDir);
  }

  if (cfg->GetResource("WINMAIN", lRect))
  {
    this->Left = lRect[0]; this->Top = lRect[1]; this->Width = lRect[2]; this->Height = lRect[3];
  }

  if (cfg->GetResource("WINPARAMS", lRect))
  {
    FormParams->Left = lRect[0]; FormParams->Top = lRect[1]; FormParams->Width = lRect[2]; FormParams->Height = lRect[3];
  }

  if (cfg->GetResource("WINTOOL", lRect))
  {
    FormTool->Left = lRect[0]; FormTool->Top = lRect[1]; FormTool->Width = lRect[2]; FormTool->Height = lRect[3];
  }

  if (cfg->GetResource("WINDROP", lRect))
  {
    FormDrop->Left = lRect[0]; FormDrop->Top = lRect[1]; FormDrop->Width = lRect[2]; FormDrop->Height = lRect[3];
  }

  if (cfg->GetResource("WINPAINT", lRect))
  {
    FormPaint->Left = lRect[0]; FormPaint->Top = lRect[1]; FormPaint->Width = lRect[2]; FormPaint->Height = lRect[3];
  }

  if (cfg->GetResource("WINLOG", lRect))
  {
    FormLog->Left = lRect[0]; FormLog->Top = lRect[1]; FormLog->Width = lRect[2]; FormLog->Height = lRect[3];
  }

  int historyCount;

  cfg->GetResource("HISTORYCOUNT", &historyCount);
  cfg->GetResource("HISTORYINDEX", &mFileHistoryIndex);

  char lFile[1024];
  int lFileCount = 0;

  for(int i = 0; i < historyCount; i++)
  {
    AnsiString res = AnsiString("HISTORY") + i;

    cfg->GetResource(res.c_str(), lFile);

    if (!AnsiString(lFile).IsEmpty())
    {
      TMenuItem* lMenuItem = new TMenuItem(MainMenu1);

      lFileCount++;

      lMenuItem->Caption = AnsiString("");
      lMenuItem->OnClick = HistoryClick;

      File1->Insert(HISTORY_MENU_ITEMINDEX, lMenuItem);

      mFileHistory.push_back(AnsiString(lFile));
    }

    if (lFileCount >= HISTORY_MENU_MAX_ITEMS)
    {
      break;
    }
  }

  for(int i = 0; i < mFileHistory.size(); i++)
  {
    File1->Items[i + HISTORY_MENU_ITEMINDEX]->Caption = AnsiString("&") + AnsiString(i + 1) + "  " + mFileHistory[i];
  }

  cfg->Close();

  delete cfg;
}
//---------------------------------------------------------------------------

void TMain::ClearChanges()
{
  for(unsigned int i = 0; i < mUndoList.size(); i++)
  {
    delete mUndoList[i];
  }

  mUndoList.clear();

  for(unsigned int i = 0; i < mRedoList.size(); i++)
  {
    delete mRedoList[i];
  }

  mRedoList.clear();
}
//---------------------------------------------------------------------------

void TMain::CreateBitmaps()
{
  mBitmaps.clear();

  Graphics::TBitmap* lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_player.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_health.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_weapon.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_mine.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_enemy_slasher.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_enemy_shooter.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_button.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_elevator.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_automatic_elevator.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_enemy_robot.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);

  lBmp = new Graphics::TBitmap();
  lBmp->LoadFromFile("data\\main_ammo.bmp");
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);
  mBitmaps.push_back(lBmp);
  //lBmp = new Graphics::TBitmap();
  //lBmp->LoadFromFile("data\\main_money.bmp");
  //lBmp->Transparent = true;
  //mBitmaps.push_back(lBmp);
}
//---------------------------------------------------------------------------

Graphics::TBitmap* TMain::GetBitmap(int _type)
{
  switch(_type)
  {
    case PLAYER_ITEM:
      return mBitmaps[0];

    case HEALTH_ITEM:
      return mBitmaps[1];

    case WEAPON_ITEM:
      return mBitmaps[2];

    case MINE_ITEM:
      return mBitmaps[3];

    case SHOOTER_ITEM:
      return mBitmaps[5];

    case SLASHER_ITEM:
      return mBitmaps[4];

    case BUTTON_ITEM:
      return mBitmaps[6];

    case ELEVATOR_ITEM:
      return mBitmaps[7];

    case AUTOMATIC_ELEVATOR_ITEM:
      return mBitmaps[8];

    case ROBOT_ITEM:
      return mBitmaps[9];

    case AMMO_ITEM:
      return mBitmaps[10];
  }

  return 0;
}
//---------------------------------------------------------------------------

Graphics::TBitmap* TMain::GetPropBitmap(int _fileIndex)
{
  std::string lFileName = std::string("data\\main_anim") + AnsiString(_fileIndex).c_str() + std::string(".bmp");

  // check if this file is in list
  for(unsigned int i = 0; i < mPropBitmaps.size(); i++)
  {
    if (mPropBitmaps[i].mFileName == lFileName)
    {
      return mPropBitmaps[i].mBitmap;
    }
  }

  if (!FileExists(lFileName.c_str()))
  {
    return 0;
  }

  // add a new bitmap
  Graphics::TBitmap* lBmp = new Graphics::TBitmap();

  lBmp->LoadFromFile(lFileName.c_str());
  lBmp->Transparent = true;
  lBmp->TransparentColor = TColor(0);

  TPropsData lData;

  lData.mFileName = lFileName;
  lData.mBitmap   = lBmp;

  mPropBitmaps.push_back(lData);

  // and return it
  return lBmp;
}
//---------------------------------------------------------------------------

void TMain::SetMapSize(int _width, int _height)
{
  if (mLevel)
  {
    delete mLevel;
  }

  CLayer* lLayer = new CLayer();
  mLevel = new CLevel(_width, _height);

  mLevel->AddLayer(lLayer);
  mLevel->SetTileSetDat("data\\tileset.txt");

  ComputeScrollLimits();

  scrollV->Position = 0;
  scrollH->Position = 0;

  // set the player params
  for(int i = 0; i < mLevel->GetItemCount(); i++)
  {
    CItem* lItem = mLevel->GetItem(i);

    if (lItem->GetType() == PLAYER_ITEM)
    {
      lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(0));
      lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(1));
      lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(2));
      lItem->SetParam(3, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(3));
      lItem->SetParam(4, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(4));
      break;
    }
  }
}
//---------------------------------------------------------------------------

void TMain::SetMapName(AnsiString _name)
{
  mMapName = _name;
  Main->Caption = AnsiString("Map Editor - Editing ") + mMapName;
}
//---------------------------------------------------------------------------

TPoint TMain::ElevatorDir(int _index)
{
  switch(_index)
  {
    case 0:
      return Point(0, -1);

    case 1:
      return Point(0, 1);

    case 2:
      return Point(-1, 0);

    case 3:
      return Point(1, 0);
  }

  return Point(0, 0);
}
//---------------------------------------------------------------------------

void __fastcall TMain::Exit1Click(TObject *Sender)
{
  if (mMapState == MAP_MODIFIED || mMapState == MAP_NEW)
  {
    switch (Application->MessageBox("The map is not saved. Do you want to save it ?",
                                    "Warning", MB_YESNOCANCEL))
    {
       case IDYES: //saves the file and exits
          Save1Click(Sender);
          break;

       case IDNO:  //exitx
          break;

       case IDCANCEL:
          return;
    }
  }

  SaveConfig();
  
  Application->Terminate();
}
//---------------------------------------------------------------------------
void __fastcall TMain::ZoneEditor1Click(TObject *Sender)
{
  FormTileEd->SetTileSetDat("blafornow");
  FormTileEd->ShowModal();
  FormTool->CreateTiles("data\\tileset.txt");
  paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TMain::New1Click(TObject *Sender)
{
  if (FormSize->ShowModal() == mrOk)
  {
    ClearChanges();

    mMapState = MAP_NEW;
    SetMapSize(FormSize->GetWidth(), FormSize->GetHeight());
    SetMapName("new.map");
    paintLevel->Visible = true;
    paintLevel->OnPaint(this);
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::paintLevelPaint(TObject *Sender)
{
  // paint the level
  if (mMapState == MAP_NONE)
  {
    return;
  }

  // erase
  paintLevel->Canvas->Brush->Color = clWhite;
  paintLevel->Canvas->FillRect(Rect(0, 0, paintLevel->Width, paintLevel->Height));

  paintLevel->Canvas->Brush->Color = clBlack;
  paintLevel->Canvas->FillRect(Rect(0, 0, std::min(paintLevel->Width, mLevel->GetWidth() * LEVEL_TILE_WIDTH), std::min(paintLevel->Height, mLevel->GetHeight() * LEVEL_TILE_HEIGHT)));

  // paint the level
  int lLeft = scrollH->Position / LEVEL_TILE_WIDTH;
  int lRight = lLeft + paintLevel->Width / LEVEL_TILE_WIDTH;

  int lTop = scrollV->Position / LEVEL_TILE_HEIGHT;
  int lBottom = lTop + paintLevel->Height / LEVEL_TILE_HEIGHT;

  CLayer* lLayer = mLevel->GetLayer(0);

  for(int x = lLeft; x < lRight; x++)
  {
    for(int y = lTop; y < lBottom; y++)
    {
      // do something!
      CBrush* lBrush = lLayer->GetBrush(x, y);

      int lX = x * LEVEL_TILE_WIDTH - scrollH->Position;
      int lY = y * LEVEL_TILE_HEIGHT - scrollV->Position;
      
      if (!lBrush)
      {
        paintLevel->Canvas->Brush->Color = (TColor)0x808080;
        paintLevel->Canvas->FillRect(TRect(lX, lY, lX + 16, lY + 16));
        continue;
      }

      // paint the brush
      CTile* lTile = FormTool->GetTile(lBrush->GetTileId());

      if (!lTile)
      {
        paintLevel->Canvas->Brush->Color = (TColor)0x808080;
        paintLevel->Canvas->FillRect(TRect(lX, lY, lX + 16, lY + 16));
        continue;
      }

      // draw the appropriate rectangle
      TRect lSrc = Bounds(lTile->GetX(), lTile->GetY(), lTile->GetW(), lTile->GetH());

      paintLevel->Canvas->CopyRect(
        TRect(lX, lY, lX + lTile->GetW(), lY + lTile->GetH()),
        FormTool->mBitmapTiles->Canvas,
        lSrc
      );
    }
  }

  // draw the selecting rectangle; this happens if the user uses the bucket paint
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

    paintLevel->Canvas->Brush->Color = TColor(0x00FFFF);
    paintLevel->Canvas->FrameRect(Rect(lSX, lSY, lCX, lCY));
  }

  TPoint lDir;

  // paint "teh" items
  for(int i = 0; i < mLevel->GetItemCount(); i++)
  {
    CItem* lItem = mLevel->GetItem(i);

    switch(lItem->GetType())
    {
      case PLAYER_ITEM:
      case HEALTH_ITEM:
      case WEAPON_ITEM:
      case MONEY_ITEM:
      case AMMO_ITEM:
      case MINE_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));
        break;

      case SLASHER_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));


        // paint the patrol extent marker
        paintLevel->Canvas->Pen->Color = (TColor)0x00FFFF;
        paintLevel->Canvas->MoveTo(lItem->GetX() - scrollH->Position - 16 * lItem->GetParam(4), lItem->GetY() - scrollV->Position);
        paintLevel->Canvas->LineTo(lItem->GetX() - scrollH->Position + 16 * lItem->GetParam(4), lItem->GetY() - scrollV->Position);
        break;

      case SHOOTER_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));


        // paint the patrol extent marker
        paintLevel->Canvas->Pen->Color = (TColor)0x00FFFF;
        paintLevel->Canvas->MoveTo(lItem->GetX() - scrollH->Position - 16 * lItem->GetParam(6), lItem->GetY() - scrollV->Position);
        paintLevel->Canvas->LineTo(lItem->GetX() - scrollH->Position + 16 * lItem->GetParam(6), lItem->GetY() - scrollV->Position);
        break;

      case TRIGGER_ENDLEVEL:
        paintLevel->Canvas->Brush->Color = (TColor)0x0000FF;
        paintLevel->Canvas->FrameRect(Rect(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position,
                                           lItem->GetX() - scrollH->Position + 32, lItem->GetY() - scrollV->Position + 32));


        paintLevel->Canvas->Font->Color = (TColor)0xFFFFFF;
        paintLevel->Canvas->Font->Name = "Small Fonts";
        paintLevel->Canvas->Font->Size = -7;
        SetBkMode(paintLevel->Canvas->Handle, TRANSPARENT);
        paintLevel->Canvas->TextOut(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position - 8, "Next: " + AnsiString(lItem->GetParam(0)));
        break;

      case TRIGGER_SAVEGAME:
        paintLevel->Canvas->Brush->Color = (TColor)0x00FF00;
        paintLevel->Canvas->FrameRect(Rect(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position,
                                           lItem->GetX() - scrollH->Position + 32, lItem->GetY() - scrollV->Position + 32));


        paintLevel->Canvas->Font->Color = (TColor)0xFFFFFF;
        paintLevel->Canvas->Font->Name = "Small Fonts";
        paintLevel->Canvas->Font->Size = -7;
        SetBkMode(paintLevel->Canvas->Handle, TRANSPARENT);
        paintLevel->Canvas->TextOut(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position - 8, "SAVEGAME");
        break;

      case TRIGGER_TEXT:
        paintLevel->Canvas->Brush->Color = (TColor)0xFF0000;
        paintLevel->Canvas->FrameRect(Rect(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position,
                                           lItem->GetX() - scrollH->Position + 32, lItem->GetY() - scrollV->Position + 32));


        paintLevel->Canvas->Font->Color = (TColor)0xFFFFFF;
        paintLevel->Canvas->Font->Name = "Small Fonts";
        paintLevel->Canvas->Font->Size = -7;
        SetBkMode(paintLevel->Canvas->Handle, TRANSPARENT);
        paintLevel->Canvas->TextOut(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position - 8, lItem->GetText().c_str());
        break;

      case BUTTON_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));

        // draw the id of the elevator this button is linked to                         
        paintLevel->Canvas->Font->Color = (TColor)0xFFFFFF;
        paintLevel->Canvas->Font->Name = "Small Fonts";
        paintLevel->Canvas->Font->Size = -6;
        SetBkMode(paintLevel->Canvas->Handle, TRANSPARENT);
        paintLevel->Canvas->TextOut(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position - 8, "Elevator: " + AnsiString(lItem->GetParam(0)));
        break;

      case ELEVATOR_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));

        // draw the id of the elevator
        paintLevel->Canvas->Font->Color = (TColor)0xFFFFFF;
        paintLevel->Canvas->Font->Name = "Small Fonts";
        paintLevel->Canvas->Font->Size = -6;
        SetBkMode(paintLevel->Canvas->Handle, TRANSPARENT);
        paintLevel->Canvas->TextOut(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position - 8, "ID: " + AnsiString(lItem->GetParam(0)));

        // draw the elevator move extents
        paintLevel->Canvas->Pen->Color = (TColor)0xFF0000;
        paintLevel->Canvas->Pen->Style = psDot;

        lDir = ElevatorDir(lItem->GetParam(1));

        paintLevel->Canvas->MoveTo(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position);
        paintLevel->Canvas->LineTo(lItem->GetX() - scrollH->Position + 16 * lItem->GetParam(2) * lDir.x, lItem->GetY() - scrollV->Position + 16 * lItem->GetParam(2) * lDir.y);
        break;

      case AUTOMATIC_ELEVATOR_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));

        // draw the elevator move extents
        paintLevel->Canvas->Pen->Color = (TColor)0xFF0000;
        paintLevel->Canvas->Pen->Style = psDot;

        lDir = ElevatorDir(lItem->GetParam(0));

        paintLevel->Canvas->MoveTo(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position);
        paintLevel->Canvas->LineTo(lItem->GetX() - scrollH->Position + 16 * lItem->GetParam(1) * lDir.x, lItem->GetY() - scrollV->Position + 16 * lItem->GetParam(1) * lDir.y);
        break;

      case ROBOT_ITEM:
        paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position,
                                 GetBitmap(lItem->GetType()));


        // paint the patrol extent marker
        paintLevel->Canvas->Pen->Color = (TColor)0x00FFFF;
        paintLevel->Canvas->MoveTo(lItem->GetX() - scrollH->Position - 16 * lItem->GetParam(3), lItem->GetY() - scrollV->Position);
        paintLevel->Canvas->LineTo(lItem->GetX() - scrollH->Position + 16 * lItem->GetParam(3), lItem->GetY() - scrollV->Position);
        break;

      case PROP_ITEM:
        Graphics::TBitmap* lBitmap = GetPropBitmap(lItem->GetParam(0));

        if (lBitmap)
        {
          paintLevel->Canvas->Draw(lItem->GetX() - scrollH->Position,
                                 lItem->GetY() - scrollV->Position, lBitmap);
        }
        else
        {
          paintLevel->Canvas->Brush->Color = (TColor)0xFF0000;
          paintLevel->Canvas->FillRect(TRect(lItem->GetX() - scrollH->Position, lItem->GetY() - scrollV->Position, lItem->GetX() - scrollH->Position + 16, lItem->GetY() - scrollV->Position + 16));
        }

        break;
    }
  }
                              

  if (ShowGrid1->Checked)
  {
    PaintGrid();
  }

  if (mTileSelectionValid)
  {
    paintLevel->Canvas->Brush->Color = TColor(0x0000FF);
    paintLevel->Canvas->FrameRect(mTileSelectionRect);
  }
}
//---------------------------------------------------------------------------

void TMain::PaintGrid()
{
  int lLeft = scrollH->Position / LEVEL_TILE_WIDTH;
  int lRight = lLeft + paintLevel->Width / LEVEL_TILE_WIDTH;

  int lTop = scrollV->Position / LEVEL_TILE_HEIGHT;
  int lBottom = lTop + paintLevel->Height / LEVEL_TILE_HEIGHT;

  paintLevel->Canvas->Pen->Color = (TColor)0x00FF00;
  paintLevel->Canvas->Pen->Style = psDot;

  for(int x = lLeft; x < lRight; x++)
  {
    for(int y = lTop; y < lBottom; y++)
    {
      int lX = x * LEVEL_TILE_WIDTH - scrollH->Position;
      int lY = y * LEVEL_TILE_HEIGHT - scrollV->Position;

      // paint the grid cross
      paintLevel->Canvas->MoveTo(lX - 1, lY);
      paintLevel->Canvas->LineTo(lX + 2, lY);

      paintLevel->Canvas->MoveTo(lX, lY - 1);
      paintLevel->Canvas->LineTo(lX, lY + 2);
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::scrollVScroll(TObject *Sender,
      TScrollCode ScrollCode, int &ScrollPos)
{
  paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TMain::scrollHScroll(TObject *Sender,
      TScrollCode ScrollCode, int &ScrollPos)
{
  paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TMain::FormResize(TObject *Sender)
{
  // recompute the scroll max
  if (mMapState == MAP_NONE)
  {
    return;
  }

  ComputeScrollLimits();
}
//---------------------------------------------------------------------------

void TMain::ComputeScrollLimits()
{
  scrollH->Max = std::max(0, mLevel->GetWidth() * LEVEL_TILE_WIDTH - paintLevel->Width);
  scrollV->Max = std::max(0, mLevel->GetHeight() * LEVEL_TILE_HEIGHT - paintLevel->Height);
}
//---------------------------------------------------------------------------

void __fastcall TMain::Save1Click(TObject *Sender)
{
  if (mMapState == MAP_NONE)
  {
    return;
  }

  // ask for a name if new map
  if (mMapState == MAP_NEW)
  {
    mMapState = MAP_NORMAL;
    Saveas1Click(Sender);
    return;
  }

  if (!mLevel->Save(mMapName.c_str()))
  {
    Application->MessageBox("Could not save the map", "Warning", MB_OK);
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::Saveas1Click(TObject *Sender)
{
  if (dlgSave->Execute())
  {
    if (mLevel->Save(dlgSave->FileName.c_str()))
    {
      mMapState = MAP_NORMAL;
      SetMapName(dlgSave->FileName.c_str());
    }
    else
    {
      Application->MessageBox("Could not save the map", "Warning", MB_OK);
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::Load1Click(TObject *Sender)
{
  // check if the map was modified or is new and ask to save
  if (mMapState == MAP_MODIFIED || mMapState == MAP_NEW)
  {
    switch(Application->MessageBox("The map is not saved. Do you want to save it ?",
                                    "Warning", MB_YESNOCANCEL))
    {
      case IDYES:
        Save1Click(Sender);
        break;

      case IDNO:
        break;

      case IDCANCEL:
        return;
    }
  }

  if (dlgLoad->Execute())
  {
    LoadMap(dlgLoad->FileName);
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::Close1Click(TObject *Sender)
{
  if (mMapState == MAP_MODIFIED || mMapState == MAP_NEW)
  {
    switch (Application->MessageBox("The map is not saved. Do you want to save it ?",
                                    "Warning", MB_YESNOCANCEL))
    {
       case IDYES:
          Save1Click(Sender);
          break;

       case IDNO:  //exitx
          break;

       case IDCANCEL:
          return;
    }
  }

  mMapState = MAP_NONE;
  paintLevel->Visible = false;
  paintLevel->OnPaint(this);
  ClearChanges();
}
//---------------------------------------------------------------------------

void __fastcall TMain::paintLevelMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  if (mMapState == MAP_NONE)
  {
    return;
  }

  // only left button does something
  if (Button != mbLeft)
  {
    return;
  }

  // depends on the selected paint tool
  switch(FormPaint->GetPaintTool())
  {
    case PT_BUCKET:
      mStartX = X;
      mStartY = Y;
      mCurrentX = X;
      mCurrentY = Y;
      mSelecting = true;
      break;

    case PT_SELECT:
      // snap to the closest grid
      mStartX = (X / 16) * 16;
      mStartY = (Y / 16) * 16;
      mCurrentX = mStartX;
      mCurrentY = mStartY;
      mSelecting = true;
      break;  
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::paintLevelMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
  if (mMapState == MAP_NONE)
  {
    return;
  }

  mPasteX = X;
  mPasteY = Y;

  // depends on the selected paint tool
  switch(FormPaint->GetPaintTool())
  {
    case PT_BUCKET:
      if (!mSelecting)
      {
        return;
      }

      mCurrentX = X;
      mCurrentY = Y;
      paintLevel->OnPaint(this);

      break;

    case PT_SELECT:
      if (!mSelecting)
      {
        return;
      }

      mCurrentX = X;
      mCurrentY = Y;
      paintLevel->OnPaint(this);

      break;  
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::paintLevelMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
  // depends on the selected paint tool
  if (mMapState == MAP_NONE)
  {
    return;
  }

  if (Button == mbRight)
  {
    // select the current tile and focus in the tools tab
    int lEndLogicX = (scrollH->Position + X) / LEVEL_TILE_WIDTH;
    int lEndLogicY = (scrollV->Position + Y) / LEVEL_TILE_HEIGHT;

    CBrush* lBrush = mLevel->GetLayer(0)->GetBrush(lEndLogicX, lEndLogicY);

    if (!lBrush)
    {
      return;
    }

    int lTileId = lBrush->GetTileId();

    if (lTileId != -1)
    {
      FormTool->PageControl1->ActivePage = FormTool->tabTiles;
      FormTool->DrawGrid1->Row = lTileId;
    }

    return;
  }

  // only left button does something
  if (Button != mbLeft)
  {
    return;
  }

  int lX, lY, lIndex;
  int lStartLogicX, lStartLogicY, lEndLogicX, lEndLogicY;

  //
  // Save the map state for later undo
  //
  SaveForUndo();

  // asked for editing
  if (Shift.Contains(ssCtrl))
  {
    EditItem(X, Y);
    return;
  }

  //
  // Selecting tiles and copying them to clipboard
  //
  if(FormPaint->GetPaintTool() ==  PT_SELECT)
  {
    mSelecting = false;

    lStartLogicX = (scrollH->Position + mStartX) / LEVEL_TILE_WIDTH;
    lStartLogicY = (scrollV->Position + mStartY) / LEVEL_TILE_HEIGHT;
    lEndLogicX = (scrollH->Position + X) / LEVEL_TILE_WIDTH + 1;
    lEndLogicY = (scrollV->Position + Y) / LEVEL_TILE_HEIGHT + 1;

    // sort the start/end values
    if (lStartLogicX > lEndLogicX)
    {
      int lTmp   = lEndLogicX;
      lEndLogicX = lStartLogicX;
      lStartLogicX = lTmp;
    }

    if (lStartLogicY > lEndLogicY)
    {
      int lTmp   = lEndLogicY;
      lEndLogicY = lStartLogicY;
      lStartLogicY = lTmp;
    }

    mClipboard.CopySelection(mLevel, lStartLogicX, lStartLogicY, lEndLogicX, lEndLogicY);

    mTileSelectionValid = true;
    mTileSelectionRect  = TRect(lStartLogicX * LEVEL_TILE_WIDTH, lStartLogicY * LEVEL_TILE_HEIGHT, lEndLogicX * LEVEL_TILE_WIDTH, lEndLogicY * LEVEL_TILE_HEIGHT);
  }

  //
  // Tiles are visible
  //
  if (FormTool->PageControl1->ActivePage == FormTool->tabTiles)
  {
    CTile* lTile;

    switch(FormPaint->GetPaintTool())
    {
      case PT_BUCKET:

        mSelecting = false;

        lStartLogicX = (scrollH->Position + mStartX) / LEVEL_TILE_WIDTH;
        lStartLogicY = (scrollV->Position + mStartY) / LEVEL_TILE_HEIGHT;
        lEndLogicX = (scrollH->Position + X) / LEVEL_TILE_WIDTH;
        lEndLogicY = (scrollV->Position + Y) / LEVEL_TILE_HEIGHT;

        // sort the start/end values
        if (lStartLogicX > lEndLogicX)
        {
          int lTmp   = lEndLogicX;
          lEndLogicX = lStartLogicX;
          lStartLogicX = lTmp;
        }

        if (lStartLogicY > lEndLogicY)
        {
          int lTmp   = lEndLogicY;
          lEndLogicY = lStartLogicY;
          lStartLogicY = lTmp;
        }

        lIndex = FormTool->DrawGrid1->Row;

        if (lIndex < 0)
        {
          return;
        }

        // create a brush
        lTile = FormTool->GetTile(lIndex);

        // is the tile invalid?
        if (!lTile)
        {
          return;
        }

        lStartLogicX = max(0, lStartLogicX);
        lStartLogicY = max(0, lStartLogicY);


        for(int x = lStartLogicX; x < lEndLogicX; x+=lTile->GetW()/LEVEL_TILE_WIDTH)
        {
          for(int y = lStartLogicY; y < lEndLogicY; y+=lTile->GetH()/LEVEL_TILE_HEIGHT)
          {
            CBrush* lBrush = new CBrush();

            lBrush->SetW(lTile->GetW());
            lBrush->SetH(lTile->GetH());
            lBrush->SetTileId(lIndex);

            mLevel->GetLayer(0)->SetBrush(x, y, lBrush);
          }
        }

        break;

      case PT_ERASER:
        lX = (scrollH->Position + X) / LEVEL_TILE_WIDTH;
        lY = (scrollV->Position + Y) / LEVEL_TILE_HEIGHT;

        if (lX >= mLevel->GetWidth() ||
            lY >= mLevel->GetHeight())
        {
          return;
        }

        mLevel->GetLayer(0)->SetBrush(lX, lY, 0);
        break;

      case PT_PENCIL:
        lX = (scrollH->Position + X) / LEVEL_TILE_WIDTH;
        lY = (scrollV->Position + Y) / LEVEL_TILE_HEIGHT;

        if (lX >= mLevel->GetWidth() ||
            lY >= mLevel->GetHeight())
        {
          return;
        }

        lIndex = FormTool->DrawGrid1->Row;

        if (lIndex >= 0)
        {
          // create a brush
          CTile*  lTile = FormTool->GetTile(lIndex);

          // is the tile invalid?
          if (!lTile)
          {
            return;
          }

          CBrush* lBrush = new CBrush();

          lBrush->SetW(lTile->GetW());
          lBrush->SetH(lTile->GetH());
          lBrush->SetTileId(lIndex);

          mLevel->GetLayer(0)->SetBrush(lX, lY, lBrush);
        }

        break;
    }
  }

  //
  // Misc are visible
  //
  if (FormTool->PageControl1->ActivePage == FormTool->tabMisc)
  {
    switch(FormPaint->GetPaintTool())
    {
      case PT_PENCIL:
        // update the player position
        if (FormTool->DrawGrid2->Row == 0)
        {
          for(int i = 0; i < mLevel->GetItemCount(); i++)
          {
            CItem* lItem = mLevel->GetItem(i);

            if (lItem->GetType() == PLAYER_ITEM)
            {
              lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(PLAYER_ITEM)->Width));
              lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(PLAYER_ITEM)->Height));
              lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(0));
              lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(1));
              lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(2));
              lItem->SetParam(3, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(3));
              lItem->SetParam(4, FormParams->GetItemRepository()->GetParams(PLAYER_ITEM)->GetParams(4));
              break;
            }
          }
        }
        break;
    }
  }

  //
  // Items are visible
  //
  if (FormTool->PageControl1->ActivePage == FormTool->tabItems)
  {
    CItem* lItem;

    switch(FormPaint->GetPaintTool())
    {
      case PT_PENCIL:

        switch(FormTool->DrawGrid3->Row)
        {
          case 0: // health
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(HEALTH_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(HEALTH_ITEM)->Height));
            lItem->SetType(HEALTH_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(HEALTH_ITEM)->GetParams(0));
            mLevel->AddItem(lItem);
            break;

          case 1: // weapon
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(WEAPON_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(WEAPON_ITEM)->Height));
            lItem->SetType(WEAPON_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(WEAPON_ITEM)->GetParams(0));
            mLevel->AddItem(lItem);
            break;

          case 2: // mine
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(MINE_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(MINE_ITEM)->Height));
            lItem->SetType(MINE_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(MINE_ITEM)->GetParams(0));
            mLevel->AddItem(lItem);
            break;

          case 3: // props
            lItem = new CItem();
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(PROP_ITEM)->GetParams(0));
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetPropBitmap(lItem->GetParam(0))->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetPropBitmap(lItem->GetParam(0))->Height));
            lItem->SetType(PROP_ITEM);
            lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(PROP_ITEM)->GetParams(1));
            lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(PROP_ITEM)->GetParams(2));
            mLevel->AddItem(lItem);
            break;

          case 4: // ammo
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(AMMO_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(AMMO_ITEM)->Height));
            lItem->SetType(AMMO_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(AMMO_ITEM)->GetParams(0));
            mLevel->AddItem(lItem);
            break;
        }
        break;

      case PT_ERASER:
        for(int i = 0; i < mLevel->GetItemCount(); i++)
        {
          CItem* lItem = mLevel->GetItem(i);

          // may erase only items of this kind
          int lType = lItem->GetType();

          if (lType != HEALTH_ITEM && lType != WEAPON_ITEM && lType != MINE_ITEM && lType != PROP_ITEM && lType != AMMO_ITEM)
          {
            continue;
          }

          if (scrollH->Position + X - 5 < lItem->GetX() && lItem->GetX() < scrollH->Position + X &&
              scrollV->Position + Y - 5 < lItem->GetY() && lItem->GetY() < scrollV->Position + Y + 5)
          {
            mLevel->RemoveItem(i);
          }
        }
        break;
    }
  }

  //
  // Triggers are visible
  //
  if (FormTool->PageControl1->ActivePage == FormTool->tabTriggers)
  {
    CItem* lItem;

    switch(FormPaint->GetPaintTool())
    {
      case PT_PENCIL:

        switch(FormTool->DrawGrid4->Row)
        {
          case 0:
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, 32));//GetBitmap(TRIGGER_ENDLEVEL)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, 32));//GetBitmap(TRIGGER_ENDLEVEL)->Height));
            lItem->SetType(TRIGGER_ENDLEVEL);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(TRIGGER_ENDLEVEL)->GetParams(0));
            mLevel->AddItem(lItem);
            break;

          case 1:
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, 32));//GetBitmap(TRIGGER_TEXT)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, 32));//GetBitmap(TRIGGER_TEXT)->Height));
            lItem->SetType(TRIGGER_TEXT);
            lItem->SetText(FormParams->GetItemRepository()->GetParams(TRIGGER_TEXT)->GetText());
            mLevel->AddItem(lItem);
            break;

          case 2:
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, 32));//GetBitmap(TRIGGER_SAVEGAME)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, 32));//GetBitmap(TRIGGER_SAVEGAME)->Height));
            lItem->SetType(TRIGGER_SAVEGAME);
            mLevel->AddItem(lItem);
            break;
        }

        break;

      case PT_ERASER:
        for(int i = 0; i < mLevel->GetItemCount(); i++)
        {
          CItem* lItem = mLevel->GetItem(i);

          // may erase only items of this kind
          int lType = lItem->GetType();

          if (lType != TRIGGER_SAVEGAME && lType != TRIGGER_TEXT && lType != TRIGGER_ENDLEVEL)
          {
            continue;
          }

          if (scrollH->Position + X - 32 <= lItem->GetX() && lItem->GetX() <= scrollH->Position + X + 32 &&
              scrollV->Position + Y -32 <= lItem->GetY() && lItem->GetY() <= scrollV->Position + Y)
          {
            mLevel->RemoveItem(i);
          }
        }
        break;
    }
  }

  //
  // Enemies are visible
  //
  if (FormTool->PageControl1->ActivePage == FormTool->tabEnemy)
  {
    CItem* lItem;

    switch(FormPaint->GetPaintTool())
    {
      case PT_PENCIL:
        switch(FormTool->DrawGrid5->Row)
        {
          case 0:
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(SLASHER_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(SLASHER_ITEM)->Height));
            lItem->SetType(SLASHER_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(0));
            lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(1));
            lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(2));
            lItem->SetParam(3, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(3));
            lItem->SetParam(4, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(4));
            lItem->SetParam(5, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(5));
            lItem->SetParam(6, FormParams->GetItemRepository()->GetParams(SLASHER_ITEM)->GetParams(6));
            mLevel->AddItem(lItem);
            break;

          case 1:
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(SHOOTER_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(SHOOTER_ITEM)->Height));
            lItem->SetType(SHOOTER_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(0));
            lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(1));
            lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(2));
            lItem->SetParam(3, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(3));
            lItem->SetParam(4, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(4));
            lItem->SetParam(5, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(5));
            lItem->SetParam(6, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(6));
            lItem->SetParam(7, FormParams->GetItemRepository()->GetParams(SHOOTER_ITEM)->GetParams(7));
            mLevel->AddItem(lItem);
            break;

          case 2:
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(ROBOT_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(ROBOT_ITEM)->Height));
            lItem->SetType(ROBOT_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(ROBOT_ITEM)->GetParams(0));
            lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(ROBOT_ITEM)->GetParams(1));
            lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(ROBOT_ITEM)->GetParams(2));
            lItem->SetParam(3, FormParams->GetItemRepository()->GetParams(ROBOT_ITEM)->GetParams(3));
            lItem->SetParam(4, FormParams->GetItemRepository()->GetParams(ROBOT_ITEM)->GetParams(4));
            mLevel->AddItem(lItem);
            break;
        }
        break;
        
      case PT_ERASER:
        for(int i = 0; i < mLevel->GetItemCount(); i++)
        {
          CItem* lItem = mLevel->GetItem(i);

          // may erase only items of this kind
          int lType = lItem->GetType();

          if (lType != SHOOTER_ITEM && lType != SLASHER_ITEM && lType != ROBOT_ITEM)
          {
            continue;
          }
          
          if (scrollH->Position + X - 32 <= lItem->GetX() && lItem->GetX() <= scrollH->Position + X + 32 &&
              scrollV->Position + Y -32 <= lItem->GetY() && lItem->GetY() <= scrollV->Position + Y)
          {
            mLevel->RemoveItem(i);
          }
        }
        break;
    }
  }
  
  //
  // Buttons/elevators are visible
  //
  if (FormTool->PageControl1->ActivePage == FormTool->tabElevators)
  {
    CItem* lItem;

    switch(FormPaint->GetPaintTool())
    {
      case PT_PENCIL:

        switch(FormTool->DrawGrid6->Row)
        {
          case 0: // button
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(BUTTON_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(BUTTON_ITEM)->Height));
            lItem->SetType(BUTTON_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(BUTTON_ITEM)->GetParams(0));
            mLevel->AddItem(lItem);
            break;

          case 1: // elevator
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(ELEVATOR_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(ELEVATOR_ITEM)->Height));
            lItem->SetType(ELEVATOR_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(ELEVATOR_ITEM)->GetParams(0));
            lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(ELEVATOR_ITEM)->GetParams(1));
            lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(ELEVATOR_ITEM)->GetParams(2));
            mLevel->AddItem(lItem);
            break;

          case 2: // automatic elevator
            lItem = new CItem();
            lItem->SetX(FormDrop->GetX(scrollH->Position + X, GetBitmap(AUTOMATIC_ELEVATOR_ITEM)->Width));
            lItem->SetY(FormDrop->GetY(scrollV->Position + Y, GetBitmap(AUTOMATIC_ELEVATOR_ITEM)->Height));
            lItem->SetType(AUTOMATIC_ELEVATOR_ITEM);
            lItem->SetParam(0, FormParams->GetItemRepository()->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(0));
            lItem->SetParam(1, FormParams->GetItemRepository()->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(1));
            lItem->SetParam(2, FormParams->GetItemRepository()->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(2));
            lItem->SetParam(3, FormParams->GetItemRepository()->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(3));
            lItem->SetParam(4, FormParams->GetItemRepository()->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(4));
            mLevel->AddItem(lItem);
            break;
        }
        break;

      case PT_ERASER:
        for(int i = 0; i < mLevel->GetItemCount(); i++)
        {
          CItem* lItem = mLevel->GetItem(i);

          // may erase only items of this kind
          int lType = lItem->GetType();

          if (lType != BUTTON_ITEM && lType != ELEVATOR_ITEM && lType != AUTOMATIC_ELEVATOR_ITEM)
          {
            continue;
          }

          if (scrollH->Position + X - 5 < lItem->GetX() && lItem->GetX() < scrollH->Position + X &&
              scrollV->Position + Y - 5 < lItem->GetY() && lItem->GetY() < scrollV->Position + Y + 5)
          {
            mLevel->RemoveItem(i);
          }
        }
        break;
    }
  }

  paintLevel->OnPaint(this);

  mMapState = MAP_MODIFIED;
}
//---------------------------------------------------------------------------

void __fastcall TMain::PasteMapPart1Click(TObject *Sender)
{
  if (mMapState == MAP_NONE)
  {
    return;
  }

  // save the current state
  SaveForUndo();

  // do the paste
  mClipboard.PasteSelection(mLevel, (mPasteX / 16), (mPasteY / 16));

  paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TMain::MakeClean1Click(TObject *Sender)
{
  mClipboard.Clear();
}
//---------------------------------------------------------------------------

void __fastcall TMain::LoadArchitecturetoClipboard1Click(TObject *Sender)
{
  if (dlgLoadArch->Execute())
  {
    mClipboard.Load(dlgLoadArch->FileName.c_str());
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::SaveArchitecturefromClipboard1Click(TObject *Sender)
{
  if (dlgSaveArch->Execute())
  {
    mClipboard.Save(dlgSaveArch->FileName.c_str());
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::ShowGrid1Click(TObject *Sender)
{
  // set the grid to show
  ShowGrid1->Checked = !ShowGrid1->Checked;

  paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TMain::ShowRulers1Click(TObject *Sender)
{
  // set the rulers to show
}
//---------------------------------------------------------------------------

void __fastcall TMain::Undo1Click(TObject *Sender)
{
  int lSize = mUndoList.size();

  if (lSize <= 0)
  {
    return;
  }

  mRedoList.push_back(mLevel->GetMemento());

  mLevel->SetMemento(mUndoList[lSize - 1]);

  //mRedoList.push_back(mUndoList[lSize - 1]);

  mUndoList.pop_back();

  paintLevel->OnPaint(this);

  //StatusBar1->SimpleText = AnsiString("undo: ") + mUndoList.size() + ", redo: " + mRedoList.size();
}
//---------------------------------------------------------------------------

void __fastcall TMain::Redo1Click(TObject *Sender)
{
  int lSize = mRedoList.size();

  if (lSize <= 0)
  {
    return;
  }

  mUndoList.push_back(mLevel->GetMemento());

  mLevel->SetMemento(mRedoList[lSize - 1]);

  mRedoList.pop_back();

  paintLevel->OnPaint(this);

  //StatusBar1->SimpleText = AnsiString("undo: ") + mUndoList.size() + ", redo: " + mRedoList.size();
}
//---------------------------------------------------------------------------

void TMain::SaveForUndo()
{
  if (mUndoList.size() >= 100)
  {
    std::vector<CMementoLevel*>::iterator lIt = mUndoList.begin();
    mUndoList.erase(lIt);

    if (mRedoList.size() > 0)
    {
      lIt = mRedoList.begin();
      mRedoList.erase(lIt);
    }
  }

  mUndoList.push_back(mLevel->GetMemento());
}
//---------------------------------------------------------------------------

void TMain::EditItem(int X, int Y)
{
  for(int i = 0; i < mLevel->GetItemCount(); i++)
  {
    CItem* lItem = mLevel->GetItem(i);

    if (scrollH->Position + X - 10 < lItem->GetX() && lItem->GetX() < scrollH->Position + X &&
        scrollV->Position + Y - 10 < lItem->GetY() && lItem->GetY() < scrollV->Position + Y + 10)
    {
      FormItemEdit->SetItem(lItem);

      FormItemEdit->ShowModal();

      break;
    }
  }
}
//---------------------------------------------------------------------------

void __fastcall TMain::FormClose(TObject *Sender, TCloseAction &Action)
{
  if (mMapState == MAP_MODIFIED || mMapState == MAP_NEW)
  {
    switch (Application->MessageBox("The map is not saved. Do you want to save it ?",
                                    "Warning", MB_YESNOCANCEL))
    {
       case IDYES: //saves the file and exits
          Save1Click(Sender);
          Action = caFree;
          break;

       case IDNO:  //exitx
          Action = caFree;
          break;

       case IDCANCEL:
          Action = caNone;
          break;
    }
  }

  SaveConfig();
}
//---------------------------------------------------------------------------

void __fastcall TMain::FormShow(TObject *Sender)
{
  LoadConfig();
}
//---------------------------------------------------------------------------

void __fastcall TMain::HistoryClick(TObject *Sender)
{
  AnsiString lFile = ((TMenuItem*)Sender)->Caption;

  // first four chars are shortcut and spaces
  lFile.Delete(1, 4);

  LoadMap(lFile);
}
//---------------------------------------------------------------------------

void TMain::LoadMap(AnsiString _fileName)
{
  if (!FileExists(_fileName))
  {
    Application->MessageBox(AnsiString(AnsiString("File ") + _fileName + " does not exist").c_str(), "Error");
    return;
  }

  // check if the file is in the history list
  bool lFound = false;

  for(int i = 0; i < mFileHistory.size(); i++)
  {
    if (mFileHistory[i] == _fileName)
    {
      lFound = true;
    }
  }

  if (!lFound)
  {
    if (mFileHistory.size() < HISTORY_MENU_MAX_ITEMS)
    {
      mFileHistory.push_back(_fileName);

      TMenuItem* lMenuItem = new TMenuItem(MainMenu1);

      lMenuItem->Caption = AnsiString("");
      lMenuItem->OnClick = HistoryClick;

      File1->Insert(HISTORY_MENU_ITEMINDEX, lMenuItem);
    }
    else
    {
      mFileHistory[mFileHistoryIndex] = _fileName;

      mFileHistoryIndex += 1;
      mFileHistoryIndex %= HISTORY_MENU_MAX_ITEMS;
    }
  }

  for(int i = 0; i < mFileHistory.size(); i++)
  {
    File1->Items[i + HISTORY_MENU_ITEMINDEX]->Caption = AnsiString("&") + AnsiString(i + 1) + "  " + mFileHistory[i];
  }
  // end history update

  // -- crap code start
  if (mLevel)
  {
    delete mLevel;
  }

  CLayer* lLayer = new CLayer();
  mLevel = new CLevel(25, 25);

  mLevel->AddLayer(lLayer);
  mLevel->SetTileSetDat("data\\tileset.txt");

  ComputeScrollLimits();
  scrollV->Position = 0;
  scrollH->Position = 0;
  // -- crap code end

  if (mLevel->Load(_fileName.c_str()))
  {
    mMapState = MAP_NORMAL;
    //SetMapSize(mLevel->GetWidth(), mLevel->GetHeight());
    SetMapName(_fileName);

    paintLevel->Visible = true;
    paintLevel->OnPaint(this);
  }

  ClearChanges();
}
//---------------------------------------------------------------------------

void __fastcall TMain::Verify1Click(TObject *Sender)
{
  if (mMapState == MAP_NONE)
  {
    FormLog->Add("Load a map before verify.");
    return;
  }

  FormLog->Add("Starting verification...");

  // verify
  VerifyCyberNinjaLevel lVerify = VerifyCyberNinjaLevel(mLevel);

  bool lResult = lVerify.Verify();

  FormLog->Add(lVerify.GetErrors());
  FormLog->Add(lVerify.GetWarnings());

  if (!lResult)
  {
    FormLog->Add("Map verification failed.");

    Application->MessageBox("Map verification failed", "Warning", MB_OK);
  }

  FormLog->Add("Verification complete.");
}
//---------------------------------------------------------------------------


void __fastcall TMain::About1Click(TObject *Sender)
{
  FormAbout->ShowModal();        
}
//---------------------------------------------------------------------------

