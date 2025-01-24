package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

public class CActions
{
  public int mUpdateMax[];
  public int mAnimFrame[];
  public int mComp1[]; // contains speedx, speedy (full list); the list may be indexed
                       // using mSpeedIndexStart/mSpeedIndexEnd (mComp3)

  public int mComp2[]; // mEffect, mAlign, mRepeatMax, mFrameDelta
  public int mComp3[]; // mSpeedIndexStart, mSpeedIndexEnd

  private int mFlags[];

  private int mActionsCount, mSpeedsCount;

  CActions(int _count)
  {
    //
    // init the actions count
    //
    mActionsCount = _count;

    mUpdateMax = new int[mActionsCount];
    mAnimFrame = new int[mActionsCount];
    //mComp1     = new int[mActionsCount];
    mComp2     = new int[mActionsCount];
    mComp3     = new int[mActionsCount];
    mFlags     = new int[mActionsCount];

    //SetEffect(0);
    //SetAlign(0);

    //SetAutoUpdate(true);
    //SetInactive(false);

    //SetFrameDelta(1);
    //SetRepeatMax(0);
    //SetSpeedX(0);
    //SetSpeedY(0);
  }

  public void setSpeedsCount(int _count)
  {
    mComp1 = null;
    mComp1 = new int[_count];
    mSpeedsCount = _count;
  }

  void SetUpdateMax(int _index, int _v)
  {
    mUpdateMax[_index] = _v;
  }

  int GetUpdateMax(int _index)
  {
    return mUpdateMax[_index];
  }

  int GetAnimFrameStart(int _index)
  {
    return (mAnimFrame[_index] >> 8);
  }

  void SetAnimFrameStart(int _index, int _v)
  {
    mAnimFrame[_index] = (mAnimFrame[_index] & 0xFFFF00FF) | (_v << 8);
  }

  int GetAnimFrameEnd(int _index)
  {
    return (mAnimFrame[_index] & 0x000000FF);
  }

  void SetAnimFrameEnd(int _index, int _v)
  {
    mAnimFrame[_index] = (_v & 0xFF) | (mAnimFrame[_index] & 0xFF00);
  }

  int GetSpeedX(int _index)
  {
    return (byte)((mComp1[_index] & 0xFF00) >> 8);
  }

  void SetSpeedX(int _index, int _v)
  {
    //int shifted = (_v << 8);
    //int reshifted = (shifted & 0xFF00) >> 8;

    mComp1[_index] = (mComp1[_index] & 0xFFFF00FF) | (_v << 8);

    //System.out.println("v:" + _v + "...shifted: " + shifted + "...reshift: " + ((byte)reshifted));
  }

  int GetSpeedY(int _index)
  {
    return (byte)(mComp1[_index] & 0xFF);
  }

  void SetSpeedY(int _index, int _v)
  {
    mComp1[_index] = (_v & 0xFF) | (mComp1[_index] & 0xFF00);
    //System.out.println("comp: " + GetSpeedX() + "," + GetSpeedY() + "...");
  }

  boolean GetInactive(int _index)
  {
    return ((mFlags[_index] & 1) == 1);
  }

  boolean GetAutoUpdate(int _index)
  {
    return ((mFlags[_index] & 2) == 2);
  }

  void SetInactive(int _index, boolean _v)
  {
    mFlags[_index] = (mFlags[_index] & 2) | (_v == true ? 1:0);
  }

  void SetAutoUpdate(int _index, boolean _v)
  {
    mFlags[_index] = (mFlags[_index] & 1) | ((_v == true ? 1:0) << 1);
  }

  int GetEffect(int _index)
  {
    return (mComp2[_index] & 0x000000FF) << 8;
  }

  void SetEffect(int _index, int _v)
  {
    mComp2[_index] = (mComp2[_index] & 0xFFFFFF00) | _v;
  }

  int GetAlign(int _index)
  {
    return (mComp2[_index] & 0x0000FF00) >> 8;
  }

  void SetAlign(int _index, int _v)
  {
    mComp2[_index] = (mComp2[_index] & 0xFFFF00FF) | (_v << 8);
  }

  void SetFrameDelta(int _index, int _v)
  {
    mComp2[_index] = (mComp2[_index] & 0xFF00FFFF) | (_v << 16);
    //mFrameDelta = _v;
  }

  void SetRepeatMax(int _index, int _v)
  {
    mComp2[_index] = (mComp2[_index] & 0x00FFFFFF) | (_v << 24);
    //mRepeatMax = _v;
  }

  int GetFrameDelta(int _index)
  {
    return (byte)((mComp2[_index] & 0x00FF0000) >> 16);
    //return mFrameDelta;
  }

  int GetRepeatMax(int _index)
  {
    return ((mComp2[_index] & 0xFF000000) >> 24);
    //return mRepeatMax;
  }

  void SetSpeedIndexStart(int _index, int _v)
  {
    mComp3[_index] = (mComp3[_index] & 0x0000FFFF) | ((_v & 0xFFFF) << 16);
  }

  int GetSpeedIndexStart(int _index)
  {
    return ((mComp3[_index] & 0xFFFF0000) >> 16);
  }

  void SetSpeedIndexEnd(int _index, int _v)
  {
    mComp3[_index] = (mComp3[_index] & 0xFFFF0000) | (_v & 0x0000FFFF);
  }

  int GetSpeedIndexEnd(int _index)
  {
    return (mComp3[_index] & 0x0000FFFF);
  }

