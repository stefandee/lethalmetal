package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import settings.OptionsStore;
import settings.Options;
import javax.microedition.lcdui.*;
import ui.TriggersObserver;

public class CObjTriggerEnd extends CObjBase
{
  int mNextLevel;

  public CObjTriggerEnd(int _x, int _y, int _nextLevel)
  {
    super(null, null, _x, _y);

    mNextLevel = _nextLevel;

    mCanCollideWithObj = true;
    mCanCollideWithTile = false;

    // create the observer manager
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
      if (mNextLevel == 127)
      {
        // end the game
        new TriggersObserver().notifyEndGame();
      }
      else
      {
        // load the previous options, for the sound state
        Options options = OptionsStore.getInstance().loadGameOptions();

        // perform save game state
        options.setX(0);
        options.setY(0);
        options.setLevelNo((byte)mNextLevel);
        options.setNewLevel(true);

        //options = new Options(0, 0, (byte)mNextLevel, true, options.getSound());

        // and save the options
        OptionsStore.getInstance().saveGameOptions(options);

        // notify the level to end
        new TriggersObserver().notifyEndLevel();
      }

       // and remove this trigger
      mTerminated = true;
    //}
  }

  public void Paint(Graphics g, int _x, int _y)
  {
    //g.setColor(0xFFFFFF);
    //g.drawRect(_x, _y, 32, 32);
  }

  public void Update()
  {
  }

  public int GetWidth()
  {
    return 32;
  }

  public int GetHeight()
  {
    return 32;
  }
}