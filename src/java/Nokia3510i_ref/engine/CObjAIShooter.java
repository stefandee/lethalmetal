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

public class CObjAIShooter extends CObjBase
{
  final static byte A_STOP_LEFT       = 0;
  final static byte A_STOP_RIGHT      = 1;
  final static byte A_TURN_LEFT_RIGHT = 2;
  final static byte A_TURN_RIGHT_LEFT = 3;
  final static byte A_DYING_RIGHT     = 4;
  final static byte A_DYING_LEFT      = 5;
  final static byte A_DEAD_RIGHT      = 6;
  final static byte A_DEAD_LEFT       = 7;
  final static byte A_FIRE_LEFT      = 8;
  final static byte A_FIRE_RIGHT     = 9;
  final static byte A_JOY_LEFT        = 10;
  final static byte A_JOY_RIGHT       = 11;
  final static byte A_RECOVERY_FALL_LEFT = 12;
  final static byte A_RECOVERY_FALL_RIGHT = 13;
  final static byte A_RECOVERY_RISE_LEFT = 14;
  final static byte A_RECOVERY_RISE_RIGHT = 15;
  final static byte A_RECOVERY_LEFT = 16;
  final static byte A_RECOVERY_RIGHT = 17;
  final static byte A_HIDE_LEFT = 18;
  final static byte A_HIDE_RIGHT = 19;
  final static byte A_TARGET_LEFT      = 20;
  final static byte A_TARGET_RIGHT     = 21;
  final static byte A_CROUCH_FIRE_LEFT      = 22;
  final static byte A_CROUCH_FIRE_RIGHT     = 23;
  final static byte A_MOVE_LEFT      = 24;
  final static byte A_MOVE_RIGHT     = 25;
  final static byte A_CROUCH_TARGET_LEFT      = 26;
  final static byte A_CROUCH_TARGET_RIGHT     = 27;
  final static byte A_CROUCH_RECOVERY_FALL_LEFT = 28;
  final static byte A_CROUCH_RECOVERY_FALL_RIGHT = 29;
  final static byte A_CROUCH_RECOVERY_RISE_LEFT = 30;
  final static byte A_CROUCH_RECOVERY_RISE_RIGHT = 31;
  final static byte A_CROUCH_RECOVERY_LEFT = 32;
  final static byte A_CROUCH_RECOVERY_RIGHT = 33;
  final static byte A_CROUCH_DYING_RIGHT     = 34;
  final static byte A_CROUCH_DYING_LEFT      = 35;

  //private CObjectManager  mObjectManager;
  //private CXSpriteManager mSpriteManager;
  //private CEnvManager mEnvManager;

  int mDamage, mAggresivity, mPatrolDistance, mBulletsDrop;
  int mMinExtentX, mMaxExtentX, mStartX;
  int mLastPlayerDistance = 0;
  CObjPlayer mPlayer;
  byte mTimeReload = 0, mTimeReloadMax = 90;
  //CXSprite mFlameSprite, mBulletSprite, mExplosionSprite;
  int mJoyTime, mJoyTimeMax;

  public CObjAIShooter(CLevel _level, CXSprite _sprite, int _x, int _y, int _stance, int _health, int _damage, int _recoveryTime, int _joyTime, int _aggresivity, int _patrolDistance, int _bulletsDrop)
  {
    super(_level, _sprite, _x, _y);

    //mObjectManager = _manager;
    //mSpriteManager = _spriteManager;
    //mEnvManager = _envManager;
    mPlayer = mLevel.mObjectManager.mPlayer;

    mActions = mLevel.mObjectManager.mAIShooterActions;

    // actions customizations
    mActions.SetUpdateMax(A_RECOVERY_LEFT, _recoveryTime);
    mActions.SetUpdateMax(A_RECOVERY_RIGHT, _recoveryTime);
    mActions.SetUpdateMax(A_CROUCH_RECOVERY_LEFT, _recoveryTime);
    mActions.SetUpdateMax(A_CROUCH_RECOVERY_RIGHT, _recoveryTime);

    // chose the starting action based on the "stance" parameter
    if (_stance == 0)
    {
      mRealX += GetWidth();
      SetA(A_STOP_LEFT);
    }
    else
    {
      SetA(A_STOP_RIGHT);
    }


    mDamage         = _damage;
    mAggresivity    = _aggresivity;
    mPatrolDistance = _patrolDistance;
    mBulletsDrop    = _bulletsDrop;
    mJoyTimeMax     = _joyTime;
    mJoyTime        = 0;
    mMaxHP          = mHP = _health;

    mStartX = _x;
    mMinExtentX = Math.max(0, mStartX - (mPatrolDistance << 4));

    // CODE SIZE REDUCTION
    mMaxExtentX = Math.min(mLevel.mEnvManager.GetWidth() << 4, mStartX + (mPatrolDistance << 4));
    //mMaxExtentX = Math.min(mLevel.GetWidth() << 4, mStartX + (mPatrolDistance << 4));

    // recompute mRealY since it came align to top and this object is aligned to
    // bottom
    mRealY = mRealY + GetHeight();
    GetBB();

    mCanCollideWithObj    = true;
    mCanCollideWithTile   = false;
    mCanCollideWithBullet = true;

    //mBulletSprite    = mSpriteManager.AddSprite("BULL");
    //mFlameSprite     = mSpriteManager.AddSprite("RED_");
    //mExplosionSprite = mSpriteManager.AddSprite("EXPL");
  }

