package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

/**
 * engine.CObjPowerUp
 *
 * Holder for power up objects: health, weapon powerup
 *
 * Maybe it would have been nice to have a base class powerup and child classes
 * health and weapon powerup
 *
 * A powerup has two traits: type and power (value)
 */

public class CObjPowerUp extends CObjBase
{
  public static final int POWER_HEALTH = 0;
  public static final int POWER_WEAPON = 1;
  public static final int POWER_MONEY  = 2;
  public static final int POWER_AMMO   = 3;

  private int mType, mPower;
  private CObjPlayer mPlayer;

  public CObjPowerUp(CLevel _level, CXSprite _sprite, int _x, int _y, int _type, int _power)
  {
    super(_level, _sprite, _x, _y);

    mActions = new CActions(1);
    mActions.setSpeedsCount(1);
    mActions.SetSpeedIndexStart(0, 0);
    mActions.SetSpeedIndexEnd(0, 0);

    // the default right action
    //        index   update  start  end                      xspeed   yspeed   autoupd  delta  effect      align
    setAction(0,      2,      0,    _sprite.CellsCount() - 1, (byte)0, (byte)0, true,    1,     0,          0);

    mType  = _type;
    mPower = _power;

    SetA(0);

    mStaticBB = true;
    mPlayer = mLevel.mObjectManager.GetPlayer();
  }

  public int GetType()
  {
    return mType;
  }

  public int GetPower()
  {
    return mPower;
  }

  public void Update()
  {
    if (!mIsVisible)
    {
      return;
    }

    super.Update();
  }
}