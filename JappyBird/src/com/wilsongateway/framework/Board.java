package com.wilsongateway.framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import com.wilsongateway.gameObjects.Background;
import com.wilsongateway.gameObjects.Pipe;
import com.wilsongateway.gameObjects.Platform;
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
 * Description: Represents the JPanel on which to paint the games graphics. Handles the rendering of each game component.
 */
@SuppressWarnings("serial")
public class Board extends JPanel{
		
	//Stage
	public enum Stage{MAINMENU, PAUSED, PLAYING, STANDBY, DEATHMENU}
	public Stage current = Stage.STANDBY;
	
	//Highscores
	private volatile LinkedList<Score> highscores = new LinkedList<Score>();
	public int lastScore = 0;
	private volatile String nameInput = "";
	
	public double speedScaler = 2;
	public boolean devMode = false;
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : none
	 * Description   : Sets current mode to standby.
	 */
	public Board(){
		
	}
	
	/**
	 * 
	 * Method Name   : resetGame
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Sets current mode to standby and refreshes tiles.
	 */
	public void resetGame(Stage goTo){
		Game.board.current = goTo;
		Tile.refreshTiles();
	}

	/**
	 * 
	 * Method Name   : paintComponent
	 * Parameters    : g : Graphics
	 * Return Values : none
	 * Description   : This paintComponent method serves as the global rendering pipeline for each game component. 
	 * 				   The rendering of the game's components depend on the games 'current' Stage enum.           
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if(!Game.spritesLoaded){return;}

		//Paint background tiles
		for(Background b : Background.getBackgrounds()){
			b.paint(g2d);
		}
		
		//Render Pipes if not in main menu
		if(current == Stage.PAUSED || current == Stage.PLAYING || current == Stage.STANDBY){
			for(Pipe p : Pipe.getPipes()){
				p.paint(g2d);
			}
		}

		//Paint platforms
		for(Platform pf : Platform.getPlatforms()){
			pf.paint(g2d);
		}
		
		//Render logo if in standby
		if(current == Stage.STANDBY && Game.getJappyLogo() != null){
			g2d.drawImage(Game.getJappyLogo(), Game.board.getWidth()/2 - Game.getJappyLogo().getWidth(null)/2, Game.board.getHeight()/4, null);
		}
		
		//Render highscores if in standby
		if(current == Stage.STANDBY && Game.dayBackground != null){
			g2d.setFont(new Font("04B_19", Font.PLAIN, (int) (Game.heightRatio()*25)));
			for(int i = 0; i < highscores.size(); i++){
				g2d.drawString((i+1) + ". " + highscores.get(i).getName() + ": " + highscores.get(i).getPoints(), 10, (int) ((i+1)*(Game.heightRatio()*25)));
			}
		}
		
		//Render score if playing
		if(current == Stage.PLAYING || current == Stage.PAUSED){
			g2d.setFont(new Font("04B_19", Font.PLAIN, (int) (Game.heightRatio()*32)));
			g2d.drawString(Game.player.getPoints() + "", Game.board.getWidth()/2, Game.board.getHeight()/10);
		}
		
		//Render next score to beat if playing
		if(current == Stage.PLAYING || current == Stage.PAUSED){
			//If highscores is not empty and current score is less that the highest
			if(!highscores.isEmpty() && Game.player.getPoints() < highscores.getFirst().getPoints()){
				g2d.setFont(new Font("04B_19", Font.PLAIN, (int) (Game.heightRatio()*25)));
				g2d.setColor(new Color(0, 0, 0, 25));
				
				//Search highscores
				int nextScore = 0;
				for(int i = 0; i < highscores.size(); i++){
					if(Game.player.getPoints() < highscores.get(i).getPoints()){
						nextScore = i;
					}
				}
				
				String scoreLabel = highscores.get(nextScore).getName() + " " + (highscores.get(nextScore).getPoints() - Game.player.getPoints() + " > ");
				int stringWidth = g2d.getFontMetrics().stringWidth(scoreLabel);
				int stringHeight = g2d.getFontMetrics().getHeight();
				g2d.drawString(scoreLabel, this.getWidth() - stringWidth, this.getHeight() + stringHeight - (Game.getPlatform().getHeight(null)/2));
			}
		}
		
		//Render name input if in Deathmenu
		if(current == Stage.DEATHMENU){
			//Input line _____
			String nameOutput = nameInput;
			for(int i = nameOutput.length(); i < 10; i++){
				nameOutput += "_";
			}
			
			//Flappy Font
			g2d.setFont(new Font("04B_19", Font.PLAIN, (int) (Game.heightRatio()*32)));
			
			//Enter name label
			int stringWidth = g2d.getFontMetrics().stringWidth("Enter Name:");
			g2d.drawString("Enter Name:", Game.board.getWidth()/2 - (stringWidth/2), (Game.board.getHeight()/10)*2);
			
			//Current Text
			stringWidth = g2d.getFontMetrics().stringWidth(nameOutput);
			g2d.drawString(nameOutput, Game.board.getWidth()/2 - (stringWidth/2), (Game.board.getHeight()/10)*3);
		}
		
		//Render Player if not in main menu
		if(current == Stage.PAUSED || current == Stage.PLAYING || current == Stage.STANDBY || current == Stage.DEATHMENU){
			Game.player.paint(g2d);
		}
		
		//Render transitions
		for(Transition t : Transition.getTransitions()){
			t.paintTransition(g2d);
		}
	}
	
	public void moveComponents(){		
		//Move background tiles
		for(Background b : Background.getBackgrounds()){
			b.move();
		}
		
		//Move pipes
		for(Pipe p : Pipe.getPipes()){
			p.move();
		}

		//Move platforms
		for(Platform pf : Platform.getPlatforms()){
			pf.move();
		}
		
		//Move player
		Game.player.move();
		
		//Move transitions
		for(Transition t : Transition.getTransitions()){
			t.runTransition();
		}
	}
	
	void recordScore(){
		//Check for existing scores
//		boolean scoreExists = false;
		int highestScore = lastScore;
//		for(Score s : highscores){
//			if(s.getName().equalsIgnoreCase(nameInput)){
//				scoreExists = true;
//				if(s.getPoints() > highestScore){
//					highestScore = s.getPoints();
//				}
//			}
//		}
//		
//		//Remove other records
//		for(int i = 0; i < highscores.size(); i++){
//			if(highscores.get(i).getName().equalsIgnoreCase(nameInput)){
//				highscores.remove(i);
//				i--;
//			}
//		}
		
		//Find where the points belong 
		int i;
		for(i = 0; i < highscores.size(); i++){
			if(highestScore > highscores.get(i).getPoints()){
				break;
			}
		}
		
		//Insert the new record
		highscores.add(i, new Score(highestScore, nameInput));
		
		//Truncate
		if(highscores.size() > 10){
			highscores.removeLast();
		}
		
		//Clear last name
		nameInput = "";
		
		//Save highscores file
		SaveManager.saveHighscores(highscores);
	}
	
	public boolean eligibleHighscore(){
		return highscores.size() < 10 || lastScore > highscores.getLast().getPoints();
	}
	
	LinkedList<Score> getHighscores(){
		return highscores;
	}
	
	void setHighscores(LinkedList<Score> scores){
		highscores = scores;
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
	
	public String getNameInput(){
		return nameInput;
	}
	
	public void appendToNameInput(char c){
		if(c == '\b' && nameInput.length() != 0){
			nameInput = nameInput.substring(0, nameInput.length() - 1);
		}else if(c != '\b'){
			nameInput += c;
		}
	}
}
