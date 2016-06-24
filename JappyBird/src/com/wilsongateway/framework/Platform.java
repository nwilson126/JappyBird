package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.wilsongateway.framework.Board.Stage;

public class Platform extends Tile{
	
	private static double speed;

	protected static ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	public Platform(int position) {
		super(position);
		platforms.add(this);
	}

	public static void refreshTiles() {
		//Refresh Tile width
		refreshTileSize();
		
		//Check if it is a new board
		if(platforms.size() == 0){
			//Creating all new Tiles, adds width + tileWidth for overlap
			for(int position = 0; position < Game.boardPanel.getWidth() + tileWidth; position += tileWidth){
				new Platform(position);
			}
		}else{
			//Re-assign the old Tiles
			int i = 0;
			for(int position = 0; position < Game.boardPanel.getWidth() + tileWidth; position += tileWidth){
				if(i < platforms.size()){
					platforms.get(i).setPosition(position);
				}else{
					//Create extra tiles if there isn't enough
					new Platform(position);
				}
				i++;
			}
			//Remove any extra tiles
			for(;i < platforms.size(); i++){
				platforms.remove(i);
			}
		}
	}

	@Override
	public void paintTile(Graphics2D g2d){
		if(position + tileWidth < 0){
			position = (platforms.size()-1) * tileWidth;
		}
		
		g2d.drawImage(Game.getPlatform(), Board.roundMid(position), Game.boardPanel.getHeight()-platformHeight, null);
		
		if(Board.current == Stage.PLAYING){
			position -= Game.heightRatio()*Board.speedScaler;
		}
	}
	
	//Boilerplate
	public static ArrayList<Platform> getPlatforms(){return platforms;}
}
