package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

/**
 * A Controller class that manages the table for the leader board
 */
public class Controller implements Initializable {

	@FXML
	TableView<Player> tableview;

	@FXML
	TableColumn<Player, String> colName;
	@FXML
	TableColumn<Player, String> colDate;
	@FXML
	TableColumn<Player, Integer> colLevel;

	/**
	 * An overridden method from the Initializable class that creates the table
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colName.setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("PlayerDate"));
		colLevel.setCellValueFactory(new PropertyValueFactory<>("PlayerLevel"));
		tableview.setItems(observableList);

	}

	ObservableList<Player> observableList = FXCollections.observableArrayList(
			new Player("name", "date", 0)
			);

	@FXML
	TextField textfieldName;

	@FXML
	TextField textfieldDate;

	@FXML
	TextField textfieldLevel;

	/**
	 * Adds information to table
	 * @param event the action that occurs
	 */
	public void buttonAdd(ActionEvent event) {
		Player player = new Player(textfieldName.getText(), textfieldDate.getText(), Integer.parseInt(textfieldLevel.getText()));
		tableview.getItems().add(player);
	}

	/**
	 * Deletes information from table
	 * @param event the action that occurs
	 */
	public void buttonDelete(ActionEvent event) {
		ObservableList<Player> allProduct, SinglePlayer;
		allProduct = tableview.getItems();
		SinglePlayer = tableview.getSelectionModel().getSelectedItems();
		SinglePlayer.forEach(allProduct::remove);
	}

}
