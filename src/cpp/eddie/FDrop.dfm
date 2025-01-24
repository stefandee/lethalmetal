object FormDrop: TFormDrop
  Left = 520
  Top = 316
  BorderStyle = bsToolWindow
  Caption = 'Drop'
  ClientHeight = 130
  ClientWidth = 217
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
  object rgDropX: TRadioGroup
    Left = 0
    Top = 0
    Width = 217
    Height = 65
    Align = alTop
    Caption = 'On X'
    ItemIndex = 1
    Items.Strings = (
      'Left'
      'Right')
    TabOrder = 0
  end
  object rgDropY: TRadioGroup
    Left = 0
    Top = 65
    Width = 217
    Height = 65
    Align = alClient
    Caption = 'On Y'
    ItemIndex = 1
    Items.Strings = (
      'Top'
      'Bottom')
    TabOrder = 1
  end
end
