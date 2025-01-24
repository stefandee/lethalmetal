//---------------------------------------------------------------------------


#pragma hdrstop

#include "Clipboard.h"
#include "Level.h"

//---------------------------------------------------------------------------

Clipboard::Clipboard()
{
  mSelection = 0;
}
//---------------------------------------------------------------------------

Clipboard::~Clipboard()
{
  Clear();
}
//---------------------------------------------------------------------------

void Clipboard::CopySelection(CLevel* _level, int _xs, int _ys, int _xe, int _ye)
{
  if (!_level)
  {
    return;
  }

  delete mSelection;

  //
  // crop the coordinates to level boundaries
  //
  _xs = std::min(_level->GetWidth(),  _xs);
  _ys = std::min(_level->GetHeight(), _ys);

  _xe = std::min(_level->GetWidth(),  _xe);
  _ye = std::min(_level->GetHeight(), _ye);

  _xs = std::max(0, _xs);
  _ys = std::max(0, _ys);

  _xe = std::max(0, _xe);
  _ye = std::max(0, _ye);

  // create the selection

  mSelection = new CLevel(_xe - _xs + 1, _ye - _ys + 1);

  CLayer* lLayer = new CLayer();

  mSelection->AddLayer(lLayer);
  
  for(int x = _xs; x < _xe; x++)
  {
    for(int y = _ys; y < _ye; y++)
    {
      CBrush* lLevelBrush = _level->GetLayer(0)->GetBrush(x, y);

      if (lLevelBrush)
      {
        CBrush* lBrush = new CBrush();

        lBrush->SetW(16);
        lBrush->SetH(16);

        lBrush->SetTileId(lLevelBrush->GetTileId());

        mSelection->GetLayer(0)->SetBrush(x - _xs, y - _ys, lBrush);
      }
      else
      {
        mSelection->GetLayer(0)->SetBrush(x - _xs, y - _ys, 0);
      }

    }
  }

  mHasSelection = true;
}
//---------------------------------------------------------------------------

void Clipboard::PasteSelection(CLevel* _level, int _x, int _y)
{
  if (!_level)
  {
    return;
  }

  if (!mSelection)
  {
    return;
  }
  
  int lW = std::min(_level->GetWidth(), _x + mSelection->GetWidth());
  int lH = std::min(_level->GetHeight(), _y + mSelection->GetHeight());

  for(int x = _x; x < lW; x++)
  {
    for(int y = _y; y < lH; y++)
    {
      //CBrush* lLevelBrush     = _level->GetLayer(0)->GetBrush(x, y);
      CBrush* lSelectionBrush = mSelection->GetLayer(0)->GetBrush(x - _x, y - _y);

      if (lSelectionBrush)
      {
        // replace the brush on the level
        CBrush* lNewBrush = new CBrush(*lSelectionBrush);

        lNewBrush->SetX(x);
        lNewBrush->SetY(y);
        lNewBrush->SetW(16);
        lNewBrush->SetH(16);

        _level->GetLayer(0)->SetBrush(x, y, lNewBrush);
      }
    }
  }
}
//---------------------------------------------------------------------------

bool Clipboard::HasSelection()
{
  return mHasSelection;
}
//---------------------------------------------------------------------------

void Clipboard::Clear()
{
  mHasSelection = false;
  delete mSelection;
  mSelection = 0;
}
//---------------------------------------------------------------------------

//const CLevel* Clipboard::GetSelection()
//{
//  return mSelection;
//}
//---------------------------------------------------------------------------

bool Clipboard::Load(std::string _fileName)
{
  delete mSelection;

  mSelection = new CLevel(50, 50);
  CLayer* lLayer = new CLayer();

  mSelection->AddLayer(lLayer);

  mHasSelection = mSelection->Load(_fileName);

  return mHasSelection;
}
//---------------------------------------------------------------------------

bool Clipboard::Save(std::string _fileName)
{
  if (!mHasSelection)
  {
    return false;
  }

  return mSelection->Save(_fileName);
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
