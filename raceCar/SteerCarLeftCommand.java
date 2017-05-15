package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements steering car left operation. 
public class SteerCarLeftCommand extends AbstractAction
{
    private static SteerCarLeftCommand steerCarLeftCommand;
    private GameWorld gameWorld;
    
    private SteerCarLeftCommand()
    {
       super("Steer Car Left");
    }
    //Method that creates a new instance of SteerCarLeftCommand and if it is already created, 
    //return the same SteerCarLeftCommand to any object that calls for it
    public static SteerCarLeftCommand getSteerCarLeft()
    {
    	if(steerCarLeftCommand == null)
	    {
		    steerCarLeftCommand = new SteerCarLeftCommand();
	    }
	    return steerCarLeftCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
       gameWorld.steerLeft();
    }
}
