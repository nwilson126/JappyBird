package com.wilsongateway.framework;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.wilsongateway.framework.Board.Stage;

public class InputManager implements KeyListener, MouseListener, ComponentListener{

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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(Game.boardPanel.getHeight() < 0 || Game.boardPanel.getWidth() < 0){
			Game.mainFrame.setSize(Game.width, Game.height);
		}
		
		new FlashTransition(Stage.STANDBY, 120);
		Game.refreshScaledImages();
		Tile.refreshTiles();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
