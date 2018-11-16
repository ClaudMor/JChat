package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
	private ServerSocket serversocket;
	private int ServerPort = 1500;
	public Thread ServerThread;
	static ArrayList<OutputStream> outputstreams = new ArrayList<OutputStream>();

	public Server() {
		ServerThread = new Thread(this, "ServerThread");

		try {
			serversocket = new ServerSocket(ServerPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServerThread.start();

	}

	public void Listen() {
		while (true) {
			try {
				Socket newclient = serversocket.accept();
				System.out.println("Client connected");
				outputstreams.add(newclient.getOutputStream());
				new DealingThread(newclient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run() {
Listen();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
	}

}
