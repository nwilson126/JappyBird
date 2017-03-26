package com.wilsongateway.objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.wilsongateway.framework.Game;

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
 * Description: Abstract class that represents a game item that moves across the screen at a constant rate.
 */
public abstract class Tile {
	
	//Position variables
	protected double position;
	
	//Scaling variables
	public static int tileWidth = 0;
	public static int platformHeight = 0;
	public static int pipeSpacing = 0;
	public static int overlap = 3;
	
	//ArrayList of all Tiles
	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : position : int
	 * Description   : Sets the current position and adds this tile to a List of all Tiles.
	 */
	public Tile(int position){
		this.position = position;
		tiles.add(this);
	}
	
	/**
	 * 
	 * Method Name   : paintTile
	 * Parameters    : g2d : Graphics
	 * Return Values : void
	 * Description   : Rendering of current tile to be implemented in sub classes.
	 */
	public abstract void paintTile(Graphics2D g2d);
	
	/**
	 * 
	 * Method Name   : moveTile
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Moving of current tile to be implemented in sub classes.
	 */
	public abstract void moveTile();
	
	/**
	 * 
	 * Method Name   : refreshTiles
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Resets the tileWidth, platformHeight, and pipeSpacing. Calls upon each sub classes refresh method.
	 */
	public static void refreshTiles(){
		tileWidth = Game.getBackground().getWidth(null) - overlap;
		platformHeight = Game.getPlatform().getHeight(null);
		pipeSpacing = Pipe.spacing * Game.getPipeTop().getWidth(null);
		
		Background.refreshTiles();
		Platform.refreshTiles();
		Pipe.refreshTiles();
		Game.player.resetPlayer();
	}
	
	/**
	 * 
	 * Method Name   : removeTile
	 * Parameters    : x : int
	 * Return Values : void
	 * Description   : Removes index x from tiles.
	 */
	public static void removeTile(int x){
		tiles.remove(x);
	}
	
	//Boilerplate
	public static ArrayList<Tile> getTiles(){return tiles;}
	public void setPosition(int position){this.position = position;}
	public double getPosition(){return position;}
}
