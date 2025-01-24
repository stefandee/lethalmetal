package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import javax.microedition.lcdui.*;

public class CFont
{
  public final static byte F_ALIGN_LEFT   = 0;
  public final static byte F_ALIGN_RIGHT  = 1;
  public final static byte F_ALIGN_MIDDLE = 2;

  CXSprite mSprite;

  /*final static*/
  byte mIndex[] = {
      //0, 0, 0, 0, 0, 0, 0, 0, // 0 - 31
      //0, 0, 0, 0, 0, 0, 0, 0,
      //0, 0, 0, 0, 0, 0, 0, 0,
      //0, 0, 0, 0, 0, 0, 0, 0,
      80, 74, 65, 79, 0, 0, 77, 65, // 32 - 63
      72, 73, 78, 67, 64, 68, 0, 66,
      52, 53, 54, 55, 56, 57, 58, 59,
      60, 61, 62, 63, 0, 69, 0, 75,
      76, 0, 1, 2, 3, 4, 5, 6, // 64 - 95
      7, 8, 9, 10, 11, 12, 13, 14,
      15, 16, 17, 18, 19, 20, 21, 22,
      23, 24, 25, 70, 0, 71, 0, 0,
      0, 26, 27, 28, 29, 30, 31, 32, // 96 - 127
      33, 34, 35, 36, 37, 38, 39, 40,
      41, 42, 43, 44, 45, 46, 47, 48,
      49, 50, 51, 0, 0, 0, 0, 0,
  };

  public CFont(String _fileName)
  {
    //
    // load the font sprite file
    //
    mSprite = new CXSprite(_fileName);

  }

  public void drawString(Graphics g, int x, int y, byte _align, String string)
  {
    //byte lBytes[] = string.getBytes();

    int lLength = string.length();

    int lX = x;

    switch(_align)
    {
      case F_ALIGN_MIDDLE:
        lX = x - (getWidth(string) >> 1);
        break;

      case F_ALIGN_RIGHT:
        lX = x - getWidth(string);
        break;
    }

    for(int i = 0; i < lLength; i++)
    {
      //System.out.print(mIndex[lBytes[i]] + " - ");
      //int lIndex = mIndex[lBytes[i]];
      int lIndex = mIndex[string.charAt(i) - 32];

      mSprite.Paint(g, lX, y, lIndex, 0, 0, 1);

      // a small offset is necessary
      lX += mSprite.GetWidth(lIndex) - 1;
    }

    //System.out.println();
    //lBytes = null;
  }

  public int getWidth(String _string)
  {
    int lLength = _string.length();

    int lW = 0;

    for(int i = 0; i < lLength; i++)
    {
      //System.out.print(mIndex[lBytes[i]] + " - ");
      //int lIndex = mIndex[lBytes[i]];
      int lIndex = mIndex[_string.charAt(i) - 32];

      // a small offset is necessary
      lW += mSprite.GetWidth(lIndex) - 1;
    }

    return lW;
  }
}