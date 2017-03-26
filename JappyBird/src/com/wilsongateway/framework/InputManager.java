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
	private boolean displaySettings = false;

	/**
	 * 
	 * Method Name   : keyPressed
	 * Parameters    : key : KeyEvent
	 * Return Values : none
	 * Description   : Calls upon the flap() method in each player that is bound to key.
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		if(Game.board.current == Stage.PAUSED || Game.board.current == Stage.PLAYING || Game.board.current == Stage.STANDBY){
			
			//Pause and Play functionality
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
				if(Game.board.current == Stage.PLAYING){
					Game.board.current = Stage.PAUSED;
				}else{
					Game.board.current = Stage.PLAYING;
				}
			}else if(key.getKeyCode() == KeyEvent.VK_H){
				displaySettings = !displaySettings;
				Game.settingsFrame.setVisible(displaySettings);
				Game.settingsFrame.repaint();
				Game.mainFrame.requestFocus();
			}else if(key.getKeyCode() == KeyEvent.VK_SPACE){
				if(!keyHeld){
					//Check player bindings
					Game.player.flap();
				}
				keyHeld = true;
				
				//Begin game on standby
				if(Game.board.current == Stage.STANDBY){
					Game.board.current = Stage.PLAYING;
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(Game.board.current == Stage.DEATHMENU){
			if(Game.board.getNameInput().length() < 10 && e.getKeyChar()    != '\n'){
				Game.board.appendToNameInput(e.getKeyChar());
			}else{
				Game.board.recordScore();
				Game.board.current = Stage.STANDBY;
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
		if(Game.board.getHeight() < 0 || Game.board.getWidth() < 0){
			Game.mainFrame.setSize(Game.width, Game.height);
		}
		
		Game.refreshSettingsFrameLocation();
		
		Game.refreshScaledImages();
		Game.board.resetGame(Game.board.current);
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
		if(w.getWindow().isVisible() && displaySettings){
			Game.settingsFrame.setVisible(true);
		}else{
			Game.settingsFrame.setVisible(false);
		}
		Game.settingsFrame.repaint();
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
			Game.player.flap();
		}
		keyHeld = true;
		
		//Begin game on standby
		if(Game.board.current == Stage.STANDBY){
			Game.board.current = Stage.PLAYING;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		keyHeld = false;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		Game.settingsFrame.setVisible(false);
		Game.settingsFrame.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		if(displaySettings){
			Game.settingsFrame.setVisible(true);
		}else{
			Game.settingsFrame.setVisible(false);
		}
		Game.settingsFrame.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
}
