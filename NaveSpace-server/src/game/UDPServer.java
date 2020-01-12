package game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPServer {
	ArrayList<InetAddress> clients = new ArrayList<>();
	public DatagramSocket socketServidor;

	public UDPServer(int port, int players) {
		try {
			socketServidor = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];
			for (int i = 0; i < players; i++) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socketServidor.receive(receivePacket);
				clients.add(receivePacket.getAddress());
				System.out.println("Cliente "+ i +" conectado no endereço " + clients.get(0).getHostAddress());
				if (i < players - 1) {
					System.out.println("Aguardando conexao cliente "+ (i + 1)+"...");
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public UDPServer(int port, InetAddress ip) {
		try {
			socketServidor = new DatagramSocket(port);
			clients.add(ip);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	
	public void sendMenssage(byte[] msg, InetAddress ip,int port)  {
		try {
			DatagramPacket dt = new DatagramPacket(msg, msg.length, ip, port);
			socketServidor.send(dt);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
