package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class LogIN extends Application {

	private Label userNameLb = new Label("User Name:");
	private Label passwordLb = new Label("PassWord :");
	private TextField usernameTF = new TextField();
	private PasswordField passwordPF = new PasswordField();
	private ImageView logInImaig = new ImageView(new Image(getClass().getResourceAsStream("/checked.png")));
	private Button logInBTn = new Button("LogIn");
	private Button cancelBTn = new Button("Cancel");
	private GridPane root = new GridPane();
	private Stage primaryStage;
	public Connection conn;
	//private DataBaseManage dbManaga = new DataBaseManage(new GridPane());

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		drowScene();

		usernameTF.addEventHandler(KeyEvent.ANY, dbConnectionEHandler());
		passwordPF.addEventHandler(KeyEvent.ANY, dbConnectionEHandler());
		logInBTn.addEventHandler(MouseEvent.MOUSE_CLICKED, changeSceneEHandler());
		cancelBTn.addEventHandler(MouseEvent.MOUSE_CLICKED, closeEHandler());

		try {
			Scene scene = new Scene(root, 600, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private EventHandler<Event> dbConnectionEHandler() {
		return event -> {
			try {
				dbConnection();
				logInImaig.setOpacity(1);
			} catch (SQLException e) {
				logInImaig.setOpacity(.1);
			}
		};
	}
	
	private EventHandler<Event> changeSceneEHandler() {
		return event -> {
			if (isConnected()) {
				primaryStage.setScene(new DataBaseManage(new GridPane()));
			} else
				connectionError();
		};
	}
	
	private void connectionError() {
			Alert alert = new Alert(AlertType.ERROR, "UserName Or password is incorrect !!!", ButtonType.OK);
			alert.setHeaderText("Oppes!!");
			alert.setTitle("Connection Error!");
			alert.show();
	}
	
	private EventHandler<Event> closeEHandler() {
		return event -> {
			primaryStage.close();
		};
	}

	private void drowScene() {
		root.setVgap(15);
		root.setHgap(15);
		root.setPadding(new Insets(150, 150, 150, 150));
		root.addRow(0, userNameLb, usernameTF);
		root.addRow(1, passwordLb, passwordPF);
		root.addRow(2, cancelBTn, logInImaig, logInBTn);

		logInImaig.setOpacity(.1);
	}

	private Connection dbConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String user, password, url;
		user = usernameTF.getText();
		password = passwordPF.getText();
		url = "jdbc:mysql://localhost:3306/";
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	private boolean isConnected() {
		boolean check;
		try {
			dbConnection();
			check = true;
		} catch (SQLException e) {
			check = false;
		}
		return check;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
