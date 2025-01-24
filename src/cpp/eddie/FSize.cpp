//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "FSize.h"
#include "FMain.h"
#include "EditorDef.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormSize *FormSize;
//---------------------------------------------------------------------------

__fastcall TFormSize::TFormSize(TComponent* Owner)
        : TForm(Owner)
{
  mWidth  = 25;
  mHeight = 25;
}
//---------------------------------------------------------------------------

void __fastcall TFormSize::edWidthExit(TObject *Sender)
{
  // convert value to int
  mWidth = DEFAULT_MAP_WIDTH;
  
  try
  {
    mWidth = edWidth->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    mWidth = DEFAULT_MAP_WIDTH;
    edWidth->Text = AnsiString(DEFAULT_MAP_WIDTH);
  }

  // verificari de limita
  if (mWidth < 10)
  {
    mWidth = DEFAULT_MAP_WIDTH;
  }

  if (mWidth > 255)
  {
    mWidth = 255;
  }

  edWidth->Text = AnsiString(mWidth);
}
//---------------------------------------------------------------------------

void __fastcall TFormSize::edHeightExit(TObject *Sender)
{
  // convert value to int
  mHeight = DEFAULT_MAP_HEIGHT;

  try
  {
    mHeight = edHeight->Text.ToInt();
  }
  catch(const EConvertError &e)
  {
    mHeight = DEFAULT_MAP_HEIGHT;
    edHeight->Text = AnsiString(DEFAULT_MAP_HEIGHT);
  }

  // verificari de limita
  if (mHeight < 10)
  {
    mHeight = DEFAULT_MAP_HEIGHT;
  }

  if (mHeight > 255)
  {
    mHeight = 255;
  }

  edHeight->Text = AnsiString(mHeight);
}
//---------------------------------------------------------------------------

void __fastcall TFormSize::bOkClick(TObject *Sender)
{
  //Main->SetMapSize(mWidth, mHeight);
}
//---------------------------------------------------------------------------

int TFormSize::GetWidth()
{
  return mWidth;
}
//---------------------------------------------------------------------------

int TFormSize::GetHeight()
{
  return mHeight;
}
//---------------------------------------------------------------------------


