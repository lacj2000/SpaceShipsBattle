package game;

import java.net.SocketException;
import java.util.ArrayList;

import jplay.GameImage;
import jplay.URL;
import jplay.Window;

public class Game {
	public Window gameWindow = null;
	public ArrayList<Integer> ports = new ArrayList<>();
	public UDPServer server = null;
	public ArrayList<UDPTakeMessages> takeMessages = new ArrayList<>();
	public SpaceShips ships = null;
	public ProjectileControler normalShots;
	public int players;
	
	
	public GameImage background01 = new GameImage(URL.sprite("800x600_starry-sky-stars-space-glitter.jpg"));

	
	
	public Game(Window gameWindow, UDPServer serverUDP, int players) throws SocketException {
		this.players = players;
		
		this.gameWindow = gameWindow;
				
		int port = 5000;
		
	
		this.server = serverUDP;
		
		
		// criar thread de recebimento
		normalShots = new ProjectileControler();
		ships = new SpaceShips(players, normalShots, server.clients, gameWindow);
		for (int i = 0; i <  players; i++) {
			takeMessages.add(new UDPTakeMessages(server.socketServidor, server.clients.get(0), port, ships.ships.get(i)));
		}
		AudioEffect.play("game.wav");
		
		start();
	}
	
	
	
	private void start() {
		for (int h = 0; h < this.players; h++) {
			takeMessages.get(h).start();
		}
		Temporize timer = new Temporize(ships);
		while(true) {
			//System.out.println(timer.hours());
			drawAll();
		}
		
	}
	public void drawAll() {
		try {
			background01.draw();
			ships.run();
			normalShots.run(ships);
			gameWindow.update();
			gameWindow.delay(20);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
