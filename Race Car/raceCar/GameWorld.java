package raceCar;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

//This class handles the creation and manipulation of all objects in the game.
public class GameWorld implements IObservable, IGameWorld
{
	//Game state values 
	private int userLives = 3;
	private int gameClock = 0;
	private boolean gameSound;
	//Random object to help create random colors, generate random locations for oil slick and random
	//possible creation of oil slicks
	private Random randomValue = new Random();
	//This integer variable will be used to store randomValue for possible oil slick creation
	private int storeRandom;
	//Color object reference to be used when giving objects in GameWorld new random color (if they are allowed)
	private Color randomColor;
    //This stores user input for the sequence or size number of pylons and fuel cans
    private String inputString;
    //This will be used to set the number of the pylon or fuel can based value stored in inputString
    private int objectNumber;
	//The following objects (to be) are to be used for object manipulations
	private Car c;
	private NPC npc;
	private Pylon p;
	//Creating GameWorldCollection object for collection and indirect accessing of game objects
    private GameWorldCollection gameWorldCollection = new GameWorldCollection();
    //Creating Observer collection object
    private Vector<IObserver> observerCollection = new Vector<IObserver>();
    private Vector<ISelectable> toBeDeletedCollection = new Vector<ISelectable>();
    //Iterator objects to be used to indirectly access the gameWorldCollection
    private IIterator theElements;
    private IIterator theElements2;
    //An object to hold an object from the collection as needed
    private Object holdObject;
    //ICollider objects that will be used to hold an object from the collection for collision detection
    private ICollider currentObject;
    private ICollider otherObject;
    //GameWorldProxy reference for the observers can use to get information from GameWorld, but
    //can't actually alter it
    private GameWorldProxy proxy;
    //GameWorldObjectFactoryObject that will be used to create game world objects as needed
    private GameWorldObjectFactory gwof = new GameWorldObjectFactory();
    //Creating new Sound object set to the path of the game's background music
    private Sound backGroundSound = new Sound("." + File.separator + "sounds" + File.separator + "Boom Background Music.wav");
    //Creating new Sound objects to be played when a player car "dies"
    private Sound carDeathSound = new Sound("." + File.separator + "sounds" + File.separator + "Explosion Collision Lost Life.wav");
    //The Sound object for collisions with a fuel can
    private Sound fuelCanCollisionSound = new Sound("." + File.separator + "sounds" + File.separator + "Fuel Can Slurp.wav");
	//The Sound object for collisions with a npc
	private Sound npcCollisionSound = new Sound("." + File.separator + "sounds" + File.separator + "Crunch NPC Collision.wav");
    //Boolean variable that will determine the game's current mode (true for "play" and false for "pause")
    private boolean gameMode = true;
    private boolean createPylon = false;
    private boolean createFuelCan = false;
	//This private method is used to determine if the game is over
	//Used in carCollision(),birdCollision() and tick
	private void checkUserLives()
	{
		//If userLives become zero, end the game
		if(userLives == 0)
		{
			System.out.println("Game Over");
			System.exit(0);
		}
	}
	//The following methods from getUserLives to getGameSound return their respectively named game state values
	//used by the MapView and ScoreView observers for graphical display
	public int getUserLives()
	{
		return userLives;
	}
	public int getGameClock()
	{
		return gameClock;
	}
	public int getLastPylonReached()
	{
		return c.getLastPylonReached();
	}
	public int getCurrentFuelLevel()
	{
		return c.getFuelLevel();
	}
	public int getCurrentDamageLevel()
	{
		return c.getDamageLevel();
	}
	public void setGameSound(boolean newGameSound)
	{
		gameSound = newGameSound;
		notifyObservers();
	}
	public boolean getGameSound()
	{
		return gameSound;
	}
	//Method that returns the Sound object backGroundSound
	public Sound getBackGroundSound()
	{
		return backGroundSound;
	}
	//Method that sets the game's game mode
	public void setGameMode(boolean newGameMode)
	{
		gameMode = newGameMode;
	}
	//Method that returns the game's current game mode
	public boolean getGameMode()
	{
		return gameMode;
	}
	//Method that sets the flag for a pylon to be created
	public void setCreatePylonFlag(boolean newCreatePylon)
	{
		createPylon = newCreatePylon;
	}
	//Method that gets the flag for a pylon to be created
	public boolean getCreatePylonFlag()
	{
		return createPylon;
	}
	//Method that sets the flag for a fuel can to be created
	public void setCreateFuelCanFlag(boolean newCreateFuelCan)
	{
		createFuelCan = newCreateFuelCan;
	}
	//Method that gets the flag for a fuel can to be created
	public boolean getCreateFuelCanFlag()
	{
		return createFuelCan;
	}
	//Method sets the initial layout of objects in Game World and initializes gameStateValue currentFuelLevel
	public void initLayout()
	{
		gameWorldCollection.add(p = gwof.makePylon(200, 200, 1));
		gameWorldCollection.add(gwof.makePylon(200, 600, 2));
		gameWorldCollection.add(gwof.makePylon(700, 600, 3));
		gameWorldCollection.add(gwof.makePylon(900, 400, 4));
		gameWorldCollection.add(c = gwof.makeCar(200, 200, 20, 40, 0, 70, 50, 10));
		gameWorldCollection.add(npc = gwof.makeNPC(350, 200, 20, 40, 70, 70, 50, 50));
		npc.setStrategy(new DestructionDerbyStrategy(npc,c));
		gameWorldCollection.add(npc = gwof.makeNPC(500, 200, 20, 40, 70, 70, 50, 50));
		npc.setStrategy(new NextPylonStrategy(npc,p));
		gameWorldCollection.add(npc = gwof.makeNPC(650, 200, 20, 40, 70, 70, 50, 50));
		npc.setStrategy(new DestructionDerbyStrategy(npc,c));
		gameWorldCollection.add(gwof.makeBird());
		gameWorldCollection.add(gwof.makeBird());
		gameWorldCollection.add(gwof.makeOilSlick(randomValue.nextInt(950) + 1, randomValue.nextInt(950) + 1));
		gameWorldCollection.add(gwof.makeOilSlick(randomValue.nextInt(950) + 1, randomValue.nextInt(950) + 1));
		gameWorldCollection.add(gwof.makeFuelCan(300, 300, randomValue.nextInt(10) + 1));
		gameWorldCollection.add(gwof.makeFuelCan(100, 400, randomValue.nextInt(10) + 1));
	}
	//Method that increases the speed of the car object 
	public void accelerateCar()
	{
		//Check to enforce car can't go over its maximum speed (if undamaged) and it has fuel and has traction
		if(c.getSpeed() < c.getMaximumSpeed() && c.getFuelLevel() > 0 && c.getTraction() == true)
		{
			//Accelerate the car based on based on damage level percentage
			c.setSpeed(c.getSpeed() + (int)(10*(1-(c.getDamageLevel()/10.0))));
			//If speed of car is over 50 due to various values for acceleration because of damage then enforce the car's maximum speed
			if(c.getSpeed() > c.getMaximumSpeed())
			{
				c.setSpeed(c.getMaximumSpeed());
			}
		}
	}
	//Method that decreases the speed of the car object
	public void brakeCar()
	{
		//Checking if car is either in a oil slick and above 0 in speed
		if(c.getTraction() == true && c.getSpeed() > 0)
		{
			c.setSpeed(c.getSpeed() - 10);
			//This check is to prevent car's speed to being negative
			if(c.getSpeed() < 0)
			{
				c.setSpeed(0);
			}
		}
	}
	//Method that steers car 5 degrees to the left with multiple checks to prevent turns less than 320 degrees among other things inside Car
	public void steerLeft()
	{
		c.setSteeringDirectionLeft();
	}
	//Method that steers car 5 degrees to the right with multiple checks to prevent turns greater than 40 degrees among other things inside Car
	public void steerRight()
	{
		c.setSteeringDirectionRight();
	}
	//Method that adds a random OilSlick into gameWorldCollection 
	public void addOilSlick()
	{
		gameWorldCollection.add(gwof.makeOilSlick(randomValue.nextInt(950) + 1, randomValue.nextInt(950) + 1));
		notifyObservers();
	}
	//Method that randomly gives objects in gameWorldCollection a new color (if they can be changed)
	public void newRandomColors()
	{
		theElements = gameWorldCollection.getIterator();
	    while(theElements.hasNext())
	    {
	    	holdObject = theElements.getNext();
	    	if(holdObject instanceof GameWorldObject)
	        {
	           GameWorldObject gwobj = (GameWorldObject)holdObject;
			   //Create a new color object with random red, green and blue values, then use that new color object to reset all allowed objects' colors
			   randomColor = new Color(randomValue.nextInt(255)+1, randomValue.nextInt(255)+1, randomValue.nextInt(255)+1);
			   gwobj.setColor(randomColor);
	        }
	    }
	    notifyObservers();
	}
	//Adjusting heading of car based on its current steering direction when the clock ticks
	//Used in tick()
	private void adjustCarHeading()
	{
		if(c.getTraction() == true)
		{
			//If steeringDirection is in the positive compass range, then add steeringDirection to the car's heading
			if(c.getSteeringDirection() > 0 && c.getSteeringDirection() <= 40)
			{
				c.changeHeading(c.getHeading() + c.getSteeringDirection());
			}
			//If steeringDirection is in the negative compass range, then subtract steeringDirection from the car's heading
			if(c.getSteeringDirection() >= 320 && c.getSteeringDirection() <= 360)
			{
				c.changeHeading(c.getHeading() - (360 - c.getSteeringDirection()));
			}
			//This handles heading of the car to keep it compass-like(0-359)
			//If the heading is below 0, then add 360 and if it is equal to or above 360,
			//subtract 360
			if(c.getHeading() < 0)
			{
				c.changeHeading(c.getHeading() + 360);
			}
			else if (c.getHeading() >= 360)
			{
				c.changeHeading(c.getHeading() - 360);
			}
		}
	}
	//Method that decrements the car's fuel level, decrements user life if fuel level reaches zero and
	//ends the game if user lives reaches zero
	//Used in tick()
	public void decrementFuelLevel()
	{
		
		c.setFuelLevel(c.getFuelLevel() - 1);
		//Check to see if the car's fuel level is zero and if it is, then decrement userLives, empty gameWorldCollection and reinitialize the collection
  		if(c.getFuelLevel() == 0)
  		{
  			userLives -= 1;
  			gameWorldCollection.removeAll();
  			initLayout();
  		}
  	    //To check if the game is over after possible losing a life due to lack of fuel
  	    checkUserLives();
	}
	//Method that invokes the current strategy of all NPCs that alters their heading
	//Used in tick()
	private void invokeNPCStrategy()
	{
		//Invoking the strategies of all NPCs before they are made to update their positions
		theElements = gameWorldCollection.getIterator();
		while(theElements.hasNext())
		{
			holdObject = theElements.getNext();
			if(holdObject instanceof NPC)
			{
				NPC nobj = (NPC)holdObject;
				nobj.invokeStrategy();
			}
		}
	}
	//Method that has all movable objects to update their positions based on elapsed time or clock cycle
	//Used in tick()
	private void updatePositions(int elapsedTime)
	{
		//Telling all movable objects in gameWorldCollection to update their positions
		theElements = gameWorldCollection.getIterator();
	    while(theElements.hasNext())
	    {
	    	holdObject = theElements.getNext();
	        if(holdObject instanceof MovableObject)
	        {
	           MovableObject mobj = (MovableObject)holdObject;
			   mobj.move(elapsedTime);
	        }
	    }
	}
	//This method checks car damage for each collision and if the car's damage is greater than 10,
	//decrement the user's lives, empty the gameWorldCollection, play a explosion sound
	//Used in tick()
	private void checkCarDamage()
	{
		//If the damage level is greater than or equal to 10, then decrement the user's life, empty out the gameWorldCollection and reinitialize the collection
		if(c.getDamageLevel() >= c.getMaxDamage())
		{
			userLives -= 1;
			gameWorldCollection.removeAll();
			if(gameSound == true)
			{
				carDeathSound.play();
			}
			initLayout();
		}
		checkUserLives();
	}
	//Method that increments in the game clock
	//Used in tick()
	public void incrementClock()
	{
		gameClock += 1;
	}
	//Method that has the car to update its heading based on steering direction, decrement the fuel level of the car, have all movable objects in the
	//collection to move, check for collisions and remove fuel cans if you collide with them and play collision sounds
	//of fuel cans and npcs if gameSound is true and their respective sound flags were set
	public void tick(int elapsedTime)
	{
		adjustCarHeading();
		invokeNPCStrategy();
		updatePositions(elapsedTime);
		//Checking for collisions
		//Two iterators "theElements" and "theElements2" are used to compare two objects in the same collection.
		theElements = gameWorldCollection.getIterator();
		while(theElements.hasNext())
		{
			currentObject = (ICollider)theElements.getNext();
			theElements2 = gameWorldCollection.getIterator();
			while(theElements2.hasNext())
			{
				otherObject = (ICollider)theElements2.getNext();
				//Making sure I am not working with the same object in both iterators.
				if(otherObject != currentObject)
				{
					//Checking for collisions
					if(currentObject.collidesWith(otherObject) == true)
					{
						//Handling collisions based on collided object
						currentObject.handleCollision(otherObject);
						//Checking if sound flags were set for NPCs and FuelCans and play their sounds
						//if gameSound was set to true
						if(currentObject instanceof Car && otherObject instanceof NPC)
						{
							NPC nobj = (NPC)otherObject;
							if(nobj.getAllowSound() == true && gameSound == true)
							{
								npcCollisionSound.play();
								nobj.setAllowSound(false);
								//Generate a oil slick at the car's position in 20% of its collisions with NPCs
								storeRandom = randomValue.nextInt(100) + 1;
								if(storeRandom % 5 == 0 && c.getOilSlickFlag() == true)
								{
									gameWorldCollection.add(gwof.makeOilSlick((float)c.getLocation().getTranslateX(),(float)c.getLocation().getTranslateY()));
									c.setOilSlickFlag(false);
								}
							}
						}
						if(currentObject instanceof Car && otherObject instanceof FuelCan)
						{
							FuelCan fcobj = (FuelCan)otherObject;
							if(fcobj.getAllowSound() == true && gameSound == true)
							{
								fuelCanCollisionSound.play();
								fcobj.setAllowSound(false);
							}
						}
					}
				}
			}
		}
		//Add any objects set to be deleted such as fuel cans into a vector collection
		theElements = gameWorldCollection.getIterator();
		while(theElements.hasNext())
		{
			holdObject = theElements.getNext();
			if(holdObject instanceof FuelCan)
			{
				FuelCan fcobj = (FuelCan)holdObject;
				if(fcobj.getDeleteStatus() == true)
				{
					toBeDeletedCollection.add(fcobj);
				}
			}
		}
		//Go through the collection of objects to be deleted and removed them from the gameWorldCollection
		for(int i = 0; i < toBeDeletedCollection.size(); i++)
		{
			gameWorldCollection.remove(toBeDeletedCollection.elementAt(i));
			gameWorldCollection.add(gwof.makeFuelCan(randomValue.nextInt(950) + 1, randomValue.nextInt(950) + 1, randomValue.nextInt(10) + 1));
		}
		//Clear out the collection
		toBeDeletedCollection.clear();
		checkCarDamage();
		notifyObservers();
	}
	//Method displays the contents of gameWorldCollection
	public void displayGameMap()
	{
		theElements = gameWorldCollection.getIterator();
	    while(theElements.hasNext())
	    {
	       GameWorldObject gwobj = (GameWorldObject) theElements.getNext();
	       System.out.println(gwobj);
	    }
	    System.out.println();
	} 
	//Method that returns gameWorldCollection
    public GameWorldCollection getGameWorldCollection()
    {
    	return gameWorldCollection;
    }
    //This method will get user input for what will be the sequence number or size of a pylon or fuel can
    //that will be created
    private void getUserInput()
    {
    	//Receiving input through JOptionPane input dialog with a prompt for pylon number input
        inputString = JOptionPane.showInputDialog("Please input an integer value");
        //If user clicks "cancel" or the "x" button on the top right corner of the input box, 
        //objectNumber is set to zero which due to a later check will not allow the object to be added
        //and drawn. This is to avoid an error generation.
        if(inputString == null)
        {
        	objectNumber = 0;
        }
        else
        {
        	//If the user does give input, try to parse the string into a integer value and store it, 
        	//but after checking for cases when input is made greater than 99 or less than 0 and if it is either
        	//of those cases, then output a Message Dialog box saying so
        	try 
        	{
        		if(Integer.parseInt(inputString) == 0)
        		{
        			JOptionPane.showMessageDialog(null, "Give a integer value greater than 0");
        			getUserInput();
        		}
        		else if(Integer.parseInt(inputString) < 0)
        		{
        			JOptionPane.showMessageDialog(null, "Give a positive integer value");
        			getUserInput();
        		}
        		else if(Integer.parseInt(inputString) > 99)
        		{
        			JOptionPane.showMessageDialog(null, "Give a integer less than 100");
        			getUserInput();
        		}
        		else
        		{
        			objectNumber = Integer.parseInt(inputString);
        		}
        	} 
        	//If the user gives invalid input such as putting letters or letters and numbers in the input, then
        	//display a message
        	catch (NumberFormatException n) 
        	{
        	    JOptionPane.showMessageDialog(null, "Give an integer value");
        	}
        }
    }
    //Method that highlights selected objects when they are click on in pause mode
    //If the control key is being held down, multiple objects can be selected.
    //Else if the control key is not being held down, then one a object is selected to be highlighted,
    //then all other objects become non-selected.
    //If either the add pylon or fuel buttons are clicked on and the user clicks
    //on the map, then the user is asked for input and a pylon or fuel can is created
    //at the clicked on map point with the given value.
    public void setGameObjectSelect(Point2D p, boolean controlHeld)
    {
    	if(gameMode == false)
    	{
	    	theElements = gameWorldCollection.getIterator();
	    	while(theElements.hasNext())
	    	{
	    		holdObject = theElements.getNext();
		        if(holdObject instanceof ISelectable)
		        {
		           ISelectable isobj = (ISelectable)holdObject;
				   if(isobj.contains(p) && controlHeld == true)
				   {
					   isobj.setSelected(true);
				   }
				   else if(controlHeld == false)
				   {
					   if(isobj.contains(p))
					   {
						   isobj.setSelected(true);
					   }
					   else
					   {
						   isobj.setSelected(false);
					   }		
				   }
		        }
	    	}
    	}
    	if(getCreatePylonFlag() == true)
		{
			getUserInput();
			if(objectNumber != 0)
			{
				createPylon((float)p.getX(), (float)p.getY(), objectNumber);
			}
			
			//Resetting the create flag to false, so pylon creation doesn't keep occuring. 
			setCreatePylonFlag(false);
		}
		else if(getCreateFuelCanFlag() == true)
		{
			getUserInput();
			if(objectNumber != 0)
			{
				createFuelCan((float)p.getX(), (float)p.getY(), objectNumber);	
			}
			//Resetting the create flag to false, so fuel can creation doesn't keep occuring.
			setCreateFuelCanFlag(false);
		}
    }
    //Method that creates and adds a pylon into the collection
    public void createPylon(float x, float y, int sequenceNumber)
    {
    	gameWorldCollection.add(gwof.makePylon(x, y, sequenceNumber));
    }
    //Method that creates and adds a fuel can into the collection
    public void createFuelCan(float x, float y, int size)
    {
    	gameWorldCollection.add(gwof.makeFuelCan(x, y, size));
    }
    //Method that deletes selected objects from the game
    public void deleteSelectedObject()
    {
    	//Any selected objects are entered into a collection for later deletion
    	theElements = gameWorldCollection.getIterator();
    	while(theElements.hasNext())
    	{
    		holdObject = theElements.getNext();
	        if(holdObject instanceof ISelectable)
	        {
	           ISelectable isobj = (ISelectable)holdObject;
			   if(isobj.isSelected() == true)
			   {
				   toBeDeletedCollection.add(isobj);
			   }
	        }
    	}
    	//From the "toBeDeletedCollection" from it's collected objects from the gameWorldCollection
    	for(int i = 0; i <  toBeDeletedCollection.size(); i++)
    	{
    		gameWorldCollection.remove(toBeDeletedCollection.elementAt(i));
    		notifyObservers();
    	}
    	//Then clear the "toBeDeletedCollection" after it is done to avoid errors
    	toBeDeletedCollection.clear();
    }
    //This method is used to unselect all selectable objects in play mode
    public void unselectAll()
    {
    	theElements = gameWorldCollection.getIterator();
    	while(theElements.hasNext())
    	{
    		holdObject = theElements.getNext();
    		if(holdObject instanceof ISelectable)
    		{
    			ISelectable isobj = (ISelectable)holdObject;
    			isobj.setSelected(false);
    		}
    	}
    }
	//Method adds object into observer collection
    public void addObserver(IObserver obs)
    {
       observerCollection.add(obs);
    }
    //Method goes through the collection of observers and has them call their update method
    public void notifyObservers()
    {
    	proxy = new GameWorldProxy(this);
        for(IObserver iobj : observerCollection)
        {
           iobj.update((IObservable)proxy, null);
        }
    }
}
