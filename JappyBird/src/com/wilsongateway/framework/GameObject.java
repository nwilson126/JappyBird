package com.wilsongateway.framework;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class GameObject {
	
//	private static ArrayList<? extends GameObject> pipeline;
//	
//	protected <T extends GameObject> GameObject(Class<T> c){
//		pipeline = new ArrayList<T>();
//	}
//	
//	public void paintObjects(Graphics2D g2d){
//		for(GameObject object : pipeline){
//			if(object.canPaint()){
//				object.paint(g2d);
//			}
//		}
//	}
//	
//	public void moveObjects(){
//		for(GameObject object : pipeline){
//			if(object.canMove()){
//				object.move();
//			}
//		}
//	}
//	
//	public static GameObject get(int i){
//		return pipeline.get(i);
//	}
//	
//	public void remove(int i){
//		return pipeline.get(i);
//	}
	
	public abstract void paint(Graphics2D g2d);
	
	public abstract void move();

//	protected abstract boolean canPaint();
//	
//	protected abstract boolean canMove();
}