  void SetSpeedXFrames(int _action, int _v)
  {
    int lStart = GetSpeedIndexStart(_action);
    int lEnd   = GetSpeedIndexEnd(_action);

    for(int i = lStart; i <= lEnd; i++)
    {
      SetSpeedX(i, _v);
    }
  }

  void SetSpeedYFrames(int _action, int _v)
  {
    int lStart = GetSpeedIndexStart(_action);
    int lEnd   = GetSpeedIndexEnd(_action);

    for(int i = lStart; i <= lEnd; i++)
    {
      SetSpeedY(i, _v);
    }
  }

/*
  void printInfo(int _index)
  {
    System.out.println("id: " + _index +
                       " - update: " + GetUpdateMax(_index)  +
                       //" - speedx: " + GetSpeedX(_index) +
                       //" - speedy: " + GetSpeedY(_index) +
                       " - repeat: " + GetRepeatMax(_index) +
                       " - delta: " + GetFrameDelta(_index) +
                       " - align: " + GetAlign(_index) +
                       " - effect: " + GetEffect(_index) +
                       " - speed start: " + GetSpeedIndexStart(_index) +
                       " - speed end: " + GetSpeedIndexEnd(_index)
                       );
  }
 */
}


/*
public class CAction
{
  public int mUpdateMax;
  public int mAnimFrame;
  public int mComp1; // contains speedx, speedy

  //public int mEffect;
  //public int mAlign;
  public int mComp2; // mEffect, mAlign, mRepeatMax, mFrameDelta

  //public boolean mInactive; // a unit with this field on true has special properties (cannot be hurt, so on)
  //public boolean mAutoUpdate;
  private int mFlags;

  //public int mRepeatMax;
  //public int mFrameDelta;

  CAction()
  {
    mUpdateMax = 0;
    mAnimFrame = 0;

    SetEffect(0);
    SetAlign(0);

    //mAlign = 0;

    SetAutoUpdate(true);
    SetInactive(false);

    //mAutoUpdate = true;
    //mInactive = false;

    SetFrameDelta(1);
    SetRepeatMax(0);
    SetSpeedX(0);
    SetSpeedY(0);
  }

  CAction(int _updateMax, int _frameStart, int _frameEnd, int _speedX, int _speedY, int _effect)
  {
    mUpdateMax = _updateMax;
    mAnimFrame = (((byte)_frameStart) << 8) | ((byte)_frameEnd);
    SetSpeedX(_speedX);
    SetSpeedY(_speedY);
    //mEffect = _effect;
    //mAlign = 0;
    SetEffect(_effect);
    SetAlign(0);
    SetAutoUpdate(true);
    SetInactive(false);
    //mAutoUpdate = true;
    //mInactive = false;
    SetFrameDelta(1);
    SetRepeatMax(0);
  }

  int GetAnimFrameStart()
  {
    return (mAnimFrame >> 8);
  }

  int GetAnimFrameEnd()
  {
    return (mAnimFrame & 0x000000FF);
  }

  int GetSpeedX()
  {
    return (byte)((mComp1 & 0xFF00) >> 8);
  }

  void SetSpeedX(int _v)
  {
    //int shifted = (_v << 8);
    //int reshifted = (shifted & 0xFF00) >> 8;

    mComp1 = (mComp1 & 0xFFFF00FF) | (_v << 8);

    //System.out.println("v:" + _v + "...shifted: " + shifted + "...reshift: " + ((byte)reshifted));
  }

  int GetSpeedY()
  {
    return (byte)(mComp1 & 0xFF);
  }

  void SetSpeedY(int _v)
  {
    mComp1 = (_v & 0xFF) | (mComp1 & 0xFF00);
    //System.out.println("comp: " + GetSpeedX() + "," + GetSpeedY() + "...");
  }

  boolean GetInactive()
  {
    return ((mFlags & 1) == 1);
  }

  boolean GetAutoUpdate()
  {
    return ((mFlags & 2) == 2);
  }

  void SetInactive(boolean _v)
  {
    mFlags = (mFlags & 2) | (_v == true ? 1:0);
  }

  void SetAutoUpdate(boolean _v)
  {
    mFlags = (mFlags & 1) | ((_v == true ? 1:0) << 1);
  }

  int GetEffect()
  {
    return (mComp2 & 0x000000FF) << 8;
  }

  void SetEffect(int _v)
  {
    mComp2 = (mComp2 & 0xFFFFFF00) | _v;
  }

  int GetAlign()
  {
    return (mComp2 & 0x0000FF00) >> 8;
  }

  void SetAlign(int _v)
  {
    mComp2 = (mComp2 & 0xFFFF00FF) | (_v << 8);
  }

  void SetFrameDelta(int _v)
  {
    mComp2 = (mComp2 & 0xFF00FFFF) | (_v << 16);
    //mFrameDelta = _v;
  }

  void SetRepeatMax(int _v)
  {
    mComp2 = (mComp2 & 0x00FFFFFF) | (_v << 24);
    //mRepeatMax = _v;
  }

  int GetFrameDelta()
  {
    return (byte)((mComp2 & 0x00FF0000) >> 16);
    //return mFrameDelta;
  }

  int GetRepeatMax()
  {
    return ((mComp2 & 0xFF000000) >> 24);
    //return mRepeatMax;
  }

  void printInfo(int _action)
  {
    System.out.println("id: " + _action +
                       " - update: " + mUpdateMax  +
                       " - speedx: " + GetSpeedX() +
                       " - speedy: " + GetSpeedY() +
                       " - repeat: " + GetRepeatMax() +
                       " - delta: " + GetFrameDelta() +
                       " - align: " + GetAlign() +
                       " - effect: " + GetEffect()
                       );
  }
}
*/