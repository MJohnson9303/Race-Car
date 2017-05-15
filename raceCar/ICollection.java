package raceCar;

//This interface forces collection classes that implement it to implement the below methods
public interface ICollection 
{
    public void add(Object newObject);
    public void remove(Object gameObject);
    public void removeAll();
	public IIterator getIterator();
}
