package clientsemplice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application implements Runnable {

	public Stage stage;
	private Scene scene;
	private BorderPane borderpane = new BorderPane();
	private HBox BottomBox = new HBox(10);
	private VBox LeftBox = new VBox(10);
	private VBox RightBox = new VBox(10);
	private Button SendButton = new Button("Invia");
	private Button ExitButton = new Button("Esci");
	private Label label = new Label("Users online");
	private TextField textfield = new TextField();
	private TextArea textarea = new TextArea();

	private Thread ClientThread;
	private Socket ClientSocket;
	private int ClientPort = 1500;
	private BufferedReader br;
	private BufferedReader br1;
	static PrintWriter pr;
	private OutputStream out;
	private boolean running = true;

	public Client() {
		this.ClientThread = new Thread(this, "ClientThread");
		try {
			
			ClientSocket = new Socket(InetAddress.getLocalHost(), ClientPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			br = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			pr = new PrintWriter(ClientSocket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br1 = new BufferedReader(new InputStreamReader(System.in));

		try {
			out = ClientSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ClientThread.start();

	}

	public void start(Stage stage1) throws Exception {
		// TODO Auto-generated method stub
		stage = stage1;
		SendButton.setOnAction(e -> {
			String s;
			try {
				s = textfield.getText() + "\n";
				out.write(s.getBytes());
				textfield.clear();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		ExitButton.setOnAction(e -> {
			closeprog();

		});

		stage.setOnCloseRequest(e -> {
			e.consume();
			closeprog();
			;
		});

		textarea.setWrapText(true);
		textarea.setEditable(false);

		BottomBox.getChildren().addAll(textfield, SendButton);
		LeftBox.getChildren().addAll(label);
		RightBox.getChildren().addAll(ExitButton);

		borderpane.setCenter(textarea);
		borderpane.setBottom(BottomBox);
		borderpane.setLeft(LeftBox);
		borderpane.setRight(RightBox);

		scene = new Scene(borderpane);
		stage.setScene(scene);
		stage.show();

	}

	public void Speak(String s) {
		// Thread SpeakThread = new Thread(this, "SpeakThread") {
		// public void run() {
		// while(true) {
		/* System.out.println("inserisci il messaggio" + "\n"); */
		// String text;
		// try {
		// text = br1.readLine();
		pr.println(s);
		// } catch (IOException e1) {
		// TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// };
		// }
		// };SpeakThread.start();
	}
	

	public void Receive() {
		// Thread ReceiveThread = new Thread(this,"ReceiveThread") {
		// public void run() {
		String text1;
		while (running) {
			try {
				text1 = br.readLine();
				textarea.appendText(text1 + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ciao ciao");
			}

		}
		System.out.println("Receive expired");
	}
//};ReceiveThread.start();

	// }

	public void run() {
		// Speak();
		Receive();
	}

	public void closeprog() {

		try {
			running = false;
			out.write("Exit request detected".getBytes());
			// br.close();
			ClientSocket.close();
			System.exit(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Client client = new Client();
		launch();
	}

}
