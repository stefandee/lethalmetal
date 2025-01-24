//---------------------------------------------------------------------------

#include <vcl.h>
#include <stdio.h>
#pragma hdrstop

#include "FTool.h"
#include "FParams.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormTool *FormTool;
//---------------------------------------------------------------------------

__fastcall TFormTool::TFormTool(TComponent* Owner)
        : TForm(Owner)
{
  mBitmapTiles = new Graphics::TBitmap();

  mBitmapTiles->LoadFromFile(AnsiString("data\\tileset.bmp"));
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::FormCreate(TObject *Sender)
{
  CreateTiles("data\\tileset.txt");
  CreateEnemy();
  CreateItems();
  CreateMisc();
  CreateTriggers();
  CreateElevators();
}
//---------------------------------------------------------------------------

void TFormTool::CreateTiles(AnsiString _file)
{
  FILE* lFile;

  lFile = fopen(_file.c_str(), "rt");

  if (!lFile)
  {
    return;
  }

  mTileList.clear();

  while(!feof(lFile))
  {
    char lName[200];
    int  lX, lY, lW, lH, lC1, lC2, lC3, lC4, lLadder, lHeadLadder;

    fscanf(lFile, "%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", lName, &lX, &lY, &lW, &lH, &lC1, &lC2, &lC3, &lC4, &lLadder, &lHeadLadder);

    CTile* lTile = new CTile();

    lTile->SetX(lX);
    lTile->SetY(lY);
    lTile->SetW(lW);
    lTile->SetH(lH);
    lTile->SetIsLadder((bool)lLadder);
    lTile->SetIsHeadLadder((bool)lHeadLadder);
    lTile->SetName(std::string(lName));

    mTileList.push_back(lTile);
  }

  fclose(lFile);

  DrawGrid1->RowCount = mTileList.size();

  for(unsigned int i = 0; i < mTileList.size(); i++)
  {
    DrawGrid1->RowHeights[i] = mTileList[i]->GetH() + 2;
  }
}
//---------------------------------------------------------------------------

void TFormTool::CreateEnemy()
{
  mEnemyList.clear();

  Graphics::TBitmap* lBitmap = new Graphics::TBitmap();

  lBitmap->LoadFromFile("data\\ed_slasher.bmp");

  mEnemyList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_shooter.bmp");

  mEnemyList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_robot.bmp");

  mEnemyList.push_back(lBitmap);
}
//---------------------------------------------------------------------------

void TFormTool::CreateItems()
{
  mItemsList.clear();

  Graphics::TBitmap* lBitmap = new Graphics::TBitmap();

  lBitmap->LoadFromFile("data\\ed_health.bmp");

  mItemsList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_weapon.bmp");

  mItemsList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_mine.bmp");

  mItemsList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_prop.bmp");
  mItemsList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_ammo.bmp");

  mItemsList.push_back(lBitmap);
}
//---------------------------------------------------------------------------

void TFormTool::CreateMisc()
{
  mMiscList.clear();

  Graphics::TBitmap* lBitmap = new Graphics::TBitmap();

  lBitmap->LoadFromFile("data\\playerpos.bmp");

  mMiscList.push_back(lBitmap);
}
//---------------------------------------------------------------------------

void TFormTool::CreateTriggers()
{
  mTriggersList.clear();

  Graphics::TBitmap* lBitmap = new Graphics::TBitmap();

  lBitmap->LoadFromFile("data\\ed_trigger_endlevel.bmp");

  mTriggersList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_trigger_text.bmp");

  mTriggersList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_trigger_savegame.bmp");

  mTriggersList.push_back(lBitmap);
}
//---------------------------------------------------------------------------

void TFormTool::CreateElevators()
{
  mElevatorsList.clear();

  Graphics::TBitmap* lBitmap = new Graphics::TBitmap();

  lBitmap->LoadFromFile("data\\ed_button.bmp");

  mElevatorsList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_elevator.bmp");

  mElevatorsList.push_back(lBitmap);

  lBitmap = new Graphics::TBitmap();
  lBitmap->LoadFromFile("data\\ed_automatic_elevator.bmp");

  mElevatorsList.push_back(lBitmap);
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::DrawGrid1DrawCell(TObject *Sender, int ACol,
      int ARow, TRect &Rect, TGridDrawState State)
{
  if (ARow >= (int)mTileList.size())
  {
    return;
  }

  CTile* lTile = mTileList[ARow];

  TRect lSrc = Bounds(lTile->GetX(), lTile->GetY(), lTile->GetW(), lTile->GetH());
  //TRect lSrc  = TRect(0, 0, mBitmapTiles->Width, mBitmapTiles->Height);

  DrawGrid1->Canvas->CopyRect(
    TRect(Rect.left, Rect.top, Rect.left + lTile->GetW(), Rect.top + lTile->GetH()),
    mBitmapTiles->Canvas,
    lSrc
  );
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::DrawGrid2DrawCell(TObject *Sender, int ACol,
      int ARow, TRect &Rect, TGridDrawState State)
{
  if (ARow >= (int)mMiscList.size())
  {
    return;
  }

  DrawGrid2->Canvas->Draw(Rect.left, Rect.top, mMiscList[ARow]);
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::DrawGrid3DrawCell(TObject *Sender, int ACol,
      int ARow, TRect &Rect, TGridDrawState State)
{
  if (ARow >= (int)mItemsList.size())
  {
    return;
  }

  DrawGrid3->Canvas->Draw(Rect.left, Rect.top, mItemsList[ARow]);
}
//---------------------------------------------------------------------------

CTile* TFormTool::GetTile(int _index)
{
  if (_index < 0 || _index >= (int)mTileList.size())
  {
    return 0;
  }

  return mTileList[_index];
}
//---------------------------------------------------------------------------


void __fastcall TFormTool::DrawGrid4DrawCell(TObject *Sender, int ACol,
      int ARow, TRect &Rect, TGridDrawState State)
{
  if (ARow >= (int)mTriggersList.size())
  {
    return;
  }

  DrawGrid4->Canvas->Draw(Rect.left, Rect.top, mTriggersList[ARow]);
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::Edit1Click(TObject *Sender)
{
  // choose now
  if (PageControl1->ActivePage == tabTiles)
  {
  }

  if (PageControl1->ActivePage == tabEnemy)
  {
  }

  if (PageControl1->ActivePage == tabItems)
  {
    switch(FormTool->DrawGrid3->Row)
    {
      case 0:
        break;

      case 1:
        break;

      case 2:
        break;

      case 3:
        break;
    }
  }

  if (PageControl1->ActivePage == tabMisc)
  {
  }

  if (PageControl1->ActivePage == tabTriggers)
  {
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::DrawGrid5DrawCell(TObject *Sender, int ACol,
      int ARow, TRect &Rect, TGridDrawState State)
{
  if (ARow >= (int)mEnemyList.size())
  {
    return;
  }

  DrawGrid5->Canvas->Draw(Rect.left, Rect.top, mEnemyList[ARow]);
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::DrawGrid6DrawCell(TObject *Sender, int ACol,
      int ARow, TRect &Rect, TGridDrawState State)
{
  if (ARow >= (int)mElevatorsList.size())
  {
    return;
  }

  DrawGrid6->Canvas->Draw(Rect.left, Rect.top, mElevatorsList[ARow]);
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::PageControl1Change(TObject *Sender)
{
  switch(PageControl1->ActivePageIndex)
  {
    case 3:
      FormParams->SetActivePage(PLAYER_ITEM);
      break;

    case 2:
      FormParams->SetActivePage(MONEY_ITEM);
      break;

    case 1:
      switch(FormTool->DrawGrid5->Row)
      {
        case 0:
          FormParams->SetActivePage(SLASHER_ITEM);
          break;

        case 1:
          FormParams->SetActivePage(SHOOTER_ITEM);
          break;

        case 2:
          FormParams->SetActivePage(ROBOT_ITEM);
          break;
      }
      break;

    case 4:
      FormParams->SetActivePage(TRIGGER_TEXT);
      break;

    case 5:
      FormParams->SetActivePage(BUTTON_ITEM);
      break;
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormTool::DrawGrid5Click(TObject *Sender)
{
  switch(FormTool->DrawGrid5->Row)
  {
    case 0:
      FormParams->SetActivePage(SLASHER_ITEM);
      break;

    case 1:
      FormParams->SetActivePage(SHOOTER_ITEM);
      break;

    case 2:
      FormParams->SetActivePage(ROBOT_ITEM);
      break;
  }
}
//---------------------------------------------------------------------------

