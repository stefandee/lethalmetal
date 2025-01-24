//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
USEFORM("..\..\..\src\cpp\eddie\FMain.cpp", Main);
USEFORM("..\..\..\src\cpp\eddie\FName.cpp", FormName);
USEFORM("..\..\..\src\cpp\eddie\FPaint.cpp", FormPaint);
USEFORM("..\..\..\src\cpp\eddie\FParams.cpp", FormParams);
USEFORM("..\..\..\src\cpp\eddie\FSize.cpp", FormSize);
USEFORM("..\..\..\src\cpp\eddie\FTileEd.cpp", FormTileEd);
USEFORM("..\..\..\src\cpp\eddie\FTool.cpp", FormTool);
USEFORM("..\..\..\src\cpp\eddie\FDrop.cpp", FormDrop);
USEFORM("..\..\..\src\cpp\eddie\FItemEdit.cpp", FormItemEdit);
USEFORM("..\..\..\src\cpp\eddie\FLog.cpp", FormLog);
USEFORM("..\..\..\src\cpp\eddie\FAbout.cpp", FormAbout);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
{
        try
        {
                 Application->Initialize();
                 Application->CreateForm(__classid(TMain), &Main);
                 Application->CreateForm(__classid(TFormName), &FormName);
                 Application->CreateForm(__classid(TFormPaint), &FormPaint);
                 Application->CreateForm(__classid(TFormParams), &FormParams);
                 Application->CreateForm(__classid(TFormSize), &FormSize);
                 Application->CreateForm(__classid(TFormTileEd), &FormTileEd);
                 Application->CreateForm(__classid(TFormTool), &FormTool);
                 Application->CreateForm(__classid(TFormDrop), &FormDrop);
                 Application->CreateForm(__classid(TFormItemEdit), &FormItemEdit);
                 Application->CreateForm(__classid(TFormLog), &FormLog);
                 Application->CreateForm(__classid(TFormAbout), &FormAbout);
                 Application->Run();
        }
        catch (Exception &exception)
        {
                 Application->ShowException(&exception);
        }
        return 0;
}
//---------------------------------------------------------------------------
