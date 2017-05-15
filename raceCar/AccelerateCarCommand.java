package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements a car acceleration operation. 
public class AccelerateCarCommand extends AbstractAction
{
    private static AccelerateCarCommand accelerateCarCommand;
    private GameWorld gameWorld;
    
    private AccelerateCarCommand()
    {
       super("Accelerate Car");
    }
    //Method that creates a new instance of AccelerateCarCommand and if it is already created, 
    //return the same AccelerateCarCommand to any object that calls for it
    public static AccelerateCarCommand getCarAccelerate()
    {
    	if(accelerateCarCommand == null)
	    {
		    accelerateCarCommand = new AccelerateCarCommand();
	    }
	    return accelerateCarCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
       gameWorld.accelerateCar();
    }
}
