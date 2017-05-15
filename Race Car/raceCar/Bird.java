package raceCar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

//This class handles the bird object in the game.
public class Bird extends MovableObject implements IDrawable, ICollider
{
	//Bird's characteristics, variables needed for movement and needed objects
	private int size;
	private double deltaX, deltaY;
	private float newX;
	private float newY;
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
	private AffineTransform birdTranslation;
	private AffineTransform birdRotation;
	private AffineTransform saveAT;
	//Bird constructor using set and random information
	public Bird()
	{
		size = 30;
		super.setLocation(randomValue.nextInt(950) + 1, randomValue.nextInt(950) + 1);
		super.setColor(Color.GREEN);
		super.changeHeading(randomValue.nextInt(359) + 1);
		super.setSpeed(50);
		birdTranslation = new AffineTransform();
		birdTranslation.translate(super.getLocation().getTranslateX(), super.getLocation().getTranslateY());
		birdRotation = new AffineTransform();
		birdRotation.rotate(-1*Math.toRadians(super.getHeading()));
		saveAT = new AffineTransform();
	}
	//Method that sets the size of the bird
	public void setSize(int newSize)
	{
		size = newSize;
	}
	//Method that gets the size of the bird
	public int getSize()
	{
		return size;
	}
	//An empty changeHeading method to prevent birds from having their heading changed
	public void changeHeading(int newHeading)
	{
		
	}
	//An empty setColor method to prevent birds from having their color changed
	public void setColor(Color newColor)
	{
		
	}
	//Method that causes the bird to change position based on it's location, speed and heading
	public void move(int elapsedTime) 
	{
		birdTranslation.setToIdentity();
		birdRotation.setToIdentity();
		deltaX = Math.cos(Math.toRadians(90-super.getHeading())) * (super.getSpeed() * (elapsedTime / 1000.0));
	    deltaY = Math.sin(Math.toRadians(90-super.getHeading())) * (super.getSpeed() * (elapsedTime / 1000.0));
	    newX = (float)(super.getLocation().getTranslateX() + deltaX);
	    newY = (float)(super.getLocation().getTranslateY() + deltaY);
	    super.setLocation(newX, newY);
	    birdTranslation.translate(newX, newY);
	    birdRotation.rotate(-1*Math.toRadians(super.getHeading()));
	}
	//Method that draws a bird as a empty circle
	public void draw(Graphics2D g2d)
	{
		saveAT = g2d.getTransform();
		g2d.transform(birdTranslation);
		g2d.transform(birdRotation);
		g2d.setColor(super.getColor());
		g2d.drawOval(0, 0, size, size);
		g2d.setTransform(saveAT);
	}
	//A String method that returns a string detailing information of the bird
	public String toString()
	{
		return "Bird: loc="+String.format("%.1f",super.getLocation().getTranslateX())+","+String.format("%.1f",super.getLocation().getTranslateY())+" color=["+getColor().getRed()+","+getColor().getGreen()+","+getColor().getBlue()+"] heading="+getHeading()+" speed="
				+getSpeed()+" size="+getSize();
	}
	//This method checks if there was a collision with another object
	public boolean collidesWith(ICollider otherObject) 
	{
		top = super.getLocation().getTranslateY() + size/2.0;
		bottom = super.getLocation().getTranslateY() - size/2.0;
		left = super.getLocation().getTranslateX() - size/2.0;
		right = super.getLocation().getTranslateX() + size/2.0;
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
	//This method handles the effect of the collision with another object.
	//Nothing in this case as no collisions affect the bird.
	public void handleCollision(ICollider otherObject) 
	{
		
	}
}
