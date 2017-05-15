package raceCar;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//The class handles the axle component of a car
public class CarAxle 
{
	private AffineTransform axleTranslation;
	private AffineTransform saveAT;
	private CarTire[] carTires;
	private CarShaft carShaft;
	
	public CarAxle(int width, int length)
	{
		axleTranslation = new AffineTransform();
		saveAT = new AffineTransform();
		carTires = new CarTire[2];
		CarTire leftTire = new CarTire(8, 20);
		leftTire.translate(-15,0);
		carTires[0] = leftTire;
		CarTire rightTire = new CarTire(8, 20);
		rightTire.translate(15, 0);
		carTires[1] = rightTire;
		carShaft = new CarShaft(width, length);
	}
	//This method translates the axle
	public void translate(double x, double y)
	{
		axleTranslation.translate(x, y);
	}
	//This method sets the translation affine transform matrix to the identity matrix
	public void setTranslateToIdentity()
	{
		axleTranslation.setToIdentity();
	}
	//This method returns the axle translation affine transform object 
	public AffineTransform getTranslation()
	{
		return axleTranslation;
	}
	//This method rotates the axle 
	public void rotate(double degrees)
	{
		for(CarTire ct: carTires)
		{
			ct.rotate(degrees);
		}
	}
	//This method sets the rotation affine transform matrix to the identity matrix
	public void setRotationToIdentity()
	{
		for(CarTire ct: carTires)
		{
			ct.setRotationToIdentity();
		}
	}
	//This method draws the axle based on it's local and passed in transformations
	void draw(Graphics2D g2d) 
	{
		saveAT = g2d.getTransform();
		g2d.transform(axleTranslation);
		for(CarTire ct: carTires)
		{
			ct.draw(g2d);
		}
		carShaft.draw(g2d);
		g2d.setTransform(saveAT);
	}
}
