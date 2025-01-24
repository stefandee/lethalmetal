//---------------------------------------------------------------------------

#ifndef FDropH
#define FDropH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormDrop : public TForm
{
__published:	// IDE-managed Components
        TRadioGroup *rgDropX;
        TRadioGroup *rgDropY;
private:	// User declarations
public:		// User declarations
        __fastcall TFormDrop(TComponent* Owner);

        int GetX(int _dropX, int _width);
        int GetY(int _dropY, int _height);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormDrop *FormDrop;
//---------------------------------------------------------------------------
#endif
