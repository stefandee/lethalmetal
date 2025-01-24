package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

import javax.microedition.lcdui.*;
import ui.TriggersObserver;

public class CObjPlayer extends CObjBase
{
  int mCollisionCount;
  private int mWeaponPower, mMaxHeight;
  public int mGrabPointsSize, mCloseGrabPointsSize;
  public int mFireAlignX;
  boolean mCanClimbUp, mCanClimbDown, mCanGrabLadderDown, mCanGrabLadderUp, mCanDescendLadderDown, mCanDescendLadderUp;
  boolean mCanPushButton;
  int mIdle = 0;
  public int mAmmo;//, mMoney;
  CXSprite mFlameSprite, mBulletSprite, mBlinkSprite, mExplosionSprite;
  int mTimeReload = 0, mTimeReloadMax = 25;
  //CPoint lMid1 = new CPoint(0, 0), lMid2 = new CPoint(0, 0);

  //
  // Flags for recovery and death actions
  //
  final static byte FALL_TO_LEFT  = 0;
  final static byte FALL_TO_RIGHT = 1;

  /**
  * when the player shoots from standing or standing crouched, it will do the
  * draw weapon and fire animation continuously; this flag links the two animations
  */
  boolean mDirectFire;

  int mCanGrabLadderDown_Temp;
  boolean mCanGrab, mCloseCanGrab, mElevatorCollision;
  CPoint mGrabPoint, mCloseGrabPoint;
  int mLeftCollisionCount, mRightCollisionCount;
  CPoint mLadderLocation, mHeadLadderLocation;
  //CObjectManager  mObjectManager;
  //CXSpriteManager mSpriteManager;

  // hit constants
  static final int HIT_BY_BULLET = 0;
  static final int HIT_BY_OTHER  = 1;

  // player config
  static final int MAX_PLAYER_HEALTH   = 3;
  static final int MAX_PLAYER_AMMO     = 5;
  static final int PLAYER_WEAPON_POWER = 1;

  // action constants id
  final static byte A_STOP_RIGHT  = 0;
  final static byte A_STOP_LEFT   = 1;
  final static byte A_MOVE_RIGHT  = 2;
  final static byte A_MOVE_LEFT   = 3;
  final static byte A_FALL_RIGHT  = 4;
  final static byte A_FALL_LEFT   = 5;
  final static byte A_JUMP_RIGHT  = 6;
  final static byte A_JUMP_LEFT   = 7;
  final static byte A_JUMP_FALL_RIGHT  = 8;
  final static byte A_JUMP_FALL_LEFT   = 9;
  final static byte A_CROUCH_RIGHT   = 10;
  final static byte A_CROUCH_LEFT   = 11;
  final static byte A_CROUCH_WALK_RIGHT   = 12;
  final static byte A_CROUCH_WALK_LEFT   = 13;
  final static byte A_CLIMB_LADDER = 14;
  final static byte A_DESCEND_LADDER_DOWN = 15;
  final static byte A_DRAW_WEAPON_LEFT = 16;
  final static byte A_DRAW_WEAPON_RIGHT = 17;
  final static byte A_TARGETING_LEFT = 18;
  final static byte A_TARGETING_RIGHT = 19;
  final static byte A_FIRE_LEFT = 20;
  final static byte A_FIRE_RIGHT = 21;
  final static byte A_JUMP_UP_LEFT = 22;
  final static byte A_JUMP_UP_RIGHT = 23;
  final static byte A_JUMP_UP_FALL_LEFT = 24;
  final static byte A_JUMP_UP_FALL_RIGHT = 25;
  final static byte A_DYING_RIGHT = 26;
  final static byte A_DYING_LEFT = 27;
  final static byte A_RECOVERY_FALL_RIGHT = 28;
  final static byte A_RECOVERY_FALL_LEFT = 29;
  final static byte A_RECOVERY_RISE_RIGHT = 30;
  final static byte A_RECOVERY_RISE_LEFT = 31;
  final static byte A_RECOVERY_LEFT = 32;
  final static byte A_RECOVERY_RIGHT = 33;
  final static byte A_DEAD_LEFT = 34;
  final static byte A_DEAD_RIGHT = 35;
  final static byte A_AIR_RECOVERY_FALL_RIGHT = 36;
  final static byte A_AIR_RECOVERY_FALL_LEFT = 37;
  final static byte A_AIR_DYING_FALL_RIGHT = 38;
  final static byte A_AIR_DYING_FALL_LEFT = 39;
  final static byte A_SLASH_RIGHT = 40;
  final static byte A_SLASH_LEFT = 41;
  final static byte A_CROUCH_SLASH_RIGHT = 42;
  final static byte A_CROUCH_SLASH_LEFT = 43;
  final static byte A_CROUCH_DYING_RIGHT = 44;
  final static byte A_CROUCH_DYING_LEFT = 45;
  final static byte A_CROUCH_RECOVERY_FALL_RIGHT = 46;
  final static byte A_CROUCH_RECOVERY_FALL_LEFT = 47;
  final static byte A_CROUCH_RECOVERY_RISE_RIGHT = 48;
  final static byte A_CROUCH_RECOVERY_RISE_LEFT = 49;
  final static byte A_CROUCH_RECOVERY_LEFT = 50;
  final static byte A_CROUCH_RECOVERY_RIGHT = 51;
  final static byte A_GRAB_EDGE_LEFT = 52;
  final static byte A_GRAB_EDGE_RIGHT = 53;
  final static byte A_CLIMB_EDGE_UP_LEFT = 54;
  final static byte A_CLIMB_EDGE_UP_RIGHT = 55;
  final static byte A_CLIMB_EDGE_DOWN_LEFT = 56;
  final static byte A_CLIMB_EDGE_DOWN_RIGHT = 57;
  final static byte A_HIDE_LEFT  = 58;
  final static byte A_HIDE_RIGHT = 59;
  final static byte A_DESCEND_LADDER_UP_JUMP = 60;
  final static byte A_DESCEND_LADDER_UP_FALL = 61;
  final static byte A_JUMP_OFF_EDGE_LEFT = 62;
  final static byte A_JUMP_OFF_EDGE_RIGHT = 63;
  final static byte A_HIDE_WITH_WEAPON_LEFT = 64;
  final static byte A_HIDE_WITH_WEAPON_RIGHT = 65;
  final static byte A_PREPARE_DESCEND_LADDER_DOWN = 66;
  final static byte A_CROUCH_DRAW_WEAPON_LEFT = 67;
  final static byte A_CROUCH_DRAW_WEAPON_RIGHT = 68;
  final static byte A_CROUCH_TARGETING_LEFT = 69;
  final static byte A_CROUCH_TARGETING_RIGHT = 70;
  final static byte A_CROUCH_FIRE_LEFT = 71;
  final static byte A_CROUCH_FIRE_RIGHT = 72;
  final static byte A_RUN_LEFT = 73;
  final static byte A_RUN_RIGHT = 74;
  final static byte A_IDLE_LEFT = 75;
  final static byte A_IDLE_RIGHT = 76;
  final static byte A_CROUCH_UNDRAW_WEAPON_LEFT = 77;
  final static byte A_CROUCH_UNDRAW_WEAPON_RIGHT = 78;
  final static byte A_USING_LEFT = 79;
  final static byte A_USING_RIGHT = 80;
  final static byte A_RUN_STOP_LEFT = 81;
  final static byte A_RUN_STOP_RIGHT = 82;
  final static byte A_WEAPON_IDLE_LEFT = 83;
  final static byte A_WEAPON_IDLE_RIGHT = 84;
  final static byte A_UNDRAW_WEAPON_RIGHT = 85;
  final static byte A_UNDRAW_WEAPON_LEFT = 86;
  final static byte A_PREPARE_JUMP_RIGHT  = 87;
  final static byte A_PREPARE_JUMP_LEFT   = 88;
  final static byte A_DESCEND_LADDER_UP = 89;

  final static byte CLIMB_SPEED = 8;

  //
  // TUNNING declarations (comment for release, uncomment for debug)
  //
  /*
  int mTunningAction = 0, mTunningActionMax = 98, mTunningActionMin = 0;
  int mTunningWhat; // 0 - action, 1 - parameter
  boolean mTunning = true;
  final static byte TUNNING_MODIFY_ACTION = 0;
  final static byte TUNNING_MODIFY_SPEEDX = 1;
  final static byte TUNNING_MODIFY_SPEEDY = 2;
  final static byte TUNNING_MODIFY_UPDATE = 3;
  int mTunningParameter = TUNNING_MODIFY_ACTION;
  */

