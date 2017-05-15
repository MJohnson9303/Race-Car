package raceCar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//Observer class that observes GameWorld for game state value changes and displays changes.
public class MapView extends JPanel implements IObserver, MouseListener, MouseMotionListener, MouseWheelListener
{
    private IGameWorld gameWorldProxy;
    private IIterator theElements;
    private GameWorldCollection gameWorldCollection;
    private Object holdObject;
    private AffineTransform worldToND, ndToScreen, theVTM;
    private AffineTransform inverseVTM;
    private AffineTransform saveAT;
    private AffineTransform pND;
    //The boundaries of the MapView window
    private float windowLeft = 0;
    private float windowRight = 1000;
    private float windowTop = 800;
    private float windowBottom = 0;
    private float windowWidth = 1000;
    private float windowHeight = 800;
    private Point2D mouseWorldLocation;
    //This stores the location of the mouse click and passes it into GameWorld's setGameObjectSelect()
    private Point2D mouseScreenLocation;
    //This stores the current location of the mouse after it is dragged
    private Point2D currentMouseScreenLocation;
    //This will be used to store the boolean value of whether the control key is held when
    //an object is selected in MapView and pass that value into GameWorld's setGameObjectSelect()
    private boolean controlKeyHeld;
    //Constructor that creates a new mapPanel, sets a black border around it and sets the panel's size
    public MapView()
    {
        setBorder(new LineBorder(Color.black, 2));
        worldToND = new AffineTransform();
        ndToScreen = new AffineTransform();
        theVTM = new AffineTransform();
        saveAT = new AffineTransform();
        mouseScreenLocation = new Point2D.Double(0, 0);
        currentMouseScreenLocation = new Point2D.Double(0, 0);
        mouseWorldLocation = new Point2D.Double(0, 0);
        //Attaching mouse listener, mouse motion listener and mouse wheel listener to the MapView panel
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }
    //This method performs a "zoom in" of the GameWorld window
    private void zoomIn()
    {
    	windowHeight = windowTop - windowBottom;
    	windowWidth = windowRight - windowLeft;
    	windowLeft += windowWidth * 0.05;
    	windowRight -= windowWidth * 0.05;
    	windowTop -= windowHeight * 0.05;
    	windowBottom += windowHeight * 0.05;
    	
    }
    //This method performs a "zoom out" of the GameWorld window
    private void zoomOut()
    {
    	windowHeight = windowTop - windowBottom;
    	windowWidth = windowRight - windowLeft;
    	windowLeft -= windowWidth * 0.05;
    	windowRight += windowWidth * 0.05;
    	windowTop += windowHeight * 0.05;
    	windowBottom -= windowHeight * 0.05;
    }
    //This method performs a "pan left" of the GameWorld window
    private void panLeft()
    {
    	windowLeft -= 30;
    	windowRight -= 30;
    }
    //This method performs a "pan right" of the GameWorld window
    private void panRight()
    {
    	windowLeft += 30;
    	windowRight += 30;
    }
    //This method performs a "pan up" of the GameWorld window
    private void panUp()
    {
    	windowTop += 30;
    	windowBottom += 30;
    }
    //This method performs a "pan down" of the GameWorld window
    private void panDown()
    {
    	windowTop -= 30;
    	windowBottom -= 30;
    }
    //This method will handle the drawing of all the objects inside the MapView panel
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	Graphics2D g2d = (Graphics2D)g;
    	saveAT = g2d.getTransform();
    	worldToND = buildWorldToNDXform(windowWidth, windowHeight, windowLeft, windowBottom);
    	ndToScreen = buildNDToScreenXform((float)this.getWidth(), (float)this.getHeight());
    	theVTM = (AffineTransform) ndToScreen.clone();
    	theVTM.concatenate(worldToND);
    	g2d.transform(theVTM);
    	theElements = gameWorldCollection.getIterator();
    	while(theElements.hasNext())
    	{
    		holdObject = theElements.getNext();
    		if(holdObject instanceof IDrawable)
    		{
    			IDrawable idobj = (IDrawable)holdObject;
    			idobj.draw(g2d);
    		}
    	}
    	g2d.setTransform(saveAT);
    }
    //This method returns a world to normal device affine transform
    private AffineTransform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom)
    {
    	pND = new AffineTransform();
    	pND.scale(1/winWidth, 1/winHeight);
    	pND.translate(-1*winLeft, -1*winBottom);
    	return pND;
    }
    //This method returns a normal device to screen affine transform
    private AffineTransform buildNDToScreenXform(float width, float height)
    {
    	pND = new AffineTransform();
    	pND.translate(0, height);
    	pND.scale(width, -1*height);
    	return pND;	
    }
    //Update method that displays current positions of game objects when a change occurs
    public void update(IObservable o, Object arg)
    {
        gameWorldProxy = (GameWorldProxy)o;
        gameWorldCollection = gameWorldProxy.getGameWorldCollection();
        this.repaint();
    }
    //Getting the mouse location in each click and passing along the information
    //as well as if the CONTROL key is held to the GameWorld during "play" mode.
	public void mouseClicked(MouseEvent e) 
	{
		inverseVTM = new AffineTransform();
		try
    	{
    		inverseVTM = theVTM.createInverse();
    	}
    	catch (NoninvertibleTransformException arg)
    	{
    		
    	}
		mouseScreenLocation = e.getPoint();
		mouseWorldLocation = inverseVTM.transform(mouseScreenLocation, null);
		controlKeyHeld = e.isControlDown();
		gameWorldProxy.setGameObjectSelect(mouseWorldLocation, controlKeyHeld);
		repaint();
	}
	public void mouseEntered(MouseEvent e) 
	{
		
	}
	public void mouseExited(MouseEvent e) 
	{
		
	}
	//Getting the location of each mouse press
	public void mousePressed(MouseEvent e) 
	{
		mouseScreenLocation = e.getPoint();
	}
	public void mouseReleased(MouseEvent e) 
	{
		
	}
	//If in "play" mode, allow the use to drag the mouse and pan around the GameWorld
	//using the mouse
	public void mouseDragged(MouseEvent e) 
	{
		if(gameWorldProxy.getGameMode() == true)
		{
			currentMouseScreenLocation = e.getPoint();
			if(mouseScreenLocation.getY() < currentMouseScreenLocation.getY())
			{
				panUp();
			}
		    if(mouseScreenLocation.getY() > currentMouseScreenLocation.getY())
			{
				panDown();
			}
			if(mouseScreenLocation.getX() < currentMouseScreenLocation.getX())
			{
				panLeft();
			}
			if (mouseScreenLocation.getX() > currentMouseScreenLocation.getX())
			{
				panRight();
			}
		}
	}
	public void mouseMoved(MouseEvent e) 
	{
		
	}
	//If in "play" mode, allow the user to zoom in and out of the GameWorld
	//using the Mouse Wheel
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		if(gameWorldProxy.getGameMode() == true)
		{
			int notches = e.getWheelRotation();
	        if (notches < 0) 
	        {
	            zoomIn();
	        } 
	        else 
	        {   
	            zoomOut();
	        }
		}
	}
}