  public void Update()
  {
    super.Update();

    //if (IsDead())
    //{
    //  return;
    //}

    //
    // WEAPON RELOAD
    //
    if (mTimeReload <= mTimeReloadMax)
    {
      mTimeReload++;
    }

    //
    // COMMON AI BEHAVIOUR
    //
    //if (IsPlayerInactive() && !IsInactive() && mCurrentAction != A_JOY_LEFT && mCurrentAction != A_JOY_RIGHT)
    //{
    //  SetA(A_JOY_LEFT);
    //  return;
    //}

    //if ((mPlayer.GetA() == mPlayer.A_FIRE_LEFT || mPlayer.GetA() == mPlayer.A_FIRE_RIGHT) &&
    //    mActions[mCurrentAction].mFinished && !IsDead())
    //{
    //  SetA(A_HIDE_LEFT, AC_TO_RIGHT);
    //  return;
    //}

    boolean lChance;

    switch(mAggresivity)
    {
      /*
      case 0:
        //  turn around and simulate look
        //lChance = (System.currentTimeMillis() % 32) == 0;

        if (Finished() && lChance)
        {
          switch(mCurrentAction)
          {
            case A_STOP_LEFT:
              SetA(A_STOP_RIGHT, AC_TO_LEFT);
              break;

            case A_STOP_RIGHT:
              SetA(A_STOP_LEFT, AC_TO_RIGHT);
              break;
          }
        }
       break;
       */

      case 1:
        // just move between the boundaries
        if (Does(A_STOP_LEFT))
        {
          SetA(A_MOVE_LEFT, AC_TO_RIGHT_WIDTH);
          return;
        }

        if (Does(A_STOP_RIGHT))
        {
          SetA(A_MOVE_RIGHT, AC_TO_RIGHT);
          return;
        }

        if (mRealX < mMinExtentX && mCurrentAction == A_MOVE_LEFT)
        {
          SetA(A_MOVE_RIGHT, AC_TO_RIGHT);
          return;
        }

        if (mRealX > mMaxExtentX && mCurrentAction == A_MOVE_RIGHT)
        {
          SetA(A_MOVE_LEFT, AC_TO_LEFT);
          return;
        }
        break;

      case 2:
        // from time to time stop and look in the opposite direction
        if (Does(A_MOVE_RIGHT) || Does(A_MOVE_LEFT))
        {
          lChance = (System.currentTimeMillis() % 64) == 0;

          if (Does(A_MOVE_RIGHT) && lChance)
          {
            SetA(A_STOP_LEFT);
          }

          if (Does(A_MOVE_LEFT) && lChance)
          {
            SetA(A_STOP_RIGHT);
          }
        }

        if (Finished())
        {

          if (Does(A_STOP_LEFT))
          {
            SetA(A_MOVE_RIGHT);
          }

          if (Does(A_STOP_RIGHT))
          {
            SetA(A_MOVE_LEFT);
          }
        }

        if (mRealX < mMinExtentX && mCurrentAction == A_MOVE_LEFT)
        {
          SetA(A_MOVE_RIGHT, AC_TO_RIGHT);
          return;
        }

        if (mRealX > mMaxExtentX && mCurrentAction == A_MOVE_RIGHT)
        {
          SetA(A_MOVE_LEFT, AC_TO_LEFT);
          return;
        }

        break;
    }

    //
    // Check if the player is close and start the combat sequence
    //
    if (PlayerInRange() && !IsInactive())
    {
      // common combat case for all aggresivity levels
      if ((mCurrentAction == A_HIDE_LEFT || mCurrentAction == A_HIDE_RIGHT) && Finished())
      {
        TargetPlayer();
        return;
      }

      if (mCurrentAction == A_STOP_LEFT || mCurrentAction == A_MOVE_LEFT ||
          mCurrentAction == A_STOP_RIGHT || mCurrentAction == A_MOVE_RIGHT)
      {
          TargetPlayer();
          return;
      }


      if ((mPlayer.mCurrentAction == CObjPlayer.A_FIRE_LEFT ||
          mPlayer.mCurrentAction == CObjPlayer.A_FIRE_RIGHT))
      {
        lChance = (System.currentTimeMillis() % 16) == 0;

        if (lChance)
        {
          switch(mCurrentAction)
          {
            case A_STOP_LEFT:
            case A_TARGET_LEFT:
              SetA(A_HIDE_LEFT);
              break;

            case A_MOVE_LEFT:
              SetA(A_HIDE_LEFT, AC_TO_RIGHT);
              break;

            case A_STOP_RIGHT:
            case A_TARGET_RIGHT:
              SetA(A_HIDE_RIGHT);
              break;

            case A_MOVE_RIGHT:
              SetA(A_HIDE_RIGHT, AC_TO_RIGHT_WIDTH);
              break;
          }
          return;
        }
      }

      if (Finished() && !Reloading())
      {
        if (Does(A_TARGET_LEFT))
        {
          SetA(A_FIRE_LEFT);
          Fire(CObjBullet.A_MOVE_LEFT);
          mTimeReload = 0;
          return;
        }

        if (Does(A_TARGET_RIGHT))
        {
          SetA(A_FIRE_RIGHT);
          Fire(CObjBullet.A_MOVE_RIGHT);
          mTimeReload = 0;
          return;
        }
      }

      // for aggresivity medium and high, the shooter will look if the player is in crouching and will react accordingly
      // the reaction is switch to crouch mode and start to fire
      switch(mAggresivity)
      {
        case 1:
        case 2:
          // if the shooter is not in a crouch stance and the player is, adjust the shooter stance to crouch
          if (mCurrentAction != A_CROUCH_TARGET_LEFT && mCurrentAction != A_CROUCH_TARGET_RIGHT &&
              mCurrentAction != A_CROUCH_FIRE_LEFT && mCurrentAction != A_CROUCH_FIRE_RIGHT && mCurrentAction != A_JOY_LEFT &&
              mCurrentAction != A_JOY_RIGHT)
          {
            lChance = ((System.currentTimeMillis() % 16) == 1);

            if (IsPlayerCrouched() && lChance)
            {
              CrouchTargetPlayer();
              return;
            }
          }

          // if the player left the crouch stance, adjust the shooter stance
          if ((mCurrentAction == A_CROUCH_TARGET_LEFT || mCurrentAction == A_CROUCH_TARGET_RIGHT) && !mActionFinished)//!mActions[mCurrentAction].mFinished)
          {
            lChance = (System.currentTimeMillis() % 8) == 0;

            if (!IsPlayerCrouched() && lChance)
            {
              TargetPlayer();
              return;
            }
          }

          if (Finished() && !Reloading())
          {
            if (Does(A_CROUCH_TARGET_LEFT))
            {
              SetA(A_CROUCH_FIRE_LEFT);
              Fire(CObjBullet.A_MOVE_LEFT);
              mTimeReload = 0;
              return;
            }

            if (Does(A_CROUCH_TARGET_RIGHT))
            {
              SetA(A_CROUCH_FIRE_RIGHT);
              Fire(CObjBullet.A_MOVE_RIGHT);
              mTimeReload = 0;
              return;
            }
          }
          break;
      }
    }
    else
    {
      if (mCurrentAction == A_HIDE_LEFT || mCurrentAction == A_TARGET_LEFT || mCurrentAction == A_CROUCH_TARGET_LEFT)
      {
        SetA(A_STOP_LEFT);
        return;
      }

      if (mCurrentAction == A_HIDE_RIGHT || mCurrentAction == A_TARGET_RIGHT || mCurrentAction == A_CROUCH_TARGET_RIGHT)
      {
        SetA(A_STOP_RIGHT);
        return;
      }

      //if (Does(A_HIDE_LEFT))
      //{
      //  SetA(A_STOP_LEFT);
      //  return;
      //}

      //if (Does(A_HIDE_RIGHT))
      //{
      //  SetA(A_STOP_RIGHT);
      //  return;
      //}
    }

    //
    // Finished
    //
    //if (mActions[mCurrentAction].mFinished)
    if (mActionFinished)
    {
      OnFinished();
    }

    //
    // DYING ACTION
    //
    /*
    if (mActions[mCurrentAction].mUpdate == 0)
    {
      if (Does(A_DYING_LEFT) || Does(A_DYING_RIGHT) ||
          Does(A_RECOVERY_FALL_LEFT) || Does(A_RECOVERY_FALL_RIGHT) ||
          Does(A_RECOVERY_RISE_LEFT) || Does(A_RECOVERY_RISE_RIGHT) ||
          //Does(A_RECOVERY_LEFT) || Does(A_RECOVERY_RIGHT) ||
          Does(A_CROUCH_RECOVERY_FALL_LEFT) || Does(A_CROUCH_RECOVERY_FALL_RIGHT) ||
          Does(A_CROUCH_RECOVERY_RISE_LEFT) || Does(A_CROUCH_RECOVERY_RISE_RIGHT) ||
          Does(A_CROUCH_RECOVERY_LEFT) || Does(A_CROUCH_RECOVERY_RIGHT))
      {
        UpdateBottom();
        return;
      }
    }
    */
  }

