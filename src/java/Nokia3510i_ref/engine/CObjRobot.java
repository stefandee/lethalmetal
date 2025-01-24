package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import javax.microedition.lcdui.*;
import com.nokia.mid.ui.*;

public class CObjRobot extends CObjBase
{
  final static byte A_STOP_LEFT       = 0;
  final static int A_STOP_RIGHT      = 1;
  final static int A_DYING_RIGHT     = 2;
  final static int A_DYING_LEFT      = 3;
  final static int A_DEAD_RIGHT      = 4;
  final static int A_DEAD_LEFT       = 5;
  final static int A_FIRE_LEFT      = 6;
  final static int A_FIRE_RIGHT     = 7;
  final static int A_RECOVERY_FALL_LEFT = 8;
  final static int A_RECOVERY_FALL_RIGHT = 9;
  final static int A_RECOVERY_RISE_LEFT = 10;
  final static int A_RECOVERY_RISE_RIGHT = 11;
  final static int A_RECOVERY_LEFT = 12;
  final static int A_RECOVERY_RIGHT = 13;
  final static int A_TARGET_LEFT      = 14;
  final static int A_TARGET_RIGHT     = 15;
  final static int A_MOVE_LEFT      = 16;
  final static int A_MOVE_RIGHT     = 17;
  final static int A_DYING_OVER_LEFT      = 18;
  final static int A_DYING_OVER_RIGHT     = 19;

  //private CObjectManager  mObjectManager;
  //private CXSpriteManager mSpriteManager;
  //private CEnvManager mEnvManager;

  int mDamage, mPatrolDistance;
  int mMinExtentX, mMaxExtentX, mStartX;
  CObjPlayer mPlayer;
  CXSprite mFlameSprite, mImpactSprite, mBulletSprite, mExplosionSprite;
  short mTimeReload = 0, mTimeReloadMax = 100;
  int mLastPlayerDistance = 0;

  public CObjRobot(CLevel _level, CXSprite _sprite, int _x, int _y, int _stance, int _health, int _damage, int _recoveryTime, int _patrolDistance)
  {
    super(_level, _sprite, _x, _y);

    mPlayer = mLevel.mObjectManager.GetPlayer();
    //mObjectManager = _manager;
    //mSpriteManager = _spriteManager;
    //mEnvManager = _envManager;

    //mActions = mObjectManager.mAIRobotActions;
    //createActions("AROB");

    // small customizations
    mActions.SetAutoUpdate(A_TARGET_LEFT, false);
    mActions.SetAutoUpdate(A_TARGET_RIGHT, false);
    mActions.SetUpdateMax(A_RECOVERY_LEFT, _recoveryTime);
    mActions.SetUpdateMax(A_RECOVERY_RIGHT, _recoveryTime);

    mDamage      = _damage;
    mMaxHP       = mHP = _health;
    mPatrolDistance = _patrolDistance;

    mCanCollideWithObj = true;
    mCanCollideWithTile = false;

    mStartX = _x;
    mMinExtentX = Math.max(0, mStartX - (mPatrolDistance << 4));
    mMaxExtentX = Math.min(mLevel.mEnvManager.GetWidth() << 4, mStartX + (mPatrolDistance << 4));

    mBulletSprite    = mLevel.mSpriteManager.AddSprite("BULL");
    mFlameSprite     = mLevel.mSpriteManager.AddSprite("RED_");
    mImpactSprite    = mLevel.mSpriteManager.AddSprite("IMPA");
    mExplosionSprite = mLevel.mSpriteManager.AddSprite("EXPL");

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

    // recompute mRealY since it came align to top and this object is aligned to
    // bottom
    mRealY = mRealY + GetHeight();
  }

