package raceCar;

//This IStrategy class will cause a NPC to chase and collide with the player car.
public class DestructionDerbyStrategy implements IStrategy 
{
	private String strategyName = "Destruction Derby";
	private NPC npc;
	private Car car;
	private int angle;
	public DestructionDerbyStrategy(NPC npc, Car car)
	{
		this.npc = npc;
		this.car = car;
	}
	public String getStrategyName() 
	{
		return strategyName;
	}
	public void apply() 
	{
		angle = (int) Math.toDegrees(Math.atan2(car.getLocation().getTranslateX() - npc.getLocation().getTranslateX(), car.getLocation().getTranslateY() - npc.getLocation().getTranslateY()));
		if(angle < 0)
		{
			angle += 360;
		}
		npc.changeHeading(angle);
	}
}
