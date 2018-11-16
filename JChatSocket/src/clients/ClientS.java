package clients;

import java.io.IOException;
import java.net.*;


public class ClientS {
private Socket ClientSocket;
private InetSocketAddress ServerSocketAddress;
private int ClientPort = 101;
private int ServerPort = 100;


public void ClientS() {
	
	
	
	try {
		this.ServerSocketAddress = new InetSocketAddress(InetAddress.getByName("192.168.0.30"), ServerPort);
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	

	//istanzia il socket del client
	try {
		this.ClientSocket = new Socket(InetAddress.getByName("192.168.0.25"), ClientPort);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//Collega il client al server
	
	try {
		ClientSocket.connect(ServerSocketAddress);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void Write() {
	
	
}








	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
