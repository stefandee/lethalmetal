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

class CEntry
{
  //byte[] mId;
  //int mId;
  String mId;
  int mSize;
  int mLocation;
  byte mFile;
}

public class CDataFile
{
  private static CDataFile instance = null;
  private CEntry[] mEntries;
  private int mEntriesCount = 0;

  /*
  public static final StringBuffer[] Files =
  {
    new StringBuffer("SHOO"),
    new StringBuffer("PLAY")
  };
      */

  public CDataFile()
  {
    // allocate the entries
    mEntries = new CEntry[35];

    InitFileTable((byte)1);
    InitFileTable((byte)2);
    InitFileTable((byte)3);
    InitFileTable((byte)4);
  }

  static public CDataFile getInstance()
  {
    if (instance == null)
    {
      instance = new CDataFile();
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
}