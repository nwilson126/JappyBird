package com.wilsongateway.framework;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Board extends JPanel{
	
	public static double speedScaler = 2;
	public static double backgroundScaler = 0.5;
	public static boolean movingBackground = true;
	
	public enum Stage{MAINMENU, PAUSED, PLAYING, STANDBY, DEATHMENU}
	public static Stage current;
	
	public static int currentDistance = 0;
	
	public static boolean devMode = false;
	
	public Board(){
		current = Stage.MAINMENU;
	}
	
	public static void resetGame(){
		current = Stage.STANDBY;
		Tile.refreshTiles();
	}
	
	/**
	 * This paintComponent method serves as the global rendering switch for each game component.
	 * The rendering of the game's components depend on the games 'current' Stage enum.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(Background b : Background.getBackgrounds()){
			b.paintTile(g2d);
		}
		
		//Render Pipes if not in main menu
		if(current != Stage.MAINMENU){
			for(Pipe p : Pipe.getPipes()){
				p.paintTile(g2d);
			}
		}

		for(Platform pf : Platform.getPlatforms()){
			pf.paintTile(g2d);
		}
		
		//Render Player if not in main menu
		if(current != Stage.MAINMENU){
			for(Player p : Player.getPlayers()){
				p.paintPlayer(g2d);
			}
		}
		
		renderLogo();
		
		if(!Transition.getTransitions().isEmpty()){
			for(Transition t : Transition.getTransitions()){
				t.runTransition(g2d);
			}
		}
	}
	
	private void renderLogo(){
		if(current == Stage.STANDBY){
			//Render logo
		}else{
			
		}
	}
	
	/**
	 * Rounds to floor or ceiling depending if the number is
	 * greater than the number + 0.5
	 * @param x double to be rounded into int
	 * @return rounded number 
	 */
	public static int roundMid(double x){
		if(x < Math.floor(x) + 0.5){
			return (int)Math.floor(x);
		}else{
			return (int)Math.ceil(x);
		}
	}
}
