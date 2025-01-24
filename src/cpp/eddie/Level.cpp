//---------------------------------------------------------------------------

#include <stdio.h>
#pragma hdrstop

#include "Level.h"
#include <fstream>
#include <ios>
#include <Forms.hpp>
#include "MementoLevel.h"

//---------------------------------------------------------------------------

int CTile::GetCollision(TCollisionFlag _id)
{
  switch(_id)
  {
    case CLS_UP:
      return (mCollision & 1);

    case CLS_DOWN:
      return (mCollision & 2) >> 1;

    case CLS_LEFT:
      return (mCollision & 4) >> 2;

    case CLS_RIGHT:
      return (mCollision & 8) >> 3;
  }

  return 0;
}
//---------------------------------------------------------------------------

void CTile::SetCollision(TCollisionFlag _id, bool _v)
{
  unsigned __int8 lMask, lFlagMask;

  switch(_id)
  {
    case CLS_UP:
      lMask     = mCollision & (~1);
      lFlagMask = (int)_v;
      break;

    case CLS_DOWN:
      lMask     = mCollision & (~2);
      lFlagMask = ((int)_v) << 1;
      break;

    case CLS_LEFT:
      lMask     = mCollision & (~4);
      lFlagMask = ((int)_v) << 2;
      break;

    case CLS_RIGHT:
      lMask     = mCollision & (~8);
      lFlagMask = ((int)_v) << 3;
      break;
  }

  mCollision = lMask | lFlagMask;
}
//---------------------------------------------------------------------------

bool CTile::IsSignificant()
{
  return (GetIsLadder() || GetIsHeadLadder() ||
          GetCollision(CLS_UP) || GetCollision(CLS_DOWN) ||
          GetCollision(CLS_LEFT) || GetCollision(CLS_RIGHT));
}
//---------------------------------------------------------------------------

unsigned char CTile::GetPackedData()
{
    unsigned char lData = 0;

    if (GetIsLadder())
    {
      lData |= 16;
    }

    if (GetIsHeadLadder())
    {
      lData |= 32;
    }

    if (GetCollision(CLS_UP))
    {
      lData |= 1;
    }

    if (GetCollision(CLS_DOWN))
    {
      lData |= 2;
    }

    if (GetCollision(CLS_LEFT))
    {
      lData |= 4;
    }

    if (GetCollision(CLS_RIGHT))
    {
      lData |= 8;
    }

    return lData;
}
//---------------------------------------------------------------------------

CBrush::CBrush()
{
  mX = 0;
  mY = 0;
  mH = LEVEL_TILE_HEIGHT;
  mW = LEVEL_TILE_HEIGHT;
  mTileId = 0;
}
//---------------------------------------------------------------------------

void CBrush::SetX(int _v)
{
  /*
  if (_v < 0)
  {
    return;
  }
  */

  mX = _v;
}
//---------------------------------------------------------------------------

void CBrush::SetY(int _v)
{
  /*
  if (_v < 0)
  {
    return;
  }
  */

  mY = _v;
}
//---------------------------------------------------------------------------

void CBrush::SetW(int _v)
{
  /*
  if (_v < 0)
  {
    return;
  }
  */

  mW = _v;
}
//---------------------------------------------------------------------------

void CBrush::SetH(int _v)
{
  /*
  if (_v < 0)
  {
    return;
  }
  */

  mH = _v;
}
//---------------------------------------------------------------------------

void CBrush::SetTileId(int _v)
{
  mTileId = _v;
}
//---------------------------------------------------------------------------

CLayer::CLayer()
{
  mName = "New Layer";
  //mBrushes.clear();
  for(int i = 0; i < LEVEL_MAX_WIDTH; i++)
  {
    for(int j = 0; j < LEVEL_MAX_HEIGHT; j++)
    {
      //mBrushes[i][j] = 0;
      mBrushes[i][j] = 0;
    }
  }
}
//---------------------------------------------------------------------------

CLayer::~CLayer()
{
  //mBrushes.clear();
  for(int i = 0; i < LEVEL_MAX_WIDTH; i++)
  {
    for(int j = 0; j < LEVEL_MAX_HEIGHT; j++)
    {
      //delete mBrushes[i][j];
      delete mBrushes[i][j];
    }
  }
}
//---------------------------------------------------------------------------

CLayer::CLayer(const CLayer& rhs)
{
  // assign
  mName = rhs.mName;

  for(int i = 0; i < LEVEL_MAX_WIDTH; i++)
  {
    for(int j = 0; j < LEVEL_MAX_HEIGHT; j++)
    {
      mBrushes[i][j] = 0;

      if (rhs.mBrushes[i][j] != 0)
      {
        mBrushes[i][j] = new CBrush(*(rhs.mBrushes[i][j]));
      }
    }
  }
}
//---------------------------------------------------------------------------

