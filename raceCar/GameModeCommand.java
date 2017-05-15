package raceCar;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;
//This class defines a command that pauses and plays the game.
public class GameModeCommand extends AbstractAction
{
	private static GameModeCommand gameModeCommand;
	private GameWorld gameWorld;
	private Game game;
	private Timer timer;
	private Sound gameSound;
	private GameModeCommand()
    {
       super("Pause");
    }
	//Method that creates a new instance of GameModeCommand and if it is already created, 
    //return the same GameModeCommand to any object that calls for it
    public static GameModeCommand getGameMode()
    {
    	if(gameModeCommand == null)
	    {
		    gameModeCommand = new GameModeCommand();
	    }
	    return gameModeCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(Timer timer, Game game, GameWorld gameWorld)
    {
    	this.timer = timer;
    	this.gameWorld = gameWorld;
    	this.game = game;
    	this.gameSound = gameWorld.getBackGroundSound();
    }
	public void actionPerformed(ActionEvent e) 
	{
		//If the game is unpaused, then set command value to "Play", alter game mode, stop the timer which will
		//cause everything to stop, turn off the background music and bind certain buttons
		if(gameWorld.getGameMode() == true)
		{
			super.putValue(NAME, "Play");
			timer.stop();
			gameWorld.setGameMode(false);
			gameSound.stop();
			game.pauseModeBinding();
		}
		//If the game is paused, then set command value to "Pause", start the timer, alter game mode,
		//turn sounds back on and unbind certain buttons.
		else
		{
			super.putValue(NAME, "Pause");
			timer.start();
			gameWorld.setGameMode(true);
			if(gameWorld.getGameSound() == true)
			{
				gameSound.loop();
			}
			game.playModeBinding();
		}
	}
}
