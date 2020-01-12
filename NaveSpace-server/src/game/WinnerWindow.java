package game;

import java.awt.Color;
import java.awt.Font;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;
	
public class WinnerWindow {
	public Window gameWindow = null;
	public SpaceShip ship;
	public GameImage image = new GameImage(URL.sprite("white.jpg"));
	
	public WinnerWindow(Window window, SpaceShip ship) {
		this.gameWindow = window;
		this.ship = ship;
		
		start();
	}
	private void start() {
		Font f = new Font(Font.MONOSPACED,Font.BOLD , 20);
		Font f7 = new Font(Font.MONOSPACED,Font.BOLD , 12);
		
		image.draw();
		
		gameWindow.drawText("Vencedor : " + this.ship.address.toString(), 280, 300, Color.BLACK, f);
		gameWindow.drawText("pressione ESC para Sair...", 360, 500, Color.BLACK, f7);

		gameWindow.update();
		if(gameWindow.getKeyboard().keyDown(Keyboard.ESCAPE_KEY)) {
			System.exit(0);
		}
	}
	
}
