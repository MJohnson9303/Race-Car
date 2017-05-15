package raceCar;

//Class that implements the Factory Method design pattern, so it handles the creations of all the objects I need
public class GameWorldObjectFactory
{
	public Pylon makePylon(float x, float y, int sequenceNumber)
	{
		return new Pylon(x, y, sequenceNumber);
	}
	public OilSlick makeOilSlick(float x, float y)
	{
		return new OilSlick(x, y);
	}
	public FuelCan makeFuelCan(float x, float y, int size)
	{
		return new FuelCan(x, y, size);
	}
	public Bird makeBird()
	{
		return new Bird();
	}
	public Car makeCar(float x, float y, int width, int length, int speed, int maxSpeed, int fuelLevel, int maxDamageLevel)
	{
		return new Car(x, y, width, length, speed, maxSpeed, fuelLevel, maxDamageLevel);
	}
	public NPC makeNPC(float x, float y, int width, int length, int speed, int maxSpeed, int fuelLevel, int maxDamageLevel)
	{
		return new NPC(x, y, width, length, speed, maxSpeed, fuelLevel, maxDamageLevel);
	}
}