  public void Update()
  {
    super.Update();

    //
    // WEAPON RELOAD
    //
    if (mTimeReload <= mTimeReloadMax)
    {
      mTimeReload++;
    }

   if (Does(A_STOP_LEFT))
   {
     SetA(A_MOVE_LEFT, AC_TO_RIGHT_WIDTH);
   }

   if (Does(A_STOP_RIGHT))
   {
     SetA(A_MOVE_RIGHT, AC_TO_RIGHT);
   }

   if (mRealX < mMinExtentX && mCurrentAction == A_MOVE_LEFT)
   {
     SetA(A_MOVE_RIGHT, AC_TO_RIGHT);
   }

   if (mRealX > mMaxExtentX && mCurrentAction == A_MOVE_RIGHT)
   {
     SetA(A_MOVE_LEFT, AC_TO_LEFT);
   }

   //
   // Check if the player is close and start the combat sequence
   //
   if (PlayerInRange() && !IsInactive())
   {
     //if (IsPlayerInactive())
     //{
     //  TargetPlayer();
     //  return;
     //}

     if (mCurrentAction == A_STOP_LEFT || mCurrentAction == A_MOVE_LEFT ||
         mCurrentAction == A_STOP_RIGHT || mCurrentAction == A_MOVE_RIGHT)
     {
         TargetPlayer();
         return;
     }

     if (!Reloading())
     {
       if (Does(A_TARGET_LEFT))
       {
         SetA(A_FIRE_LEFT, AC_TO_RIGHT);
         Fire(CObjBullet.A_MOVE_LEFT);
         Flame(CObjBullet.A_MOVE_LEFT);
         mTimeReload = 0;
       }

       if (Does(A_TARGET_RIGHT))
       {
         SetA(A_FIRE_RIGHT);
         Fire(CObjBullet.A_MOVE_RIGHT);
         Flame(CObjBullet.A_MOVE_RIGHT);
         mTimeReload = 0;
       }
     }
   }
   else
   {
     if (Finished())
     {
       if (mCurrentAction == A_TARGET_LEFT)
       {
         SetA(A_STOP_LEFT);
         return;
       }

       if (mCurrentAction == A_TARGET_RIGHT)
       {
         SetA(A_STOP_RIGHT);
         return;
       }
     }
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
     case A_DYING_LEFT:
       SetA(A_DYING_OVER_LEFT);
       break;

     case A_DYING_RIGHT:
       SetA(A_DYING_OVER_RIGHT);
       break;

     case A_DYING_OVER_LEFT:
       SetA(A_DEAD_LEFT);
       break;

     case A_DYING_OVER_RIGHT:
       SetA(A_DEAD_RIGHT);
       break;

     case A_RECOVERY_RIGHT:
       SetA(A_RECOVERY_RISE_RIGHT);
       break;

     case A_RECOVERY_LEFT:
       SetA(A_RECOVERY_RISE_LEFT);
       break;

     case A_RECOVERY_FALL_RIGHT:
       SetA(A_RECOVERY_RIGHT);
       break;

     case A_RECOVERY_FALL_LEFT:
       SetA(A_RECOVERY_LEFT);
       break;

     case A_FIRE_LEFT:
       SetA(A_TARGET_LEFT);
       break;

     case A_FIRE_RIGHT:
       SetA(A_TARGET_RIGHT);
       break;

     case A_RECOVERY_RISE_RIGHT:
       SetA(A_STOP_RIGHT);
       break;

     case A_RECOVERY_RISE_LEFT:
       SetA(A_STOP_LEFT);
       break;

   }
 }

