package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class DataBaseManage extends Scene {

	private Label dataBaseLb = new Label("Choose DB:");
	private ChoiceBox<String> dataBaseCB = new ChoiceBox<>();
	private Label tableLb = new Label("Choose table:");
	private ChoiceBox<String> tablesCB = new ChoiceBox<>();
	private TextArea datashowTA = new TextArea();
	private LogIN logIN = new LogIN();
	private PreparedStatement chooseDB;
	private Connection conn= logIN.conn;

	public DataBaseManage(GridPane root) {
		super(root);
		drowDBManage(root);
		// TODO Auto-generated constructor stub
	}

	private void drowDBManage(GridPane grPane) {
		dataBaseCB.getItems().addAll("music", "javabook");
		dataBaseCB.setPrefWidth(100);
		tablesCB.setPrefWidth(100);

		dataBaseCB.setOnAction(fillTablesCBEHandler());

		grPane.setPrefSize(600, 600);
		grPane.setPadding(new Insets(5));
		grPane.setHgap(15);
		grPane.setVgap(10);
		grPane.addRow(0, dataBaseLb, dataBaseCB, tableLb, tablesCB);
		grPane.add(datashowTA, 0, 1, 4, 1);
	}
	
	private EventHandler<ActionEvent> fillTablesCBEHandler(){
		return (event) -> {
			if (dataBaseCB.getSelectionModel().getSelectedItem() == "music") {
				tablesCB.getItems().clear();
				tablesCB.getItems().addAll("album", "artist", "played", "track");
				chooseDB("music");
			}
			if (dataBaseCB.getSelectionModel().getSelectedItem() == "javabook") {
				tablesCB.getItems().clear();
				tablesCB.getItems().addAll("account", "address", "college", "country", "course", "csci1301", "csci1302",
						"csci4990", "department", "enrollment", "faculty", "person", "quiz", "scores", "staff",
						"statecapital", "student", "student1", "student2", "subject", "taughtby", "temp");
				chooseDB("javabook");
			}
		};
	}
	
	
	private void chooseDB(String dbName) {
		try {
			
			chooseDB = conn.prepareStatement("use ? if exists");
			chooseDB.setString(1, dbName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
