package servers;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;

public class ServerSJoin implements Runnable {
	private Thread ServerThread;
	private int ServerPort = 8818;
	private String IPaddress = "192.168.0.30";
	private ServerSocket serversocket;
	public ArrayList<Socket> ClientInfoSArrayList = new ArrayList<Socket>();
	private InputStream ServerInputStream;

	public ServerSJoin() {

		ServerThread = new Thread(this, "ServerThread");
		// Istanzia il Server
		try {
			serversocket = new ServerSocket(ServerPort, 500, InetAddress.getByName(IPaddress));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServerThread.start();
		

	}

	public void ListenForJoinS() {
		// accetta le connessioni
		while (true) {
			try {
				
				Socket newClient = serversocket.accept();
				System.out.println("Client connected, Client IP address: " + newClient.getInetAddress() + "on port: "
						+ newClient.getLocalPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run() {
		ListenForJoinS();

	}

	public static void main(String[] args) {
		ServerSJoin serversjoin = new ServerSJoin();
	}
}
