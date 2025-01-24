package engine;

//package impalator;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */
import ui.*;
import javax.microedition.lcdui.*;
import com.nokia.mid.ui.*;
import java.io.*;
import java.util.Vector;

import settings.Options;
import settings.OptionsStore;

public class CLevel
{
  public CTileManager mTileManager = null;
  public CXSpriteManager mSpriteManager = null;
  public CObjectManager mObjectManager = null;
  public CEnvManager mEnvManager = null;

  public Vector mManagerObjects = null;

  public CXSprite mNumbers = null, mHealthSprite, mAmmoSprite;
  public Image    mScoreImage = null;

  int mViewX = 0, mViewY = 0, mTargetViewX = 0, mTargetViewY = 0, mPlayerY, mMapWidth, mMapHeight;
  public CObjPlayer mPlayer = null;
  boolean mPause;

  public int mScore = 0, mLastScore = -1;
  public int mMaxEnemies, mShotEnemies;

  public int mScreenWidth, mScreenHeight;

  static final byte PLAYER_ITEM = 0;
  static final byte HEALTH_ITEM = 1;
  static final byte WEAPON_ITEM = 2;
  static final byte MONEY_ITEM = 3;
  static final byte MINE_ITEM = 4;
  static final byte SLASHER_ITEM = 6;
  static final byte SHOOTER_ITEM = 5;
  static final byte TRIGGER_ENDLEVEL = 7;
  static final byte TRIGGER_SAVEGAME = 8;
  static final byte TRIGGER_TEXT = 9;
  static final byte BUTTON_ITEM = 10;
  static final byte ELEVATOR_ITEM = 11;
  static final byte AUTOMATIC_ELEVATOR_ITEM = 12;
  static final byte ROBOT_ITEM = 13;
  static final byte PROP_ITEM = 14;
  static final byte AMMO_ITEM = 15;

  public CLevel(int _screenWidth, int _screenHeight)
  {
    //mTileManager = null;
    //System.out.println("ctor level free: " + Runtime.getRuntime().freeMemory());
    Init();

    mScreenWidth  = _screenWidth;
    mScreenHeight = _screenHeight;
  }

  //public void Init(CTileManager _tilem)
  public void Init()
  {
    try
    {
      System.gc();
      //System.out.println("init level free: " + Runtime.getRuntime().freeMemory());

      //mTileManager = new CTileManager();
      //System.out.println("engine.CLevel constructor start");
      //mTileManager = _tilem;
      //System.out.println("engine.CLevel new XSpriteManager");
      mSpriteManager = new CXSpriteManager();

      mSpriteManager.AddSprite("SHOO");
      mSpriteManager.AddSprite("PLAY");

      mObjectManager = new CObjectManager();
      mTileManager = new CTileManager();

      new CEnvManager(null, 2, 2);
      //System.out.println("engine.CLevel new engine.CObjectManager");
      //System.out.println("engine.CLevel new engine.CEnvManager");
      //mEnvManager = new CEnvManager();
      //System.out.println("engine.CLevel reading map");
      //ReadMap();
      System.gc();

      // create the observer manager
      //System.out.println("engine.CLevel create observers");
      //System.out.println("engine.CLevel constructor end");

      //
      // preload the sprites
      //
      mSpriteManager.AddSprite("BLUE");
      mSpriteManager.AddSprite("BLIN");
      mSpriteManager.AddSprite("IMPA");
      mSpriteManager.AddSprite("BULL");
      mSpriteManager.AddSprite("EXPL");
      mSpriteManager.AddSprite("MINE");
      mSpriteManager.AddSprite("HEAL");
      mSpriteManager.AddSprite("R2BO");
      mSpriteManager.AddSprite("R2HE");
      mSpriteManager.AddSprite("R2AR");
      mSpriteManager.AddSprite("ELEV");
      mSpriteManager.AddSprite("COMP");
      mSpriteManager.AddSprite("RED_");
      mSpriteManager.AddSprite("BOXB");
      mSpriteManager.AddSprite("ANI1");
      //mSpriteManager.AddSprite("ANI2");
      //mSpriteManager.AddSprite("ROBO");
      //System.out.println("init level free: " + Runtime.getRuntime().freeMemory());

      //
      // preload the zoundz
      //
      //CFeedback.getInstance().addSound("????");

      //
      // preload hud sprites
      //
      mHealthSprite = mSpriteManager.AddSprite("IHEA");
      mAmmoSprite   = mSpriteManager.AddSprite("IBUL");

      //
      // preload the score and create an image canvas
      //
      //mNumbers = mSpriteManager.AddSprite("N.SP");
      //mScoreImage = Image.createImage(30, 5);

      System.gc();
      //System.out.println("init level free: " + Runtime.getRuntime().freeMemory());
    }
    catch(Exception e)
    {
      //System.out.println("Exception: " + this.getClass().getName());
      //e.printStackTrace();
    }
  }

