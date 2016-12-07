package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Tile {
	
	//Position variables
	protected double position;
	
	//Scaling variables
	public static int tileWidth = 0;
	public static int platformHeight = 0;
	public static int pipeSpacing = 0;
	
	//ArrayList of all Tiles
	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public Tile(int position){
		this.position = position;
		tiles.add(this);
	}
	
	public abstract void paintTile(Graphics2D g2d);
	
	public static void refreshTileSize(){
		//TODO edit photo to be continuous
		tileWidth = Game.getDayBackground().getWidth(null)-2;
		platformHeight = Game.getPlatform().getHeight(null);
		pipeSpacing = Pipe.spacing * Game.getPipeTop().getWidth(null);
	}
	
	public static void refreshTiles(){
		Background.refreshTiles();
		Platform.refreshTiles();
		Pipe.refreshTiles();
		for(Player p : Player.getPlayers()){
			p.resetPlayer();
		}
	}
	
	//Boilerplate
	public static ArrayList<Tile> getTiles(){return tiles;}
	public static void removeTile(int x){tiles.remove(x);}
	public void setPosition(int position){this.position = position;}
	public double getPosition(){return position;}
}
