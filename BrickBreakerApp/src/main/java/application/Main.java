package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * A Main class used to run the JavaFX application
 */
public class Main extends Application {

	Stage window;
	Button play, tutorial, rankings, closeButton;
	Label title;

	/**
	 * An overridden method from Application class that starts the stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Brick-Breaker");

		InnerShadow is = new InnerShadow();

		Text title = new Text();
		title.setEffect(is);
		title.setText("BRICK BREAKER");
		title.setFill(Color.MAROON);
		title.setFont(Font.font(null, FontWeight.BOLD, 80));
		title.relocate(120, 150);

		play = new Button("Play Game");
		play.relocate(345, 450);

		play.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				Parent root = null;

				try {
					root = FXMLLoader.load(getClass().getResource("/brickbreaker.fxml"));
				} catch (java.io.IOException e) {
					System.out.println(e);
					System.exit(1);
				}

				Scene scene = new Scene(root, 640, 600);
				Game game = new BrickBreaker(primaryStage, scene);
				primaryStage.setTitle(game.getTitle());
				primaryStage.setResizable(false);

				primaryStage.show();
				game.run();
			}

		});

		rankings = new Button("Leaderboard");
		rankings.relocate(340, 550);

		rankings.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Parent root = null;

				try {
					root = FXMLLoader.load(getClass().getResource("/table.fxml"));
				} catch (java.io.IOException e) {
				}

				Scene scene = new Scene(root, 600, 400);
				Game game = new BrickBreaker(primaryStage, scene);
				primaryStage.setTitle(game.getTitle());
				primaryStage.setResizable(false);

				primaryStage.show();

			}

		});

		tutorial = new Button("Tutorial");
		tutorial.relocate(355, 500);

		tutorial.setOnAction(e -> {
			Tutorial.display("Tutorial",
					"Welcome to Brick-Breaker!" + "\n" + "\nHere’s how to play the game:" + "\n"
							+ "\nYour goal is to knock out all the bricks on the screen by moving the paddle. "
							+ "\n\nLaunch the ball by pressing the SPACE bar."
							+ "\nControl the paddle using the LEFT and RIGHT arrow keys on your keyboard." + "\n"
							+ "\nDon't let the ball hit the bottom of the screen, otherwise you lose a life."
							+ "\nYou have 5 lives."
							+ "\n\nIf you lose, press ESC to quit and restart the game "
							+ "\nto enter your score in the Leaderboard"
							+"\n\nHave fun!! :)");

		});

		closeButton = new Button("Close game");
		closeButton.setOnAction(e -> window.close());
		closeButton.relocate(343, 600);

		Image img = new Image("file:back.jpeg");
		ImageView mv = new ImageView(img);

		Pane layout = new Pane();
		layout.getChildren().addAll(mv, title, play, tutorial, rankings, closeButton);
		Scene scene = new Scene(layout, 800, 800);
		window.setScene(scene);
		window.setResizable(false);
		window.show();

	}

	/**
	 * An overridden method that stops the application
	 */
	@Override
	public void stop() throws Exception {
		System.exit(0);
	}

	/**
	 * Main method to run the application
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
