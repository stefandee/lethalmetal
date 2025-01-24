package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CObjTouchElevator extends CObjElevator
{
  private int mElevatorId, mDir, mDist;

  public CObjTouchElevator(CLevel _level, CXSprite _sprite, int _x, int _y, int _elevatorId, int _dir, int _dist)
  {
    super(_level, _sprite, _x, _y, _dir, _dist, 0, 50000, (byte)1);

    mElevatorId = _elevatorId;

    SetA(A_WAIT_IN1);
  }

  public final int GetId()
  {
    return mElevatorId;
  }

  public final void Activate()
  {
    if (Does(A_MOVE_TO1) || Does(A_MOVE_TO2) || Does(A_WAIT_IN2))
    {
      return;
    }

    SetA(A_MOVE_TO2);
  }

  public final void ManageObjCollision(CObjBase _obj)
  {
    super.ManageObjCollision(_obj);

    if (_obj == null)
    {
      return;
    }

    CBox lBox = _obj.mBB;

    if (!BBCollision(lBox))
    {
      return;
    }

    if (_obj instanceof CObjPlayer)
    {
      CObjPlayer lPlayer = (CObjPlayer) _obj;

      if (OverPlatform(lBox))
      {
        if (Does(A_WAIT_IN2))
        {
          SetA(A_MOVE_TO1);
        }
      }
    }
  }
}