  public final void OnFinished()
  {
    switch(mCurrentAction)
    {
      case A_DYING_LEFT:
      case A_CROUCH_DYING_LEFT:
        SetA(A_DEAD_LEFT, AC_TO_LEFT);
        return;

      case A_DYING_RIGHT:
      case A_CROUCH_DYING_RIGHT:
        SetA(A_DEAD_RIGHT, AC_TO_RIGHT);
        return;

      case A_RECOVERY_FALL_LEFT:
        SetA(A_RECOVERY_LEFT);
        return;

      case A_RECOVERY_FALL_RIGHT:
        SetA(A_RECOVERY_RIGHT);
        return;
    }

    /*
    if (Does(A_DYING_LEFT))
    {
      SetA(A_DEAD_LEFT, AC_TO_LEFT);
      return;
    }

    if (Does(A_DYING_RIGHT))
    {
      SetA(A_DEAD_RIGHT, AC_TO_RIGHT);
      return;
    }

    if (Does(A_RECOVERY_FALL_LEFT))
    {
      SetA(A_RECOVERY_LEFT);
      return;
    }

    if (Does(A_RECOVERY_FALL_RIGHT))
    {
      SetA(A_RECOVERY_RIGHT);
      return;
    }
   */

    if (Does(A_RECOVERY_LEFT))
    {
      SetA(A_RECOVERY_RISE_LEFT);
      return;
    }

    if (Does(A_RECOVERY_RIGHT))
    {
      SetA(A_RECOVERY_RISE_RIGHT);
      return;
    }

    if (Does(A_RECOVERY_RISE_LEFT))
    {
      // set a probability here to hide instead of stop
      boolean lChance = (System.currentTimeMillis() % 4) == 0;

      if (lChance)
      {
        SetA(A_HIDE_LEFT);
      }
      else
      {
        SetA(A_STOP_LEFT);
      }
      return;
    }

    if (Does(A_RECOVERY_RISE_RIGHT))
    {
      // set a probability here to hide instead of stop
      boolean lChance = (System.currentTimeMillis() % 4) == 0;

      if (lChance)
      {
        SetA(A_HIDE_RIGHT);
      }
      else
      {
        SetA(A_STOP_RIGHT);
      }
      return;
    }

    if (Does(A_CROUCH_RECOVERY_FALL_LEFT))
    {
      SetA(A_CROUCH_RECOVERY_LEFT);
      return;
    }

    if (Does(A_CROUCH_RECOVERY_FALL_RIGHT))
    {
      SetA(A_CROUCH_RECOVERY_RIGHT);
      return;
    }

    if (Does(A_CROUCH_RECOVERY_LEFT))
    {
      SetA(A_CROUCH_RECOVERY_RISE_LEFT);
      return;
    }

    if (Does(A_CROUCH_RECOVERY_RIGHT))
    {
      SetA(A_CROUCH_RECOVERY_RISE_RIGHT);
      return;
    }

    if (Does(A_CROUCH_RECOVERY_RISE_LEFT))
    {
      SetA(A_CROUCH_TARGET_LEFT);
      return;
    }

    if (Does(A_CROUCH_RECOVERY_RISE_RIGHT))
    {
      SetA(A_CROUCH_TARGET_RIGHT);
      return;
    }

    if (Does(A_FIRE_RIGHT))
    {
      SetA(A_TARGET_RIGHT);
      return;
    }

    if (Does(A_FIRE_LEFT))
    {
      SetA(A_TARGET_LEFT);
      return;
    }

    if (Does(A_CROUCH_FIRE_RIGHT))
    {
      SetA(A_CROUCH_TARGET_RIGHT);
      return;
    }

    if (Does(A_CROUCH_FIRE_LEFT))
    {
      SetA(A_CROUCH_TARGET_LEFT);
      return;
    }

    if (Does(A_JOY_LEFT))
    {
      mJoyTime++;

      //if (IsPlayerInactive())
      if (mJoyTime < mJoyTimeMax)
      {
        //System.out.println(mJoyTime);
        SetA(A_JOY_LEFT);
      }
      else
      {
        mJoyTime = 0;
        SetA(A_STOP_LEFT);
      }
      return;
    }

    if (Does(A_JOY_RIGHT))
    {
      //if (IsPlayerInactive())
      mJoyTime++;
      if (mJoyTime < mJoyTimeMax)
      {
        //System.out.println(mJoyTime);
        SetA(A_JOY_RIGHT);
      }
      else
      {
        mJoyTime = 0;
        SetA(A_STOP_RIGHT);
      }
      return;
    }
  }