  public void ReadMap()
  {
    try
    {
      //System.out.println("CLevel::ReadMap");

      Clean();
      System.gc();

      //System.out.println("level readmap options read: " + Runtime.getRuntime().freeMemory());
      // get the game options stored in the phone memory
      Options options = OptionsStore.getInstance().loadGameOptions();

      int lOptionsLevel = options.getLevelNo();
      int lOptionsX = options.getX();
      int lOptionsY = options.getY();
      boolean lIsNewLevel = options.isNewLevel();

      //System.out.println("loading level " + lOptionsLevel);

      options = null;
      System.gc();

      //mSpriteManager.PrintEntries();

      // the map name is composed based on the level number (extracted from
      // the phone memory) and a string ("level")
      //InputStream lIs = getClass().getResourceAsStream("/map1.map");
      lOptionsLevel = 1;
      InputStream lIs = getClass().getResourceAsStream("/map" + lOptionsLevel + ".map");
      //System.out.println("reading map: " + lOptionsLevel);
      //InputStream lIs = getClass().getResourceAsStream("/map6.map");
      //System.out.println("level istream opened: " + Runtime.getRuntime().freeMemory());
      //InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName());
      //lIs.skip(CDataFile.getInstance().GetLocation("MAP" + options.getLevelNo()));

      System.gc();

      //System.out.println("level readmap: " + Runtime.getRuntime().freeMemory());
      //int lWidth, lHeight;

      int lWidth = lIs.read();
      int lHeight = lIs.read();

      //mEnvManager = null;
      //mPlayer = null;
      //mObjectManager.Clean();
      //mManagerObjects = null;
      //System.gc();

      //System.gc();
      //System.out.println("level envmanager: " + Runtime.getRuntime().freeMemory());
      mEnvManager = new CEnvManager(mTileManager, lWidth, lHeight);
      //System.gc();
      //System.out.println("level envmanager: " + Runtime.getRuntime().freeMemory());
      //System.gc();
      //System.out.println("level envmanager: " + Runtime.getRuntime().freeMemory());

      //mViewX = 0;
      //mViewY = Math.max(mPlayer.mBB.mTop - 32, 0);
      //mViewY = (mEnvManager.GetHeight() << 4) - 65;
      //mTargetViewX = mViewX;
      //mTargetViewY = mViewY;

      //mMap = new byte[mWidth][mHeight];

      //
      // read tiles
      //
      for(int x = 0; x < lWidth; x++)
      {
        for(int y = 0; y < lHeight; y++)
        {
          int lTemp = lIs.read();

          if (lTemp == -1)
          {
            return;
          }

          //mMap[x][y] = (byte)lTemp;
          mEnvManager.SetMap(x, y, (byte)lTemp);
        }
      }

      mMapWidth  = lWidth;
      mMapHeight = lHeight;

      //System.out.println(mMapWidth + "...." + mEnvManager.GetWidth());
      //System.out.println(mMapHeight + "...." + mEnvManager.GetHeight());
      //System.gc();
      //System.out.println(Runtime.getRuntime().freeMemory());

      //
      // read the objects
      //

      // read the items number
      int lItemNo = lIs.read();

      int lItemParam[];

      lItemParam = new int[10];

      for(int i = 0; i < lItemNo; i++)
      {
        int lType = lIs.read();
        int lX = CDataFile.BuildShort(lIs.read(), lIs.read());
        int lY = CDataFile.BuildShort(lIs.read(), lIs.read());

        //System.out.print("object: " + lType + " at " + lX + ", " + lY);
        //System.out.println(", free: " + Runtime.getRuntime().freeMemory());

        switch(lType)
        {
          case PLAYER_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();
            lItemParam[3] = lIs.read();
            lItemParam[4] = lIs.read();

            //System.out.println("loading player start");

            if (!lIsNewLevel)
            {
              // use the map default position for the player (new level)
              //System.out.println("---BEFORE ctor player free: " + Runtime.getRuntime().freeMemory());
              //mObjectManager.AddObject(new engine.CObjParticleStorm(lX, lY));
              lX = lOptionsX;
              lY = lOptionsY;
            }
            /*
            else
            {
              // use the positions stored in the phone memory
              System.out.println("before ctor player free: " + Runtime.getRuntime().freeMemory());
              mPlayer = new CObjPlayer(mObjectManager, mSpriteManager, mEnvManager,
                                       mSpriteManager.AddSprite("PLAY"),
                                       options.getX(), options.getY());

              //mObjectManager.AddObject(new engine.CObjParticleStorm(options.getX(), options.getY()));
            }
            */

            //System.out.println("loading player end");

            //System.out.println("player free: " + Runtime.getRuntime().freeMemory());
            System.gc();
            mPlayer = new CObjPlayer(this,
                                     mSpriteManager.AddSprite("PLAY"),
                                     lX, lY, lItemParam[0]);

            mObjectManager.AddObject(mPlayer);
            //System.out.println("player free: " + Runtime.getRuntime().freeMemory());

            // HACK: init view coordinates
            mViewX = mTargetViewX = mPlayer.mRealX;
            //mViewY = mTargetViewY = mPlayer.mRealY;
            mViewY = mTargetViewY = Math.max(mPlayer.mBB.mTop - 32, 0);

            break;

          case HEALTH_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            mObjectManager.AddObject(new CObjPowerUp(this, mSpriteManager.AddSprite("HEAL"), lX, lY, CObjPowerUp.POWER_HEALTH, lItemParam[0]));
            break;

          case AMMO_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            mObjectManager.AddObject(new CObjPowerUp(this, mSpriteManager.AddSprite("BOXB"), lX, lY, CObjPowerUp.POWER_AMMO, lItemParam[0]));
            break;

          //case WEAPON_ITEM:
            // read object params
            //lItemParam[0] = lIs.read();
            //mObjectManager.AddObject(new CObjPowerUp(mSpriteManager.AddSprite("WEAP"), lX, lY, CObjPowerUp.POWER_HEALTH, lItemParam[0]));
            //break;

          case MINE_ITEM:
            // read object params
            //System.out.println("loading a mine");
            lItemParam[0] = lIs.read();
            mObjectManager.AddObject(new CObjTrap(this, mSpriteManager.AddSprite("MINE"), lX, lY, lItemParam[0]));
            break;

          case SLASHER_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();
            lItemParam[3] = lIs.read();
            lItemParam[4] = lIs.read();
            lItemParam[5] = lIs.read();
            lItemParam[6] = lIs.read();

            mObjectManager.AddObject(new CObjAISlasher(this, mSpriteManager.AddSprite("R2BO"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], lItemParam[4], lItemParam[5], lItemParam[6]));
            break;

          case SHOOTER_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();
            lItemParam[3] = lIs.read();
            lItemParam[4] = lIs.read();
            lItemParam[5] = lIs.read();
            lItemParam[6] = lIs.read();
            lItemParam[7] = lIs.read();

            mObjectManager.AddObject(new CObjAIShooter(this, mSpriteManager.AddSprite("SHOO"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], lItemParam[4], lItemParam[5], lItemParam[6], lItemParam[7]));
            break;

          //case ROBOT_ITEM:
            // read object params
            //lItemParam[0] = lIs.read();
            //lItemParam[1] = lIs.read();
            //lItemParam[2] = lIs.read();
            //lItemParam[3] = lIs.read();
            //lItemParam[4] = lIs.read();

            //mObjectManager.AddObject(new CObjRobot(mObjectManager, mSpriteManager, mEnvManager, mSpriteManager.AddSprite("ROBO"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[4], lItemParam[3]));
            //break;

          //case MONEY_ITEM:
            // read object params
            //lItemParam[0] = lIs.read();
            //break;

          case TRIGGER_SAVEGAME:
            // this trigger has no params
            mObjectManager.AddObject(new CObjTriggerSave(lX, lY, 1));
            break;

          case TRIGGER_ENDLEVEL:
            // 0 - next level
            lItemParam[0] = lIs.read();

            //System.out.println("NEXT LEVEL TRIGGER: " + lItemParam[0]);

            mObjectManager.AddObject(new CObjTriggerEnd(lX, lY, (byte)lItemParam[0]));
            break;

          case TRIGGER_TEXT:
            // read object params
            lItemParam[0] = lIs.read();

            StringBuffer lText = new StringBuffer();

            for(int j = 0; j < lItemParam[0]; j++)
            {
              lText.append((char)lIs.read());
            }

            mObjectManager.AddObject(new CObjTriggerText(lX, lY, lText.toString()));

            lText = null;
            break;

          case BUTTON_ITEM:
            // read object params
            lItemParam[0] = lIs.read();

            mObjectManager.AddObject(new CObjButton(this, mSpriteManager.AddSprite("COMP"), lX, lY, (byte)lItemParam[0]));
            break;

          case ELEVATOR_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();

            mObjectManager.AddObject(new CObjTouchElevator(this, mSpriteManager.AddSprite("ELEV"), lX, lY, (byte)lItemParam[0], (byte)lItemParam[1], (byte)lItemParam[2]));
            break;

          case AUTOMATIC_ELEVATOR_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();
            lItemParam[3] = lIs.read();
            lItemParam[4] = lIs.read();

            //mObjectManager.AddObject(new CObjAutomaticElevator(mObjectManager, mSpriteManager.AddSprite("ELEV"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], (byte)lItemParam[4]));
            mObjectManager.AddObject(new CObjElevator(this, mSpriteManager.AddSprite("ELEV"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], (byte)lItemParam[4]));
            break;

          case PROP_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();

            boolean lAutoTerminate = (lItemParam[2] == 1);

            mObjectManager.AddObject(new CObjAnimation(this, mSpriteManager.AddSprite("ANI" + lItemParam[0]), lX, lY, lAutoTerminate, lItemParam[2], 0, 0));
            break;
        }

        //System.out.println("}");

        //System.gc();
      }

      //options = null;
      lItemParam = null;
      lIs.close();
    }
    catch(Exception e)
    {
      //System.err.println("ReadMap failed " + this.getClass().getName());
      //e.printStackTrace();
    }

    System.gc();

    // compute the collision map
    //System.out.println("compute collision map");
    mEnvManager.ComputeCollisionMap();

    // compute the grab points
    //System.out.println("compute grab points");
    mEnvManager.ComputeGrabPoints();

    mPlayer.mGrabPointsSize = mEnvManager.GetGrabPoints().size();
    mPlayer.mCloseGrabPointsSize = mEnvManager.GetCloseGrabPoints().size();

    //System.out.println(mPlayer.mGrabPointsSize);
    //System.out.println(mPlayer.mCloseGrabPointsSize);

    SetupView();
    //mObjectManager.SortList();

    //
    // statistics
    //
    //mMaxEnemies  = mObjectManager.EnemiesCount();
    //mShotEnemies = 0;

    mManagerObjects = mObjectManager.mObjects;

    mPause = false;

    //mPlayerY = mPlayer.GetY();
    //mViewY = mPlayerY - 56/2;

    // temporary code - testing view scroll
    /*
    if (mPlayer.mCurrentAction == mPlayer.A_CROUCH_WALK_LEFT || mPlayer.mCurrentAction == mPlayer.A_MOVE_LEFT ||
        mPlayer.mCurrentAction == mPlayer.A_JUMP_LEFT || mPlayer.mCurrentAction == mPlayer.A_JUMP_FALL_LEFT)
    {
      mViewX = Math.max(mPlayer.GetX() - 96 + 16 * 1, 0);
    }

    if (mPlayer.mCurrentAction == mPlayer.A_CROUCH_WALK_RIGHT || mPlayer.mCurrentAction == mPlayer.A_MOVE_RIGHT ||
        mPlayer.mCurrentAction == mPlayer.A_JUMP_RIGHT || mPlayer.mCurrentAction == mPlayer.A_JUMP_FALL_RIGHT)
    {
      mViewX = Math.max(mPlayer.GetX() - 16 * 1, 0);
    }

    mTargetViewX = mViewX;
   */

    //mSpriteManager.PrintEntries();
    System.gc();
  }

