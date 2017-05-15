package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements a car deceleration operation. 
public class BrakeCarCommand extends AbstractAction
{
    private static BrakeCarCommand brakeCarCommand;
    private GameWorld gameWorld;
    
    private BrakeCarCommand()
    {
       super("Brake Car");
    }
    //Method that creates a new instance of BrakeCarCommand and if it is already created, 
    //return the same BrakeCarCommand to any object that calls for it
    public static BrakeCarCommand getCarBrake()
    {
    	if(brakeCarCommand == null)
	    {
		    brakeCarCommand = new BrakeCarCommand();
	    }
	    return brakeCarCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
       gameWorld.brakeCar();
    }
}
