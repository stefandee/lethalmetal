//---------------------------------------------------------------------------

#ifndef ClipboardH
#define ClipboardH
//---------------------------------------------------------------------------

#include <string>

class CLevel;

class Clipboard
{
  public:
    Clipboard();
    ~Clipboard();

  public:
    void CopySelection(CLevel* _level, int _x, int _y, int _w, int _h);
    void PasteSelection(CLevel* _level, int _x, int _y);
    bool HasSelection();
    void Clear();
    //const CLevel* GetSelection();

  public:
    bool Load(std::string _fileName);
    bool Save(std::string _fileName);

  private:
    CLevel* mSelection;
    bool  mHasSelection;
};

#endif
