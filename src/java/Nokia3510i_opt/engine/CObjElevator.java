package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CObjElevator extends CObjBase
{
  private int mDir, mDist1, mDist2, mWait;
  private CPoint mOriginal, mDestination1, mDestination2;
  //private CObjectManager mObjManager;
  private byte mSpeed;

  final static int A_MOVE_TO1   = 0;
  final static int A_MOVE_TO2   = 1;
  final static int A_WAIT_IN1   = 2;
  final static int A_WAIT_IN2   = 3;

  final static int X_COLLISION_OFFSET = 1;
  final static int Y_COLLISION_OFFSET = 5;

  public CObjElevator(CLevel _level, CXSprite _sprite, int _x, int _y, int _dir, int _dist1, int _dist2, int _wait, byte _speed)
  {
    super(_level, _sprite, _x, _y);

    //mObjManager = _manager;

    mActions = new CActions(4);
    mActions.setSpeedsCount(4);
    mActions.SetSpeedIndexStart(A_MOVE_TO1, 0);
    mActions.SetSpeedIndexEnd(A_MOVE_TO1, 0);
    mActions.SetSpeedIndexStart(A_MOVE_TO2, 1);
    mActions.SetSpeedIndexEnd(A_MOVE_TO2, 1);
    mActions.SetSpeedIndexStart(A_WAIT_IN1, 2);
    mActions.SetSpeedIndexEnd(A_WAIT_IN1, 2);
    mActions.SetSpeedIndexStart(A_WAIT_IN2, 3);
    mActions.SetSpeedIndexEnd(A_WAIT_IN2, 3);

    mCanCollideWithObj = true;
    mCanCollideWithTile = false;

    // the default action
    //        index       update  start  end  xspeed   yspeed   autoupd  delta  effect      align
    setAction(A_MOVE_TO1, 3,      0,     0,   0,       0,       true,    1,     0,          0);
    setAction(A_MOVE_TO2, 3,      0,     0,   0,       0,       true,    1,     0,          0);
    setAction(A_WAIT_IN1, _wait,  0,     0,   0,       0,       true,    1,     0,          0);
    setAction(A_WAIT_IN2, _wait,  0,     0,   0,       0,       true,    1,     0,          0);


    mDestination1 = new CPoint(_x, _y);
    mDestination2 = new CPoint(_x, _y);

    //System.out.println("elevator: " + _dist1 + "....." + _dist2);
    //System.out.println("elevator at (" + _x + "," + _y + ")");

    switch(_dir)
    {
      // moving up
      case 0:
        mDestination1 = new CPoint(_x, _y + (_dist2 << 4));
        mDestination2 = new CPoint(_x, _y - (_dist1 << 4));
        mActions.SetSpeedYFrames(A_MOVE_TO1, _speed);
        mActions.SetSpeedYFrames(A_MOVE_TO2, -_speed);
        SetA2(A_MOVE_TO2);
        break;

      // moving down
      case 1:
        mDestination1 = new CPoint(_x, _y - (_dist2 << 4));
        mDestination2 = new CPoint(_x, _y + (_dist1 << 4));
        mActions.SetSpeedYFrames(A_MOVE_TO1, -_speed);
        mActions.SetSpeedYFrames(A_MOVE_TO2, _speed);
        SetA2(A_MOVE_TO2);
        break;

      // moving left
      case 2:
        mDestination1 = new CPoint(_x + (_dist2 << 4), _y);
        mDestination2 = new CPoint(_x - (_dist1 << 4), _y);
        mActions.SetSpeedXFrames(A_MOVE_TO1, _speed);
        mActions.SetSpeedXFrames(A_MOVE_TO2, -_speed);

        //System.out.println("elevator (left): (" + mDestination1.x + "," + mDestination1.y + ").....(" +
        //                   mDestination2.x + "," + mDestination2.y + ")");
        SetA2(A_MOVE_TO2);
        break;

      // moving right
      case 3:
        mDestination1 = new CPoint(_x - (_dist2 << 4), _y);
        mDestination2 = new CPoint(_x + (_dist1 << 4), _y);
        mActions.SetSpeedXFrames(A_MOVE_TO1, -_speed);
        mActions.SetSpeedXFrames(A_MOVE_TO2, _speed);
        SetA2(A_MOVE_TO2);
        break;
    }
  }

  //public CObjElevator(CObjectManager _manager, CXSprite _sprite, int _x, int _y, int _dir, int _dist1, int _dist2, int _wait, byte _speed)
  //{
  //}

  public final void Update()
  {
    super.Update();

    if (Does(A_MOVE_TO1))
    {
      //System.out.println("current: (" + mRealX + "," + mRealY + ") - to: (" + mDestination1.x + "," + mDestination1.y + ")");

      int lSpeedX = mActions.GetSpeedX(mSpeedIndex);
      int lSpeedY = mActions.GetSpeedY(mSpeedIndex);

      if (lSpeedY > 0)
      {
        if (mRealY >= mDestination1.y)
        {
          SetA(A_WAIT_IN1);
        }
      } else if (lSpeedY < 0)
      {
        if (mRealY <= mDestination1.y)
        {
          SetA(A_WAIT_IN1);
        }
      }

      if (lSpeedX < 0)
      {
        if (mRealX <= mDestination1.x)
        {
          SetA(A_WAIT_IN1);
        }
      }
      else if (lSpeedX > 0)
      {
        if (mRealX >= mDestination1.x)
        {
          SetA(A_WAIT_IN1);
        }
      }
    }

    if (Does(A_MOVE_TO2))
    {
      int lSpeedX = mActions.GetSpeedX(mSpeedIndex);
      int lSpeedY = mActions.GetSpeedY(mSpeedIndex);

      if (lSpeedY < 0)
      {
        if (mRealY <= mDestination2.y)
        {
          SetA(A_WAIT_IN2);
        }
      }
      else if (mActions.GetSpeedY(mSpeedIndex) > 0)
      {
        if (mRealY >= mDestination2.y)
        {
          SetA(A_WAIT_IN2);
        }
      }

      if (lSpeedX > 0)
      {
        if (mRealX >= mDestination2.x)
        {
          SetA(A_WAIT_IN2);
        }
      }
      else if (lSpeedX < 0)
      {
        if (mRealX <= mDestination2.x)
        {
          SetA(A_WAIT_IN2);
        }
      }
    }

    if (Finished())
    {
      if (Does(A_WAIT_IN1))
      {
        SetA(A_MOVE_TO2);
      }

      if (Does(A_WAIT_IN2))
      {
        //System.out.println("going to 1");
        SetA(A_MOVE_TO1);
      }
    }
  }

  public void ManageObjCollision(CObjBase _obj)
  {
    //if (_obj == null)
    //{
    //  return;
    //}

    if (!(_obj instanceof CObjPlayer))
    {
      return;
    }

    CBox lBox = _obj.mBB;

    if (!BBCollision(lBox))
    {
      return;
    }

    //if (_obj instanceof CObjPlayer)
    //{
      CObjPlayer lPlayer = (CObjPlayer)_obj;

      if (OverPlatform(lBox))
      {
        int lH = (lPlayer.mBB.mBottom - lPlayer.mBB.mTop);

        lPlayer.mRealY = mBB.mTop - lH + 1;
        lPlayer.mBB.mTop = mBB.mTop - lH + 1;
        lPlayer.mBB.mBottom = mBB.mTop + 1;

        // updating the player x must be corellated by the update of the
        // elevator coordinates (which only happens when mUpdate is 0)
        // the player y update should not ;)
        if (mUpdate == 0)
        {
          lPlayer.mRealX += mActions.GetSpeedX(mSpeedIndex);
          lPlayer.GetBB();
        }

        if (lPlayer.Does(CObjPlayer.A_GRAB_EDGE_LEFT))
        {
          lPlayer.SetA2(CObjPlayer.A_STOP_LEFT);
        }

        if (lPlayer.Does(CObjPlayer.A_GRAB_EDGE_RIGHT))
        {
          lPlayer.SetA2(CObjPlayer.A_STOP_RIGHT);
        }

        if (lPlayer.Does(CObjPlayer.A_FALL_LEFT))
        {
          //System.out.println("fall -> stop");
          lPlayer.SetA2(CObjPlayer.A_STOP_LEFT);
        }

        if (lPlayer.Does(CObjPlayer.A_FALL_RIGHT))
        {
          //System.out.println("fall -> stop");
          lPlayer.SetA2(CObjPlayer.A_STOP_RIGHT);
        }

        if (lPlayer.Does(CObjPlayer.A_JUMP_FALL_RIGHT))
        {
          lPlayer.SetA(CObjPlayer.A_FALL_RIGHT);
        }

        if (lPlayer.Does(CObjPlayer.A_JUMP_FALL_LEFT))
        {
          lPlayer.SetA(CObjPlayer.A_FALL_LEFT);
        }

        if (lPlayer.Does(CObjPlayer.A_AIR_RECOVERY_FALL_LEFT))
        {
          lPlayer.SetA2(CObjPlayer.A_RECOVERY_LEFT);
        }

        if (lPlayer.Does(CObjPlayer.A_AIR_RECOVERY_FALL_RIGHT))
        {
          lPlayer.SetA2(CObjPlayer.A_RECOVERY_RIGHT);
        }

        if (lPlayer.Does(CObjPlayer.A_AIR_DYING_FALL_LEFT))
        {
          lPlayer.SetA2(CObjPlayer.A_DEAD_LEFT);
        }

        if (lPlayer.Does(CObjPlayer.A_AIR_DYING_FALL_RIGHT))
        {
          lPlayer.SetA2(CObjPlayer.A_DEAD_RIGHT);
        }
      }
    //}
  }

  public final boolean OverPlatform(CBox _bb)
  {
    // instead of 6 should be something like 25% of player height (only when touching with the feet the elevator we should
    // consider collision
    if (_bb.mBottom  >= mBB.mTop - 2 &&
        mBB.mTop + 6 > _bb.mBottom &&
        !(_bb.mRight < mBB.mLeft + X_COLLISION_OFFSET)
        && !(_bb.mLeft > mBB.mRight - X_COLLISION_OFFSET))
    {
      return true;
    }

    return false;
  }
}