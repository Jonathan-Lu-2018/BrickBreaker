package application;
	
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * A Main class extending from Application that runs the JavaFX project
 * @param width the width constant
 * @param height the height constant
 * @param menuData the list pair for the menu item
 * @param root the Pane object
 * @param menuBox the VBox object
 * @param line a line
 *
 */

public class Main extends Application {
	private static final int WIDTH = 1280;	
    private static final int HEIGHT = 720;	

    // menuData uses lambda expression to be called
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Play", () -> {}),
            new Pair<String, Runnable>("Tutorial", () -> {}),
            new Pair<String, Runnable>("Leaderboard", () -> {}),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );
    
    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;
    
    private Parent createContent() {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    /**
     * Displays the background image 
     */
    private void addBackground() {
        ImageView imageView = new ImageView(new Image(getClass().getResource("img/blue.jpg").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }
    
    /**
     * Displays the title of the game 
     */
    private void addTitle() {
        BrickBreakerTitle title = new BrickBreakerTitle("BRICK BREAKER");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }
    
    /**
     * Creates a vertical line with a drop shadow effect
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 180);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    /**
     * Creates a scale animation on the line 
     */
    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }
    
    /**
     * Displays the main menu through a rectangular clip 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            BrickBreakerMenuItem item= new BrickBreakerMenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }
    
    /**
     * @param primaryStage the primary stage
     * @throws Exception catches error the application might run into
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
        primaryStage.setTitle("Break Breaker Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	/**
	 * Main method to run the application
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
