package engine;

//package impalator;

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

public class CObjBase
{
  protected int mRealX, mRealY;//, mPrevRealX, mPrevRealY;
  //protected int mAction;
  protected CXSprite mXSprite;
  protected CActions mActions;
  protected int mCurrentAction;
  public int mFactor = 1;
  //CLevel mLevel;

  static final int COLLISION_X_OFFSET = 1;

  static final int COLLISION_RESPONSE_FLAG = 0;
  static final int COLLISION_RESPONSE_VAL1 = 1;
  static final int COLLISION_RESPONSE_VAL2 = 2;
  static final int COLLISION_RESPONSE_VAL3 = 3;

  //CollisionResponseData mCollision = new CollisionResponseData(0);
  int mCollision[] = new int[3];

  int mUpdate;
  int mAnimFrame, mPrevAnimFrame, mRepeat, mAlign, mEffect, mSpeedIndex = 0;
  boolean mActionFinished = false;
  boolean mStaticBB = false;
  //public int mActionsCount = 0;

  //
  // the bounding box is a cached attribute; it is computed only when coordinates
  // change or when width/height change (after changing the action)
  //
  public CBox mBB = new CBox(0, 0, 0, 0);
  public CBox mPrevBB = new CBox(0, 0, 0, 0);

  //protected CEnvManager     mEnvManager;
  //protected CTileManager    mTileManager;
  protected CLevel mLevel;

  //
  // collision is only performed after:
  // 1. changing position
  // 2. changing the action
  //
  //public boolean mCollisionEnabled = false;

  public boolean mTerminated;//, mStickLeft, mStickRight, mFreeze = false;
  public boolean mCanCollideWithObj = false, mCanCollideWithTile = false, mCanCollideWithBullet = false;
  public boolean mIsVisible = false;

  //
  // Hit points
  //
  protected int mHP, mMaxHP;

  //
  // COLLISION FLAGS
  //
  final static byte C_LEFT = 1;
  final static byte C_RIGHT = 2;
  final static byte C_UP = 4;
  final static byte C_DOWN = 8;
  final static byte C_LADDER = 16;
  final static byte C_HEAD_LADDER = 32;
  final static byte C_NONE = 64;

  //
  // ACTION FLAGS
  //
  final static byte ACTION_UPDATEMAX      = 0;
  final static byte ACTION_UPDATE         = 1;
  final static byte ACTION_SPEEDX         = 2;
  final static byte ACTION_SPEEDY         = 3;
  final static byte ACTION_ANIMFRAME      = 4;
  final static byte ACTION_ANIMPREVFRAME  = 5;
  final static byte ACTION_ANIMFRAMESTART = 6;
  final static byte ACTION_ANIMFRAMEEND   = 7;
  final static byte ACTION_FINISHED       = 8;
  final static byte ACTION_EFFECT         = 9;
  final static byte ACTION_ALIGN          = 10;
  final static byte ACTION_AUTOUPDATE     = 11;
  final static byte ACTION_FRAMEDELTA     = 12;
  final static byte ACTION_REPEATMAX      = 13;
  final static byte ACTION_REPEAT         = 14;

  //
  // ACTION CHANGING FLAGS
  // - these flags are used by SetA and SetA2 when changing stances
  // - based on the value of these flags, the X coordinate is updated
  // - for AC_TO_LEFT, the mBB.mLeft is taken as alignement basis
  // - for AC_TO_RIGHT, the mBB.mRight is taken as alignement basis
  //
  final static byte AC_TO_LEFT   = 0;
  final static byte AC_TO_RIGHT  = 1;
  final static byte AC_TO_MIDDLE_LEFT = 2;
  final static byte AC_TO_MIDDLE_RIGHT = 3;
  final static byte AC_TO_RIGHT_WIDTH  = 4;

  /**
   * ORIENTATION VARIABLES AND CONSTANTS
   *
   * DIR_LEFT  - 0 for mActions[mCurrentAction].mEffect
   * DIR_RIGHT - 1 for mActions[mCurrentAction].mEffect
   */
  //int mEffect;