CLayer& CLayer::operator=(const CLayer& rhs)
{
  if (&rhs == this)
  {
    return *this;
  }

  // delete
  for(int i = 0; i < LEVEL_MAX_WIDTH; i++)
  {
    for(int j = 0; j < LEVEL_MAX_HEIGHT; j++)
    {
      //delete mBrushes[i][j];
      delete mBrushes[i][j];
    }
  }

  // assign
  mName = rhs.mName;

  for(int i = 0; i < LEVEL_MAX_WIDTH; i++)
  {
    for(int j = 0; j < LEVEL_MAX_HEIGHT; j++)
    {
      mBrushes[i][j] = new CBrush(*(rhs.mBrushes[i][j]));
    }
  }

  return *this;
}
//---------------------------------------------------------------------------

void CLayer::SetBrush(int _x, int _y, CBrush* _brush)
{


  if (mBrushes[_x][_y] != 0)
  {
    delete mBrushes[_x][_y];
  }

  mBrushes[_x][_y] = _brush;
}
//---------------------------------------------------------------------------

CBrush* CLayer::GetBrush(int _x, int _y)
{
  if (_x >= 0 && _y >= 0)
  {
    return mBrushes[_x][_y];
  }

  return 0;  
}
//---------------------------------------------------------------------------

/*
bool    CLayer::AddBrush(CBrush* _brush)
{
  if (!_brush)
  {
    return false;
  }

  mBrushes.push_back(_brush);

  return true;
}
//---------------------------------------------------------------------------

bool    CLayer::RemoveBrush(int x, int y, int w, int h)
{
  if (mBrushes.empty())
  {
    return false;
  }

  std::vector<CBrush*>::iterator lIt = mBrushes.begin();

  while(lIt != mBrushes.end())
  {
  }

  for(int i = 0; i < mBrushes.size(); i++)
  {
    int lBX = mBrushes[i]->GetX();
    int lBY = mBrushes[i]->GetY();
    int lBW = mBrushes[i]->GetW();
    int lBH = mBrushes[i]->GetH();

    if ((x < lBX && lBX < x + w) &&
       (y < lBY && lBY < y + h))
    {
      // vector
    }
  }
}
//---------------------------------------------------------------------------

bool    CLayer::RemoveAll()
{
  mBrushes.clear();
}
//---------------------------------------------------------------------------

CBrush* CLayer::GetBrush(int _index)
{
  if ((_index < 0) || (_index > mBrushes.size()))
  {
    return 0;
  }

  return mBrushes[_index];
}
//---------------------------------------------------------------------------

int CLayer::GetBrushCount()
{
  return mBrushes.size();
}
//---------------------------------------------------------------------------
*/

CLevel::CLevel(int _width, int _height)
{
  if (_width < 0)
  {
    _width = 0;
  }

  if (_width > LEVEL_MAX_WIDTH)
  {
    _width = LEVEL_MAX_WIDTH;
  }

  if (_height < 0)
  {
    _height = 0;
  }

  if (_height > LEVEL_MAX_HEIGHT)
  {
    _height = LEVEL_MAX_HEIGHT;
  }

  mWidth = _width;
  mHeight = _height;

  mTileSetDat = "";

  mItems.clear();

  CItem* lItem = new CItem();
  lItem->SetType(PLAYER_ITEM);
  lItem->SetX(0);
  lItem->SetY(0);
  mItems.push_back(lItem);


  mLayers.clear();
}
//---------------------------------------------------------------------------

CLevel::~CLevel()
{
  mLayers.clear();
}
//---------------------------------------------------------------------------

CLevel::CLevel(const CLevel& rhs)
{
  mWidth      = rhs.mWidth;
  mHeight     = rhs.mHeight;
  mTileSetDat = rhs.mTileSetDat;

  mItems.clear();
  mLayers.clear();

  for(unsigned int i = 0; i < rhs.mLayers.size(); i++)
  {
    mLayers.push_back(new CLayer(*(rhs.mLayers[i])));
  }

  for(unsigned int i = 0; i < rhs.mItems.size(); i++)
  {
    mItems.push_back(new CItem(*(rhs.mItems[i])));
  }
}
//---------------------------------------------------------------------------

