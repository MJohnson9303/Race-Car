package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements a add oil slick operation. 
public class AddOilSlickCommand extends AbstractAction
{
    private static AddOilSlickCommand addOilSlickCommand;
    private GameWorld gameWorld;
    
    private AddOilSlickCommand()
    {
       super("Add Oil Slick");
    }
    //Method that creates a new instance of AddOilSlickCommand and if it is already created, 
    //return the same AddOilSlickCommand to any object that calls for it
    public static AddOilSlickCommand getAddOilSlick()
    {
    	if(addOilSlickCommand == null)
	    {
		    addOilSlickCommand = new AddOilSlickCommand();
	    }
	    return addOilSlickCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
       gameWorld.addOilSlick();
    }
}
