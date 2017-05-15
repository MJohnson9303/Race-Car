package raceCar;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//The class handles the shaft component of an axle
public class CarShaft 
{
	private int length;
	private int width;
	private AffineTransform shaftTranslation;
	private AffineTransform shaftRotation;
	private AffineTransform saveAT;
	public CarShaft(int width,int length)
	{
		this.length = length;
		this.width = width;
		shaftTranslation = new AffineTransform();
		shaftRotation = new AffineTransform();
		saveAT = new AffineTransform();
	}
	//This method translates the shaft
	public void translate(double x, double y)
	{
		shaftTranslation.translate(x, y);
	}
	//This method sets the translation affine transform matrix to the identity matrix
	public void setTranslateToIdentity()
	{
		shaftTranslation.setToIdentity();
	}
	//This method returns the shaft translation affine transform object 
	public AffineTransform getTranslation()
	{
		return shaftTranslation;
	}
	//This method rotates the shaft 
	public void rotate(double degrees)
	{
		shaftRotation.rotate(degrees);
	}
	//This method sets the rotation affine transform matrix to the identity matrix
	public void setRotationToIdentity()
	{
		shaftRotation.setToIdentity();
	}
	//This method returns the shaft rotation affine transform object
	public AffineTransform getRotation()
	{
		return shaftRotation;
	}
	//This method draws the shaft based on it's local and passed in transformations
	public void draw(Graphics2D g2d) 
	{
		saveAT = g2d.getTransform();
		g2d.transform(shaftTranslation);
		g2d.transform(shaftRotation);
		g2d.fillRect(0 - width/2, 0 - length/2, width, length);
		g2d.setTransform(saveAT);
	}
}
