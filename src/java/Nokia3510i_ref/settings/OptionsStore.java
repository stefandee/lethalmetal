package settings;

import settings.Options;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan
 * Date: Jul 1, 2003
 * Time: 10:46:47 PM
 * To change this template use Options | File Templates.
 */

public class OptionsStore {
  private static OptionsStore instance = null;
  private RecordStore store;
  private final static int xId = 1;
  private final static int yId = 2;
  private final static int levelNoID = 3;
  private final static int isNewLevelID = 4;
  private final static int isSoundID = 5;

  private String mGameOptionsName;//, mMenuOptionsName;

  private OptionsStore() {
    mGameOptionsName = this.getClass().getName() + "game";
    //mMenuOptionsName = this.getClass().getName() + "menu";
  }

  public static OptionsStore getInstance() {
    if (instance == null) {
      instance = new OptionsStore();
//            instance.saveOptions(new Options(0, 0, (byte) 1, true));
    }
    return instance;
  }

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

      int x = Integer.parseInt(new String(store.getRecord(OptionsStore.xId)));
      int y = Integer.parseInt(new String(store.getRecord(OptionsStore.yId)));
      byte levelNo = Byte.parseByte(new String(store.getRecord(OptionsStore.
          levelNoID)));
      byte isNewLevel = Byte.parseByte(new String(store.getRecord(OptionsStore.
          isNewLevelID)));
      byte sound = Byte.parseByte(new String(store.getRecord(OptionsStore.
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

  /*
  public void saveMenuOptions(boolean _sound)
  {
    try
    {
      if (RecordStore.listRecordStores() != null)
      {
        RecordStore.deleteRecordStore(mMenuOptionsName);
      }

      store = RecordStore.openRecordStore(mMenuOptionsName, true);

      String x = String.valueOf(_sound == true);

      System.out.println("saving " + x);

//      store.addRecord(x.getBytes(), 0, 1);
      store.setRecord(isSoundID, x.getBytes(), 0, 1);

      // and do something
    }
    catch (RecordStoreException e)
    {
    //  e.printStackTrace();
    }
    finally
    {
      try
      {
        store.closeRecordStore();
      }
      catch (RecordStoreException e)
      {
        //e.printStackTrace();
      }
    }
  }

  public boolean loadMenuOptions()
  {
    boolean lSoundOption = false;

    try
    {
      // if the phone was just started and there are no data saved in memory of the phone
      //System.out.println("OptionsStore: start loading . . .");
      if (RecordStore.listRecordStores() == null)
      {
        saveMenuOptions(false);
        return false;
      }

      store = RecordStore.openRecordStore(mMenuOptionsName, true);

      lSoundOption = Byte.parseByte(new String(store.getRecord(OptionsStore.isSoundID))) == 1;
    }
    catch (RecordStoreException e)
    {
      e.printStackTrace();
    } catch (NumberFormatException e)
    {
      //e.printStackTrace();
    }

    try
    {
      store.closeRecordStore();
    }
    catch (RecordStoreException e)
    {
      //e.printStackTrace();
    }
    //System.out.println("Options are:\n\n" + options + "OptionsStore: end loading . . .\n");
    return lSoundOption;
  }
  */
}
