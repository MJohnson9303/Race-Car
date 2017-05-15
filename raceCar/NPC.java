package raceCar;

import java.awt.Color;
import java.awt.Graphics2D;

//This class handles the NPC object in the game.
public class NPC extends Car implements IDrawable
{
	//IStrategy object that will hold the current NPC strategy
	private IStrategy currentStrategy;
	//This boolean variable will be the flag for when an object can play it's collision sound
	private boolean allowSound;
	public NPC(float x, float y, int width, int length, int speed, int maxSpeed, int fuelLevel, int maxDamageLevel) 
	{
		super(x, y, width, length, speed, maxSpeed, fuelLevel, maxDamageLevel);
	}
	//Method that will set the strategy for the NPC
	public void setStrategy(IStrategy s)
	{
		currentStrategy = s;
	}
	//Method that returns the current strategy of the NPC
	public IStrategy getStrategy()
	{
		return currentStrategy;
	}
	//Method that invokes the strategy behavior of the current strategy
	public void invokeStrategy()
	{
		currentStrategy.apply();
	}
	//Method that causes the car npc to change position based on it's location, speed and heading
	public void move(int elapsedTime) 
	{
		super.move(elapsedTime);
	}
	//Method that draws a NPC as a empty rectangle
	public void draw(Graphics2D g2d)
	{
		super.draw(g2d);
	}
	//A String method that returns a string detailing information of the npc
	public String toString()
	{
		return "NPC: loc="+String.format("%.1f",super.getLocation().getTranslateX())+","+String.format("%.1f",super.getLocation().getTranslateY())+" color=["+getColor().getRed()+","+getColor().getGreen()+","+getColor().getBlue()+"] "
				+"heading="+getHeading()+" speed="+getSpeed()+" width="+getWidth()+" length="+getLength()+" maxSpeed="+getMaximumSpeed()+" steeringDirection="
				+getSteeringDirection()+" fuelLevel="+getFuelLevel()+" damage="+getDamageLevel()+" current strategy:"+currentStrategy.getStrategyName();
	}
	//This method checks if there was a collision with another object
	public boolean collidesWith(ICollider otherObject) 
	{
		return super.collidesWith(otherObject);
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
