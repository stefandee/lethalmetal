package ui;

//import ui.TextForm;
//import ui.MainMenuList;
//import ui.PauseMenuList;
import main.MainMIDlet;
import engine.CLevel;
import engine.CXSprite;
import settings.*;
import engine.CFont;
import engine.CFeedback;
import javax.microedition.lcdui.*;
import com.nokia.mid.ui.*;

public class GameScreen extends com.nokia.mid.ui.FullCanvas implements Runnable
{
  public final static byte GS_GAME            = 0;
  public final static byte GS_PAUSE_MENU      = 1;
  public final static byte GS_MAIN_MENU       = 2;
  public final static byte GS_SPLASH_GAME     = 3;
  public final static byte GS_SPLASH_COMPANY  = 4;
  public final static byte GS_CREDITS         = 5;
  public final static byte GS_LOADING         = 6;
  public final static byte GS_ENDLEVEL        = 7;
  public final static byte GS_ENDGAME_VICTORY = 8;
  public final static byte GS_ENDGAME_DEATH   = 9;
  public final static byte GS_OPTIONS_MENU    = 10;
  public final static byte GS_HELP            = 11;
  public final static byte GS_TEXT            = 12;

  /**Construct the displayable*/
  private static GameScreen mInstance = null;
  private boolean mRunning;
  private CLevel mLevel;
  long mOldTime;
  protected int _keyCurrent = 0, _flush = -1, _keyOff = 0, _key = 0, _keyReleased = 0, _keyPressed = 0;
  protected byte mCurrentScreen = -1, mFrames = 0;
  protected byte mCurrentMainMenuOption = 0, mMaxMainMenuOption = 5,
      mCurrentOptionsOption = 0, mMaxOptionsOption = 1,
      mCurrentPauseOption = 0, mMaxPauseOption = 3;

  //protected boolean mSoundState = false;
  private String[] mText = new String[5];
  private int mTextLinesSize = 0;
  //Vector mText;

  CXSprite mImageSplashCompany, mImageTiles, mImageCursor, mImageGameLogo, mImageTitle;

  private int mScreenWidth, mScreenHeight;

  //engine.CTileManager mTileManager;

  public engine.CFont mFont;

  protected void SetScreen(byte _v)
  {
    mImageSplashCompany = null;
    mImageTitle = null;
    System.gc();

    mFrames = 0;
    _flush = 6;

    int lOldScreen = mCurrentScreen;
    mCurrentScreen = _v;

    //System.out.println("screen set to " + mCurrentScreen);
    //System.out.println("screen set to " + mCurrentScreen + "...free: " + Runtime.getRuntime().freeMemory());

    switch(mCurrentScreen)
    {
      case GS_SPLASH_COMPANY:
        mImageSplashCompany = new CXSprite("DROB", true);
        break;

      case GS_SPLASH_GAME:
        mImageTitle = new CXSprite("DTIT", true);
        break;

      case GS_LOADING:
        //System.out.println("newing a level...");
        //System.gc();
        //mLevel = new CLevel();
        //mLevel.Init(mTileManager);
        break;

      case GS_GAME:
        if (lOldScreen == GS_MAIN_MENU || lOldScreen == GS_ENDLEVEL || lOldScreen == GS_ENDGAME_DEATH)
        {
          //System.gc();
          //System.out.println("game logo: " + Runtime.getRuntime().freeMemory());
          //mImageGameLogo = null;
          //System.gc();
          //System.out.println("after game logo: " + Runtime.getRuntime().freeMemory());
          //mFont = null;
          //mImageTiles = null;
          System.gc();
          initLevel();
        }
        break;

      case GS_MAIN_MENU:
        if (lOldScreen == GS_LOADING)
        {
          mLevel = new CLevel(getWidth(), getHeight());

          //System.out.println("canvas size: " + getWidth() + "," + getHeight());
        }
        break;

      case GS_PAUSE_MENU:
        break;

      case GS_OPTIONS_MENU:
        mCurrentOptionsOption = 0;
        break;

      case GS_CREDITS:
        break;

      case GS_ENDLEVEL:
        break;

      case GS_ENDGAME_VICTORY:
        break;

      case GS_ENDGAME_DEATH:
        break;

      case GS_TEXT:
        //setPause(true);
        //_keyCurrent = 0;
        break;
    }
  }

