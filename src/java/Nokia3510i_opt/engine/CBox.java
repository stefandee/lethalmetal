package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CBox {
  public int mLeft, mRight, mBottom, mTop;

  public CBox(int _left, int _top, int _right, int _bottom)
  {
    mLeft   = _left;
    mRight  = _right;
    mTop    = _top;
    mBottom = _bottom;
  }
}