  final static int EFFECT_RIGHT = 0;
  final static int EFFECT_LEFT  = DirectGraphics.FLIP_HORIZONTAL;

  //int mActions[][];

  /**
   * Constructor
   */
  public CObjBase(CLevel _level, CXSprite _sprite, int _x, int _y)
  //public CObjBase(CLevel _level, CXSprite _sprite, int _x, int _y)
  {
    mRealX = _x;
    mRealY = _y;
    //mPrevRealX = _x;
    //mPrevRealY = _y;
    mXSprite = _sprite;
    mTerminated = false;
    //mLevel = _level;
    mHP = mMaxHP = 1;
    mLevel = _level;
  }

  public void Paint(Graphics g, int _x, int _y)
  {
    if (mBB.mRight < _x || mBB.mBottom < _y)
    {
      return;
    }

    if (_x + mLevel.mScreenWidth < mBB.mLeft || mLevel.mScreenHeight + _y < mBB.mTop)
    {
      return;
    }

    //mXSprite.Paint(g, mRealX - _x, mRealY - _y, mActions[mCurrentAction].mAnimFrame, mActions[mCurrentAction].mAlign, mActions[mCurrentAction].mEffect);
    //mXSprite.Paint(g, mRealX - _x, mRealY - _y, mAnimFrame, mActions[mCurrentAction].GetAlign(), mActions[mCurrentAction].GetEffect() << 8, 1);
    mXSprite.Paint(g, mRealX - _x, mRealY - _y, mAnimFrame, mAlign, mEffect, 1);
    mIsVisible = true;
  }

  public void Update()
  {
    UpdateAction(mCurrentAction);
  }

  public final void UpdateAction(int _actionId)
  {
    //CAction lAction = mActions[_actionId];

    //lAction.mUpdate++;
    mUpdate++;

    //System.out.println("update action: " + mUpdate + "/" + mActions.GetUpdateMax(mCurrentAction));

    if (mUpdate >= mActions.GetUpdateMax(mCurrentAction) * mFactor)
    {
      if (mActions.GetAutoUpdate(mCurrentAction))
      {
        ManageActionParameters(_actionId);
      }
      else
      {
        //lAction.mFinished = true;
        //System.out.println("autoupdate is false?");
        mActionFinished = true;
      }
    }
  }

  public final void ManageActionParameters(int _actionId)
  {
    //CAction lAction = mActions[_actionId];

    mRepeat++;
    mUpdate = 0;


    if (mRepeat >= mActions.GetRepeatMax(_actionId))
    {
      mRepeat = 0;

      int lDelta = mActions.GetFrameDelta(_actionId);

      //lAction.mUpdate = 0;
      mPrevAnimFrame = mAnimFrame;
      mAnimFrame += lDelta;
      mSpeedIndex += lDelta;

      if (lDelta < 0)
      {
        if (mAnimFrame < mActions.GetAnimFrameStart(_actionId))
        {
          mPrevAnimFrame = mActions.GetAnimFrameStart(_actionId);
          mAnimFrame = mActions.GetAnimFrameEnd(_actionId);
          //lAction.mFinished = true;
          mActionFinished = true;
          //OnFinished();
        }

        if (mSpeedIndex < mActions.GetSpeedIndexStart(_actionId))
        {
          mSpeedIndex = mActions.GetSpeedIndexEnd(_actionId);
        }
      }
      else
      {
        if (mAnimFrame > mActions.GetAnimFrameEnd(_actionId))
        {
          mAnimFrame = mActions.GetAnimFrameStart(_actionId);
          mPrevAnimFrame = mActions.GetAnimFrameEnd(_actionId);
          //lAction.mFinished = true;
          mActionFinished = true;
          //OnFinished();
        }

        if (mSpeedIndex > mActions.GetSpeedIndexEnd(_actionId))
        {
          mSpeedIndex = mActions.GetSpeedIndexStart(_actionId);
        }
      }
    }

    //mPrevRealX = mRealX;
    //mPrevRealY = mRealY;

    int lSpeedX = mActions.GetSpeedX(mSpeedIndex);
    int lSpeedY = mActions.GetSpeedY(mSpeedIndex);

    mRealX += lSpeedX;
    mRealY += lSpeedY;

    UpdateBottom();

    if (!mStaticBB)
    {
      GetBB();
    }
  }

