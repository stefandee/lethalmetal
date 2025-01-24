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
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import com.nokia.mid.ui.*;
import java.io.*;
import java.util.Vector;

import engine.Options;

public class CLevel
{
  public CXSpriteManager mSpriteManager = null;
  //public CEnvManager mEnvManager = null;

  public Vector mManagerObjects = null;

  public CXSprite mNumbers = null, mHealthSprite, mAmmoSprite;
  public Image    mScoreImage = null;

  int mViewX = 0, mViewY = 0, mTargetViewX = 0, mTargetViewY = 0, mPlayerY, mMapWidth, mMapHeight;
  public CObjPlayer mPlayer = null;
  boolean mPause;

  public int mScore = 0, mLastScore = -1;
  public int mMaxEnemies, mShotEnemies;

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

  public CLevel()
  {
    //mTileManager = null;
    //System.out.println("ctor level free: " + Runtime.getRuntime().freeMemory());
    Init();
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

      //
      //  Sprite Manager Iniatialization
      //
      mSpriteManager = new CXSpriteManager();

      mSpriteManager.AddSprite("SHOO");
      mSpriteManager.AddSprite("PLAY");

      //
      // Object Manager Initialization
      //
      mObjects = new Vector(3);
      mBullets = new Vector(3);

      mPlayerActions    = createActions("APLA");
      mAIShooterActions = createActions("ASHO");
      mAISlasherActions = createActions("ASLA");

      //
      // Tile Manager Initialization
      //
      ReadTileSet();

      //new CEnvManager(null, 2, 2);
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
      //mSpriteManager.AddSprite("ANI1");
      mSpriteManager.AddSprite("ANI2");
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
      // preload the score and create a image canvas
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
      Options options = CUtility.getInstance().loadGameOptions();

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
      //System.out.println("reading map: " + lOptionsLevel);
      InputStream lIs = getClass().getResourceAsStream("/map" + lOptionsLevel + ".map");
      //InputStream lIs = getClass().getResourceAsStream("/map6.map");
      //System.out.println("level istream opened: " + Runtime.getRuntime().freeMemory());
      //InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName());
      //lIs.skip(CDataFile.getInstance().GetLocation("MAP" + options.getLevelNo()));

      System.gc();

      //System.out.println("level readmap: " + Runtime.getRuntime().freeMemory());
      //int lWidth, lHeight;

      mMapWidth = lIs.read();
      mMapHeight = lIs.read();

      //mEnvManager = null;
      //mPlayer = null;
      //mObjectManager.Clean();
      //mManagerObjects = null;
      //System.gc();

      //System.gc();
      //System.out.println("level envmanager: " + Runtime.getRuntime().freeMemory());
      //mEnvManager = new CEnvManager(mTileManager, lWidth, lHeight);
      mMap = new byte[mMapWidth * mMapHeight];
      //System.out.println(mMapWidth + "," + mMapHeight);
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
      // read map tiles from file
      //
      for(int x = 0; x < mMapWidth; x++)
      {
        for(int y = 0; y < mMapHeight; y++)
        {
          int lTemp = lIs.read();

          if (lTemp == -1)
          {
            return;
          }

          //mMap[x][y] = (byte)lTemp;
          SetMap(x, y, (byte)lTemp);
        }
      }

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
        int lX = CUtility.BuildShort(lIs.read(), lIs.read());
        int lY = CUtility.BuildShort(lIs.read(), lIs.read());

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

            //System.out.println("loading player end");

            //System.out.println("player free: " + Runtime.getRuntime().freeMemory());
            System.gc();
            mPlayer = new CObjPlayer(this,
                                     mSpriteManager.AddSprite("PLAY"),
                                     lX, lY, lItemParam[0]);

            AddObject(mPlayer);
            //System.out.println("player free: " + Runtime.getRuntime().freeMemory());

            // HACK: init view coordinates
            mViewX = mTargetViewX = mPlayer.mRealX;
            //mViewY = mTargetViewY = mPlayer.mRealY;
            mViewY = mTargetViewY = Math.max(mPlayer.mBB.mTop - 32, 0);

            break;

          case HEALTH_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            AddObject(new CObjPowerUp(this, mSpriteManager.AddSprite("HEAL"), lX, lY, CObjPowerUp.POWER_HEALTH, lItemParam[0]));
            break;

          case AMMO_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            AddObject(new CObjPowerUp(this, mSpriteManager.AddSprite("BOXB"), lX, lY, CObjPowerUp.POWER_AMMO, lItemParam[0]));
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
            AddObject(new CObjTrap(this, mSpriteManager.AddSprite("MINE"), lX, lY, lItemParam[0]));
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

            AddObject(new CObjAISlasher(this, mSpriteManager.AddSprite("R2BO"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], lItemParam[4], lItemParam[5], lItemParam[6]));
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

            AddObject(new CObjAIShooter(this, mSpriteManager.AddSprite("SHOO"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], lItemParam[4], lItemParam[5], lItemParam[6], lItemParam[7]));
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
            //mObjectManager.AddObject(new CObjTriggerSave(lX, lY, 1));
            AddObject(new CObjTrigger(CObjTrigger.OBJ_TRIGGER_SAVE, lX, lY, 1));
            break;

          case TRIGGER_ENDLEVEL:
            // 0 - next level
            lItemParam[0] = lIs.read();

            //System.out.println("NEXT LEVEL TRIGGER: " + lItemParam[0]);

            //mObjectManager.AddObject(new CObjTriggerEnd(lX, lY, (byte)lItemParam[0]));
            AddObject(new CObjTrigger(CObjTrigger.OBJ_TRIGGER_END, lX, lY, (byte)lItemParam[0]));
            break;

          case TRIGGER_TEXT:
            // read object params
            lItemParam[0] = lIs.read();

            StringBuffer lText = new StringBuffer();

            for(int j = 0; j < lItemParam[0]; j++)
            {
              lText.append((char)lIs.read());
            }

            //mObjectManager.AddObject(new CObjTriggerText(lX, lY, lText.toString()));
            AddObject(new CObjTrigger(lX, lY, lText.toString()));

            lText = null;
            break;

          case BUTTON_ITEM:
            // read object params
            lItemParam[0] = lIs.read();

            AddObject(new CObjButton(this, mSpriteManager.AddSprite("COMP"), lX, lY, (byte)lItemParam[0]));
            break;

          case ELEVATOR_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();

            AddObject(new CObjTouchElevator(this, mSpriteManager.AddSprite("ELEV"), lX, lY, (byte)lItemParam[0], (byte)lItemParam[1], (byte)lItemParam[2]));
            break;

          case AUTOMATIC_ELEVATOR_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();
            lItemParam[3] = lIs.read();
            lItemParam[4] = lIs.read();

            //mObjectManager.AddObject(new CObjAutomaticElevator(mObjectManager, mSpriteManager.AddSprite("ELEV"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], (byte)lItemParam[4]));
            AddObject(new CObjElevator(this, mSpriteManager.AddSprite("ELEV"), lX, lY, lItemParam[0], lItemParam[1], lItemParam[2], lItemParam[3], (byte)lItemParam[4]));
            break;

          case PROP_ITEM:
            // read object params
            lItemParam[0] = lIs.read();
            lItemParam[1] = lIs.read();
            lItemParam[2] = lIs.read();

            boolean lAutoTerminate = (lItemParam[2] == 1);

            AddObject(new CObjAnimation(this, mSpriteManager.AddSprite("ANI" + lItemParam[0]), lX, lY, lAutoTerminate, lItemParam[2], 0, 0));
            break;
        }

