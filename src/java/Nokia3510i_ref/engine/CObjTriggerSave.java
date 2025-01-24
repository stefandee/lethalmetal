package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import settings.Options;
import settings.OptionsStore;
import javax.microedition.lcdui.*;

public class CObjTriggerSave extends CObjBase
{
  int mCurrentLevel;

  public CObjTriggerSave(int _x, int _y, int _currentLevel)
  {
    super(null, null, _x, _y);

    mCanCollideWithObj = true;
    mCanCollideWithTile = false;

    // compute the bounding box
    mBB.mLeft = mRealX;
    mBB.mRight = mRealX + 32;
    mBB.mTop = mRealY;
    mBB.mBottom = mRealY + 32;
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

    if (!BBCollision(_obj.mBB))
    {
      return;
    }

    //if (_obj instanceof CObjPlayer)
    //{
      // perform save game state
      Options options;

      // load the previous save, to find out the level number
      options = OptionsStore.getInstance().loadGameOptions();

      // alter the position and new level flag
      options.setX(_obj.mRealX);
      //options.setY(_obj.GetY());

      // set the saved y, because the player may enter the trigger
      // using a jump or other actions that is not aligned to the bottom of
      // the trigger
      options.setY(mBB.mBottom - _obj.mXSprite.GetHeight(0));

      options.setNewLevel(false);

      // and save the options
      OptionsStore.getInstance().saveGameOptions(options);

      // and remove this trigger
      mTerminated = true;
    //}
  }

  public void Paint(Graphics g, int _x, int _y)
  {
    //g.setColor(0x00FF00);
    //g.drawRect(mBB.mLeft - _x, mBB.mTop - _y, mBB.mRight - mBB.mLeft, mBB.mBottom - mBB.mTop);
  }

  public void Update()
  {
  }

  public int GetWidth()
  {
    // it's 2 cells width
    return 32;
  }

  public int GetHeight()
  {
    // it's 2 cells height
    return 32;
  }
}