  public static GameScreen getInstance()
  {
    if (mInstance == null)
    {
      mInstance = new GameScreen();
    }

    return mInstance;
  }

  private GameScreen()
  {
    try
    {
      // WORKAROUND - LOAD THIS AT START WHEN WE HAVE MEMORY SINCE IT
      // NEEDS SOME MANEUVER MEMORY
      //mTileManager = new engine.CTileManager();

      engine.CDataFile.getInstance();

      //mLevel = new CLevel();

      mImageCursor   = new CXSprite("CURS", true);
      mImageTiles    = new CXSprite("TILE");
      mImageGameLogo = new CXSprite("MNIN", true);

      engine.CFeedback.getInstance().addSound("STIT");

      SetScreen(GS_SPLASH_COMPANY);
      setRunning(true);
      //System.out.println("GameScreen start thread");
      jbInit();

      mFont = new engine.CFont("FONT");

      mScreenWidth  = getWidth();
      mScreenHeight = getHeight();
    }
    catch (Exception e)
    {
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      //e.printStackTrace();
    }
  }

  protected void paint(Graphics g)
  {
    _keyPressed  = ~_key & _keyCurrent;
    _keyReleased = _key & ~_keyCurrent;
    _key         = _keyCurrent;

    switch(mCurrentScreen)
    {
      case GS_GAME:
        //try
        //{
        //g.setClip(0, 0, 96, 65);

        //long lTime = System.currentTimeMillis();

        //for(int i = 0; i < 500; i++)
        {
          mLevel.Paint(g);
        }

        //long lDuration = System.currentTimeMillis() - lTime;

        //System.out.println("duration: " + lDuration + ", fps: " + 500000 / lDuration);

        //System.gc();
        //g.setColor(0, 220, 0);
        //g.drawString(String.valueOf(Runtime.getRuntime().freeMemory()), 0, 55, Graphics.TOP | Graphics.LEFT);
        //g.drawString(String.valueOf(mFPS), 0, 55, Graphics.TOP | Graphics.LEFT);
        //g.drawString(String.valueOf(lDuration), 0, 0, Graphics.TOP | Graphics.LEFT);
        //}
        //catch (Exception e)
        //{
        //System.out.println("paint: " + this.getClass().getName());
        //e.printStackTrace();
        //}
        break;

      case GS_SPLASH_COMPANY:
        mImageSplashCompany.Paint(g, 0, 24, 0, 0, 0, 1);
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_SPLASH_GAME:
        PaintBack(g);
        mImageGameLogo.Paint(g, 0, 0, 0, 0, 0, 2);
        mImageTitle.Paint(g, 43, 15, 0, 0, 0, 1);
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_LOADING:
        //System.out.println("loading");
        PaintBack(g);
        mImageGameLogo.Paint(g, 0, 0, 0, 0, 0, 2);
        mFont.drawString(g, 70, 54, CFont.F_ALIGN_LEFT, "loading");
        break;

      case GS_MAIN_MENU:
        PaintBack(g);
        mImageGameLogo.Paint(g, 0, 0, 0, 0, 0, 2);
        //mImageGameLogo.Paint(g, 76, 0, 0, 0, DirectGraphics.FLIP_HORIZONTAL, 1);
        mImageCursor.Paint(g, 46, 8 + 8 * mCurrentMainMenuOption, 0, 0, 0, 1);

        //mFont.drawString(g, 0, 8, ": ; , \" / + - = [ ] ( )");
        //mFont.drawString(g, 0, 16, "! ? @ & * # ");
        //mFont.drawString(g, 0, 24, "0 1 2 3 4 5 6 7 8 9");
        //mFont.drawString(g, 0, 8, "abcdefghijklmn");
        //mFont.drawString(g, 0, 16, "opqrstuvwxyz");

        mFont.drawString(g, 52, 8,  CFont.F_ALIGN_LEFT, "NEW GAME");
        mFont.drawString(g, 52, 16, CFont.F_ALIGN_LEFT, "CONTINUE");
        mFont.drawString(g, 52, 24, CFont.F_ALIGN_LEFT, "OPTIONS");
        mFont.drawString(g, 52, 32, CFont.F_ALIGN_LEFT, "HELP");
        mFont.drawString(g, 52, 40, CFont.F_ALIGN_LEFT, "CREDITS");
        mFont.drawString(g, 52, 48, CFont.F_ALIGN_LEFT, "EXIT");
        break;

      case GS_PAUSE_MENU:
        PaintBack(g);
        mFont.drawString(g, 32, 16, CFont.F_ALIGN_LEFT, "RESUME");
        mFont.drawString(g, 32, 32, CFont.F_ALIGN_LEFT, "MAIN MENU");
        mFont.drawString(g, 32, 40, CFont.F_ALIGN_LEFT, "EXIT");

        mImageCursor.Paint(g, 26, 16 + 8 * mCurrentPauseOption, 0, 0, 0, 1);

        if (CFeedback.getInstance().getSoundState())
        {
          mFont.drawString(g, 32, 24, CFont.F_ALIGN_LEFT, "SOUND: ON");
        }
        else
        {
          mFont.drawString(g, 32, 24, CFont.F_ALIGN_LEFT, "SOUND: OFF");
        }
        break;

      case GS_OPTIONS_MENU:
        PaintBack(g);
        mImageGameLogo.Paint(g, 0, 0, 0, 0, 0, 2);
        mFont.drawString(g, 52, 16, CFont.F_ALIGN_LEFT, "BACK");

        mImageCursor.Paint(g, 46, 8 + 8 * mCurrentOptionsOption, 0, 0, 0, 1);

        if (CFeedback.getInstance().getSoundState())
        {
          mFont.drawString(g, 52, 8, CFont.F_ALIGN_LEFT, "SOUND: ON");
        }
        else
        {
          mFont.drawString(g, 52, 8, CFont.F_ALIGN_LEFT, "SOUND: OFF");
        }

        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_CREDITS:
        PaintBack(g);
        mFont.drawString(g, 48, 0, CFont.F_ALIGN_MIDDLE, "Design:");
        mFont.drawString(g, 48, 8, CFont.F_ALIGN_MIDDLE, "Andrei Fantana");

        mFont.drawString(g, 48, 18, CFont.F_ALIGN_MIDDLE, "Programming:");
        mFont.drawString(g, 48, 26, CFont.F_ALIGN_MIDDLE, "Stefan \"Karg\" Dicu");
        mFont.drawString(g, 48, 34, CFont.F_ALIGN_MIDDLE, "Bogdan Hodorog");

        mFont.drawString(g, 48, 44, CFont.F_ALIGN_MIDDLE, "Artwork:");
        mFont.drawString(g, 48, 52, CFont.F_ALIGN_MIDDLE, "Iulian Agapie");
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_HELP:
        PaintBack(g);
        mFont.drawString(g, 13, 0, CFont.F_ALIGN_LEFT, "4,6");  mFont.drawString(g, 30, 0, CFont.F_ALIGN_LEFT, " - LEFT, RIGHT");
        mFont.drawString(g, 13, 8, CFont.F_ALIGN_LEFT, "8");    mFont.drawString(g, 30, 8, CFont.F_ALIGN_LEFT, " - DOWN, CROUCH");
        mFont.drawString(g, 13, 16, CFont.F_ALIGN_LEFT, "1,3"); mFont.drawString(g, 30, 16, CFont.F_ALIGN_LEFT, " - JUMP");
        mFont.drawString(g, 13, 24, CFont.F_ALIGN_LEFT, "7,9"); mFont.drawString(g, 30, 24, CFont.F_ALIGN_LEFT, " - ROLL");
        mFont.drawString(g, 13, 32, CFont.F_ALIGN_LEFT, "5");   mFont.drawString(g, 30, 32, CFont.F_ALIGN_LEFT, " - FIRE");
        mFont.drawString(g, 13, 40, CFont.F_ALIGN_LEFT, "2");   mFont.drawString(g, 30, 40, CFont.F_ALIGN_LEFT, " - UP, COVER");
        mFont.drawString(g, 13, 48, CFont.F_ALIGN_LEFT, "0");   mFont.drawString(g, 30, 48, CFont.F_ALIGN_LEFT, " - USE");
        mFont.drawString(g, 13, 56, CFont.F_ALIGN_LEFT, "Right");  mFont.drawString(g, 30, 56, CFont.F_ALIGN_LEFT, " - MENU, PAUSE");
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_ENDLEVEL:
        PaintBack(g);
        mFont.drawString(g, 48, 32, CFont.F_ALIGN_MIDDLE, "LEVEL FINISHED!");
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_ENDGAME_VICTORY:
        PaintBack(g);
        mFont.drawString(g, 48, 32, CFont.F_ALIGN_MIDDLE, "YOU BEAT THE GAME!");
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_ENDGAME_DEATH:
        PaintBack(g);
        mFont.drawString(g, 48, 24, CFont.F_ALIGN_MIDDLE, "You died!");
        mFont.drawString(g, 48, 32, CFont.F_ALIGN_MIDDLE, "G A M E  O V E R");
        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;

      case GS_TEXT:
        PaintBack(g);

        int lTextY = 32 - (mTextLinesSize * 8) / 2;

        for(int i = 0; i < mTextLinesSize; i++)
        {
          mFont.drawString(g, 48, lTextY + i * 8, CFont.F_ALIGN_MIDDLE, mText[i]);
        }

        mImageCursor.Paint(g, 88, 58, 0, 0, DirectGraphics.ROTATE_270, 1);
        break;
    }

    if (_keyOff != 0)
    {
      _keyCurrent = 0;
    }

    _keyOff = 0;
  }