  //public void Paint(Graphics g, int _x, int _y)
  //{
    //mRealX = mPlayer.mRealX + 50;

    //System.out.println("painting shooter: " + (mRealX - _x) + ", " + (mRealY - _y));

    //super.Paint(g, _x, _y);

    // bounding box
    //g.setColor(0xFFFFFF);
    //g.drawRect(mBB.mLeft - _x, mBB.mTop - _y, mBB.mRight - mBB.mLeft, mBB.mBottom - mBB.mTop);

    //g.setColor(0xFFFFFF);
    //g.drawString(Integer.toString(mCurrentAction), _x, _y - 10, Graphics.RIGHT|Graphics.TOP);

    //g.setColor(0xFFFFFF);
    //g.drawString(Integer.toString(mActions[mCurrentAction].mAnimFrame), _x, _y + 12, Graphics.RIGHT|Graphics.TOP);
  //}

  public boolean PlayerInRange()
  {
    int lThisX = GetMidX();
    int lPlayerX = mPlayer.GetMidX();
    int lThisY = GetMidY();
    int lPlayerY = mPlayer.GetMidY();

    int lCurrentXDistance = Math.abs(lThisX - lPlayerX);
    //System.out.println(Math.abs(lPlayerY - lThisY) + "..." + lCurrentXDistance);

    if (((lCurrentXDistance < 65) || (Math.abs(mLastPlayerDistance - lCurrentXDistance) < 10)) && (Math.abs(lPlayerY - lThisY)) <= 20)
    {
      mLastPlayerDistance = lThisX - lPlayerX;
      return true;
    }

    return false;
  }