CLevel& CLevel::operator=(const CLevel& rhs)
{
  // check for self-assignment
  if (&rhs == this)
  {
    return *this;
  }


  // make some cleanup
  for(unsigned int i = 0; i < mLayers.size(); i++)
  {
    delete mLayers[i];
  }

  for(unsigned int i = 0; i < mItems.size(); i++)
  {
    delete mItems[i];
  }

  mLayers.clear();
  mItems.clear();

  // assign
  mWidth      = rhs.mWidth;
  mHeight     = rhs.mHeight;
  mTileSetDat = rhs.mTileSetDat;

  for(unsigned int i = 0; i < rhs.mLayers.size(); i++)
  {
    mLayers.push_back(new CLayer(*(rhs.mLayers[i])));
  }

  for(unsigned int i = 0; i < rhs.mItems.size(); i++)
  {
    mItems.push_back(new CItem(*(rhs.mItems[i])));
  }

  return *this;
}
//---------------------------------------------------------------------------

bool CLevel::RemoveLayer(int _index)
{
  // ???
  return false;
}
//---------------------------------------------------------------------------

bool CLevel::AddLayer(CLayer* _layer)
{
  if (!_layer)
  {
    return false;
  }

  mLayers.push_back(_layer);

  return true;
}
//---------------------------------------------------------------------------

CLayer* CLevel::GetLayer(int _index)
{
  if (mLayers.empty())
  {
    return 0;
  }

  if ((_index < 0) || (_index >= (int)mLayers.size()))
  {
    return 0;
  }

  return mLayers[_index];
}
//---------------------------------------------------------------------------

int CLevel::GetLayerCount()
{
  return mLayers.size();
}
//---------------------------------------------------------------------------

void CLevel::SetTileSetDat(std::string _name)
{
  mTileSetDat = _name;
}
//---------------------------------------------------------------------------

void CLevel::SortItems()
{
  std::vector<CItem*> mTempItems;

  std::vector<CItem*>::iterator lIt = mItems.begin();

  // put props in front of the list
  for(unsigned int i = 0; i < mItems.size(); i++)
  {
    int lType = mItems[i]->GetType();

    switch(lType)
    {
      case PROP_ITEM:
        lIt = mTempItems.begin();
        mTempItems.insert(lIt, mItems[i]);
        break;

      case ELEVATOR_ITEM:
      case AUTOMATIC_ELEVATOR_ITEM:
        break;

      default:
        mTempItems.push_back(mItems[i]);
    }
  }

  // put elevators and automatic elevators at the end of the list
  for(unsigned int i = 0; i < mItems.size(); i++)
  {
    int lType = mItems[i]->GetType();

    switch(lType)
    {
      case ELEVATOR_ITEM:
      case AUTOMATIC_ELEVATOR_ITEM:
        mTempItems.push_back(mItems[i]);
        break;
    }
  }  

  mItems = mTempItems;
}
//---------------------------------------------------------------------------

