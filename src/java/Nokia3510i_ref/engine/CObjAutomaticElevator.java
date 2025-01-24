package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CObjAutomaticElevator extends CObjElevator
{
  public CObjAutomaticElevator(CLevel _level, CXSprite _sprite, int _x, int _y, int _dir, int _dist1, int _dist2, int _wait, byte _speed)
  {
    super(_level, _sprite, _x, _y, _dir, _dist1, _dist2, _wait, _speed);
  }
}