  public void ManageObjCollision(CObjBase _obj)
  {
    //if (_obj == null)
    //{
    //  return;
    //}

    if (!(_obj instanceof CObjBullet))
    {
      return;
    }

    if (!BBCollision(_obj.mBB))
    {
      return;
    }

    //if (_obj instanceof CObjBullet)
    //{
      if (((CObjBullet)_obj).mParent == this)
      {
        _obj.mTerminated = true;
        return;
      }

      // check passive situations - this unit cannot be hurt if is inactive
      if (IsInactive() || mCurrentAction == A_HIDE_LEFT || mCurrentAction == A_HIDE_RIGHT)
      {
        return;
      }

      SetHP(mHP - ((CObjBullet)_obj).GetDamage());

      // the bullet dissapers after hiting the target
      _obj.mTerminated = true;

      if (mHP <= 0)
      {
        //mLevel.mShotEnemies++;
        SetDyingAction();

        if (mBulletsDrop > 0)
        {
          mLevel.mObjectManager.InsertObject(new CObjPowerUp(mLevel, mLevel.mSpriteManager.AddSprite("BOXB"), GetMidX(), mBB.mBottom - 10, CObjPowerUp.POWER_AMMO, mBulletsDrop));
        }
      }
      else
      {
        SetRecoveryAction();
      }
    //}
  }

