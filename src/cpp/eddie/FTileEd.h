//---------------------------------------------------------------------------

#ifndef FTileEdH
#define FTileEdH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ExtCtrls.hpp>
#include "Level.h"
#include <ComCtrls.hpp>
#include <Dialogs.hpp>
#include <Menus.hpp>
//---------------------------------------------------------------------------
class TFormTileEd : public TForm
{
__published:	// IDE-managed Components
        TGroupBox *gbOptions;
        TGroupBox *gbTile;
        TLabel *Label1;
        TPaintBox *paintTile;
        TStatusBar *statusTile;
        TGroupBox *GroupBox1;
        TComboBox *cbTiles;
        TButton *btnAdd;
        TButton *btnDel;
        TButton *btnRename;
        TButton *bntClearAll;
        TGroupBox *GroupBox2;
        TCheckBox *cbDrawAll;
        TCheckBox *cbDrawGrid;
        TGroupBox *GroupBox3;
        TCheckBox *checkUp;
        TCheckBox *checkDown;
        TCheckBox *checkLeft;
        TCheckBox *checkRight;
        TEdit *edGridW;
        TEdit *edGridH;
        TLabel *Label2;
        TShape *shapeGridColor;
        TShape *shapeSelColor;
        TShape *shapeUnselColor;
        TColorDialog *dlgColor;
        TCheckBox *checkLadder;
        TGroupBox *GroupBox4;
        TTrackBar *trackZoom;
        TScrollBar *sbH;
        TScrollBar *sbV;
        TCheckBox *checkHeadLadder;
        TButton *btn1x1;
        TMainMenu *MainMenu1;
        TMenuItem *File1;
        TMenuItem *Export1x1tilesetdata1;
        TSaveDialog *dlgSave;
        TMenuItem *Export16bittilesetenginev310120041;
        void __fastcall FormShow(TObject *Sender);
        void __fastcall btnAddClick(TObject *Sender);
        void __fastcall FormDestroy(TObject *Sender);
        void __fastcall paintTilePaint(TObject *Sender);
        void __fastcall cbTilesChange(TObject *Sender);
        void __fastcall paintTileMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall paintTileMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall paintTileMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall btnDelClick(TObject *Sender);
        void __fastcall btnRenameClick(TObject *Sender);
        void __fastcall bntClearAllClick(TObject *Sender);
        void __fastcall cbTilesEnter(TObject *Sender);
        void __fastcall FormHide(TObject *Sender);
        void __fastcall edGridWExit(TObject *Sender);
        void __fastcall edGridHExit(TObject *Sender);
        void __fastcall cbDrawGridClick(TObject *Sender);
        void __fastcall shapeGridColorMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall shapeSelColorMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall shapeUnselColorMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall cbDrawAllClick(TObject *Sender);
        void __fastcall trackZoomChange(TObject *Sender);
        void __fastcall sbVChange(TObject *Sender);
        void __fastcall sbHChange(TObject *Sender);
        void __fastcall btn1x1Click(TObject *Sender);
        void __fastcall Export1x1tilesetdata1Click(TObject *Sender);
        void __fastcall Export16bittilesetenginev310120041Click(
          TObject *Sender);
private:	// User declarations
  AnsiString mTileSetDat;
  int mStartX, mStartY, mCurrentX, mCurrentY;
  std::vector<CTile*> mTiles;
  Graphics::TBitmap* mBitmapTile;
  bool mSelecting;
  int mGridW, mGridH;

  void SaveToFile(AnsiString _file);
  void LoadFromfile(AnsiString _file);
  void BeginSelection(int X, int Y);
  void EndSelection(int X, int Y);
  void MakeCombo();
  void MakeCheckUp();
  void DrawTiles();
  void SetupCollisions();
  void DrawGrid();
  void ComputeScrollLimits();
  bool Export1x1TilesetData(std::string _fileName);
  bool Export16bitTilesetData(std::string _fileName);

public:		// User declarations
        __fastcall TFormTileEd(TComponent* Owner);
        void SetTileSetDat(AnsiString _fileName);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormTileEd *FormTileEd;
//---------------------------------------------------------------------------
#endif
