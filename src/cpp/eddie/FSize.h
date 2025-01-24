//---------------------------------------------------------------------------

#ifndef FSizeH
#define FSizeH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
//---------------------------------------------------------------------------
class TFormSize : public TForm
{
__published:	// IDE-managed Components
        TEdit *edWidth;
        TLabel *Label1;
        TLabel *Label2;
        TEdit *edHeight;
        TButton *bOk;
        TButton *bCancel;
        void __fastcall edWidthExit(TObject *Sender);
        void __fastcall edHeightExit(TObject *Sender);
        void __fastcall bOkClick(TObject *Sender);
private:	// User declarations
  int mWidth, mHeight;
public:		// User declarations
  __fastcall TFormSize(TComponent* Owner);
  int GetWidth();
  int GetHeight();
};
//---------------------------------------------------------------------------
extern PACKAGE TFormSize *FormSize;
//---------------------------------------------------------------------------
#endif
