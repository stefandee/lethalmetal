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

public class CObjBullet extends CObjBase
{
  public final static int GENERIC_TIMEOUT_DISTANCE = 70;

  // action constants id
  static int A_MOVE_RIGHT = 0;
  static int A_MOVE_LEFT = 1;
  private int mDamage, mTimeOutDistance, mStartX;
  public CObjBase mParent;
  //private CEnvManager mEnvManager;
  //CObjectManager  mObjectManager;
  //CXSpriteManager mSpriteManager;

  public CObjBullet(CObjBase _parent, CXSprite _sprite, int _x, int _y, int _direction, int _damage, int _timeOutDistance)
  {
    super(_parent.mLevel, _sprite, _x, _y);

    mParent = _parent;
    //mEnvManager = _envManager;
    //mObjectManager = _manager;
    //mSpriteManager = _spriteManager;
    //mTileManager = _tileManager;
    mCanCollideWithObj = true;
    mCanCollideWithTile = true;

    mTimeOutDistance = _timeOutDistance;
    mStartX = mRealX;

    mActions = new CActions(1);
    mActions.setSpeedsCount(1);
    mActions.SetSpeedIndexStart(0, 0);
    mActions.SetSpeedIndexEnd(0, 0);

    // the action setup
    int lDirection = 0;
    int lSpeedX    = 4;

    if (_direction == A_MOVE_LEFT)
    {
      lDirection = 0x20;//DirectGraphics.FLIP_HORIZONTAL;
      lSpeedX = -4;
    }

    //        index   update  start  end  xspeed   yspeed   autoupd  delta  effect      align
    setAction(0,      1,      0,     0,   lSpeedX, 0,       true,    1,     lDirection, 0);

    //mActions.printInfo(0);

    SetA(0);

    //System.out.println("bullet starts: " + mRealX + "," + mRealY + "..." + lSpeedX);

    mDamage = _damage;
  }

  public void Paint(Graphics g, int _x, int _y)
  {
    super.Paint(g, _x, _y);
  }

  public void ManageTileCollision()
  {
    if (mRealX < 0 || mRealX > (mLevel.mEnvManager.GetWidth() << 4) || mRealY < 0 || mRealY > (mLevel.mEnvManager.GetHeight() << 4))
    {
      //System.out.println("bullet exits map space");
      mTerminated = true;
      return;
    }

    if (Math.abs(mStartX - mRealX) > mTimeOutDistance)
    {
      mTerminated = true;
      return;
    }

    /*
    TileCollision();

    int lFlags = mCollision.GetFlags();

    if (lFlags != C_LADDER && lFlags != 0 && lFlags != C_HEAD_LADDER)
    {
      mTerminated = true;
    }
    */

    // we should also check if mRealY is inside the map bounds...
    int lColData = mLevel.mEnvManager.GetCollisionMap(GetMidX() >> 4, mRealY >> 4);

    if ((lColData & CTileManager.LEFT) != 0 || (lColData & CTileManager.RIGHT) != 0)
    {
      //System.out.println("bullet ends: " + mRealX + "," + mRealY);
      mTerminated = true;
    }
  }

/*
  public void ManageObjCollision(CObjBase _obj)
  {

    if (_obj == null)
    {
      return;
    }

    CBox lBox = _obj.mBB;

    if (lBox == null)
    {
      return;
    }

    if (!BBCollision(lBox))
    {
      return;
    }

    if (_obj instanceof CObjTrap)
    {
      _obj.mTerminated = true;
      mTerminated      = true;

      // add an explosion object
      mObjectManager.AddObject(new CObjAnimation(mSpriteManager.AddSprite("EXPL"), _obj.mRealX + _obj.GetWidth() / 2, _obj.mRealY + _obj.GetHeight() / 2, true, 5, Graphics.HCENTER | Graphics.VCENTER, 0));
    }

    /*
    if (_obj instanceof CObjPlayer)
    {
      if (mParent instanceof CObjAIShooter)
      {
        ((CObjAIShooter) mParent).BulletHit(this);
      }
    }
    */
  //}

  public void HitConfirmed()
  {
    if (mParent instanceof CObjAIShooter)
    {
      ((CObjAIShooter) mParent).BulletHit(this);
    }
  }

  public int GetDamage()
  {
    return mDamage;
  }
}