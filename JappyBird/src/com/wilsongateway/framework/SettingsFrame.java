package com.wilsongateway.framework;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class SettingsFrame extends JFrame{
	
	private JLabel fpsLabel;
	private JLabel maxScoreLabel;
	private int maxScore = 0;
	
	public SettingsFrame(){
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		setLayout(new FlowLayout());
		
		add(new JLabel("Settings"));
		
		JButton startBtn = new JButton("Reset");
		startBtn.addActionListener(e -> {
			Board.resetGame();
			Game.mainFrame.requestFocus();
		});
		add(startBtn);
		
		add(new JLabel("Press space to start"));
		add(new JLabel("Press esc to pause"));
		
		JPanel speedPanel = new JPanel();
		add(speedPanel);
		
		speedPanel.add(new JLabel("Speed:"));
		
		JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(Board.speedScaler,0.1,5.0,0.1));
		((JSpinner.DefaultEditor)speedSpinner.getEditor()).getTextField().setColumns(2);
		((JSpinner.DefaultEditor)speedSpinner.getEditor()).getTextField().setEditable(false);
		speedSpinner.addChangeListener(e -> {
			Board.speedScaler = (double)speedSpinner.getValue();
			Board.resetGame();
		});
		speedPanel.add(speedSpinner);
		
		JPanel spacingPanel = new JPanel();
		add(spacingPanel);
		
		spacingPanel.add(new JLabel("Spacing:"));
		
		JSpinner spacingSpinner = new JSpinner(new SpinnerNumberModel(Pipe.spacing,1,5,1));
		((JSpinner.DefaultEditor)spacingSpinner.getEditor()).getTextField().setColumns(2);
		((JSpinner.DefaultEditor)spacingSpinner.getEditor()).getTextField().setEditable(false);
		spacingSpinner.addChangeListener(e -> {
			Pipe.spacing = (int)spacingSpinner.getValue();
			Board.resetGame();
		});
		spacingPanel.add(spacingSpinner);
		
		maxScoreLabel = new JLabel("Max Score: 0");
		add(maxScoreLabel);
		
		fpsLabel = new JLabel();
		add(fpsLabel);
		
		setVisible(true);
		this.setSize(startBtn.getWidth()*2, 250);
	}

	public void refreshFPSLabel() {
		fpsLabel.setText("FPS: " + Game.fps);
	}
	
	public void refreshMaxScoreLabel(){
		if(Game.player.getPoints() > maxScore){
			maxScoreLabel.setText("Max Score: " + Game.player.getPoints());
			maxScore = Game.player.getPoints();
		}
	}
}
