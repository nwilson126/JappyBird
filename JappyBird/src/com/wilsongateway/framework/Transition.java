package com.wilsongateway.framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.wilsongateway.framework.Board.Stage;

public abstract class Transition {
	
	//Object-Specific Variables
	protected Stage destinationStage;
	protected int transitionTick = 0;
	protected int length;
	
	//Global variables
	private static ArrayList<Transition> transitions = new ArrayList<Transition>();
	
	/**
	 * The Transition class defines any graphical transition that takes place according to the Game's tick and moves the
	 * Board.current from the current Stage, to the destination Stage.
	 * 
	 * @param type : specifies the type of transition to be started
	 * @param destinationStage : specifies the Stage that the transition will end with
	 */
	Transition(Stage destinationStage, int length){
		this.destinationStage = destinationStage;
		this.length = length;
		transitions.add(this);
	}
	
	public void runTransition(Graphics2D g2d){
		if(transitionTick < length){
			paintTransition(g2d);
			transitionTick++;
		}else{
			transitions.remove(this);
		}
	}
	
	protected abstract void paintTransition(Graphics2D g2d);
	
	//BoilerPlate
	public static ArrayList<Transition> getTransitions(){return transitions;}
}
