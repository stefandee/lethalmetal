//---------------------------------------------------------------------------

#ifndef FItemEditH
#define FItemEditH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------

#include "Level.h"

class TFormItemEdit : public TForm
{
__published:	// IDE-managed Components
        TGroupBox *GroupBox1;
        TPageControl *PageControl1;
        TTabSheet *TabSheet1;
        TLabel *Label1;
        TEdit *edHealth;
        TTabSheet *TabSheet2;
        TLabel *Label4;
        TLabel *Label5;
        TLabel *Label7;
        TLabel *Label17;
        TLabel *Label18;
        TEdit *edPHealth;
        TEdit *edPMoney;
        TEdit *edPWeapon;
        TEdit *edPRec;
        TComboBox *cbPStance;
        TTabSheet *TabSheet3;
        TLabel *Label9;
        TLabel *Label10;
        TLabel *Label11;
        TLabel *Label19;
        TLabel *Label22;
        TLabel *Label43;
        TEdit *edSlHealth;
        TEdit *edSlWeapon;
        TComboBox *cbSlAggresivity;
        TComboBox *cbSlStance;
        TEdit *edSlDist;
        TCheckBox *checkSlFriendly;
        TColorBox *cbSlColor;
        TTabSheet *TabSheet4;
        TLabel *Label12;
        TLabel *Label13;
        TLabel *Label14;
        TLabel *Label15;
        TLabel *Label16;
        TLabel *Label20;
        TLabel *Label23;
        TLabel *Label45;
        TEdit *edShHealth;
        TEdit *edShWeapon;
        TComboBox *cbShAggresivity;
        TEdit *edShRec;
        TEdit *edShJoy;
        TComboBox *cbShStance;
        TEdit *edShDist;
        TEdit *edShBulletsDrop;
        TTabSheet *TabSheet5;
        TLabel *Label21;
        TEdit *edTriggerLevel;
        TTabSheet *TabSheet6;
        TLabel *Label25;
        TLabel *Label26;
        TLabel *Label27;
        TLabel *Label28;
        TLabel *Label32;
        TEdit *edElevatorId;
        TComboBox *cbElevatorDir;
        TEdit *edElevatorDist;
        TTabSheet *TabSheet7;
        TLabel *Label36;
        TLabel *Label37;
        TLabel *Label38;
        TLabel *Label39;
        TLabel *Label40;
        TComboBox *cbRShStance;
        TEdit *edRShHealth;
        TEdit *edRShWeapon;
        TEdit *edRShDist;
        TEdit *edRShRec;
        TTabSheet *TabSheet8;
        TLabel *Label41;
        TLabel *Label42;
        TEdit *edPFile;
        TEdit *edPTime;
        TCheckBox *cbPAutoTerminate;
        TGroupBox *GroupBox2;
        TButton *Button1;
        TButton *Button2;
        TUpDown *LeftRight;
        TUpDown *UpDown;
        TTabSheet *TabSheet9;
        TEdit *edTriggerText;
        TLabel *Label8;
        TTabSheet *TabSheet10;
        TLabel *Label24;
        TComboBox *cbButtonElevator;
        TTabSheet *TabSheet11;
        TLabel *Label29;
        TLabel *Label30;
        TComboBox *cbAElevatorDir;
        TLabel *Label31;
        TEdit *edAElevatorDist1;
        TLabel *Label33;
        TEdit *edAElevatorDist2;
        TLabel *Label34;
        TEdit *edAElevatorWait;
        TLabel *Label35;
        TEdit *edAElevatorSpeed;
        TTabSheet *TabSheet12;
        TLabel *Label3;
        TEdit *edMine;
        TTabSheet *TabSheet13;
        TLabel *Label44;
        TEdit *edAmmo;
        TTabSheet *TabSheet14;
        TLabel *Label2;
        TEdit *edWeapon;
        TTabSheet *TabSheet15;
        TLabel *Label6;
        TEdit *edMoney;
        void __fastcall FormShow(TObject *Sender);
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall Button2Click(TObject *Sender);
        void __fastcall UpDownChangingEx(TObject *Sender,
          bool &AllowChange, short NewValue, TUpDownDirection Direction);
        void __fastcall LeftRightChangingEx(TObject *Sender,
          bool &AllowChange, short NewValue, TUpDownDirection Direction);
private:	// User declarations
        CItem* mItem;
        TColor CodeToColor(int _code);
        char ColorToCode(TColor _color);

        void ItemToScreen();
        void ScreenToItem();

        int mBackupX, mBackupY;

public:		// User declarations
        __fastcall TFormItemEdit(TComponent* Owner);

        void SetItem(CItem*);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormItemEdit *FormItemEdit;
//---------------------------------------------------------------------------
#endif
