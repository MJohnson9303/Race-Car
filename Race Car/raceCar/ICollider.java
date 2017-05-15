package raceCar;
//An interface that requires classes that implement it to detect and handle collisions
public interface ICollider 
{
	public boolean collidesWith(ICollider otherObject);
	public void handleCollision(ICollider otherObject);
}
