package com.wilsongateway.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class SaveManager {
	
	public static final String fileName = "flappySave.data";
	
	static boolean saveFileExists(){
		return new File(fileName).exists();
	}
	
	static boolean loadHighscores(){
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object obj = ois.readObject();

			if(obj instanceof LinkedList){
				LinkedList<Score> scores = (LinkedList<Score>) obj;
				Board.setHighscores(scores);
			}else{
				return false;
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	static boolean saveHighscores(LinkedList<Score> highscores){
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(highscores);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