  //private int BuildShort(int lsb, int msb)
  //{
  //  return lsb + (msb << 8);
  //}

  // returns the tile that covers the point sent as parameter
  private int GetClosestTile(int _x, int _y)
  {
    // common case: a tile is sitting right in this map location
    if (mEnvManager.GetMap(_x, _y) != -1)
    {
      return mEnvManager.GetMap(_x, _y);
    }

    for(int x = _x; x > Math.max(_x - 3, 0); x--)
    {
      for(int y = _y; y > Math.max(_y - 3, 0); y--)
      {
        if (mEnvManager.GetMap(x, y) != -1)
        {
          //CTile lTile = mTileManager.GetTile(mEnvManager.GetMap(x, y));

          //if (CTile.HEIGHT + y >= _y && CTile.WIDTH + x >= _x)
          //{
          //  return mEnvManager.GetMap(x, y);
          //}
        }
      }
    }


    return -1;
  }

  private void PaintMapTiles(Graphics g)
  {
    int lMaxX = Math.min(((mViewX + mScreenWidth) >> 4) + 1, mMapWidth);
    int lMaxY = Math.min(((mViewY + mScreenHeight) >> 4) + 1, mMapHeight);
    int lMinX = Math.max(mViewX >> 4, 0);
    int lMinY = Math.max((mViewY >> 4) /*- 1*/, 0);

    DirectGraphics dg = DirectUtils.getDirectGraphics(g);

    // draw map tiles
    int lXStart = lMinX * mEnvManager.mHeight;

    for(int x = lMinX; x < lMaxX; x++)
    {
      int lPaintX = (x << 4) - mViewX;
      int lPaintY = (lMinY << 4) - mViewY;

      for(int y = lMinY; y < lMaxY; y++)
      {
        //int lTileIndex = mEnvManager.GetMap(x, y);
        int lTileIndex = mEnvManager.mMap[lXStart + y];

        if (lTileIndex != -1)
        {
          mTileManager.Paint(dg, lTileIndex, lPaintX, lPaintY, 0);
        }

        lPaintY += 16;
      }

      lXStart += mEnvManager.mHeight;
    }
  }

