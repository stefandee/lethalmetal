//---------------------------------------------------------------------------

#ifndef FParamsH
#define FParamsH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "Level.h"
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormParams : public TForm
{
__published:	// IDE-managed Components
        TPageControl *PageControl1;
        TTabSheet *TabSheet1;
        TTabSheet *TabSheet2;
        TTabSheet *TabSheet3;
        TTabSheet *TabSheet4;
        TTabSheet *TabSheet5;
        TLabel *Label1;
        TEdit *edHealth;
        TLabel *Label2;
        TEdit *edWeapon;
        TLabel *Label3;
        TEdit *edMine;
        TLabel *Label4;
        TEdit *edPHealth;
        TLabel *Label5;
        TEdit *edPMoney;
        TLabel *Label6;
        TEdit *edMoney;
        TLabel *Label7;
        TEdit *edPWeapon;
        TLabel *Label8;
        TEdit *edTriggerText;
        TLabel *Label9;
        TEdit *edSlHealth;
        TLabel *Label10;
        TEdit *edSlWeapon;
        TLabel *Label11;
        TComboBox *cbSlAggresivity;
        TLabel *Label12;
        TEdit *edShHealth;
        TEdit *edShWeapon;
        TLabel *Label13;
        TLabel *Label14;
        TComboBox *cbShAggresivity;
        TLabel *Label15;
        TEdit *edShRec;
        TLabel *Label16;
        TEdit *edShJoy;
        TLabel *Label17;
        TEdit *edPRec;
        TLabel *Label18;
        TComboBox *cbPStance;
        TLabel *Label19;
        TComboBox *cbSlStance;
        TLabel *Label20;
        TComboBox *cbShStance;
        TEdit *edTriggerLevel;
        TLabel *Label21;
        TEdit *edSlDist;
        TLabel *Label22;
        TEdit *edShDist;
        TLabel *Label23;
        TTabSheet *TabSheet6;
        TLabel *Label24;
        TComboBox *cbButtonElevator;
        TLabel *Label25;
        TEdit *edElevatorId;
        TLabel *Label26;
        TComboBox *cbElevatorDir;
        TLabel *Label27;
        TEdit *edElevatorDist;
        TLabel *Label28;
        TLabel *Label29;
        TLabel *Label30;
        TComboBox *cbAElevatorDir;
        TLabel *Label31;
        TEdit *edAElevatorDist1;
        TLabel *Label32;
        TEdit *edAElevatorDist2;
        TLabel *Label33;
        TEdit *edAElevatorWait;
        TLabel *Label34;
        TLabel *Label35;
        TEdit *edAElevatorSpeed;
        TTabSheet *TabSheet7;
        TLabel *Label36;
        TComboBox *cbRShStance;
        TLabel *Label37;
        TEdit *edRShHealth;
        TEdit *edRShWeapon;
        TLabel *Label38;
        TEdit *edRShDist;
        TLabel *Label39;
        TEdit *edRShRec;
        TLabel *Label40;
        TTabSheet *TabSheet8;
        TLabel *Label41;
        TEdit *edPFile;
        TEdit *edPTime;
        TLabel *Label42;
        TCheckBox *cbPAutoTerminate;
        TLabel *Label43;
        TCheckBox *checkSlFriendly;
        TColorBox *cbSlColor;
        TEdit *edAmmo;
        TLabel *Label44;
        TLabel *Label45;
        TEdit *edShBulletsDrop;
        void __fastcall edHealthExit(TObject *Sender);
        void __fastcall edWeaponExit(TObject *Sender);
        void __fastcall edMineExit(TObject *Sender);
        void __fastcall edMoneyExit(TObject *Sender);
        void __fastcall edTriggerTextChange(TObject *Sender);
        void __fastcall edPHealthExit(TObject *Sender);
        void __fastcall edPWeaponExit(TObject *Sender);
        void __fastcall edPRecExit(TObject *Sender);
        void __fastcall edPMoneyExit(TObject *Sender);
        void __fastcall edSlHealthExit(TObject *Sender);
        void __fastcall edSlWeaponExit(TObject *Sender);
        void __fastcall cbSlAggresivityExit(TObject *Sender);
        void __fastcall edShHealthExit(TObject *Sender);
        void __fastcall edShWeaponExit(TObject *Sender);
        void __fastcall edShRecExit(TObject *Sender);
        void __fastcall edShJoyExit(TObject *Sender);
        void __fastcall cbShAggresivityExit(TObject *Sender);
        void __fastcall FormDeactivate(TObject *Sender);
        void __fastcall cbSlStanceExit(TObject *Sender);
        void __fastcall cbPStanceExit(TObject *Sender);
        void __fastcall cbShStanceExit(TObject *Sender);
        void __fastcall edTriggerLevelChange(TObject *Sender);
        void __fastcall edSlDistExit(TObject *Sender);
        void __fastcall edShDistExit(TObject *Sender);
        void __fastcall cbButtonElevatorExit(TObject *Sender);
        void __fastcall edElevatorIdExit(TObject *Sender);
        void __fastcall cbElevatorDirExit(TObject *Sender);
        void __fastcall edElevatorDistExit(TObject *Sender);
        void __fastcall cbAElevatorDirExit(TObject *Sender);
        void __fastcall edAElevatorDist1Exit(TObject *Sender);
        void __fastcall edAElevatorDist2Exit(TObject *Sender);
        void __fastcall edAElevatorWaitExit(TObject *Sender);
        void __fastcall edAElevatorSpeedExit(TObject *Sender);
        void __fastcall cbRShStanceExit(TObject *Sender);
        void __fastcall edRShHealthExit(TObject *Sender);
        void __fastcall edRShWeaponExit(TObject *Sender);
        void __fastcall edRShDistExit(TObject *Sender);
        void __fastcall edRShRecExit(TObject *Sender);
        void __fastcall edPFileExit(TObject *Sender);
        void __fastcall edPTimeExit(TObject *Sender);
        void __fastcall cbPAutoTerminateExit(TObject *Sender);
        void __fastcall cbSlColorExit(TObject *Sender);
        void __fastcall checkSlFriendlyExit(TObject *Sender);
        void __fastcall edAmmoExit(TObject *Sender);
        void __fastcall edShBulletsDropExit(TObject *Sender);
private:	// User declarations
  CItemRepository* mItemRepository;
  void RepositoryToScreen();
  TColor CodeToColor(int _code);
  char ColorToCode(TColor _color);

public:		// User declarations
        CItemRepository* GetItemRepository();
        __fastcall TFormParams(TComponent* Owner);

        void SetActivePage(int _itemIndex);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormParams *FormParams;
//---------------------------------------------------------------------------
#endif
