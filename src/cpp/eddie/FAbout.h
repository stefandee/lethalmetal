//---------------------------------------------------------------------------

#ifndef FAboutH
#define FAboutH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormAbout : public TForm
{
__published:	// IDE-managed Components
        TLabel *Label1;
        TButton *Button1;
        TLabel *Label2;
        TLabel *Label3;
        TBevel *Bevel1;
        TLabel *Label4;
        TLabel *labelMemPhys;
        TLabel *Label6;
        TLabel *labelMemFree;
        TLabel *Label5;
        TLabel *Label7;
        void __fastcall FormShow(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TFormAbout(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormAbout *FormAbout;
//---------------------------------------------------------------------------
#endif
