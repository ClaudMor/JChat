package server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server implements Runnable {
	public Thread ServerThread;
	int JoinPort = 100;
	int BroadcastPort = 101;
	private InetAddress ServerInetAddress;
	private DatagramSocket BroadcastSocket;
	private DatagramSocket JoinSocket;
	private DatagramPacket JoinPacket;
	private DatagramPacket BroadcastPacket;
	private boolean running = true;
	private ClientInfo clientinfo;
	public String LocalIP = "192.168.0.30";
	public String ExternalIP = "2.39.29.61";
	private ArrayList<ClientInfo> ClientInfoArrayList = new ArrayList<ClientInfo>();
	// public InetSocketAddress inetsocketaddress = new InetSocketAddress();

	public Server() throws UnknownHostException {
		ServerThread = new Thread(this, "ServerThread");
		// this.ServerInetAddress = InetAddress.getByName(LocalIP);
		this.ServerInetAddress = InetAddress.getByName(LocalIP);

		// this.ServerDatagramSocket = new DatagramSocket(ServerPort,ServerInetAddress);
		try {
			JoinSocket = new DatagramSocket(JoinPort, ServerInetAddress);
			BroadcastSocket = new DatagramSocket(BroadcastPort, ServerInetAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("ServerDatagramSocket started, listening on port " +
		// ServerPort);

		System.out.println(JoinSocket.getPort());
		ServerThread.start();
	}

	private void ListenForJoin() {
		Thread ListenForJoinThread = new Thread("ListenForJoinThread") {
			
		public void run() {
		while (running) {
			byte[] JoinData = new byte[1024];
			JoinPacket = new DatagramPacket(JoinData, JoinData.length);
			try {
				System.out.println("Waiting for ClientDatagramPacket");
				JoinSocket.receive(JoinPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				clientinfo = new ClientInfo(JoinPacket.getAddress(), JoinPacket.getPort(),
						new String(JoinPacket.getData(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ClientInfoArrayList.add(clientinfo);
			System.out.println("Client connected. IP: "+ clientinfo.getClientInetAddress()+"port:" + clientinfo.getClientPort()+"name: "+ clientinfo.getClientName());

		}
		JoinSocket.close();
		}
		}; ListenForJoinThread.start();
	
	
	}

	public void Broadcast() throws IOException {
		Thread BroadcastThread = new Thread("BroadcastThread") {
			
		public void run() {
		while (running) {
			byte ReceivedBytes[] = new byte[10240]; // bytes ricevuti
			BroadcastPacket = new DatagramPacket(ReceivedBytes, ReceivedBytes.length);

			try {
				
				BroadcastSocket.receive(BroadcastPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
System.out.println("message received");
			for (int i = 0; i < ClientInfoArrayList.size(); i++) {
				BroadcastPacket.setPort(ClientInfoArrayList.get(i).getClientPort());
				BroadcastPacket.setAddress(ClientInfoArrayList.get(i).getClientInetAddress());
				BroadcastPacket.setData(
						new String(ClientInfoArrayList.get(i).getClientName()+ ": "+ BroadcastPacket.getData().toString())
								.getBytes());
				try {
					BroadcastSocket.send(BroadcastPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}
	}; BroadcastThread.start();
	}

	public void ShutDown() {
		this.running = false;
	}

	@Override
	public void run() {
		ListenForJoin();
		try {
			Broadcast();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			Server server = new Server();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
