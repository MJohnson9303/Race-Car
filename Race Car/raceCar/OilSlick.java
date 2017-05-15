package raceCar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

//This class handles the oil slick object in the game.
public class OilSlick extends FixedObject implements IDrawable, ICollider
{
	//Oil Slick's characteristics and needed objects
	private int width;
	private int length;
	private Random randomValue = new Random();
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
	private AffineTransform oilSlickTranslation;
	private AffineTransform saveAT;
	
	//Oil slick constructor using set information
	public OilSlick(float x, float y)
	{
		width = randomValue.nextInt(20) + 10;
		length = randomValue.nextInt(20) + 10;
		super.setLocation(x, y);
		super.setColor(Color.BLACK);
		oilSlickTranslation = new AffineTransform();
		oilSlickTranslation.translate(super.getLocation().getTranslateX(), super.getLocation().getTranslateY());
		saveAT = new AffineTransform();
	}
	//Method that sets the width of the oil slick
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}
	//Method that gets the width of the oil slick
	public int getWidth()
	{
		return width;
	}
	//Method that sets the length of the oil slick
	public void setLength(int newLength)
	{
		length = newLength;
	}
	//Method that gets the length of the oil slick
	public int getLength()
	{
		return length;
	}
	//Method that draws a oil slick as a filled oval
	public void draw(Graphics2D g2d)
	{
		saveAT = g2d.getTransform();
		g2d.transform(oilSlickTranslation);
		g2d.setColor(super.getColor());
		g2d.fillOval(0 - width/2, 0 - length/2, width, length);
		g2d.setTransform(saveAT);
	}
	//A String method that returns a string detailing information of the oil slick
	public String toString()
	{
		return "OilSlick: loc="+getLocation().getTranslateX()+","+getLocation().getTranslateY()+" color=["+getColor().getRed()+","+getColor().getGreen()+","+getColor().getBlue()+"] width="+getWidth()+" length="+getLength();
	}
	//This method checks if there was a collision with another object
	public boolean collidesWith(ICollider otherObject) 
	{
		top = super.getLocation().getTranslateY() + length/2.0;
		bottom = super.getLocation().getTranslateY() - length/2.0;
		left = super.getLocation().getTranslateX() - width/2.0;
		right = super.getLocation().getTranslateX() + width/2.0;
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
