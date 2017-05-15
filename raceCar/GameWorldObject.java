package raceCar;

import java.awt.Color;
import java.awt.geom.AffineTransform;

//This class has all characteristics and methods that all objects in the game share
public class GameWorldObject 
{
	private AffineTransform objectLocation = new AffineTransform();
	private Color objectColor = new Color(0,0,0);
	
	//Method that sets the color of a game object
	public void setColor(Color newColor)
	{
		objectColor = newColor;
	}
	//Method that gets the color object of a game object
	public Color getColor()
	{
		return objectColor;
	}
	//Method that set the location of a game object
	public void setLocation(float x, float y)
	{
		objectLocation.setToIdentity();
		objectLocation.translate(x, y);
	}
	//Method that gets the location object of a game object
	public AffineTransform getLocation()
	{
		return objectLocation;
	}
}
