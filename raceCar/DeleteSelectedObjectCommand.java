package raceCar;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//This class defines a command that implements delete selected object command.
public class DeleteSelectedObjectCommand extends AbstractAction
{

	private static DeleteSelectedObjectCommand deleteSelectedObjectCommand;
	private GameWorld gameWorld;
	
    private DeleteSelectedObjectCommand()
    {
       super("Delete");
    }
    //Method that creates a new instance of DeleteSelectedObjectCommand and if it is already created, return the same
    //DeleteSelectedObjectCommand to any object that calls for it
    public static DeleteSelectedObjectCommand getDeleteSelectedObject()
    {
    	if(deleteSelectedObjectCommand == null)
	    {
		    deleteSelectedObjectCommand = new DeleteSelectedObjectCommand();
	    }
	    return deleteSelectedObjectCommand;
    }
    //Setting target for command that will allow it to access a specific method when the command is invoked
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    }
    public void actionPerformed(ActionEvent e)
    {
        gameWorld.deleteSelectedObject();
    }
}

