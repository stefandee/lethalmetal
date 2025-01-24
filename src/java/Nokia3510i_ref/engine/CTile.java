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

import com.nokia.mid.ui.*;

/**
 * engine.CTile
 *
 * A class that holds information about a map tile.
 *
 * It holds an image and collisions by the four directions and about the
 * special collision types (ladder and head ladder)
 *
 * The width and height of a tile is fixed (and specified by WIDTH and HEIGHT
 * constants)
 *
 * A tile may draw itself onto a Graphics context.
 *
 * This class is no more used because it holds a reference to a short data array
 * and a tile manager will also has to hold references to CTile objects, resulting
 * in losing 16 + 16 bytes of data (for 104 tiles, this is a loss 3328 bytes)
 *
 * Should memory requirements become not so strict, reactivating this class
 * would be a design and maintenace smart-choice ;)
 *
 */

public class CTile
{
  final static byte UP = 1;
  final static byte DOWN = 2;
  final static byte LEFT = 4;
  final static byte RIGHT = 8;
  final static byte LADDER = 16;
  final static byte HEAD_LADDER = 32;

  public final static int WIDTH  = 16;
  public final static int HEIGHT = 16;

  private short[] mImage;
  private byte mCollision;

 public CTile(short[] _image, byte _collision)
 {
   mImage = new short[256];

   System.arraycopy(_image, 0, mImage, 0, 256);

   mCollision = _collision;
 }

  public boolean Collision(byte _flag)
  {
    if ((mCollision & _flag) != 0)
    {
      return true;
    }

    return false;
  }

  public boolean NotCollidable()
  {
    return (mCollision == 0);
  }

  public void Paint(DirectGraphics dg, int _x, int _y, int _effect)
  {
    dg.drawPixels(mImage, false, 0, 16, _x, _y, 16, 16, _effect, DirectGraphics.TYPE_USHORT_4444_ARGB);
  }
}