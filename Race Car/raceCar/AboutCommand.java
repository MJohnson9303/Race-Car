package raceCar;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/*This class defines a command that implements an about operation. 
  It creates a dialog box with relevant information displayed inside.*/
public class AboutCommand extends AbstractAction
{
    private static AboutCommand aboutCommand;
    
    private AboutCommand()
    {
       super("About");
    }
    //Method that creates a new instance of AboutCommand and if it is already created, return the same AboutCommand
    //to any object that calls for it
    public static AboutCommand getAbout()
    {
    	if(aboutCommand == null)
	    {
		    aboutCommand = new AboutCommand();
	    }
	    return aboutCommand;
    }
    public void actionPerformed(ActionEvent e)
    {
       JOptionPane.showMessageDialog(null, "Name: Michael Johnson, Course Name: CSC 133, Project Version: 2.0");
    }
}