bool CLevel::Save(std::string _fileName)
{
  // open the file
  std::fstream lFile;

  lFile.open(_fileName.c_str(), std::ios_base::out | std::ios_base::binary | std::ios_base::trunc);//, 0666);

  // exit if file cannot be opened
  if (lFile.fail())
  {
    return false;
  }

  // dump map width&height
  unsigned char lWidth = mWidth;
  unsigned char lHeight = mHeight;

  lFile.write(&lWidth, 1);
  lFile.write(&lHeight, 1);

  // dump layers data
  for(int i = 0; i < (int)mLayers.size(); i++)
  {
    for(int x = 0; x < mWidth; x++)
    {
      for(int y = 0; y < mHeight; y++)
      {
        CBrush* lBrush = mLayers[i]->GetBrush(x, y);

        unsigned char lData;

        if (lBrush)
        {
          lData = (unsigned char)(lBrush->GetTileId());
        }
        else
        {
          lData = (unsigned char)255;
        }

        lFile.write(&lData, 1);
      }
    }
  }

  // sort the items before saving (reduces code in the engine)
  SortItems();

  //
  // dump items
  //
  // write the number of objects
  unsigned char lItemNo = mItems.size();

  /*
  AnsiString lTemp = AnsiString(mItems.size()) + "...";

  for(int i = 0; i < mItems.size(); i++)
  {
    lTemp = lTemp + AnsiString((int)(mItems[i]->GetType())) + ", ";
  }

  Application->MessageBox(lTemp.c_str(), "Saved");
  */

  /*
  for(int i = 0; i < mItems.size(); i++)
  {
    short int lX = (short int)mItems[i]->GetX();
    short int lY = (short int)mItems[i]->GetY();

    if (mItems[i]->GetType() <= 14 && lX < mWidth * 16 && lY < mHeight * 16)
    {
      lItemNo++;
    }
  }
  */

  char lData[2048];
  int  lSize = 0;

  lData[lSize++] = lItemNo;

  for(int i = 0; i < lItemNo; i++)
  {
    lData[lSize++] = mItems[i]->GetType();

    lData[lSize++] = mItems[i]->GetX() % 256;
    lData[lSize++] = mItems[i]->GetX() / 256;

    lData[lSize++] = mItems[i]->GetY() % 256;
    lData[lSize++] = mItems[i]->GetY() / 256;

    unsigned char lParam[10];
    char lText[1024];
    
    // fill the params
    for(int j = 0; j < 10; j++)
    {
      lParam[j] = mItems[i]->GetParam(j);
    }

    switch(mItems[i]->GetType())
    {
      case PLAYER_ITEM:
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        lData[lSize++] = lParam[3];
        lData[lSize++] = lParam[4];
        break;

      case HEALTH_ITEM:
        // 0 - healing amount
        lData[lSize++] = lParam[0];
        break;

      case WEAPON_ITEM:
        // 0 - power amount
        lData[lSize++] = lParam[0];
        break;

      case AMMO_ITEM:
        // 0 - power amount
        lData[lSize++] = lParam[0];
        break;

      case SLASHER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - aggresivity, 4 - patrol distance, 5 - color, 6 - friendly, (notyet) 7 - text if friendly
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        lData[lSize++] = lParam[3];
        lData[lSize++] = lParam[4];
        lData[lSize++] = lParam[5];
        lData[lSize++] = lParam[6];
        break;

      case SHOOTER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - recovery time, 4- joy time, 5 - aggresivity, 6 - patrol distance
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        lData[lSize++] = lParam[3];
        lData[lSize++] = lParam[4];
        lData[lSize++] = lParam[5];
        lData[lSize++] = lParam[6];
        lData[lSize++] = lParam[7];
        break;

      case MINE_ITEM:
        // 0 - damage
        lData[lSize++] = lParam[0];
        break;

      case MONEY_ITEM:
        // 0 - amount
        lData[lSize++] = lParam[0];
        break;

      case TRIGGER_SAVEGAME:
        //lFile.read(&lParam[0], 0);
        break;

      case TRIGGER_ENDLEVEL:
        // next level
        lData[lSize++] = lParam[0];
        break;

      case TRIGGER_TEXT:
        strcpy(lText, mItems[i]->GetText().c_str());

        lData[lSize++] = strlen(lText);

        for(unsigned int i = 0; i < strlen(lText); i++)
        {
          lData[lSize++] = lText[i];
        }
        break;

      case BUTTON_ITEM:
        // 0 - the elevator id this button is linked to
        lData[lSize++] = lParam[0];
        break;

      case ELEVATOR_ITEM:
        // 0 - elevator id, 1 - direction, 2 - distance
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        break;

      case AUTOMATIC_ELEVATOR_ITEM:
        // 0 - direction, 1 - distance1, 2 - distance2, 3 - wait time, 4 - speed
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        lData[lSize++] = lParam[3];
        lData[lSize++] = lParam[4];
        break;

      case ROBOT_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - patrol distance, 4 - recovery time
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        lData[lSize++] = lParam[3];
        lData[lSize++] = lParam[4];
        break;

      case PROP_ITEM:
        // 0 - file index, 1 - time, 2 - auto terminate
        lData[lSize++] = lParam[0];
        lData[lSize++] = lParam[1];
        lData[lSize++] = lParam[2];
        break;
    }
  }

  lFile.write(lData, lSize);


  /*
  lFile.write(&lItemNo, 1);

  // write the objects
  for(int i = 0; i < lItemNo; i++)
  {
    char lType = mItems[i]->GetType();

    short int lX = (short int)mItems[i]->GetX();
    short int lY = (short int)mItems[i]->GetY();

    //if (lX > mWidth * 16 || lY > mHeight * 16)
    //{
    //  continue;
    //}

    lFile.write(&lType, 1);
    lFile.write((char*)(&lX), 2);
    lFile.write((char*)(&lY), 2);

    unsigned char lParam[10];
    char lText[1024];

    // fill the params
    for(int j = 0; j < 10; j++)
    {
      lParam[j] = mItems[i]->GetParam(j);
    }

    switch(lType)
    {
      case PLAYER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - weapon, 3 - recovery, 4 - money
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        lFile.write(&lParam[3], 1);
        lFile.write(&lParam[4], 1);
        break;

      case HEALTH_ITEM:
        // 0 - healing amount
        lFile.write(&lParam[0], 1);
        break;

      case WEAPON_ITEM:
        // 0 - power amount
        lFile.write(&lParam[0], 1);
        break;

      case AMMO_ITEM:
        // 0 - power amount
        lFile.write(&lParam[0], 1);
        break;

      case SLASHER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - aggresivity, 4 - patrol distance, 5 - color, 6 - friendly, (notyet) 7 - text if friendly
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        lFile.write(&lParam[3], 1);
        lFile.write(&lParam[4], 1);
        lFile.write(&lParam[5], 1);
        lFile.write(&lParam[6], 1);
        break;

      case SHOOTER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - recovery time, 4- joy time, 5 - aggresivity, 6 - patrol distance
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        lFile.write(&lParam[3], 1);
        lFile.write(&lParam[4], 1);
        lFile.write(&lParam[5], 1);
        lFile.write(&lParam[6], 1);
        break;

      case MINE_ITEM:
        // 0 - damage
        lFile.write(&lParam[0], 0);
        break;

      case MONEY_ITEM:
        // 0 - amount
        lFile.write(&lParam[0], 0);
        break;

      case TRIGGER_SAVEGAME:
        //lFile.read(&lParam[0], 0);
        break;

      case TRIGGER_ENDLEVEL:
        // next level
        lFile.write(&lParam[0], 1);
        break;

      case TRIGGER_TEXT:
        strcpy(lText, mItems[i]->GetText().c_str());

        lParam[0] = strlen(lText);

        lFile.write(&lParam[0], 1);
        lFile.write(lText, lParam[0]);
        break;

      case BUTTON_ITEM:
        // 0 - the elevator id this button is linked to
        lFile.write(&lParam[0], 1);
        break;

      case ELEVATOR_ITEM:
        // 0 - elevator id, 1 - direction, 2 - distance
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        break;

      case AUTOMATIC_ELEVATOR_ITEM:
        // 0 - direction, 1 - distance1, 2 - distance2, 3 - wait time, 4 - speed
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        lFile.write(&lParam[3], 1);
        lFile.write(&lParam[4], 1);
        break;

      case ROBOT_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - patrol distance, 4 - recovery time
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        lFile.write(&lParam[3], 1);
        lFile.write(&lParam[4], 1);
        break;

      case PROP_ITEM:
        // 0 - file index, 1 - time, 2 - auto terminate
        lFile.write(&lParam[0], 1);
        lFile.write(&lParam[1], 1);
        lFile.write(&lParam[2], 1);
        break;
    }
  }
  */


  lFile.close();

  return true;
}
//---------------------------------------------------------------------------

