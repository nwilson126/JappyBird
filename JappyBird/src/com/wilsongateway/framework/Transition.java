package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

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
 * Description: Represents an abstract transition which happens between global scene changes.
 */
public abstract class Transition {
	
	//Object-Specific Variables
	protected Stage destinationStage;
	protected int transitionTick = 0;
	protected int length;
	
	//Global variables
	private static volatile CopyOnWriteArrayList<Transition> transitions = new CopyOnWriteArrayList<Transition>();
	
	/**
	 * The Transition class defines any graphical transition that takes place according to the Game's tick and moves the
	 * Board.current from the current Stage, to the destination Stage.
	 * 
	 * @param type : specifies the type of transition to be started
	 * @param destinationStage : specifies the Stage that the transition will end with
	 */
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : destinationStage : Stage, length : int
	 * Description   : Initializes variables and adds transition to all transitions.
	 */
	protected Transition(Stage destinationStage, int length){
		this.destinationStage = destinationStage;
		this.length = length;
		transitions.add(this);
	}
	
	/**
	 * 
	 * Method Name   : runTransition
	 * Parameters    : g2d : Graphics2D
	 * Return Values : void
	 * Description   : If the transition time has expired, the transition is removed, else it is played.
	 */
	public void runTransition(){
		if(transitionTick < length){
			moveTransition();
			transitionTick++;
		}else{
			transitions.remove(this);
		}
	}
		
	public static boolean noneActive(){
		return !(transitions.size() > 0);
	}
	
	/**
	 * 
	 * Method Name   : paintTransition
	 * Parameters    : g2d : Graphics2D
	 * Return Values : void
	 * Description   : To be implemented in subclasses for rendering.
	 */
	public abstract void paintTransition(Graphics2D g2d);
	
	protected abstract void moveTransition();
	
	//BoilerPlate getters
	public static CopyOnWriteArrayList<Transition> getTransitions(){return transitions;}
}
