package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.wilsongateway.framework.Board.Stage;

public class Background extends Tile{
	
	private static double scaler = 0.25;
	private static double speed;
	//private double pos = 0;
	private static ArrayList<Background> backgrounds = new ArrayList<Background>();
	
	public Background(int position) {
		super(position);
		backgrounds.add(this);
	}

	public static void refreshTiles() {
		//Refresh Tile width
		refreshTileSize();
		
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

	@Override
	public void paintTile(Graphics2D g2d){
		if(position + tileWidth < 0){
			position = (backgrounds.size()-1) * tileWidth;
		}
		
		g2d.drawImage(Game.getDayBackground(), Board.roundMid(position), 0, null);
		
		switch(Board.current){
		case PLAYING:
			position -= speed * scaler * Board.speedScaler;
			break;
		default:
			break;
		}
	}
	
	//Boilerplate
	public static ArrayList<Background> getBackgrounds(){return backgrounds;}
}