bool CLevel::Load(std::string _fileName)
{
  // open the file
  std::fstream lFile;

  lFile.open(_fileName.c_str(), std::ios_base::in | std::ios_base::binary);

  // exit if file cannot be opened
  if (lFile.fail())
  {
    return false;
  }

  // dump map width&height
  unsigned char lWidth;
  unsigned char lHeight;

  lFile.read(&lWidth, 1);
  lFile.read(&lHeight, 1);

  mWidth  = lWidth;
  mHeight = lHeight;

  // read layers data
  for(int i = 0; i < 1; i++)
  {
    for(int x = 0; x < mWidth; x++)
    {
      for(int y = 0; y < mHeight; y++)
      {
        unsigned char lData;

        lFile.read(&lData, 1);

        CBrush* lBrush = 0;

        if (lData == 255)
        {
          lBrush = 0;
        }
        else
        {
          lBrush = new CBrush();

          lBrush->SetTileId(lData);
        }

        mLayers[i]->SetBrush(x, y, lBrush);
      }
    }
  }

  // read items
  mItems.clear();

  unsigned char lItemNo;

  lFile.read(&lItemNo, 1);

  // write the objects
  for(int i = 0; i < lItemNo; i++)
  {
    //short int lType;

    //lFile.read((char*)(&lType), 2);
    unsigned char lType;

    lFile.read(&lType, 1);

    short int lX;
    short int lY;

    lFile.read((char*)(&lX), 2);
    lFile.read((char*)(&lY), 2);

    unsigned char lParam[10];
    char lText[1024];

    lText[0] = '\0';

    switch(lType)
    {
      case PLAYER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - weapon, 3 - recovery, 4 - money
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        lFile.read(&lParam[3], 1);
        lFile.read(&lParam[4], 1);
        break;

      case HEALTH_ITEM:
        // 0 - healing amount
        lFile.read(&lParam[0], 1);
        break;

      case WEAPON_ITEM:
        // 0 - power amount
        lFile.read(&lParam[0], 1);
        break;

      case AMMO_ITEM:
        // 0 - power amount
        lFile.read(&lParam[0], 1);
        break;

      case SLASHER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - aggresivity, 4 - patrol distance
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        lFile.read(&lParam[3], 1);
        lFile.read(&lParam[4], 1);
        lFile.read(&lParam[5], 1);
        lFile.read(&lParam[6], 1);
        break;

      case SHOOTER_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - recovery time, 4- joy time, 5 - aggresivity, 6 - patrol distance, 7 - bullets dropped
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        lFile.read(&lParam[3], 1);
        lFile.read(&lParam[4], 1);
        lFile.read(&lParam[5], 1);
        lFile.read(&lParam[6], 1);
        lFile.read(&lParam[7], 1);
        //lParam[7] = 0;
        break;

      case MINE_ITEM:
        // 0 - damage
        lFile.read(&lParam[0], 1);
        break;

      case MONEY_ITEM:
        // 0 - amount
        lFile.read(&lParam[0], 1);
        break;

      case TRIGGER_SAVEGAME:
        //lFile.read(&lParam[0], 0);
        break;

      case TRIGGER_ENDLEVEL:
        lFile.read(&lParam[0], 1);
        break;

      case TRIGGER_TEXT:
        lFile.read(&lParam[0], 1);
        lFile.read(lText, lParam[0]);

        lText[lParam[0]] = '\0';
        break;

      case BUTTON_ITEM:
        // 0 - the elevator id this button is linked to
        lFile.read(&lParam[0], 1);
        break;

      case ELEVATOR_ITEM:
        // 0 - elevator id, 1 - direction, 2 - distance
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        break;

      case AUTOMATIC_ELEVATOR_ITEM:
        // 0 - direction, 1 - distance1, 2 - distance2, 3 - wait time, 4 - speed
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        lFile.read(&lParam[3], 1);
        lFile.read(&lParam[4], 1);
        break;

      case ROBOT_ITEM:
        // 0 - stance (left, right), 1 - health, 2 - damage, 3 - patrol distance, 4 - recovery time
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        lFile.read(&lParam[3], 1);
        lFile.read(&lParam[4], 1);
        break;

      case PROP_ITEM:
        // 0 - file index, 1 - time, 2 - auto terminate
        lFile.read(&lParam[0], 1);
        lFile.read(&lParam[1], 1);
        lFile.read(&lParam[2], 1);
        break;
    }

    // we have all the info needed to create the CItem

    if ((int)lType <= MAX_ITEMS)
    {
      CItem* lItem = new CItem();

      lItem->SetType(lType);
      lItem->SetX(lX);
      lItem->SetY(lY);
      lItem->SetText(std::string(lText));

      for(int j = 0; j < 10; j++)
      {
        lItem->SetParam(j, lParam[j]);
      }

      mItems.push_back(lItem);
    }
    else
    {
    }
  }

  lFile.close();


  //Application->MessageBox(AnsiString(mItems.size()).c_str(), "Added");

  /*
  for(int i = 0; i < mItems.size(); i++)
  {
    if (i > 0 && mItems[i]->GetType() == 0)
    {
      RemoveItem(i);
      break;
    }
  }
  */

  /*
  AnsiString lTemp = "";//AnsiString("items: ") + AnsiString(mItems.size()) + "...";
  for(int i = 0; i < mItems.size(); i++)
  {
    lTemp = lTemp + AnsiString((int)(mItems[i]->GetType())) + ", ";
  }

  Application->MessageBox(lTemp.c_str(), "Added");
  */

  return true;
}
//---------------------------------------------------------------------------

