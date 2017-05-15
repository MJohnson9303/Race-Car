package raceCar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

//This class will execute commands based on user input.
public class Game extends JFrame implements ActionListener
{
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	//Private JMenuBar, JMenu, JMenuItem, JCheckbox,JPanel, JButton, InputMap, ActionMap and KeyStroke objects
	//needed for building a GUI interface and key binding for user interaction with the program
	private InputMap imap;
    private KeyStroke downKey;
	private KeyStroke upKey;
	private KeyStroke rightKey;
	private KeyStroke leftKey;
	private KeyStroke oilSlickKey;
	private KeyStroke quitKey;
	private KeyStroke switchStrategyKey;
	private KeyStroke deleteKey;
	private ActionMap amap;
	//AbstractAction command objects to attach to File menu items, Command menu items and keys
    private AboutCommand aboutCommand;
    private QuitCommand quitCommand;
    private SoundCommand soundCommand;
    private NewColorsCommand newColorsCommand;
    private AddOilSlickCommand addOilSlickCommand;
    private SwitchStrategyCommand switchStrategyCommand;
    private TickCommand tickCommand;
    private BrakeCarCommand brakeCarCommand;
    private AccelerateCarCommand accelerateCarCommand;
    private SteerCarRightCommand steerCarRightCommand;
    private SteerCarLeftCommand steerCarLeftCommand;
    private GameModeCommand gameModeCommand;
    private DeleteSelectedObjectCommand deleteSelectedObjectCommand;
    private AddPylonCommand addPylonCommand;
    private AddFuelCanCommand addFuelCanCommand;
    //Timer object to be used for object animation
    private Timer timer; 
    //This integer variable will be the time cycle for the timer.
    //Set to 20 milliseconds.
    private int elapsedTime = 20;
    //This integer variable will be used to keep track of how many times the Timer object "time"
    //signals an ActionEvent to determine when to switch the strategies of the NPCs
    private int timeCount = 0;
	//Construct game world and initialize the layout of the game
	public Game()
	{
		gw = new GameWorld();
		mv = new MapView();
		sv = new ScoreView();
		//Adding observers
		gw.addObserver(mv);
	    gw.addObserver(sv);
	    //Initializing game world.
		gw.initLayout();
		//Notifying observers so everything is displayed on the GUI frame
		gw.notifyObservers();
		//Creating new instance of timer object with it cycling every 1000 milliseconds and Game
        //as the ActionListener
        timer = new Timer(elapsedTime, this);
		//Setting title on GUI frame
		this.setTitle("Race Car!");
		//Setting size of GUI frame
		this.setSize(1000, 800);
		//Setting "x" button operation to close program on default
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Constructing command objects to be used by GUI buttons and menu items indirectly using the Simpleton Design Pattern
		//This is to ensure that no more than 1 instance exists at a time
	    aboutCommand = AboutCommand.getAbout();
	    quitCommand = QuitCommand.getQuit();
	    soundCommand = SoundCommand.getSound();
	    newColorsCommand = NewColorsCommand.getNewColors();
	    addOilSlickCommand = AddOilSlickCommand.getAddOilSlick();
	    switchStrategyCommand = SwitchStrategyCommand.getSwitchStrategy();
	    tickCommand = TickCommand.getTick();
	    brakeCarCommand = BrakeCarCommand.getCarBrake();
	    accelerateCarCommand = AccelerateCarCommand.getCarAccelerate();
	    steerCarRightCommand = SteerCarRightCommand.getSteerCarRight();
	    steerCarLeftCommand = SteerCarLeftCommand.getSteerCarLeft();
	    gameModeCommand = GameModeCommand.getGameMode();
	    deleteSelectedObjectCommand = DeleteSelectedObjectCommand.getDeleteSelectedObject();
	    addPylonCommand = AddPylonCommand.getAddPylon();
	    addFuelCanCommand = AddFuelCanCommand.getAddFuelCan();
	    //Setting targets for command objects
	    soundCommand.setTarget(gw);
	    newColorsCommand.setTarget(gw);
	    addOilSlickCommand.setTarget(gw);
	    switchStrategyCommand.setTarget(gw);
	    tickCommand.setTarget(gw);
	    brakeCarCommand.setTarget(gw);
	    accelerateCarCommand.setTarget(gw);
	    steerCarRightCommand.setTarget(gw);
	    steerCarLeftCommand.setTarget(gw);
	    gameModeCommand.setTarget(timer, this, gw);
	    deleteSelectedObjectCommand.setTarget(gw);
	    addPylonCommand.setTarget(gw);
	    addFuelCanCommand.setTarget(gw);
		//Creating and attaching menu bar to GUI
        JMenuBar bar = createMenuBar();
        this.setJMenuBar(bar);
        //Creating and attaching button panel to the GUI
        buttonPanelSetUp();
        //Attaching mouse listener to the MapView panel
        //mv.addMouseListener(this);
        //Setting the delete, add pylon and add fuel can buttons to be initially locked
	    playModeBinding();
        //Starting the timer
        timer.start();
		//Making GUI visible
	    this.setVisible(true);
	}
	//Private method that creates a menu bar with two menus and multiple menu items and returns the menu bar
    private JMenuBar createMenuBar()
    {
       JMenuBar bar = new JMenuBar();
       JMenu fileMenu = new JMenu("File");
       JMenuItem fileNew = new JMenuItem("New");
       fileMenu.add(fileNew);
       JMenuItem fileSave = new JMenuItem("Save");
       fileMenu.add(fileSave);
       JCheckBox soundCheckBox = new JCheckBox("Sound");
       fileMenu.add(soundCheckBox);
       JMenuItem fileAbout = new JMenuItem("About");
       fileMenu.add(fileAbout);
       JMenuItem fileQuit = new JMenuItem("Quit");
       fileMenu.add(fileQuit);
       bar.add(fileMenu);
       JMenu commandMenu = new JMenu("Commands");
       JMenuItem oilSlickCommand = new JMenuItem("Add Oil Slick");
       commandMenu.add(oilSlickCommand);
       JMenuItem colorsCommand = new JMenuItem("New Colors");
       commandMenu.add(colorsCommand);
       bar.add(commandMenu);
       //Attaching AbstractAction commands to menu items
       soundCheckBox.setAction(soundCommand);
       fileAbout.setAction(aboutCommand);
       fileQuit.setAction(quitCommand);
       oilSlickCommand.setAction(addOilSlickCommand);
       colorsCommand.setAction(newColorsCommand);
       return bar;
    }
    //Private method that adds three panels to the GUI frame as well as adding buttons to one of those panels.
    //It also adds action listeners and key listeners for those buttons
    private void buttonPanelSetUp()
    {
    	//Adding the ScoreView panel to the north of the GUI frame
	    this.add(sv, BorderLayout.NORTH);
	    //Creating a button panel that will contain the buttons of the GUI
	    JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new TitledBorder(" Commands: "));
        buttonPanel.setLayout(new GridLayout(20,1));
        this.add(buttonPanel,BorderLayout.WEST);
        JButton gameModeButton = new JButton("Pause");
        buttonPanel.add(gameModeButton);
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);
        JButton addPylonButton = new JButton("Add Pylon");
        buttonPanel.add(addPylonButton);
        JButton addFuelCanButton = new JButton("Add Fuel Can");
        buttonPanel.add(addFuelCanButton);
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(quitButton);
        //Adding the MapView panel to the center of the GUI frame
	    this.add(mv, BorderLayout.CENTER);
	    //Attaching AbstractAction commands to buttons
	    gameModeButton.setAction(gameModeCommand);
	    quitButton.setAction(quitCommand);
	    deleteButton.setAction(deleteSelectedObjectCommand);
	    addPylonButton.setAction(addPylonCommand);
	    addFuelCanButton.setAction(addFuelCanCommand);
	    //Removing the buttons' default key binding to the space key
	    gameModeButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
	    quitButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
	    //Attaching key binders to arrow keys for car movement
        imap = mv.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        downKey = KeyStroke.getKeyStroke("DOWN");
        upKey = KeyStroke.getKeyStroke("UP");
        rightKey = KeyStroke.getKeyStroke("RIGHT");
        leftKey = KeyStroke.getKeyStroke("LEFT");
        oilSlickKey = KeyStroke.getKeyStroke('o');
        quitKey = KeyStroke.getKeyStroke('q');
        switchStrategyKey = KeyStroke.getKeyStroke("SPACE");
        deleteKey = KeyStroke.getKeyStroke("DELETE");
        imap.put(downKey, "Down");
        imap.put(upKey, "Up");
        imap.put(rightKey, "Right");
        imap.put(leftKey, "Left");
        imap.put(oilSlickKey, "Add Oil Slick");
        imap.put(quitKey, "Quit");
        imap.put(switchStrategyKey, "Switch Strategy");
        imap.put(deleteKey, "Delete");
        amap = mv.getActionMap();
        //Requesting focus for the MapView panel
        mv.requestFocus();
    }
    //Method that will unbind the key bindings of certain keys and lock certain buttons while the game 
    //is in "play" mode
    public void playModeBinding()
    {
    	deleteSelectedObjectCommand.setEnabled(false);
    	addPylonCommand.setEnabled(false);
    	addFuelCanCommand.setEnabled(false);
    	newColorsCommand.setEnabled(false);
    	addOilSlickCommand.setEnabled(false);
    	amap.put("Down", brakeCarCommand);
        amap.put("Up", accelerateCarCommand);
        amap.put("Right", steerCarRightCommand);
        amap.put("Left", steerCarLeftCommand);
        amap.put("Add Oil Slick", null);
        amap.put("Quit", quitCommand);
        amap.put("Switch Strategy", switchStrategyCommand);
        amap.put("Delete", null);
        gw.unselectAll();
    }
    //Method that will bind the key bindings of certain keys and unlock certain buttons while the game 
    //is in "pause" mode
    public void pauseModeBinding()
    {
    	deleteSelectedObjectCommand.setEnabled(true);
    	addPylonCommand.setEnabled(true);
    	addFuelCanCommand.setEnabled(true);
    	newColorsCommand.setEnabled(true);
    	addOilSlickCommand.setEnabled(true);
    	amap.put("Down", null);
        amap.put("Up", null);
        amap.put("Right", null);
        amap.put("Left", null);
        amap.put("Switch Strategy",  null);
        amap.put("Add Oil Slick", addOilSlickCommand);
        amap.put("Delete", deleteSelectedObjectCommand);
    }
    //An ActionEvent method that is called every time the timer ticks
    public void actionPerformed(ActionEvent e)
    {
    	//Setting time cycle in tickCommand.
    	tickCommand.setTimerCycle(elapsedTime);
    	//Invoking the "actionPerformed" method in tickCommand which invokes the
    	//"tick" method in GameWorld
    	tickCommand.actionPerformed(e);
    	timeCount += 1;
    	//Every 10 seconds invoke the "actionPerformed" method in switchStrategyCommand
    	if(timeCount % 500 == 0)
    	{
    		switchStrategyCommand.actionPerformed(e);
    		timeCount = 0;
    	}
    }
}
