//---------------------------------------------------------------------------


#pragma hdrstop

#include "MementoLevel.h"
#include "Level.h"

//---------------------------------------------------------------------------

CMementoLevel::CMementoLevel()
{
  mState = 0;
}
//---------------------------------------------------------------------------

CMementoLevel::~CMementoLevel()
{
  delete mState;
  mState = 0;
}
//---------------------------------------------------------------------------

const CLevel* CMementoLevel::GetState()
{
  return mState;
}
//---------------------------------------------------------------------------

void    CMementoLevel::SetState(CLevel* _v)
{
  delete mState;
  mState = new CLevel(*_v);
}
//---------------------------------------------------------------------------


#pragma package(smart_init)
