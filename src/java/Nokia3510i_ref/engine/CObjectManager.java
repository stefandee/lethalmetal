package engine;

/**
 * <p>Title: Impalator</p>
 * <p>Description: small platform game for Nokia 3510</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Karg
 * @version 1.0
 */

import javax.microedition.lcdui.*;
import java.util.Vector;
import java.io.*;

public class CObjectManager
{
  public Vector mObjects;
  public Vector mBullets;

  CObjPlayer mPlayer;

  public CActions mPlayerActions;
  public CActions mAISlasherActions;
  public CActions mAIShooterActions;

  public CObjectManager()
  {
    //try
    //{
      mObjects = new Vector(3);
      mBullets = new Vector(3);

      mPlayerActions    = createActions("APLA");
      mAIShooterActions = createActions("ASHO");
      mAISlasherActions = createActions("ASLA");

      //for (int i = 0; i < 32; i++) {
      //  mObjects[i] = null;
      //}

      //mObjectCount = 0;
    //}
    //catch(Exception e)
    //{
      //System.out.println("Am prins-o de coada" + this.getClass().getName());
      //e.printStackTrace();
    //}
  }

  public void AddObject(CObjBase _obj)
  {
    //for(int i = 0; i < mObjectCount; i++)
    //{
    //  if (mObjects[i] == null)
    //  {
    //    mObjects[i] = _obj;
    //    return;
    //  }
    //}

    // no protection
    //mObjects[mObjectCount] = _obj;
    //mObjectCount++;
    mObjects.addElement(_obj);
    //mObjects.insertElementAt(_obj, 0);

    if (_obj instanceof CObjPlayer)
    {
      mPlayer = (CObjPlayer)_obj;
    }

    if (_obj instanceof CObjBullet)
    {
      mBullets.addElement(_obj);
    }
  }

  public void InsertObject(CObjBase _obj)
  {
    mObjects.insertElementAt(_obj, 0);

    if (_obj instanceof CObjPlayer)
    {
      mPlayer = (CObjPlayer)_obj;
    }

    if (_obj instanceof CObjBullet)
    {
      mBullets.addElement(_obj);
    }
  }

  CObjPlayer GetPlayer()
  {
    return mPlayer;
  }

  public int EnemiesCount()
  {
    int lCount = 0;
    int lSize = mObjects.size();

    for(int i = 0; i < lSize; i++)
    {
      CObjBase lObj = (CObjBase) (mObjects.elementAt(i));

      if (lObj instanceof CObjAIShooter || lObj instanceof CObjAISlasher)
          //lObj instanceof CObjRobot)
      {
        lCount++;
      }
    }

    //System.out.println("Enemy count: " + lCount);
    return lCount;
  }

  /*
  public void SortList()
  {
    Vector lTemp = new Vector(mObjects.size());

    // move elevators and animations at the end of the list (should be painted over the player)
    int lSize = mObjects.size();

    for(int i = 0; i < lSize; i++)
    {
      CObjBase lObj = (CObjBase)(mObjects.elementAt(i));

      if (lObj instanceof CObjElevator || lObj instanceof CObjAnimation)
      {
        lTemp.insertElementAt(lObj, 0);
      }
      else
      {
        lTemp.addElement(lObj);
      }
    }

    mObjects = lTemp;
    lTemp = null;
  }
  */

  public int GetCount()
  {
    //return mObjectCount;
    return mObjects.size();
  }

  public CObjTouchElevator GetElevator(int _id)
  {
    int lSize = mObjects.size();

    for(int i = 0; i < lSize; i++)
    {
      CObjBase lObj = (CObjBase)(mObjects.elementAt(i));

      if (lObj instanceof CObjTouchElevator)
      {
        if (((CObjTouchElevator)lObj).GetId() == _id)
        {
          return (CObjTouchElevator)lObj;
        }
      }
    }

    return null;
  }

  public void Clean()
  {
    mObjects.removeAllElements();
    mPlayer = null;
  }

