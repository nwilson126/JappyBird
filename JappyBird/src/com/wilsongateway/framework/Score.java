package com.wilsongateway.framework;

public class Score {
	private int value;
	private String name;
	
	Score(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	public int getValue(){return value;}
	public String getName(){return name;}
}
