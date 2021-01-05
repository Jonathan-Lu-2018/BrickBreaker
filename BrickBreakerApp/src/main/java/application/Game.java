
package application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javafx.scene.input.KeyCode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * An abstract class Game that acts as a Game Engine
 */
public abstract class Game {

    protected final Stage stage;
    private final Timeline loop;
    private final String title;
    
    private Instant actualFrameTime = Instant.now();
    private KeyCode lastKey;
    
    /*
     * Creates the game stage 
     */
    public Game(Stage stage, Scene scene, String title, int fps) {
        this.stage = stage;
        this.title = title;
        
        stage.setScene(scene);
        stage.sizeToScene();
        
        Duration frameTime = Duration.millis(1000.0d / fps);
        KeyFrame frame = new KeyFrame(frameTime, (e) -> {
            this.update(this);
            this.actualFrameTime = Instant.now();
        });
        
        this.loop = new Timeline();
        this.loop.setCycleCount(-1);
        this.loop.getKeyFrames().add(frame);
    }
    
    /**
     * Runs the game
     */
    public void run() {
        this.attachKeyHandler(this.stage.getScene());
        this.loop.play();
    }
    
    /**
     * Stops the game
     */
    public void stop() {
        this.loop.stop();
    }
    
    /**
     * Gets the FPS
     * @return the FPS
     */
    public double getFPS() {
        return 1000.0d / this.actualFrameTime.until(Instant.now(), ChronoUnit.MILLIS);
    }
    
    /**
     * Manages the action when a key is pressed
     * @param scene the scene
     */
    private void attachKeyHandler(Scene scene) {
        scene.setOnKeyPressed((e) -> this.lastKey = e.getCode());
        scene.setOnKeyReleased((e) -> this.lastKey = null);
    }
    
    /**
     * Gets the key pressed
     * @return the key pressed
     */
    public KeyCode getKeyPressed() {
        return this.lastKey;
    }
    
    /**
     * Gets the title of the game
     * @return the title of the game
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Abstract method that updates the game
     * @param gameObj the game object
     */
    public abstract void update(Game gameObj);
  
}
