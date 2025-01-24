//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "FLog.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TFormLog *FormLog;
//---------------------------------------------------------------------------

__fastcall TFormLog::TFormLog(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

void TFormLog::Add(std::string _line)
{
  memoLog->Lines->Add(_line.c_str());
}
//---------------------------------------------------------------------------

void TFormLog::Add(std::vector<std::string> _list)
{
  for(unsigned int i = 0; i < _list.size(); i++)
  {
    memoLog->Lines->Add(_list[i].c_str());
  }
}
//---------------------------------------------------------------------------

void TFormLog::Clear()
{
  memoLog->Lines->Clear();
}
//---------------------------------------------------------------------------

void __fastcall TFormLog::Clear1Click(TObject *Sender)
{
  Clear();
}
//---------------------------------------------------------------------------
