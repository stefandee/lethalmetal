//---------------------------------------------------------------------------

#ifndef LevelH
#define LevelH
//---------------------------------------------------------------------------

const int LEVEL_TILE_WIDTH = 16;
const int LEVEL_TILE_HEIGHT = 16;
const int LEVEL_MAX_WIDTH = 255;
const int LEVEL_MAX_HEIGHT = 255;
enum TCollisionFlag { CLS_UP, CLS_DOWN, CLS_RIGHT, CLS_LEFT };

// item types
const int PLAYER_ITEM             = 0;
const int HEALTH_ITEM             = 1;
const int WEAPON_ITEM             = 2;
const int MONEY_ITEM              = 3;
const int MINE_ITEM               = 4;
const int SHOOTER_ITEM            = 5;
const int SLASHER_ITEM            = 6;
const int TRIGGER_ENDLEVEL        = 7;
const int TRIGGER_SAVEGAME        = 8;
const int TRIGGER_TEXT            = 9;
const int BUTTON_ITEM             = 10;
const int ELEVATOR_ITEM           = 11;
const int AUTOMATIC_ELEVATOR_ITEM = 12;
const int ROBOT_ITEM              = 13;
const int PROP_ITEM               = 14;
const int AMMO_ITEM               = 15;
const int MAX_ITEMS               = 15;

#include <string>
#include <vector>

#include "MementoLevel.h"

class CTile
{
  public: // c-d
    CTile() { mCollision = 0; mIsLadder = false; mIsHeadLadder = false; };
    ~CTile() {};

  public: // get-set
    int GetX() { return mX; }
    int GetY() { return mY; }
    int GetW() { return mW; }
    int GetH() { return mH; }
    std::string GetName() { return mName; }
    bool GetIsLadder() { return mIsLadder; }
    bool GetIsHeadLadder() { return mIsHeadLadder; }

    void SetX(int _v) { mX = _v; }
    void SetY(int _v) { mY = _v; }
    void SetW(int _v) { mW = _v; }
    void SetH(int _v) { mH = _v; }
    void SetName(std::string _v) { mName = _v; }
    void SetIsLadder(bool _v) { mIsLadder = _v; }
    void SetIsHeadLadder(bool _v) { mIsHeadLadder = _v; }

    int GetCollision(TCollisionFlag _id);
    void SetCollision(TCollisionFlag _id, bool _v);

    bool IsSignificant();
    unsigned char GetPackedData();

  protected:
  private:
    std::string mName;
    int mX, mY, mW, mH;
    unsigned __int8 mCollision;
    bool mIsLadder;
    bool mIsHeadLadder;
};

class CBrush
{
  public: // c-d
    CBrush();
    ~CBrush() {};

  public: // get-set
    int GetX() { return mX; }
    int GetY() { return mY; }
    int GetW() { return mW; }
    int GetH() { return mH; }
    int GetTileId() { return mTileId; }

    void SetX(int _v);
    void SetY(int _v);
    void SetW(int _v);
    void SetH(int _v);
    void SetTileId(int _v);

  protected:

  private:
    int mX, mY, mW, mH, mTileId;
};

class CLayer
{
  public: // c-d
    CLayer();
    ~CLayer();

    CLayer(const CLayer& rhs);
    CLayer& operator=(const CLayer& rhs);
    
  public: // ops
    /*
    bool    AddBrush(CBrush* _brush);
    bool    RemoveBrush(int x, int y, int w, int h);
    bool    RemoveAll();
    CBrush* GetBrush(int _index);
    int GetBrushCount();
    */

    std::string  GetName() { return mName; };
    void SetName(std::string _name) { mName = _name; }

    void SetBrush(int _x, int _y, CBrush* _brush);
    CBrush* GetBrush(int _x, int _y);

  protected:

  private:
    //std::vector<CBrush*> mBrushes;
    CBrush* mBrushes[LEVEL_MAX_WIDTH][LEVEL_MAX_HEIGHT];
    std::string mName;
};

class CItem
{
  public: // c-d
    CItem() { mText = "nothing"; }
    ~CItem() {}

  public: // get-set
    void SetType(int _v) { mType = _v;}
    int  GetType() { return mType; }

    void SetX(int _v) { mX = _v;}
    int  GetX() { return mX; }

    void SetY(int _v) { mY = _v;}
    int  GetY() { return mY; }

    void SetParam(int _index, unsigned char _v) { mParam[_index] = _v;}
    unsigned char  GetParam(int _index) { return mParam[_index]; }

    void SetText(std::string _v) { mText = _v; }
    std::string GetText() { return mText; }

  private:
    int mType;
    int mX, mY;
    unsigned char mParam[32];
    std::string mText;
};

class CLevel
{
  public: // c-d
    CLevel(int _width, int _height);
    ~CLevel();

    CLevel(const CLevel& rhs);
    CLevel& operator=(const CLevel& rhs);

  public: // layer ops
    bool RemoveLayer(int _index);
    bool AddLayer(CLayer* _layer);
    CLayer* GetLayer(int _index);
    int GetLayerCount();

  public: // tile set
    //string GetTileSetBitmap() { return mTileSetBitmap; }
    std::string GetTileSetDat() { return mTileSetDat; }

    //void SetTileSetBitmap(string _name);
    void SetTileSetDat(std::string _name);

  public: // items
    void   AddItem(CItem* _item);
    CItem* GetItem(int _index);
    void   RemoveItem(int _index);
    int    GetItemCount() { return mItems.size(); }

    // CItem* GetTemplateItem(int _type);
    // void SetTemplateItem(int _type, CItem* _value);

  public: // dim
    int GetWidth() { return mWidth; }
    int GetHeight() { return mHeight; }

    //void SetWidth();
    //void SetHeight();

  public: // disk ops
    bool Save(std::string _fileName);
    bool Load(std::string _fileName);

  public: // memento pattern
    void SetMemento(CMementoLevel*);
    CMementoLevel* GetMemento();

  private:
    std::vector<CLayer*> mLayers;
    std::vector<CItem*> mItems;
    std::string /*mTileSetBitmap, */mTileSetDat;
    int mWidth, mHeight;

    void SortItems();
};

/*
class CLevel2
{
  public: // c-d
    CLevel2(int _width, int _height);
    ~CLevel2();

  public: // dim
    int GetWidth() { return mWidth; }
    int GetHeigth() { return mHeight; }

  public: // map
    void SetTile(int _x, int _y, CTile* _tile);
    CTile* GetTile(int _x, int _y);

  private:
    CTile* mTiles[LEVEL_MAX_WIDTH][LEVEL_MAX_HEIGHT];
};
*/

class CItemParams
{
  public:
    CItemParams() { mText = "Boo"; }
    ~CItemParams() {}

  public:
    int GetParams(int _index) { return mParams[_index]; }
    void  SetParams(int _index, int _v) { mParams[_index] = _v; }

    std::string GetText() { return mText; }
    void SetText(std::string _v) { mText = _v; }

  private:
    int         mParams[20];
    std::string mText;
};

class CItemRepository
{
  public: // c-d
    CItemRepository();
    ~CItemRepository();

  public: // ops
    CItemParams* GetParams(int _index) { return mItemsParams[_index]; }

    void SaveToFile(std::string _filename);
    void LoadFromFile(std::string _filename);

  protected:

  private:
    std::vector<CItemParams*> mItemsParams;
};

#endif
