package raceCar;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//The class handles the tire component of an axle.
public class CarTire 
{
	private int length;
	private int width;
	private AffineTransform tireTranslation;
	private AffineTransform tireRotation;
	private AffineTransform saveAT;
	public CarTire(int width,int length)
	{
		this.length = length;
		this.width = width;
		tireTranslation = new AffineTransform();
		tireRotation = new AffineTransform();
		saveAT = new AffineTransform();
	}
	//This method translates the tire 
	public void translate(double x, double y)
	{
		tireTranslation.translate(x, y);
	}
	//This method sets the translation affine transform matrix to the identity matrix
	public void setTranslateToIdentity()
	{
		tireTranslation.setToIdentity();
	}
	//This method returns the tire translation affine transform object 
	public AffineTransform getTranslation()
	{
		return tireTranslation;
	}
	//This method rotates the tire 
	public void rotate(double degrees)
	{
		tireRotation.rotate(degrees);
	}
	//This method sets the rotation affine transform matrix to the identity matrix
	public void setRotationToIdentity()
	{
		tireRotation.setToIdentity();
	}
	//This method returns the tire rotation affine transform object
	public AffineTransform getRotation()
	{
		return tireRotation;
	}
	//This method draws the tire based on it's local and passed in transformations
	public void draw(Graphics2D g2d) 
	{
		saveAT = g2d.getTransform();
		g2d.transform(tireTranslation);
		g2d.transform(tireRotation);
		g2d.fillRect(0 - width/2, 0 - length/2, width, length);
		g2d.setTransform(saveAT);
	}
}
