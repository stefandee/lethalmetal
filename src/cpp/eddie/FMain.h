//---------------------------------------------------------------------------

#ifndef FMainH
#define FMainH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Menus.hpp>
//---------------------------------------------------------------------------

#include "EditorDef.h"
#include "Level.h"
#include "PropData.h"
#include "Clipboard.h"
#include <ExtCtrls.hpp>
#include <ComCtrls.hpp>
#include <Dialogs.hpp>

const int HISTORY_MENU_ITEMINDEX = 6; 
const int HISTORY_MENU_MAX_ITEMS  = 5; 

class TMain : public TForm
{
__published:	// IDE-managed Components
        TMainMenu *MainMenu1;
        TMenuItem *File1;
        TMenuItem *Edit1;
        TMenuItem *Tools1;
        TMenuItem *Help1;
        TMenuItem *Help2;
        TMenuItem *N1;
        TMenuItem *About1;
        TMenuItem *New1;
        TMenuItem *Load1;
        TMenuItem *Save1;
        TMenuItem *Saveas1;
        TMenuItem *N2;
        TMenuItem *Exit1;
        TMenuItem *Layers1;
        TMenuItem *Layereditor1;
        TMenuItem *N3;
        TMenuItem *ZoneEditor1;
        TStatusBar *StatusBar1;
        TScrollBar *scrollH;
        TScrollBar *scrollV;
        TPaintBox *paintLevel;
        TSaveDialog *dlgSave;
        TOpenDialog *dlgLoad;
        TMenuItem *Close1;
        TMenuItem *PasteMapPart1;
        TMenuItem *N4;
        TMenuItem *MakeClean1;
        TOpenDialog *dlgLoadArch;
        TSaveDialog *dlgSaveArch;
        TMenuItem *N5;
        TMenuItem *LoadArchitecturetoClipboard1;
        TMenuItem *SaveArchitecturefromClipboard1;
        TMenuItem *Views1;
        TMenuItem *ShowGrid1;
        TMenuItem *ShowRulers1;
        TMenuItem *N6;
        TMenuItem *Undo1;
        TMenuItem *Redo1;
        TMenuItem *N7;
        TMenuItem *N8;
        TMenuItem *Verify1;
        void __fastcall Exit1Click(TObject *Sender);
        void __fastcall ZoneEditor1Click(TObject *Sender);
        void __fastcall New1Click(TObject *Sender);
        void __fastcall paintLevelPaint(TObject *Sender);
        void __fastcall paintLevelMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall paintLevelMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall paintLevelMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall scrollVScroll(TObject *Sender,
          TScrollCode ScrollCode, int &ScrollPos);
        void __fastcall scrollHScroll(TObject *Sender,
          TScrollCode ScrollCode, int &ScrollPos);
        void __fastcall FormResize(TObject *Sender);
        void __fastcall Save1Click(TObject *Sender);
        void __fastcall Saveas1Click(TObject *Sender);
        void __fastcall Load1Click(TObject *Sender);
        void __fastcall Close1Click(TObject *Sender);
        void __fastcall PasteMapPart1Click(TObject *Sender);
        void __fastcall MakeClean1Click(TObject *Sender);
        void __fastcall LoadArchitecturetoClipboard1Click(TObject *Sender);
        void __fastcall SaveArchitecturefromClipboard1Click(
          TObject *Sender);
        void __fastcall ShowGrid1Click(TObject *Sender);
        void __fastcall ShowRulers1Click(TObject *Sender);
        void __fastcall Undo1Click(TObject *Sender);
        void __fastcall Redo1Click(TObject *Sender);
        void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
        void __fastcall FormShow(TObject *Sender);
        void __fastcall Verify1Click(TObject *Sender);
        void __fastcall About1Click(TObject *Sender);
private:	// User declarations
  TMapState mMapState;
  CLevel*   mLevel;
  AnsiString mMapName;
  int mCurrentX, mCurrentY, mStartX, mStartY, mPasteX, mPasteY;
  bool mSelecting;
  int mFileHistoryIndex;
  std::vector<AnsiString> mFileHistory;
  TRect mRequiredGeometry;

  TRect mTileSelectionRect;
  bool  mTileSelectionValid;

  std::vector<Graphics::TBitmap*> mBitmaps;
  std::vector<TPropsData> mPropBitmaps;
  Clipboard mClipboard;

  void CreateBitmaps();
  Graphics::TBitmap* GetBitmap(int _type);
  Graphics::TBitmap* GetPropBitmap(int _fileIndex);
  void SetMapName(AnsiString _name);

  TPoint ElevatorDir(int _index);
  void PaintGrid();

  std::vector<CMementoLevel*> mUndoList;
  std::vector<CMementoLevel*> mRedoList;

  void ClearChanges();
  void SaveForUndo();

  void EditItem(int X, int Y);

  void SaveConfig();
  void LoadConfig();

  void LoadMap(AnsiString _fileName);

  void __fastcall HistoryClick(TObject *Sender);

public:		// User declarations
        __fastcall TMain(TComponent* Owner);
        void SetMapSize(int _width, int _height);
        void ComputeScrollLimits();
};
//---------------------------------------------------------------------------
extern PACKAGE TMain *Main;
//---------------------------------------------------------------------------
#endif
