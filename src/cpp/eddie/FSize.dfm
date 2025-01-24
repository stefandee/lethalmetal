object FormSize: TFormSize
  Left = 918
  Top = 279
  BorderIcons = []
  BorderStyle = bsSingle
  Caption = 'Select Map Size'
  ClientHeight = 117
  ClientWidth = 212
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 16
    Top = 28
    Width = 52
    Height = 13
    Caption = 'Map Width'
  end
  object Label2: TLabel
    Left = 16
    Top = 52
    Width = 55
    Height = 13
    Caption = 'Map Height'
  end
  object edWidth: TEdit
    Left = 100
    Top = 20
    Width = 97
    Height = 21
    TabOrder = 0
    Text = '25'
    OnExit = edWidthExit
  end
  object edHeight: TEdit
    Left = 100
    Top = 48
    Width = 97
    Height = 21
    TabOrder = 1
    Text = '25'
    OnExit = edHeightExit
  end
  object bOk: TButton
    Left = 16
    Top = 84
    Width = 75
    Height = 25
    Caption = '&Ok'
    ModalResult = 1
    TabOrder = 2
    OnClick = bOkClick
  end
  object bCancel: TButton
    Left = 124
    Top = 84
    Width = 75
    Height = 25
    Caption = '&Cancel'
    ModalResult = 2
    TabOrder = 3
  end
end