  //public int GetX()
  //{
  //  return mRealX;
  //}

  //public int GetY()
  //{
  //  return mRealY;
  //}

  //public int GetA()
  //{
  //  return mCurrentAction;
  //}

  public void OnFinished()
  {
  }

  public final void SetA(int _action, byte _flag)
  {
    switch(_flag)
    {
      case AC_TO_LEFT:
        mRealX = mBB.mLeft;
        break;
      case AC_TO_RIGHT:
        mRealX = mBB.mRight;
        break;
      case AC_TO_MIDDLE_LEFT:
        mRealX = GetMidX() - mXSprite.GetWidth(mActions.GetAnimFrameStart(_action)) / 2;
        break;
      case AC_TO_MIDDLE_RIGHT:
        mRealX = GetMidX() + mXSprite.GetWidth(mActions.GetAnimFrameStart(_action)) / 2;
        break;
      case AC_TO_RIGHT_WIDTH:
        mRealX = mBB.mRight - mXSprite.GetWidth(mActions.GetAnimFrameStart(_action));
        break;
    }

    //mActions[_action].mFinished = false;
    mCurrentAction = _action;
    //mActions[mCurrentAction].mFinished = false;
    mActionFinished = false;

    mRepeat = 0;
    mUpdate = 0;
    mEffect = mActions.GetEffect(mCurrentAction);
    mAlign  = mActions.GetAlign(mCurrentAction);
    mPrevAnimFrame = mAnimFrame;
    mAnimFrame = mActions.GetAnimFrameStart(mCurrentAction);
    mSpeedIndex = mActions.GetSpeedIndexStart(mCurrentAction);

    //mActions.printInfo(mCurrentAction);
    GetBB();
  }

  public final void SetA2(int _action, byte _flag)
  {
    switch(_flag)
    {
      case AC_TO_LEFT:
        mRealX = mBB.mLeft;
        break;
      case AC_TO_RIGHT:
        mRealX = mBB.mRight;
        break;
      case AC_TO_MIDDLE_LEFT:
        mRealX = GetMidX() - mXSprite.GetWidth(mActions.GetAnimFrameStart(_action)) / 2;
        break;
      case AC_TO_MIDDLE_RIGHT:
        mRealX = GetMidX() + mXSprite.GetWidth(mActions.GetAnimFrameStart(_action)) / 2;
        break;
      case AC_TO_RIGHT_WIDTH:
        mRealX = mBB.mRight - mXSprite.GetWidth(mActions.GetAnimFrameStart(_action));
        break;
    }

    //mActions[_action].mFinished = false;
    int lOldBottom = mRealY + mXSprite.GetHeight(mAnimFrame);
    mCurrentAction = _action;
    mActionFinished = false;

    //mActions[mCurrentAction].mFinished = false;
    mPrevAnimFrame = mAnimFrame;
    mAnimFrame = mActions.GetAnimFrameStart(mCurrentAction);
    mRealY = lOldBottom - mXSprite.GetHeight(mAnimFrame);

    mRepeat = 0;
    mUpdate = 0;
    mEffect = mActions.GetEffect(mCurrentAction);
    mAlign  = mActions.GetAlign(mCurrentAction);
    mSpeedIndex = mActions.GetSpeedIndexStart(mCurrentAction);

    //mActions.printInfo(mCurrentAction);
    GetBB();
  }

