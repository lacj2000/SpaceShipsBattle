package game;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;

public class Main {	
	
	
	public static Window config(int width, 
	 			int height, String name){
		Window gameWindow = new Window(width, height);
		gameWindow.setTitle(name);
		return gameWindow;
	}
	
	public static void introduction(Window gameWindow) {
		
		GameImage intImage01 = new GameImage(URL.sprite("game.png"));
		intImage01.draw();
		gameWindow.update();
	}
	
	
	public static void main(String[] args) {
		boolean mainState = false;
		int  width = 800, height = 600 ;
		Window gameWindow = config(width , height, "SpaceShip Arena Batle");
		Keyboard gameKeyboard = gameWindow.getKeyboard();
		try{
			
			while(true) {				
				if (gameKeyboard.keyDown(Keyboard.ENTER_KEY)) {
					mainState = true;
				}
				
				if (!mainState) {
					introduction(gameWindow);
				}else {
					new ConnectionMenu(gameWindow);
				}
				
			
			
				
			}		
		}catch (RuntimeException erro) {
			System.out.println("errp:"+erro.getMessage());
			erro.getLocalizedMessage();
		}
		catch (Exception erro) {
			System.out.println("erro:"+erro.getMessage());
		}
	}

}