  public void BulletHit(CObjBullet _obj)
  {
    //System.out.println("aishooter bullet hit! " + mCurrentAction + "..." + mPlayer.mCurrentAction);

    if (!IsInactive() && mPlayer.IsInactive() && mCurrentAction != A_JOY_LEFT && mCurrentAction != A_JOY_RIGHT)
    {
      if (mPlayer.mRealX < mRealX)
      {
        SetA(A_JOY_LEFT);
        return;
      }

      SetA(A_JOY_RIGHT);
    }
  }

  void TargetPlayer()
  {
    if (mPlayer.mRealX < mRealX)
    {
      switch(mCurrentAction)
      {
        //case A_JOY_LEFT:
          //SetA(A_TARGET_LEFT);
          //break;
        //case A_JOY_RIGHT:
          //SetA(A_TARGET_LEFT, AC_TO_LEFT);
          //break;
        default:
          SetA(A_TARGET_LEFT, AC_TO_RIGHT);
      }

      return;
    }

    switch(mCurrentAction)
    {
      //case A_JOY_LEFT:
        //SetA(A_TARGET_RIGHT, AC_TO_RIGHT_WIDTH);
        //break;
      //case A_JOY_RIGHT:
        //SetA(A_TARGET_RIGHT);
        //break;
      default:
        SetA(A_TARGET_RIGHT, AC_TO_LEFT);
    }
  }

  void CrouchTargetPlayer()
  {
    if (mPlayer.mRealX < mRealX)
    {
      SetA(A_CROUCH_TARGET_LEFT);
      return;
    }

    SetA(A_CROUCH_TARGET_RIGHT);
  }

  boolean IsPlayerInactive()
  {
    return mPlayer.IsInactive();
  }

  boolean IsPlayerCrouched()
  {
    switch(mPlayer.mCurrentAction)
    {
      case CObjPlayer.A_CROUCH_WALK_LEFT:
      case CObjPlayer.A_CROUCH_LEFT:
      case CObjPlayer.A_CROUCH_WALK_RIGHT:
      case CObjPlayer.A_CROUCH_RIGHT:
      case CObjPlayer.A_CROUCH_FIRE_LEFT:
      case CObjPlayer.A_CROUCH_FIRE_RIGHT:
      case CObjPlayer.A_CROUCH_TARGETING_LEFT:
      case CObjPlayer.A_CROUCH_TARGETING_RIGHT:
      case CObjPlayer.A_CROUCH_DRAW_WEAPON_LEFT:
      case CObjPlayer.A_CROUCH_DRAW_WEAPON_RIGHT:
        return true;
    }

    return false;
}

  public int GetDamage()
  {
    return mDamage;
  }

  private void Fire(int _fireDirection)
  {
    // set parameters
    int lHotX, lHotY;
    CXSprite lBulletSprite = mLevel.mSpriteManager.AddSprite("BULL");

    if (_fireDirection == CObjBullet.A_MOVE_LEFT)
    {
      //lHotX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, true) - mBulletSprite.GetWidth(0) - mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame) - 1;
      lHotX = mRealX + mXSprite.GetHotX(mAnimFrame, true) - lBulletSprite.GetWidth(0) - mXSprite.GetWidth(mAnimFrame) - 1;
      //lHotX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, true) - mXSprite.GetWidth(mActions[GetA()].mAnimFrame) + 1;
    }
    else
    {
      //lHotX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, false) + mBulletSprite.GetWidth(0) + 1;
      lHotX = mRealX + mXSprite.GetHotX(mAnimFrame, false) + lBulletSprite.GetWidth(0) + 1;
    }

    //lHotY = GetY() + mXSprite.GetHotY(mActions[mCurrentAction].mAnimFrame) - mXSprite.GetHeight(mActions[GetA()].mAnimFrame);
    lHotY = mRealY + mXSprite.GetHotY(mAnimFrame) - mXSprite.GetHeight(mAnimFrame);

    CObjBullet lBullet = new CObjBullet(this,
                                        lBulletSprite,
                                          lHotX,
                                          lHotY,
                                          _fireDirection,
                                          1,
                                          CObjBullet.GENERIC_TIMEOUT_DISTANCE);

    mLevel.mObjectManager.InsertObject(lBullet);
  }

