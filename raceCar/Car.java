package raceCar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Vector;

//This class handles the car object in the game.
public class Car extends MovableObject implements IDrawable, ICollider
{
	//Car's characteristics, flags, variables needed for movement
	private int width;
	private int length;
	private int steeringDirection;
	private int maximumSpeed;
	private int fuelLevel;
	private int damageLevel;
	//A limit or "max damage" a car can sustain
	private int maximumDamageLevel;
	//An important flag that determines if a car can accelerate, brake and change its heading
	private Boolean traction;
	//A pylon flag that shows the last pylon the car collided with
	private int lastPylonReached = 0;
	//This is to be used as a limiter for the steering of the car
	private int steeringLimit = 0;
	//The following variables and object are used for car movement
	private double deltaX;
	private double deltaY;
	private float newX;
	private float newY;
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
	//A collection to hold collided with objects and use it to avoid effects from continuous collisions
	private Vector collisionCollection = new Vector();
	private ICollider holdObject;
	private boolean createOilSlick;
	//These AffineTransform objects are for object transformation into a local coordinate
	private AffineTransform carTranslation;
	private AffineTransform carRotation;
	private AffineTransform carScale;
	private AffineTransform saveAT;
	//Needed objects to help draw the car
	private CarBody carBody;
	private CarAxle[] carAxles;
	//Static integer number that will increment for each car created with "1" being the player car
	private static int tempNumber = 0;
	//Since the final result for tempNumber will be the number of total cars, each time it is incremented
	//its value is stored in carNumber that will properly number the cars
	private int carNumber;
	//Car constructor using given, set and random information
	public Car(float x, float y, int width, int length, int speed, int maxSpeed, int fuelLevel, int maxDamageLevel)
	{
		this.width = width;
		this.length = length;
		super.setLocation(x, y);
		super.setColor(Color.RED);
		super.changeHeading(0);
		super.setSpeed(speed);
		maximumSpeed = maxSpeed;
		steeringDirection = 0;
		this.fuelLevel = fuelLevel;
		damageLevel = 0;
		maximumDamageLevel = maxDamageLevel;
		traction = true;
		carTranslation = new AffineTransform();
		carTranslation.translate(super.getLocation().getTranslateX(), super.getLocation().getTranslateY());
		carRotation = new AffineTransform();
		carRotation.rotate(Math.toRadians(super.getHeading()));
		carScale = new AffineTransform();
		carScale.scale(1, -1);
		saveAT = new AffineTransform();
		carBody = new CarBody(width,length);
		carAxles = new CarAxle[2];
		CarAxle frontAxle = new CarAxle(width+15, 5);
		frontAxle.translate(0, 0+length/3);
		carAxles[0] = frontAxle;
		CarAxle rearAxle = new CarAxle(width+15, 5);
		rearAxle.translate(0, 0-length/3);
		carAxles[1] = rearAxle;
		tempNumber += 1;
		carNumber = tempNumber;
	}
	//Method that sets the width of the car
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}
	//Method the gets the width of the car
	public int getWidth()
	{
		return width;
	}
	//Method to set the length of the car
	public void setLength(int newLength)
	{
		length = newLength;
	}
	//Method to get the length of the car
	public int getLength()
	{
		return length;
	}
	//Method to set the steering direction of the car 5 degrees to the right with correcting of the steering direction if it is above 360 and 
	//a check to enforce that the steering direction can't be more than 40 degrees to the right in relation to the front of the car
	public void setSteeringDirectionRight()
	{
		for(CarAxle ca: carAxles)
		{
			ca.setRotationToIdentity();
		}
		if(steeringLimit < 40)
		{
			steeringDirection += 5;
			//To keep the steering direction compass-like (0-359), steering direction is checked after being incremented by 5 to see if
			//it is above 360 and decrement it by 360 if it is so
			if(steeringDirection >= 360)
			{
				steeringDirection -= 360;
			}
			steeringLimit += 5;
		}
		for(CarAxle ca: carAxles)
		{
			ca.rotate(Math.toRadians(-1*steeringDirection));
		}
	}
	//Method to set the steering direction of the car 5 degrees to the left with correcting of the steering direction if it is below 0 and 
	//a check to enforce that the steering direction can't be more than 320 degrees to the left in relation to the front of the car
	public void setSteeringDirectionLeft()
	{
		for(CarAxle ca: carAxles)
		{
			ca.setRotationToIdentity();
		}
		if(steeringLimit > -40)
		{
			steeringDirection -= 5;
			//To keep the steering direction compass-like (0-359), steering direction is checked after being decremented by 5 to see if
			//it is below 0 and increment it by 360 if it is so
			if(steeringDirection < 0)
			{
				steeringDirection += 360;
			}
			steeringLimit -= 5;
		}
		for(CarAxle ca: carAxles)
		{
			ca.rotate(Math.toRadians(-1*steeringDirection));
		}
	}
	//Method to get the steering direction of the car
	public int getSteeringDirection()
	{
		return steeringDirection;
	}
	//Method to set the maximum speed of the car
	public void setMaximumSpeed(int newMaximumSpeed)
	{
		maximumSpeed = newMaximumSpeed;
	}
	//Method to get the maximum speed of the car
	public int getMaximumSpeed()
	{
		return maximumSpeed;
	}
	//Method to set the fuel level of the car
	public void setFuelLevel(int newFuelLevel)
	{
		fuelLevel = newFuelLevel;
	}
	//Method to get the fuel level of the car
	public int getFuelLevel()
	{
		return fuelLevel;
	}
	//Method to set the damage level of the car and update the car's speed based on the damage level
	public void setDamageLevel(int newDamageLevel)
	{
		damageLevel = newDamageLevel;
		setSpeed((int)(getSpeed() * (1-(getDamageLevel()/10.0))));
	}
	//Method to get the damage level of the car
	public int getDamageLevel()
	{
		return damageLevel;
	}
	public void setMaxDamage(int newMaximumDamageLevel)
	{
		maximumDamageLevel = newMaximumDamageLevel;
	}
	public int getMaxDamage()
	{
		return maximumDamageLevel;
	}
	//Method to set the traction flag of the car
	public void setTraction(Boolean newTraction)
	{
		traction = newTraction;
	}
	//Method to get the traction flag of the car
	public Boolean getTraction()
	{
		return traction;
	}
	//Method that sets the pylon flag of the car
	public void setLastPylonReached(int newLastPylonReached)
	{
		lastPylonReached = newLastPylonReached;	
	}
	//Method that gets the pylon flag of the car
	public int getLastPylonReached()
	{
		return lastPylonReached;
	}
	//Method that causes the car to change position based on it's location, speed and heading
	public void move(int elapsedTime) 
	{
		carTranslation.setToIdentity();
		carRotation.setToIdentity();
		deltaX = Math.cos(Math.toRadians(90-super.getHeading())) * (super.getSpeed() * (elapsedTime / 1000.0));
		deltaY = Math.sin(Math.toRadians(90-super.getHeading())) * (super.getSpeed() * (elapsedTime / 1000.0));
	    newX = (float)(super.getLocation().getTranslateX() + deltaX);
	    newY = (float)(super.getLocation().getTranslateY() + deltaY);
	    super.setLocation(newX, newY);
	    carTranslation.translate(newX, newY);
	    //Multiply the radians heading by -1 because the radian circle goes counter clock wise to get the 
	    //correct direction that the car is facing while it is moving due to the inverse by the y-axis scale
	    carRotation.rotate(-1*Math.toRadians(super.getHeading()));
	}
	//Method that draws a car as a filled rectangle with car axles and wheels
	public void draw(Graphics2D g2d)
	{	
		saveAT = g2d.getTransform();
	    g2d.transform(carTranslation);
		g2d.transform(carRotation);
		g2d.setColor(super.getColor());
		carBody.draw(g2d);
		g2d.setColor(Color.BLACK);
		for(CarAxle ca: carAxles)
		{
			ca.draw(g2d);
		}
		g2d.setTransform(saveAT);
		g2d.setColor(Color.WHITE);
		g2d.transform(carTranslation);
		g2d.transform(carRotation);
		g2d.transform(carScale);
		g2d.drawString(""+carNumber, -3, 4);
		g2d.setTransform(saveAT);
	}
	//A String method that returns a string detailing information of the car
	public String toString()
	{
		return "Car: loc="+String.format("%.1f",super.getLocation().getTranslateX())+","+String.format("%.1f",super.getLocation().getTranslateY())+" color=["+getColor().getRed()+","+getColor().getGreen()+","+getColor().getBlue()+"] "
				+"heading="+getHeading()+" speed="+getSpeed()+" width="+getWidth()+" length="+getLength()+" maxSpeed="+getMaximumSpeed()+" steeringDirection="
				+getSteeringDirection()+" fuelLevel="+getFuelLevel()+" damage="+getDamageLevel();
	}
	public void setOilSlickFlag(boolean oilSlickFlagStatus)
	{
		createOilSlick = oilSlickFlagStatus;
	}
	public boolean getOilSlickFlag()
	{
		return createOilSlick;
	}
	//This method checks if there was a collision with another object and returns a boolean
	public boolean collidesWith(ICollider otherObject) 
	{
		top = super.getLocation().getTranslateY() + length/2.0;
		bottom = super.getLocation().getTranslateY() - length/2.0;
		left = super.getLocation().getTranslateX() - width/2.0;
		right = super.getLocation().getTranslateX() + width/2.0;
		if(otherObject instanceof FuelCan)
		{
			FuelCan fcobj = (FuelCan)otherObject;
			otherTop = fcobj.getLocation().getTranslateY() + fcobj.getSize()/2.0;
			otherBottom = fcobj.getLocation().getTranslateY() - fcobj.getSize()/2.0;
			otherLeft = fcobj.getLocation().getTranslateX() - fcobj.getSize()/2.0;
			otherRight = fcobj.getLocation().getTranslateX() + fcobj.getSize()/2.0;
		}
		if(otherObject instanceof Bird)
		{
			Bird bobj = (Bird)otherObject;
			otherTop = bobj.getLocation().getTranslateY() + bobj.getSize()/2.0;
			otherBottom = bobj.getLocation().getTranslateY() - bobj.getSize()/2.0;
			otherLeft = bobj.getLocation().getTranslateX() - bobj.getSize()/2.0;
			otherRight = bobj.getLocation().getTranslateX() + bobj.getSize()/2.0;
		}
		if(otherObject instanceof Pylon)
		{
			Pylon pobj = (Pylon)otherObject;
			otherTop = pobj.getLocation().getTranslateY() + pobj.getRadius()/2.0;
			otherBottom = pobj.getLocation().getTranslateY() - pobj.getRadius()/2.0;
			otherLeft = pobj.getLocation().getTranslateX() - pobj.getRadius()/2.0;
			otherRight = pobj.getLocation().getTranslateX() + pobj.getRadius()/2.0;
		}
		if(otherObject instanceof OilSlick)
		{
			OilSlick osobj = (OilSlick)otherObject;
			otherTop = osobj.getLocation().getTranslateY() + osobj.getLength()/2.0;
			otherBottom = osobj.getLocation().getTranslateY() - osobj.getLength()/2.0;
			otherLeft = osobj.getLocation().getTranslateX() - osobj.getWidth()/2.0;
			otherRight = osobj.getLocation().getTranslateX() + osobj.getWidth()/2.0;
		}
		if(otherObject instanceof NPC)
		{
			NPC npcobj = (NPC)otherObject;
			otherTop = npcobj.getLocation().getTranslateY() + npcobj.getLength()/2.0;
			otherBottom = npcobj.getLocation().getTranslateY() - npcobj.getLength()/2.0;
			otherLeft = npcobj.getLocation().getTranslateX() - npcobj.getWidth()/2.0;
			otherRight = npcobj.getLocation().getTranslateX() + npcobj.getWidth()/2.0;
		}
		if(otherObject instanceof Car)
		{
			Car cobj = (Car)otherObject;
			otherTop = cobj.getLocation().getTranslateY() + cobj.getLength()/2.0;
			otherBottom = cobj.getLocation().getTranslateY() - cobj.getLength()/2.0;
			otherLeft = cobj.getLocation().getTranslateX() - cobj.getWidth()/2.0;
			otherRight = cobj.getLocation().getTranslateX() + cobj.getWidth()/2.0;
		}
		//If the objects are not within each other, then a collision is not occurring and remove 
		//objects out of the collision collection.
		if (right < otherLeft || left > otherRight || otherTop < bottom || top < otherBottom)
		{
			collided = false;
			for(int i = 0; i < collisionCollection.size(); i++)
			{
				holdObject = (ICollider)collisionCollection.elementAt(i);
				if(holdObject.collidesWith(this) == false)
				{
					if(holdObject instanceof OilSlick)
					{
						//Make the car able to turn since it is out of the OilSlick
						traction = true;
					}
					collisionCollection.remove(holdObject);
				}
			}
		}
		//Car collided with objects.
		else
		{
			collided = true;
		}
		return collided;
	}
	//This method handles the effect of the collision with another object
	public void handleCollision(ICollider otherObject) 
	{
		//If the object is not within the car's collision collection, it is added.
		if(collisionCollection.contains(otherObject) == false)
		{
			collisionCollection.add(otherObject);
			//Give the car gas, play sound and set the FuelCan to be deleted.
			if(otherObject instanceof FuelCan)
			{
				FuelCan fcobj = (FuelCan)otherObject;
				fuelLevel += fcobj.getSize();
				fcobj.setAllowSound(true);
				fcobj.setDeleteStatus(true);	
			}
			//Damage the car.
			if(otherObject instanceof Bird)
			{
				damageLevel += 1;
			}
			//Update the Pylon reached.
			if(otherObject instanceof Pylon)
			{
				Pylon pobj = (Pylon)otherObject;
				//Making certain that the Pylon number in the User GUI is supposed to update
				//by reaching the Pylons sequentially.
				if(pobj.getSequenceNumber() - lastPylonReached == 1)
				{
					lastPylonReached = pobj.getSequenceNumber();
				}
			}
			//Make the car unable to turn.
			if(otherObject instanceof OilSlick)
			{
				traction = false;
			}
			//Damage the car and damage the NPC car with a crash sound.
			//OilSlick is created from the collision as well.
			if(otherObject instanceof NPC)
			{
				NPC npcobj = (NPC)otherObject;
				setDamageLevel(damageLevel + 2);
				npcobj.setAllowSound(true);
				npcobj.setDamageLevel(npcobj.getDamageLevel() + 2);
				createOilSlick = true;
			}
		}
	}
}
