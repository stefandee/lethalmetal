package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: Environment manager</p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.util.Vector;

public class CEnvManager
{
  public int mWidth, mHeight;
  //public byte mMap[][];
  public byte mMap[];
  //private byte mCollisionMap[][];
  private byte mCollisionMap[];
  public Vector mGrabPoints;
  public Vector mCloseGrabPoints;
  //CPoint[] mGrabPoints;
  //CPoint[] mCloseGrabPoints;

  private CTileManager mTileManager;

  public CEnvManager(CTileManager _tileManager, int _w, int _h)
  {
    mWidth  = _w;
    mHeight = _h;

    mTileManager = _tileManager;

    //mMap = new byte[mWidth][mHeight];
    mMap = new byte[mWidth * mHeight];
  }

  public void SetWidth(int _v)
  {
    mWidth = _v;
  }

  public void SetHeight(int _v)
  {
    mHeight = _v;
  }

  public int GetWidth()
  {
    return mWidth;
  }

  public int GetHeight()
  {
    return mHeight;
  }

  public void SetMap(int _x, int _y, byte _data)
  {
    //mMap[_x][_y] = _data;
    mMap[_x * mHeight + _y] = _data;
  }

  public byte GetMap(int _x, int _y)
  {
    //if (_x < 0 || _y < 0 || _x >= mWidth || _y >= mHeight)
    //{
    //  return -1;
    //}

    //return mMap[_x][_y];
    return mMap[_x * mHeight + _y];
  }

  public int GroundDist(int x, int y)
  {
    if (y == mHeight - 1)
    {
      return 10;
    }

    if ((mCollisionMap[(x+1) * mHeight + y+1] & CTileManager.UP) != 0)
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

    for(int x = 0; x < mWidth; x++)
    {
      for(int y = 0; y < mHeight; y++)
      {
        int lIndexX2 = (x+2) * mHeight + y;
        int lIndexX1 = (x+1) * mHeight + y;
        int lIndexX = lIndexX1 - mHeight;

        if ((mCollisionMap[lIndexX1] & CTileManager.LEFT) != 0 && (mCollisionMap[lIndexX1] & CTileManager.UP) != 0
            && (mCollisionMap[lIndexX] == 0 || (mCollisionMap[lIndexX] & CTileManager.HEAD_LADDER) != 0)
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

        if ((mCollisionMap[lIndexX1] & CTileManager.RIGHT) != 0 && (mCollisionMap[lIndexX1] & CTileManager.UP) != 0
            && (mCollisionMap[lIndexX2] == 0 || (mCollisionMap[lIndexX2] & CTileManager.HEAD_LADDER) != 0)
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
    mCollisionMap = new byte[(mWidth + 2) * mHeight];

    //try
    //{
      for (int x = 0; x < mWidth; x++)
      {
        for (int y = 0; y < mHeight; y++)
        {
          int lIndex = (x+1) * mHeight + y;

          mCollisionMap[lIndex] = 0;

          if (x == 0)
          {
            mCollisionMap[y] |= CTileManager.RIGHT;
          }

          if (x == mWidth - 1)
          {
            mCollisionMap[(mWidth + 1) * mHeight + y] |= CTileManager.LEFT;
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
          if (mTileManager.Collision(lTileIndex, CTileManager.LADDER))
          {
            int lUpTileIndex = GetTileAt(x, y - 1);
            int lLeftTileIndex = GetTileAt(x - 1, y);
            int lRightTileIndex = GetTileAt(x + 1, y);

            if (lUpTileIndex != -1 && lLeftTileIndex != -1 && lRightTileIndex != -1)
            {
              if (!(mTileManager.Collision(lUpTileIndex, CTileManager.LADDER)))
              {
                mCollisionMap[lIndex - 1] |= CTileManager.HEAD_LADDER;

                //System.out.println(x + "," + y + "..." + lUpTileIndex + "..." + lTileIndex);
                //System.out.println("found head ladder at " + (x + 1) + "," + (y - 1) + " => " + mCollisionMap[x + 1][y - 1]);
              }

              if (!mTileManager.Collision(lUpTileIndex, CTileManager.LADDER) &&
                   (mTileManager.Collision(lLeftTileIndex, CTileManager.UP) ||
                   mTileManager.Collision(lRightTileIndex, CTileManager.UP)))
              {
                mCollisionMap[lIndex] |= CTileManager.UP;
                mCollisionMap[lIndex] |= CTileManager.LADDER;
                continue;
              }
            }

            mCollisionMap[lIndex] |= CTileManager.LADDER;
          }

          if (mTileManager.Collision(lTileIndex, CTileManager.LEFT))
          {
            for (int _y = y; _y < y + CTileManager.HEIGHT/16; _y++)
            {
              int lColTileIndex = GetTileAt(x - 1, _y);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(x+1) * mHeight + _y] |= CTileManager.LEFT;
                continue;
              }

              if (!mTileManager.Collision(lColTileIndex, CTileManager.RIGHT))
              {
                mCollisionMap[(x+1) * mHeight + _y] |= CTileManager.LEFT;
              }
            }
          }

          if (mTileManager.Collision(lTileIndex, CTileManager.UP)) {
            for (int _x = x; _x < x + CTileManager.WIDTH/16; _x++) {
              int lColTileIndex = GetTileAt(_x, y - 1);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(_x+1) * mHeight + y] |= CTileManager.UP;
                continue;
              }

              if (!mTileManager.Collision(lColTileIndex, CTileManager.DOWN)) {
                mCollisionMap[(_x+1) * mHeight + y] |= CTileManager.UP;
              }
            }
          }

          if (mTileManager.Collision(lTileIndex, CTileManager.DOWN)) {
            for (int _x = x; _x < x + CTileManager.WIDTH/16; _x++) {
              int lColTileIndex = GetTileAt(_x, y + 1);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(_x+1) * mHeight + y] |= CTileManager.DOWN;
                continue;
              }

              if (!mTileManager.Collision(lColTileIndex, CTileManager.UP)) {
                mCollisionMap[(_x+1) * mHeight + y] |= CTileManager.DOWN;
              }
            }
          }

          if (mTileManager.Collision(lTileIndex, CTileManager.RIGHT)) {
            for (int _y = y; _y < y + CTileManager.HEIGHT/16; _y++) {
              int lColTileIndex = GetTileAt(x + 1, _y);

              if (lColTileIndex == -1)
              {
                mCollisionMap[(x+1) * mHeight + _y] |= CTileManager.RIGHT;
                continue;
              }

              if (!mTileManager.Collision(lColTileIndex, CTileManager.LEFT))
              {
                mCollisionMap[(x+1) * mHeight + _y] |= CTileManager.RIGHT;
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

  //private CTile GetTileAt(int _x, int _y)
  private int GetTileAt(int _x, int _y)
  {
    if (_x < 0 || _y < 0 || _x >= mWidth || _y >= mHeight)
    {
      return -1;
    }

    //byte lIndex = mMap[_x * mHeight + _y];

    return mMap[_x * mHeight + _y];

    //if (lIndex != -1)
    //{
    //  return mTileManager.GetTile(lIndex);
    //}

    //return -1;
  }

  public int GetCollisionMap(int _x, int _y)
  {
    //return mCollisionMap[_x + 1][_y]; // tile from -1 to width + 1, to include collision with level margins
    return mCollisionMap[(_x + 1) * mHeight + _y];
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

  public void Clean()
  {
    mMap = null;
    mCollisionMap = null;
    mGrabPoints.removeAllElements();
    mCloseGrabPoints.removeAllElements();

    mGrabPoints = null;
    mCloseGrabPoints = null;

    System.gc();
  }
}