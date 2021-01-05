package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.text.*;


/**
 * A Tutorial class that manages the tutorial page
 */
public class Tutorial {

	/**
	 * Display the tutorial page
	 * @param title the title display
	 * @param message the message display
	 */
	public static void display(String title, String message) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(610);
		window.setHeight(590);

		Label label = new Label();
		label.setText(message);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(Font.font("Stencil", FontWeight.BOLD, 18));

		Button closeButton = new Button("Back to Main Menu");
		closeButton.setOnAction(e -> window.close());

		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);

		VBox layout = new VBox(60);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		layout.setBackground(background);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

	}

}
