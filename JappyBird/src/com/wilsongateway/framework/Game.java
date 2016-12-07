package com.wilsongateway.framework;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.wilsongateway.framework.Board.Stage;

public class Game {
	
	//Constants
	public static int width = 576;
	public static int height = 1024;
	public static int fps = 60;
	public static int fpsCap = 60;
	
	//Static game variables
	public static Thread tick;
	public static JFrame mainFrame;
	public static SettingsFrame settingsFrame;
	public static Board boardPanel;
	public static double flappyTick = 0;
	public static Player player;
	
	//Images
	private static BufferedImage atlas;
	public static BufferedImage dayBackground;
	private static BufferedImage platform;
	private static BufferedImage pipeTop;
	private static BufferedImage pipeBottom;
	private static BufferedImage flappyUp;
	private static BufferedImage flappyMid;
	private static BufferedImage flappyDown;
	private static BufferedImage jappyLogo;
	
	public static Image scaledDayBackground;
	private static Image scaledPlatform;
	private static Image scaledPipeTop;
	private static Image scaledPipeBottom;
	private static Image scaledFlappyUp;
	private static Image scaledFlappyMid;
	private static Image scaledFlappyDown;
	private static Image scaledJappyLogo;
	
	
	private Game(){
		loadResources();
		
		boardPanel = new Board();
		
		mainFrame = new JFrame("JappyBird");
		mainFrame.setSize(width, height);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setAlwaysOnTop(true);
		mainFrame.add(boardPanel);
		
		settingsFrame = new SettingsFrame();
		settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		settingsFrame.setLocationRelativeTo(mainFrame);
		refreshSettingsFrameLocation();
		settingsFrame.setAlwaysOnTop(true);
		settingsFrame.setVisible(true);
		
		loadSprites();
		
		Tile.refreshTiles();
		
		initTick();
		
		InputManager manager = new InputManager();
		mainFrame.addMouseListener(manager);
		mainFrame.addKeyListener(manager);
		mainFrame.addComponentListener(manager);
		mainFrame.addWindowStateListener(manager);
		
		//Create main player and set initial scene
		player = new Player();
	}
	
	private void loadResources() {
		//Load sprite sheet
		try {
			atlas = ImageIO.read(getClass().getResource("atlas.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//Load fonts
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/FlappyBirdy.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/04B_19__.TTF")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void refreshSettingsFrameLocation() {
		settingsFrame.setLocation(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());
	}

	public static void main(String [] args){
		new Game();
	}
	
	private void loadSprites(){
		dayBackground = atlas.getSubimage(0, 0, 288, 512);
		platform = atlas.getSubimage(584, 0, 336, 112);
		pipeTop = atlas.getSubimage(168, 646, 52, 320);
		pipeBottom = atlas.getSubimage(112, 646, 52, 320);
		flappyUp = atlas.getSubimage(6, 982, 33, 23);
		flappyMid = atlas.getSubimage(62, 982, 33, 23);
		flappyDown = atlas.getSubimage(118, 982, 33, 23);
		
		jappyLogo = atlas.getSubimage(713, 182, 168, 48);
		refreshScaledImages();
	}
	
	private void initTick(){
		tick = new Thread(new Runnable(){

			@Override
			public void run() {
				long lastTime;
				while(true){
					lastTime = System.nanoTime();
					boardPanel.repaint();
					mainFrame.getContentPane().getWidth();
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
		tick.start();
	}
	
	public static void refreshScaledImages(){
		scaledDayBackground = dayBackground.getScaledInstance(-1, boardPanel.getHeight(), Image.SCALE_FAST);
		scaledPlatform = platform.getScaledInstance(-1, (int) (heightRatio()*platform.getHeight()), Image.SCALE_FAST);
		scaledPipeTop = pipeTop.getScaledInstance(-1, (int) (heightRatio()*pipeTop.getHeight()), Image.SCALE_FAST);
		scaledPipeBottom = pipeBottom.getScaledInstance(-1, (int) (heightRatio()*pipeBottom.getHeight()), Image.SCALE_FAST);
		scaledFlappyUp = flappyUp.getScaledInstance(-1, (int) (heightRatio()*flappyUp.getHeight()), Image.SCALE_FAST);
		scaledFlappyMid = flappyMid.getScaledInstance(-1, (int) (heightRatio()*flappyMid.getHeight()), Image.SCALE_FAST);
		scaledFlappyDown = flappyDown.getScaledInstance(-1, (int) (heightRatio()*flappyDown.getHeight()), Image.SCALE_FAST);
		scaledJappyLogo = jappyLogo.getScaledInstance(-1, (int) (heightRatio()*jappyLogo.getHeight()), Image.SCALE_FAST);
	}
	
	public static double heightRatio(){
		return (double)scaledDayBackground.getHeight(null)/(double)dayBackground.getHeight();
	}
	
	public static Image getFlappy(){
		Image temp = null;
		switch(((int)flappyTick)%4){
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
		
		//Only plays animation if game is playing
		if(Board.current == Stage.PLAYING){
			if(flappyTick > 40){
				flappyTick = 0;
			}else{
				flappyTick += 0.2;
			}
		}
		
		return temp;
	}
	
	//Boilerplate
	public static Image getDayBackground(){return scaledDayBackground;}
	public static Image getPlatform(){return scaledPlatform;}
	public static Image getPipeTop(){return scaledPipeTop;}
	public static Image getPipeBottom(){return scaledPipeBottom;}
	public static Image getFlappyUp(){return scaledFlappyUp;}
	public static Image getJappyLogo(){return scaledJappyLogo;}
}