  public final void SetA(int _action)
  {
    mCurrentAction = _action;
    mActionFinished = false;
    mRepeat = 0;
    mUpdate = 0;
    mPrevAnimFrame = mAnimFrame;
    mAnimFrame = mActions.GetAnimFrameStart(mCurrentAction);
    mEffect = mActions.GetEffect(mCurrentAction);
    mAlign  = mActions.GetAlign(mCurrentAction);
    mSpeedIndex = mActions.GetSpeedIndexStart(mCurrentAction);

    //mActions.printInfo(mCurrentAction);

    GetBB();
  }

  // setting action with bottom update
  public final void SetA2(int _action)
  {
    //int lOldHeight = mXSprite.GetHeight(mAnimFrame);
    int lOldBottom = mRealY + mXSprite.GetHeight(mAnimFrame);
    mCurrentAction = _action;
    mActionFinished = false;
    mPrevAnimFrame = mAnimFrame;
    mAnimFrame = mActions.GetAnimFrameStart(mCurrentAction);
    mRealY = lOldBottom - mXSprite.GetHeight(mAnimFrame);

    mRepeat = 0;
    mUpdate = 0;
    mEffect = mActions.GetEffect(mCurrentAction);
    mAlign  = mActions.GetAlign(mCurrentAction);
    mSpeedIndex = mActions.GetSpeedIndexStart(mCurrentAction);

    //mActions.printInfo(mCurrentAction);
    GetBB();
  }

  public void UpdateBottom()
  {
    //int lOldBottom = mRealY + mXSprite.GetHeight(mPrevAnimFrame);
    //mRealY = lOldBottom - mXSprite.GetHeight(mAnimFrame);

    //GetBB();
  }

  public boolean GetTerminated()
  {
    return mTerminated;
  }