  protected CActions createActions(String _fileId)
  {
    CActions _actions = null;

    try
    {
      //System.out.println("reading actions for: " + _fileId);

      InputStream lIs = getClass().getResourceAsStream(CDataFile.getInstance().getFileName(_fileId));

      lIs.skip(CDataFile.getInstance().GetLocation(_fileId));

      // read the actions count
      int lActionsCount = lIs.read();

      // create the actions vector
      _actions = new CActions(lActionsCount);

      // read and create the actions
      for(int i = 0; i < lActionsCount; i++)
      {
        //_actions[i] = new CAction();

        _actions.SetUpdateMax(i, CDataFile.BuildShort(lIs.read(), lIs.read()));
        //_actions[i].mAnimFrameStart  = (byte)lIs.read();
        //_actions[i].mAnimFrameEnd    = (byte)lIs.read();
        _actions.SetAnimFrameStart(i, (byte)lIs.read());
        _actions.SetAnimFrameEnd  (i, (byte)lIs.read());

        int lXAlign      = lIs.read();
        if (lXAlign == 0)
        {
          _actions.SetAlign(i, Graphics.LEFT);
        }
        else
        {
          _actions.SetAlign(i, Graphics.RIGHT);
        }

        int lYAlign      = lIs.read();
        if (lYAlign == 0)
        {
          _actions.SetAlign(i, _actions.GetAlign(i) | Graphics.TOP);
        }
        else
        {
          _actions.SetAlign(i, _actions.GetAlign(i) | Graphics.BOTTOM);
        }

        //System.out.println("i: " + _actions[i].GetAlign());

        int lEffect      = lIs.read();
        if (lEffect == 1)
        {
          _actions.SetEffect(i, 0x20);//DirectGraphics.FLIP_HORIZONTAL);
        }
        else
        {
          _actions.SetEffect(i, 0);
        }

        int lInactive    = lIs.read();

        if (lInactive == 0)
        {
          _actions.SetInactive(i, true);
        }
        else
        {
          _actions.SetInactive(i, false);
        }

        _actions.SetRepeatMax(i, (byte)lIs.read());

        //
        // these two should also be read from disk?
        // anyway, just default them since these are the most used values
        //
        _actions.SetAutoUpdate(i, true);
        _actions.SetFrameDelta(i, 1);

        _actions.SetSpeedIndexStart(i, (short)CDataFile.BuildShort(lIs.read(), lIs.read()));
        _actions.SetSpeedIndexEnd(i, (short)CDataFile.BuildShort(lIs.read(), lIs.read()));

        //System.out.println("read: " + v + ", get: " + _actions[i].GetRepeatMax());
        //System.out.println("index: " + _actions.GetSpeedIndexStart(i) + "..." + _actions.GetSpeedIndexEnd(i));

        // read the speeds
        //int lSpeedsCount = lIs.read();

        //for(int j = 0; j < lSpeedsCount; j++)
        //{
          //_actions.SetSpeedX(i, (byte)lIs.read());//lIs.read());
          //_actions.SetSpeedY(i, (byte)lIs.read());//lIs.read());

          //if (x != 0 || y != 0)
          //{
          //  System.out.println("i: " + i + "...speed: " + _actions[i].GetSpeedX() + "," +
          //                     _actions[i].GetSpeedY());
          //}
          //System.out.println("i: " + i + "...speed: " + x + "," + y);
          //_actions[i].mSpeedX = (byte)lIs.read();
          //_actions[i].mSpeedY = (byte)lIs.read();
        //}

      }

      // read the speeds list
      int lSpeedsCount = CDataFile.BuildShort(lIs.read(), lIs.read());
      _actions.setSpeedsCount(lSpeedsCount);

      for(int i = 0; i < lSpeedsCount; i++)
      {
        _actions.SetSpeedX(i, (byte)lIs.read());//lIs.read());
        _actions.SetSpeedY(i, (byte)lIs.read());//lIs.read());
        //System.out.println("speed: (" + _actions.GetSpeedX(i) + "," + _actions.GetSpeedY(i) + ")");
      }

      lIs.close();
    }
    catch(Exception e)
    {
    }

    //if (_actions == null)
    //{
    //  System.out.println("actions are null for: " + _fileId);
    //}

    return _actions;
  }
}