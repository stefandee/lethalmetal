package ui;

import ui.GameScreen;

/**
 * Created by IntelliJ IDEA.
 * User: bogdan
 * Date: 06.08.2003
 * Time: 16:16:01
 * To change this template use Options | File Templates.
 */
public class TriggersObserver {
  public void notifyPlayerIsDead()
  {
    //System.out.println("TriggersObserver.notifyPlayerIsDead notified . . .");
    GameScreen.getInstance().SetScreen(GameScreen.GS_ENDGAME_DEATH);
    //GameScreen.getInstance().initLevel();
    //MainMIDlet.getInstance().setCurrent(new TextForm("You've died", "You are dead !\n\nWe feel sorry for your family loss but \"a la guerre come a ala guerre\"", GameScreen.getInstance()));
  }

  public void notifySaveGame()
  {
    //System.out.println("TriggersObserver.notifySaveGame notified . . .");
    //MainMIDlet.getInstance().setCurrent(new TextForm("Save game", "The game has been saved, continue your game", GameScreen.getInstance()));
  }

  public void notifyEndLevel()
  {
    GameScreen.getInstance().SetScreen(GameScreen.GS_ENDLEVEL);
    //System.out.println("TriggersObserver.notifyEndLevel notified . . .");
    //GameScreen.getInstance().initLevel();
    //MainMIDlet.getInstance().setCurrent(new TextForm("End level", "Congratulations!\nYou've completed the level, now try the next one.", GameScreen.getInstance()));
  }

  public void notifyTextMessage(String _text)
  {
    GameScreen.getInstance().setText(_text);
    GameScreen.getInstance().SetScreen(GameScreen.GS_TEXT);
  }

  public void notifyEndGame()
  {
    GameScreen.getInstance().SetScreen(GameScreen.GS_ENDGAME_VICTORY);
    //System.out.println("TriggersObserver.notifyEndGame notified . . .");
    //MainMIDlet.getInstance().setCurrent(new TextForm("End game", "Done!", new MainMenuList("Impalator", List.IMPLICIT)));
  }
}