void   CLevel::AddItem(CItem* _item)
{
  mItems.push_back(_item);
}
//---------------------------------------------------------------------------

CItem* CLevel::GetItem(int _index)
{
  return mItems[_index];
}
//---------------------------------------------------------------------------

void   CLevel::RemoveItem(int _index)
{
  std::vector<CItem*>::iterator lIt = mItems.begin();

  lIt += _index;

  mItems.erase(lIt);
}
//---------------------------------------------------------------------------

void CLevel::SetMemento(CMementoLevel* _v)
{
  const CLevel* lLevel = _v->GetState();

  *this = *lLevel; // :)
}
//---------------------------------------------------------------------------

CMementoLevel* CLevel::GetMemento()
{
  CMementoLevel* lTemp = new CMementoLevel();
  lTemp->SetState(this);

  return lTemp;
}


CItemRepository::CItemRepository()
{
  CItemParams* lParam;

  for(int i = 0; i <= MAX_ITEMS; i++)
  {
    lParam = new CItemParams();
    mItemsParams.push_back(lParam);
  }
}
//---------------------------------------------------------------------------

CItemRepository::~CItemRepository()
{
  mItemsParams.clear();
}
//---------------------------------------------------------------------------

void CItemRepository::SaveToFile(std::string _filename)
{
  FILE* lFile;

  lFile = fopen(_filename.c_str(), "w+t");

  if (!lFile)
  {
    return;
  }

  fprintf(lFile, "HEALTH_ITEM %d\n",  mItemsParams[HEALTH_ITEM]->GetParams(0));
  fprintf(lFile, "AMMO_ITEM %d\n",    mItemsParams[AMMO_ITEM]->GetParams(0));
  fprintf(lFile, "WEAPON_ITEM %d\n",  mItemsParams[WEAPON_ITEM]->GetParams(0));
  fprintf(lFile, "MONEY_ITEM %d\n",   mItemsParams[MONEY_ITEM]->GetParams(0));
  fprintf(lFile, "MINE_ITEM %d\n",    mItemsParams[MINE_ITEM]->GetParams(0));
  fprintf(lFile, "TRIGGER_TEXT %s\n", mItemsParams[TRIGGER_TEXT]->GetText().c_str());
  fprintf(lFile, "TRIGGER_ENDLEVEL %d\n", mItemsParams[TRIGGER_ENDLEVEL]->GetParams(0));
  fprintf(lFile, "BUTTON_ITEM %d\n", mItemsParams[BUTTON_ITEM]->GetParams(0));

  fprintf(lFile, "PLAYER_ITEM %d\t%d\t%d\t%d\t%d\n",
    mItemsParams[PLAYER_ITEM]->GetParams(0),
    mItemsParams[PLAYER_ITEM]->GetParams(1),
    mItemsParams[PLAYER_ITEM]->GetParams(2),
    mItemsParams[PLAYER_ITEM]->GetParams(3),
    mItemsParams[PLAYER_ITEM]->GetParams(4)
  );

  fprintf(lFile, "SLASHER_ITEM %d\t%d\t%d\t%d\t%d\t%d\t%d\n",
    mItemsParams[SLASHER_ITEM]->GetParams(0),
    mItemsParams[SLASHER_ITEM]->GetParams(1),
    mItemsParams[SLASHER_ITEM]->GetParams(2),
    mItemsParams[SLASHER_ITEM]->GetParams(3),
    mItemsParams[SLASHER_ITEM]->GetParams(4),
    mItemsParams[SLASHER_ITEM]->GetParams(5),
    mItemsParams[SLASHER_ITEM]->GetParams(6)
  );

  fprintf(lFile, "SHOOTER_ITEM %d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
    mItemsParams[SHOOTER_ITEM]->GetParams(0),
    mItemsParams[SHOOTER_ITEM]->GetParams(1),
    mItemsParams[SHOOTER_ITEM]->GetParams(2),
    mItemsParams[SHOOTER_ITEM]->GetParams(3),
    mItemsParams[SHOOTER_ITEM]->GetParams(4),
    mItemsParams[SHOOTER_ITEM]->GetParams(5),
    mItemsParams[SHOOTER_ITEM]->GetParams(6),
    mItemsParams[SHOOTER_ITEM]->GetParams(7)
  );

  fprintf(lFile, "ELEVATOR_ITEM %d\t%d\t%d\n",
    mItemsParams[ELEVATOR_ITEM]->GetParams(0),
    mItemsParams[ELEVATOR_ITEM]->GetParams(1),
    mItemsParams[ELEVATOR_ITEM]->GetParams(2)
  );

  fprintf(lFile, "AUTOMATIC_ELEVATOR_ITEM %d\t%d\t%d\t%d\t%d\n",
    mItemsParams[AUTOMATIC_ELEVATOR_ITEM]->GetParams(0),
    mItemsParams[AUTOMATIC_ELEVATOR_ITEM]->GetParams(1),
    mItemsParams[AUTOMATIC_ELEVATOR_ITEM]->GetParams(2),
    mItemsParams[AUTOMATIC_ELEVATOR_ITEM]->GetParams(3),
    mItemsParams[AUTOMATIC_ELEVATOR_ITEM]->GetParams(4)
  );

  fprintf(lFile, "ROBOT_ITEM %d\t%d\t%d\t%d\t%d\n",
    mItemsParams[ROBOT_ITEM]->GetParams(0),
    mItemsParams[ROBOT_ITEM]->GetParams(1),
    mItemsParams[ROBOT_ITEM]->GetParams(2),
    mItemsParams[ROBOT_ITEM]->GetParams(3),
    mItemsParams[ROBOT_ITEM]->GetParams(4)
  );

  fprintf(lFile, "PROP_ITEM %d\t%d\t%d\n",
    mItemsParams[PROP_ITEM]->GetParams(0),
    mItemsParams[PROP_ITEM]->GetParams(1),
    mItemsParams[PROP_ITEM]->GetParams(2)
  );

  fclose(lFile);
}
//---------------------------------------------------------------------------

