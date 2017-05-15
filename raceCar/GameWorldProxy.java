package raceCar;

import java.awt.geom.Point2D;

//This class is to act as a proxy for GameWorld to be passed to it's observers and prevent those observers from
//using methods not needed to obtain game state information.
public class GameWorldProxy implements IObservable, IGameWorld
{
	private GameWorld realGameWorld;
	
    public GameWorldProxy(GameWorld gw)
    {
       realGameWorld = gw;
    }
	public int getUserLives() 
	{
		return realGameWorld.getUserLives();
	}
	public int getGameClock() 
	{
		return realGameWorld.getGameClock();
	}
	public int getLastPylonReached() 
	{
		return realGameWorld.getLastPylonReached();
	}
	public int getCurrentFuelLevel() 
	{
		return realGameWorld.getCurrentFuelLevel();
	}
	public int getCurrentDamageLevel() 
	{
		return realGameWorld.getCurrentDamageLevel();
	}
	public void setGameSound(boolean newGameSound) 
	{
		throw new RuntimeException("Observer can not alter the model");	
	}
	public boolean getGameSound() 
	{
		return realGameWorld.getGameSound();
	}
	//Method that sets the game's game mode
	public void setGameMode(boolean newGameMode)
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	//Method that returns the game's current game mode
	public boolean getGameMode()
	{
		return realGameWorld.getGameMode();
	}
	//Method that sets the flag for a pylon to be created
	public void setCreatePylonFlag(boolean newCreatePylon)
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	//Method that gets the flag for a pylon to be created
	public boolean getCreatePylonFlag()
	{
		return realGameWorld.getCreatePylonFlag();
	}
	//Method that sets the flag for a fuel can to be created
	public void setCreateFuelCanFlag(boolean newCreateFuelCan)
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	//Method that gets the flag for a fuel can to be created
	public boolean getCreateFuelCanFlag()
	{
		return realGameWorld.getCreateFuelCanFlag();
	}
	public void initLayout() 
	{
		throw new RuntimeException("Observer can not alter the model");	
	}
	public void accelerateCar() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void brakeCar() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void steerLeft() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void steerRight() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void addOilSlick() 
	{
		throw new RuntimeException("Observer can not alter the model");	
	}
	public void newRandomColors() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void decrementFuelLevel()
	{
		realGameWorld.decrementFuelLevel();
	}
	public void incrementClock()
	{
		realGameWorld.incrementClock();
	}
	public void tick(int timerCycle) 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public GameWorldCollection getGameWorldCollection() 
	{
		return realGameWorld.getGameWorldCollection();
	}
	public void displayGameMap() 
	{
		realGameWorld.displayGameMap();
	}
	public void setGameObjectSelect(Point2D p, boolean controlHeld) 
	{
		realGameWorld.setGameObjectSelect(p, controlHeld);
	}
	public void createPylon(float x, float y, int sequenceNumber) 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void createFuelCan(float x, float y, int size) 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void deleteSelectedObject() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void unselectAll() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void addObserver(IObserver obs) 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
	public void notifyObservers() 
	{
		throw new RuntimeException("Observer can not alter the model");
	}
}