  public CObjPlayer(CLevel _level, CXSprite _sprite, int _x, int _y, int _stance)
  {
    super(_level, _sprite, _x, _y);

    //mObjectManager = _manager;
    //mSpriteManager = _spriteManager;
    //mEnvManager    = _envManager;
    //mTileManager   = _tileManager;
    mLadderLocation = new CPoint();
    mHeadLadderLocation = new CPoint();
    mCanCollideWithObj = true;
    mCanCollideWithTile = true;
    mCanCollideWithBullet = true;

    mActions = mLevel.mObjectManager.mPlayerActions;

    if (_stance == 1)
    {
      SetA(A_STOP_RIGHT);
    }
    else
    {
      SetA(A_STOP_LEFT);
    }

    mMaxHP       = mHP = MAX_PLAYER_HEALTH;
    mAmmo        = MAX_PLAYER_AMMO;
    mWeaponPower = PLAYER_WEAPON_POWER;

    // money are points, should not be initialized here?
    //mMoney       = 0;

    mBulletSprite    = mLevel.mSpriteManager.AddSprite("BULL");
    mFlameSprite     = mLevel.mSpriteManager.AddSprite("BLUE");
    mBlinkSprite     = mLevel.mSpriteManager.AddSprite("BLIN");
    mExplosionSprite = mLevel.mSpriteManager.AddSprite("EXPL");

    //mActions.SetSpeedXFrames(A_RECOVERY_FALL_RIGHT, -3);
    //mActions.SetUpdateMax(A_RECOVERY_FALL_RIGHT, 1);
    //mActions.SetRepeatMax(A_RECOVERY_FALL_RIGHT, 2);
    //mActions.SetSpeedYFrames(A_RECOVERY_FALL_RIGHT, 1);

    // TEST CODE
    mActions.SetAnimFrameEnd(A_MOVE_LEFT, 5);
    mActions.SetAnimFrameEnd(A_MOVE_RIGHT, 5);

    // movement old: 7,8,4,3,5,7,6,4
    // 1 = x, 2 = x, 3 = 8, 4 = 4, 5 = 3, 6 = 5, 7 = 7, 8 = 6, 9 = 4, 10 = 7
    // movement new: 7,8,10,2,5,13,6,8

    /*
     frame 3 + 6 pixeli fata de ce am eu
z_oblio: fuck -
z_oblio: nu e vb de 3 ci de 4
z_oblio: again
z_oblio: frameul 4 + 6 pixeli
z_oblio: frame 5 - 1(minus un pixel )
z_oblio: Frame 7 + 6 pixeli
z_oblio: frame 9 + 4 pixeli

     */
  }

  public void ComputeAction(int _key)
  {
    // if a key was pressed, reset the idle counter
    if (_key != 0)
    {
      mIdle = 0;
    }

    // soft key - up
    if ((_key & 1) != 0)
    {
    }

    // pound
    /*
    if ((_key & 2048) != 0)
    {
      // DEBUG START
      // slow motion enabler
      if (mFactor == 15)
      {
        mFactor = 1;
        //System.out.println("slow motion disabled");
      }
      else
      {
        mFactor = 15;
        //System.out.println("slow motion enabled");
      }
    }
    */

    // 0
    if ((_key & 1024) != 0)
    {
    }

    // 2
    if ((_key & 4096) != 0)
    {
      if (mCanGrabLadderUp && (Does(A_FALL_RIGHT) || Does(A_FALL_LEFT)))
      {
        SetA2(A_CLIMB_LADDER);

        // just a hack, otherwise i'm not able to grab the ladder
        mRealY -= 2;

        // center the player to the ladder (so that the player won't be outside the ladder)
        mRealX = mLadderLocation.x - GetWidth() / 2;

        GetBB();

        //mActions[mCurrentAction].mFinished = false;
        mActions.SetAutoUpdate(mCurrentAction, false);
        mActions.SetFrameDelta(mCurrentAction, 1);
        mActions.SetSpeedYFrames(mCurrentAction, (byte)-CLIMB_SPEED);
        return;
      }

      if (Does(A_CROUCH_LEFT))
      {
        SetA2(A_STOP_LEFT);
        return;
      }

      if (Does(A_CROUCH_TARGETING_LEFT))
      {
        SetA2(A_TARGETING_LEFT);
        return;
      }

      if (Does(A_CROUCH_RIGHT))
      {
        SetA2(A_STOP_RIGHT);
        return;
      }

      if (Does(A_CROUCH_TARGETING_RIGHT))
      {
        SetA2(A_TARGETING_RIGHT);
        return;
      }

      if (Does(A_CLIMB_LADDER))
      {
        //if (mActions[mCurrentAction].mUpdate >= mActions[mCurrentAction].mUpdateMax)
        if (mUpdate >= mActions.GetUpdateMax(mCurrentAction))
        {
          mActions.SetFrameDelta(mCurrentAction, 1);
          mActions.SetSpeedYFrames(mCurrentAction, (byte)-CLIMB_SPEED);
          mActions.SetSpeedXFrames(mCurrentAction, (byte)-mActions.GetSpeedX(mSpeedIndex));
          //mRealX = mLadderLocation.x - GetWidth() / 2;
          ManageActionParameters(mCurrentAction);
        }
        return;
      }

      if (mCanGrabLadderUp && (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) || Does(A_IDLE_RIGHT) || Does(A_IDLE_LEFT)))
      {
        SetA2(A_CLIMB_LADDER);

        // just a hack, otherwise i'm not able to grab the ladder because i will collide with the ground
        mRealY -= 4;

        // center the player to the ladder (so that the player won't be outside the ladder)
        mRealX = mLadderLocation.x - GetWidth() / 2;

        GetBB();

        //mActions[mCurrentAction].mFinished = false;
        mActions.SetAutoUpdate(mCurrentAction, false);
        mActions.SetFrameDelta(mCurrentAction, 1);
        mActions.SetSpeedYFrames(mCurrentAction, (byte)-CLIMB_SPEED);
        return;
      }

      if (Does(A_STOP_LEFT) || Does(A_IDLE_LEFT))
      {
        SetA2(A_HIDE_LEFT);
        return;
      }

      if (Does(A_TARGETING_LEFT) || Does(A_WEAPON_IDLE_LEFT))
      {
        SetA2(A_HIDE_WITH_WEAPON_LEFT, AC_TO_RIGHT_WIDTH);
        return;
      }

      if (Does(A_STOP_RIGHT) || Does(A_IDLE_RIGHT))
      {
        SetA2(A_HIDE_RIGHT);
        return;
      }

      if (Does(A_TARGETING_RIGHT) || Does(A_WEAPON_IDLE_RIGHT))
      {
        SetA2(A_HIDE_WITH_WEAPON_RIGHT);
        return;
      }

      if (Does(A_GRAB_EDGE_RIGHT))
      {
        SetA(A_CLIMB_EDGE_UP_RIGHT);
        //mActions[mCurrentAction].mFinished = false;
        return;
      }

      if (Does(A_GRAB_EDGE_LEFT))
      {
        SetA(A_CLIMB_EDGE_UP_LEFT);
        //mActions[mCurrentAction].mFinished = false;
        return;
      }
    }

    // 5
    if ((_key & 256) != 0)
    {
      //CFeedback.getInstance().playSound(0);

      if (Does(A_FIRE_LEFT) || Does(A_FIRE_RIGHT) || Does(A_DRAW_WEAPON_RIGHT) || Does(A_DRAW_WEAPON_LEFT) ||
          Does(A_CROUCH_DRAW_WEAPON_RIGHT) || Does(A_CROUCH_DRAW_WEAPON_LEFT) || Does(A_CROUCH_FIRE_LEFT) || Does(A_CROUCH_FIRE_RIGHT))
      {
        return;
      }

      if (mCanPushButton)
      {
        switch(mCurrentAction)
        {
          case A_STOP_LEFT:
          case A_IDLE_LEFT:
            SetA2(A_USING_LEFT);
            return;

          case A_STOP_RIGHT:
          case A_IDLE_RIGHT:
            SetA2(A_USING_RIGHT);
            return;
        }
      }

      if (mAmmo <= 0)
      {
        return;
      }

      if (Does(A_STOP_LEFT) || Does(A_HIDE_LEFT) || Does(A_IDLE_LEFT))
      {
        //mRealX += mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame);
        mRealX += mXSprite.GetWidth(mAnimFrame);
        SetA2(A_DRAW_WEAPON_LEFT);

        mAmmo--;
        mDirectFire = true;
        return;
      }

      if (Does(A_STOP_RIGHT) || Does(A_HIDE_RIGHT) || Does(A_IDLE_RIGHT))
      {
        SetA2(A_DRAW_WEAPON_RIGHT);

        mAmmo--;
        mDirectFire = true;
        return;
      }

      if (Does(A_WEAPON_IDLE_LEFT))
      {
        SetA2(A_TARGETING_LEFT);
        return;
      }

      if (Does(A_WEAPON_IDLE_RIGHT))
      {
        SetA2(A_TARGETING_RIGHT);
        return;
      }

      if (Does(A_CROUCH_LEFT))
      {
        //mRealX += mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame);
        mRealX += mXSprite.GetWidth(mAnimFrame);
        SetA2(A_CROUCH_DRAW_WEAPON_LEFT);
        //GetBB();

        mAmmo--;
        mDirectFire = true;
        return;
      }

      if (Does(A_CROUCH_RIGHT))
      {
        SetA2(A_CROUCH_DRAW_WEAPON_RIGHT);
        mAmmo--;
        mDirectFire = true;
        return;
      }

      if (Does(A_HIDE_WITH_WEAPON_RIGHT) && Reloading())
      {
        SetA2(A_TARGETING_RIGHT);
        return;
      }

      if (Does(A_HIDE_WITH_WEAPON_LEFT) && Reloading())
      {
        SetA2(A_TARGETING_LEFT, AC_TO_RIGHT);
        return;
      }

      if ((Does(A_TARGETING_RIGHT) || Does(A_HIDE_WITH_WEAPON_RIGHT)) && !Reloading())
      {
        //mActions[mCurrentAction].mFinished = false;
        //mActions[mCurrentAction].mUpdate = 0;
        //mUpdate = 0;

        mAmmo--;
        SetA2(A_FIRE_RIGHT);
        Fire(CObjBullet.A_MOVE_RIGHT);
        Flame(CObjBullet.A_MOVE_RIGHT);
        return;
      }

      if ((Does(A_TARGETING_LEFT) || Does(A_HIDE_WITH_WEAPON_LEFT)) && !Reloading())
      {
        //mActions[mCurrentAction].mFinished = false;
        //mUpdate = 0;

        //mRealX += mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame);
        mAmmo--;
        SetA2(A_FIRE_LEFT, AC_TO_RIGHT);

        Fire(CObjBullet.A_MOVE_LEFT);
        Flame(CObjBullet.A_MOVE_LEFT);
        return;
      }

      if (Does(A_CROUCH_TARGETING_RIGHT) && !Reloading())
      {
        //mActions[mCurrentAction].mFinished = false;
        //mUpdate = 0;

        mAmmo--;
        SetA2(A_CROUCH_FIRE_RIGHT);

        Fire(CObjBullet.A_MOVE_RIGHT);
        Flame(CObjBullet.A_MOVE_RIGHT);
        return;
      }

      if (Does(A_CROUCH_TARGETING_LEFT) && !Reloading())
      {
        //mActions[mCurrentAction].mFinished = false;
        //mUpdate = 0;

        mAmmo--;
        SetA2(A_CROUCH_FIRE_LEFT);

        Fire(CObjBullet.A_MOVE_LEFT);
        Flame(CObjBullet.A_MOVE_LEFT);
        return;
      }
    }

