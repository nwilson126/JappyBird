package com.wilsongateway.framework;

import java.io.Serializable;

public class Score implements Serializable{

	private static final long serialVersionUID = 3064796304425071240L;
	
	private int value;
	private String name;
	
	Score(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	public int getValue(){return value;}
	public String getName(){return name;}
}
