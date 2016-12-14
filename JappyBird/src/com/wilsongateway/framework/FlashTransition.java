package com.wilsongateway.framework;

import java.awt.Color;
import java.awt.Graphics2D;

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
 * Description: Represents a transition which fades the screen to white and changes the stage.
 */
public class FlashTransition extends Transition{

	double opacity = 0;
	double increment;
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : DestinationStage : Stage, length : int
	 * Description   : Calls super() and sets increment to 255/length.
	 */
	FlashTransition(Stage destinationStage, int length) {
		super(destinationStage, length);
		increment = 255/length;
	}

	/**
	 * 
	 * Method Name   : paintTransition
	 * Parameters    : g2d : Graphics2D
	 * Return Values : none
	 * Description   : Renders the transition and updates the opacity and increment variables.
	 */
	@Override
	protected void paintTransition(Graphics2D g2d) {
		if(transitionTick < length/2){
			opacity += increment;
		}else{
			opacity -= increment;
		}
		
		g2d.setColor(new Color(255, 255, 255, (int)opacity));
		g2d.fillRect(0, 0, Game.boardPanel.getWidth(), Game.boardPanel.getHeight());
		
		if(transitionTick == length/2){
			Board.current = destinationStage;
			System.out.println("Stage Changed");
		}
		System.out.println(transitionTick);
	}
}