  private void PaintGrabPoints(Graphics g)
  {
    int lMaxX = Math.min(mViewX/16 + 96/16 + 1, mEnvManager.GetWidth());
    int lMaxY = Math.min(mViewY/16 + 65/16 + 1, mEnvManager.GetHeight());
    int lMinX = Math.max(mViewX/16 - 3, 0);
    int lMinY = Math.max(mViewY/16 - 3, 0);

    Vector lGrabPoints = mEnvManager.GetGrabPoints();

    for(int i = 0; i < lGrabPoints.size(); i++)
    {
      CPoint lPoint = (CPoint)lGrabPoints.elementAt(i);

      if (lPoint.x > lMinX * 16 && lPoint.x < lMaxX * 16 &&
          lPoint.y > lMinY * 16 && lPoint.y < lMaxY * 16)
      {
        g.setColor(0xFFFF00);
        g.fillRect(lPoint.x - mViewX, lPoint.y - mViewY, 2, 2);
      }
    }

    Vector lCloseGrabPoints = mEnvManager.GetCloseGrabPoints();

    for(int i = 0; i < lCloseGrabPoints.size(); i++)
    {
      CPoint lPoint = (CPoint)lCloseGrabPoints.elementAt(i);

      if (lPoint.x > lMinX * 16 && lPoint.x < lMaxX * 16 &&
          lPoint.y > lMinY * 16 && lPoint.y < lMaxY * 16)
      {
        g.setColor(0xFF0000);
        g.fillRect(lPoint.x - mViewX, lPoint.y - mViewY, 2, 2);
      }
    }
  }

