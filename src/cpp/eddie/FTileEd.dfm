object FormTileEd: TFormTileEd
  Left = 349
  Top = 390
  Width = 600
  Height = 402
  Caption = 'Tile Editor'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnDestroy = FormDestroy
  OnHide = FormHide
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object gbOptions: TGroupBox
    Left = 360
    Top = 0
    Width = 232
    Height = 334
    Align = alRight
    Caption = 'Setup'
    TabOrder = 0
    object Label1: TLabel
      Left = 12
      Top = 32
      Width = 25
      Height = 13
      Caption = 'Tiles:'
    end
    object GroupBox1: TGroupBox
      Left = 2
      Top = 15
      Width = 228
      Height = 106
      Align = alTop
      Caption = 'Tiles'
      TabOrder = 0
      object cbTiles: TComboBox
        Left = 8
        Top = 16
        Width = 213
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 0
        OnChange = cbTilesChange
        OnEnter = cbTilesEnter
      end
      object btnAdd: TButton
        Left = 8
        Top = 44
        Width = 69
        Height = 25
        Caption = '&Add tile'
        TabOrder = 1
        OnClick = btnAddClick
      end
      object btnDel: TButton
        Left = 82
        Top = 44
        Width = 67
        Height = 25
        Caption = '&Delete Tile'
        TabOrder = 2
        OnClick = btnDelClick
      end
      object btnRename: TButton
        Left = 82
        Top = 72
        Width = 67
        Height = 25
        Caption = '&Rename'
        TabOrder = 3
        OnClick = btnRenameClick
      end
      object bntClearAll: TButton
        Left = 8
        Top = 72
        Width = 69
        Height = 25
        Caption = '&Clear All'
        TabOrder = 4
        OnClick = bntClearAllClick
      end
      object btn1x1: TButton
        Left = 154
        Top = 44
        Width = 67
        Height = 25
        Caption = '1&x1'
        TabOrder = 5
        OnClick = btn1x1Click
      end
    end
    object GroupBox2: TGroupBox
      Left = 2
      Top = 237
      Width = 228
      Height = 64
      Align = alTop
      Caption = 'Options'
      TabOrder = 1
      object Label2: TLabel
        Left = 116
        Top = 24
        Width = 7
        Height = 13
        Caption = 'X'
      end
      object shapeGridColor: TShape
        Left = 164
        Top = 20
        Width = 16
        Height = 16
        Hint = 'Grid color'
        Brush.Color = clBlack
        OnMouseUp = shapeGridColorMouseUp
      end
      object shapeSelColor: TShape
        Left = 84
        Top = 40
        Width = 16
        Height = 16
        Hint = 'Selected'
        Brush.Color = clRed
        OnMouseUp = shapeSelColorMouseUp
      end
      object shapeUnselColor: TShape
        Left = 104
        Top = 40
        Width = 16
        Height = 16
        Hint = 'Unselected'
        OnMouseUp = shapeUnselColorMouseUp
      end
      object cbDrawAll: TCheckBox
        Left = 8
        Top = 40
        Width = 69
        Height = 17
        Caption = 'Draw all'
        TabOrder = 0
        OnClick = cbDrawAllClick
      end
      object cbDrawGrid: TCheckBox
        Left = 8
        Top = 20
        Width = 73
        Height = 17
        Caption = 'Draw grid'
        TabOrder = 1
        OnClick = cbDrawGridClick
      end
      object edGridW: TEdit
        Left = 84
        Top = 16
        Width = 29
        Height = 21
        TabOrder = 2
        Text = '16'
        OnExit = edGridWExit
      end
      object edGridH: TEdit
        Left = 128
        Top = 16
        Width = 29
        Height = 21
        TabOrder = 3
        Text = '16'
        OnExit = edGridHExit
      end
    end
    object GroupBox3: TGroupBox
      Left = 2
      Top = 121
      Width = 228
      Height = 116
      Align = alTop
      Caption = 'Collision'
      TabOrder = 2
      object checkUp: TCheckBox
        Left = 84
        Top = 16
        Width = 37
        Height = 17
        Caption = 'Up'
        TabOrder = 0
      end
      object checkDown: TCheckBox
        Left = 84
        Top = 72
        Width = 49
        Height = 17
        Caption = 'Down'
        TabOrder = 1
      end
      object checkLeft: TCheckBox
        Left = 24
        Top = 44
        Width = 49
        Height = 17
        Alignment = taLeftJustify
        Caption = 'Left'
        TabOrder = 2
      end
      object checkRight: TCheckBox
        Left = 108
        Top = 44
        Width = 49
        Height = 17
        Caption = 'Right'
        TabOrder = 3
      end
      object checkLadder: TCheckBox
        Left = 8
        Top = 92
        Width = 97
        Height = 17
        Caption = 'Ladder'
        TabOrder = 4
      end
      object checkHeadLadder: TCheckBox
        Left = 120
        Top = 92
        Width = 97
        Height = 17
        Caption = 'Ladder Head'
        TabOrder = 5
      end
    end
    object GroupBox4: TGroupBox
      Left = 2
      Top = 301
      Width = 228
      Height = 31
      Align = alClient
      Caption = 'Zoom'
      TabOrder = 3
      object trackZoom: TTrackBar
        Left = 8
        Top = 12
        Width = 205
        Height = 33
        Max = 16
        Min = 1
        Orientation = trHorizontal
        Frequency = 1
        Position = 1
        SelEnd = 0
        SelStart = 0
        TabOrder = 0
        TickMarks = tmBottomRight
        TickStyle = tsAuto
        OnChange = trackZoomChange
      end
    end
  end
  object gbTile: TGroupBox
    Left = 0
    Top = 0
    Width = 360
    Height = 334
    Align = alClient
    Caption = 'Big tile'
    TabOrder = 1
    object paintTile: TPaintBox
      Left = 2
      Top = 15
      Width = 338
      Height = 299
      Align = alClient
      OnMouseDown = paintTileMouseDown
      OnMouseMove = paintTileMouseMove
      OnMouseUp = paintTileMouseUp
      OnPaint = paintTilePaint
    end
    object sbH: TScrollBar
      Left = 2
      Top = 314
      Width = 356
      Height = 18
      Align = alBottom
      PageSize = 0
      TabOrder = 0
      OnChange = sbHChange
    end
    object sbV: TScrollBar
      Left = 340
      Top = 15
      Width = 18
      Height = 299
      Align = alRight
      Kind = sbVertical
      PageSize = 0
      TabOrder = 1
      OnChange = sbVChange
    end
  end
  object statusTile: TStatusBar
    Left = 0
    Top = 334
    Width = 592
    Height = 19
    Panels = <>
    SimplePanel = False
  end
  object dlgColor: TColorDialog
    Ctl3D = True
    Left = 553
    Top = 269
  end
  object MainMenu1: TMainMenu
    Left = 16
    Top = 24
    object File1: TMenuItem
      Caption = 'File'
      object Export1x1tilesetdata1: TMenuItem
        Caption = 'Export 1x1 tileset data (engine before 31.01.2004)...'
        OnClick = Export1x1tilesetdata1Click
      end
      object Export16bittilesetenginev310120041: TMenuItem
        Caption = 'Export 16bit tileset (engine after 31.01.2004)...'
        OnClick = Export16bittilesetenginev310120041Click
      end
    end
  end
  object dlgSave: TSaveDialog
    Filter = 
      'Tileset Binary Data|*.dat|Full tileset binary (16bit)|*.dat16|Al' +
      'l Files|*.*'
    InitialDir = '.\data'
    Options = [ofHideReadOnly, ofNoChangeDir, ofPathMustExist, ofEnableSizing]
    Left = 48
    Top = 24
  end
end
