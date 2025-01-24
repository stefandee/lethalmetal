package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import engine.Options;
import engine.GameScreen;
import javax.microedition.lcdui.*;

class CObjTrigger extends CObjBase
{
  //
  // Trigger Save and End common constructor
  //
  int mLevel;

  public CObjTrigger(int _objId, int _x, int _y, int _level)
  {
    super(null, null, _x, _y);

    mId = _objId;

    mLevel = _level;

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

    Options options;

    switch(mId)
    {
      case OBJ_TRIGGER_END:
        if (mLevel == 127)
        {
          // end the game
          //new TriggersObserver().notifyEndGame();
          GameScreen.getInstance().notifyEndGame();
        }
        else
        {
          // load the previous options, for the sound state
          options = CUtility.getInstance().loadGameOptions();

          // perform save game state
          options.setX(0);
          options.setY(0);
          options.setLevelNo((byte)mLevel);
          options.setNewLevel(true);

          //options = new Options(0, 0, (byte)mNextLevel, true, options.getSound());

          // and save the options
          CUtility.getInstance().saveGameOptions(options);

          // notify the level to end
          //new TriggersObserver().notifyEndLevel();
          GameScreen.getInstance().notifyEndLevel();
        }
        break;

      case OBJ_TRIGGER_SAVE:
        // perform save game state
        // load the previous save, to find out the level number
        options = CUtility.getInstance().loadGameOptions();

        // alter the position and new level flag
        options.setX(_obj.mRealX);
        //options.setY(_obj.GetY());

        // set the saved y, because the player may enter the trigger
        // using a jump or other actions that is not aligned to the bottom of
        // the trigger
        options.setY(mBB.mBottom - _obj.mXSprite.GetHeight(0));

        options.setNewLevel(false);

        // and save the options
        CUtility.getInstance().saveGameOptions(options);
        break;

      case OBJ_TRIGGER_TEXT:
        GameScreen.getInstance().notifyTextMessage(mText);
        break;
    }

    // and remove this trigger
    mTerminated = true;
  }

  //
  // Trigger Text Constructor
  //
  String mText;

  public CObjTrigger(int _x, int _y, String _text)
  {
    super(null, null, _x, _y);

    mId = OBJ_TRIGGER_TEXT;

    mText = _text;
    //mShowText = false;

    mCanCollideWithObj = true;
    mCanCollideWithTile = false;

    // compute the bounding box
    mBB.mLeft = mRealX;
    mBB.mRight = mRealX + 32;
    mBB.mTop = mRealY;
    mBB.mBottom = mRealY + 32;
  }

  //
  // Common Triggers Interface
  //
  public void Paint(Graphics g, int _x, int _y)
  {
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