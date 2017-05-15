package raceCar;

//Strategy interface that demands that all classes that implement this strategy must provide methods to
//provide their strategy name and an "apply()" which will affect behavior in run-time
public interface IStrategy 
{
	public String getStrategyName();
	public void apply();
}