  public final void TileCollision()
  {
    //mCollision.SetFlags(0);
    SetFlags(0);

    //
    // check collision with the tiles that this object intersects
    //

    //boolean lStillCollided;

    //do
    //{
      //lStillCollided = false;

    //
    // compute lMinX so that we can collide with level horizontal limits
    //

    //if (mCurrentAction == 12 || mCurrentAction == 13)
    //{
    //  System.out.println(mAnimFrame + "..." + mRealY);
    //  System.out.println("prev x: " + mPrevBB.mLeft + "...." + mPrevBB.mRight);
    //  System.out.println("prev y: " + mPrevBB.mTop + "...." + mPrevBB.mBottom);
    //}

    int lMidX = GetMidX();

    mBB.mLeft += COLLISION_X_OFFSET;
    mBB.mRight -= COLLISION_X_OFFSET;

    int lMinX = (mBB.mLeft >> 4);
    int lMaxX = (mBB.mRight >> 4);
    int lMinY = (mBB.mTop >> 4);
    int lMaxY = (mBB.mBottom >> 4);

    if (mBB.mLeft < 0)
    {
      lMinX = -1;
    }

    lMaxY = Math.min((mLevel.mEnvManager.GetHeight() << 4) - 16, lMaxY);

    int lDownCollisionCount = 0, x16temp = 0, y16temp = 0;

    for (int x = lMinX; x <= lMaxX; x++)
    {
      int x16 = (x << 4);

      for (int y = lMinY; y <= lMaxY; y++)
      {
          int lColData = mLevel.mEnvManager.GetCollisionMap(x, y);
          int y16 = (y << 4);

          // if the tile does not have collision info, skip it
          if (lColData == 0)
          {
            continue;
          }

          //CTile lTile = mTileManager.GetTile();

          // it's < and > and not <= and >= so that we won't interfere with
          // ladder tiles placed one near another horizontally
          if ((x16 < lMidX) && (lMidX < x16 + 16))
          {
            if ((lColData & CTileManager.LADDER) != 0)
            {
              /*mCollision.*/AddFlags(C_LADDER);
              //mCollision.mIntData[0] = x16 + 8;
              mCollision[COLLISION_RESPONSE_VAL1] = x16 + 8;
            }

            if ((lColData & CTileManager.HEAD_LADDER) != 0)
            {
              /*mCollision.*/AddFlags(C_HEAD_LADDER);

              mCollision[COLLISION_RESPONSE_VAL1] = x16 + 8;
              mCollision[COLLISION_RESPONSE_VAL2] = y16 + 16;
              //mCollision.mIntData[0] = x16 + 8;
              //mCollision.mIntData[1] = y16 + 16;
            }
          }

          // we've got some collision info on the tile, let's check it

          // if the previous bottom was higher than the tile y then
          // we've got a collision on top
          // otherwise we've got a collision on the side
          if (mPrevBB.mBottom <= y16)
          {
            //System.out.println("down collision!");
            if ( (lColData & CTileManager.UP) != 0)
            {
              mRealY = y16 - (mBB.mBottom - mBB.mTop);
              //y16temp = y16 - (mBB.mBottom - mBB.mTop);

              /*mCollision.*/
              AddFlags(C_DOWN);
              //lStillCollided = true;
              //break;
              //lDownCollisionCount++;
              //x16temp = x16;
            }
          }
          else if (mPrevBB.mTop >= y16 + 16)
          {
            if ((lColData & CTileManager.DOWN) != 0)
            {
              mRealY = y16 + 16;

              /*mCollision.*/AddFlags(C_UP);
            }
          }
          else {
            // check the previous position regarding this tile
            if ( (lColData & CTileManager.LEFT) != 0 && mBB.mLeft < x16)//(mBB.mLeft - mPrevBB.mLeft >= 0))
            {
              //System.out.println("right collision!");

              if ( (mActions.GetAlign(mCurrentAction) & Graphics.RIGHT) == 0)
              {
                //System.out.println("right collision, mRealX = " + mRealX + ", x16 = " + x16);
                //System.out.println("box is: (" + mBB.mLeft + "," + mBB.mTop + ") - ( " + mBB.mRight + "," + mBB.mBottom + ")");
                mRealX = x16 - (mBB.mRight - mBB.mLeft) - COLLISION_X_OFFSET;//mXSprite.GetWidth(mAnimFrame);
                //System.out.println("mRealX = " + mRealX);
              }
              else
              {
                //System.out.println("right collision, aligned right. mRealX = " + mRealX + ", x16 = " + x16);
                //System.out.println("box is: (" + mBB.mLeft + "," + mBB.mTop + ") - ( " + mBB.mRight + "," + mBB.mBottom + ")");
                mRealX = x16 + COLLISION_X_OFFSET;
                //System.out.println("mRealX = " + mRealX);
              }

              /*mCollision.*/AddFlags(C_RIGHT);
              //lStillCollided = true;
              //break;
            }

            if ( (lColData & CTileManager.RIGHT) != 0 && mBB.mLeft > x16)//(mPrevBB.mLeft - mBB.mLeft >= 0))
            {
              if ( (mActions.GetAlign(mCurrentAction) & Graphics.RIGHT) == 0)
              {
                //System.out.println("tile right 1");
                mRealX = x16 + 16 - COLLISION_X_OFFSET;
              }
              else
              {
                //System.out.println("tile right 2");
                mRealX = x16 + 16 + mBB.mRight - mBB.mLeft + COLLISION_X_OFFSET;//mXSprite.GetWidth(mAnimFrame);
              }

              /*mCollision.*/AddFlags(C_LEFT);
              //lStillCollided = true;
              //break;
            }
        }
      }
    }

    /*
    if (lDownCollisionCount > 0)
    {
      if (lDownCollisionCount == 1)
      {
        int lCollisionDistance = Math.abs(Math.max(x16temp, mBB.mLeft) - Math.min(x16temp + 16, mBB.mRight));

        if (lCollisionDistance < 5)
        {
          //System.out.println(lCollisionDistance);
          GetBB();
          return;
        }
      }

      mRealY = y16temp;
      AddFlags(C_DOWN);
    }
        */

    GetBB();
  }