 public void ManageObjCollision(CObjBase _obj)
 {
   if (_obj == null)
   {
     return;
   }

   if (!BBCollision(_obj.mBB))
   {
     return;
   }

   if (_obj instanceof CObjBullet)
   {
     if (((CObjBullet)_obj).mParent == this)
     {
       _obj.mTerminated = true;
       return;
     }

     // check passive situations - this unit cannot be hurt if is inactive
     if (IsInactive())
     {
       return;
     }

     SetHP(mHP - ((CObjBullet)_obj).GetDamage());

     Impact((CObjBullet)_obj);

     // the bullet dissapers after hiting the target
     _obj.mTerminated = true;

     if (mHP <= 0)
     {
       int lX = GetMidX();
       int lY = GetMidY();

       mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mExplosionSprite, lX, lY, true, 5, Graphics.HCENTER | Graphics.VCENTER, 0));

       //mLevel.mShotEnemies++;

       SetDyingAction();
     }
     else
     {
       SetRecoveryAction();
     }
   }
 }

 public boolean PlayerInRange()
 {
   int lThisX = GetMidX();
   int lPlayerX = mPlayer.GetMidX();

   int lCurrentXDistance = Math.abs(lThisX - lPlayerX);

   if (((lCurrentXDistance < 50) || (Math.abs(mLastPlayerDistance - lCurrentXDistance) < 10)) && (Math.abs(mRealY - mPlayer.mRealY) >> 4) < 2)
   {
     mLastPlayerDistance = lThisX - lPlayerX;
     return true;
   }

   return false;
 }

 void TargetPlayer()
 {
   if (mPlayer.mRealX < mRealX)
   {
     switch(mCurrentAction)
     {
       case A_STOP_LEFT:
       case A_MOVE_LEFT:
         SetA(A_TARGET_LEFT, AC_TO_RIGHT);
         break;
     }

     return;
   }

   switch(mCurrentAction)
   {
     case A_STOP_RIGHT:
       SetA(A_TARGET_RIGHT);
       break;

     case A_MOVE_RIGHT:
       SetA(A_TARGET_RIGHT, AC_TO_LEFT);
       break;
   }
 }

 public int GetDamage()
 {
   return mDamage;
 }

 private void Fire(int _fireDirection)
 {
   // set parameters
   int lHotX, lHotY;

   if (_fireDirection == CObjBullet.A_MOVE_LEFT)
   {
     //lHotX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, true) - mBulletSprite.GetWidth(0) - mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame) - 1;
     lHotX = mRealX + mXSprite.GetHotX(mAnimFrame, true) - mBulletSprite.GetWidth(0) - mXSprite.GetWidth(mAnimFrame) - 1;
     //lHotX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, true) - mXSprite.GetWidth(mActions[GetA()].mAnimFrame) + 1;
   }
   else
   {
     //lHotX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, false) + mBulletSprite.GetWidth(0) + 1;
     lHotX = mRealX + mXSprite.GetHotX(mAnimFrame, false) + mBulletSprite.GetWidth(0) + 1;
   }

   //lHotY = GetY() + mXSprite.GetHotY(mActions[mCurrentAction].mAnimFrame) - mXSprite.GetHeight(mActions[GetA()].mAnimFrame);
   lHotY = mRealY + mXSprite.GetHotY(mAnimFrame) - mXSprite.GetHeight(mAnimFrame);

   CObjBullet lBullet = new CObjBullet(this,
                                       mBulletSprite,
                                         lHotX,
                                         lHotY,
                                         _fireDirection,
                                         1,
                                         CObjBullet.GENERIC_TIMEOUT_DISTANCE);

   mLevel.mObjectManager.InsertObject(lBullet);
 }

 private void Flame(int _flameDirection)
 {
   int lFlameAlign = 0, lFlameEffect = 0;
   int lFlameX;

   if (_flameDirection == CObjBullet.A_MOVE_LEFT)
   {
     //lFlameX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, true) - mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame) + 1;
     lFlameX = mRealX + mXSprite.GetHotX(mAnimFrame, true) - mXSprite.GetWidth(mAnimFrame) + 1;
     lFlameAlign = Graphics.RIGHT | Graphics.VCENTER;
     lFlameEffect = 0x20;//DirectGraphics.FLIP_HORIZONTAL;
   }
   else
   {
     lFlameAlign = Graphics.LEFT | Graphics.VCENTER;
     //lFlameX = mRealX + mXSprite.GetHotX(mActions[mCurrentAction].mAnimFrame, false) + 1;
     lFlameX = mRealX + mXSprite.GetHotX(mAnimFrame, false) + 1;
   }

   //int lHotY = GetY() + mXSprite.GetHotY(mActions[mCurrentAction].mAnimFrame) - mXSprite.GetHeight(mActions[GetA()].mAnimFrame);
   int lHotY = mRealY + mXSprite.GetHotY(mAnimFrame) - mXSprite.GetHeight(mAnimFrame);

   mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mFlameSprite, lFlameX, lHotY, true, 5, lFlameAlign, lFlameEffect));
 }

 private void Impact(CObjBullet _obj)
 {
   int lAlign = 0, lEffect = 0, lX, lY = _obj.mRealY;

   if (_obj.mActions.GetSpeedX(_obj.mSpeedIndex) < 0)
   {
     lX = _obj.mRealX;
     lAlign = Graphics.LEFT | Graphics.VCENTER;
     lEffect = DirectGraphics.FLIP_HORIZONTAL;
   }
   else
   {
     lX = _obj.mRealX - _obj.GetWidth();
     lAlign = Graphics.RIGHT | Graphics.VCENTER;
   }

   mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mImpactSprite, lX, lY, true, 5, lAlign, lEffect));
 }

 private void SetRecoveryAction()
 {
   switch(mCurrentAction)
   {
     case A_STOP_LEFT:
     case A_TARGET_LEFT:
     case A_FIRE_LEFT:
       SetA(A_RECOVERY_FALL_LEFT);
       break;

     case A_MOVE_LEFT:
       SetA(A_RECOVERY_FALL_LEFT, AC_TO_RIGHT);
       break;

     case A_STOP_RIGHT:
     case A_TARGET_RIGHT:
     case A_FIRE_RIGHT:
       SetA(A_RECOVERY_FALL_RIGHT);
       break;

     case A_MOVE_RIGHT:
       SetA(A_RECOVERY_FALL_RIGHT, AC_TO_LEFT);
       break;
   }
 }

 private void SetDyingAction()
 {
   switch(mCurrentAction)
   {
     case A_STOP_LEFT:
     case A_TARGET_LEFT:
     case A_FIRE_LEFT:
       SetA(A_DYING_LEFT);
       break;

     case A_MOVE_LEFT:
       SetA(A_DYING_LEFT, AC_TO_RIGHT);
       break;

     case A_STOP_RIGHT:
     case A_TARGET_RIGHT:
     case A_FIRE_RIGHT:
       SetA(A_DYING_RIGHT);
       break;

     case A_MOVE_RIGHT:
       SetA(A_DYING_RIGHT, AC_TO_LEFT);
       break;
   }
 }

 //private boolean IsInactive()
 //{
 // return mActions.GetInactive(mCurrentAction);
 //}

 boolean IsPlayerInactive()
 {
   return mPlayer.IsInactive();
 }

 final boolean Reloading()
 {
   return (mTimeReload < mTimeReloadMax);
 }
}