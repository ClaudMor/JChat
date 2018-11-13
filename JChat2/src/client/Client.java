package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class Client implements Runnable {
	public Thread ClientThread;
	private int ClientPort = 107;
	private InetAddress ClientInetAddress;
	private DatagramSocket ClientSocket;
	private DatagramPacket Packet;
	private DatagramPacket OutPacket;
	private boolean running = true;
	private String ServerIP = "2.39.29.61";
	public InetAddress ServerInetAddress;

	public Client() {
		this.ClientThread = new Thread(this, "ClientThread");
		try {
			this.ClientInetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.ClientSocket = new DatagramSocket(ClientPort, ClientInetAddress);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientThread.start();
	}

	private void Join() throws IOException {
		String name = null;
		System.out.println(" Please enter your username");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		name = br.readLine();
		byte[] msgByte = name.getBytes();
		Packet = new DatagramPacket(msgByte, msgByte.length, InetAddress.getByName("192.168.0.30"), 100);
		try {
			ClientSocket.send(Packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to send message");
		}
		//System.out.println("message sent");

	}

	private void Write() throws IOException {
		Thread WriteThread = new Thread("WriteThread") {
			public void run() {
		while (running) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			try {
				System.out.println("type your message");
				msg = br.readLine();
		
			byte msgByte[] = msg.getBytes();
			OutPacket = new DatagramPacket(msgByte, msgByte.length, InetAddress.getByName("192.168.0.30"), 100);
			ClientSocket.send(OutPacket);
			}catch( IOException e) {
				System.out.println("Write failed");
			}
		}
			}
	};WriteThread.start();
	}
	
	private void Receive() throws IOException {
		Thread ReceiveThread = new Thread("ReceiveThread") {
			
		public void run() {
		while(running) {
			byte ReceivedMessage[] = new byte[10240];
			Packet = new DatagramPacket(ReceivedMessage,ReceivedMessage.length);
			try {
			ClientSocket.receive(Packet);
			System.out.println(new String(Packet.getData(),"UTF-8"));
			}catch(IOException e) {
				System.out.println("Receive failed, probably got too long a message");
			}
		}
	}
	};ReceiveThread.start();
}

	public void run() {
		try {
			Join();
			Write();
			Receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
	}

}