  public final boolean BBCollision(CBox _bb)
  {
    if ((mBB.mRight < _bb.mLeft) || (mBB.mBottom < _bb.mTop) || (_bb.mBottom < mBB.mTop) || (_bb.mRight < mBB.mLeft))
    {
      return false;
    }

    return true;
  }

  public int GetWidth()
  {
    //return mXSprite.GetWidth(mActions[mCurrentAction].mAnimFrame);
    return mXSprite.GetWidth(mAnimFrame);
  }

  public int GetHeight()
  {
    //return mXSprite.GetHeight(mActions[mCurrentAction].mAnimFrame);
    return mXSprite.GetHeight(mAnimFrame);
  }

  public void ManageTileCollision()
  {
  }

  public void ManageObjCollision(CObjBase _obj)
  {
  }

  public void BeginCollision()
  {
  }

  public void EndCollision()
  {
  }

  protected boolean Finished()
  {
    //return mActions[mCurrentAction].mFinished;
    return mActionFinished;
  }

  protected boolean IsInactive()
  {
    return mActions.GetInactive(mCurrentAction);
  }

  protected boolean Does(int _action)
  {
    return (_action == mCurrentAction);
  }

  public void GetBB()
  {
    int lLeft, lRight, lTop, lBottom;

    if ((mActions.GetAlign(mCurrentAction) & Graphics.RIGHT) != 0)
    {
      lLeft = mRealX - GetWidth();
      lRight = mRealX;
    }
    else
    {
      lLeft = mRealX;
      lRight = mRealX + GetWidth();
    }

    if ((mActions.GetAlign(mCurrentAction) & Graphics.BOTTOM) != 0)
    {
      lTop = mRealY - GetHeight();
      lBottom = mRealY;
    }
    else
    {
      lTop = mRealY;
      lBottom = mRealY + GetHeight();
    }

    mPrevBB.mLeft   = mBB.mLeft;
    mPrevBB.mTop    = mBB.mTop;
    mPrevBB.mRight  = mBB.mRight;
    mPrevBB.mBottom = mBB.mBottom;

    mBB.mLeft   = lLeft;
    mBB.mTop    = lTop;
    mBB.mRight  = lRight;
    mBB.mBottom = lBottom;

    //mCollisionEnabled = true;
  }

  public boolean CanCollide()
  {
    return mCanCollideWithObj;
  }

  protected void setAction(int _index, int _updateMax, int _frameStart, int _frameEnd, int _speedX, int _speedY, boolean _autoUpdate, int _frameDelta, int _effect, int _align)
  {
    mActions.SetUpdateMax(_index, _updateMax);
    mActions.SetAnimFrameStart(_index, _frameStart);
    mActions.SetAnimFrameEnd(_index, _frameEnd);
    mActions.SetEffect(_index, _effect);
    mActions.SetAutoUpdate(_index, _autoUpdate);
    mActions.SetAlign(_index, _align);
    mActions.SetFrameDelta(_index, _frameDelta);

    mActions.SetSpeedXFrames(_index, _speedX);
    mActions.SetSpeedYFrames(_index, _speedY);
  }

  public int GetMidX()
  {
    return (mBB.mLeft + mBB.mRight) >> 1;
  }

  public int GetMidY()
  {
    return (mBB.mTop + mBB.mBottom) >> 1;
  }

  public void SetFlags(int _flags)
  {
    mCollision[COLLISION_RESPONSE_FLAG] = _flags;
  }

  public void AddFlags(int _flags)
  {
    mCollision[COLLISION_RESPONSE_FLAG] |= _flags;
  }

  public final int GetHP()
  {
    return mHP;
  }

  public final void SetHP(int _v)
  {
    if (_v < 0)
    {
      _v = 0;
      mTerminated = true;
    }

    if (_v > mMaxHP)
    {
      _v = mMaxHP;
    }

    mHP = _v;
  }

  public final boolean IsDead()
  {
    if (mHP <= 0)
    {
      return true;
    }

    return false;
  }
}