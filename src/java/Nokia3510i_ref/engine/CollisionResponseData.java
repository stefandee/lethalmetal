package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CollisionResponseData
{
  int mCollisionFlags;
  public int mIntData[];
  //int mBoolData[];

  public CollisionResponseData(int _flags)
  {
    mCollisionFlags = _flags;
    mIntData        = new int[2];
    //mBoolData       = new int[10];
  }

  public void SetFlags(int _flags)
  {
    mCollisionFlags = _flags;
  }

  public void AddFlags(int _flags)
  {
    mCollisionFlags |= _flags;
  }

  public int GetFlags()
  {
    return mCollisionFlags;
  }

  public int GetIntData(int _index)
  {
    return mIntData[_index];
  }

  public int GetBoolData(int _index)
  {
    return 0;
    //return mIntData[_index];
  }
}