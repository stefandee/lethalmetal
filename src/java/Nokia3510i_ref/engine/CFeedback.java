package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.io.*;
import settings.*;
import com.nokia.mid.sound.Sound;
import java.util.Vector;

public class CFeedback
{
  private static CFeedback mInstance = null;
  Vector mSounds;
  boolean mSoundState = true;

  protected CFeedback()
  {
    mSounds = new Vector(6);
  }

  static public CFeedback getInstance()
  {
    if (mInstance == null)
    {
      mInstance = new CFeedback();
    }

    return mInstance;
  }

  public void addSound(String _audioFile)
  {
    // load the sounds from disk
    try
    {
      InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName(_audioFile));

      lIs.skip(CDataFile.getInstance().GetLocation(_audioFile));

      byte[] lData = new byte[CDataFile.getInstance().GetSize(_audioFile)];

      lIs.read(lData);

      Sound lSound = new Sound(lData, 1);
      lSound.setGain(255);

      mSounds.addElement(lSound);

      lIs.close();
    }
    catch(Exception e)
    {
      //System.out.println("error adding sound " + _audioFile);
    }
  }

  public void playSound(int _index)
  {
    // sound is not enabled, exit without play
    if (!mSoundState)
    {
      return;
    }

    Sound lSound = (Sound)(mSounds.elementAt(_index));

    if (lSound.getState() == Sound.SOUND_PLAYING)
    {
      lSound.stop();
    }

    lSound.play(1);
  }

  public void setSoundState(boolean _v)
  {
    mSoundState = _v;
    saveToOptions(_v);
  }

  public boolean getSoundState()
  {
    return mSoundState;
  }

  private void saveToOptions(boolean _v)
  {
    Options lOptions = OptionsStore.getInstance().loadGameOptions();

    lOptions.setSound(mSoundState);

    OptionsStore.getInstance().saveGameOptions(lOptions);
  }
}