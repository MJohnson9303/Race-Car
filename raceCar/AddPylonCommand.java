package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements pylon addition command.
public class AddPylonCommand extends AbstractAction
{

	private static AddPylonCommand addPylonCommand;
	private GameWorld gameWorld;
	
    private AddPylonCommand()
    {
       super("Add Pylon");
    }
    //Method that creates a new instance of AddPylonCommand and if it is already created, return the same
    //AddPylonCommand to any object that calls for it
    public static AddPylonCommand getAddPylon()
    {
    	if(addPylonCommand == null)
	    {
		    addPylonCommand = new AddPylonCommand();
	    }
	    return addPylonCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    //Setting the pylon creation flag to be true when the button is clicked
    public void actionPerformed(ActionEvent e)
    {
        gameWorld.setCreatePylonFlag(true);
    }
}

