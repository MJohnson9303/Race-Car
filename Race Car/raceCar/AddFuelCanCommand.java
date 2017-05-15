package raceCar;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

//This class defines a command that implements fuel can addition command.
public class AddFuelCanCommand extends AbstractAction
{
	private static AddFuelCanCommand addFuelCanCommand;
	private GameWorld gameWorld;
	
    private AddFuelCanCommand()
    {
       super("Add Fuel Can");
    }
    //Method that creates a new instance of AddFuelCanCommand and if it is already created, return the same
    //AddFuelCanCommand to any object that calls for it
    public static AddFuelCanCommand getAddFuelCan()
    {
    	if(addFuelCanCommand == null)
	    {
		    addFuelCanCommand = new AddFuelCanCommand();
	    }
	    return addFuelCanCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    //Setting the fuel can creation flag to be true when the button is clicked
    public void actionPerformed(ActionEvent e)
    {
        gameWorld.setCreateFuelCanFlag(true);
    }
}
