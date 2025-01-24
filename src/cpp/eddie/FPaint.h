//---------------------------------------------------------------------------

#ifndef FPaintH
#define FPaintH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ExtCtrls.hpp>
#include <ComCtrls.hpp>
#include <ToolWin.hpp>
#include <Buttons.hpp>
#include <ActnList.hpp>
//---------------------------------------------------------------------------

enum TPaintTool {PT_PENCIL, PT_BUCKET, PT_ERASER, PT_SELECT}; 

class TFormPaint : public TForm
{
__published:	// IDE-managed Components
        TSpeedButton *sbPencil;
        TSpeedButton *sbBucket;
        TSpeedButton *sbEraser;
        TSpeedButton *sbNone;
        void __fastcall sbPencilClick(TObject *Sender);
        void __fastcall sbBucketClick(TObject *Sender);
        void __fastcall sbEraserClick(TObject *Sender);
        void __fastcall sbNoneClick(TObject *Sender);
private:	// User declarations
        TPaintTool mPaintTool;

public:		// User declarations
        __fastcall TFormPaint(TComponent* Owner);
        TPaintTool GetPaintTool();
};
//---------------------------------------------------------------------------
extern PACKAGE TFormPaint *FormPaint;
//---------------------------------------------------------------------------
#endif
