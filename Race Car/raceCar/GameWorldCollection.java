package raceCar;

import java.util.Vector;

//This class handles collection of objects and requires an iterator to go through it
public class GameWorldCollection implements ICollection
{
   private Vector<Object> gameCollection;
   
   public GameWorldCollection()
   {
      gameCollection = new Vector<Object>();
   }
   //Method that adds objects to the collection
   public void add(Object newObject)
   {
      gameCollection.addElement(newObject);
   }
   //Method that removes objects from the collection
   public void remove(Object gameObject)
   {
      gameCollection.remove(gameObject);
   }
   //Method that removes all objects from the collection
   public void removeAll()
   {
      gameCollection.clear();
   }
   //Method returns a created GameVectorIterator object
   public IIterator getIterator()
   {
      return new GameVectorIterator();
   }
   //This private class implements the iterator required to go through the collection
   private class GameVectorIterator implements IIterator
   {
      private int currentElementIndex;
      public GameVectorIterator()
      {
         currentElementIndex = -1;
      }
      //Method that serves as a check to prevent someone from going out of bounds of the collection
      public boolean hasNext()
      {
         if(gameCollection.size() <= 0)
         {
            return false;
         }
         if(currentElementIndex == gameCollection.size() - 1)
         {
            return false;
         }
         return true;
      }
      //Method that returns the next object stored inside the collection
      public Object getNext()
      {
    	  currentElementIndex++;
    	  return (gameCollection.elementAt(currentElementIndex));
      }
   }
}