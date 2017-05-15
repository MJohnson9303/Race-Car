package raceCar;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

//This interface defines the methods provided by an object which is "Selectable" on the screen
public interface ISelectable 
{
	//This method provides a way to mark an object a "selected" or not
	public void setSelected(boolean yesNo);
	//This method provides a way to test whether an object is selected
	public boolean isSelected();
	//This method provides a way to determine if a mouse point is "in" an object
	public boolean contains(Point2D p);
	//This method sets an object's status to be deleted
	public void setDeleteStatus(boolean deleteStatus);
	//This method provides a object's status for deletion
	public boolean getDeleteStatus();
	//This method proves a way to "draw" that knows about drawing in different
	//ways depending on "isSeleted"
	public void draw(Graphics2D g2d);
}
