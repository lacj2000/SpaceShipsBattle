package game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPTakeMessages extends Thread {
	public InetAddress address;
	public SpaceShip player;
	public int port;
	DatagramSocket dt;
	
	
	public UDPTakeMessages(DatagramSocket dt,InetAddress address, int port, SpaceShip player) throws SocketException {
			this.address = address;
			this.player = player;
			this.port = port;
			this.dt = dt;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		int length = 3;
		byte[] receiveMessage = new byte[length];
		try { 
			while (true) {
				DatagramPacket packet = new DatagramPacket(receiveMessage, length);
				this.dt.receive(packet);
				if (packet.getAddress().equals(this.address)) {
					String command = new String(packet.getData());
					this.player.mover(command);
					
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
