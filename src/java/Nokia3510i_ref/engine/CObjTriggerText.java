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
import ui.*;

public class CObjTriggerText extends CObjBase
{
  String mText;
  //boolean mShowText;

  public CObjTriggerText(int _x, int _y, String _text)
  {
    super(null, null, _x, _y);

    mText = _text;
    //mShowText = false;

    mCanCollideWithObj = true;
    mCanCollideWithTile = false;

    // compute the bounding box
    mBB.mLeft = mRealX;
    mBB.mRight = mRealX + 32;
    mBB.mTop = mRealY;
    mBB.mBottom = mRealY + 32;
  }

  public void ManageObjCollision(CObjBase _obj)
  {
    //if (_obj == null)
    //{
    //  return;
    //}

    if (!(_obj instanceof CObjPlayer))
    {
      return;
    }

    if (!BBCollision(_obj.mBB))
    {
      //if (_obj instanceof CObjPlayer)
      //{
      //  mShowText = false;
      //}
      return;
    }

    //if (_obj instanceof CObjPlayer)
    //{
      // notify the level to end
      new TriggersObserver().notifyTextMessage(mText);
      //mShowText = true;
      mTerminated = true;
    //}
  }

  public void Paint(Graphics g, int _x, int _y)
  {
    // bounding box
    //g.setColor(0xFFFFFF);
    //g.drawRect(mBB.mLeft - _x, mBB.mTop - _y, mBB.mRight - mBB.mLeft, mBB.mBottom - mBB.mTop);
    /*
    if (mShowText)
    {
      g.setColor(0xFFFFFF);
      g.drawString(mText, 48, 65, Graphics.HCENTER | Graphics.BOTTOM);
    }
    */
  }

  public void Update()
  {
  }

  public int GetWidth()
  {
    return 32;
  }

  public int GetHeight()
  {
    return 32;
  }
}