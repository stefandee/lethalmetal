//---------------------------------------------------------------------------

#ifndef FToolH
#define FToolH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
#include <Grids.hpp>
#include <Menus.hpp>
#include <vector>
#include "Level.h"
//---------------------------------------------------------------------------
class TFormTool : public TForm
{
__published:	// IDE-managed Components
        TPageControl *PageControl1;
        TTabSheet *tabTiles;
        TTabSheet *tabEnemy;
        TTabSheet *tabItems;
        TTabSheet *tabMisc;
        TDrawGrid *DrawGrid1;
        TDrawGrid *DrawGrid2;
        TDrawGrid *DrawGrid3;
        TTabSheet *tabTriggers;
        TDrawGrid *DrawGrid4;
        TPopupMenu *pupTools;
        TMenuItem *Edit1;
        TDrawGrid *DrawGrid5;
        TTabSheet *tabElevators;
        TDrawGrid *DrawGrid6;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall DrawGrid1DrawCell(TObject *Sender, int ACol,
          int ARow, TRect &Rect, TGridDrawState State);
        void __fastcall DrawGrid2DrawCell(TObject *Sender, int ACol,
          int ARow, TRect &Rect, TGridDrawState State);
        void __fastcall DrawGrid3DrawCell(TObject *Sender, int ACol,
          int ARow, TRect &Rect, TGridDrawState State);
        void __fastcall DrawGrid4DrawCell(TObject *Sender, int ACol,
          int ARow, TRect &Rect, TGridDrawState State);
        void __fastcall Edit1Click(TObject *Sender);
        void __fastcall DrawGrid5DrawCell(TObject *Sender, int ACol,
          int ARow, TRect &Rect, TGridDrawState State);
        void __fastcall DrawGrid6DrawCell(TObject *Sender, int ACol,
          int ARow, TRect &Rect, TGridDrawState State);
        void __fastcall PageControl1Change(TObject *Sender);
        void __fastcall DrawGrid5Click(TObject *Sender);
private:	// User declarations
  std::vector<Graphics::TBitmap*> mMiscList;
  std::vector<Graphics::TBitmap*> mItemsList;
  std::vector<Graphics::TBitmap*> mTriggersList;
  std::vector<Graphics::TBitmap*> mEnemyList;
  std::vector<Graphics::TBitmap*> mElevatorsList;
  std::vector<CTile*> mTileList;

public:		// User declarations
        void CreateTiles(AnsiString _file);
        void CreateEnemy();
        void CreateItems();
        void CreateMisc();
        void CreateTriggers();
        void CreateElevators();


        CTile* GetTile(int _index);

        Graphics::TBitmap* mBitmapTiles;

        __fastcall TFormTool(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormTool *FormTool;
//---------------------------------------------------------------------------
#endif
