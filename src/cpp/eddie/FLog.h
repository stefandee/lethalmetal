//---------------------------------------------------------------------------

#ifndef FLogH
#define FLogH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Menus.hpp>
//---------------------------------------------------------------------------

#include <string>
#include <vector>

class TFormLog : public TForm
{
__published:	// IDE-managed Components
        TMemo *memoLog;
        TPopupMenu *PopupMenu1;
        TMenuItem *Clear1;
        void __fastcall Clear1Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TFormLog(TComponent* Owner);
        
        void Add(std::string _line);
        void Add(std::vector<std::string> _list);
        void Clear();
};
//---------------------------------------------------------------------------
extern PACKAGE TFormLog *FormLog;
//---------------------------------------------------------------------------
#endif
