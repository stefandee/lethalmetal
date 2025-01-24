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

/**
 * engine.CTileManager
 *
 * It constructs engine.CTile objects given a tileset image and a tileset definition
 * (currently tileset.txt, output of the Eddie)
 *
 * For the moment, the filenames are hardcoded (it's less likely this game
 * will use more than one tile set).
 *
 */

import com.nokia.mid.ui.*;
import java.io.*;

public class CTileManager
{
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

  public CTileManager()
  {
    ReadTileSet();
  }

  //public final CTile GetTile(int _index)
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
    //if (_index < 0 || _index > mTileCollision.length)
    //{
    //  return false;
    //}

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

  public void Paint(DirectGraphics dg, int _index, int _x, int _y, int _effect)
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
      e.printStackTrace();
    }
  }

  //
  // tileset with a binary definition file and a png
  //
  /*
  private final void ReadTileSet()
  {
    try
    {
      mTiles = new CTile[91];

      //InputStream lIs = getClass().getResourceAsStream("/data/tinf.dat");
      InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName());
      lIs.skip(CDataFile.getInstance().GetLocation("TINF"));

      // read the tileset definition file into an internal structure
      byte lTilesetDataCount = (byte) lIs.read();
      short lTilesetData[][];

      lTilesetData = new short[lTilesetDataCount][3];

      for(int i = 0; i < lTilesetDataCount; i++)
      {
        lTilesetData[i][0] = BuildShort(lIs.read(), lIs.read());
        lTilesetData[i][1] = BuildShort(lIs.read(), lIs.read());
        lTilesetData[i][2] = (byte)lIs.read();
      }

      lIs.close();
      System.gc();

      Image lImgTileSet;

      //System.out.println("free: " + Runtime.getRuntime().freeMemory());
      lImgTileSet = Image.createImage("/data/tileset.png");

      //System.out.println("free: " + Runtime.getRuntime().freeMemory());

      for (int x = 0; x < lImgTileSet.getWidth() / 16; x++)
      {
        for (int y = 0; y < lImgTileSet.getHeight() / 16; y++)
        {
          byte lData = 0;

          for(int i = 0; i < lTilesetDataCount; i++)
          {
            if (lTilesetData[i][0] == x * 16 && lTilesetData[i][1] == y * 16)
            {
              lData = (byte)lTilesetData[i][2];
            }
          }

          CreateTile(lImgTileSet, x * 16, y * 16, lData);
          //System.out.println("free: " + Runtime.getRuntime().freeMemory());
          //System.gc();
        }
      }

      // out
      lImgTileSet = null;

      System.gc();
      //System.out.println("free: " + Runtime.getRuntime().freeMemory());
    }
    catch(Exception e)
    {
    }
  }
  */

  /*
  private short BuildShort(int lsb, int msb)
  {
    return (short)(lsb + (msb << 8));
  }
  */

  //
  // tileset with a text definition file and a png
  //
  /*
  private void ReadTileSet()
  {
    try
    {
      //System.out.println("Reading Tile Set start");
      InputStream lIs = getClass().getResourceAsStream("/data/tileset.txt");

      //System.out.println("Reading png");
      System.gc();
      Image lImgTileSet = Image.createImage("/data/tileset.png");

      //System.out.println("Reading Tile Set init Tiles vector");
      //mTiles = new engine.CTile[64];
      //mTiles = new Vector(32);
      mTiles = new CTile[91];

      String lLine = ReadLine(lIs);

      while(lLine != "")
      {
        //System.out.println("line: " + lLine);
        ParseLine(lLine, lImgTileSet);
        lLine = ReadLine(lIs);
      }

      lIs.close();

      System.gc();
    }
    catch(Exception e)
    {
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      e.printStackTrace();
    }
  }

  private String ReadLine(InputStream _is)
  {
    StringBuffer lLine = new StringBuffer();

    try
    {
      int lTemp = _is.read();

      if (lTemp == -1)
      {
        return "";
      }

      char lChar = (char)lTemp;

      while(lChar != '\n' && lChar != '\r')
      {
        lLine.append(lChar);
        lChar = (char)_is.read();
      }

      // skip the next character, which should be \n
      _is.read();
    }
    catch(Exception e)
    {
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      e.printStackTrace();
      return lLine.toString();
    }

    return lLine.toString();
  }

  private boolean ParseLine(String _line, Image _img)
  {
    // split this line into name x y w h col_up col_down col_left col_right
    // the separator is \t
    int lPrevTab = 0, lNextTab = 0, lCount = 0;
    String[] lValues = new String[11];

    // append a \t at the end of the string line
    //System.out.println(_line);
    _line = _line.concat("\t");

    lNextTab = _line.indexOf("\t", lPrevTab);

    while(lNextTab != -1 && lCount < 11)
    {
      lValues[lCount] = _line.substring(lPrevTab, lNextTab);
      lPrevTab = lNextTab + 1;
      lNextTab = _line.indexOf("\t", lPrevTab);
      lCount++;
    }

    // create a engine.CTile object out of the data parsed
    int lX, lY, lWidth, lHeight, lCUp = 0, lCDown = 0, lCLeft = 0, lCRight = 0, lIsLadder = 0, lIsHeadLadder = 0;

    try
    {
      lX = Integer.parseInt(lValues[1]);
      lY = Integer.parseInt(lValues[2]);
      lWidth = Integer.parseInt(lValues[3]);
      lHeight = Integer.parseInt(lValues[4]);
      lCUp = Integer.parseInt(lValues[5]);
      lCDown = Integer.parseInt(lValues[6]);
      lCLeft = Integer.parseInt(lValues[7]);
      lCRight = Integer.parseInt(lValues[8]);
      lIsLadder = Integer.parseInt(lValues[9]);
      lIsHeadLadder = Integer.parseInt(lValues[10]);
    }
    catch(Exception e)
    {
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      e.printStackTrace();
      //System.err.println(e.getMessage());
      return false;
    }

    CreateTile(_img, lValues[0], lX, lY, lWidth, lHeight, lCUp, lCDown, lCLeft, lCRight, lIsLadder, lIsHeadLadder);

    return true;
  }

  private final void CreateTile(Image _img, String _name, int _x, int _y, int _w, int _h, int _cu, int _cd, int _cl, int _cr, int _isLadder, int _isHeadLadder)
  {
    //try
    //{
      Image lTileImage = DirectUtils.createImage(_w, _h,
          0x00000000);

      DirectGraphics dg = DirectUtils.getDirectGraphics(lTileImage.getGraphics());
      dg.drawImage(_img, -_x, -_y, Graphics.LEFT | Graphics.TOP, 0);

      // init collisions flags
      byte lCollision = 0;

      if (_cu == 1)
        lCollision |= CTile.UP;
      if (_cd == 1)
        lCollision |= CTile.DOWN;
      if (_cl == 1)
        lCollision |= CTile.LEFT;
      if (_cr == 1)
        lCollision |= CTile.RIGHT;

      mTiles[mTileCount] = new engine.CTile(lTileImage, lCollision, _isLadder, _isHeadLadder);
      //mTiles.addElement(new CTile(lTileImage, _name, lCollision, _isLadder, _isHeadLadder));
      mTileCount++;
    //}
    //catch(Exception e)
    //{
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      //e.printStackTrace();
      //System.err.println("CreateTile:" + e.getMessage());
    //}
  }
  */

 /*
 private final void CreateTile(Image _img, int _x, int _y, int _data)
  {
    try
    {
      Image lTileImage = DirectUtils.createImage(16, 16, 0x00000000);

      DirectGraphics dg = DirectUtils.getDirectGraphics(lTileImage.getGraphics());
      dg.drawImage(_img, -_x, -_y, Graphics.LEFT | Graphics.TOP, 0);

      // init collisions flags
      byte lCollision = 0;
      int lIsLadder = 0, lIsHeadLadder = 0;

      if ((_data & 1) == 1)
        lCollision |= CTile.UP;
      if ((_data & 2) == 2)
        lCollision |= CTile.DOWN;
      if ((_data & 4) == 4)
        lCollision |= CTile.LEFT;
      if ((_data & 8) == 8)
        lCollision |= CTile.RIGHT;
      if ((_data & 16) == 16)
        lIsLadder = 1;
      if ((_data & 32) == 32)
        lIsHeadLadder = 1;

      mTiles[mTileCount] = new engine.CTile(lTileImage, lCollision, lIsLadder, lIsHeadLadder);
      //mTiles.addElement(new CTile(lTileImage, _name, lCollision, _isLadder, _isHeadLadder));
      mTileCount++;
    }
    catch(Exception e)
    {
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      //e.printStackTrace();
      //System.err.println("CreateTile:" + e.getMessage());
    }
  }
  */

 //private final void CreateTile(int _index, short[] _img, int _data)
 //private final void CreateTile(int _index, byte[] _img, int _data)
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
   {
     //System.out.println(_index + "..." + _data);
     lCollision |= LADDER;
   }

    // should not exist anymore - head ladders are only computer in engine,
    // and are no more exported by the eddie
    //if ((_data & 32) == 32)
    //{
    //  lCollision |= HEAD_LADDER;
    //}

   //if ((_data & 16) == 16)
   //  lIsLadder = 1;
   //if ((_data & 32) == 32)
   //  lIsHeadLadder = 1;

   mTileCollision[_index] = lCollision;//, lIsLadder, lIsHeadLadder);
   //mTiles[_index] = new engine.CTile(_img, _index, lCollision, lIsLadder, lIsHeadLadder);
 }

}