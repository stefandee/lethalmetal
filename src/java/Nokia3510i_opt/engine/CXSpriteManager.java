package engine;

/**
 * <p>Title: Lethal Metal</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

import java.util.Vector;

//class SpriteManagerEntry
//{
//  public String mFileName;
//  public CXSprite mSprite;
//}

public class CXSpriteManager
{
  //private Vector mEntries;
  private Vector mFileNames, mSprites;

  public CXSpriteManager()
  {
    //try
    //{
    //mEntries = new Vector(8);
    mFileNames = new Vector(27);
    mSprites = new Vector(27);
    //}
    //catch(Exception e)
    //{
      //System.out.println("error allocating sprite entries");
    //}
  }

  /*
  public CXSprite AddSprite(String _fileName, String _defName)
  {
    int lSize = mEntries.size();

    for(int i = 0; i < lSize; i++)
    {
      if (((engine.SpriteManagerEntry)mEntries.elementAt(i)).mFileName == _fileName)
      {
        return ((engine.SpriteManagerEntry)mEntries.elementAt(i)).mSprite;
      }
    }

    //try
    //{
      engine.SpriteManagerEntry lEntry = new engine.SpriteManagerEntry();

      //lEntry.mSprite = new engine.CXSprite(_fileName, _defName);
      lEntry.mFileName = _fileName;

      mEntries.addElement(lEntry);

      return lEntry.mSprite;
    //}
    //catch(Exception e)
    //{
      //System.out.println("Error creating sprite");
    //}

    //return null;
  }
  */

  public CXSprite AddSprite(String _fileName)
  {
    System.gc();

    //System.out.println("loading " + _fileName);

    //int lSize = mEntries.size();
    int lSize = mFileNames.size();

    //if (_fileName == "PLAY")
    //{
     //System.out.println("free: " + Runtime.getRuntime().freeMemory());
    //}

    for(int i = 0; i < lSize; i++)
    {
      //if (_fileName == "/data/player.spr")
      //{
      //System.out.print(i + "...");
      //}

      if (((String)mFileNames.elementAt(i)).equals(_fileName))
      {
        //if (_fileName == "PLAY")
        //{
        //  System.out.println("player found, exiting.");
        //}

        //System.out.println("");
        return ((CXSprite)mSprites.elementAt(i));
      }
    }

    //System.out.println("");

    //System.out.println("sprite not found, loading " + _fileName);

    //try
    //{
      //engine.SpriteManagerEntry lEntry = new engine.SpriteManagerEntry();

      //lEntry.mSprite = new CXSprite(_fileName);
      //lEntry.mFileName = _fileName;

      //if (_fileName == "BULL")
      //{
      //  System.out.println("bullet found: " + lEntry.mSprite.GetWidth(0) + ", " + lEntry.mSprite.GetHeight(0));
      //}

      //mEntries.addElement(lEntry);

      CXSprite lSprite = new CXSprite(_fileName);
      mSprites.addElement(lSprite);
      mFileNames.addElement(_fileName);

      return lSprite;
    //}
    //catch(Exception e)
    //{
      //System.out.println("Error creating sprite");
    //}

    //return null;
  }

/*
  public void PrintEntries()
  {
    //int lSize = mEntries.size();
    int lSize = mFileNames.size();

    for(int i = 0; i < lSize; i++)
    {
      //System.out.println(((engine.SpriteManagerEntry)mEntries.elementAt(i)).mFileName);
      System.out.println(((String)mFileNames.elementAt(i)));
    }
  }
 */

  public void Clean()
  {
    //int lSize = mEntries.size();
    int lSize = mFileNames.size();

    //for(int i = 0; i < lSize; i++)
    //{
      //((engine.SpriteManagerEntry)mEntries.elementAt(i)).mSprite = null;
    //}

    //mEntries.removeAllElements();
    mFileNames.removeAllElements();
    mSprites.removeAllElements();

    System.gc();
  }
}