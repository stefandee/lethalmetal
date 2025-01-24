object FormItemEdit: TFormItemEdit
  Left = 549
  Top = 298
  BorderIcons = []
  BorderStyle = bsSingle
  Caption = 'Item Editor'
  ClientHeight = 390
  ClientWidth = 359
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnShow = FormShow
  DesignSize = (
    359
    390)
  PixelsPerInch = 96
  TextHeight = 13
  object GroupBox1: TGroupBox
    Left = 0
    Top = 0
    Width = 359
    Height = 249
    Align = alTop
    Caption = 'Items'
    TabOrder = 0
    object PageControl1: TPageControl
      Left = 2
      Top = 15
      Width = 355
      Height = 232
      ActivePage = TabSheet8
      Align = alClient
      TabIndex = 7
      TabOrder = 0
      object TabSheet1: TTabSheet
        Caption = 'Health'
        object Label1: TLabel
          Left = 4
          Top = 16
          Width = 34
          Height = 13
          Caption = 'Health:'
        end
        object edHealth: TEdit
          Left = 84
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '1'
        end
      end
      object TabSheet2: TTabSheet
        Caption = 'Player'
        ImageIndex = 1
        object Label4: TLabel
          Left = 4
          Top = 40
          Width = 34
          Height = 13
          Caption = 'Health:'
        end
        object Label5: TLabel
          Left = 4
          Top = 112
          Width = 35
          Height = 13
          Caption = 'Money:'
        end
        object Label7: TLabel
          Left = 4
          Top = 64
          Width = 77
          Height = 13
          Caption = 'Weapon Power:'
        end
        object Label17: TLabel
          Left = 4
          Top = 88
          Width = 93
          Height = 13
          Caption = 'Recovery time (ms):'
        end
        object Label18: TLabel
          Left = 4
          Top = 16
          Width = 37
          Height = 13
          Caption = 'Stance:'
        end
        object edPHealth: TEdit
          Left = 108
          Top = 32
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '3'
        end
        object edPMoney: TEdit
          Left = 108
          Top = 104
          Width = 121
          Height = 21
          TabOrder = 1
          Text = '0'
        end
        object edPWeapon: TEdit
          Left = 108
          Top = 56
          Width = 121
          Height = 21
          TabOrder = 2
          Text = '1'
        end
        object edPRec: TEdit
          Left = 108
          Top = 80
          Width = 121
          Height = 21
          TabOrder = 3
          Text = '3'
        end
        object cbPStance: TComboBox
          Left = 108
          Top = 8
          Width = 121
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 4
          Items.Strings = (
            'Left'
            'Right')
        end
      end
      object TabSheet3: TTabSheet
        Caption = 'Slasher'
        ImageIndex = 2
        object Label9: TLabel
          Left = 4
          Top = 40
          Width = 34
          Height = 13
          Caption = 'Health:'
        end
        object Label10: TLabel
          Left = 4
          Top = 64
          Width = 77
          Height = 13
          Caption = 'Weapon Power:'
        end
        object Label11: TLabel
          Left = 4
          Top = 136
          Width = 54
          Height = 13
          Caption = 'Aggresivity:'
        end
        object Label19: TLabel
          Left = 4
          Top = 16
          Width = 37
          Height = 13
          Caption = 'Stance:'
        end
        object Label22: TLabel
          Left = 4
          Top = 88
          Width = 73
          Height = 13
          Caption = 'Patrol distance:'
        end
        object Label43: TLabel
          Left = 4
          Top = 112
          Width = 27
          Height = 13
          Caption = 'Color:'
        end
        object edSlHealth: TEdit
          Left = 100
          Top = 32
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '3'
        end
        object edSlWeapon: TEdit
          Left = 100
          Top = 56
          Width = 121
          Height = 21
          TabOrder = 1
          Text = '3'
        end
        object cbSlAggresivity: TComboBox
          Left = 100
          Top = 128
          Width = 121
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 2
          Items.Strings = (
            'Low'
            'Medium'
            'High')
        end
        object cbSlStance: TComboBox
          Left = 100
          Top = 8
          Width = 121
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 3
          Items.Strings = (
            'Left'
            'Right')
        end
        object edSlDist: TEdit
          Left = 100
          Top = 80
          Width = 121
          Height = 21
          TabOrder = 4
          Text = '3'
        end
        object checkSlFriendly: TCheckBox
          Left = 4
          Top = 156
          Width = 97
          Height = 17
          Caption = 'Friendly'
          TabOrder = 5
        end
        object cbSlColor: TColorBox
          Left = 100
          Top = 104
          Width = 121
          Height = 22
          DefaultColorColor = clRed
          NoneColorColor = clRed
          Selected = clRed
          Style = [cbStandardColors, cbExtendedColors, cbSystemColors, cbPrettyNames]
          ItemHeight = 16
          TabOrder = 6
        end
      end
      object TabSheet4: TTabSheet
        Caption = 'Shooter'
        ImageIndex = 3
        object Label12: TLabel
          Left = 4
          Top = 40
          Width = 34
          Height = 13
          Caption = 'Health:'
        end
        object Label13: TLabel
          Left = 4
          Top = 64
          Width = 77
          Height = 13
          Caption = 'Weapon Power:'
        end
        object Label14: TLabel
          Left = 4
          Top = 184
          Width = 54
          Height = 13
          Caption = 'Aggresivity:'
        end
        object Label15: TLabel
          Left = 4
          Top = 136
          Width = 90
          Height = 13
          Caption = 'Recovery time(ms):'
        end
        object Label16: TLabel
          Left = 4
          Top = 160
          Width = 63
          Height = 13
          Caption = 'Joy time (ms):'
        end
        object Label20: TLabel
          Left = 4
          Top = 16
          Width = 37
          Height = 13
          Caption = 'Stance:'
        end
        object Label23: TLabel
          Left = 4
          Top = 88
          Width = 73
          Height = 13
          Caption = 'Patrol distance:'
        end
        object Label45: TLabel
          Left = 4
          Top = 112
          Width = 58
          Height = 13
          Caption = 'Bullets drop:'
        end
        object edShHealth: TEdit
          Left = 104
          Top = 32
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '3'
        end
        object edShWeapon: TEdit
          Left = 104
          Top = 56
          Width = 121
          Height = 21
          TabOrder = 1
          Text = '3'
        end
        object cbShAggresivity: TComboBox
          Left = 104
          Top = 176
          Width = 121
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 2
          Items.Strings = (
            'Low'
            'Medium'
            'High')
        end
        object edShRec: TEdit
          Left = 104
          Top = 128
          Width = 121
          Height = 21
          TabOrder = 3
          Text = '100'
        end
        object edShJoy: TEdit
          Left = 104
          Top = 152
          Width = 121
          Height = 21
          TabOrder = 4
          Text = '1000'
        end
        object cbShStance: TComboBox
          Left = 104
          Top = 8
          Width = 121
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 5
          Items.Strings = (
            'Left'
            'Right')
        end
        object edShDist: TEdit
          Left = 104
          Top = 80
          Width = 121
          Height = 21
          TabOrder = 6
          Text = '3'
        end
        object edShBulletsDrop: TEdit
          Left = 104
          Top = 104
          Width = 121
          Height = 21
          Hint = '0 (will no drop any bullets)'
          TabOrder = 7
          Text = '3'
        end
      end
      object TabSheet5: TTabSheet
        Caption = 'Trigger Level'
        ImageIndex = 4
        object Label21: TLabel
          Left = 4
          Top = 12
          Width = 54
          Height = 13
          Caption = 'Next Level:'
        end
        object edTriggerLevel: TEdit
          Left = 84
          Top = 8
          Width = 121
          Height = 21
          Hint = 'Use 127 to end the game'
          ParentShowHint = False
          ShowHint = True
          TabOrder = 0
        end
      end
      object TabSheet6: TTabSheet
        Caption = 'Elevator'
        ImageIndex = 5
        object Label25: TLabel
          Left = 4
          Top = 16
          Width = 53
          Height = 13
          Caption = 'Elevator: id'
        end
        object Label26: TLabel
          Left = 116
          Top = 16
          Width = 37
          Height = 13
          Caption = 'moving '
        end
        object Label27: TLabel
          Left = 224
          Top = 16
          Width = 15
          Height = 13
          Caption = 'for '
        end
        object Label28: TLabel
          Left = 300
          Top = 16
          Width = 21
          Height = 13
          Caption = 'cells'
        end
        object Label32: TLabel
          Left = 348
          Top = 64
          Width = 21
          Height = 13
          Caption = 'cells'
        end
        object edElevatorId: TEdit
          Left = 64
          Top = 8
          Width = 45
          Height = 21
          TabOrder = 0
          Text = '0'
        end
        object cbElevatorDir: TComboBox
          Left = 164
          Top = 8
          Width = 53
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 1
          Items.Strings = (
            'UP'
            'DOWN'
            'LEFT'
            'RIGHT')
        end
        object edElevatorDist: TEdit
          Left = 248
          Top = 8
          Width = 45
          Height = 21
          TabOrder = 2
          Text = '0'
        end
      end
      object TabSheet7: TTabSheet
        Caption = 'Robot shooter'
        ImageIndex = 6
        object Label36: TLabel
          Left = 4
          Top = 16
          Width = 37
          Height = 13
          Caption = 'Stance:'
        end
        object Label37: TLabel
          Left = 4
          Top = 40
          Width = 34
          Height = 13
          Caption = 'Health:'
        end
        object Label38: TLabel
          Left = 4
          Top = 64
          Width = 77
          Height = 13
          Caption = 'Weapon Power:'
        end
        object Label39: TLabel
          Left = 4
          Top = 88
          Width = 73
          Height = 13
          Caption = 'Patrol distance:'
        end
        object Label40: TLabel
          Left = 4
          Top = 112
          Width = 90
          Height = 13
          Caption = 'Recovery time(ms):'
        end
        object cbRShStance: TComboBox
          Left = 104
          Top = 8
          Width = 121
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 0
          Items.Strings = (
            'Left'
            'Right')
        end
        object edRShHealth: TEdit
          Left = 104
          Top = 32
          Width = 121
          Height = 21
          TabOrder = 1
          Text = '3'
        end
        object edRShWeapon: TEdit
          Left = 104
          Top = 56
          Width = 121
          Height = 21
          TabOrder = 2
          Text = '3'
        end
        object edRShDist: TEdit
          Left = 104
          Top = 80
          Width = 121
          Height = 21
          TabOrder = 3
          Text = '3'
        end
        object edRShRec: TEdit
          Left = 104
          Top = 104
          Width = 121
          Height = 21
          TabOrder = 4
          Text = '100'
        end
      end
      object TabSheet8: TTabSheet
        Caption = 'Props'
        ImageIndex = 7
        object Label41: TLabel
          Left = 4
          Top = 16
          Width = 57
          Height = 13
          Caption = 'File number:'
        end
        object Label42: TLabel
          Left = 4
          Top = 40
          Width = 91
          Height = 13
          Caption = 'Time delay (frames)'
        end
        object edPFile: TEdit
          Left = 104
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '1'
        end
        object edPTime: TEdit
          Left = 104
          Top = 32
          Width = 121
          Height = 21
          TabOrder = 1
          Text = '3'
        end
        object cbPAutoTerminate: TCheckBox
          Left = 4
          Top = 60
          Width = 113
          Height = 17
          Alignment = taLeftJustify
          Caption = 'Auto terminate'
          TabOrder = 2
        end
      end
      object TabSheet9: TTabSheet
        Caption = 'Trigger Text'
        ImageIndex = 8
        object Label8: TLabel
          Left = 4
          Top = 16
          Width = 24
          Height = 13
          Caption = 'Text:'
        end
        object edTriggerText: TEdit
          Left = 84
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = 'No text'
        end
      end
      object TabSheet10: TTabSheet
        Caption = 'Button'
        ImageIndex = 9
        object Label24: TLabel
          Left = 4
          Top = 16
          Width = 114
          Height = 13
          Caption = 'linked to elevator with id'
        end
        object cbButtonElevator: TComboBox
          Left = 124
          Top = 8
          Width = 53
          Height = 21
          ItemHeight = 13
          TabOrder = 0
          Text = '0'
        end
      end
      object TabSheet11: TTabSheet
        Caption = 'Auto Elevator'
        ImageIndex = 10
        object Label29: TLabel
          Left = 4
          Top = 16
          Width = 91
          Height = 13
          Caption = 'Automatic elevator:'
        end
        object Label30: TLabel
          Left = 100
          Top = 16
          Width = 37
          Height = 13
          Caption = 'moving '
        end
        object Label31: TLabel
          Left = 208
          Top = 16
          Width = 15
          Height = 13
          Caption = 'for '
        end
        object Label33: TLabel
          Left = 284
          Top = 16
          Width = 3
          Height = 13
          Caption = '-'
        end
        object Label34: TLabel
          Left = 100
          Top = 40
          Width = 77
          Height = 13
          Caption = 'with waiting time'
        end
        object Label35: TLabel
          Left = 236
          Top = 40
          Width = 50
          Height = 13
          Caption = 'and speed'
        end
        object cbAElevatorDir: TComboBox
          Left = 148
          Top = 8
          Width = 53
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          TabOrder = 0
          Items.Strings = (
            'UP'
            'DOWN'
            'LEFT'
            'RIGHT')
        end
        object edAElevatorDist1: TEdit
          Left = 232
          Top = 8
          Width = 45
          Height = 21
          TabOrder = 1
          Text = '0'
        end
        object edAElevatorDist2: TEdit
          Left = 296
          Top = 8
          Width = 45
          Height = 21
          TabOrder = 2
          Text = '0'
        end
        object edAElevatorWait: TEdit
          Left = 184
          Top = 32
          Width = 45
          Height = 21
          TabOrder = 3
          Text = '0'
        end
        object edAElevatorSpeed: TEdit
          Left = 296
          Top = 32
          Width = 45
          Height = 21
          TabOrder = 4
          Text = '0'
        end
      end
      object TabSheet12: TTabSheet
        Caption = 'Mine'
        ImageIndex = 11
        object Label3: TLabel
          Left = 4
          Top = 16
          Width = 43
          Height = 13
          Caption = 'Damage:'
        end
        object edMine: TEdit
          Left = 60
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '5'
        end
      end
      object TabSheet13: TTabSheet
        Caption = 'Ammo'
        ImageIndex = 12
        object Label44: TLabel
          Left = 4
          Top = 16
          Width = 32
          Height = 13
          Caption = 'Ammo:'
        end
        object edAmmo: TEdit
          Left = 84
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '1'
        end
      end
      object TabSheet14: TTabSheet
        Caption = 'Weapon'
        ImageIndex = 13
        object Label2: TLabel
          Left = 4
          Top = 16
          Width = 44
          Height = 13
          Caption = 'Weapon:'
        end
        object edWeapon: TEdit
          Left = 84
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '1'
        end
      end
      object TabSheet15: TTabSheet
        Caption = 'Money'
        ImageIndex = 14
        object Label6: TLabel
          Left = 4
          Top = 16
          Width = 39
          Height = 13
          Caption = 'Amount:'
        end
        object edMoney: TEdit
          Left = 52
          Top = 8
          Width = 121
          Height = 21
          TabOrder = 0
          Text = '5'
        end
      end
    end
  end
  object GroupBox2: TGroupBox
    Left = 0
    Top = 249
    Width = 359
    Height = 105
    Align = alTop
    Caption = 'Position'
    TabOrder = 1
    object LeftRight: TUpDown
      Left = 36
      Top = 24
      Width = 49
      Height = 25
      Min = 0
      Max = 1000
      Orientation = udHorizontal
      Position = 0
      TabOrder = 0
      Wrap = False
      OnChangingEx = LeftRightChangingEx
    end
    object UpDown: TUpDown
      Left = 8
      Top = 24
      Width = 25
      Height = 49
      Min = 0
      Max = 1000
      Position = 0
      TabOrder = 1
      Wrap = False
      OnChangingEx = UpDownChangingEx
    end
  end
  object Button1: TButton
    Left = 197
    Top = 362
    Width = 75
    Height = 25
    Anchors = [akRight, akBottom]
    Caption = '&Ok'
    ModalResult = 1
    TabOrder = 2
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 277
    Top = 362
    Width = 75
    Height = 25
    Anchors = [akRight, akBottom]
    Caption = '&Cancel'
    ModalResult = 2
    TabOrder = 3
    OnClick = Button2Click
  end
end