  void PaintBack(Graphics g)
  {
    for(int x = 0; x < mScreenWidth / 16 + 1; x++)
    {
      for(int y = 0; y < mScreenHeight / 16 + 1; y++)
      {
        mImageTiles.Paint(g, x * 16, y * 16, (x + y) % 3, 0, 0, 1);
      }
    }
  }

  public void run()
  {
    while (isRunning())
    {
      Thread.yield();

      mOldTime = System.currentTimeMillis();

      if (_flush > 0)
      {
        _flush--;
      }

      switch(mCurrentScreen)
      {
        case GS_GAME:
          mLevel.mPlayer.ComputeAction(_keyCurrent);
          break;

        case GS_SPLASH_COMPANY:
          mFrames++;
          if (mFrames >= 100)
          {
            SetScreen(GS_SPLASH_GAME);
          }
          ComputeMenuAction(_keyCurrent);
          break;

        case GS_SPLASH_GAME:
          mFrames++;
          if (mFrames >= 100)
          {
            SetScreen(GS_LOADING);
          }
          ComputeMenuAction(_keyCurrent);
          break;

        case GS_LOADING:
          //mFrames++;
          //if (mFrames >= 5)
          //{
            SetScreen(GS_MAIN_MENU);
          //}
          break;

        case GS_MAIN_MENU:
        case GS_OPTIONS_MENU:
        case GS_CREDITS:
        case GS_PAUSE_MENU:
        case GS_HELP:
        case GS_ENDGAME_DEATH:
        case GS_ENDGAME_VICTORY:
        case GS_ENDLEVEL:
          ComputeMenuAction(_keyCurrent);
          _keyCurrent = 0;
          break;

        case GS_TEXT:
          mFrames++;
          if (mFrames >= 50)
          {
            setPause(false);
            SetScreen(GS_GAME);
          }
          ComputeMenuAction(_keyCurrent);
          break;
      }

      repaint();
      serviceRepaints();

      while ((System.currentTimeMillis() - mOldTime) < 1000 / 17)
      {
        Thread.yield();
      }

      //System.out.println(_flush);
      //long timeTaken = System.currentTimeMillis() - mOldTime;
    }
  }

