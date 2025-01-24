object Main: TMain
  Left = 305
  Top = 262
  Width = 640
  Height = 480
  Caption = 'Map Editor'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  Position = poDesktopCenter
  OnClose = FormClose
  OnResize = FormResize
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object paintLevel: TPaintBox
    Left = 0
    Top = 0
    Width = 614
    Height = 397
    Align = alClient
    OnMouseDown = paintLevelMouseDown
    OnMouseMove = paintLevelMouseMove
    OnMouseUp = paintLevelMouseUp
    OnPaint = paintLevelPaint
  end
  object StatusBar1: TStatusBar
    Left = 0
    Top = 415
    Width = 632
    Height = 19
    Panels = <>
    SimplePanel = False
  end
  object scrollH: TScrollBar
    Left = 0
    Top = 397
    Width = 632
    Height = 18
    Align = alBottom
    PageSize = 0
    TabOrder = 1
    OnScroll = scrollHScroll
  end
  object scrollV: TScrollBar
    Left = 614
    Top = 0
    Width = 18
    Height = 397
    Align = alRight
    Kind = sbVertical
    PageSize = 0
    TabOrder = 2
    OnScroll = scrollVScroll
  end
  object MainMenu1: TMainMenu
    Left = 4
    Top = 4
    object File1: TMenuItem
      Caption = '&File'
      object New1: TMenuItem
        Caption = '&New'
        OnClick = New1Click
      end
      object Load1: TMenuItem
        Caption = '&Load...'
        OnClick = Load1Click
      end
      object Save1: TMenuItem
        Caption = '&Save'
        OnClick = Save1Click
      end
      object Saveas1: TMenuItem
        Caption = '&Save as...'
        OnClick = Saveas1Click
      end
      object Close1: TMenuItem
        Caption = '&Close'
        OnClick = Close1Click
      end
      object N7: TMenuItem
        Caption = '-'
      end
      object N2: TMenuItem
        Caption = '-'
      end
      object Exit1: TMenuItem
        Caption = 'E&xit'
        OnClick = Exit1Click
      end
    end
    object Edit1: TMenuItem
      Caption = '&Edit'
      object PasteMapPart1: TMenuItem
        Caption = '&Paste Map'
        ShortCut = 16470
        OnClick = PasteMapPart1Click
      end
      object N4: TMenuItem
        Caption = '-'
      end
      object MakeClean1: TMenuItem
        Caption = '&Clean Map'
        OnClick = MakeClean1Click
      end
      object N5: TMenuItem
        Caption = '-'
      end
      object LoadArchitecturetoClipboard1: TMenuItem
        Caption = '&Load Architecture to Clipboard...'
        OnClick = LoadArchitecturetoClipboard1Click
      end
      object SaveArchitecturefromClipboard1: TMenuItem
        Caption = 'Save Architecture from Clipboard...'
        OnClick = SaveArchitecturefromClipboard1Click
      end
      object N6: TMenuItem
        Caption = '-'
      end
      object Undo1: TMenuItem
        Caption = 'Undo'
        ShortCut = 16474
        OnClick = Undo1Click
      end
      object Redo1: TMenuItem
        Caption = 'Redo'
        ShortCut = 16472
        OnClick = Redo1Click
      end
    end
    object Layers1: TMenuItem
      Caption = '&Layers'
      object Layereditor1: TMenuItem
        Caption = '&Layer editor...'
      end
      object N3: TMenuItem
        Caption = '-'
      end
    end
    object Tools1: TMenuItem
      Caption = '&Tools'
      object ZoneEditor1: TMenuItem
        Caption = '&Tile Editor...'
        OnClick = ZoneEditor1Click
      end
      object N8: TMenuItem
        Caption = '-'
      end
      object Verify1: TMenuItem
        Caption = '&Verify'
        ShortCut = 120
        OnClick = Verify1Click
      end
    end
    object Views1: TMenuItem
      Caption = '&Views'
      object ShowGrid1: TMenuItem
        Caption = 'Show &Grid'
        Checked = True
        ShortCut = 16455
        OnClick = ShowGrid1Click
      end
      object ShowRulers1: TMenuItem
        Caption = 'Show &Rulers'
        Checked = True
        ShortCut = 16466
        OnClick = ShowRulers1Click
      end
    end
    object Help1: TMenuItem
      Caption = '&Help'
      object Help2: TMenuItem
        Caption = 'Help'
      end
      object N1: TMenuItem
        Caption = '-'
      end
      object About1: TMenuItem
        Caption = '&About...'
        OnClick = About1Click
      end
    end
  end
  object dlgSave: TSaveDialog
    DefaultExt = 'map'
    Filter = 'Map files|*.map|All files|*.*'
    InitialDir = '.\data'
    Options = [ofOverwritePrompt, ofHideReadOnly, ofEnableSizing]
    Title = 'Save Map'
    Left = 36
    Top = 4
  end
  object dlgLoad: TOpenDialog
    DefaultExt = 'map'
    Filter = 'Eddie Maps|*.map|All Files|*.*'
    InitialDir = '.\data'
    Options = [ofHideReadOnly, ofNoChangeDir, ofPathMustExist, ofFileMustExist, ofEnableSizing]
    Title = 'Load map'
    Left = 68
    Top = 4
  end
  object dlgLoadArch: TOpenDialog
    DefaultExt = 'arch'
    Filter = 'Impalator Architecture Files|*.arch|All Files|*.*'
    InitialDir = '.\data'
    Options = [ofHideReadOnly, ofNoChangeDir, ofPathMustExist, ofFileMustExist, ofEnableSizing]
    Title = 'Load Architecture'
    Left = 40
    Top = 40
  end
  object dlgSaveArch: TSaveDialog
    DefaultExt = 'arch'
    Filter = 'Impalator Architecture Files|*.arch|All Files|*.*'
    InitialDir = '.\data'
    Options = [ofOverwritePrompt, ofHideReadOnly, ofEnableSizing]
    Title = 'Save Architecture'
    Left = 72
    Top = 40
  end
end
