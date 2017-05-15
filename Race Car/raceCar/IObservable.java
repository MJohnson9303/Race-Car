package raceCar;

//Observable interface with undefined methods to be defined and used by classes to be observed by their respective observers
public interface IObservable
{
   public void addObserver(IObserver obs);
   public void notifyObservers();
}