  /**Component initialization*/
  private void jbInit() throws Exception
  {
    // set up this Displayable to listen to command events
    //setCommandListener(this);
    // add the Exit command
    //addCommand(new Command("Exit", Command.EXIT, 1));
    new Thread(this).start();
  }

  /**Handle command events*/
  public void commandAction(Command command, Displayable displayable)
  {
    if (command.getCommandType() == Command.EXIT)
    {
      //System.out.println("Exit the application");
    }
  }

  protected void ComputeMenuAction(int _key)
  {
    boolean lUp    = (_key & 4096) != 0 || (_key & 1) != 0;
    boolean lDown  = (_key & 512)  != 0 || (_key & 2) != 0;
    boolean lEnter = (_key & 256)  != 0 || (_key & 8) != 0;

    //if (_key != 0)
    //{
    //  _flush = 6;
    //}

    switch(mCurrentScreen)
    {
      case GS_CREDITS:
      case GS_HELP:
      case GS_ENDGAME_VICTORY:
        // 5
        if (lEnter)
        {
          SetScreen(GS_MAIN_MENU);
        }
        break;

      case GS_ENDGAME_DEATH:
        // 5
        if (lEnter)
        {
          SetScreen(GS_GAME);
        }
        break;

      case GS_TEXT:
        // 5
        if (lEnter && mFrames > 10)
        {
          setPause(false);
          SetScreen(GS_GAME);
        }
        break;

      case GS_SPLASH_COMPANY:
      case GS_SPLASH_GAME:
        // 5
        if (lEnter)
        {
          SetScreen(GS_LOADING);
          //System.out.println("loading set");
        }
        break;

      case GS_ENDLEVEL:
        if (lEnter)
        {
          initLevel();
          SetScreen(GS_GAME);
        }
        break;

      case GS_MAIN_MENU:
        // 2
        if (lUp)
        {
          //System.out.println("computemenuaction menu up");

          mCurrentMainMenuOption--;

          if (mCurrentMainMenuOption < 0)
          {
            mCurrentMainMenuOption = mMaxMainMenuOption;
          }
        }

        // 5
        if (lEnter)
        {
          switch(mCurrentMainMenuOption)
          {
            case 0: // new game - start from the first level
              // get the options
              Options lOptions = OptionsStore.getInstance().loadGameOptions();

              // create a new game from level 1
              OptionsStore.getInstance().saveGameOptions(new Options(0, 0, (byte) 1, true, lOptions.getSound()));

              lOptions = null;
              //System.out.println("new game!");

              SetScreen(GS_GAME);
              break;

            case 1: // continue the game, the level is responsible to read the options stored
              SetScreen(GS_GAME);
              break;

            case 4: // show credits
              SetScreen(GS_CREDITS);
              break;

            case 3: // show help
              SetScreen(GS_HELP);
              break;

            case 2: // show options
              SetScreen(GS_OPTIONS_MENU);
              break;

            case 5: // exit the application
              MainMIDlet.getInstance().notifyDestroyed();
              break;
          }
        }

        // 8
        if (lDown)
        {
          mCurrentMainMenuOption++;

          if (mCurrentMainMenuOption > mMaxMainMenuOption)
          {
            mCurrentMainMenuOption = 0;
          }
        }
        break;

      case GS_OPTIONS_MENU:
        // 2
        if (lUp)
        {
          mCurrentOptionsOption--;

          if (mCurrentOptionsOption < 0)
          {
            mCurrentOptionsOption = mMaxOptionsOption;
          }
        }

        // 5
        if (lEnter)
        {
          switch(mCurrentOptionsOption)
          {
            case 0: // sound on/off
              updateSoundState();
              break;

            case 1: // back to main
              SetScreen(GS_MAIN_MENU);
              break;
          }
        }

        // 8
        if (lDown)
        {
          mCurrentOptionsOption++;

          if (mCurrentOptionsOption > mMaxOptionsOption)
          {
            mCurrentOptionsOption = 0;
          }
        }
        break;

      case GS_PAUSE_MENU:
        // 2
        if (lUp)
        {
          mCurrentPauseOption--;

          if (mCurrentPauseOption < 0)
          {
            mCurrentPauseOption = mMaxPauseOption;
          }
        }

        // 5
        if (lEnter)
        {
          switch(mCurrentPauseOption)
          {
            case 0: // resume
              setPause(false);
              SetScreen(GS_GAME);
              break;

            case 1: // sound on/off
              updateSoundState();
              break;

            case 2: // main menu
              mLevel.Clean();
              SetScreen(GS_MAIN_MENU);
              break;

            case 3: // exit
              MainMIDlet.getInstance().notifyDestroyed();
              break;
          }
        }

        // 8
        if (lDown)
        {
          mCurrentPauseOption++;

          if (mCurrentPauseOption > mMaxPauseOption)
          {
            mCurrentPauseOption = 0;
          }
        }
        break;
    }
  }

