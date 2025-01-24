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
 * engine.CXSprite
 *
 * A class that holds an xtended sprite - a sprite that has no more
 * horizontal and vertical cells, but anumber of cells defined in another
 * file (*.sde - sprite definition); along the cells, there is also defined
 * in the file a hot spot (which mai be used to draw for weapon muzzle/slashing
 * damage)
 */

import javax.microedition.lcdui.*;
import com.nokia.mid.ui.*;
import java.io.*;

/*
class CXSpriteCell
{
  //short mWidth, mHeight;
  //short mHotX, mHotY;

  int mDim;
  int mHot;
  int mOffset;

  //byte[] mImage;

  public CXSpriteCell(short _width, short _height, short _x, short _y)
  {
    //mImage = _image;
    //mLeft = _left;
    //mTop  = _top;
    //mWidth = _width;
    //mHeight = _height;
    //mHotX = _x;
    //mHotY = _y;
    mDim = (_width << 16) | _height;
    mHot = (_x << 16) | _y;
  }

  public int GetWidth()
  {
    return (mDim >> 16);
  }

  public int GetHeight()
  {
    return (mDim & 0x0000FFFF);
  }

  public int GetHotX()
  {
    return (mHot >> 16);
  }

  public int GetHotY()
  {
    return (mHot & 0x0000FFFF);
  }
}
*/

public class CXSprite
{
  //private Vector mCells;
  private int mColorCount;
  private short mPallete[];
  private int mMaxWidth, mMaxHeight;
  short mTempPixels[];
  int mLastCell = -1;
  boolean mBigImage = false;
  byte[] mPixels;

  int[] mDim;
  int[] mHot;
  int[] mOffset;
  int mCellsCount;

  public CXSprite (String _spriteFile)
  {
    // load the image data and definition from one file
    //try
    //{
      if (!Init(_spriteFile))
      {
        return;
      }

      //mIsValid = true;
      //mMemoryConsumed = 0;
    //}
    //catch(Exception e)
    //{
      //mIsValid = false;
      //System.gc();
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      //e.printStackTrace();
    //}
  }

  public CXSprite (String _spriteFile, boolean _big)
  {
    // load the image data and definition from one file
    //try
    //{
      mBigImage = _big;

      if (!Init(_spriteFile))
      {
        //mIsValid = false;
        return;
      }

      //mIsValid = true;
      //mMemoryConsumed = 0;
    //}
    //catch(Exception e)
    //{
      //mIsValid = false;
      //System.gc();
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      //e.printStackTrace();
    //}
  }

