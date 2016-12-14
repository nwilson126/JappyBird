package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

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
 * Description: Represents a pipe game tile. Moves across screen at a constant rate and detects if the current player collides with itself.
 */
public class Pipe extends Tile{
	
	//Height and random generator for height resetting
	private int y;
	private Random gen;
	
	//Gap adjustment
	private static int gap = 0;
	private static int heightSpacing = 0;
	
	//Pipe placement variables
	protected static double scaler = 1;
	protected static double speed;
	public static int spacing = 3;
	
	//Keeps track if this pipe has been counted towards score
	boolean scored = false;

	//ArrayList of all pipe objects
	private static ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : position : int
	 * Description   : Adds itself to List of all Pipes, then calls resetHeight().
	 */
	public Pipe(int position) {
		super(position);
		pipes.add(this);
		
		gen = new Random();
		resetHeight();
	}

	/**
	 * 
	 * Method Name   : refreshTiles
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Refreshes tile size. Then calculates the gap, heightspacing, speed. Finally either creates all 
	 * 				   new pipes or repositions old ones.
	 */
	public static void refreshTiles() {
		
		//Adjust gap and heightSpacing for resized window
		gap = (int) (Game.heightRatio()*150);
		heightSpacing = (int) (Game.heightRatio()*50);
		
		//Adjust speed for resizing window
		speed = Game.heightRatio()*Board.speedScaler;
		
		//Check if it is a new board
		if(pipes.size() == 0){
			//Creating all new Tiles, adds width + tileWidth for overlap
			for(int position = 0; position < Game.boardPanel.getWidth() + pipeSpacing; position += pipeSpacing){
				new Pipe(position + Game.width/2 + gap);
			}
		}else{
			//Re-assign the old Tiles
			int i = 0;
			for(int position = 0; position < Game.boardPanel.getWidth() + pipeSpacing; position += pipeSpacing){
				if(i < pipes.size()){
					pipes.get(i).setPosition(position + Game.width/2 + gap);
					pipes.get(i).resetHeight();
					pipes.get(i).setScored(false);
				}else{
					//Create extra tiles if there isn't enough
					new Pipe(position + Game.width/2 + gap);
				}
				i++;
			}
			//Remove any extra tiles
			for(;i < pipes.size(); i++){
				pipes.remove(i);
			}
		}
	}

	/**
	 * 
	 * Method Name   : paintTile
	 * Parameters    : g2d : Graphics2D
	 * Return Values : none
	 * Description   : Paints the pipes in their current position and then advances the position.
	 */
	@Override
	public void paintTile(Graphics2D g2d){
		//Reset pipe at right side
		if(position + pipeSpacing < 0){
			position = (pipes.size()-1) * pipeSpacing;
			resetHeight();
			
			//Set as uncounted in score
			scored = false;
		}else if(scored == false && position < Game.player.getX()){
			scored = true;
			Game.player.addPoint();
		}
		
		g2d.drawImage(Game.getPipeBottom(), Board.roundMid(position), y - Game.getPipeTop().getHeight(null) + heightSpacing, null);
		g2d.drawImage(Game.getPipeTop(), Board.roundMid(position), y + gap, null);
		
		//Dev mode outline
		if(Board.devMode){
			for(Shape s : getOutlines()){
				g2d.draw(s);
			}
		}
		
		if(Board.current == Stage.PLAYING){
			position -= speed * scaler;
		}
	}
	
	/**
	 * 
	 * Method Name   : resetHeight
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Resets the current pipe's height.
	 */
	private void resetHeight(){
		y = gen.nextInt(Game.getBackground().getHeight(null) - Game.getPlatform().getHeight(null) - 2*heightSpacing - gap);
	}
	
	/**
	 * 
	 * Method Name   : getOutlines
	 * Parameters    : none
	 * Return Values : Rectangle2D[]
	 * Description   : Generates and returns collision boxes for the current pipe.
	 */
	public Rectangle2D[] getOutlines(){
		Rectangle2D top = new Rectangle(Board.roundMid(position),y - Game.getPipeTop().getHeight(null) + heightSpacing,
				Game.getPipeTop().getWidth(null),Game.getPipeTop().getHeight(null));
		Rectangle2D bottom = new Rectangle(Board.roundMid(position),y + gap,
				Game.getPipeBottom().getWidth(null),Game.getPipeBottom().getHeight(null));
		
		return new Rectangle2D[]{top, bottom};
	}
	
	//Boilerplate
	public static ArrayList<Pipe> getPipes(){return pipes;}
	public int getGapTopY(){return y;}
	public int getGapBottomY(){return y + gap;}
	public void setScored(boolean value){scored = value;}
}
