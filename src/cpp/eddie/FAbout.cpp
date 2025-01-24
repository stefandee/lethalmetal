//---------------------------------------------------------------------------

#include <vcl.h>
#include <stdio.h>
#pragma hdrstop

#include "FAbout.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormAbout *FormAbout;
//---------------------------------------------------------------------------
__fastcall TFormAbout::TFormAbout(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TFormAbout::FormShow(TObject *Sender)
{
  TMemoryStatus MS;
  MS.dwLength = sizeof(MS);
  GlobalMemoryStatus(&MS);
  labelMemPhys->Caption = FormatFloat("#,###' KB'", MS.dwTotalPhys / 1024);
  CHAR lpMemLoad[5];
  sprintf(lpMemLoad, "%d %%", MS.dwMemoryLoad);
  labelMemFree->Caption = lpMemLoad;
}
//---------------------------------------------------------------------------

