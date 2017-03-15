package com.wilsongateway.framework;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

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
 * Description: Acts a listener for key presses, mouse clicks, window resizes and window state changes.
 */
public class InputManager implements KeyListener, MouseListener, ComponentListener, WindowStateListener{

	private boolean keyHeld = false;

	/**
	 * 
	 * Method Name   : keyPressed
	 * Parameters    : key : KeyEvent
	 * Return Values : none
	 * Description   : Calls upon the flap() method in each player that is bound to key.
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		if(Board.current == Stage.PAUSED || Board.current == Stage.PLAYING || Board.current == Stage.STANDBY){
			
			//Pause and Play functionality
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
				if(Board.current == Stage.PLAYING){
					Board.current = Stage.PAUSED;
				}else{
					Board.current = Stage.PLAYING;
				}
			}else if(key.getKeyCode() == KeyEvent.VK_H){
				Game.settingsFrame.setVisible(!Game.settingsFrame.isVisible());
				Game.mainFrame.requestFocus();
			}else if(key.getKeyCode() == KeyEvent.VK_SPACE){
				if(!keyHeld){
					//Check player bindings
					for(Player p : Player.getPlayers()){
						if(key.getKeyCode() == p.getKeyBind()){
							p.flap();
						}
					}
				}
				keyHeld = true;
				
				//Begin game on standby
				if(Board.current == Stage.STANDBY){
					Board.current = Stage.PLAYING;
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(Board.current == Stage.DEATHMENU){
			if(Board.getNameInput().length() < 10 && e.getKeyChar()    != '\n'){
				Board.appendToNameInput(e.getKeyChar());
			}else{
				Board.recordScore();
				Board.current = Stage.STANDBY;
			}
		}
	}

	/**
	 * 
	 * Method Name   : keyReleased
	 * Parameters    : e : KeyEvent
	 * Return Values : none
	 * Description   : Sets keyHeld to false.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keyHeld = false;
	}
	
	/**
	 * 
	 * Method Name   : componentResized
	 * Parameters    : e : ComponentEvent
	 * Return Values : none
	 * Description   : Sets the mainFrame to the minimum size and refreshes game objects.
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		if(Game.boardPanel.getHeight() < 0 || Game.boardPanel.getWidth() < 0){
			Game.mainFrame.setSize(Game.width, Game.height);
		}
		
		Game.refreshSettingsFrameLocation();
		
		Game.refreshScaledImages();
		Board.resetGame(Board.current);
	}
	
	/**
	 * 
	 * Method Name   : windowStateChanged
	 * Parameters    : w : WindowEvent
	 * Return Values : none
	 * Description   : Changes the settingsFrame's visibility.
	 */
	@Override
	public void windowStateChanged(WindowEvent w) {
		Game.settingsFrame.setVisible(!Game.settingsFrame.isVisible());
	}
	
	/**
	 * 
	 * Method Name   : componentMoved
	 * Parameters    : e : ComponentEvent
	 * Return Values : none
	 * Description   : Refreshes the settingsFrame location.
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		Game.refreshSettingsFrameLocation();
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		//Pause and Play functionality
		if(!keyHeld){
			//Check player bindings
			for(Player p : Player.getPlayers()){
				p.flap();
			}
		}
		keyHeld = true;
		
		//Begin game on standby
		if(Board.current == Stage.STANDBY){
			Board.current = Stage.PLAYING;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		keyHeld = false;
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
}
