package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
 * Description: Represents a background tile. This tile moves across the screen at a constant rate and has no user control.
 */
public class Background extends Tile{
	
	private static double scaler = 0.25;
	private static double speed;
	//private double pos = 0;
	private static ArrayList<Background> backgrounds = new ArrayList<Background>();
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : position : int
	 * Description   : Calls super() and adds this to backgrounds.
	 */
	Background(int position) {
		super(position);
		backgrounds.add(this);
	}

	/**
	 * 
	 * Method Name   : refreshTiles
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Repositions old tiles and creates new tiles if necessary.
	 */
	public static void refreshTiles() {
		//Check if it is a new board
		if(backgrounds.size() == 0){
			//Creating all new Tiles, adds width + tileWidth for overlap
			for(int position = 0; position < Game.boardPanel.getWidth() + tileWidth; position += tileWidth){
				new Background(position);
			}
		}else{
			//Re-assign the old Tiles
			int i = 0;
			for(int position = 0; position < Game.boardPanel.getWidth() + tileWidth; position += tileWidth){
				if(i < backgrounds.size()){
					backgrounds.get(i).setPosition(position);
				}else{
					//Create extra tiles if there isn't enough
					new Background(position);
				}
				i++;
			}
			//Remove any extra tiles
			for(;i < backgrounds.size(); i++){
				backgrounds.remove(i);
			}
		}
		
		//Adjust speed for resizing window
		speed = Game.heightRatio()*Board.speedScaler;
	}

	/**
	 * 
	 * Method Name   : paintTile
	 * Parameters    : g2d : Graphics2D
	 * Return Values : none
	 * Description   : Renders the current background tile and advances the position.
	 */
	@Override
	public void paintTile(Graphics2D g2d){
		if(position + tileWidth < 0){
			position = (backgrounds.size()-1) * tileWidth;
		}
		
		g2d.drawImage(Game.getBackground(), Board.roundMid(position), 0, null);
		
		//Dev mode outline
		if(Board.devMode){
			g2d.drawLine(Board.roundMid(position), 0, Board.roundMid(position), Game.boardPanel.getHeight()-platformHeight);
		}
		
		switch(Board.current){
		case PLAYING:
			position -= speed * scaler;
			break;
		default:
			break;
		}
	}
	
	//Boilerplate getters
	public static ArrayList<Background> getBackgrounds(){return backgrounds;}
}
