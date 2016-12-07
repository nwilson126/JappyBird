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

public class InputManager implements KeyListener, MouseListener, ComponentListener, WindowStateListener{

	private boolean keyHeld = false;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		//Pause and Play functionality
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(Board.current == Stage.PLAYING){
				Board.current = Stage.PAUSED;
			}else{
				Board.current = Stage.PLAYING;
			}
		}
		
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

	@Override
	public void keyReleased(KeyEvent e) {
		keyHeld = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {
		Game.refreshSettingsFrameLocation();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(Game.boardPanel.getHeight() < 0 || Game.boardPanel.getWidth() < 0){
			Game.mainFrame.setSize(Game.width, Game.height);
		}
		
		Game.refreshSettingsFrameLocation();
		
		Game.refreshScaledImages();
		Board.resetGame();
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void windowStateChanged(WindowEvent arg0) {
		Game.settingsFrame.setVisible(!Game.settingsFrame.isVisible());
	}

}
