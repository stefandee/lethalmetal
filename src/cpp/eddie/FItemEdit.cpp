//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "FItemEdit.h"
#include "FMain.h"
//---------------------------------------------------------------------------

#pragma package(smart_init)
#pragma resource "*.dfm"
TFormItemEdit *FormItemEdit;
//---------------------------------------------------------------------------

__fastcall TFormItemEdit::TFormItemEdit(TComponent* Owner)
        : TForm(Owner)
{
  //PageControl1->Visible = true;

  for(int i = 0; i < PageControl1->PageCount; i++)
  {
    //PageControl1->Pages[i]->Enabled = false;
    PageControl1->Pages[i]->TabVisible = false;
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormItemEdit::FormShow(TObject *Sender)
{
  //PageControl1->Pages[0]->Enabled = true;
  //PageControl1->Pages[0]->TabVisible = true;
}
//---------------------------------------------------------------------------

void TFormItemEdit::SetItem(CItem* _item)
{
  for(int i = 0; i < PageControl1->PageCount; i++)
  {
    PageControl1->Pages[i]->TabVisible = false;
  }

  switch(_item->GetType())
  {
    case PLAYER_ITEM             :
      PageControl1->Pages[1]->TabVisible = true;
      break;

    case HEALTH_ITEM             :
      PageControl1->Pages[0]->TabVisible = true;
      break;

    case WEAPON_ITEM             :
      PageControl1->Pages[13]->TabVisible = true;
      break;

    case MONEY_ITEM              :
      PageControl1->Pages[14]->TabVisible = true;
      break;

    case MINE_ITEM               :
      PageControl1->Pages[11]->TabVisible = true;
      break;

    case AMMO_ITEM               :
      PageControl1->Pages[12]->TabVisible = true;
      break;

    case SHOOTER_ITEM            :
      PageControl1->Pages[3]->TabVisible = true;
      break;

    case SLASHER_ITEM            :
      PageControl1->Pages[2]->TabVisible = true;
      break;

    case TRIGGER_ENDLEVEL        :
      PageControl1->Pages[4]->TabVisible = true;
      break;

    case TRIGGER_SAVEGAME        :
      //PageControl1->Pages[13]->TabVisible = true;
      break;

    case TRIGGER_TEXT            :
      PageControl1->Pages[8]->TabVisible = true;
      break;

    case BUTTON_ITEM             :
      PageControl1->Pages[9]->TabVisible = true;
      break;

    case ELEVATOR_ITEM           :
      PageControl1->Pages[5]->TabVisible = true;
      break;

    case AUTOMATIC_ELEVATOR_ITEM :
      PageControl1->Pages[10]->TabVisible = true;
      break;

    case ROBOT_ITEM              :
      PageControl1->Pages[6]->TabVisible = true;
      break;

    case PROP_ITEM               :
      PageControl1->Pages[7]->TabVisible = true;
      break;
  }

  mItem = _item;

  mBackupX = mItem->GetX();
  mBackupY = mItem->GetY();

  ItemToScreen();
}
//---------------------------------------------------------------------------

void TFormItemEdit::ItemToScreen()
{
  switch(mItem->GetType())
  {
    case PLAYER_ITEM             :
      cbPStance->ItemIndex = mItem->GetParam(0);
      edPHealth->Text      = AnsiString(mItem->GetParam(1));
      edPWeapon->Text      = AnsiString(mItem->GetParam(2));
      edPRec->Text         = AnsiString(mItem->GetParam(3));
      edPMoney->Text       = AnsiString(mItem->GetParam(4));
      break;

    case HEALTH_ITEM             :
      edHealth->Text = AnsiString(mItem->GetParam(0));
      break;

    case WEAPON_ITEM             :
      edWeapon->Text = AnsiString(mItem->GetParam(0));
      break;

    case MONEY_ITEM              :
      edMoney->Text  = AnsiString(mItem->GetParam(0));
      break;

    case MINE_ITEM               :
      edMine->Text   = AnsiString(mItem->GetParam(0));
      break;

    case AMMO_ITEM               :
      edAmmo->Text = AnsiString(mItem->GetParam(0));
      break;

    case SHOOTER_ITEM            :
      cbShStance->ItemIndex      = mItem->GetParam(0);
      edShHealth->Text           = AnsiString(mItem->GetParam(1));
      edShWeapon->Text           = AnsiString(mItem->GetParam(2));
      edShRec->Text              = AnsiString(mItem->GetParam(3));
      edShJoy->Text              = AnsiString(mItem->GetParam(4));
      cbShAggresivity->ItemIndex = mItem->GetParam(5);
      edShDist->Text             = AnsiString(mItem->GetParam(6));
      edShBulletsDrop->Text      = AnsiString(mItem->GetParam(7));
      break;

    case SLASHER_ITEM            :
      cbSlStance->ItemIndex      = mItem->GetParam(0);
      edSlHealth->Text           = AnsiString(mItem->GetParam(1));
      edSlWeapon->Text           = AnsiString(mItem->GetParam(2));
      edSlDist->Text             = AnsiString(mItem->GetParam(4));
      cbSlAggresivity->ItemIndex = mItem->GetParam(3);
      cbSlColor->Selected        = CodeToColor(mItem->GetParam(5));
      checkSlFriendly->Checked   = mItem->GetParam(6);
      break;

    case ROBOT_ITEM              :
      cbRShStance->ItemIndex      = mItem->GetParam(0);
      edRShHealth->Text           = AnsiString(mItem->GetParam(1));
      edRShWeapon->Text           = AnsiString(mItem->GetParam(2));
      edRShRec->Text              = AnsiString(mItem->GetParam(4));
      edRShDist->Text             = AnsiString(mItem->GetParam(3));
      break;

    case TRIGGER_ENDLEVEL        :
      edTriggerLevel->Text = mItem->GetParam(0);
      break;

    case TRIGGER_SAVEGAME        :
      //PageControl1->Pages[13]->TabVisible = true;
      break;

    case TRIGGER_TEXT            :
      edTriggerText->Text = mItem->GetText().c_str();
      break;

    case BUTTON_ITEM             :
      cbButtonElevator->Text = AnsiString(mItem->GetParam(0));
      break;

    case ELEVATOR_ITEM           :
      edElevatorId->Text        = AnsiString(mItem->GetParam(0));
      cbElevatorDir->ItemIndex  = mItem->GetParam(1);
      edElevatorDist->Text      = AnsiString(mItem->GetParam(2));
      break;

    case AUTOMATIC_ELEVATOR_ITEM :
      cbAElevatorDir->ItemIndex = mItem->GetParam(0);
      edAElevatorDist1->Text    = AnsiString(mItem->GetParam(1));
      edAElevatorDist2->Text    = AnsiString(mItem->GetParam(2));
      edAElevatorWait->Text     = AnsiString(mItem->GetParam(3));
      edAElevatorSpeed->Text    = AnsiString(mItem->GetParam(4));
      break;

    case PROP_ITEM               :
      edPFile->Text               = AnsiString(mItem->GetParam(0));
      edPTime->Text               = AnsiString(mItem->GetParam(1));
      cbPAutoTerminate->Checked   = mItem->GetParam(2);
      break;
  }
}
//---------------------------------------------------------------------------

void TFormItemEdit::ScreenToItem()
{
  switch(mItem->GetType())
  {
    case PLAYER_ITEM             :
      mItem->SetParam(0, cbPStance->ItemIndex);
      mItem->SetParam(1, edPHealth->Text.ToIntDef(3));
      mItem->SetParam(2, edPWeapon->Text.ToIntDef(5));
      mItem->SetParam(3, edPRec->Text.ToIntDef(3));
      mItem->SetParam(4, edPMoney->Text.ToIntDef(100));
      break;

    case HEALTH_ITEM             :
      mItem->SetParam(0, edHealth->Text.ToIntDef(1));
      break;

    case WEAPON_ITEM             :
      mItem->SetParam(0, edWeapon->Text.ToIntDef(1));
      break;

    case MONEY_ITEM              :
      mItem->SetParam(0, edMoney->Text.ToIntDef(10));
      break;

    case MINE_ITEM               :
      mItem->SetParam(0, edMine->Text.ToIntDef(1));
      break;

    case AMMO_ITEM               :
      mItem->SetParam(0, edAmmo->Text.ToIntDef(1));
      break;

    case SHOOTER_ITEM            :
      mItem->SetParam(0, cbShStance->ItemIndex);
      mItem->SetParam(1, edShHealth->Text.ToIntDef(3));
      mItem->SetParam(2, edShWeapon->Text.ToIntDef(3));
      mItem->SetParam(3, edShRec->Text.ToIntDef(100));
      mItem->SetParam(4, edShJoy->Text.ToIntDef(3));
      mItem->SetParam(5, cbShAggresivity->ItemIndex);
      mItem->SetParam(6, edShDist->Text.ToIntDef(3));
      mItem->SetParam(7, edShBulletsDrop->Text.ToIntDef(1));
      break;

    case SLASHER_ITEM            :
      mItem->SetParam(0, cbSlStance->ItemIndex);
      mItem->SetParam(1, edSlHealth->Text.ToIntDef(3));
      mItem->SetParam(2, edSlWeapon->Text.ToIntDef(1));
      mItem->SetParam(4, edSlDist->Text.ToIntDef(3));
      mItem->SetParam(3, cbSlAggresivity->ItemIndex);
      mItem->SetParam(5, ColorToCode(cbSlColor->Selected));
      mItem->SetParam(6, checkSlFriendly->Checked);
      break;

    case ROBOT_ITEM              :
      mItem->SetParam(0, cbRShStance->ItemIndex);
      mItem->SetParam(1, edRShHealth->Text.ToIntDef(3));
      mItem->SetParam(2, edRShWeapon->Text.ToIntDef(1));
      mItem->SetParam(4, edRShRec->Text.ToIntDef(100));
      mItem->SetParam(3, edRShDist->Text.ToIntDef(3));
      break;

    case TRIGGER_ENDLEVEL        :
      mItem->SetParam(0, edTriggerLevel->Text.ToIntDef(1));
      break;

    case TRIGGER_SAVEGAME        :
      //PageControl1->Pages[13]->TabVisible = true;
      break;

    case TRIGGER_TEXT            :
      mItem->SetText(edTriggerText->Text.c_str());
      break;

    case BUTTON_ITEM             :
      mItem->SetParam(0, cbButtonElevator->Text.ToIntDef(1));
      break;

    case ELEVATOR_ITEM           :
      mItem->SetParam(0, edElevatorId->Text.ToIntDef(1));
      mItem->SetParam(1, cbElevatorDir->ItemIndex);
      mItem->SetParam(2, edElevatorDist->Text.ToIntDef(3));
      break;

    case AUTOMATIC_ELEVATOR_ITEM :
      mItem->SetParam(0, cbAElevatorDir->ItemIndex);
      mItem->SetParam(1, edAElevatorDist1->Text.ToIntDef(0));
      mItem->SetParam(2, edAElevatorDist2->Text.ToIntDef(0));
      mItem->SetParam(3, edAElevatorWait->Text.ToIntDef(0));
      mItem->SetParam(4, edAElevatorSpeed->Text.ToIntDef(1));
      break;

    case PROP_ITEM               :
      mItem->SetParam(0, edPFile->Text.ToIntDef(1));
      mItem->SetParam(1, edPTime->Text.ToIntDef(3));
      mItem->SetParam(2, (int)cbPAutoTerminate->Checked);
      break;
  }
}
//---------------------------------------------------------------------------

void __fastcall TFormItemEdit::Button1Click(TObject *Sender)
{
  ScreenToItem();
}
//---------------------------------------------------------------------------

TColor TFormItemEdit::CodeToColor(int _code)
{
  switch(_code)
  {
    case 0:
      return (TColor)0x00FF00;

    case 1:
      return (TColor)0x0000FF;
  }

  return (TColor)0;
}
//---------------------------------------------------------------------------

char TFormItemEdit::ColorToCode(TColor _color)
{
  switch(_color)
  {
    case 0x0000FF:
      return 1;

    case 0x00FF00:
      return 0;
  }

  return 255;
}
//---------------------------------------------------------------------------



void __fastcall TFormItemEdit::Button2Click(TObject *Sender)
{
  mItem->SetX(mBackupX);
  mItem->SetY(mBackupY);

  Main->paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormItemEdit::UpDownChangingEx(TObject *Sender,
      bool &AllowChange, short NewValue, TUpDownDirection Direction)
{
  AllowChange = true;

  switch(Direction)
  {
    case updUp:
      mItem->SetY(mItem->GetY() - UpDown->Increment);
      break;

    case updDown:
      mItem->SetY(mItem->GetY() + UpDown->Increment);
      break;
  }

  Main->paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormItemEdit::LeftRightChangingEx(TObject *Sender,
      bool &AllowChange, short NewValue, TUpDownDirection Direction)
{
  AllowChange = true;

  switch(Direction)
  {
    case updUp:
      mItem->SetX(mItem->GetX() - LeftRight->Increment);
      break;

    case updDown:
      mItem->SetX(mItem->GetX() + LeftRight->Increment);
      break;
  }


  Main->paintLevel->OnPaint(this);
}
//---------------------------------------------------------------------------