  private void PaintCollisionMap(Graphics g)
  {
    int lMaxX = Math.min(mViewX / 16 + 96 / 16 + 1, mEnvManager.GetWidth() + 1);
    int lMaxY = Math.min(mViewY / 16 + 65 / 16 + 1, mEnvManager.GetHeight());
    int lMinX = Math.max(mViewX / 16 - 3, -1);
    int lMinY = Math.max(mViewY / 16 - 3, 0);

    for(int x = lMinX; x < lMaxX; x++)
    {
      for(int y = lMinY; y < lMaxY; y++)
      {
        /*
        if ((mEnvManager.GetCollisionMap(x, y) & CTileManager.DOWN) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(x*16 - mViewX, (y+1)*16 - mViewY, (x+1)*16 - mViewX, (y+1)*16 - mViewY);
        }

        if ((mEnvManager.GetCollisionMap(x, y) & CTileManager.UP) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(x*16 - mViewX, y*16 - mViewY, (x+1)*16 - mViewX, y*16 - mViewY);
        }

        if ((mEnvManager.GetCollisionMap(x, y) & CTileManager.LEFT) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(x*16 - mViewX, y*16 - mViewY, x*16 - mViewX, (y+1)*16 - mViewY);
        }

        if ((mEnvManager.GetCollisionMap(x, y) & CTileManager.RIGHT) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine((x+1)*16 - mViewX, y*16 - mViewY, (x+1)*16 - mViewX, (y+1)*16 - mViewY);
        }
        */

        if ((mEnvManager.GetCollisionMap(x, y) & CTileManager.HEAD_LADDER) != 0)
        {
          g.setColor(0xFF0000);
          g.drawLine(x*16 - mViewX, y*16 - mViewY, (x+1)*16 - mViewX, (y+1)*16 - mViewY);
          g.drawLine(x*16 - mViewX, (y+1)*16 - mViewY, (x+1)*16 - mViewX, y*16 - mViewY);
        }
      }
    }
  }

  public void PaintScore(Graphics g)
  {
    //
    // this is some sort of caching mechanism, so that we'll only decompose the
    // digits and blit them only if the score changes
    //
    if (mLastScore != mScore)
    {
      int lScore = mScore;
      int x = 26;
      int lCount = 0;

      Graphics lGraphics = mScoreImage.getGraphics();
      lGraphics.setColor(0, 0, 0);
      lGraphics.fillRect(0, 0, 30, 5);

      while (lScore > 0)
      {
        mNumbers.Paint(lGraphics, x, 0, lScore % 10, 0, 0, 1);
        lScore = lScore/10;
        x -= 5;
        lCount++;
      }

      for(int i = 6 - lCount; i > 0; i--)
      {
        mNumbers.Paint(lGraphics, x, 0, 0, 0, 0, 1);
        x -= 5;
      }

      mLastScore = mScore;
    }

    //
    // blit the score iamge
    //
    g.drawImage(mScoreImage, 96 - 30, 0, Graphics.TOP | Graphics.LEFT);
  }