 //
 // 4 bit sprites Init
 //
 private final boolean Init(String _spriteFile)
 {
   try
   {
     InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName(_spriteFile));

     lIs.skip(CDataFile.getInstance().GetLocation(_spriteFile));

     // read the number of cells from file
     mCellsCount = lIs.read();

     //System.out.println(mCellsCount + " cells in " + _spriteFile);

     // create the cells "repository"
     mHot    = new int[mCellsCount];
     mDim    = new int[mCellsCount];
     mOffset = new int[mCellsCount];

     // read the pallete entries count from file
     mColorCount = lIs.read();

     //System.out.println(mColorCount + " color entries.");

     mPallete = new short[mColorCount];

     // read the pallete from file
     for(int i = 0; i < mColorCount; i++)
     {
       short lR = (short) lIs.read();
       short lG = (short) lIs.read();
       short lB = (short) lIs.read();

       // mauve is the background (transparent) color
       if (lR == 255 && lG == 0 && lB == 255)
       {
         mPallete[i] = 0;
       }
       else
       {
         lR = (short)(lR >> 4);
         lG = (short)(lG >> 4);
         lB = (short)(lB >> 4);

         mPallete[i] = (short)((((((15 << 4) | lR) << 4) | lG) << 4) | lB);
         //mPallete[i] = (short)((lB << 8) | (lG << 4) | lR);

         //System.out.print(lR + "," + lG + "," + lB);
         //System.out.println("=" + mPallete[i]);
       }
     }

     //int lMemoryConsumed = 0;
     int lOffset = 0;

     mPixels = new byte[CDataFile.getInstance().GetSize(_spriteFile) - (1 + 1 + mColorCount * 3 + mCellsCount * 8)];

     //System.out.println(_spriteFile + "...size: " + (CDataFile.getInstance().GetSize(_spriteFile) - (1 + 1 + mColorCount * 3 + lCellsNo * 8)));

     for(int i = 0; i < mCellsCount; i++)
     {
       // read the sprite definition from file
       int lWidth = CDataFile.BuildShort(lIs.read(), lIs.read());
       int lHeight = CDataFile.BuildShort(lIs.read(), lIs.read());

       int lX = CDataFile.BuildShort(lIs.read(), lIs.read());
       int lY = CDataFile.BuildShort(lIs.read(), lIs.read());

       //System.out.print("cell "); System.out.print(i); System.out.print(": ");
       //System.out.print(lWidth);System.out.print("x");System.out.println(lHeight);

       // read the sprite data from file
       int lPixelSize = lWidth * lHeight;
       int lBytesToRead = lPixelSize / 2 + lPixelSize % 2;

       lIs.read(mPixels, lOffset, lBytesToRead);


       mDim[i] = (lWidth << 16) | lHeight;
       mHot[i] = (lX << 16) | lY;
       mOffset[i] = lOffset;

       lOffset += lBytesToRead;
       //lMemoryConsumed += lBytesToRead;
       //System.out.print((lRight - lLeft + 1));System.out.print("x");System.out.println((lBottom - lTop + 1));
     }

     lIs.close();
     System.gc();

     //System.out.println(_spriteFile + " memory consumed: " + lMemoryConsumed + ", memory left: " + Runtime.getRuntime().freeMemory());

     ComputeMaxDimensions();

     return true;
   }
   catch(Exception e)
   {
     //e.printStackTrace();
     //System.err.println("Read of " + _spriteFile + " failed.");
     return false;
   }
 }

  private final void ComputeMaxDimensions()
  {
    mMaxWidth = 0;
    mMaxHeight = 0;

    //System.out.println("compute max dimensions.");

    //int lCellsSize = mCells.size();

    for(int i = 0; i < mCellsCount; i++)
    {
      //engine.CXSpriteCell lCell = (engine.CXSpriteCell) (mCells.elementAt(i));
      //System.out.println("dim: (" + GetWidth(i) + "," + GetHeight(i) + ")");

      if (GetWidth(i) > mMaxWidth)
      {
        mMaxWidth = GetWidth(i);
      }

      if (GetHeight(i) > mMaxHeight)
      {
        mMaxHeight = GetHeight(i);
      }
    }

    mTempPixels = null;
    mTempPixels = new short[mMaxWidth * mMaxHeight + 1];

    if (mBigImage)
    {
      //CXSpriteCell lCell = (engine.CXSpriteCell) (mCells.elementAt(0));

      int lSize = (mMaxWidth * mMaxHeight) / 2 + (mMaxWidth * mMaxHeight) % 2;
      int lTempPixelsIndex = 0;

      for (int i = 0; i < lSize; i++)
      {
        //byte lDoubleIndex = lCell.mImage[i];
        byte lDoubleIndex = mPixels[i + mOffset[i]];

        int lIndex1 = (lDoubleIndex & 15);
        int lIndex2 = (lDoubleIndex & 240) >> 4;

        mTempPixels[lTempPixelsIndex++] = mPallete[lIndex1];
        mTempPixels[lTempPixelsIndex++] = mPallete[lIndex2];
      }

      //((engine.CXSpriteCell) (mCells.elementAt(0))).mImage = null;
      mPixels = null;

      mLastCell = 0;
    }

    //mCellImage = DirectUtils.createImage(mMaxWidth, mMaxHeight, 0x00000000);
    //System.out.println("Max dim: (" + mMaxWidth + "," + mMaxHeight + ")");
    System.gc();
  }


 //
 // 4 bit sprites paint
 //
 public final void Paint(Graphics g, int _x, int _y, int _cell, int _align, int _effect, int _zoom)
 {
   //try
   //{
     int lW = GetWidth(_cell);
     int lH = GetHeight(_cell);

     // adjust the coordinates based on the align
     if ((_align & Graphics.BOTTOM) != 0)
     {
       _y -= lH;
     }

     if ((_align & Graphics.RIGHT) != 0)
     {
       _x -= lW;
     }

     if ((_align & Graphics.HCENTER) != 0)
     {
       _x -= lW/2;
     }

     if ((_align & Graphics.VCENTER) != 0)
     {
       _y -= lH/2;
     }

     if (mLastCell != _cell)
     {
       // TODO: check the _align and update the coordinates accordingly
       int lSize = (lW * lH) / 2 + (lW * lH) % 2;
       int lTempPixelsIndex = 0;

       int lOffset = mOffset[_cell];

       for (int i = lOffset; i < lSize + lOffset; i++)
       {
         byte lDoubleIndex = mPixels[i];

         int lIndex1 = (lDoubleIndex & 15);
         int lIndex2 = (lDoubleIndex & 240) >> 4;

         mTempPixels[lTempPixelsIndex++] = mPallete[lIndex1];
         mTempPixels[lTempPixelsIndex++] = mPallete[lIndex2];
       }

       mLastCell = _cell;
     }

     if (_zoom == 1)
     {
       DirectUtils.getDirectGraphics(g).drawPixels(mTempPixels, true, 0, lW, _x,
                                                   _y, lW, lH, _effect,
                                                   DirectGraphics.
                                                   TYPE_USHORT_4444_ARGB);
     }
     else
     {
       short[] lLine = new short[lW * 2];

       int lScreenY = _y;
       int lLineY   = 0;

       for(int y = 0; y < lH; y++)
       {
         for(int x = 0; x < lW - 1; x++)
         {
           lLine[x * 2] = mTempPixels[lLineY + x];
           lLine[x * 2 + 1] = mTempPixels[lLineY + x];
         }

         lLineY += lW;

         DirectUtils.getDirectGraphics(g).drawPixels(lLine, true, 0, lW * 2, _x,
                                                     lScreenY, lW * 2, 1, _effect,
                                                     DirectGraphics.
                                                     TYPE_USHORT_4444_ARGB);
         DirectUtils.getDirectGraphics(g).drawPixels(lLine, true, 0, lW * 2, _x,
         lScreenY + 1, lW * 2, 1, _effect,
         DirectGraphics.
         TYPE_USHORT_4444_ARGB);

         lScreenY += 2;
       }
     }

   //}
   //catch(Exception e)
   //{
     //System.out.println("cell " + _cell + "/" + mCells.size() + " out of range.");
     //return;
   //}
 }

  public final int GetWidth(int _cell)
  {
    return ((mDim[_cell] & 0xFFFF0000) >> 16);
  }

  public final int GetHeight(int _cell)
  {
    return (mDim[_cell] & 0x0000FFFF);
  }

  public final int GetHotX(int _cell, boolean _flipped)
  {
    if (!_flipped)
    {
      return (mHot[_cell] >> 16);
    }
    else
    {
      return (mHot[_cell] >> 16) - GetWidth(_cell);
    }

  }

  public final int GetHotY(int _cell)
  {
    return (mHot[_cell] & 0x0000FFFF);
  }

  public final int CellsCount()
  {
    return mCellsCount;
  }
}