void CItemRepository::LoadFromFile(std::string _filename)
{
  FILE* lFile;

  lFile = fopen(_filename.c_str(), "r+t");

  if (!lFile)
  {
    return;
  }

  while(!feof(lFile))
  {
    char lItemString[200];

    fscanf(lFile, "%s", lItemString);

    if (std::string(lItemString) == "TRIGGER_TEXT")
    {
      char lText[200];;

      fscanf(lFile, "%s\n", &lText);

      mItemsParams[TRIGGER_TEXT]->SetText(lText);
    }

    if (std::string(lItemString) == "TRIGGER_ENDLEVEL")
    {
      int lData;

      fscanf(lFile, "%d\n", &lData);

      mItemsParams[TRIGGER_ENDLEVEL]->SetParams(0, lData);
    }

    if (std::string(lItemString) == "HEALTH_ITEM")
    {
      int lData;

      fscanf(lFile, "%d\n", &lData);

      mItemsParams[HEALTH_ITEM]->SetParams(0, lData);
    }

    if (std::string(lItemString) == "AMMO_ITEM")
    {
      int lData;

      fscanf(lFile, "%d\n", &lData);

      mItemsParams[AMMO_ITEM]->SetParams(0, lData);
    }

    if (std::string(lItemString) == "WEAPON_ITEM")
    {
      int lData;

      fscanf(lFile, "%d\n", &lData);

      mItemsParams[WEAPON_ITEM]->SetParams(0, lData);
    }

    if (std::string(lItemString) == "MINE_ITEM")
    {
      int lData;

      fscanf(lFile, "%d\n", &lData);

      mItemsParams[MINE_ITEM]->SetParams(0, lData);
    }

    if (std::string(lItemString) == "MONEY_ITEM")
    {
      int lData;

      fscanf(lFile, "%d\n", &lData);

      mItemsParams[MONEY_ITEM]->SetParams(0, lData);
    }
    
    if (std::string(lItemString) == "PLAYER_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2], &lData[3], &lData[4]);

      for(int i = 0; i < 5; i++)
      {
        mItemsParams[PLAYER_ITEM]->SetParams(i, lData[i]);
      }
    }

    if (std::string(lItemString) == "SHOOTER_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2], &lData[3], &lData[4], &lData[5], &lData[6], &lData[7]);

      for(int i = 0; i < 8; i++)
      {
        mItemsParams[SHOOTER_ITEM]->SetParams(i, lData[i]);
      }
    }

    if (std::string(lItemString) == "SLASHER_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\t%d\t%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2], &lData[3], &lData[4], &lData[5], &lData[6]);

      for(int i = 0; i < 7; i++)
      {
        mItemsParams[SLASHER_ITEM]->SetParams(i, lData[i]);
      }
    }

    if (std::string(lItemString) == "BUTTON_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\n", &lData[0]);

      mItemsParams[BUTTON_ITEM]->SetParams(0, lData[0]);
    }

    if (std::string(lItemString) == "ELEVATOR_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2]);

      for(int i = 0; i < 3; i++)
      {
        mItemsParams[ELEVATOR_ITEM]->SetParams(i, lData[i]);
      }
    }

    if (std::string(lItemString) == "AUTOMATIC_ELEVATOR_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2], &lData[3], &lData[4]);

      for(int i = 0; i < 5; i++)
      {
        mItemsParams[AUTOMATIC_ELEVATOR_ITEM]->SetParams(i, lData[i]);
      }
    }

    if (std::string(lItemString) == "ROBOT_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2], &lData[3], &lData[4]);

      for(int i = 0; i < 5; i++)
      {
        mItemsParams[ROBOT_ITEM]->SetParams(i, lData[i]);
      }
    }

    if (std::string(lItemString) == "PROP_ITEM")
    {
      int lData[10];

      fscanf(lFile, "%d\t%d\t%d\n", &lData[0], &lData[1], &lData[2]);

      for(int i = 0; i < 3; i++)
      {
        mItemsParams[PROP_ITEM]->SetParams(i, lData[i]);
      }
    }
  }

  fclose(lFile);
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
