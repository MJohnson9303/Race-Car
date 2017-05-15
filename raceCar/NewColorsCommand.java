package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that defines a new colors operation. 
//If invoked, will give all applicable game world objects new random colors.
public class NewColorsCommand extends AbstractAction
{
    private static NewColorsCommand newColorsCommand;
    private GameWorld gameWorld;
    
    private NewColorsCommand()
    {
       super("New Colors");
    }
    //Method that creates a new instance of NewColorsCommand and if it is already created, 
    //return the same NewColorsCommand to any object that calls for it
    public static NewColorsCommand getNewColors()
    {
    	if(newColorsCommand == null)
	    {
		    newColorsCommand = new NewColorsCommand();
	    }
	    return newColorsCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
       gameWorld.newRandomColors();
    }
}
