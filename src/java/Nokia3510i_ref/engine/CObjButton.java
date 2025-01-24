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

public class CObjButton extends CObjBase
{
  private int mElevatorId;
  //private CObjectManager mObjectManager;

  public CObjButton(CLevel _level, CXSprite _sprite, int _x, int _y, int _elevatorId)
  {
    super(_level, _sprite, _x, _y);

    mElevatorId = _elevatorId;

    mActions = new CActions(2);
    mActions.setSpeedsCount(2);
    mActions.SetSpeedIndexStart(0, 0);
    mActions.SetSpeedIndexEnd(0, 0);
    mActions.SetSpeedIndexStart(1, 1);
    mActions.SetSpeedIndexEnd(1, 1);

    // the default action
    //        index   update  start  end  xspeed   yspeed   autoupd  delta  effect      align
    setAction(0,      4,      0,     0,   0,       0,       true,    1,     0,          Graphics.RIGHT | Graphics.BOTTOM);
    setAction(1,      20,     1,     1,   0,       0,       true,    1,     0,          Graphics.RIGHT | Graphics.BOTTOM);

    //mActions.SetAlign(0, Graphics.RIGHT | Graphics.BOTTOM);
    //mActions.SetAlign(1, Graphics.RIGHT | Graphics.BOTTOM);

    mRealY = mRealY + GetHeight();
    mRealX = mRealX + GetWidth();

    //mObjectManager = _manager;

    SetA(0);

    mStaticBB = true;
    mCanCollideWithObj = true;
  }

  public final void Update()
  {
    if (!mIsVisible)
    {
      return;
    }

    super.Update();

    //if (mActions[mCurrentAction].mFinished)
    if (mActionFinished)
    {
      if (mCurrentAction == 1)
      {
        SetA(0);
      }
    }
  }

  public final void Activate()
  {
    // ask the object manager the elevator with the id
    CObjTouchElevator lElevator = mLevel.mObjectManager.GetElevator(mElevatorId);

    if (lElevator == null)
    {
      SetA(1);
      return;
    }

    lElevator.Activate();
  }
}