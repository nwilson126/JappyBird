package com.wilsongateway.framework;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.wilsongateway.framework.Board.Stage;
import com.wilsongateway.gameObjects.Player;
import com.wilsongateway.gameObjects.Tile;

/**
 * Name	 	: Nicholas Lane Wilson
 * Class 	    : 1620 - 002
 * Program # 	: 7
 * Due Date  	: 12/7/2016
 *
 * Honor Pledge:  On my honor as a student of the University
 *                of Nebraska at Omaha, I have neither given nor received
 *                unauthorized help on this homework assignment.
 *
 * NAME: Nicholas Lane Wilson
 * NUID: 350
 * EMAIL: nlwilson@unomaha.edu
 * 
 * Partners:   NONE
 *	
 * Description: Serves as the runner for the entire program. Imports all required 
 * resources, creates players, loads tiles, and initializes the tick.
 */
public class Game {
	
	//Constants
	public static int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/3;
	public static int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/2;
	public static int fps = 45;
	public static int fpsCap = 120;
	public static int tps = 45;
	
	//Static game variables
	public static Thread frames;
	public static Thread ticks;
	public static JFrame mainFrame;
	public static SettingsFrame settingsFrame;
	public static Board board;
	public static Player player;
	
	public enum Time {DAY, NIGHT}
	public static Time currentTime = Time.DAY;
	
	//Images
	private static BufferedImage atlas;
	public static BufferedImage dayBackground;
	public static BufferedImage nightBackground;
	private static BufferedImage platform;
	private static BufferedImage pipeTop;
	private static BufferedImage pipeBottom;
	private static BufferedImage flappyUp;
	private static BufferedImage flappyMid;
	private static BufferedImage flappyDown;
	private static BufferedImage jappyLogo;
	
	//Scaled Images
	public static Image scaledDayBackground;
	public static Image scaledNightBackground;
	private static Image scaledPlatform;
	private static Image scaledPipeTop;
	private static Image scaledPipeBottom;
	private static Image scaledFlappyUp;
	private static Image scaledFlappyMid;
	private static Image scaledFlappyDown;
	private static Image scaledJappyLogo;
	
	public static boolean spritesLoaded = false;
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : none
	 * Description   : Creates the boardPanel, mainFrame, and settingsFrame. Then loads resources and starts tick.
	 */
	private Game(){
		loadResources();
		
		board = new Board();
		player = new Player();
		
		//Load Save File
		if(SaveManager.saveFileExists()){
			SaveManager.loadHighscores();
		}else{
			SaveManager.saveHighscores(Game.board.getHighscores());
		}
		
		initGUI();
		
		loadSprites();
		
		Tile.refreshTiles();
		player.resetPlayer();
		
		startTickLoop();
		
		startFrameLoop();
	}
	
	private void initGUI() {
		mainFrame = new JFrame("JappyBird");
		mainFrame.setSize(width, height);
		mainFrame.setMinimumSize(new Dimension(100,100));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setAlwaysOnTop(true);
		mainFrame.add(board);
		
		settingsFrame = new SettingsFrame();
		settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		settingsFrame.setLocationRelativeTo(mainFrame);
		refreshSettingsFrameLocation();
		settingsFrame.setAlwaysOnTop(true);
		settingsFrame.setVisible(false);
		
		InputManager manager = new InputManager();
		mainFrame.addMouseListener(manager);
		mainFrame.addKeyListener(manager);
		mainFrame.addComponentListener(manager);
		mainFrame.addWindowStateListener(manager);
		mainFrame.addWindowListener(new WindowListener(){
			@Override
			public void windowClosing(WindowEvent e) {
				//Save on close
				SaveManager.saveHighscores(Game.board.getHighscores());
				System.exit(0);
			}
			//Unused
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		});
	}

