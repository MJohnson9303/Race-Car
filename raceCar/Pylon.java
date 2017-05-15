package raceCar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

//This class handles the pylon object in the game.
public class Pylon extends FixedObject implements IDrawable, ISelectable, ICollider
{
	//Pylon's characteristics
	private int radius;
	private int sequenceNumber;
	//These variables will be used for fuel can selection detection
	private Point2D localPoint;
	private double px;
	private double py;
	private double xLoc;
	private double yLoc;
	//This variable will be used for selection conditions
	private boolean selected;
	//The Color and following integer variables will be used for object highlighting
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
	//These AffineTransform objects are for object transformation into a local coordinate
	private AffineTransform pylonTranslation;
	private AffineTransform pylonScale;
	private AffineTransform clickTransform;
	private AffineTransform saveAT;
	//Pylon constructor using given and set information
	public Pylon(float x, float y, int seqNumber)
	{
		radius = 24;
		sequenceNumber = seqNumber;
		super.setLocation(x, y);
		super.setColor(Color.ORANGE);
		pylonTranslation = new AffineTransform();
		pylonTranslation.translate(super.getLocation().getTranslateX(), super.getLocation().getTranslateY());
		pylonScale = new AffineTransform();
		pylonScale.scale(1, -1);
		saveAT = new AffineTransform();
		clickTransform = new AffineTransform();
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
	//Method to set radius of the pylon
	public void setRadius(int newRadius)
	{
		radius = newRadius;
	}
	//Method to get the radius of the pylon
	public int getRadius()
	{
		return radius;
	}
	//Method to set the sequence number of the pylon
	public void setSequenceNumber(int newSequenceNumber)
	{
		sequenceNumber = newSequenceNumber;
	}
	//Method to get the sequence number of the pylon
	public int getSequenceNumber()
	{
		return sequenceNumber;
	}
	//An empty setColor method to prevent pylons from having their color changed
	public void setColor(Color newColor)
	{
		
	}
	//A String method that returns a string detailing information of the pylon
	public String toString()
	{
		return "Pylon: loc="+getLocation().getTranslateX()+","+getLocation().getTranslateY()+" color=["+getColor().getRed()+","+getColor().getGreen()+","+getColor().getBlue()+"] radius="+getRadius()+" sequenceNumber="+getSequenceNumber();
	}
	//This method sets the flag that says this object is selected or not
	public void setSelected(boolean yesNo) 
	{
		selected = yesNo;
	}
	//This method returns the boolean value of the selected flag
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
		if((px + radius/2.0 >= xLoc) && (px <= xLoc + radius/2.0) && (py + radius/2.0 >= yLoc) && (py <= yLoc + radius/2.0))
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
	//Method that draws a pylon as a filled circle
	public void draw(Graphics2D g2d)
	{
		setHighLightedColor();
		if(selected == false)
		{
			saveAT = g2d.getTransform();
			g2d.transform(pylonTranslation);
			g2d.setColor(super.getColor());
			g2d.fillOval(0 - radius/2, 0 - radius/2, radius, radius);
			g2d.setTransform(saveAT);
			g2d.setColor(Color.BLACK);
			g2d.transform(pylonTranslation);
			g2d.transform(pylonScale);
			g2d.drawString(""+sequenceNumber, -4, 4);
			g2d.setTransform(saveAT);
		}
		else
		{
			saveAT = g2d.getTransform();
			g2d.transform(pylonTranslation);
			g2d.setColor(highLightedColor);
			g2d.fillOval(0 - radius/2, 0 - radius/2, radius, radius);
			g2d.setTransform(saveAT);
			g2d.setColor(Color.WHITE);
			g2d.transform(pylonTranslation);
			g2d.transform(pylonScale);
			g2d.drawString(""+sequenceNumber, -4, 4);
			g2d.setTransform(saveAT);
		}
	}
	//This method checks if there was a collision with another object
	public boolean collidesWith(ICollider otherObject) 
	{
		top = super.getLocation().getTranslateX() + radius/2.0;
		bottom = super.getLocation().getTranslateY() - radius/2.0;
		left = super.getLocation().getTranslateX() - radius/2.0;
		right = super.getLocation().getTranslateX() + radius/2.0;
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
}

