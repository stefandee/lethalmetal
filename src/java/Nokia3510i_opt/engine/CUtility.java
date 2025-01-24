package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: Holds informations about the data chunks in a data file</p>
 * <p>Copyright: Copyright Karg (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.io.*;
import com.nokia.mid.sound.Sound;
import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

class CEntry
{
  //byte[] mId;
  //int mId;
  String mId;
  int mSize;
  int mLocation;
  byte mFile;
}

public class CUtility
{
  private static CUtility instance = null;
  private CEntry[] mEntries;
  private int mEntriesCount = 0;

  /*
  public static final StringBuffer[] Files =
  {
    new StringBuffer("SHOO"),
    new StringBuffer("PLAY")
  };
      */
  //
  // CDataFile interface
  //

  public CUtility()
  {
    // allocate file entries
    mEntries = new CEntry[35];

    InitFileTable((byte)1);
    InitFileTable((byte)2);
    InitFileTable((byte)3);
    InitFileTable((byte)4);

    // sounds init
    mSounds = new Vector(6);

    // options store init
    mGameOptionsName = this.getClass().getName() + "game";
  }

  static public CUtility getInstance()
  {
    if (instance == null)
    {
      instance = new CUtility();
    }

    return instance;
  }

  public String getFileName(String _id)
  {
    return "/0" + GetEntry(_id).mFile + ".data";
  }

  private void InitFileTable(byte _fileId)
  {
    try
    {
      // read the informations in the datafile header
      InputStream lIs = getClass().getResourceAsStream("/0" + _fileId + ".data");

      // read the entries number
      short lEntries = BuildShort(lIs.read(), lIs.read());

      // read the datafile header
      for(int i = mEntriesCount; i < mEntriesCount + lEntries; i++)
      {
        mEntries[i] = new CEntry();

        byte[] lId = new byte[4];

        lIs.read(lId);

        mEntries[i].mId       = new String(lId);
        //mEntries[i].mId       = new byte[4];
        //System.arraycopy(lId, 0, mEntries[i].mId, 0, 4);

        mEntries[i].mLocation = BuildInt(lIs.read(), lIs.read(), lIs.read(), lIs.read());
        mEntries[i].mSize     = BuildInt(lIs.read(), lIs.read(), lIs.read(), lIs.read());
        mEntries[i].mFile     = _fileId;

        lId = null;

        //System.out.println("[" + mEntries[i].mId + "]...at " + mEntries[i].mLocation + "...size " + mEntries[i].mSize);
      }

      mEntriesCount += lEntries;

      lIs.close();
      System.gc();
    }
    catch(Exception e)
    {
      //e.printStackTrace();
    }
  }

  public int GetLocation(String _id)
  {
    // assume we find the id
    return GetEntry(_id).mLocation;
  }

  public int GetSize(String _id)
  {
    // assume we find the id
    return GetEntry(_id).mSize;
  }

  private CEntry GetEntry(String _id)
  {
    int lLength = mEntries.length;

    for(int i = 0; i < lLength; i++)
    {
      if (_id.compareTo(mEntries[i].mId) == 0)
      {
        return mEntries[i];
      }
    }

    //System.out.println(_id + " not found in datafile");
    return null;
  }

  public static short BuildShort(int lsb, int msb)
  {
    return (short)(lsb + (msb << 8));
  }

  public static int BuildInt(int _1, int _2, int _3, int _4 )
  {
    return _1 + (_2 * 256) + (_3 + _4 * 256) * 256;
  }

  //
  // Feedback Interface
  //
  Vector mSounds;
  boolean mSoundState = true;

  public void addSound(String _audioFile)
  {
    // load the sounds from disk
    try
    {
      //InputStream lIs = getClass().getResourceAsStream(CUtility.getInstance().getFileName(_audioFile));
      InputStream lIs = getClass().getResourceAsStream(_audioFile);

      //lIs.skip(CUtility.getInstance().GetLocation(_audioFile));

      //byte[] lData = new byte[CUtility.getInstance().GetSize(_audioFile)];
      byte[] lData = new byte[256];

      lIs.read(lData, 0, 256);

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
    Options lOptions = loadGameOptions();

    lOptions.setSound(mSoundState);

    saveGameOptions(lOptions);
  }

  //
  // OptionsStore Interface
  //
  private RecordStore store;
  private final static int xId = 1;
  private final static int yId = 2;
  private final static int levelNoID = 3;
  private final static int isNewLevelID = 4;
  private final static int isSoundID = 5;

  private String mGameOptionsName;//, mMenuOptionsName;

  public void saveGameOptions(Options options) {
    //System.out.println("OptionsStore: start saving . . .");
    try {
      //String[] lRecordNames = RecordStore.listRecordStores();

      if (RecordStore.listRecordStores() != null) {
        RecordStore.deleteRecordStore(mGameOptionsName);
      }

      store = RecordStore.openRecordStore(mGameOptionsName, true);
      String x = String.valueOf(options.getX());
      String y = String.valueOf(options.getY());
      String levelNo = String.valueOf(options.getLevelNo());
      String isNewLevel = String.valueOf(options.getNewLevel());
      String sound = String.valueOf(options.getSound());

      store.addRecord(x.getBytes(), 0, x.getBytes().length);
//      store.setRecord(OptionsStore.xId, x.getBytes(), 0, x.getBytes().length);
      store.addRecord(y.getBytes(), 0, y.getBytes().length);
//      store.setRecord(OptionsStore.yId, y.getBytes(), 0, y.getBytes().length);
      store.addRecord(levelNo.getBytes(), 0, levelNo.getBytes().length);
//      store.setRecord(OptionsStore.levelNoID, levelNo.getBytes(), 0, levelNo.getBytes().length);
      store.addRecord(isNewLevel.getBytes(), 0, isNewLevel.getBytes().length);
//      store.setRecord(OptionsStore.isNewLevelID, isNewLevel.getBytes(), 0, isNewLevel.getBytes().length);
      store.addRecord(sound.getBytes(), 0, sound.getBytes().length);
    }
    catch (RecordStoreException e) {
      e.printStackTrace();
    }
    finally {
      try {
        store.closeRecordStore();
      }
      catch (RecordStoreException e) {
        e.printStackTrace();
      }
    }
    //System.out.println("Options are:\n\n" + options + "OptionsStore: end saving . . .\n");
  }

  public Options loadGameOptions() {
    Options options = null;
    try {
      // if the phone was just started and there are no data saved in memory of the phone
      //System.out.println("OptionsStore: start loading . . .");
      if (RecordStore.listRecordStores() == null) {
        // initialize some data like at New menu
        options = new Options(0, 0, (byte) 1, true, (byte)0);
        saveGameOptions(options);
        //System.out.println("Options are:\n\n" + options + "OptionsStore: end loading (not from rms) . . .\n");
        return options;
      }
      store = RecordStore.openRecordStore(mGameOptionsName, true);
      options = new Options();
//      DEBUG
//      System.out.println("<-- Enumerate start");
//      RecordEnumeration en = store.enumerateRecords(null, null, false);
//      while (en.hasNextElement()) {
//        int id = en.nextRecordId();
//        System.out.println("id = " + id);
//        System.out.println("data = " + en.nextRecord() + "\n");
//      }
//      System.out.println("Enumerate end -->");

      int x = Integer.parseInt(new String(store.getRecord(CUtility.xId)));
      int y = Integer.parseInt(new String(store.getRecord(CUtility.yId)));
      byte levelNo = Byte.parseByte(new String(store.getRecord(CUtility.
          levelNoID)));
      byte isNewLevel = Byte.parseByte(new String(store.getRecord(CUtility.
          isNewLevelID)));
      byte sound = Byte.parseByte(new String(store.getRecord(CUtility.
          isSoundID)));

      options.setX(x);
      options.setY(y);
      options.setLevelNo(levelNo);
      options.setNewLevel(isNewLevel);
      options.setSound(sound);
    }
    catch (RecordStoreException e) {
      e.printStackTrace();
    }
    catch (NumberFormatException e) {
      e.printStackTrace();
    }
    try {
      store.closeRecordStore();
    }
    catch (RecordStoreException e) {
      e.printStackTrace();
    }

    //System.out.println("Options are:\n\n" + options + "OptionsStore: end loading . . .\n");
    return options;
  }

  //
  // String Manager Interface
  //
  boolean StrMgr_Load()
  {
	  return false;
  }

  String StrMgr_GetString(int stringId)
  {
	  return "";
  }
}