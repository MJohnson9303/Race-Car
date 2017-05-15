package raceCar;

//This class defines the Next Pylon strategy for NPCs.
//NPCs will go to the next pylon when invoked.
public class NextPylonStrategy implements IStrategy 
{
	private String strategyName = "Next Pylon";
	private NPC npc;
	private Pylon pylon;
	private int angle;
	public NextPylonStrategy(NPC npc, Pylon pylon)
	{
		this.npc = npc;
		this.pylon = pylon;
	}
	public String getStrategyName()
	{
		return strategyName;
	}
	public void apply() 
	{
		angle = (int) Math.toDegrees(Math.atan2(pylon.getLocation().getTranslateX() - npc.getLocation().getTranslateX(), pylon.getLocation().getTranslateY() - npc.getLocation().getTranslateY()));
		if(angle < 0)
		{
			angle += 360;
		}
		npc.changeHeading(angle);
	}
}
