package raceCar;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

//This class defines a command that defines a switch strategy command. 
public class SwitchStrategyCommand extends AbstractAction
{
	//Iterator objects to be used to indirectly access the gameWorldCollection
    private IIterator theElements;
    private IIterator theElements2;
    //GameWorld object needed to obtain the collection of GameWorld and notify observers when a NPC's strategy changes
    private GameWorld gameWorld;
    //gameWorldCollection object needed to handle the multiple pylons that a NPC may be commanded to head toward
    private GameWorldCollection gameWorldCollection;
    //Temporary storage objects needed to access the methods of the objects in gameWorldCollection
    private Object holdObject;
    private Object holdObject2;
    private Object holdObject3;
	private static SwitchStrategyCommand switchStrategyCommand;
	
    private SwitchStrategyCommand()
    {
       super("Switch Strategy");
    }
    //Method that creates a new instance of SwitchStraegyCommand and if it is already created, return the same
    //CarCollisionCommand to any object that calls for it
    public static SwitchStrategyCommand getSwitchStrategy()
    {
    	if(switchStrategyCommand == null)
	    {
		    switchStrategyCommand = new SwitchStrategyCommand();
	    }
	    return switchStrategyCommand;
    }
    //Setting target for command that will allow it to access  specific methods when the command is invoked
    //as well as storing a copy of GameWorld's collection of game objects
    public void setTarget(GameWorld gameWorld)
    {
    	this.gameWorld = gameWorld;
    	gameWorldCollection = gameWorld.getGameWorldCollection();
    }
    public void actionPerformed(ActionEvent e)
    {
    	//Iterate through the collection and if an object is an NPC, perform checks on its current strategy
		theElements = gameWorldCollection.getIterator();
		while(theElements.hasNext())
		{
			holdObject = theElements.getNext();
			if(holdObject instanceof NPC)
			{
				NPC nobj = (NPC)holdObject;
				//If an NPC's current strategy is DestructionDerbyStrategy, then iterate through the collection
				//until a pylon whose sequence number is greater than the NPC's lastPylonReached variable by 1.
				//Store the found pylon object into a temporary Object storage variable, holdObject2. 
				//Set the new strategy to NextPylonStrategy passing the current NPC in the outer loop and a 
				//holdObject2 type casted to Pylon and notify the observers of GameWorld of this change
				if(nobj.getStrategy() instanceof DestructionDerbyStrategy)
				{
					theElements2 = gameWorldCollection.getIterator();
					while(theElements2.hasNext())
					{
						holdObject2 = theElements2.getNext();
						if(holdObject2 instanceof Pylon)
						{
							Pylon pobj = (Pylon)holdObject2;
							if(pobj.getSequenceNumber() - nobj.getLastPylonReached() == 1)
							{
								holdObject3 = pobj;
								//Break statement here to avoid other objects from accidently being passed into the
								//current NPC's setStrategy method. Another possible solution is using a third storage
								//object to catch the found pylon and pass that into the NPC's invoked method below.
								break;
							}
						}
					}
					nobj.setStrategy(new NextPylonStrategy(nobj,(Pylon)holdObject3));
					gameWorld.notifyObservers();
				}
				//If an NPC's current strategy is the NextPylonStrategy, then iterate through the collection and 
				//when the first object of type Car is found break the loop. Set the new strategy to
				//DestructionDerbyStrategy passing the current NPC in the outer loop and a holdObject2 
				//type casted to Car and notify the observers of GameWorld of this change
				else if(nobj.getStrategy() instanceof NextPylonStrategy)
				{
					theElements2 = gameWorldCollection.getIterator();
					while(theElements2.hasNext())
					{
						holdObject2 = theElements2.getNext();
						if(holdObject2 instanceof Car)
						{
							break;
						}
					}
					nobj.setStrategy(new DestructionDerbyStrategy(nobj, (Car)holdObject2));
					//For each switch between strategies each NPC has its "lastPylonReached" integer variable 
					//incremented by 1
					nobj.setLastPylonReached(nobj.getLastPylonReached() + 1);
					gameWorld.notifyObservers();
				}
			}
		}
    }
}
