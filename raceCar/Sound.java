package raceCar;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
//This class allows the use of certain sound file types like .wav to be used in the game.
public class Sound 
{
	AudioClip soundClip;
	public Sound(String fileName)
	{
		try
		{
			File file = new File(fileName);
			if(file.exists())
			{
				soundClip = Applet.newAudioClip(file.toURI().toURL());
			}
			else
			{
				throw new RuntimeException("File not found: " + fileName);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Problem with " +fileName + ": " + e);
		}
	}
	public void play()
	{
		soundClip.play();
	}
	public void loop()
	{
		soundClip.loop();
	}
	public void stop()
	{
		soundClip.stop();
	}
}