  private void updateSoundState()
  {
    CFeedback.getInstance().setSoundState(!CFeedback.getInstance().getSoundState());
  }

  public void setPause(boolean _v)
  {
    if (_v)
    {
      SetScreen(GS_PAUSE_MENU);
      mLevel.setPause(true);
    }
    else
    {
      mLevel.setPause(false);
    }
  }

  public void initLevel()
  {
    //mLevel.Clean();
    mLevel.ReadMap();
  }

  public boolean isRunning()
  {
    return mRunning;
  }

  public void setRunning(boolean running)
  {
    this.mRunning = running;
  }

  public void setText(String _text)
  {
    mTextLinesSize = 0;

    int lIndex = 0;
    int lNextIndex = _text.indexOf("\\n");

    //mText = _text;

    while(lNextIndex >= 0)
    {
      if (lNextIndex == -1)
      {
        break;
      }
      else
      {
        mText[mTextLinesSize++] = _text.substring(lIndex, lNextIndex);
      }

      if (mTextLinesSize > 4)
      {
        break;
      }

      lIndex = lNextIndex + 2;
      lNextIndex = _text.indexOf("\\n", lIndex);
    }

    mText[mTextLinesSize++] = _text.substring(lIndex, _text.length());

    //System.out.println("lines in text: " + mTextLinesSize);
    //for(int i = 0; i < mTextLinesSize; i++)
    //{
    //  System.out.println(mText[i]);
    //}
  }

