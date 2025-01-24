package engine;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan
 * Date: Jul 1, 2003
 * Time: 11:01:38 PM
 * To change this template use settings.Options | File Templates.
 */

public class Options {
    private int x;
    private int y;
    byte levelNo;
    byte isNewLevel;
    byte sound;

    public Options() {}

    public Options(int x, int y, byte levelNo, boolean newLevel, byte sound) {
        this.x = x;
        this.y = y;
        this.levelNo = levelNo;
        this.setNewLevel(newLevel);
        this.sound = sound;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public byte getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(byte levelNo) {
        this.levelNo = levelNo;
    }

    public boolean isNewLevel() {
        if (isNewLevel == 0 )
            return false;
        return true;
    }

    public byte getNewLevel() {
        return isNewLevel;
    }

    public void setNewLevel(boolean newLevel) {
        if (newLevel == true)
            isNewLevel = 1;
        else
            isNewLevel = 0;
    }

    public void setNewLevel(byte newLevel) {
        isNewLevel = newLevel;
    }

    public void setSound(boolean _v)
    {
      if (_v == true)
          sound = 1;
      else
          sound = 0;
    }

    public void setSound(byte _v)
    {
      sound = _v;
    }

    public byte getSound()
    {
      return sound;
    }

    /*
    public String toString() {
        String result = new String();
        result = "x = " + String.valueOf(x) + "\n";
        result += "y = " + String.valueOf(y) + "\n";
        result += "levelNo = " + String.valueOf(levelNo) + "\n";
        result += "isNewLevel = " + String.valueOf(isNewLevel) + "\n";
        return result;
    }
      */
}
