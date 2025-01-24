package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

public class CObjTrap extends CObjBase
{
  private int mDamage;
  private CObjPlayer mPlayer;

  public CObjTrap(CLevel _level, CXSprite _sprite, int _x, int _y, int _damage)
  {
    super(_level, _sprite, _x, _y);

    mActions = new CActions(2);
    mActions.setSpeedsCount(1);
    mActions.SetSpeedIndexStart(0, 0);
    mActions.SetSpeedIndexEnd(0, 0);
    mActions.SetSpeedIndexStart(1, 0);
    mActions.SetSpeedIndexEnd(1, 0);

    //mActions[0] = new CAction(6, 0, 3, 0, 0, 0);
    //        index   update  start  end  xspeed   yspeed   autoupd  delta  effect      align
    setAction(0,      2,      0,     1,   0,       0,       true,    1,     0,          0);
    setAction(1,      2,      2,     5,   0,       0,       true,    1,     0,          0);

    SetA(0);

    mDamage = _damage;
    mStaticBB = true;

    mPlayer = mLevel.mObjectManager.GetPlayer();
  }

  public int GetDamage()
  {
    return mDamage;
  }

  public void Update()
  {
    if (!mIsVisible)
    {
      return;
    }

    super.Update();

    int lMidX = GetMidX();
    //int lMidY = (mBB.mTop + mBB.mBottom) >> 1;

    int lPlayerMidX = mPlayer.GetMidX();
    //int lPlayerMidY = (mPlayer.mBB.mTop + mPlayer.mBB.mBottom) >> 1;

    int lDist = Math.abs(lMidX - lPlayerMidX);

    if (lDist < 32)
    {
      if (mCurrentAction == 0)
      {
        SetA(1);
      }
    }
    else
    {
      if (mCurrentAction == 1)
      {
        SetA(0);
      }
    }
  }

  //public void Paint(Graphics g, int _x, int _y)
  //{
  //  super.Paint(g, _x, _y);

    //g.setColor(0xFFFF00);
    //g.drawLine(_x, _y, _x + GetWidth() - 1, _y);
    //g.drawLine(_x, _y + GetHeight() - 1, _x + GetWidth() - 1, _y + GetHeight() - 1);
    //g.drawLine(_x, _y, _x, _y + GetHeight() - 1);
    //g.drawLine(_x + GetWidth() - 1, _y, _x + GetWidth() - 1, _y + GetHeight() - 1);
  //}
}