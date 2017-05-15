package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements steering car right operation. 
public class SteerCarRightCommand extends AbstractAction
{
    private static SteerCarRightCommand steerCarRightCommand;
    private GameWorld gameWorld;
    
    private SteerCarRightCommand()
    {
       super("Steer Car Right");
    }
    //Method that creates a new instance of SteerCarRightCommand and if it is already created, 
    //return the same SteerCarRightCommand to any object that calls for it
    public static SteerCarRightCommand getSteerCarRight()
    {
    	if(steerCarRightCommand == null)
	    {
		    steerCarRightCommand = new SteerCarRightCommand();
	    }
	    return steerCarRightCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
       gameWorld.steerRight();
    }
}