  public void Paint(Graphics g)
  {
    if (getPause())
    {
      return;
    }

   //long lTime = System.currentTimeMillis();
   PaintMapTiles(g);
   //System.out.println("paintmaptiles took: " + (System.currentTimeMillis() - lTime));

   //PaintCollisionMap(g);
   //PaintGrabPoints(g);

   for(int i = mObjectManager.GetCount() - 1; i >= 0; --i)
   {
     CObjBase lObj = (CObjBase)mManagerObjects.elementAt(i);

     if (lObj == null)
     {
       continue;
     }

     lObj.Update();

     if (lObj.mTerminated)
     {
       mObjectManager.mBullets.removeElement(lObj);
       mManagerObjects.removeElementAt(i);
       continue;
     }

     if (lObj.mCanCollideWithTile || lObj.mCanCollideWithObj)
     {
       lObj.BeginCollision();
     }

     if (lObj.mCanCollideWithTile)
     {
       /*
       int lObjX = lObj.mRealX;
       int lObjY = lObj.mRealY;
       int lEnvWidth = mEnvManager.GetWidth();
       int lEnvHeight = mEnvManager.GetHeight();

       int lMinX = Math.max(-1, (lObjX >> 4) - 2);
       int lMaxX = Math.min((lObjX >> 4) + 2, lEnvWidth + 1);

       int lMinY = Math.max((lObjY >> 4) - 2, 0);
       int lMaxY = Math.min((lObjY >> 4) + 2, lEnvHeight - 1);

       for (int x = lMinX; x < lMaxX; x++)
       {
         for (int y = lMaxY; y > lMinY; y--)
         {
           CTile lTile = mTileManager.GetTile(mEnvManager.GetMap(x, y));
           //CTile lTile = mTileManager.GetTile(mEnvManager.mMap[x][y]);

           lObj.ManageTileCollision(lTile, x << 4, y << 4);
         }
       }
       */
      lObj.ManageTileCollision();
     }

     if (lObj.mCanCollideWithBullet)
     {
       int lBulletsCount = mObjectManager.mBullets.size();

       if (lBulletsCount != 0)
       {
         for (int j = 0; j < lBulletsCount; j++) {
           lObj.ManageObjCollision( (CObjBase) (mObjectManager.mBullets.
               elementAt(j)));
         }
       }
     }

     if (lObj.mCanCollideWithObj || lObj != mPlayer)
     {
       /*
       int lObjectCount = mObjectManager.GetCount();
       for (int j = 0; j < lObjectCount; j++)
       {
         if (i == j)
         {
           continue;
         }

         lObj.ManageObjCollision( (CObjBase) mManagerObjects.elementAt(j));
       }
       */

       lObj.ManageObjCollision(mPlayer);
       //mPlayer.ManageObjCollision(lObj);
     }

     if (lObj instanceof CObjPlayer)
     {
       int lObjectCount = mObjectManager.GetCount();
       for (int j = 0; j < lObjectCount; j++)
       {
         lObj.ManageObjCollision((CObjBase) mManagerObjects.elementAt(j));
       }
     }

     if (lObj.mCanCollideWithTile || lObj.mCanCollideWithObj)
     {
       lObj.EndCollision();
     }

     //lObj.Paint(g, mViewX, mViewY);
   }

   // end the level if the player is dead
   ManageEndConditions();

   int mCount = mObjectManager.GetCount() - 1;

   for(int i = mCount; i >= 0; --i)
   {
     CObjBase lObj = (CObjBase)mManagerObjects.elementAt(i);
     lObj.Paint(g, mViewX, mViewY);
   }

   SetupView();

   for(int i = 0; i < mPlayer.mHP; i++)
   {
     mHealthSprite.Paint(g, 2 + i * 7, 2, 0, 0, 0, 1);
   }

   for(int i = 0; i < mPlayer.mAmmo; i++)
   {
     mAmmoSprite.Paint(g, mScreenWidth - 7 - i * 7, 2, 0, 0, 0, 1);
   }

   //g.drawString(String.valueOf(mPlayer.GetX()) + ", " + String.valueOf(mPlayer.GetY()) + ".." + mPlayer.mFireAlignX, 30, 55, Graphics.TOP | Graphics.LEFT);

   //g.setColor(0xFF0000);
   //g.fillRect(1, 0, 30, 3);
   //g.setColor(0x00FF00);
   //g.fillRect(1, 0, 30 * mPlayer.GetHP() / 100, 3);

   //
   // draw player health as discrete value
   //
   //g.setColor(0x00FF00);

   //int lMinHP = Math.min(3, mPlayer.mHP);

   //for(int i = 0; i < lMinHP; i++)
   //{
   //  g.fillRect(1 + i * 8, 0, 6, 3);
   //}

   //
   // draw player weapon power as discrete value
   //
   //g.setColor(0x00FFFF);

   //int lMinWeaponPower = Math.min(3, mPlayer.GetWeaponPower());

   //for(int i = 0; i < lMinWeaponPower; i++)
   //{
   //  g.fillRect(1 + i * 8, 4, 6, 3);
   //}

   //
   // paint the score
   //
   // PaintScore(g);

   // draw stick flags
   /*
   if (mPlayer.mStickLeft)
   {
     g.setColor(0xFF0000);
     for(int i = 0; i < Math.min(3, mPlayer.GetWeaponPower()); i++)
     {
       g.fillRect(1 + i * 8, 8, 6, 3);
     }
   }

   if (mPlayer.mStickRight)
   {
     g.setColor(0xFF0000);
     for(int i = 0; i < Math.min(3, mPlayer.GetWeaponPower()); i++)
     {
       g.fillRect(1 + i * 8, 12, 6, 3);
     }
   }
   */

   //g.setColor(0xFFFFFF);
   //g.drawString(Integer.toString(mPlayer.GetMoney()), 96, 0, Graphics.RIGHT|Graphics.TOP);

   //System.gc();

   //g.setColor(0xFF0000);
   //g.drawString(mScreenWidth + "," + mScreenHeight, 0, 0, 0);
  }