    // 7
    if ((_key & 64) != 0)
    {
      if (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) || Does(A_IDLE_LEFT) || Does(A_IDLE_RIGHT) ||
          Does(A_WEAPON_IDLE_LEFT) || Does(A_WEAPON_IDLE_RIGHT) || Does(A_CROUCH_LEFT) || Does(A_CROUCH_RIGHT) ||
          Does(A_HIDE_RIGHT) || Does(A_HIDE_LEFT))
      {
        SetA2(A_CROUCH_WALK_LEFT);
        return;
      }
    }

    // 9
    if ((_key & 128) != 0)
    {
      if (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) || Does(A_IDLE_LEFT) || Does(A_IDLE_RIGHT) ||
          Does(A_WEAPON_IDLE_LEFT) || Does(A_WEAPON_IDLE_RIGHT) || Does(A_CROUCH_RIGHT) || Does(A_CROUCH_LEFT) ||
          Does(A_HIDE_RIGHT) || Does(A_HIDE_LEFT))
      {
        SetA2(A_CROUCH_WALK_RIGHT);
        return;
      }
    }

    // 8
    if ((_key & 512) != 0)
    {
      if (Does(A_GRAB_EDGE_RIGHT))
      {
        SetA(A_FALL_RIGHT);
        //mActions[GetA()].mFinished = false;
        return;
      }

      if (Does(A_GRAB_EDGE_LEFT))
      {
        SetA(A_FALL_LEFT);
        //mActions[GetA()].mFinished = false;
        return;
      }

      if (/*(mCanGrabLadderDown_Temp == 0) &&*/ mCanDescendLadderDown && (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) || Does(A_IDLE_LEFT) || Does(A_IDLE_RIGHT) || Does(A_CROUCH_LEFT) || Does(A_CROUCH_RIGHT)))
      {
        SetA2(A_PREPARE_DESCEND_LADDER_DOWN);

        mRealX = mHeadLadderLocation.x - GetWidth() / 2;
        mRealY = mHeadLadderLocation.y - 10;// - 2 * 5; // 2 is the y speed of the action, there are 5 frames for this action

        GetBB();

        return;
      }

      if (mCanGrabLadderDown && (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) || Does(A_IDLE_LEFT) || Does(A_IDLE_RIGHT)))
      {
        SetA(A_CLIMB_LADDER);

        //System.out.println("GRAB DOWN");

        //mActions[mCurrentAction].mFinished = false;
        mActions.SetAutoUpdate(mCurrentAction, false);
        mActions.SetFrameDelta(mCurrentAction, 1);
        mActions.SetSpeedYFrames(mCurrentAction, (byte)CLIMB_SPEED);

        mRealX = mLadderLocation.x - GetWidth() / 2;

        GetBB();

        return;
      }

      if (Does(A_STOP_RIGHT) || Does(A_IDLE_RIGHT) || Does(A_HIDE_RIGHT))
      {
        SetA2(A_CROUCH_RIGHT);

        return;
      }

      if (Does(A_WEAPON_IDLE_RIGHT) || Does(A_TARGETING_RIGHT) || Does(A_HIDE_WITH_WEAPON_RIGHT))
      {
        SetA2(A_CROUCH_TARGETING_RIGHT);
        return;
      }

      if (Does(A_STOP_LEFT) || Does(A_IDLE_LEFT) || Does(A_HIDE_LEFT))
      {
        SetA2(A_CROUCH_LEFT);
        return;
      }

      if (Does(A_WEAPON_IDLE_LEFT) || Does(A_TARGETING_LEFT)  || Does(A_HIDE_WITH_WEAPON_LEFT))
      {
        SetA2(A_CROUCH_TARGETING_LEFT, AC_TO_RIGHT);
        return;
      }

      if (Does(A_CLIMB_LADDER))
      {
        if (mUpdate >= mActions.GetUpdateMax(mCurrentAction))
        {
          mActions.SetFrameDelta(mCurrentAction, -1);
          mActions.SetSpeedYFrames(mCurrentAction, (byte)CLIMB_SPEED);
          mActions.SetSpeedXFrames(mCurrentAction, (byte)-mActions.GetSpeedX(mSpeedIndex));
          //mRealX = mLadderLocation.x - GetWidth() / 2;
          ManageActionParameters(mCurrentAction);
        }
        return;
      }
    }

    // 1
    if ((_key & 8192) != 0)
    {
      if (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) ||
          Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) ||
          Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT) ||
          Does(A_IDLE_RIGHT) || Does(A_IDLE_LEFT) ||
          Does(A_TARGETING_RIGHT)
          )
      {
        SetA2(A_PREPARE_JUMP_LEFT);
        //mJumpBottom = mBB.mBottom;
        return;
      }

      if (Does(A_TARGETING_LEFT))
      {
        SetA2(A_PREPARE_JUMP_LEFT, AC_TO_RIGHT_WIDTH);
        return;
      }

      if (Does(A_CLIMB_LADDER))
      {
        SetA(A_JUMP_LEFT);
        return;
      }

      if (Does(A_GRAB_EDGE_LEFT))
      {
        SetA(A_CLIMB_EDGE_UP_LEFT);
        return;
      }
    }

    // 3
    if ((_key & 16384) != 0)
    {
      if (Does(A_STOP_LEFT) || Does(A_STOP_RIGHT) ||
          Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) ||
          Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT) ||
          Does(A_IDLE_RIGHT) || Does(A_IDLE_LEFT) ||
          Does(A_TARGETING_RIGHT)
          )
      {
        SetA2(A_PREPARE_JUMP_RIGHT);
        //mJumpBottom = mBB.mBottom;
        return;
      }

      if (Does(A_TARGETING_LEFT))
      {
        SetA2(A_PREPARE_JUMP_RIGHT, AC_TO_RIGHT_WIDTH);
        return;
      }

      if (Does(A_CLIMB_LADDER))
      {
        SetA(A_JUMP_RIGHT);
        return;
      }

      if (Does(A_GRAB_EDGE_RIGHT))
      {
        SetA(A_CLIMB_EDGE_UP_RIGHT);
        return;
      }
    }

    // 6
    if ((_key & 32) != 0)
    {
      if (Does(A_GRAB_EDGE_RIGHT))
      {
        SetA(A_CLIMB_EDGE_UP_RIGHT);
        return;
      }

      if (Does(A_GRAB_EDGE_LEFT))
      {
        SetA(A_FALL_LEFT);
        return;
      }

      if (Does(A_STOP_LEFT) || Does(A_IDLE_LEFT))
      {
        SetA2(A_STOP_RIGHT);
        return;
      }

      if (Does(A_STOP_RIGHT) || Does(A_IDLE_RIGHT))
      {
        SetA2(A_MOVE_RIGHT, AC_TO_RIGHT);
        return;
      }

      if (Does(A_MOVE_RIGHT) && (mAnimFrame >= mActions.GetAnimFrameStart(mCurrentAction) + 1))
      {
        int lFramesDiff = mAnimFrame - mActions.GetAnimFrameStart(mCurrentAction);
        SetA2(A_RUN_RIGHT);
        mAnimFrame = mActions.GetAnimFrameStart(mCurrentAction) + lFramesDiff;
        UpdateBottom();
        GetBB();
        return;
      }

      if (Does(A_CROUCH_LEFT))
      {
        SetA2(A_CROUCH_RIGHT);
        return;
      }

      if (Does(A_CROUCH_RIGHT))
      {
        SetA2(A_CROUCH_WALK_RIGHT);
        return;
      }

      if ((Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) || Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT)) && Finished())
      {
        SetA2(A_STOP_RIGHT);
        return;
      }

      if (Does(A_TARGETING_RIGHT) || Does(A_WEAPON_IDLE_LEFT) || Does(A_WEAPON_IDLE_RIGHT))
      {
        SetA(A_UNDRAW_WEAPON_RIGHT);
        Blink();
        return;
      }

      if (Does(A_TARGETING_LEFT))
      {
        SetA2(A_TARGETING_RIGHT, AC_TO_MIDDLE_LEFT);
        return;
      }

      if (Does(A_CROUCH_TARGETING_RIGHT))
      {
        SetA(A_CROUCH_UNDRAW_WEAPON_RIGHT);
        Blink();
        return;
      }

      if (Does(A_CROUCH_TARGETING_LEFT))
      {
        SetA2(A_CROUCH_TARGETING_RIGHT, AC_TO_MIDDLE_LEFT);
        return;
      }

      if (Does(A_CLIMB_LADDER))
      {
        SetA2(A_FALL_RIGHT);
        return;
      }
    }

    // 4
    if ((_key & 16) != 0)
    {
      if (Does(A_GRAB_EDGE_RIGHT))
      {
        SetA(A_FALL_RIGHT);
        return;
      }

      if (Does(A_GRAB_EDGE_LEFT))
      {
        SetA(A_CLIMB_EDGE_UP_LEFT);
        return;
      }

      if (Does(A_STOP_RIGHT) || Does(A_IDLE_RIGHT))
      {
        SetA2(A_STOP_LEFT);
        return;
      }

      if (Does(A_STOP_LEFT) || Does(A_IDLE_LEFT))
      {
        SetA2(A_MOVE_LEFT, AC_TO_RIGHT_WIDTH);
        return;
      }

      if (Does(A_MOVE_LEFT) && (mAnimFrame >= mActions.GetAnimFrameStart(mCurrentAction) + 1))
      {
        int lFramesDiff = mAnimFrame - mActions.GetAnimFrameStart(mCurrentAction);
        SetA2(A_RUN_LEFT);
        mAnimFrame = mActions.GetAnimFrameStart(mCurrentAction) + lFramesDiff;

        UpdateBottom();
        GetBB();
        return;
      }

      if (Does(A_CROUCH_RIGHT))
      {
        SetA2(A_CROUCH_LEFT);
        return;
      }

      if (Does(A_CROUCH_LEFT))
      {
        SetA2(A_CROUCH_WALK_LEFT);
        return;
      }

      if ((Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) || Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT)) && Finished())
      {
        SetA2(A_STOP_LEFT);
        return;
      }

      if (Does(A_TARGETING_LEFT) || Does(A_WEAPON_IDLE_LEFT))
      {
        SetA2(A_UNDRAW_WEAPON_LEFT, AC_TO_RIGHT_WIDTH);
        Blink();
      }

      if (Does(A_WEAPON_IDLE_RIGHT))
      {
        SetA2(A_UNDRAW_WEAPON_LEFT);
        Blink();
        return;
      }

      if (Does(A_TARGETING_RIGHT))
      {
        SetA2(A_TARGETING_LEFT, AC_TO_MIDDLE_RIGHT);
        return;
      }

      if (Does(A_CROUCH_TARGETING_LEFT))
      {
        SetA2(A_CROUCH_UNDRAW_WEAPON_LEFT, AC_TO_RIGHT_WIDTH);
        Blink();
        return;
      }

      if (Does(A_CROUCH_TARGETING_RIGHT))
      {
        SetA2(A_CROUCH_TARGETING_LEFT, AC_TO_MIDDLE_RIGHT);
        return;
      }

      if (Does(A_CLIMB_LADDER))
      {
        SetA2(A_FALL_LEFT);
        return;
      }
    }

    /*
    if (mTunning)
    {
      // UP ARROW
      if ((_key & 1) != 0)
      {
        switch(mTunningParameter)
        {
          case TUNNING_MODIFY_ACTION:
            mTunningAction++;

            if (mTunningAction > mTunningActionMax)
            {
              mTunningAction = mTunningActionMin;
            }
            break;

          case TUNNING_MODIFY_SPEEDX:
            mActions[mTunningAction].mSpeedX++;
            break;

          case TUNNING_MODIFY_SPEEDY:
            mActions[mTunningAction].mSpeedY++;
            break;

          case TUNNING_MODIFY_UPDATE:
            mActions[mTunningAction].mUpdateMax++;
            break;
        }
      }

      // DOWN ARROW
      if ((_key & 2) != 0)
      {
        switch(mTunningParameter)
        {
          case TUNNING_MODIFY_ACTION:
            mTunningAction--;

            if (mTunningAction < mTunningActionMin)
            {
              mTunningAction = mTunningActionMax - 1;
            }
            break;

          case TUNNING_MODIFY_SPEEDX:
            mActions[mTunningAction].mSpeedX--;
            break;

          case TUNNING_MODIFY_SPEEDY:
            mActions[mTunningAction].mSpeedY--;
            break;

          case TUNNING_MODIFY_UPDATE:
            mActions[mTunningAction].mUpdateMax--;

            if (mActions[mTunningAction].mUpdateMax < 0)
            {
              mActions[mTunningAction].mUpdateMax = 0;
            }
            break;
        }
      }

      // LEFT ARROW
      if ((_key & 4) != 0)
      {
        mTunningParameter--;

        if (mTunningParameter < 0)
        {
          mTunningParameter = 3;
        }

      }

      // RIGHT ARROW
      if ((_key & 8) != 0)
      {
        mTunningParameter++;

        if (mTunningParameter > 3)
        {
          mTunningParameter = 0;
        }

      }
    }
    */

    if (_key == 0)
    {
      if (Does(A_RUN_RIGHT))
      {
        SetA2(A_RUN_STOP_RIGHT);
      }

      if (Does(A_RUN_LEFT))
      {
        SetA2(A_RUN_STOP_LEFT);
      }

      //
      // re-enable this code if there is normal crouch walk, and not roll
      //
      /*
      if (Finished())
      {
        if (Does(A_CROUCH_WALK_RIGHT))
        {
          SetA2(A_CROUCH_RIGHT);
          return;
        }

        if (Does(A_CROUCH_WALK_LEFT))
        {
          SetA2(A_CROUCH_LEFT);
          return;
        }
      }
      */

      mIdle++;

      if (mIdle > 250)
      {
        // it seems the player just sits
        // set the IDLE action
        if (Does(A_STOP_LEFT))
        {
          SetA2(A_IDLE_LEFT);
        }

        if (Does(A_STOP_RIGHT))
        {
          SetA2(A_IDLE_RIGHT);
        }

        mIdle = 0;
      }
    }
  }

  public void UpdateBottom()
  {
    switch(mCurrentAction)
    {
      case A_CROUCH_WALK_LEFT:
      case A_CROUCH_WALK_RIGHT:
      case A_RECOVERY_FALL_LEFT:
      case A_RECOVERY_FALL_RIGHT:
      case A_RECOVERY_RISE_LEFT:
      case A_RECOVERY_RISE_RIGHT:
      case A_DRAW_WEAPON_LEFT:
      case A_DRAW_WEAPON_RIGHT:
      case A_CROUCH_DRAW_WEAPON_LEFT:
      case A_CROUCH_DRAW_WEAPON_RIGHT:
      case A_RUN_LEFT:
      case A_RUN_RIGHT:
      case A_RUN_STOP_LEFT:
      case A_RUN_STOP_RIGHT:
      case A_MOVE_LEFT:
      case A_MOVE_RIGHT:
      case A_CROUCH_RECOVERY_FALL_LEFT:
      case A_CROUCH_RECOVERY_FALL_RIGHT:
      case A_CROUCH_RECOVERY_RISE_LEFT:
      case A_CROUCH_RECOVERY_RISE_RIGHT:
      case A_CROUCH_DYING_RIGHT:
      case A_DYING_LEFT:
      case A_DYING_RIGHT:
      case A_CROUCH_DYING_LEFT:
      case A_PREPARE_JUMP_LEFT:
      case A_PREPARE_JUMP_RIGHT:

        //if (mCurrentAction == 12 || mCurrentAction == 13)
        //{
        //  System.out.println("updatebottom before: " + mRealY);
        //}

        //int lOldBottom = mRealY + mXSprite.GetHeight(mPrevAnimFrame);
        //mRealY = lOldBottom - mXSprite.GetHeight(mAnimFrame);
        mRealY = mBB.mBottom - mXSprite.GetHeight(mAnimFrame);
        //System.out.println("stand to crouch: " + lOldBottom);

        //if (mCurrentAction == 12 || mCurrentAction == 13)
        //{
        //  System.out.println("updatebottom after: " + mRealY);
        //}
        break;
        //GetBB();
        //UpdateBottom();
    }
  }


  public final void Update()
  {
    super.Update();

    CheckFallDeath();

   //
   // WEAPON RELOAD
   //
   if (mTimeReload <= mTimeReloadMax)
   {
     mTimeReload++;
   }

   //
   // Update bottom section
   //
   if (mUpdate == 0)
   {
   }

   //if (mActions[mCurrentAction].mFinished)
   if (mActionFinished)
   {
     OnFinished();
   }
 }

 public final void OnFinished()
 {
   switch(mCurrentAction)
   {
     case A_JUMP_RIGHT:
       SetA(A_JUMP_FALL_RIGHT);
       //mJumpBottom = 0;
       return;

     case A_JUMP_LEFT:
       SetA(A_JUMP_FALL_LEFT);
       //mJumpBottom = 0;
       return;

     case A_JUMP_UP_RIGHT:
       SetA(A_JUMP_UP_FALL_RIGHT);
       return;

     case A_JUMP_UP_LEFT:
       SetA(A_JUMP_UP_FALL_LEFT);
       return;

     case A_CROUCH_RECOVERY_FALL_LEFT:
       SetA2(A_CROUCH_RECOVERY_LEFT);
       return;

     case A_CROUCH_RECOVERY_FALL_RIGHT:
       SetA2(A_CROUCH_RECOVERY_RIGHT);
       return;

     case A_CROUCH_RECOVERY_LEFT:
       SetA2(A_CROUCH_RECOVERY_RISE_LEFT);
       return;

     case A_CROUCH_RECOVERY_RIGHT:
       SetA2(A_CROUCH_RECOVERY_RISE_RIGHT);
       return;

     case A_CROUCH_RECOVERY_RISE_LEFT:
     case A_CROUCH_UNDRAW_WEAPON_LEFT:
     case A_CROUCH_WALK_LEFT:
       SetA2(A_CROUCH_LEFT);
       return;

     case A_CROUCH_RECOVERY_RISE_RIGHT:
     case A_CROUCH_UNDRAW_WEAPON_RIGHT:
     case A_CROUCH_WALK_RIGHT:
       SetA2(A_CROUCH_RIGHT);
       return;

     case A_RUN_STOP_RIGHT:
       SetA2(A_STOP_RIGHT, AC_TO_RIGHT_WIDTH);
       return;

     case A_JUMP_OFF_EDGE_LEFT:
     case A_JUMP_UP_FALL_LEFT:
     case A_JUMP_FALL_LEFT:
       SetA(A_FALL_LEFT);
       return;

     case A_JUMP_UP_FALL_RIGHT:
     case A_JUMP_FALL_RIGHT:
     case A_JUMP_OFF_EDGE_RIGHT:
       SetA(A_FALL_RIGHT);
       return;

     case A_IDLE_RIGHT:
     case A_USING_RIGHT:
     case A_UNDRAW_WEAPON_RIGHT:
     case A_RECOVERY_RISE_RIGHT:
       SetA2(A_STOP_RIGHT);
       return;

     case A_IDLE_LEFT:
     case A_USING_LEFT:
     case A_UNDRAW_WEAPON_LEFT:
     case A_RUN_STOP_LEFT:
     case A_RECOVERY_RISE_LEFT:
     case A_MOVE_LEFT:
       SetA2(A_STOP_LEFT);
       return;

     case A_PREPARE_JUMP_RIGHT:
       //mRealX+=8;
       SetA(A_JUMP_RIGHT);
       return;

     case A_PREPARE_JUMP_LEFT:
       // adjust x, so it won't seem the player takes a step backward (there is a
       // width difference between prepare jump and jump positions
       //mRealX -= 8;

       SetA(A_JUMP_LEFT);
       return;

     case A_RECOVERY_FALL_LEFT:
       SetA2(A_RECOVERY_LEFT);
       return;

     case A_RECOVERY_FALL_RIGHT:
       SetA2(A_RECOVERY_RIGHT);
       return;

     case A_RECOVERY_LEFT:
       SetA2(A_RECOVERY_RISE_LEFT);
       return;

     case A_RECOVERY_RIGHT:
       SetA2(A_RECOVERY_RISE_RIGHT);
       return;

     case A_CROUCH_DYING_LEFT:
     case A_DYING_LEFT:
       SetA2(A_DEAD_LEFT);
       return;

     case A_CROUCH_DYING_RIGHT:
     case A_DYING_RIGHT:
       SetA2(A_DEAD_RIGHT);
       return;

     case A_MOVE_RIGHT:
       SetA2(A_STOP_RIGHT, AC_TO_RIGHT_WIDTH);
       return;

     case A_TARGETING_LEFT:
       SetA2(A_WEAPON_IDLE_LEFT);
       return;

     case A_TARGETING_RIGHT:
       SetA2(A_WEAPON_IDLE_RIGHT);
       return;

     case A_WEAPON_IDLE_LEFT:
       SetA2(A_TARGETING_LEFT);
       return;

     case A_WEAPON_IDLE_RIGHT:
       SetA2(A_TARGETING_RIGHT);
       return;

     case A_DRAW_WEAPON_RIGHT:
       if (mDirectFire && !Reloading())
       {
         SetA2(A_FIRE_RIGHT);
         Fire(CObjBullet.A_MOVE_RIGHT);
         Flame(CObjBullet.A_MOVE_RIGHT);
         mDirectFire = false;
         return;
       }

       SetA2(A_TARGETING_RIGHT);
       return;

     case A_DRAW_WEAPON_LEFT:
       if (mDirectFire && !Reloading())
       {
         SetA2(A_FIRE_LEFT);
         //mActions[mCurrentAction].mAlign = Graphics.RIGHT | Graphics.TOP;

         Fire(CObjBullet.A_MOVE_LEFT);
         Flame(CObjBullet.A_MOVE_LEFT);
         mDirectFire = false;
         return;
       }

       SetA2(A_TARGETING_LEFT);
       return;

     case A_CROUCH_DRAW_WEAPON_LEFT:
       if (mDirectFire && !Reloading())
       {
         SetA2(A_CROUCH_FIRE_LEFT);
         Fire(CObjBullet.A_MOVE_LEFT);
         Flame(CObjBullet.A_MOVE_LEFT);
         mDirectFire = false;
         return;
       }

       SetA2(A_CROUCH_TARGETING_LEFT);
       return;

     case A_CROUCH_DRAW_WEAPON_RIGHT:
       if (mDirectFire && !Reloading())
       {
         SetA2(A_CROUCH_FIRE_RIGHT);
         Fire(CObjBullet.A_MOVE_RIGHT);
         Flame(CObjBullet.A_MOVE_RIGHT);
         mDirectFire = false;
         return;
       }

       SetA2(A_CROUCH_TARGETING_RIGHT);
       return;

     case A_FIRE_LEFT:
       if (mAmmo == 0)
       {
         SetA2(A_UNDRAW_WEAPON_LEFT, AC_TO_RIGHT_WIDTH);
         Blink();
         return;
       }

       SetA2(A_TARGETING_LEFT);
       return;

     case A_FIRE_RIGHT:
       if (mAmmo == 0)
       {
         SetA2(A_UNDRAW_WEAPON_RIGHT);
         Blink();
         return;
       }

       SetA2(A_TARGETING_RIGHT);
       return;

     case A_CROUCH_FIRE_LEFT:
       if (mAmmo == 0)
       {
         SetA2(A_CROUCH_UNDRAW_WEAPON_LEFT, AC_TO_RIGHT_WIDTH);
         Blink();
       }
       else
       {
         SetA2(A_CROUCH_TARGETING_LEFT);
       }

       return;

     case A_CROUCH_FIRE_RIGHT:
       if (mAmmo == 0)
       {
         SetA2(A_CROUCH_UNDRAW_WEAPON_RIGHT);
         Blink();
         return;
       }

       SetA2(A_CROUCH_TARGETING_RIGHT);
       return;

     case A_PREPARE_DESCEND_LADDER_DOWN:
       SetA(A_DESCEND_LADDER_DOWN);
       return;

     case A_DESCEND_LADDER_DOWN:
       //System.out.println(mRealX + ", " + mRealY);
       SetA(A_CLIMB_LADDER);
       //System.out.println(mRealX + ", " + mRealY);

       //mActions[mCurrentAction].mFinished = false;
       mActions.SetAutoUpdate(mCurrentAction, false);
       mActions.SetFrameDelta(mCurrentAction, 1);
       mActions.SetSpeedYFrames(mCurrentAction, (byte)-CLIMB_SPEED);
       return;

     case A_DESCEND_LADDER_UP:
       SetA(A_DESCEND_LADDER_UP_JUMP);

       mRealY -= 10;
       return;

     case A_DESCEND_LADDER_UP_JUMP:
       SetA(A_DESCEND_LADDER_UP_FALL);
       return;

     case A_CLIMB_EDGE_UP_RIGHT:
       mRealY -= 6;
       mActions.SetRepeatMax(A_JUMP_OFF_EDGE_RIGHT, 5);
       SetA2(A_JUMP_OFF_EDGE_RIGHT);
       //mRealX += 3;
       return;

     case A_CLIMB_EDGE_UP_LEFT:
       mRealY -= 6;
       mActions.SetRepeatMax(A_JUMP_OFF_EDGE_LEFT, 5);
       SetA2(A_JUMP_OFF_EDGE_LEFT);
       //mRealX -= 3;
       return;
   }
 }

 /*
 public void Paint(Graphics g, int _x, int _y)
 {
   super.Paint(g, _x, _y);

   if (mTunning)
   {
     g.setColor(0, 0, 0);
     g.fillRect(0, 0, 96, 12);

     GameScreen.getInstance().mFont.drawString(g, 0, 1, CFont.F_ALIGN_LEFT,
                                               "a:" + mTunningAction + "/" +
                                               mCurrentAction);
     GameScreen.getInstance().mFont.drawString(g, 30, 1, CFont.F_ALIGN_LEFT,
                                               "sx:" +
                                               mActions[mTunningAction].mSpeedX);
     GameScreen.getInstance().mFont.drawString(g, 55, 1, CFont.F_ALIGN_LEFT,
                                               "sy:" +
                                               mActions[mTunningAction].mSpeedY);
     GameScreen.getInstance().mFont.drawString(g, 80, 1, CFont.F_ALIGN_LEFT,
                                               "u:" +
                                               mActions[mTunningAction].mUpdateMax);

     int lX = 0;

     switch (mTunningParameter) {
       case TUNNING_MODIFY_ACTION:
         lX = 0;
         break;

       case TUNNING_MODIFY_SPEEDX:
         lX = 30;
         break;

       case TUNNING_MODIFY_SPEEDY:
         lX = 55;
         break;

       case TUNNING_MODIFY_UPDATE:
         lX = 80;
         break;
     }

     g.setColor(255, 0, 0);
     g.drawLine(lX, 10, lX + 10, 10);
   }
 }
*/

  //public void Paint(Graphics g, int _x, int _y)
  //{
    //super.Paint(g, _x, _y);

    /*
    if (mCurrentAction != 0 && mCurrentAction != 1 && mCurrentAction != 3 && mCurrentAction != 2)
    {
        //System.out.println("action: " + GetA() + "/" + mActions[GetA()].mAnimFrame + "....." + mRealX + "," + mRealY);
      g.setColor(0xFFFFFF);
    }
    else
    {
      g.setColor(0xFF0000);
    }

    g.drawString(Integer.toString(mCurrentAction), _x, _y + 5, Graphics.RIGHT|Graphics.TOP);

    g.setColor(0xFFFFFF);
    g.drawString(Integer.toString(mActions[GetA()].mAnimFrame), _x, _y + 15, Graphics.RIGHT|Graphics.TOP);
    */

    //if (mCanGrab)
    //{
    //  g.setColor(0xFFFFFF);
    //  g.drawString("Y", _x, _y,
    //               Graphics.RIGHT | Graphics.TOP);
    //}
  //}

  public final void BeginCollision()
  {
    mElevatorCollision    = false;
    mCollisionCount       = 0;
    mCanClimbUp           = false;
    mCanClimbDown         = false;
    mCanGrabLadderDown_Temp = 0;
    mCanGrabLadderDown    = false;
    mCanGrabLadderUp      = false;
    mCanDescendLadderDown = false;
    mCanDescendLadderUp   = false;
    //mStickLeft          = false;
    //mStickRight         = false;
    mCanGrab              = false;
    mCloseCanGrab         = false;
    mLeftCollisionCount   = 0;
    mRightCollisionCount  = 0;
    mCanPushButton        = false;
  }

  //
  // Checks if the player felt outside the map
  //
  private final void CheckFallDeath()
  {
    if (mBB.mBottom >= (mLevel.mEnvManager.GetHeight() << 4) - 16)
    {
      new TriggersObserver().notifyPlayerIsDead();
      //SetHP(0);
      //mTerminated = true;
    }
  }

  public final void ManageTileCollision()
  {
    //if (mFreeze)
    //{
    //  return;
    //}

    // these actions are not to be treated as collidable
    if (Does(A_CLIMB_EDGE_UP_RIGHT) || Does(A_CLIMB_EDGE_UP_LEFT) ||
        //Does(A_CLOSE_CLIMB_EDGE_UP_LEFT) || Does(A_CLOSE_CLIMB_EDGE_UP_RIGHT) ||
        Does(A_GRAB_EDGE_RIGHT) || Does(A_GRAB_EDGE_LEFT)
        || Does(A_DESCEND_LADDER_DOWN) || Does(A_DESCEND_LADDER_UP) ||
        Does(A_JUMP_OFF_EDGE_LEFT) || Does(A_JUMP_OFF_EDGE_RIGHT) ||
        mCurrentAction == A_DESCEND_LADDER_UP_JUMP || //mCurrentAction == A_DESCEND_LADDER_UP_FALL ||
        mCurrentAction == A_PREPARE_DESCEND_LADDER_DOWN)
    {
      return;
    }

    //CollisionResponseData lCollision = TileCollision(_tile, _x, _y);
    TileCollision();

    //int lCollisionFlags = mCollision.GetFlags();
    int lCollisionFlags = mCollision[COLLISION_RESPONSE_FLAG];

    if (lCollisionFlags == 0)
    {
      return;
    }

    if ((lCollisionFlags & C_HEAD_LADDER) != 0)
    {
      //mCollisionCount = 1;

      if (Does(A_CLIMB_LADDER))
      {
        if (mActions.GetSpeedY(mSpeedIndex) < 0)
        {
          mCanDescendLadderUp = true;
        }
      }
      else
      {
        //mHeadLadderLocation.x = mCollision.mIntData[0];
        //mHeadLadderLocation.y = mCollision.mIntData[1];
        mHeadLadderLocation.x = mCollision[COLLISION_RESPONSE_VAL1];
        mHeadLadderLocation.y = mCollision[COLLISION_RESPONSE_VAL2];
        mCanDescendLadderDown = true;
        mCanGrabLadderDown    = false;
        mCanGrabLadderUp      = false;
      }

      //return;
    }

    if ((lCollisionFlags & C_LADDER) != 0)
    {
      // unless this is set to 1, the player will fall through the ladder
      // (in some special cases)

      //mCollisionCount = 1;

      //mCanClimbUp = true;
      if (mCanDescendLadderDown)
      {
        mCanGrabLadderUp = false;
      }
      else
      {
        mCanGrabLadderUp = true;
      }

      mCanClimbUp   = true;

      if (mCanGrabLadderDown_Temp == 0)
      {
        mCanClimbDown = true;
      }

      //mLadderLocation.x = mCollision.mIntData[0];
      mLadderLocation.x = mCollision[COLLISION_RESPONSE_VAL1];

      if (!mCanDescendLadderDown)
      {
        mCanGrabLadderDown = true;
      }

      if (Does(A_CLIMB_LADDER))
      {
        mRealX = mLadderLocation.x - GetWidth() / 2;
        GetBB();
      }
      //return;
    }

    if ((lCollisionFlags & C_UP) != 0)
    {
      switch(mCurrentAction)
      {
        case A_JUMP_LEFT:
        //case A_JUMP_FALL_LEFT:
          SetA(A_FALL_LEFT);
          break;

        case A_JUMP_RIGHT:
        //case A_JUMP_FALL_RIGHT:
          SetA(A_FALL_RIGHT);
          break;

        case A_STOP_LEFT:
        case A_MOVE_LEFT:
          SetA2(A_CROUCH_LEFT);
          break;

        case A_STOP_RIGHT:
        case A_MOVE_RIGHT:
          SetA2(A_CROUCH_RIGHT);
          break;
      }
    }

    if ((lCollisionFlags & C_DOWN) != 0)
    {
      mCollisionCount++;

      switch(mCurrentAction)
      {
        case A_DESCEND_LADDER_UP_FALL:
        case A_FALL_LEFT:
          SetA2(A_STOP_LEFT);
          break;

        case A_FALL_RIGHT:
          SetA2(A_STOP_RIGHT);
          break;

        case A_JUMP_FALL_RIGHT:
          //SetA2(A_CROUCH_RIGHT);
          SetA2(A_FALL_RIGHT);
          break;

        case A_JUMP_FALL_LEFT:
          SetA2(A_FALL_LEFT);
          break;

        case A_DYING_LEFT:
        case A_AIR_DYING_FALL_LEFT:
          SetA2(A_DEAD_LEFT);
          break;

        case A_DYING_RIGHT:
        case A_AIR_DYING_FALL_RIGHT:
          SetA2(A_DEAD_RIGHT);
          break;

        case A_AIR_RECOVERY_FALL_LEFT:
          SetA2(A_RECOVERY_LEFT);
          break;

        case A_AIR_RECOVERY_FALL_RIGHT:
          SetA2(A_RECOVERY_RIGHT);
          break;

        case A_CLIMB_LADDER:
          mCanClimbDown = false;
          break;
      }

      mCanGrabLadderDown_Temp++;
    }

    if ((lCollisionFlags & C_RIGHT) != 0)
    {
      switch(mCurrentAction)
      {
        case A_MOVE_RIGHT:
        case A_RUN_RIGHT:
        case A_RUN_STOP_RIGHT:
        case A_CROUCH_WALK_RIGHT:
        case A_JUMP_RIGHT:
        case A_JUMP_FALL_RIGHT:
        case A_CROUCH_RIGHT:
          mRightCollisionCount++;
          break;
      }
    }

    if ((lCollisionFlags & C_LEFT) != 0)
    {
      switch(mCurrentAction)
      {
        case A_MOVE_LEFT:
        case A_RUN_LEFT:
        case A_RUN_STOP_LEFT:
        case A_CROUCH_WALK_LEFT:
        case A_JUMP_LEFT:
        case A_JUMP_FALL_LEFT:
        case A_CROUCH_LEFT:
          mLeftCollisionCount++;
          break;
      }
    }
  }

  public final void EndCollision()
  {
    //if (mFreeze)
    //{
    //  return;
    //}

    for(int i = 0; i < mGrabPointsSize; i++)
    {
      CPoint lPoint = (CPoint)mLevel.mEnvManager.mGrabPoints.elementAt(i);
      //CPoint lPoint = mEnvManager.mGrabPoints[i];

      if (GrabPointCollision(lPoint))
      {
        mCanGrab = true;
        mGrabPoint = lPoint;
        break;
      }
    }

    for(int i = 0; i < mCloseGrabPointsSize; i++)
    {
      CPoint lPoint = (CPoint)mLevel.mEnvManager.mCloseGrabPoints.elementAt(i);
      //CPoint lPoint = mEnvManager.mCloseGrabPoints[i];

      if (GrabPointCollision(lPoint))
      {
        mCloseCanGrab   = true;
        mCloseGrabPoint = lPoint;
        //mCanGrab = true;
        //mGrabPoint = lPoint;
        break;
      }
    }

    if (Does(A_JUMP_UP_RIGHT) || Does(A_JUMP_UP_FALL_RIGHT))
    {
      //System.out.println(mJumpBottom + "..." + mBB.mBottom);
      if (mCanGrab)
      {
        SetA(A_GRAB_EDGE_RIGHT);

        // set the grab point over the hot spot
        mRealX = -mXSprite.GetHotX(mAnimFrame, false) + mGrabPoint.x;
        mRealY = -mXSprite.GetHotY(mAnimFrame) + mGrabPoint.y;

        GetBB();

        return;
      }

      if (mCloseCanGrab && Does(A_JUMP_UP_RIGHT))
      {
        //mActions.SetRepeatMax(A_JUMP_OFF_EDGE_RIGHT, 4);
        SetA(A_JUMP_OFF_EDGE_RIGHT);
        //SetA(A_GRAB_EDGE_RIGHT);

        // set the grab point over the hot spot
        //mRealX = mCloseGrabPoint.x - GetWidth();
        //mRealY = mCloseGrabPoint.y - 8;

        //GetBB();

        return;
      }
    }

    if (Does(A_JUMP_UP_LEFT) || Does(A_JUMP_UP_FALL_LEFT))
    {
      if (mCanGrab)
      {
        SetA(A_GRAB_EDGE_LEFT);

        // set the grab point over the hot spot
        mRealX = mXSprite.GetHotX(mAnimFrame, true) + mGrabPoint.x;
        mRealY = -mXSprite.GetHotY(mAnimFrame) + mGrabPoint.y;

        GetBB();

        return;
      }

      if (mCloseCanGrab && Does(A_JUMP_UP_LEFT))
      {
        //mActions.SetRepeatMax(A_JUMP_OFF_EDGE_LEFT, 4);
        SetA(A_JUMP_OFF_EDGE_LEFT);

        // set the grab point over the hot spot
        //mRealX = mCloseGrabPoint.x;
        //mRealY = mCloseGrabPoint.y - 8;

        //GetBB();

        return;
      }
    }

    if (mRightCollisionCount > 0)
    {
      //System.out.println("right col count");

      if (Does(A_MOVE_RIGHT) || Does(A_RUN_RIGHT) || Does(A_RUN_STOP_RIGHT))
      {
        // CLOSE CLIMB FEATURE
        //if (mCanGrab)
        //{
        //  SetA(A_CLOSE_CLIMB_EDGE_UP_RIGHT);
        //  return;
        //}

        if (Does(A_RUN_STOP_RIGHT))
        {
          //System.out.println("runstopright->stopright");
        }

        SetA2(A_STOP_RIGHT, AC_TO_RIGHT_WIDTH);
      }

      if (Does(A_CROUCH_WALK_RIGHT))
      {
        SetA2(A_CROUCH_RIGHT);
      }

      if (Does(A_JUMP_RIGHT) || Does(A_JUMP_FALL_RIGHT))
      {
        //System.out.println(mJumpBottom + "..." + mBB.mBottom);

        if (mCanGrab)
        {
          //SetA(A_CLOSE_CLIMB_EDGE_UP_RIGHT);
          SetA(A_GRAB_EDGE_RIGHT);

          // set the grab point over the hot spot
          mRealX = -mXSprite.GetHotX(mAnimFrame, false) + mGrabPoint.x;
          mRealY = -mXSprite.GetHotY(mAnimFrame) + mGrabPoint.y;

          GetBB();

          return;
        }

        if (mAnimFrame <= mActions.GetAnimFrameStart(mCurrentAction) + 2 && Does(A_JUMP_RIGHT))
        {
          SetA(A_JUMP_UP_RIGHT);
          return;
        }

        SetA(A_FALL_RIGHT, AC_TO_RIGHT_WIDTH);
      }
    }

    if (mLeftCollisionCount > 0)
    {
      if (Does(A_MOVE_LEFT) || Does(A_RUN_LEFT) || Does(A_RUN_STOP_LEFT) || Does(A_RUN_STOP_RIGHT))
      {
        // CLOSE CLIMB FEATURE
        //if (mCanGrab)
        //{
        //  SetA(A_CLOSE_CLIMB_EDGE_UP_LEFT);
        //  return;
        //}
        SetA2(A_STOP_LEFT);
      }

      if (Does(A_CROUCH_WALK_LEFT))
      {
        SetA2(A_CROUCH_LEFT);
      }

      if (Does(A_JUMP_LEFT) || Does(A_JUMP_FALL_LEFT))
      {
        if (mCanGrab)
        {
          SetA(A_GRAB_EDGE_LEFT);

          // set the grab point over the hot spot
          mRealX = mXSprite.GetHotX(mAnimFrame, true) + mGrabPoint.x;
          mRealY = -mXSprite.GetHotY(mAnimFrame) + mGrabPoint.y;

          GetBB();

          return;
        }

        if (mAnimFrame <= mActions.GetAnimFrameStart(mCurrentAction) + 2 && Does(A_JUMP_LEFT))
        {
          SetA(A_JUMP_UP_LEFT);
          return;
        }

        SetA(A_FALL_LEFT);
      }
    }

    if (mCollisionCount == 0)
    {
        if (Does(A_STOP_LEFT) || Does(A_MOVE_LEFT) ||
            Does(A_CROUCH_LEFT) || Does(A_CROUCH_WALK_LEFT) ||
            Does(A_RUN_LEFT) || Does(A_RUN_STOP_LEFT) ||
            /*Does(A_PREPARE_JUMP_LEFT) || */Does(A_DRAW_WEAPON_LEFT) ||
            Does(A_TARGETING_LEFT) || Does(A_FIRE_LEFT) ||
            Does(A_HIDE_LEFT) || Does(A_UNDRAW_WEAPON_LEFT)
            )
        {
          // hack, otherwise will just change between stances (fall->stop)
          if (!mElevatorCollision)
          {
            mRealX -= 2;
          }

          //if (Does(A_CROUCH_WALK_RIGHT))
          //{
          //  System.out.println("fall from crouch_walk left");
          //}

          //System.out.println("FALL LEFT!");
          SetA2(A_FALL_LEFT, AC_TO_RIGHT_WIDTH);
        }

        if (Does(A_RUN_RIGHT) || Does(A_RUN_STOP_RIGHT) || Does(A_MOVE_RIGHT))
        {
          //mBB.mLeft += 2;
          if (!mElevatorCollision)
          {
            mRealX += 2;
          }
          SetA2(A_FALL_RIGHT, AC_TO_LEFT);
          return;
        }


        if (Does(A_STOP_RIGHT) ||
            Does(A_CROUCH_RIGHT) || Does(A_CROUCH_WALK_RIGHT) ||
            /*Does(A_PREPARE_JUMP_RIGHT) || */Does(A_DRAW_WEAPON_RIGHT) ||
            Does(A_TARGETING_RIGHT) || Does(A_FIRE_RIGHT) ||
            Does(A_HIDE_RIGHT) || Does(A_UNDRAW_WEAPON_RIGHT)
            )
        {
          // hack, otherwise will just change between stances (fall->stop)
          if (!mElevatorCollision)
          {
            mRealX += 2;
          }

          //if (Does(A_CROUCH_WALK_RIGHT))
          //{
          //  System.out.println("fall from crouch_walk right");
          //}

          //System.out.println("FALL RIGHT!");
          SetA2(A_FALL_RIGHT);
        }

        if (Does(A_RECOVERY_LEFT))
        {
          SetA2(A_AIR_RECOVERY_FALL_LEFT);
        }

        if (Does(A_RECOVERY_RIGHT))
        {
          SetA2(A_AIR_RECOVERY_FALL_RIGHT);
        }
    }

    if (mCanDescendLadderUp)
    {
      SetA(A_DESCEND_LADDER_UP);
      return;
    }

    if (mCanGrabLadderDown_Temp > 0)
    {
      mCanGrabLadderDown = false;
    }

    if (!mCanClimbUp && Does(A_CLIMB_LADDER))
    {
      SetA(A_FALL_LEFT);
    }

    if (!mCanClimbDown && Does(A_CLIMB_LADDER))
    {
      SetA(A_FALL_LEFT);
    }
  }

  public final void ManageObjCollision(CObjBase _obj)
  {
    //if (_obj == null)
    //{
    //  return;
    //}


    //if (lBox == null)
    //{
    //  return;
    //}

    /*
    if (_obj instanceof CObjAutomaticElevator)
    {
      if (((CObjAutomaticElevator)_obj).OverPlatform(mBB))
      {
        mCollisionCount = 1;
        mElevatorCollision = true;
        return;
      }
    }
    */

    if (_obj instanceof CObjElevator)
    {
      if (((CObjElevator)_obj).OverPlatform(mBB))
      {
        mCollisionCount = 1;
        mElevatorCollision = true;
        return;
      }
    }

    CBox lBox = _obj.mBB;

    if (!BBCollision(lBox))
    {
      return;
    }

    if (_obj instanceof CObjButton)
    {
      mCanPushButton = true;

      if (Does(A_USING_LEFT) || Does(A_USING_RIGHT))
      {
        ((engine.CObjButton)_obj).Activate();
        return;
      }
    }

    if (_obj instanceof CObjTrap)
    {
      SetHP(mHP - ((CObjTrap)_obj).GetDamage());

      //int lHitDirection = (mActions.GetEffect(mCurrentAction) == 0) ? FALL_TO_RIGHT : FALL_TO_LEFT;

      if (mHP <= 0)
      {
        SetDyingAction(HitDirectionByPosition(_obj));
      }
      else
      {
        //SetRecoveryAction(lHitDirection, HIT_BY_OTHER);
        SetRecoveryAction(HitDirectionByPosition(_obj), HIT_BY_OTHER);
      }

      _obj.mTerminated = true;

      // add an explosion
      mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mExplosionSprite, _obj.mRealX + _obj.GetWidth() / 2, _obj.mRealY + _obj.GetHeight() / 2, true, 5, Graphics.HCENTER | Graphics.VCENTER, 0));
    }

    //if (_obj instanceof CObjPlayer)
    //{
      //System.out.println("intersecting a player");
    //}

    if (_obj instanceof CObjAIShooter)
    {
      //System.out.println("intersecting an AI shooter ");

      // check if player is in an inactive state
      if (IsInactive())
      {
        return;
      }

      // check if the player is hiding or climbing ladders
      if (/*Does(A_CLIMB_LADDER) || */Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) || Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT))
      {
        return;
      }

      if (((engine.CObjAIShooter)_obj).IsDead())
      {
        return;
      }

      // off with the player HP
      SetHP(mHP - ((engine.CObjAIShooter)_obj).GetDamage());

      if (mHP <= 0)
      {
        SetDyingAction(HitDirection(_obj));
      }
      else
      {
        SetRecoveryAction(HitDirectionByPosition(_obj), HIT_BY_OTHER);
      }
    }

    if (_obj instanceof CObjAISlasher)
    {
      // is inactive?
      if (IsInactive())
      {
        return;
      }

      // check if the player is hiding or climbing ladders
      if (/*Does(A_CLIMB_LADDER) || */Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) || Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT))
      {
        return;
      }

      if (_obj.IsDead())
      {
        return;
      }

      // if the slasher is dead, no need to perform checks
      //System.out.println("intersecting an AI slasher ");

      // off with the player HP
      SetHP(mHP - ((CObjAISlasher)_obj).GetDamage());

      // is the player dead or will he just recover?
      if (mHP <= 0)
      {
        SetDyingAction(HitDirectionByPosition(_obj));
      }
      else
      {
        SetRecoveryAction(HitDirectionByPosition(_obj), HIT_BY_OTHER);
      }

      // terminte the slasher and add an explosion
      _obj.mTerminated = true;

      mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mExplosionSprite, _obj.mRealX + _obj.GetWidth() / 2, _obj.mRealY + _obj.GetHeight() / 2, true, 5, Graphics.HCENTER | Graphics.VCENTER, 0));
    }

    //System.out.println("player: check powerup");
    if (_obj instanceof CObjPowerUp)
    {
      //System.out.println("intersecting a powerup");

      switch(((CObjPowerUp)_obj).GetType())
      {
        case CObjPowerUp.POWER_AMMO:
          if (mAmmo < 5)
          {
            mAmmo += ( (CObjPowerUp) _obj).GetPower();

            if (mAmmo > 5)
            {
              mAmmo = 5;
            }
            _obj.mTerminated = true;
          }
          break;

        case CObjPowerUp.POWER_HEALTH:
          if (mHP < 3)
          {
            SetHP( ( (CObjPowerUp) _obj).GetPower() + mHP);
            _obj.mTerminated = true;
          }
          break;

        //case CObjPowerUp.POWER_WEAPON:
          //SetWeaponPower(GetWeaponPower() + ((CObjPowerUp)_obj).GetPower());
          //break;

        //case CObjPowerUp.POWER_MONEY:
          //SetMoney(GetMoney() + ((CObjPowerUp)_obj).GetPower());
          //break;
      }

    }

    if (_obj instanceof CObjBullet)
    {
      //System.out.println("player: hit by bullet");

      if (((engine.CObjBullet)_obj).mParent == this)
      {
        return;
      }

      if (IsInactive())
      {
        return;
      }

      // check if the player is hiding or climbing ladders
      if (/*Does(A_CLIMB_LADDER) || */Does(A_HIDE_LEFT) || Does(A_HIDE_RIGHT) || Does(A_HIDE_WITH_WEAPON_LEFT) || Does(A_HIDE_WITH_WEAPON_RIGHT))
      {
        return;
      }

      SetHP(mHP - ((engine.CObjBullet)_obj).GetDamage());

      // is the player dead or will he just recover?
      if (mHP <= 0)
      {
        SetDyingAction(HitDirection(_obj));
      }
      else
      {
        SetRecoveryAction(HitDirection(_obj), HIT_BY_BULLET);
      }

      ((engine.CObjBullet)_obj).HitConfirmed();
      _obj.mTerminated = true;
    }
  }

  private final void SetRecoveryAction(int _flag, int _hitBy)
  {
    if (_hitBy == HIT_BY_BULLET)
    {
      // check if the player is in the air
      switch(mCurrentAction)
      {
        case A_JUMP_UP_LEFT:
        case A_JUMP_UP_FALL_LEFT:
        case A_FALL_LEFT:
        case A_JUMP_LEFT:
        case A_JUMP_FALL_LEFT:
          SetA2(A_AIR_RECOVERY_FALL_LEFT);
          return;

        case A_JUMP_UP_RIGHT:
        case A_JUMP_UP_FALL_RIGHT:
        case A_FALL_RIGHT:
        case A_JUMP_RIGHT:
        case A_JUMP_FALL_RIGHT:
          SetA2(A_AIR_RECOVERY_FALL_RIGHT);
          return;
      }
    }

   switch(mCurrentAction)
   {
      case A_CROUCH_LEFT:
      case A_CROUCH_WALK_LEFT:
        SetA2(A_CROUCH_RECOVERY_FALL_LEFT);
        return;

      case A_CROUCH_RIGHT:
      case A_CROUCH_WALK_RIGHT:
        SetA2(A_CROUCH_RECOVERY_FALL_RIGHT);
        return;
    }

    switch(_flag)
    {
      case FALL_TO_LEFT:
        SetA2(A_RECOVERY_FALL_LEFT);
        break;

      case FALL_TO_RIGHT:
        SetA2(A_RECOVERY_FALL_RIGHT, AC_TO_LEFT);
        break;
    }
  }

  private final void SetDyingAction(int _flag)
  {
    // check if the player is in the air
    if (mCurrentAction == A_JUMP_UP_LEFT ||
        mCurrentAction == A_JUMP_UP_FALL_LEFT ||
        mCurrentAction == A_FALL_LEFT ||
        mCurrentAction == A_JUMP_LEFT ||
        mCurrentAction == A_JUMP_FALL_LEFT ||
        mCurrentAction == A_FALL_LEFT)
    {
      SetA(A_AIR_DYING_FALL_LEFT);
      return;
    }

    if (mCurrentAction == A_JUMP_UP_RIGHT ||
        mCurrentAction == A_JUMP_UP_FALL_RIGHT ||
        mCurrentAction == A_FALL_RIGHT ||
        mCurrentAction == A_JUMP_RIGHT ||
        mCurrentAction == A_JUMP_FALL_RIGHT ||
        mCurrentAction == A_FALL_RIGHT
        )
    {
      SetA(A_AIR_DYING_FALL_RIGHT);
      return;
    }

    if (mCurrentAction == A_CROUCH_LEFT || mCurrentAction == A_CROUCH_RIGHT ||
        mCurrentAction == A_CROUCH_WALK_LEFT || mCurrentAction == A_CROUCH_WALK_RIGHT)
    {
      SetA2(A_CROUCH_DYING_LEFT);
      return;
    }

    switch(_flag)
    {
      case FALL_TO_LEFT:
        SetA2(A_DYING_LEFT);
        break;

      case FALL_TO_RIGHT:
        SetA2(A_DYING_RIGHT, AC_TO_LEFT);
        break;
    }
  }

  //public final boolean IsInactive()
  //{
    // check if player is in an inactive state
  //  return mActions.GetInactive(mCurrentAction);
  //}

  protected final void Fire(int _fireDirection)
  {
    int lHotX;

    if (_fireDirection == CObjBullet.A_MOVE_LEFT)
    {
      // adjust the bullet starting point according to the alignment of the sprite (eg. RIGHT)
      // and to the orientation
      lHotX = mRealX + mXSprite.GetHotX(mAnimFrame, true) - mBulletSprite.GetWidth(0) - mXSprite.GetWidth(mAnimFrame) + 1;
    }
    else
    {
      lHotX = mBB.mLeft + mXSprite.GetHotX(mAnimFrame, false);// + mBulletSprite.GetWidth(0) + 1;
    }

    CObjBullet lBullet = new CObjBullet(this,
                                        mBulletSprite,
                                        lHotX,
                                        mRealY + mXSprite.GetHotY(mAnimFrame),
                                        _fireDirection,
                                        1,
                                        CObjBullet.GENERIC_TIMEOUT_DISTANCE);

    // verify if the bullet is not already in a collision state so we won't
    // bother inserting it
    // this happens if a bullet is created near a vertical edge (tile with
    // collision LEFT or RIGHT)
    lBullet.ManageTileCollision();

    if (!lBullet.mTerminated)
    {
      mLevel.mObjectManager.InsertObject(lBullet);
    }

    mTimeReload = 0;
  }

  private final void Flame(int _flameDirection)
  {
    int lFlameAlign = 0, lFlameEffect = 0;
    int lFlameX;

    if (_flameDirection == CObjBullet.A_MOVE_LEFT)
    {
      lFlameX = mRealX + mXSprite.GetHotX(mAnimFrame, true) - mXSprite.GetWidth(mAnimFrame) + 1;
      lFlameAlign = Graphics.RIGHT | Graphics.VCENTER;
      lFlameEffect = 0x20;//DirectGraphics.FLIP_HORIZONTAL;
    }
    else
    {
      lFlameAlign = Graphics.LEFT | Graphics.VCENTER;
      lFlameX = mRealX + mXSprite.GetHotX(mAnimFrame, false) + 1;
    }

    int lHotY = mRealY + mXSprite.GetHotY(mAnimFrame);// - mXSprite.GetHeight(mActions[GetA()].mAnimFrame);

    mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mFlameSprite, lFlameX, lHotY, true, 5, lFlameAlign, lFlameEffect));
  }

  private final boolean PointCollision(CPoint _p)
  {
    if (_p.x > mBB.mLeft + 3 && _p.x < mBB.mRight - 3 &&
        _p.y > mBB.mTop && _p.y < mBB.mBottom)
    {
      return true;
    }

    return false;
  }

  private final boolean GrabPointCollision(CPoint _p)
  {
   if (_p.x >= mBB.mLeft - 2 && _p.x <= mBB.mRight + 2 &&
      _p.y >= mBB.mTop && _p.y <= mBB.mBottom - 3)
   {
     return true;
   }

    return false;
  }

  private final boolean Reloading()
  {
    return (mTimeReload < mTimeReloadMax);
  }

  private final void Blink()
  {
    mLevel.mObjectManager.InsertObject(new CObjAnimation(mLevel, mBlinkSprite, GetMidX(), GetMidY() - 4, true, 8, Graphics.HCENTER | Graphics.VCENTER, 0));
  }

  private int HitDirection(CObjBase _obj)
  {
    return _obj.mActions.GetSpeedX(_obj.mSpeedIndex) > 0 ? FALL_TO_LEFT:FALL_TO_RIGHT;
  }

  private int HitDirectionByPosition(CObjBase _obj)
  {
    return _obj.GetMidX() > GetMidX() ? FALL_TO_RIGHT : FALL_TO_LEFT;
  }
}