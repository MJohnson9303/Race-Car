package raceCar;

import java.awt.geom.Point2D;

//This interface is to enforce all methods that GameWorld and any other class that implements it must perform.
public interface IGameWorld
{
	//The following methods from getUserLives to getGameSound return their respectively named game state values
	public int getUserLives();
	public int getGameClock();
	public int getLastPylonReached();
	public int getCurrentFuelLevel();
	public int getCurrentDamageLevel();
	public void setGameSound(boolean newGameSound);
	public boolean getGameSound();
	//Method that sets the game's game mode
	public void setGameMode(boolean newGameMode);
	//Method that returns the game's current game mode
	public boolean getGameMode();
	//Method that sets the flag for a pylon to be created
	public void setCreatePylonFlag(boolean newCreatePylon);
	//Method that gets the flag for a pylon to be created
	public boolean getCreatePylonFlag();
	//Method that sets the flag for a fuel can to be created
	public void setCreateFuelCanFlag(boolean newCreateFuelCan);
	//Method that gets the flag for a fuel can to be created
	public boolean getCreateFuelCanFlag();
	//Method sets the initial layout of objects in Game World and initializes gameStateValue currentFuelLevel
	public void initLayout();
	//Method that increases the speed of the car object
	public void accelerateCar();
	//Method that decreases the speed of the car object 
	public void brakeCar();
	//Method that steers car 5 degrees to the left with multiple checks to prevent turns less than 320 degrees among other things inside Car
	public void steerLeft();
	//Method that steers car 5 degrees to the right with multiple checks to prevent turns greater than 40 degrees among other things inside Car
	public void steerRight();
	//Method that adds a random OilSlick into gameWorldCollection 
	public void addOilSlick();
	//Method that randomly gives objects in gameWorldCollection a new color (if they can be changed)
	public void newRandomColors();
	//Method that decrements the car's fuel level, decrements user life if fuel level reaches zero and
	//ends the game if user lives reaches zero
	//Used in tick()
	public void decrementFuelLevel();
	//Method that increments the game clock
	public void incrementClock();
	//Method that has the car to update its heading based on steering direction, decrement the fuel level of the car, have all moveable objects in the
	//collection to move and increment the game clock 
	public void tick(int timerCycle);
	//Method displays the contents of gameWorldCollection
	public void displayGameMap(); 
	//Method that returns the gameWorldCollection
	public GameWorldCollection getGameWorldCollection();
	//Method that highlights selected objects when they are click on in pause mode
    //If the control key is being held down, multiple objects can be selected.
    //Else if the control key is not being held down, then one a object is selected to be highlighted,
    //then all other objects become non-selected.
    public void setGameObjectSelect(Point2D p, boolean controlHeld);
    //Method that creates and adds a pylon into the collection
    public void createPylon(float x, float y, int sequenceNumber);
    //Method that creates and adds a fuel can into the collection
    public void createFuelCan(float x, float y, int size);
    //Method that deletes selected objects from the game
    public void deleteSelectedObject();
    //This method is used to unselect all selectable objects in play mode
    public void unselectAll();
}
