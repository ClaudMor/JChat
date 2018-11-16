package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class DealingThread implements Runnable {
	private Thread dealingthread;
	private Socket clientsocket;
	private BufferedReader br;
	private PrintWriter pr;
	private OutputStream out;

	public DealingThread(Socket socket) {
		this.dealingthread = new Thread(this, "DealingThread");
		this.clientsocket = socket;

		try {
			this.pr = new PrintWriter(clientsocket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dealingthread.start();

	}

	public void run() {
		String line;

		try {
			this.br = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			// this.out = clientsocket.getOutputStream();
			while (true) {
				if (!clientsocket.isClosed()) {
					line = br.readLine();
					System.out.println("message received: " + line);
					line += "\n";
					for (OutputStream out : Server.outputstreams) {
						out.write(line.getBytes());

					}

				} else {clientsocket.close();
					break;}

			} 

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.getMessage();

		}
	}
}
