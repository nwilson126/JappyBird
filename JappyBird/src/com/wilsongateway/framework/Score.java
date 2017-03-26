package com.wilsongateway.framework;

import java.io.Serializable;

public class Score implements Serializable{

	private static final long serialVersionUID = 3064796304425071240L;
	
	private int points;
	private String name;
	
	Score(int points, String name){
		this.points = points;
		this.name = name;
	}
	
	public int getPoints(){return points;}
	public void setPoints(int points){this.points = points;}
	public String getName(){return name;}
}
