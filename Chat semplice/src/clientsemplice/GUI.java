package clientsemplice;

import java.io.PrintWriter;

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

public class GUI extends Application {
	
	public Stage stage;
	private Scene scene;
	private BorderPane borderpane = new BorderPane();
	private HBox BottomBox = new HBox(10);
	private VBox LeftBox = new VBox(10);
	private VBox RightBox = new VBox(10);
	private Button SendButton = new Button("Invia");
	private Button ExitButton = new Button("Esci");
	private Label label = new Label("Users online");
	static TextField textfield = new TextField();
	static TextArea textarea = new TextArea();
	
	/*private PrintWriter pr;
	
	public GUI(PrintWriter Passedpr) {
		this.pr = Passedpr;
		
	}*/
	
	
	
	
	@Override
	public void start(Stage stage1) throws Exception {
		// TODO Auto-generated method stub
		SendButton.setOnAction(e -> {
		textarea.appendText(textfield.getText()+"\n");
		textfield.clear();
		});
		
		textarea.setWrapText(true);
		textarea.setEditable(false);
	
		
		BottomBox.getChildren().addAll(textfield,SendButton);
		LeftBox.getChildren().addAll(label);
		RightBox.getChildren().addAll(ExitButton);
		
		borderpane.setCenter(textarea);
		borderpane.setBottom(BottomBox);
		borderpane.setLeft(LeftBox);
		borderpane.setRight(RightBox);
		
		scene = new Scene(borderpane);
		stage1.setScene(scene);
		stage1.setTitle("Chat");
		stage1.show();
		
		
		
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}
