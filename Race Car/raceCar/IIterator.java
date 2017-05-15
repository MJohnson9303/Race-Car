package raceCar;

//This interface forces an class that implements it to have the below methods for iterators
public interface IIterator 
{
	public boolean hasNext();
	public Object getNext();
}
