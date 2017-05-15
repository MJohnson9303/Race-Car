package raceCar;

//This class details the characteristics and methods that all movable objects share though they will have to implement their own move method
public abstract class MovableObject extends GameWorldObject implements ISteerable
{
	private int heading;
	private int speed;
	//Abstract method that moveable objects have to implement themselves
	public abstract void move(int elapsedTime);
	//Method that changes the heading of a moveable object
	public void changeHeading(int newHeading)
	{
		heading = newHeading;
	}
	//Method that gets the heading of a movable object
	public int getHeading()
	{
		return heading;
	}
	//Method that sets the speed of a movable object
	public void setSpeed(int newSpeed)
	{
		speed = newSpeed;
	}
	//Method that gets the speed of a movable object
	public int getSpeed()
	{
		return speed;
	}
}
