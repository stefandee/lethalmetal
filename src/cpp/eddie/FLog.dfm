object FormLog: TFormLog
  Left = 617
  Top = 364
  Width = 407
  Height = 281
  BorderIcons = []
  Caption = 'Log'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  FormStyle = fsStayOnTop
  OldCreateOrder = False
  Visible = True
  PixelsPerInch = 96
  TextHeight = 13
  object memoLog: TMemo
    Left = 0
    Top = 0
    Width = 399
    Height = 251
    Align = alClient
    Color = clBlack
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clLime
    Font.Height = -11
    Font.Name = 'Fixedsys'
    Font.Style = []
    ParentFont = False
    PopupMenu = PopupMenu1
    ReadOnly = True
    ScrollBars = ssVertical
    TabOrder = 0
  end
  object PopupMenu1: TPopupMenu
    Left = 360
    Top = 8
    object Clear1: TMenuItem
      Caption = '&Clear'
      OnClick = Clear1Click
    end
  end
end
