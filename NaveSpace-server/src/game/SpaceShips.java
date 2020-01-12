package game;

import java.net.InetAddress;
import java.util.ArrayList;


import jplay.Window;

public class SpaceShips {
	ArrayList<SpaceShip> ships = new ArrayList<SpaceShip>();
	public StartPositions positions = new StartPositions();
	Window gameWindow;
	
	public SpaceShips(int players, ProjectileControler normalShots, ArrayList<InetAddress> address, Window gameWindow) {
		this.gameWindow = gameWindow;
		for (int j = 0; j < players; j++) {
			System.out.println("nave "+j+ " ok");
			ships.add(new SpaceShip(positions.positions.get(j).x, positions.positions.get(j).y, j, normalShots, address.get(j)));
		} 
	}
	public void run() {
		int lives = 0;
		for (SpaceShip i : ships) {
			if (i.isLife()) {
				i.draw();
				lives++;
			}
			
		}
		if (lives == 1){
			for (SpaceShip i : ships) {
				if (i.isLife()) {
					new WinnerWindow(gameWindow ,i);
				}
			}
		}
		
	}
	
	public void counterReducer() {
		for (SpaceShip i : ships) {
			i.reducerCDR();
		}
	}
}
