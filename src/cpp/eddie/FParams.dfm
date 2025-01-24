object FormParams: TFormParams
  Left = 422
  Top = 610
  Width = 399
  Height = 260
  BorderIcons = []
  BorderStyle = bsSizeToolWin
  Caption = 'Tool Parameters'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  FormStyle = fsStayOnTop
  OldCreateOrder = False
  Visible = True
  OnDeactivate = FormDeactivate
  PixelsPerInch = 96
  TextHeight = 13
  object PageControl1: TPageControl
    Left = 0
    Top = 0
    Width = 391
    Height = 230
    ActivePage = TabSheet8
    Align = alClient
    TabIndex = 7
    TabOrder = 0
    object TabSheet1: TTabSheet
      Caption = 'Items'
      object Label1: TLabel
        Left = 4
        Top = 16
        Width = 34
        Height = 13
        Caption = 'Health:'
      end
      object Label2: TLabel
        Left = 4
        Top = 40
        Width = 44
        Height = 13
        Caption = 'Weapon:'
      end
      object Label3: TLabel
        Left = 4
        Top = 64
        Width = 69
        Height = 13
        Caption = 'Mine Damage:'
      end
      object Label6: TLabel
        Left = 4
        Top = 88
        Width = 73
        Height = 13
        Caption = 'Money amount:'
      end
      object Label44: TLabel
        Left = 4
        Top = 112
        Width = 32
        Height = 13
        Caption = 'Ammo:'
      end
      object edHealth: TEdit
        Left = 84
        Top = 8
        Width = 121
        Height = 21
        TabOrder = 0
        Text = '1'
        OnExit = edHealthExit
      end
      object edWeapon: TEdit
        Left = 84
        Top = 32
        Width = 121
        Height = 21
        TabOrder = 1
        Text = '1'
        OnExit = edWeaponExit
      end
      object edMine: TEdit
        Left = 84
        Top = 56
        Width = 121
        Height = 21
        TabOrder = 2
        Text = '5'
        OnExit = edMineExit
      end
      object edMoney: TEdit
        Left = 84
        Top = 80
        Width = 121
        Height = 21
        TabOrder = 3
        Text = '5'
        OnExit = edMoneyExit
      end
      object edAmmo: TEdit
        Left = 84
        Top = 104
        Width = 121
        Height = 21
        TabOrder = 4
        Text = '1'
        OnExit = edAmmoExit
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
        OnExit = edPHealthExit
      end
      object edPMoney: TEdit
        Left = 108
        Top = 104
        Width = 121
        Height = 21
        TabOrder = 1
        Text = '0'
        OnExit = edPMoneyExit
      end
      object edPWeapon: TEdit
        Left = 108
        Top = 56
        Width = 121
        Height = 21
        TabOrder = 2
        Text = '1'
        OnExit = edPWeaponExit
      end
      object edPRec: TEdit
        Left = 108
        Top = 80
        Width = 121
        Height = 21
        TabOrder = 3
        Text = '3'
        OnExit = edPRecExit
      end
      object cbPStance: TComboBox
        Left = 108
        Top = 8
        Width = 121
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 4
        OnExit = cbPStanceExit
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
        OnExit = edSlHealthExit
      end
      object edSlWeapon: TEdit
        Left = 100
        Top = 56
        Width = 121
        Height = 21
        TabOrder = 1
        Text = '3'
        OnExit = edSlWeaponExit
      end
      object cbSlAggresivity: TComboBox
        Left = 100
        Top = 128
        Width = 121
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 2
        OnExit = cbSlAggresivityExit
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
        OnExit = cbSlStanceExit
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
        OnExit = edSlDistExit
      end
      object checkSlFriendly: TCheckBox
        Left = 4
        Top = 156
        Width = 97
        Height = 17
        Caption = 'Friendly'
        TabOrder = 5
        OnExit = checkSlFriendlyExit
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
        OnExit = cbSlColorExit
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
        OnExit = edShHealthExit
      end
      object edShWeapon: TEdit
        Left = 104
        Top = 56
        Width = 121
        Height = 21
        TabOrder = 1
        Text = '3'
        OnExit = edShWeaponExit
      end
      object cbShAggresivity: TComboBox
        Left = 104
        Top = 176
        Width = 121
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 2
        OnExit = cbShAggresivityExit
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
        OnExit = edShRecExit
      end
      object edShJoy: TEdit
        Left = 104
        Top = 152
        Width = 121
        Height = 21
        TabOrder = 4
        Text = '1000'
        OnExit = edShJoyExit
      end
      object cbShStance: TComboBox
        Left = 104
        Top = 8
        Width = 121
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 5
        OnExit = cbShStanceExit
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
        OnExit = edShDistExit
      end
      object edShBulletsDrop: TEdit
        Left = 104
        Top = 104
        Width = 121
        Height = 21
        Hint = '0 (will no drop any bullets)'
        TabOrder = 7
        Text = '3'
        OnExit = edShBulletsDropExit
      end
    end
    object TabSheet5: TTabSheet
      Caption = 'Triggers'
      ImageIndex = 4
      object Label8: TLabel
        Left = 4
        Top = 16
        Width = 24
        Height = 13
        Caption = 'Text:'
      end
      object Label21: TLabel
        Left = 4
        Top = 36
        Width = 54
        Height = 13
        Caption = 'Next Level:'
      end
      object edTriggerText: TEdit
        Left = 84
        Top = 8
        Width = 121
        Height = 21
        TabOrder = 0
        Text = 'No text'
        OnChange = edTriggerTextChange
      end
      object edTriggerLevel: TEdit
        Left = 84
        Top = 32
        Width = 121
        Height = 21
        Hint = 'Use 127 to end the game'
        ParentShowHint = False
        ShowHint = True
        TabOrder = 1
        OnChange = edTriggerLevelChange
      end
    end
    object TabSheet6: TTabSheet
      Caption = 'Elevators/Buttons'
      ImageIndex = 5
      object Label24: TLabel
        Left = 4
        Top = 16
        Width = 151
        Height = 13
        Caption = 'Button: linked to elevator with id'
      end
      object Label25: TLabel
        Left = 4
        Top = 40
        Width = 53
        Height = 13
        Caption = 'Elevator: id'
      end
      object Label26: TLabel
        Left = 116
        Top = 40
        Width = 37
        Height = 13
        Caption = 'moving '
      end
      object Label27: TLabel
        Left = 224
        Top = 40
        Width = 15
        Height = 13
        Caption = 'for '
      end
      object Label28: TLabel
        Left = 300
        Top = 40
        Width = 21
        Height = 13
        Caption = 'cells'
      end
      object Label29: TLabel
        Left = 4
        Top = 64
        Width = 91
        Height = 13
        Caption = 'Automatic elevator:'
      end
      object Label30: TLabel
        Left = 100
        Top = 64
        Width = 37
        Height = 13
        Caption = 'moving '
      end
      object Label31: TLabel
        Left = 208
        Top = 64
        Width = 15
        Height = 13
        Caption = 'for '
      end
      object Label32: TLabel
        Left = 348
        Top = 64
        Width = 21
        Height = 13
        Caption = 'cells'
      end
      object Label33: TLabel
        Left = 284
        Top = 64
        Width = 3
        Height = 13
        Caption = '-'
      end
      object Label34: TLabel
        Left = 100
        Top = 88
        Width = 77
        Height = 13
        Caption = 'with waiting time'
      end
      object Label35: TLabel
        Left = 236
        Top = 88
        Width = 50
        Height = 13
        Caption = 'and speed'
      end
      object cbButtonElevator: TComboBox
        Left = 164
        Top = 8
        Width = 53
        Height = 21
        ItemHeight = 13
        TabOrder = 0
        Text = '0'
        OnExit = cbButtonElevatorExit
      end
      object edElevatorId: TEdit
        Left = 64
        Top = 32
        Width = 45
        Height = 21
        TabOrder = 1
        Text = '0'
        OnExit = edElevatorIdExit
      end
      object cbElevatorDir: TComboBox
        Left = 164
        Top = 32
        Width = 53
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 2
        OnExit = cbElevatorDirExit
        Items.Strings = (
          'UP'
          'DOWN'
          'LEFT'
          'RIGHT')
      end
      object edElevatorDist: TEdit
        Left = 248
        Top = 32
        Width = 45
        Height = 21
        TabOrder = 3
        Text = '0'
        OnExit = edElevatorDistExit
      end
      object cbAElevatorDir: TComboBox
        Left = 148
        Top = 56
        Width = 53
        Height = 21
        Style = csDropDownList
        ItemHeight = 13
        TabOrder = 4
        OnExit = cbAElevatorDirExit
        Items.Strings = (
          'UP'
          'DOWN'
          'LEFT'
          'RIGHT')
      end
      object edAElevatorDist1: TEdit
        Left = 232
        Top = 56
        Width = 45
        Height = 21
        TabOrder = 5
        Text = '0'
        OnExit = edAElevatorDist1Exit
      end
      object edAElevatorDist2: TEdit
        Left = 296
        Top = 56
        Width = 45
        Height = 21
        TabOrder = 6
        Text = '0'
        OnExit = edAElevatorDist2Exit
      end
      object edAElevatorWait: TEdit
        Left = 184
        Top = 80
        Width = 45
        Height = 21
        TabOrder = 7
        Text = '0'
        OnExit = edAElevatorWaitExit
      end
      object edAElevatorSpeed: TEdit
        Left = 296
        Top = 80
        Width = 45
        Height = 21
        TabOrder = 8
        Text = '0'
        OnExit = edAElevatorSpeedExit
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
        OnExit = cbRShStanceExit
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
        OnExit = edRShHealthExit
      end
      object edRShWeapon: TEdit
        Left = 104
        Top = 56
        Width = 121
        Height = 21
        TabOrder = 2
        Text = '3'
        OnExit = edRShWeaponExit
      end
      object edRShDist: TEdit
        Left = 104
        Top = 80
        Width = 121
        Height = 21
        TabOrder = 3
        Text = '3'
        OnExit = edRShDistExit
      end
      object edRShRec: TEdit
        Left = 104
        Top = 104
        Width = 121
        Height = 21
        TabOrder = 4
        Text = '100'
        OnExit = edRShRecExit
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
        OnExit = edPFileExit
      end
      object edPTime: TEdit
        Left = 104
        Top = 32
        Width = 121
        Height = 21
        TabOrder = 1
        Text = '3'
        OnExit = edPTimeExit
      end
      object cbPAutoTerminate: TCheckBox
        Left = 4
        Top = 60
        Width = 113
        Height = 17
        Alignment = taLeftJustify
        Caption = 'Auto terminate'
        TabOrder = 2
        OnExit = cbPAutoTerminateExit
      end
    end
  end
end
