object FormTool: TFormTool
  Left = 621
  Top = 277
  Width = 153
  Height = 391
  BorderIcons = []
  BorderStyle = bsSizeToolWin
  Caption = 'Tools'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  FormStyle = fsStayOnTop
  OldCreateOrder = False
  Visible = True
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object PageControl1: TPageControl
    Left = 0
    Top = 0
    Width = 145
    Height = 361
    ActivePage = tabEnemy
    Align = alClient
    MultiLine = True
    PopupMenu = pupTools
    TabIndex = 1
    TabOrder = 0
    TabPosition = tpRight
    OnChange = PageControl1Change
    object tabTiles: TTabSheet
      Caption = 'Tiles'
      object DrawGrid1: TDrawGrid
        Left = 0
        Top = 0
        Width = 118
        Height = 353
        Align = alClient
        BorderStyle = bsNone
        ColCount = 1
        DefaultRowHeight = 64
        FixedCols = 0
        RowCount = 100
        FixedRows = 0
        GridLineWidth = 0
        Options = [goDrawFocusSelected, goRowSizing, goColSizing]
        TabOrder = 0
        OnDrawCell = DrawGrid1DrawCell
      end
    end
    object tabEnemy: TTabSheet
      Caption = 'Enemy'
      ImageIndex = 1
      object DrawGrid5: TDrawGrid
        Left = 0
        Top = 0
        Width = 118
        Height = 353
        Align = alClient
        BorderStyle = bsNone
        ColCount = 1
        DefaultRowHeight = 64
        FixedCols = 0
        FixedRows = 0
        GridLineWidth = 0
        Options = [goDrawFocusSelected, goRowSizing, goColSizing]
        TabOrder = 0
        OnClick = DrawGrid5Click
        OnDrawCell = DrawGrid5DrawCell
      end
    end
    object tabItems: TTabSheet
      Caption = 'Items'
      ImageIndex = 2
      object DrawGrid3: TDrawGrid
        Left = 0
        Top = 0
        Width = 118
        Height = 353
        Align = alClient
        BorderStyle = bsNone
        ColCount = 1
        DefaultRowHeight = 64
        FixedCols = 0
        FixedRows = 0
        GridLineWidth = 0
        Options = [goDrawFocusSelected, goRowSizing, goColSizing]
        TabOrder = 0
        OnDrawCell = DrawGrid3DrawCell
      end
    end
    object tabMisc: TTabSheet
      Caption = 'Misc'
      ImageIndex = 3
      object DrawGrid2: TDrawGrid
        Left = 0
        Top = 0
        Width = 118
        Height = 353
        Align = alClient
        BorderStyle = bsNone
        ColCount = 1
        DefaultRowHeight = 64
        FixedCols = 0
        FixedRows = 0
        GridLineWidth = 0
        Options = [goDrawFocusSelected, goRowSizing, goColSizing]
        TabOrder = 0
        OnDrawCell = DrawGrid2DrawCell
      end
    end
    object tabTriggers: TTabSheet
      Caption = 'Triggers'
      ImageIndex = 4
      object DrawGrid4: TDrawGrid
        Left = 0
        Top = 0
        Width = 118
        Height = 353
        Align = alClient
        BorderStyle = bsNone
        ColCount = 1
        DefaultRowHeight = 64
        FixedCols = 0
        FixedRows = 0
        GridLineWidth = 0
        Options = [goDrawFocusSelected, goRowSizing, goColSizing]
        TabOrder = 0
        OnDrawCell = DrawGrid4DrawCell
      end
    end
    object tabElevators: TTabSheet
      Caption = 'Buttons/Elevators'
      ImageIndex = 5
      object DrawGrid6: TDrawGrid
        Left = 0
        Top = 0
        Width = 118
        Height = 353
        Align = alClient
        BorderStyle = bsNone
        ColCount = 1
        DefaultRowHeight = 64
        FixedCols = 0
        FixedRows = 0
        GridLineWidth = 0
        Options = [goDrawFocusSelected, goRowSizing, goColSizing]
        TabOrder = 0
        OnDrawCell = DrawGrid6DrawCell
      end
    end
  end
  object pupTools: TPopupMenu
    Left = 8
    Top = 326
    object Edit1: TMenuItem
      Caption = '&Parameters...'
      OnClick = Edit1Click
    end
  end
end