        //System.out.println("}");

        System.gc();
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
    ComputeCollisionMap();

    // compute the grab points
    ComputeGrabPoints();

    mPlayer.mGrabPointsSize = GetGrabPoints().size();
    mPlayer.mCloseGrabPointsSize = GetCloseGrabPoints().size();

    //System.out.println(mPlayer.mGrabPointsSize);
    //System.out.println(mPlayer.mCloseGrabPointsSize);

    SetupView();
    //mObjectManager.SortList();

    //
    // statistics
    //
    //mMaxEnemies  = mObjectManager.EnemiesCount();
    //mShotEnemies = 0;

    mManagerObjects = mObjects;

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
    //System.out.println("compute grab points");
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
    if (GetMap(_x, _y) != -1)
    {
      return GetMap(_x, _y);
    }

    for(int x = _x; x > Math.max(_x - 3, 0); x--)
    {
      for(int y = _y; y > Math.max(_y - 3, 0); y--)
      {
        if (GetMap(x, y) != -1)
        {
          //CTile lTile = GetTile(mEnvManager.GetMap(x, y));

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
    int lMaxX = Math.min(((mViewX + 96) >> 4) + 1, mMapWidth);
    int lMaxY = Math.min(((mViewY + 65) >> 4) + 1, mMapHeight);
    int lMinX = Math.max(mViewX >> 4, 0);
    int lMinY = Math.max((mViewY >> 4) /*- 1*/, 0);

    DirectGraphics dg = DirectUtils.getDirectGraphics(g);

    // draw map tiles
    int lXStart = lMinX * mMapHeight;

    for(int x = lMinX; x < lMaxX; x++)
    {
      int lPaintX = (x << 4) - mViewX;
      int lPaintY = (lMinY << 4) - mViewY;

      for(int y = lMinY; y < lMaxY; y++)
      {
        //int lTileIndex = mEnvManager.GetMap(x, y);
        int lTileIndex = mMap[lXStart + y];

        if (lTileIndex != -1)
        {
          PaintTile(dg, lTileIndex, lPaintX, lPaintY, 0);
        }

        lPaintY += 16;
      }

      lXStart += mMapHeight;
    }
  }

  private void PaintGrabPoints(Graphics g)
  {
    int lMaxX = Math.min(mViewX/16 + 96/16 + 1, mMapWidth);
    int lMaxY = Math.min(mViewY/16 + 65/16 + 1, mMapHeight);
    int lMinX = Math.max(mViewX/16 - 3, 0);
    int lMinY = Math.max(mViewY/16 - 3, 0);

    Vector lGrabPoints = GetGrabPoints();

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

    Vector lCloseGrabPoints = GetCloseGrabPoints();

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
    int lMaxX = Math.min(mViewX / 16 + 96 / 16 + 1, mMapWidth + 1);
    int lMaxY = Math.min(mViewY / 16 + 65 / 16 + 1, mMapHeight);
    int lMinX = Math.max(mViewX / 16 - 3, -1);
    int lMinY = Math.max(mViewY / 16 - 3, 0);

    for(int x = lMinX; x < lMaxX; x++)
    {
      for(int y = lMinY; y < lMaxY; y++)
      {

        if ((GetCollisionMap(x, y) & DOWN) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(x*16 - mViewX, (y+1)*16 - mViewY, (x+1)*16 - mViewX, (y+1)*16 - mViewY);
        }

        if ((GetCollisionMap(x, y) & UP) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(x*16 - mViewX, y*16 - mViewY, (x+1)*16 - mViewX, y*16 - mViewY);
        }

        if ((GetCollisionMap(x, y) & LEFT) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(x*16 - mViewX, y*16 - mViewY, x*16 - mViewX, (y+1)*16 - mViewY);
        }

        if ((GetCollisionMap(x, y) & RIGHT) != 0)
        {
          g.setColor(0xFFFFFF);
          g.drawLine((x+1)*16 - mViewX, y*16 - mViewY, (x+1)*16 - mViewX, (y+1)*16 - mViewY);
        }

        /*
        if ((mEnvManager.GetCollisionMap(x, y) & HEAD_LADDER) != 0)
        {
          g.setColor(0xFF0000);
          g.drawLine(x*16 - mViewX, y*16 - mViewY, (x+1)*16 - mViewX, (y+1)*16 - mViewY);
          g.drawLine(x*16 - mViewX, (y+1)*16 - mViewY, (x+1)*16 - mViewX, y*16 - mViewY);
        }
        */
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
   //System.out.println("paint map tiles");
   PaintMapTiles(g);
   //System.out.println("painted map tiles");
   //System.out.println("paintmaptiles took: " + (System.currentTimeMillis() - lTime));

   //PaintCollisionMap(g);
   //PaintGrabPoints(g);

   //System.out.println("paint the objects");
   for(int i = GetObjectCount() - 1; i >= 0; --i)
   {
     CObjBase lObj = (CObjBase)mManagerObjects.elementAt(i);

     if (lObj == null)
     {
       continue;
     }

     lObj.Update();

     if (lObj.mTerminated)
     {
       mBullets.removeElement(lObj);
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
           CTile lTile = GetTile(mEnvManager.GetMap(x, y));
           //CTile lTile = GetTile(mEnvManager.mMap[x][y]);

           lObj.ManageTileCollision(lTile, x << 4, y << 4);
         }
       }
       */
      lObj.ManageTileCollision();
     }

     if (lObj.mCanCollideWithBullet)
     {
       int lBulletsCount = mBullets.size();

       if (lBulletsCount != 0)
       {
         for (int j = 0; j < lBulletsCount; j++) {
           lObj.ManageObjCollision( (CObjBase) (mBullets.
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
       int lObjectCount = GetObjectCount();
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

   int mCount = GetObjectCount() - 1;

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
     mAmmoSprite.Paint(g, 89 - i * 7, 2, 0, 0, 0, 1);
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
       mTargetViewX = Math.max(mPlayer.mBB.mLeft - 96 + 32, 0);
       break;

     case CObjPlayer.A_CLIMB_LADDER:
       mTargetViewX = Math.max(mPlayer.GetMidX() - 48, 0);
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
       mTargetViewY = Math.max(mPlayer.GetMidY() - 32, 0);
       break;

     default:
       mTargetViewY = Math.max(mPlayer.mBB.mTop - 32, 0);
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

    mTargetViewX = Math.min(mTargetViewX, (mMapWidth << 4) - 96);
    mTargetViewY = Math.min(mTargetViewY, (mMapHeight << 4) - 65);

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

    if (mViewX > (mMapWidth << 4) - 96)
    {
      mViewX = (mMapWidth << 4) - 96;
    }

    if (mViewX < 0)
    {
      mViewX = 0;
    }
  }

  public void SetViewY(int _y)
  {
    mViewY = _y;

    if (mViewY > (mMapHeight << 4) - 65)
    {
      mViewY = (mMapHeight << 4) - 65;
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
      //new TriggersObserver().notifyPlayerIsDead();
      GameScreen.getInstance().notifyPlayerIsDead();
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
    //
    // Object Manage Clean
    //
    mObjects.removeAllElements();

    //mEnvManager = null;
    mManagerObjects = null;
    mPlayer = null;

    mMap = null;
    mCollisionMap = null;
    //mGrabPoints.removeAllElements();
    //mCloseGrabPoints.removeAllElements();

    mGrabPoints = null;
    mCloseGrabPoints = null;

    System.gc();
  }

  //
  // ENVIRONMENT MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //
  public byte mMap[];
  private byte mCollisionMap[];
  public Vector mGrabPoints;
  public Vector mCloseGrabPoints;

  public void SetMap(int _x, int _y, byte _data)
  {
    //mMap[_x][_y] = _data;
    mMap[_x * mMapHeight + _y] = _data;
  }

  public byte GetMap(int _x, int _y)
  {
    //if (_x < 0 || _y < 0 || _x >= mWidth || _y >= mHeight)
    //{
    //  return -1;
    //}

    //return mMap[_x][_y];
    return mMap[_x * mMapHeight + _y];
  }

  public int GroundDist(int x, int y)
  {
    if (y == mMapHeight - 1)
    {
      return 10;
    }

    if ((mCollisionMap[(x+1) * mMapHeight + y+1] & UP) != 0)
    {
      return 1;
    }

    return 10;
  }

  public void ComputeGrabPoints()
  {
    mGrabPoints = new Vector(25);
    mCloseGrabPoints = new Vector(28);

    //mGrabPoints = new CPoint[30];
    //mCloseGrabPoints = new CPoint[30];

    int lGrabPointsIndex = 0, lCloseGrabPointsIndex = 0;

    for(int x = 0; x < mMapWidth; x++)
    {
      for(int y = 0; y < mMapHeight; y++)
      {
        int lIndexX2 = (x+2) * mMapHeight + y;
        int lIndexX1 = (x+1) * mMapHeight + y;
        int lIndexX = lIndexX1 - mMapHeight;

        if ((mCollisionMap[lIndexX1] & LEFT) != 0 && (mCollisionMap[lIndexX1] & UP) != 0
            && (mCollisionMap[lIndexX] == 0 || (mCollisionMap[lIndexX] & HEAD_LADDER) != 0)
            )
        {
          if (GroundDist(x-1, y) > 1)
          {
            mGrabPoints.addElement(new CPoint(x << 4, y << 4));
            //mGrabPoints[lGrabPointsIndex++] = new CPoint(x << 4, y << 4);
          }
          else
          {
            mCloseGrabPoints.addElement(new CPoint(x << 4, y << 4));
            //mCloseGrabPoints[lCloseGrabPointsIndex++] = new CPoint(x << 4, y << 4);
          }
        }

        if ((mCollisionMap[lIndexX1] & RIGHT) != 0 && (mCollisionMap[lIndexX1] & UP) != 0
            && (mCollisionMap[lIndexX2] == 0 || (mCollisionMap[lIndexX2] & HEAD_LADDER) != 0)
            )
        {
          if (GroundDist(x+1, y) > 1)
          {
            mGrabPoints.addElement(new CPoint( (x << 4) + 16, y << 4));
            //mGrabPoints[lGrabPointsIndex++] = new CPoint(x << 4, y << 4);
          }
          else
          {
            mCloseGrabPoints.addElement(new CPoint( (x << 4) + 16, y << 4));
            //mCloseGrabPoints[lCloseGrabPointsIndex++] = new CPoint(x << 4, y << 4);
          }
        }
      }
    }
  }

  public void ComputeCollisionMap()
  {
    //mCollisionMap = new byte[mWidth + 2][mHeight];
    mCollisionMap = new byte[(mMapWidth + 2) * mMapHeight];

    //try
    //{
      for (int x = 0; x < mMapWidth; x++)
      {
        for (int y = 0; y < mMapHeight; y++)
        {
          int lIndex = (x+1) * mMapHeight + y;

          mCollisionMap[lIndex] = 0;

          if (x == 0)
          {
            mCollisionMap[y] |= RIGHT;
          }

          if (x == mMapWidth - 1)
          {
            mCollisionMap[(mMapWidth + 1) * mMapHeight + y] |= LEFT;
          }

          //CTile lTile = GetTileAt(x, y);
          int lTileIndex = GetTileAt(x, y);

          if (lTileIndex == -1)
          {
            continue;
          }

          // walk the collidable edges of this tile and check the neighbouring tiles
          // for collisions

          //
          // if a tile is ladder, then check if above we have a head ladder
          // and if the near left and right tiles are UP collidable (floors). if so,
          // put a DOWN collide flag in this square
          //
          //if (lTile.GetIsLadder())
          if (Collision(lTileIndex, LADDER))
          {
            int lUpTileIndex = GetTileAt(x, y - 1);
            int lLeftTileIndex = GetTileAt(x - 1, y);
            int lRightTileIndex = GetTileAt(x + 1, y);

            if (lUpTileIndex != -1 && lLeftTileIndex != -1 && lRightTileIndex != -1)
            {
              if (!Collision(lUpTileIndex, LADDER))
              {
                mCollisionMap[lIndex - 1] |= HEAD_LADDER;
                //System.out.println("found head ladder at " + (x + 1) + "," + (y - 1) + " => " + mCollisionMap[x + 1][y - 1]);
              }

              if (!Collision(lUpTileIndex, LADDER) &&
                   (Collision(lLeftTileIndex, UP) ||
                   Collision(lRightTileIndex, UP)))
              {
                mCollisionMap[lIndex] |= UP;
                mCollisionMap[lIndex] |= LADDER;
                continue;
              }
            }

            mCollisionMap[lIndex] |= LADDER;
          }

          if (Collision(lTileIndex, LEFT))
          {
            for (int _y = y; _y < y + HEIGHT/16; _y++)
            {
              int lColTileIndex = GetTileAt(x - 1, _y);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(x+1) * mMapHeight + _y] |= LEFT;
                continue;
              }

              if (!Collision(lColTileIndex, RIGHT))
              {
                mCollisionMap[(x+1) * mMapHeight + _y] |= LEFT;
              }
            }
          }

          if (Collision(lTileIndex, UP)) {
            for (int _x = x; _x < x + WIDTH/16; _x++) {
              int lColTileIndex = GetTileAt(_x, y - 1);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(_x+1) * mMapHeight + y] |= UP;
                continue;
              }

              if (!Collision(lColTileIndex, DOWN)) {
                mCollisionMap[(_x+1) * mMapHeight + y] |= UP;
              }
            }
          }

          if (Collision(lTileIndex, DOWN)) {
            for (int _x = x; _x < x + WIDTH/16; _x++) {
              int lColTileIndex = GetTileAt(_x, y + 1);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(_x+1) * mMapHeight + y] |= DOWN;
                continue;
              }

              if (!Collision(lColTileIndex, UP)) {
                mCollisionMap[(_x+1) * mMapHeight + y] |= DOWN;
              }
            }
          }

          if (Collision(lTileIndex, RIGHT)) {
            for (int _y = y; _y < y + HEIGHT/16; _y++) {
              int lColTileIndex = GetTileAt(x + 1, _y);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(x+1) * mMapHeight + _y] |= RIGHT;
                continue;
              }

              if (!Collision(lColTileIndex, LEFT))
              {
                mCollisionMap[(x+1) * mMapHeight + _y] |= RIGHT;
              }
            }
          }
        }
      }
    //}
    //catch(Exception e)
    //{
      //System.out.println("Error computing collision map.");
      //e.printStackTrace();
    //}

    //for(int i = 0; i < mHeight; i++)
    //{
    //  for(int j = 0; j < mWidth + 1; j++)
    //  {
    //    System.out.print(GetCollisionMap(j, i) + "  ");
    //  }

    //  System.out.println(" ");
    //}
  }

  private int GetTileAt(int _x, int _y)
  {
    if (_x < 0 || _y < 0 || _x >= mMapWidth || _y >= mMapHeight)
    {
      return -1;
    }

    //byte lIndex = mMap[_x * mHeight + _y];

    return mMap[_x * mMapHeight + _y];

    //if (lIndex != -1)
    //{
    //  return GetTile(lIndex);
    //}

    //return -1;
  }

  public int GetCollisionMap(int _x, int _y)
  {
    //return mCollisionMap[_x + 1][_y]; // tile from -1 to width + 1, to include collision with level margins
    return mCollisionMap[(_x + 1) * mMapHeight + _y];
  }

  public Vector GetGrabPoints()
  //public CPoint[] GetGrabPoints()
  {
    return mGrabPoints;
  }

  public Vector GetCloseGrabPoints()
  //public CPoint[] GetCloseGrabPoints()
  {
    return mCloseGrabPoints;
  }

  //
  // OBJECT MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //
  public Vector mObjects;
  public Vector mBullets;

  public CActions mPlayerActions;
  public CActions mAISlasherActions;
  public CActions mAIShooterActions;

  public void AddObject(CObjBase _obj)
  {
    //for(int i = 0; i < mObjectCount; i++)
    //{
    //  if (mObjects[i] == null)
    //  {
    //    mObjects[i] = _obj;
    //    return;
    //  }
    //}

    // no protection
    //mObjects[mObjectCount] = _obj;
    //mObjectCount++;
    mObjects.addElement(_obj);
    //mObjects.insertElementAt(_obj, 0);

    if (_obj instanceof CObjPlayer)
    {
      mPlayer = (CObjPlayer)_obj;
    }

    if (_obj instanceof CObjBullet)
    {
      mBullets.addElement(_obj);
    }
  }

  public void InsertObject(CObjBase _obj)
  {
    mObjects.insertElementAt(_obj, 0);

    if (_obj instanceof CObjPlayer)
    {
      mPlayer = (CObjPlayer)_obj;
    }

    if (_obj instanceof CObjBullet)
    {
      mBullets.addElement(_obj);
    }
  }

  CObjPlayer GetPlayer()
  {
    return mPlayer;
  }

  public int EnemiesCount()
  {
    int lCount = 0;
    int lSize = mObjects.size();

    for(int i = 0; i < lSize; i++)
    {
      CObjBase lObj = (CObjBase) (mObjects.elementAt(i));

      if (lObj instanceof CObjAIShooter)// || lObj instanceof CObjAISlasher)
          //lObj instanceof CObjRobot)
      {
        lCount++;
      }
    }

    //System.out.println("Enemy count: " + lCount);
    return lCount;
  }

  /*
  public void SortList()
  {
    Vector lTemp = new Vector(mObjects.size());

    // move elevators and animations at the end of the list (should be painted over the player)
    int lSize = mObjects.size();

    for(int i = 0; i < lSize; i++)
    {
      CObjBase lObj = (CObjBase)(mObjects.elementAt(i));

      if (lObj instanceof CObjElevator || lObj instanceof CObjAnimation)
      {
        lTemp.insertElementAt(lObj, 0);
      }
      else
      {
        lTemp.addElement(lObj);
      }
    }

    mObjects = lTemp;
    lTemp = null;
  }
  */

  public int GetObjectCount()
  {
    //return mObjectCount;
    return mObjects.size();
  }

  public CObjTouchElevator GetElevator(int _id)
  {
    int lSize = mObjects.size();

    for(int i = 0; i < lSize; i++)
    {
      CObjBase lObj = (CObjBase)(mObjects.elementAt(i));

      if (lObj instanceof CObjTouchElevator)
      {
        if (((CObjTouchElevator)lObj).GetId() == _id)
        {
          return (CObjTouchElevator)lObj;
        }
      }
    }

    return null;
  }

  protected CActions createActions(String _fileId)
  {
    CActions _actions = null;

    try
    {
      //System.out.println("reading actions for: " + _fileId);

      InputStream lIs = getClass().getResourceAsStream(CUtility.getInstance().getFileName(_fileId));

      lIs.skip(CUtility.getInstance().GetLocation(_fileId));

      // read the actions count
      int lActionsCount = lIs.read();

      // create the actions vector
      _actions = new CActions(lActionsCount);

      // read and create the actions
      for(int i = 0; i < lActionsCount; i++)
      {
        //_actions[i] = new CAction();

        _actions.SetUpdateMax(i, CUtility.BuildShort(lIs.read(), lIs.read()));
        //_actions[i].mAnimFrameStart  = (byte)lIs.read();
        //_actions[i].mAnimFrameEnd    = (byte)lIs.read();
        _actions.SetAnimFrameStart(i, (byte)lIs.read());
        _actions.SetAnimFrameEnd  (i, (byte)lIs.read());

        int lXAlign      = lIs.read();
        if (lXAlign == 0)
        {
          _actions.SetAlign(i, Graphics.LEFT);
        }
        else
        {
          _actions.SetAlign(i, Graphics.RIGHT);
        }

        int lYAlign      = lIs.read();
        if (lYAlign == 0)
        {
          _actions.SetAlign(i, _actions.GetAlign(i) | Graphics.TOP);
        }
        else
        {
          _actions.SetAlign(i, _actions.GetAlign(i) | Graphics.BOTTOM);
        }

        //System.out.println("i: " + _actions[i].GetAlign());

        int lEffect      = lIs.read();
        if (lEffect == 1)
        {
          _actions.SetEffect(i, 0x20);//DirectGraphics.FLIP_HORIZONTAL);
        }
        else
        {
          _actions.SetEffect(i, 0);
        }

        int lInactive    = lIs.read();

        if (lInactive == 0)
        {
          _actions.SetInactive(i, true);
        }
        else
        {
          _actions.SetInactive(i, false);
        }

        _actions.SetRepeatMax(i, (byte)lIs.read());

        //
        // these two should also be read from disk?
        // anyway, just default them since these are the most used values
        //
        _actions.SetAutoUpdate(i, true);
        _actions.SetFrameDelta(i, 1);

        _actions.SetSpeedIndexStart(i, (short)CUtility.BuildShort(lIs.read(), lIs.read()));
        _actions.SetSpeedIndexEnd(i, (short)CUtility.BuildShort(lIs.read(), lIs.read()));

        //System.out.println("read: " + v + ", get: " + _actions[i].GetRepeatMax());
        //System.out.println("index: " + _actions.GetSpeedIndexStart(i) + "..." + _actions.GetSpeedIndexEnd(i));

        // read the speeds
        //int lSpeedsCount = lIs.read();

        //for(int j = 0; j < lSpeedsCount; j++)
        //{
          //_actions.SetSpeedX(i, (byte)lIs.read());//lIs.read());
          //_actions.SetSpeedY(i, (byte)lIs.read());//lIs.read());

          //if (x != 0 || y != 0)
          //{
          //  System.out.println("i: " + i + "...speed: " + _actions[i].GetSpeedX() + "," +
          //                     _actions[i].GetSpeedY());
          //}
          //System.out.println("i: " + i + "...speed: " + x + "," + y);
          //_actions[i].mSpeedX = (byte)lIs.read();
          //_actions[i].mSpeedY = (byte)lIs.read();
        //}

      }

      // read the speeds list
      int lSpeedsCount = CUtility.BuildShort(lIs.read(), lIs.read());
      _actions.setSpeedsCount(lSpeedsCount);

      for(int i = 0; i < lSpeedsCount; i++)
      {
        _actions.SetSpeedX(i, (byte)lIs.read());//lIs.read());
        _actions.SetSpeedY(i, (byte)lIs.read());//lIs.read());
        //System.out.println("speed: (" + _actions.GetSpeedX(i) + "," + _actions.GetSpeedY(i) + ")");
      }

      lIs.close();
    }
    catch(Exception e)
    {
    }

    return _actions;
  }

  //
  // TILE MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //
  final static byte UP = 1;
  final static byte DOWN = 2;
  final static byte LEFT = 4;
  final static byte RIGHT = 8;
  final static byte LADDER = 16;
  final static byte HEAD_LADDER = 32;

  public final static int WIDTH  = 16;
  public final static int HEIGHT = 16;

  //private CTile mTiles[];

  private static short mTileData[];
  private static byte mTileCollision[];

  public final int GetTile(int _index)
  {
    if (_index < 0)// || _index >= mTiles.length)
    {
      //System.out.println("invalid index: " + _index);

      //return null;
      return -1;
    }

    //return (CTile)mTiles.elementAt(_index);
    //return mTiles[_index];
    return mTileCollision[_index];
  }

  public boolean Collision(int _index, byte _flag)
  {
    if ((mTileCollision[_index] & _flag) != 0)
    {
      return true;
    }

    return false;
  }

  public boolean NotCollidable(int _index)
  {
    return (mTileCollision[_index] == 0);
  }

  public void PaintTile(DirectGraphics dg, int _index, int _x, int _y, int _effect)
  {
    // if the flag is true, it seems that we have a great speed boot in emulator...
    // it's non-sense though...it should be slower
    dg.drawPixels(mTileData, true, _index << 8, 16, _x, _y, 16, 16, _effect, DirectGraphics.TYPE_USHORT_4444_ARGB);
  }

  //
  // tileset with dat4 extension (a binary file containing significant tiles
  // pallete and data in 4bit format)
  //
  private final void ReadTileSet()
  {
    try
    {
      //InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName());
      //lIs.skip(CDataFile.getInstance().GetLocation("TID4"));
      InputStream lIs = getClass().getResourceAsStream("/tid4.dat16");

      // read the tileset definition file into an internal structure
      byte lTilesetDataCount = (byte) lIs.read();
      byte lTilesetData[][]  = new byte[lTilesetDataCount][2];

      for(int i = 0; i < lTilesetDataCount; i++)
      {
        lTilesetData[i][0] = (byte)lIs.read();
        lTilesetData[i][1] = (byte)lIs.read();
        //lTilesetData[i][2] = (byte)lIs.read();
      }

      // read the total number of tiles
      byte lAllTilesCount = (byte)lIs.read();

      //mTiles = new CTile[lAllTilesCount];
      mTileCollision = new byte[lAllTilesCount];
      mTileData = new short[lAllTilesCount * WIDTH * HEIGHT];

      byte lColorCount = (byte)lIs.read();

      // read the pallete
      short lPallete[] = new short[lColorCount];

      // read the pallete from file
      for(int i = 0; i < lColorCount; i++)
      {
        short lR = (short) lIs.read();
        short lG = (short) lIs.read();
        short lB = (short) lIs.read();

        // mauve is the background (transparent) color
        if (lR == 255 && lG == 0 && lB == 255)
        {
          lPallete[i] = 0;
        }
        else
        {
          lR = (short)(lR >> 4);
          lG = (short)(lG >> 4);
          lB = (short)(lB >> 4);

          lPallete[i] = (short)((((((15 << 4) | lR) << 4) | lG) << 4) | lB);
        }
      }

      byte lReadPixels[] = new byte[16 * 16 / 2];
      short lFinalPixels[] = new short[16 * 16];

      // read the data and create the tiles
      for(int i = 0; i < lAllTilesCount; i++)
      {
        //System.out.println("tile " + i);

        lIs.read(lReadPixels, 0, 16 * 16 / 2);

        // uncomment this for storing tiles as short
        int lTileDataStart = i * WIDTH * HEIGHT;

        for(int j = 0; j < 16 * 16/2; j++)
        {
          int lDoubleNibble = lReadPixels[j];

          int lIndex1 = (lDoubleNibble & 15);
          int lIndex2 = (lDoubleNibble & 240) >> 4;

          //lFinalPixels[2 * j] = lPallete[lIndex1];
          //lFinalPixels[2 * j + 1] = lPallete[lIndex2];
          mTileData[lTileDataStart + 2 * j] = lPallete[lIndex1];
          mTileData[lTileDataStart + 2 * j + 1] = lPallete[lIndex2];
        }

        byte lData = 0;

        for(int j = 0; j < lTilesetDataCount; j++)
        {
          if (lTilesetData[j][0] == i)
          {
            lData = lTilesetData[j][1];
            //System.out.println("found for " + i + " data " + lData + " at " + i%13 + "," + i/13);
            break;
          }
        }

        CreateTile(i, lData);
        //CreateTile(i, lFinalPixels, lData);
        //CreateTile(i, lReadPixels, lData);
      }

      lIs.close();

      //System.arraycopy(lPallete, 0, CTile.mPallete, 0, 16);

      lReadPixels  = null;
      lFinalPixels = null;
      lTilesetData = null;
      lPallete = null;

      System.gc();
    }
    catch(Exception e)
    {
      //e.printStackTrace();
    }
  }

  private final void CreateTile(int _index, int _data)
  {
    // init collisions flags
    byte lCollision = 0;
    int lIsLadder = 0, lIsHeadLadder = 0;

    if ((_data & 1) == 1)
      lCollision |= UP;
    if ((_data & 2) == 2)
      lCollision |= DOWN;
    if ((_data & 4) == 4)
      lCollision |= LEFT;
    if ((_data & 8) == 8)
      lCollision |= RIGHT;
    if ((_data & 16) == 16)
       lCollision |= LADDER;

     // should not exist anymore - head ladders are only computer in engine,
     // and are no more exported by the eddie
     //if ((_data & 32) == 32)
     //   lCollision |= HEAD_LADDER;

    //if ((_data & 16) == 16)
    //  lIsLadder = 1;
    //if ((_data & 32) == 32)
    //  lIsHeadLadder = 1;

    mTileCollision[_index] = lCollision;//, lIsLadder, lIsHeadLadder);
    //mTiles[_index] = new engine.CTile(_img, _index, lCollision, lIsLadder, lIsHeadLadder);
  }

  //
  // SPRITE MANAGER (SHOULD HAVE BEEN A SEPARATE CLASS)
  //
}