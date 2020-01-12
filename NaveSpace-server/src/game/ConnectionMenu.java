package game;



import java.net.SocketException;

import javax.swing.JOptionPane;

import game.GameException;
import game.IP;
import jplay.GameImage;
import jplay.URL;
import jplay.Window;

public class ConnectionMenu {
	public Window gameWindow;
	public GameImage backgroudImage = new GameImage(URL.sprite("menu.png"));
	public boolean state = false;
	UDPServer servidor = null;
	public String ip;
	public int port = 5000;
	public int players = 2;
	 
	public ConnectionMenu(Window gameWindow) throws SocketException {
		this.gameWindow = gameWindow;
		AudioEffect.play("carregamento.wav");
		try {
			this.players = Integer.parseInt(JOptionPane.showInputDialog(null ,"Digite o número de players: MIN=2 MAX = 5"));
			if (this.players > 5) {
				this.players = 5;
			}
			if (this.players <= 1) {
				this.players = 2;
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try{
			ip = new IP().getIp() ;
		}catch (GameException e) {
			ip = e.getMessage();
		}
		
		run();
	}
	
	
	private void run() throws SocketException {
		while(true) {			
			if (!state) {
				backgroudImage.draw();
				gameWindow.drawText("IP: "+ip+" Porta: "+port, 300, 60, null);
				gameWindow.drawText("Aguardando "+this.players+" players...", 303, 80, null);
				gameWindow.update();
				servidor = new UDPServer(port, players);
				System.out.println("ok");
				this.state = true;
			}else {
				new Game(gameWindow, servidor, players);
			}
		}
	}
	
}