	/**
	 * 
	 * Method Name   : loadResources
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Loads font, and image files to be used.
	 */
	private void loadResources() {
		//Load sprite sheet
		try {
			atlas = ImageIO.read(getClass().getResource("/resources/atlas.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//Load fonts
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("resources/FlappyBirdy.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("resources/04B_19__.TTF")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 
	 * Method Name   : refreshSettingsFrameLocation
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Updates the settingsFrame location so that is sticks to the mainFrame.
	 */
	public static void refreshSettingsFrameLocation() {
		settingsFrame.setLocation(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());
	}

	/**
	 * 
	 * Method Name   : main
	 * Parameters    : args : String
	 * Return Values : void
	 * Description   : Creates an instance of Game.
	 */
	public static void main(String [] args){
		new Game();
	}
	
	/**
	 * 
	 * Method Name   : loadSprites
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Creates sprite images using sub-images of atlas.png.
	 */
	private void loadSprites(){
		dayBackground = atlas.getSubimage(0, 0, 288, 512);
		nightBackground = atlas.getSubimage(292, 0, 288, 512);
		platform = atlas.getSubimage(584, 0, 336, 112);
		pipeTop = atlas.getSubimage(168, 646, 52, 320);
		pipeBottom = atlas.getSubimage(112, 646, 52, 320);
		flappyUp = atlas.getSubimage(6, 982, 33, 23);
		flappyMid = atlas.getSubimage(62, 982, 33, 23);
		flappyDown = atlas.getSubimage(118, 982, 33, 23);
		
		jappyLogo = atlas.getSubimage(712, 182, 169, 48);
		refreshScaledImages();
		
		spritesLoaded = true;
	}
	
	/**
	 * 
	 * Method Name   : startFrameLoop
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Uses a separate thread to maintain a constant frame refresh rate of 60fps.
	 */
	private void startFrameLoop(){
		frames = new Thread(new Runnable(){

			@Override
			public void run() {
				long lastTime;
				while(true){
					lastTime = System.nanoTime();
					board.repaint();

					width = mainFrame.getContentPane().getWidth();
					height = mainFrame.getContentPane().getHeight();
					
					try {
						long timeElapsed = System.nanoTime() - lastTime;
						long sleepTime = ((1000000000/fps) - timeElapsed)/1000000;
						
						if(sleepTime < 10){
							fps--;
							sleepTime = 0;
						}else if(fps < fpsCap){
							fps++;
						}else if(fps > fpsCap){
							fps--;
						}
						settingsFrame.refreshFPSLabel();
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			}
			
		});
		frames.start();
	}
	
	
	/**
	 * 
	 * Method Name   : startTickLoop
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Uses a separate thread to maintain a constant tick speed
	 */
	private void startTickLoop(){
		ticks = new Thread(new Runnable(){

			@Override
			public void run() {
				long lastTime;
				while(true){
					lastTime = System.nanoTime();

					board.moveComponents();
					
					try {
						long timeElapsed = System.nanoTime() - lastTime;
						long sleepTime = ((1000000000/tps) - timeElapsed)/1000000;
						
						if(sleepTime > 0){
							Thread.sleep(sleepTime);

						}
						
						settingsFrame.refreshTPSLabel(1.0/(((double)(System.nanoTime() - lastTime))/1000000000));
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			}
			
		});
		ticks.start();
	}
	
	
	/**
	 * 
	 * Method Name   : refreshScaledImages
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Scales the loaded sub-images so that they fit the aspect ratio of the JFrame.
	 */
	public static void refreshScaledImages(){
		scaledDayBackground = dayBackground.getScaledInstance(-1, board.getHeight(), Image.SCALE_FAST);
		scaledNightBackground = nightBackground.getScaledInstance(-1, board.getHeight(), Image.SCALE_FAST);
		scaledPlatform = platform.getScaledInstance(-1, (int) (heightRatio()*platform.getHeight()), Image.SCALE_FAST);
		scaledPipeTop = pipeTop.getScaledInstance(-1, (int) (heightRatio()*pipeTop.getHeight()), Image.SCALE_FAST);
		scaledPipeBottom = pipeBottom.getScaledInstance(-1, (int) (heightRatio()*pipeBottom.getHeight()), Image.SCALE_FAST);
		scaledFlappyUp = flappyUp.getScaledInstance(-1, (int) (heightRatio()*flappyUp.getHeight()), Image.SCALE_FAST);
		scaledFlappyMid = flappyMid.getScaledInstance(-1, (int) (heightRatio()*flappyMid.getHeight()), Image.SCALE_FAST);
		scaledFlappyDown = flappyDown.getScaledInstance(-1, (int) (heightRatio()*flappyDown.getHeight()), Image.SCALE_FAST);
		scaledJappyLogo = jappyLogo.getScaledInstance(-1, (int) (heightRatio()*jappyLogo.getHeight()), Image.SCALE_FAST);
	}
	
	/**
	 * 
	 * Method Name   : heightRatio
	 * Parameters    : none
	 * Return Values : double
	 * Description   : Returns the ratio between the scaled background size and full background size.
	 */
	public static double heightRatio(){
		return (double)scaledDayBackground.getHeight(null)/(double)dayBackground.getHeight();
	}
	
	public static double tpsRatio(){
		return 60.0/tps;
	}
	
	/**
	 * 
	 * Method Name   : getFlappy
	 * Parameters    : none
	 * Return Values : Image
	 * Description   : Returns the current bird image using a %4 to cycle through the images.
	 */
	public static Image getFlappy(double state){
		Image temp = null;
		switch(((int)state)%4){
		case 0:
			temp = scaledFlappyUp;
			break;
		case 1:
			temp = scaledFlappyMid;
			break;
		case 2:
			temp = scaledFlappyDown;
			break;
		case 3:
			temp = scaledFlappyMid;
		}
		
		return temp;
	}
	
	/**
	 * 
	 * Method Name   : getBackground
	 * Parameters    : none
	 * Return Values : Image
	 * Description   : Returns the background image corresponding to currentTime.
	 */
	public static Image getBackground() {
			if(currentTime == Time.DAY){
				return scaledDayBackground;
			}else{
				return scaledNightBackground;
			}
	}
	
	//Boilerplate getters
	public static Image getPlatform(){return scaledPlatform;}
	public static Image getPipeTop(){return scaledPipeTop;}
	public static Image getPipeBottom(){return scaledPipeBottom;}
	public static Image getFlappyUp(){return scaledFlappyUp;}
	public static Image getJappyLogo(){return scaledJappyLogo;}
}
