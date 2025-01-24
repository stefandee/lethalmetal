//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "FParams.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormParams *FormParams;
//---------------------------------------------------------------------------
__fastcall TFormParams::TFormParams(TComponent* Owner)
        : TForm(Owner)
{
  mItemRepository = new CItemRepository();

  mItemRepository->LoadFromFile("data\\itemrepository.txt");

  // fill the controls, according to what was read from file
  RepositoryToScreen();
}
//---------------------------------------------------------------------------

CItemRepository* TFormParams::GetItemRepository()
{
  return mItemRepository;
}
//---------------------------------------------------------------------------

void TFormParams::RepositoryToScreen()
{
  cbShStance->ItemIndex      = mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(0);
  edShHealth->Text           = AnsiString(mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(1));
  edShWeapon->Text           = AnsiString(mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(2));
  edShRec->Text              = AnsiString(mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(3));
  edShJoy->Text              = AnsiString(mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(4));
  cbShAggresivity->ItemIndex = mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(5);
  edShDist->Text             = AnsiString(mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(6));
  edShBulletsDrop->Text      = AnsiString(mItemRepository->GetParams(SHOOTER_ITEM)->GetParams(7));

  cbSlStance->ItemIndex      = mItemRepository->GetParams(SLASHER_ITEM)->GetParams(0);
  edSlHealth->Text           = AnsiString(mItemRepository->GetParams(SLASHER_ITEM)->GetParams(1));
  edSlWeapon->Text           = AnsiString(mItemRepository->GetParams(SLASHER_ITEM)->GetParams(2));
  edSlDist->Text             = AnsiString(mItemRepository->GetParams(SLASHER_ITEM)->GetParams(4));
  cbSlAggresivity->ItemIndex = mItemRepository->GetParams(SLASHER_ITEM)->GetParams(3);
  cbSlColor->Selected        = CodeToColor(mItemRepository->GetParams(SLASHER_ITEM)->GetParams(5));
  checkSlFriendly->Checked   = mItemRepository->GetParams(SLASHER_ITEM)->GetParams(6);

  edTriggerText->Text = mItemRepository->GetParams(TRIGGER_TEXT)->GetText().c_str();
  edTriggerLevel->Text = mItemRepository->GetParams(TRIGGER_ENDLEVEL)->GetParams(0);

  cbPStance->ItemIndex = mItemRepository->GetParams(PLAYER_ITEM)->GetParams(0);
  edPHealth->Text      = AnsiString(mItemRepository->GetParams(PLAYER_ITEM)->GetParams(1));
  edPWeapon->Text      = AnsiString(mItemRepository->GetParams(PLAYER_ITEM)->GetParams(2));
  edPRec->Text         = AnsiString(mItemRepository->GetParams(PLAYER_ITEM)->GetParams(3));
  edPMoney->Text       = AnsiString(mItemRepository->GetParams(PLAYER_ITEM)->GetParams(4));

  edHealth->Text = AnsiString(mItemRepository->GetParams(HEALTH_ITEM)->GetParams(0));
  edWeapon->Text = AnsiString(mItemRepository->GetParams(WEAPON_ITEM)->GetParams(0));
  edMine->Text   = AnsiString(mItemRepository->GetParams(MINE_ITEM)->GetParams(0));
  edMoney->Text  = AnsiString(mItemRepository->GetParams(MONEY_ITEM)->GetParams(0));
  edAmmo->Text = AnsiString(mItemRepository->GetParams(AMMO_ITEM)->GetParams(0));

  cbButtonElevator->Text = AnsiString(mItemRepository->GetParams(BUTTON_ITEM)->GetParams(0));

  edElevatorId->Text        = AnsiString(mItemRepository->GetParams(ELEVATOR_ITEM)->GetParams(0));
  cbElevatorDir->ItemIndex  = mItemRepository->GetParams(ELEVATOR_ITEM)->GetParams(1);
  edElevatorDist->Text      = AnsiString(mItemRepository->GetParams(ELEVATOR_ITEM)->GetParams(2));

  cbAElevatorDir->ItemIndex = mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(0);
  edAElevatorDist1->Text    = AnsiString(mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(1));
  edAElevatorDist2->Text    = AnsiString(mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(2));
  edAElevatorWait->Text     = AnsiString(mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(3));
  edAElevatorSpeed->Text    = AnsiString(mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->GetParams(4));
  
  cbRShStance->ItemIndex      = mItemRepository->GetParams(ROBOT_ITEM)->GetParams(0);
  edRShHealth->Text           = AnsiString(mItemRepository->GetParams(ROBOT_ITEM)->GetParams(1));
  edRShWeapon->Text           = AnsiString(mItemRepository->GetParams(ROBOT_ITEM)->GetParams(2));
  edRShRec->Text              = AnsiString(mItemRepository->GetParams(ROBOT_ITEM)->GetParams(4));
  edRShDist->Text             = AnsiString(mItemRepository->GetParams(ROBOT_ITEM)->GetParams(3));

  edPFile->Text               = AnsiString(mItemRepository->GetParams(PROP_ITEM)->GetParams(0));
  edPTime->Text               = AnsiString(mItemRepository->GetParams(PROP_ITEM)->GetParams(1));
  cbPAutoTerminate->Checked   = mItemRepository->GetParams(PROP_ITEM)->GetParams(2);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edHealthExit(TObject *Sender)
{
  // convert value to int
  int lV = 1;

  try
  {
    lV = edHealth->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edHealth->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(HEALTH_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edWeaponExit(TObject *Sender)
{
  // convert value to int
  int lV = 1;

  try
  {
    lV = edWeapon->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edWeapon->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(WEAPON_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edMineExit(TObject *Sender)
{
  // convert value to int
  int lV = 5;

  try
  {
    lV = edMine->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 5;
    edMine->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(MINE_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edMoneyExit(TObject *Sender)
{
  // convert value to int
  int lV = 5;

  try
  {
    lV = edMoney->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 5;
    edMoney->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(MONEY_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edTriggerTextChange(TObject *Sender)
{
  mItemRepository->GetParams(TRIGGER_TEXT)->SetText(edTriggerText->Text.c_str());
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbPStanceExit(TObject *Sender)
{
  mItemRepository->GetParams(PLAYER_ITEM)->SetParams(0, cbPStance->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edPHealthExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edPHealth->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edPHealth->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(PLAYER_ITEM)->SetParams(1, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edPWeaponExit(TObject *Sender)
{
  // convert value to int
  int lV = 1;

  try
  {
    lV = edPWeapon->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 1;
    edPWeapon->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(PLAYER_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edPRecExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edPRec->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edPRec->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(PLAYER_ITEM)->SetParams(3, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edPMoneyExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edPMoney->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edPMoney->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(PLAYER_ITEM)->SetParams(4, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbSlStanceExit(TObject *Sender)
{
  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(0, cbSlStance->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edSlHealthExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edSlHealth->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edSlHealth->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(1, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edSlWeaponExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edSlWeapon->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edSlWeapon->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edSlDistExit(TObject *Sender)
{
  // convert value to int
  int lV = 2;

  try
  {
    lV = edSlDist->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 2;
    edSlDist->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(4, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbSlAggresivityExit(TObject *Sender)
{
  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(3, cbSlAggresivity->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbShStanceExit(TObject *Sender)
{
  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(0, cbShStance->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edShHealthExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edShHealth->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edShHealth->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(1, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edShWeaponExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edShWeapon->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edShWeapon->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edShDistExit(TObject *Sender)
{
  // convert value to int
  int lV = 2;

  try
  {
    lV = edShDist->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 2;
    edShDist->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(6, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edShRecExit(TObject *Sender)
{
  // convert value to int
  int lV = 100;

  try
  {
    lV = edShRec->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 100;
    edShRec->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(3, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edShJoyExit(TObject *Sender)
{
  // convert value to int
  int lV = 1000;

  try
  {
    lV = edShJoy->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 1000;
    edShJoy->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(4, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbShAggresivityExit(TObject *Sender)
{
  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(5, cbShAggresivity->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::FormDeactivate(TObject *Sender)
{
  mItemRepository->SaveToFile("data\\itemrepository.txt");
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edTriggerLevelChange(TObject *Sender)
{
  // convert value to int
  int lV = 1;

  try
  {
    lV = edTriggerLevel->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 1;
    edTriggerLevel->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(TRIGGER_ENDLEVEL)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbButtonElevatorExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = cbButtonElevator->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    cbButtonElevator->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(BUTTON_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edElevatorIdExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edElevatorId->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edElevatorId->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(ELEVATOR_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbElevatorDirExit(TObject *Sender)
{
  mItemRepository->GetParams(ELEVATOR_ITEM)->SetParams(1, cbElevatorDir->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edElevatorDistExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edElevatorDist->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edElevatorDist->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(ELEVATOR_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbAElevatorDirExit(TObject *Sender)
{
  mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->SetParams(0, cbAElevatorDir->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edAElevatorDist1Exit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edAElevatorDist1->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edAElevatorDist1->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->SetParams(1, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edAElevatorDist2Exit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edAElevatorDist2->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edAElevatorDist2->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edAElevatorWaitExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edAElevatorWait->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edAElevatorWait->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->SetParams(3, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edAElevatorSpeedExit(TObject *Sender)
{
  // convert value to int
  int lV = 0;

  try
  {
    lV = edAElevatorSpeed->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 0;
    edAElevatorSpeed->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(AUTOMATIC_ELEVATOR_ITEM)->SetParams(4, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbRShStanceExit(TObject *Sender)
{
  mItemRepository->GetParams(ROBOT_ITEM)->SetParams(0, cbRShStance->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edRShHealthExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edRShHealth->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edRShHealth->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(ROBOT_ITEM)->SetParams(1, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edRShWeaponExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edRShWeapon->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edRShWeapon->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(ROBOT_ITEM)->SetParams(2, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edRShDistExit(TObject *Sender)
{
  // convert value to int
  int lV = 2;

  try
  {
    lV = edRShDist->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 2;
    edRShDist->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(ROBOT_ITEM)->SetParams(3, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edRShRecExit(TObject *Sender)
{
  // convert value to int
  int lV = 100;

  try
  {
    lV = edRShRec->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 100;
    edRShRec->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(ROBOT_ITEM)->SetParams(4, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edPFileExit(TObject *Sender)
{
  // convert value to int
  int lV = 1;

  try
  {
    lV = edPFile->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 1;
    edPFile->Text = AnsiString(lV);
  }

  if(lV > 2 || lV < 1)
  {
    lV = 1;
    edPFile->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(PROP_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edPTimeExit(TObject *Sender)
{
  // convert value to int
  int lV = 3;

  try
  {
    lV = edPTime->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    lV = 3;
    edPTime->Text = AnsiString(lV);
  }

  mItemRepository->GetParams(PROP_ITEM)->SetParams(1, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbPAutoTerminateExit(TObject *Sender)
{
  mItemRepository->GetParams(PROP_ITEM)->SetParams(2, (int)(cbPAutoTerminate->Checked));
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::cbSlColorExit(TObject *Sender)
{
  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(5, ColorToCode(cbSlColor->Selected));
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::checkSlFriendlyExit(TObject *Sender)
{
  mItemRepository->GetParams(SLASHER_ITEM)->SetParams(6, (int)(checkSlFriendly->Checked));
}
//---------------------------------------------------------------------------

char TFormParams::ColorToCode(TColor _color)
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

TColor TFormParams::CodeToColor(int _code)
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

void __fastcall TFormParams::edAmmoExit(TObject *Sender)
{
  // convert value to int
  int lV = edAmmo->Text.ToIntDef(1);

  edAmmo->Text = AnsiString(lV);

  mItemRepository->GetParams(AMMO_ITEM)->SetParams(0, lV);
}
//---------------------------------------------------------------------------

void __fastcall TFormParams::edShBulletsDropExit(TObject *Sender)
{
  // convert value to int
  int lV = edShBulletsDrop->Text.ToIntDef(1);

  if (lV < 0)
  {
    lV = 0;
  }

  edShBulletsDrop->Text = AnsiString(lV);

  mItemRepository->GetParams(SHOOTER_ITEM)->SetParams(7, lV);
}
//---------------------------------------------------------------------------

void TFormParams::SetActivePage(int _itemIndex)
{
  switch(_itemIndex)
  {
    case PLAYER_ITEM             :
      PageControl1->ActivePageIndex = 1;
      break;

    case HEALTH_ITEM             :
    case WEAPON_ITEM             :
    case MONEY_ITEM              :
    case MINE_ITEM               :
    case AMMO_ITEM               :
      PageControl1->ActivePageIndex = 0;
      break;

    case SHOOTER_ITEM            :
      PageControl1->ActivePageIndex = 3;
      break;

    case SLASHER_ITEM            :
      PageControl1->ActivePageIndex = 2;
      break;

    case TRIGGER_ENDLEVEL        :
    case TRIGGER_SAVEGAME        :
    case TRIGGER_TEXT            :
      PageControl1->ActivePageIndex = 4;
      break;

    case BUTTON_ITEM             :
    case ELEVATOR_ITEM           :
    case AUTOMATIC_ELEVATOR_ITEM :
      PageControl1->ActivePageIndex = 5;
      break;

    case ROBOT_ITEM              :
      PageControl1->ActivePageIndex = 6;
      break;

    case PROP_ITEM               :
      PageControl1->ActivePageIndex = 7;
      break;
  }
}
//---------------------------------------------------------------------------

