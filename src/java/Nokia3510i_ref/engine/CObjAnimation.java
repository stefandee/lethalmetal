package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

public class CObjAnimation extends CObjBase
{
  private boolean mAutoTerminate;

  public CObjAnimation(CLevel _level, CXSprite _sprite, int _x, int _y, boolean _autoTerminate, int _time, int _align, int _effect)
  {
    super(_level, _sprite, _x, _y);

    mActions = new CActions(1);
    mActions.setSpeedsCount(1);
    mActions.SetSpeedIndexStart(0, 0);
    mActions.SetSpeedIndexEnd(0, 0);

    // the default right action
    //        index   update  start  end                       xspeed   yspeed   autoupd  delta  effect      align
    setAction(0,      _time,  0,     _sprite.CellsCount() - 1, 0,       0,       true,    1,    _effect,     0);
    mActions.SetAlign(0, _align);

    SetA(0);

    mAutoTerminate = _autoTerminate;
    mStaticBB = true;
  }

  public void Update()
  {
    //if (mIsVisible)
    //{
      super.Update();
    //}

    //if (mActions[0].mFinished)
    if (mActionFinished)
    {
      if (mAutoTerminate)
      {
        mTerminated = true;
      }
      else
      {
        //mActions[0].mFinished = false;
        mActionFinished = false;
      }
    }
  }
}