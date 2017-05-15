package raceCar;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//Observer class that observes GameWorld for game state value changes and displays changes within the GUI frame in Game
public class ScoreView extends JPanel implements IObserver
{
   private IGameWorld gameWorldProxy;
   private JLabel timeLabel = new JLabel();
   private JLabel livesLabel = new JLabel();
   private JLabel pylonLabel = new JLabel();
   private JLabel fuelLabel = new JLabel();
   private JLabel damageLabel = new JLabel();
   private JLabel soundLabel = new JLabel();
   private int timeCount;
   //Constructor that creates adds labels into the ScoreView panel and sets a black border around it
   public ScoreView()
   {
      add(timeLabel);
      add(livesLabel);
      add(pylonLabel);
      add(fuelLabel);
      add(damageLabel);
      add(soundLabel);
      setBorder(new LineBorder(Color.black, 2));
   }
   //Update method that sets the text inside the scorePanel to current game state information
   public void update(IObservable o, Object arg)
   {
      gameWorldProxy = (GameWorldProxy)o;
      timeLabel.setText("Time: "+gameWorldProxy.getGameClock());
      livesLabel.setText("Lives: "+gameWorldProxy.getUserLives());
      pylonLabel.setText("Highest Player Pylon: "+gameWorldProxy.getLastPylonReached());
      fuelLabel.setText("Player Fuel Remaining: "+gameWorldProxy.getCurrentFuelLevel());
      damageLabel.setText("Player Damage Level: "+gameWorldProxy.getCurrentDamageLevel());
      soundLabel.setText("Sound: "+gameWorldProxy.getGameSound());
      if(gameWorldProxy.getGameSound() == true)
      {
         soundLabel.setText("Sound: ON");
      }
      else
      {
         soundLabel.setText("Sound: OFF");
      }
      timeCount += 1;
      //In 50 update calls which occurs in every tick caused by the Game class,
      //update the game clock and decrease the car fuel level.
      if(timeCount % 50 == 0)
      {
	      gameWorldProxy.decrementFuelLevel();
	      gameWorldProxy.incrementClock();
	      timeCount = 0;
      }
   }
}