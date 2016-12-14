package com.wilsongateway.framework;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.wilsongateway.framework.Board.Stage;

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
 * Description: Represents the JPanel on which to paint the games graphics. Handles the rendering of each game component.
 */
@SuppressWarnings("serial")
public class Board extends JPanel{
	
	public static volatile double speedScaler = 2;
	public static double backgroundScaler = 0.5;
	public static boolean movingBackground = true;
	
	public enum Stage{MAINMENU, PAUSED, PLAYING, STANDBY, DEATHMENU}
	public static Stage current;
	
	public static boolean devMode = false;
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : none
	 * Description   : Sets current mode to standby.
	 */
	public Board(){
		current = Stage.STANDBY;
	}
	
	/**
	 * 
	 * Method Name   : resetGame
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Sets current mode to standby and refreshes tiles.
	 */
	public static void resetGame(){
		current = Stage.STANDBY;
//		new FlashTransition(Stage.STANDBY, 60);
		Tile.refreshTiles();
	}

	/**
	 * 
	 * Method Name   : paintComponent
	 * Parameters    : g : Graphics
	 * Return Values : none
	 * Description   : This paintComponent method serves as the global rendering switch for each game component. 
	 * 				   The rendering of the game's components depend on the games 'current' Stage enum.           
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//Paint background tiles
		for(Background b : Background.getBackgrounds()){
			b.paintTile(g2d);
		}
		
		//Render Pipes if not in main menu
		if(current != Stage.MAINMENU){
			for(Pipe p : Pipe.getPipes()){
				p.paintTile(g2d);
			}
		}

		//Paint platforms
		for(Platform pf : Platform.getPlatforms()){
			pf.paintTile(g2d);
		}
		
		//Render logo if in standby
		if(current == Stage.STANDBY && Game.getJappyLogo() != null){
			g2d.drawImage(Game.getJappyLogo(), Game.boardPanel.getWidth()/2 - Game.getJappyLogo().getWidth(null)/2, Game.boardPanel.getHeight()/4, null);
		}
		
		//Render score if playing
		if(current == Stage.PLAYING){
			g2d.setFont(new Font("04B_19", Font.PLAIN, (int) (Game.heightRatio()*32)));
			g2d.drawString(Game.player.getPoints() + "", Game.boardPanel.getWidth()/2, Game.boardPanel.getHeight()/10);
		}
		
		//Render Player if not in main menu
		if(current != Stage.MAINMENU){
			for(Player p : Player.getPlayers()){
				p.paintPlayer(g2d);
			}
		}
		
		//Render transitions
		if(!Transition.getTransitions().isEmpty()){
			for(Transition t : Transition.getTransitions()){
				t.runTransition(g2d);
			}
		}
	}
	
	/**
	 * 
	 * Method Name   : roundMid
	 * Parameters    : x : double
	 * Return Values : int
	 * Description   : Rounds a double to int according to whether the double is < *.5 or > *.5
	 */
	public static int roundMid(double x){
		if(x < Math.floor(x) + 0.5){
			return (int)Math.floor(x);
		}else{
			return (int)Math.ceil(x);
		}
	}
}
