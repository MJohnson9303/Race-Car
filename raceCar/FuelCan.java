package raceCar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

//This class handles the fuel can object in the game.
public class FuelCan extends FixedObject implements IDrawable, ISelectable, ICollider
{
	//Fuel can's characteristic and needed objects
	private int size;
	//The variable will be used for fuel can selection detection
	private Point2D localPoint;
	private double px;
	private double py;
	private double xLoc;
	private double yLoc;
	//This variable will be used for selection conditions
	private boolean selected;
	//The Color and following int variables will be used for object highlighting
	private Color highLightedColor;
	private int redColor;
	private int greenColor;
	private int blueColor;
	//Boolean that will determine if an object is to be deleted
	private boolean delete;
	//The boolean and following variables will be used for collision detection
	private boolean collided;
	private double top;
	private double bottom;
	private double left;
	private double right;
	private double otherTop;
	private double otherBottom;
	private double otherLeft;
	private double otherRight;
	//This boolean variable will be the flag for when an object can play it's collision sound
	private boolean allowSound;
	//These AffineTransform objects are for object transformation into a local coordinate
	private AffineTransform fuelCanTranslation;
	private AffineTransform	clickTransform;
	private AffineTransform fuelCanScale;
	private AffineTransform saveAT;
	
	//Fuel can constructor that uses set and random information
	public FuelCan(float x, float y, int size)
	{
		this.size = size;
		super.setLocation(x, y);
		super.setColor(Color.BLUE);
		fuelCanTranslation = new AffineTransform();
		fuelCanTranslation.translate(super.getLocation().getTranslateX(), super.getLocation().getTranslateY());
		fuelCanScale = new AffineTransform();
		fuelCanScale.scale(1, -1);
		clickTransform = new AffineTransform();
		saveAT = new AffineTransform();
		localPoint = new Point2D.Double(0, 0);
		setHighLightedColor();
	}
	//Method to get the objects highlighted color based on it's initialized color that will be used if
	//the object is selected
	private void setHighLightedColor()
	{
		redColor = Math.abs(super.getColor().getRed() - 255);
		greenColor = Math.abs(super.getColor().getGreen() - 255);
		blueColor = Math.abs(super.getColor().getBlue() - 255);
		highLightedColor = new Color(redColor, greenColor, blueColor);
	}
	//Method that sets the size of the fuel can
	public void setSize(int newSize)
	{
		size = newSize;
	}
	//Method that gets the size of the fuel can
	public int getSize()
	{
		return size;
	}
	//A String method that returns a string detailing information of the fuel can
	public String toString()
	{
		return "FuelCan: loc="+getLocation().getTranslateX()+","+getLocation().getTranslateY()+" color=["+getColor().getRed()+","+getColor().getGreen()+","+getColor().getBlue()+"] size="+getSize();
	}
	//This method provides a way to mark an object a "selected" or not
	public void setSelected(boolean yesNo) 
	{
		selected = yesNo;
	}
	//This method provides a way to test whether an object is selected
	public boolean isSelected() 
	{
		if(selected == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//This method provides a way to determine if a mouse point is "in" an object
	public boolean contains(Point2D worldPoint)
	{
		try
		{
			localPoint = clickTransform.createInverse().transform(worldPoint, null);
		}
		catch (NoninvertibleTransformException e)
		{
			
		}
		px = localPoint.getX();
		py = localPoint.getY();
		xLoc = super.getLocation().getTranslateX();
		yLoc = super.getLocation().getTranslateY();
		if((px + 12 >= xLoc) && (px <= xLoc + 12) && (py + 12 >= yLoc) && (py <= yLoc + 12))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//This method sets a fuel can's status to be deleted
	public void setDeleteStatus(boolean deleteStatus)
	{
		delete = deleteStatus;
	}
	//This method provides a fuel can's status for deletion
	public boolean getDeleteStatus()
	{
		return delete;
	}
	//Method that draws a fuel can as a filled square with a string drawn next to it displaying its value if not selected
	//but, if the fuel can is selected it is drawn as an empty square
	public void draw(Graphics2D g2d)
	{
		setHighLightedColor();
		if(selected == false)
		{
			saveAT = g2d.getTransform();
			g2d.transform(fuelCanTranslation);
			g2d.setColor(super.getColor());
			g2d.fillRect(0 - 12, 0 - 12, 24, 24);
			g2d.setTransform(saveAT);
			g2d.setColor(Color.WHITE);
			g2d.transform(fuelCanTranslation);
			g2d.transform(fuelCanScale);
			g2d.drawString(""+size, -5, 4);
			g2d.setTransform(saveAT);
		}
		else
		{
			saveAT = g2d.getTransform();
			g2d.transform(fuelCanTranslation);
			g2d.setColor(highLightedColor);
			g2d.fillRect(0 - 12, 0 - 12, 24, 24);
			g2d.setTransform(saveAT);
			g2d.setColor(Color.WHITE);
			g2d.transform(fuelCanTranslation);
			g2d.transform(fuelCanScale);
			g2d.drawString(""+size, -5, 4);
			g2d.setTransform(saveAT);
		}
	}
	//This method checks if there was a collision with another object
	public boolean collidesWith(ICollider otherObject) 
	{
		top = super.getLocation().getTranslateY() + 24/2.0;
		bottom = super.getLocation().getTranslateY() - 24/2.0;
		left = super.getLocation().getTranslateX() - 24/2.0;
		right = super.getLocation().getTranslateX() + 24/2.0;
		if(otherObject instanceof Car)
		{
			Car cobj = (Car)otherObject;
			otherTop = cobj.getLocation().getTranslateY() + cobj.getLength()/2.0;
			otherBottom = cobj.getLocation().getTranslateY() - cobj.getLength()/2.0;
			otherLeft = cobj.getLocation().getTranslateX() - cobj.getWidth()/2.0;
			otherRight = cobj.getLocation().getTranslateX() + cobj.getWidth()/2.0;
		}
		if (right < otherLeft || left > otherRight || otherTop < bottom || top < otherBottom)
		{
			collided = false;
		}
		else
		{
			collided = true;
		}
		return collided;
	}
	//This method handles the effect of the collision with another object
	public void handleCollision(ICollider otherObject) 
	{
		
	}
	//This method sets the boolean flag for the object to be able to play it's collision sound
	public void setAllowSound(boolean soundStatus)
	{
		allowSound = soundStatus;
	}
	//This method returns the object's flag status for playing it's collision sound
	public boolean getAllowSound()
	{
		return allowSound;
	}
}