  public void SetupView()
  {
    int lAction = mPlayer.mCurrentAction;

   switch(lAction)
   {
     case CObjPlayer.A_RECOVERY_FALL_LEFT:
     case CObjPlayer.A_RECOVERY_LEFT:
     case CObjPlayer.A_RECOVERY_RISE_LEFT:
     case CObjPlayer.A_TARGETING_LEFT:
     case CObjPlayer.A_CROUCH_TARGETING_LEFT:
     //case CObjPlayer.A_CROUCH_DRAW_WEAPON_LEFT:
     case CObjPlayer.A_CROUCH_WALK_LEFT:
     case CObjPlayer.A_MOVE_LEFT:
     case CObjPlayer.A_RUN_LEFT:
     case CObjPlayer.A_RUN_STOP_LEFT:
     case CObjPlayer.A_JUMP_LEFT:
     case CObjPlayer.A_JUMP_FALL_LEFT:
     case CObjPlayer.A_STOP_LEFT:
     case CObjPlayer.A_CROUCH_LEFT:
     case CObjPlayer.A_FALL_LEFT:
     case CObjPlayer.A_DYING_LEFT:
     case CObjPlayer.A_AIR_DYING_FALL_LEFT:
       mTargetViewX = Math.max(mPlayer.mBB.mLeft - mScreenWidth + 32, 0);
       break;

     case CObjPlayer.A_CLIMB_LADDER:
       mTargetViewX = Math.max(mPlayer.GetMidX() - mScreenHeight / 2, 0);
       break;

     case CObjPlayer.A_RECOVERY_FALL_RIGHT:
     case CObjPlayer.A_RECOVERY_RIGHT:
     case CObjPlayer.A_RECOVERY_RISE_RIGHT:
     case CObjPlayer.A_CROUCH_TARGETING_RIGHT:
     //case CObjPlayer.A_CROUCH_FIRE_RIGHT:
     //case CObjPlayer.A_CROUCH_DRAW_WEAPON_RIGHT:
     case CObjPlayer.A_CROUCH_WALK_RIGHT:
     case CObjPlayer.A_CROUCH_RIGHT:
     case CObjPlayer.A_TARGETING_RIGHT:
     case CObjPlayer.A_MOVE_RIGHT:
     case CObjPlayer.A_RUN_RIGHT:
     case CObjPlayer.A_RUN_STOP_RIGHT:
     case CObjPlayer.A_JUMP_RIGHT:
     case CObjPlayer.A_JUMP_FALL_RIGHT:
     case CObjPlayer.A_STOP_RIGHT:
     case CObjPlayer.A_FALL_RIGHT:
     case CObjPlayer.A_DYING_RIGHT:
     case CObjPlayer.A_AIR_DYING_FALL_RIGHT:
       mTargetViewX = Math.max(mPlayer.mBB.mRight - 32, 0);
   }

   switch(lAction)
   {
     case CObjPlayer.A_CROUCH_LEFT:
     case CObjPlayer.A_CROUCH_RIGHT:
       mTargetViewY = Math.max(mPlayer.mBB.mTop - 36, 0);
       break;

     case CObjPlayer.A_JUMP_OFF_EDGE_LEFT:
     case CObjPlayer.A_JUMP_OFF_EDGE_RIGHT:
     case CObjPlayer.A_CROUCH_WALK_LEFT:
     case CObjPlayer.A_CROUCH_WALK_RIGHT:
       break;

     case CObjPlayer.A_CLIMB_LADDER:
       mTargetViewY = Math.max(mPlayer.GetMidY() - mScreenHeight / 2, 0);
       break;

     default:
       mTargetViewY = Math.max(mPlayer.mBB.mTop - mScreenHeight / 2, 0);
   }

   /*
    if (lAction == CObjPlayer.A_MOVE_LEFT || lAction == CObjPlayer.A_MOVE_RIGHT ||
        lAction == CObjPlayer.A_RUN_LEFT || lAction == CObjPlayer.A_RUN_RIGHT ||
        lAction == CObjPlayer.A_CROUCH_WALK_LEFT || lAction == CObjPlayer.A_CROUCH_WALK_RIGHT ||
        lAction == CObjPlayer.A_RUN_STOP_RIGHT || lAction == CObjPlayer.A_RUN_STOP_LEFT ||
        lAction == CObjPlayer.A_JUMP_OFF_EDGE_LEFT || lAction == CObjPlayer.A_JUMP_OFF_EDGE_RIGHT
        )
    {
      //mTargetViewY = Math.max(mPlayer.mBB.mTop - 56, 0);
    }
    else
    {

      //mViewY = Math.max(mPlayer.mRealY - 56/2, 0);
    }
    */

    mTargetViewX = Math.min(mTargetViewX, (mEnvManager.GetWidth() << 4) - mScreenWidth);
    mTargetViewY = Math.min(mTargetViewY, (mEnvManager.GetHeight() << 4) - mScreenHeight);

    mViewX = (mTargetViewX + mViewX) >> 1;
    mViewY = (3 * mViewY + mTargetViewY) >> 2;

    //System.out.println("targetviewy: " + mTargetViewY);
    //System.out.println("viewy: " + mViewY);
    //System.out.println("player top: " + mPlayer.mBB.mTop);
    //mViewY = Math.min(mViewY, (mEnvManager.GetHeight() << 4) - 56);

    /*
    if (mViewX < mTargetViewX)
    {
      mViewX += 4;

      if (mViewX > mTargetViewX)
      {
        mViewX = mTargetViewX;
      }
    }

    if (mViewX > mTargetViewX)
    {
      mViewX -= 4;

      if (mViewX < mTargetViewX)
      {
        mViewX = mTargetViewX;
      }
    }

    //
    // Y
    //
    if (mViewY <= mTargetViewY)
    {
      mViewY += 2;

      if (mViewY >= mTargetViewY)
      {
        mViewY = mTargetViewY;
      }
    }

    if (mViewY >= mTargetViewY)
    {
      mViewY -= 2;

      if (mViewY <= mTargetViewY)
      {
        mViewY = mTargetViewY;
      }
    }
        */

    //System.out.println("player: (" + mPlayer.mBB.mLeft + "," + mPlayer.mBB.mTop + ").....(" + mViewX + "," + mViewY + ")");
  }

