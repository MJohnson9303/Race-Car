package raceCar;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//The class handles the body component of a car
public class CarBody implements IDrawable
{
	private int length;
	private int width;
	private AffineTransform bodyTranslation;
	private AffineTransform bodyRotation;
	private AffineTransform saveAT;
	public CarBody(int width,int length)
	{
		this.length = length;
		this.width = width;
		bodyTranslation = new AffineTransform();
		bodyRotation = new AffineTransform();
		saveAT = new AffineTransform();
	}
	//This method translates the body of a car
	public void translate(double x, double y)
	{
		bodyTranslation.translate(x, y);
	}
	//This method sets the translation affine transform matrix to the identity matrix
	public void setTranslateToIdentity()
	{
		bodyTranslation.setToIdentity();
	}
	//This method returns the body translation affine transform object of a car
	public AffineTransform getTranslation()
	{
		return bodyTranslation;
	}
	//This method rotates the body of a car
	public void rotate(double degrees)
	{
		bodyRotation.rotate(degrees);
	}
	//This method sets the rotation affine transform matrix to the identity matrix
	public void setRotationToIdentity()
	{
		bodyRotation.setToIdentity();
	}
	//This method returns the body rotation affine transform object of a car
	public AffineTransform getRotation()
	{
		return bodyRotation;
	}
	//This method draws the body of a car based on it's local and passed in transformations
	public void draw(Graphics2D g2d) 
	{
		saveAT = g2d.getTransform();
		g2d.transform(bodyTranslation);
		g2d.transform(bodyRotation);
		g2d.fillRect(0 - width/2, 0 - length/2, width, length);
		g2d.setTransform(saveAT);
	}
}
