//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "FPaint.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormPaint *FormPaint;
//---------------------------------------------------------------------------
__fastcall TFormPaint::TFormPaint(TComponent* Owner)
        : TForm(Owner)
{
  mPaintTool = PT_PENCIL;
}
//---------------------------------------------------------------------------

void __fastcall TFormPaint::sbPencilClick(TObject *Sender)
{
  mPaintTool = PT_PENCIL;
}
//---------------------------------------------------------------------------

void __fastcall TFormPaint::sbBucketClick(TObject *Sender)
{
  mPaintTool = PT_BUCKET;
}
//---------------------------------------------------------------------------

void __fastcall TFormPaint::sbEraserClick(TObject *Sender)
{
  mPaintTool = PT_ERASER;
}
//---------------------------------------------------------------------------

TPaintTool TFormPaint::GetPaintTool()
{
  return mPaintTool;
}
//---------------------------------------------------------------------------

void __fastcall TFormPaint::sbNoneClick(TObject *Sender)
{
  mPaintTool = PT_SELECT;
}
//---------------------------------------------------------------------------