  public int GetViewX()
  {
    return mViewX;
  }

  public int GetViewY()
  {
    return mViewY;
  }

  public void SetViewX(int _x)
  {
    mViewX = _x;

    if (mViewX > (mEnvManager.GetWidth() << 4) - 96)
    {
      mViewX = (mEnvManager.GetWidth() << 4) - 96;
    }

    if (mViewX < 0)
    {
      mViewX = 0;
    }
  }

  public void SetViewY(int _y)
  {
    mViewY = _y;

    if (mViewY > (mEnvManager.GetHeight() << 4) - 65)
    {
      mViewY = (mEnvManager.GetHeight() << 4) - 65;
    }

    if (mViewY < 0)
    {
      mViewY = 0;
    }
  }

  public void ModX(int _delta)
  {
    SetViewX(GetViewX() + _delta);
  }

  public void ModY(int _delta)
  {
    SetViewY(GetViewY() + _delta);
  }

  private void ManageEndConditions()
  {
    if (mPlayer.mHP <= 0)// && ((mPlayer.mCurrentAction == CObjPlayer.A_DEAD_LEFT) || mPlayer.mCurrentAction == CObjPlayer.A_DEAD_RIGHT))
    {
       //notify player is dead !
      new TriggersObserver().notifyPlayerIsDead();
   }
  }

  public void setPause(boolean _v)
  {
    mPause = _v;
  }

  public boolean getPause()
  {
    return mPause;
  }

  public void Clean()
  {
    mObjectManager.Clean();

    mEnvManager = null;
    mManagerObjects = null;
    mPlayer = null;

    System.gc();
  }

  //
  // OBJECT MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //

  //
  // ENVIRONMENT MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //

  //
  // SPRITE MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //

  //
  // TILE MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //
}