package com.wilsongateway.framework;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.wilsongateway.framework.Board.Stage;
import com.wilsongateway.framework.Game.Time;

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
 * Description: Represents the settings JFrame which controls game aspects.
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JFrame{
	
	private JLabel fpsLabel;
	private JLabel maxScoreLabel;
	private int maxScore = 0;
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : none
	 * Description   : Configures the JFrame and adds all GUI elements to it.
	 */
	SettingsFrame(){
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		getContentPane().setBackground(new Color(222, 216, 149));
		setLayout(new FlowLayout());
		
		//Title
		add(new JLabel("Settings"));
		add(new JLabel("________________"));
		
		//Resets all game pieces
		JButton startBtn = new JButton("Reset");
		startBtn.addActionListener(e -> {
			Board.resetGame(Stage.STANDBY);
			Game.mainFrame.requestFocus();
		});
		add(startBtn);
		
		//Instructions
		add(new JLabel("Press h to hide"));
		add(new JLabel("Press esc to pause"));
		
		//Title + Panel
		JPanel speedPanel = new JPanel();
		speedPanel.setBackground(new Color(222, 216, 149));
		add(speedPanel);
		speedPanel.add(new JLabel("Speed:"));
		
		//Speed adjustment
		JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(Board.speedScaler,0.1,5.0,0.5));
		((JSpinner.DefaultEditor)speedSpinner.getEditor()).getTextField().setColumns(2);
		((JSpinner.DefaultEditor)speedSpinner.getEditor()).getTextField().setEditable(false);
		speedSpinner.addChangeListener(e -> {
			Board.speedScaler = (double)speedSpinner.getValue();
			Board.resetGame(Stage.STANDBY);
			Game.mainFrame.requestFocus();
		});
		speedSpinner.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				Game.mainFrame.requestFocus();
			}

			@Override
			public void focusLost(FocusEvent arg0) {}
			
		});
		speedPanel.add(speedSpinner);
		
		//Title + Panel
		JPanel spacingPanel = new JPanel();
		spacingPanel.setBackground(new Color(222, 216, 149));
		add(spacingPanel);
		spacingPanel.add(new JLabel("Spacing:"));
		
		//Spacing adjustment
		JSpinner spacingSpinner = new JSpinner(new SpinnerNumberModel(Pipe.spacing,1,5,1));
		((JSpinner.DefaultEditor)spacingSpinner.getEditor()).getTextField().setColumns(2);
		((JSpinner.DefaultEditor)spacingSpinner.getEditor()).getTextField().setEditable(false);
		spacingSpinner.addChangeListener(e -> {
			Pipe.spacing = (int)spacingSpinner.getValue();
			Board.resetGame(Stage.STANDBY);
			Game.mainFrame.requestFocus();
		});
		spacingPanel.add(spacingSpinner);
		
		//Title + Panel
		JPanel timePanel = new JPanel();
		timePanel.setBackground(new Color(222, 216, 149));
		add(timePanel);
		timePanel.add(new JLabel("Time:"));
		
		//Time selection
		JComboBox<Time> timeSelector = new JComboBox<Time>();
		timeSelector.addItem(Time.DAY);
		timeSelector.addItem(Time.NIGHT);
		timeSelector.setSelectedItem(Time.DAY);
		timeSelector.addActionListener(e -> {
			Game.currentTime = (Time) timeSelector.getSelectedItem();
			Board.resetGame(Stage.STANDBY);
			Game.mainFrame.requestFocus();
		});
		timePanel.add(timeSelector);
		
		//JLabels to display current statistics
		maxScoreLabel = new JLabel("Max Score: 0");
		add(maxScoreLabel);
		
		add(new JLabel("________________"));
		
		//Dev mode selection
		JCheckBox devCheckBox = new JCheckBox("Dev Mode");
		devCheckBox.setBackground(new Color(222, 216, 149));
		devCheckBox.addActionListener(e -> {
			Board.devMode = devCheckBox.isSelected();
			Board.resetGame(Stage.STANDBY);
			Game.mainFrame.requestFocus();
		});
		add(devCheckBox);
		
		//FPS Stat
		fpsLabel = new JLabel();
		add(fpsLabel);
		
		//JFrame configuration
		setVisible(false);
		setSize(startBtn.getWidth()*2, 300);
		this.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				Game.boardPanel.requestFocus();
			}

			@Override
			public void focusLost(FocusEvent arg0) {}
			
		});
	}

	/**
	 * 
	 * Method Name   : refreshFPSLabel
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Resets the fpsLabel's text.
	 */
	public void refreshFPSLabel() {
		fpsLabel.setText("FPS: " + Game.fps);
	}
	
	/**
	 * 
	 * Method Name   : refreshMaxScoreLabel
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Resets the scoreLabel's text.
	 */
	public void refreshMaxScoreLabel(){
		if(Game.player.getPoints() > maxScore){
			maxScoreLabel.setText("Max Score: " + Game.player.getPoints());
			maxScore = Game.player.getPoints();
		}
	}
}