  private void SetRecoveryAction()
  {
    if (mCurrentAction == A_CROUCH_TARGET_RIGHT || mCurrentAction == A_CROUCH_FIRE_RIGHT)
    {
      Blink();

      SetA(A_CROUCH_RECOVERY_FALL_RIGHT);
      return;
    }

    if (mCurrentAction == A_CROUCH_TARGET_LEFT || mCurrentAction == A_CROUCH_FIRE_LEFT)
    {
      Blink();

      SetA(A_CROUCH_RECOVERY_FALL_LEFT);
      return;
    }

    if (mCurrentAction == A_STOP_LEFT || mCurrentAction == A_TARGET_LEFT ||
        mCurrentAction == A_FIRE_LEFT || mCurrentAction == A_JOY_LEFT)
    {
      SetA(A_RECOVERY_FALL_LEFT);
      return;
    }

    if (mCurrentAction == A_MOVE_LEFT)
    {
      SetA(A_RECOVERY_FALL_LEFT, AC_TO_RIGHT);
      return;
    }

    if (mCurrentAction == A_MOVE_RIGHT)
    {
      SetA(A_RECOVERY_FALL_RIGHT, AC_TO_RIGHT_WIDTH);
      return;
    }

    SetA(A_RECOVERY_FALL_RIGHT);
  }

  private void SetDyingAction()
  {
    Blink();

    if (//mCurrentAction == A_CROUCH_LEFT || mCurrentAction == A_CROUCH_RIGHT ||
        mCurrentAction == A_CROUCH_TARGET_LEFT || mCurrentAction == A_CROUCH_FIRE_LEFT)
    {
      SetA(A_CROUCH_DYING_LEFT);
      return;
    }

    if (//mCurrentAction == A_CROUCH_LEFT || mCurrentAction == A_CROUCH_RIGHT ||
        mCurrentAction == A_CROUCH_TARGET_RIGHT || mCurrentAction == A_CROUCH_FIRE_RIGHT)
    {
      SetA(A_CROUCH_DYING_RIGHT);
      return;
    }

    if (mCurrentAction == A_STOP_LEFT || mCurrentAction == A_TARGET_LEFT ||
        mCurrentAction == A_FIRE_LEFT || mCurrentAction == A_JOY_LEFT)
    {
      SetA(A_DYING_LEFT);
      return;
    }

    if (mCurrentAction == A_MOVE_LEFT)
    {
      SetA(A_DYING_LEFT, AC_TO_RIGHT);
      return;
    }

    if (mCurrentAction == A_MOVE_RIGHT)
    {
      SetA(A_DYING_RIGHT, AC_TO_RIGHT_WIDTH);
      return;
    }

    SetA(A_DYING_RIGHT);
  }

  //private boolean IsInactive()
  //{
  //  return mActions.GetInactive(mCurrentAction);
  //}

  private final boolean Reloading()
  {
    return (mTimeReload < mTimeReloadMax);
  }

  private final void Blink()
  {
    CXSprite lSprite = mLevel.mSpriteManager.AddSprite("BLIN");

    mLevel.mObjectManager.InsertObject(new CObjAnimation(mLevel, lSprite, GetMidX() - 4, GetMidY() - 2, true, 8, Graphics.HCENTER | Graphics.VCENTER, 0));
    mLevel.mObjectManager.InsertObject(new CObjAnimation(mLevel, lSprite, GetMidX() + 4, GetMidY() - 2, true, 8, Graphics.HCENTER | Graphics.VCENTER, 0));
    mLevel.mObjectManager.InsertObject(new CObjAnimation(mLevel, lSprite, GetMidX(), GetMidY() + 4, true, 8, Graphics.HCENTER | Graphics.VCENTER, 0));
  }
}