  protected void keyPressed(int keyCode)
  {
    //System.out.println("key pressed " + getKeyMask(keyCode));

    if (_flush > 0)
     return;

   _keyCurrent	= getKeyMask(keyCode);

    if ((_keyCurrent & 8) != 0 && mCurrentScreen == GS_GAME)
    {
      setPause(true);
      _keyCurrent = 0;
    }
  }

  protected void keyReleased(int keyCode)
  {
    //System.out.println("key released " + getKeyMask(keyCode));

    if ((_key & getKeyMask(keyCode)) != 0)
      _keyCurrent	&= ~getKeyMask(keyCode);
    else
      _keyOff	|= getKeyMask(keyCode);
  }

  protected void keyRepeated(int keyCode)
  {
    //System.out.println("key repeated " + getKeyMask(keyCode));

    if (_flush > 0)
      return;

    _keyCurrent	= getKeyMask(keyCode);
  }

  protected int getKeyMask(int keyCode)
  {
    switch (keyCode)
    {
      case  -1: // UP ARROW
      return 1;

      case  -2: // DOWN ARROW
      return 2;

      case  -6: // LEFT ARROW
      return 4;

      case  -7: // RIGHT ARROW
        //System.out.println("-7");
      return 8;

      //case -10:
      //return 0;

      case  50: // NUM2
        return 4096;

      case  51: // NUM3
        return 16384;

      case  49: // NUM1
        return 8192;

      case  53: // NUM5
        return 256;

      case  56: // NUM8
        return 512;

      case  52: // NUM4
        return 16;

      case  54: // NUM6
        return 32;

      case  55: // NUM7
        return 64;

      case  57: // NUM9
        return 128;

      case  48: // NUM0
        return 1024;

      case  35: // POUND
        return 2048;

      default:
        //System.out.println(keyCode + " ");
        return 0;
    }
  }
}
