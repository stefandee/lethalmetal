object FormAbout: TFormAbout
  Left = 318
  Top = 278
  BorderIcons = [biSystemMenu]
  BorderStyle = bsToolWindow
  Caption = 'About Eddie'
  ClientHeight = 207
  ClientWidth = 283
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poDesktopCenter
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 8
    Top = 8
    Width = 73
    Height = 33
    BiDiMode = bdLeftToRight
    Caption = 'Eddie'
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -24
    Font.Name = 'Arial Black'
    Font.Style = []
    ParentBiDiMode = False
    ParentFont = False
  end
  object Label2: TLabel
    Left = 8
    Top = 48
    Width = 53
    Height = 13
    Caption = 'Version 1.0'
  end
  object Label3: TLabel
    Left = 8
    Top = 64
    Width = 163
    Height = 13
    Caption = '(c) Stefan "Karg" Dicu, 2003-2004'
  end
  object Bevel1: TBevel
    Left = 8
    Top = 120
    Width = 265
    Height = 9
    Shape = bsTopLine
  end
  object Label4: TLabel
    Left = 8
    Top = 128
    Width = 126
    Height = 13
    Caption = 'Physical memory available:'
  end
  object labelMemPhys: TLabel
    Left = 216
    Top = 128
    Width = 68
    Height = 13
    Caption = 'labelMemPhys'
  end
  object Label6: TLabel
    Left = 8
    Top = 144
    Width = 63
    Height = 13
    Caption = 'Free memory:'
  end
  object labelMemFree: TLabel
    Left = 216
    Top = 144
    Width = 66
    Height = 13
    Caption = 'labelMemFree'
  end
  object Label5: TLabel
    Left = 8
    Top = 80
    Width = 128
    Height = 13
    Caption = 'http://aspc.cs.utt.ro/~karg'
  end
  object Label7: TLabel
    Left = 8
    Top = 96
    Width = 160
    Height = 13
    Caption = 'http://www.robotsonbicycles.com'
  end
  object Button1: TButton
    Left = 112
    Top = 176
    Width = 75
    Height = 25
    Caption = '&Ok'
    ModalResult = 1
    TabOrder = 0
  end
end
