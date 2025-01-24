//---------------------------------------------------------------------------


#pragma hdrstop

#include "VerifyCyberNinjaLevel.h"
#include "Level.h"
#include <sstream>
//---------------------------------------------------------------------------

VerifyCyberNinjaLevel::VerifyCyberNinjaLevel(CLevel* _level)
{
  mLevel = _level;
}
//---------------------------------------------------------------------------

VerifyCyberNinjaLevel::~VerifyCyberNinjaLevel()
{
}
//---------------------------------------------------------------------------

bool VerifyCyberNinjaLevel::Verify()
{
  if (!mLevel)
  {
    AddWarning("Level to verify is null");
    return false;
  }

  CLayer* lLayer = mLevel->GetLayer(0);

  bool lErrors = true;

  for(int x = 0; x < mLevel->GetWidth(); x++)
  {
    for(int y = 0; y < mLevel->GetHeight(); y++)
    {
      std::stringstream lLine;
      lLine.clear();

      if (lLayer->GetBrush(x, y))
      {
        if (lLayer->GetBrush(x, y)->GetTileId() == -1)
        {
          lLine << "Brush at (" << x << "," << y << ") has invalid tile id (-1)";

          AddError(std::string(lLine.str()));
          lErrors = false;
        }
      }
      else
      {
        lLine << "Brush at (" << x << "," << y << ") is not set";

        AddError(std::string(lLine.str()));

        lErrors = false;
      }
    }
  }

  // could also check the number of objects...

  // check to see if we have proper animations objects
  // we should only have the animation2
  for(int i = 0; i < mLevel->GetItemCount(); i++)
  {
    CItem* lItem = mLevel->GetItem(i);

    if (lItem->GetType() == PROP_ITEM)
    {
      if (lItem->GetParam(0) != 2)
      {
        std::stringstream lLine;
        lLine.clear();

        lLine << "Animation item #" << (int)lItem->GetParam(0) << " at " << lItem->GetX() << "," << lItem->GetY() << " is not recognized by the engine. Please use only animation file #2.";

        AddError(lLine.str());
      }
    }
  }

  return lErrors; 
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
