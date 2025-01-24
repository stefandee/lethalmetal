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
import com.nokia.mid.ui.*;

public class CObjAISlasher extends CObjBase
{
  final static byte A_MOVE_LEFT       = 0;
  final static byte A_MOVE_RIGHT      = 1;
  final static byte A_DEAD_RIGHT      = 2;
  final static byte A_DEAD_LEFT       = 3;

  int mDamage, mAggresivity, mStartX, mMinExtentX, mMaxExtentX, mPatrolDistance;

  int mArmsAnimFrame = 0, mArmsAnimFrameMax = 3, mArmsUpdate = 0, mArmsUpdateMax = 5;

  int mColorAnimFrame = 0;

  //boolean mFriendly;
  //String mDialogText;

  CXSprite mArmsSprite = null, mHeadSprite = null;

  //CEnvManager     mEnvManager;
  //CXSpriteManager mSpriteManager;
  //CObjectManager  mObjectManager;

  public CObjAISlasher(CLevel _level, CXSprite _sprite, int _x, int _y, int _stance, int _health, int _damage, int _aggresivity, int _patrolDistance, int _color, int _friendly)
  {
    super(_level, _sprite, _x, _y);
    //System.out.println("slasher at: " + mRealX + "," + mRealY);

    // create the actions
    //mActions = new CAction[4];

    //mActions[A_MOVE_LEFT]       = new CAction(4, 0, 0, -3, 0, DirectGraphics.FLIP_HORIZONTAL);
    //mActions[A_MOVE_RIGHT]      = new CAction(4, 0, 0, 3, 0, 0);
    //mActions[A_DEAD_LEFT]       = new CAction(10000, 0, 0, 0, 0, DirectGraphics.FLIP_HORIZONTAL);
    //mActions[A_DEAD_RIGHT]      = new CAction(10000, 0, 0, 0, 0, 0);

    mDamage      = _damage;
    //mAggresivity = _aggresivity;
    mPatrolDistance = _patrolDistance;
    //mEnvManager = _envManager;
    //mSpriteManager = _spriteManager;
    //mObjectManager = _manager;
    mCanCollideWithObj = true;
    mCanCollideWithBullet = true;
    mMaxHP          = mHP = _health;

    mActions = mLevel.mObjectManager.mAISlasherActions;

    // based on the desired robot color, select the body sprite
    mColorAnimFrame = _color;

    /*
    switch(_color)
    {
      case 0:
        mColorAnimFrame = 0;
        break;

      case 1:
        mColorAnimFrame = 1;
        break;
    }
    */

    mHeadSprite = mLevel.mSpriteManager.AddSprite("R2HE");
    mArmsSprite = mLevel.mSpriteManager.AddSprite("R2AR");

    mStartX = _x;
    mMinExtentX = Math.max(0, mStartX - (mPatrolDistance << 4));
    mMaxExtentX = Math.min(mLevel.mEnvManager.GetWidth() << 4, mStartX + (mPatrolDistance << 4));

    // chose the starting action based on the "stance" parameter
    if (_stance == 0)
    {
      SetA(A_MOVE_LEFT);
    }
    else
    {
      SetA(A_MOVE_RIGHT);
    }
  }

  final public void Paint(Graphics g, int _x, int _y)
  {
    int lPaintX = mRealX - _x;
    int lPaintY = mRealY - _y;

    //if (lPaintX < 0 || lPaintX > 96)
    if (mBB.mRight - _x < 0 || mBB.mLeft - _x > 96)
    {
      return;
    }

    if (lPaintY < 0 || lPaintY > 65)
    {
      return;
    }

    // paint the body
    mXSprite.Paint(g, lPaintX, lPaintY, 0, Graphics.LEFT | Graphics.TOP, 0, 1);

    // paint the arms, mirrored
    mArmsSprite.Paint(g, lPaintX - mArmsSprite.GetHotX(mArmsAnimFrame, true), lPaintY, mArmsAnimFrame, Graphics.RIGHT | Graphics.TOP, 0, 1);

    int lOpposedArmFrame = mArmsAnimFrameMax - mArmsAnimFrame;

    mArmsSprite.Paint(g, lPaintX + 14 + mArmsSprite.GetHotX(lOpposedArmFrame, false) - mArmsSprite.GetWidth(lOpposedArmFrame) + 1, lPaintY, lOpposedArmFrame, Graphics.LEFT | Graphics.TOP, DirectGraphics.FLIP_HORIZONTAL, 1);

    // paint the head
    mHeadSprite.Paint(g, lPaintX + 1, lPaintY, mColorAnimFrame, Graphics.LEFT | Graphics.BOTTOM, 0, 1);

    //g.setColor(255, 0, 0);
    //g.drawRect(mBB.mLeft - _x, mBB.mTop - _y, mBB.mRight - mBB.mLeft, mBB.mBottom - mBB.mTop);
  }

  public final void Update()
  {
    super.Update();

    mArmsUpdate++;

    if (mArmsUpdate > mArmsUpdateMax)
    {
      mArmsUpdate = 0;

      mArmsAnimFrame++;

      if (mArmsAnimFrame > mArmsAnimFrameMax) {
        mArmsAnimFrame = 0;
      }
    }

    if (mHP <= 0)
    {
      if (mCurrentAction == A_MOVE_LEFT)
      {
        SetA2(A_DEAD_LEFT);
      }

      if (mCurrentAction == A_MOVE_RIGHT)
      {
        SetA2(A_DEAD_RIGHT);
      }

      mTerminated = true;
    }
    else
    {
      if (mRealX < mMinExtentX && mCurrentAction == A_MOVE_LEFT)
      {
        SetA(A_MOVE_RIGHT);
      }

      if (mRealX > mMaxExtentX && mCurrentAction == A_MOVE_RIGHT)
      {
        SetA(A_MOVE_LEFT);
      }
    }
  }

  final public void GetBB()
  {
    // also update the bb
    int lOpposedArmFrame = mArmsAnimFrameMax - mArmsAnimFrame;

    mBB.mTop    = mRealY - mHeadSprite.GetHeight(0);
    mBB.mBottom = mRealY + mXSprite.GetHeight(0);
    mBB.mLeft   = mRealX - mArmsSprite.GetHotX(mArmsAnimFrame, true) - mArmsSprite.GetWidth(mArmsAnimFrame);
    mBB.mRight  = mRealX + 14 - mArmsSprite.GetHotX(lOpposedArmFrame, false) + mArmsSprite.GetWidth(lOpposedArmFrame);

    //mBB.mLeft -= mArmsSprite.GetWidth(mArmsAnimFrame);
    //mBB.mRight += mArmsSprite.GetWidth(mArmsAnimFrameMax - mArmsAnimFrame);
  }

  final public void ManageObjCollision(CObjBase _obj)
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
        return;
      }

      SetHP(mHP - ((CObjBullet)_obj).GetDamage());

      if (IsDead())
      {
        mLevel.mObjectManager.AddObject(new CObjAnimation(mLevel, mLevel.mSpriteManager.AddSprite("EXPL"), GetMidX(), GetMidY(), true, 2, Graphics.HCENTER | Graphics.VCENTER, 0));
        //mLevel.mShotEnemies++;
      }

      // the bullet dissapers after it hits the target
      _obj.mTerminated = true;
    //}
  }

  final public int GetDamage()
  {
    return mDamage;
  }
}