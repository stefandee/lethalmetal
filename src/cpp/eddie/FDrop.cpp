//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "FDrop.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormDrop *FormDrop;
//---------------------------------------------------------------------------
__fastcall TFormDrop::TFormDrop(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

int TFormDrop::GetX(int _dropX, int _width)
{
  if (rgDropX->ItemIndex == 1)
  {
    return _dropX - _width;
  }

  return _dropX;
}
//---------------------------------------------------------------------------

int TFormDrop::GetY(int _dropY, int _height)
{
  if (rgDropY->ItemIndex == 1)
  {
    return _dropY - _height;
  }

  return _dropY; 
}
//---------------------------------------------------------------------------
