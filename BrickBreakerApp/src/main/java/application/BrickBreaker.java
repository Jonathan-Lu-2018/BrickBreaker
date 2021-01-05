
package application;

import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * A BrickBreaker class that serves as the main class of the game
*/
public class BrickBreaker extends Game {
    
    private boolean gameStarted, ballLaunched = false;
    final private int levelCount = 5;
    final private int maxLives = 5;
    final private double initialSpeed = 4;
    final private double paddleSpeed = 37d/3d;
    
    private int level = 1;
    private int livesRemaining = maxLives;
    
    private Group[] groups;
    private Label levelInd, livesInd, levelIndIn, livesIndIn;
    private Bricks bricks;
    private Paddle paddle;
    private Ball ball;
    
    
    /**
     * Creates a new game for BrickBreaker.
     * 
     * @param stage the stage.
     * @param rootNode the root node.
     */
    public BrickBreaker(Stage stage, Scene scene) {
        super(stage, scene, "Brick-Breaker", 60);
        
        this.groups = new Group[5];
        this.groups[0] = (Group)scene.lookup("#titleGroup");
        this.groups[1] = (Group)scene.lookup("#gameGroup");
        this.groups[2] = (Group)scene.lookup("#levelInterstitial");
        this.groups[3] = (Group)scene.lookup("#winInterstitial");
        this.groups[4] = (Group)scene.lookup("#lostInterstitial");
        
        this.levelInd = (Label)scene.lookup("#level");
        this.livesInd = (Label)scene.lookup("#lives");
        this.levelIndIn = (Label)scene.lookup("#levelIn");
        this.livesIndIn = (Label)scene.lookup("#livesIn");
        
        this.bricks = new Bricks((GridPane)scene.lookup("#bricks"));
        this.paddle = new Paddle((Rectangle)scene.lookup("#paddle"));
        this.ball = new Ball((Circle)scene.lookup("#ball"), this.bricks, this.paddle);
        
        this.bricks.addWinListener(this::levelUp);
        this.ball.addLossListener(this::loseLife);
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::toggleGameState);
        
    }

    // An overridden method from the Game class that updates the controls 
    @Override
    public void update(Game game) {
        if(this.gameStarted) {
            Boolean moved;
            if(game.getKeyPressed() == KeyCode.LEFT) {
                moved = this.paddle.animate(-paddleSpeed);
                if(!this.ballLaunched && moved) this.ball.setTranslateX(this.ball.getTranslateX() - paddleSpeed);
            }
            if(game.getKeyPressed() == KeyCode.RIGHT) {
                moved = this.paddle.animate(paddleSpeed);
                if(!this.ballLaunched && moved) this.ball.setTranslateX(this.ball.getTranslateX() + paddleSpeed);
            }
            
            if(this.ballLaunched) {
                this.ball.animate();
            }
        }
    }
    
    /** 
     * Sets the player's lives
     *
     * @param num the players lives
     */
    private void setLives(int num) {
        this.livesRemaining = num;
        this.livesInd.setText("" + this.livesRemaining);
        this.livesIndIn.setText("x " + this.livesRemaining);
    }
    
    /** 
     * Sets the player's level
     *
     * @param The level.
     */
    private void setLevel(int num) {
        this.level = num;
        this.levelInd.setText("Level " + this.level);
        this.levelIndIn.setText("Level " + this.level);
    }
    
    /**
     * Switches to the right screen.
     *
     * @param newGroup the right screen.
     */
    private void switchToGroup(int newGroup) {
        if(newGroup < this.groups.length && newGroup >= 0 && !isGroupVisible(newGroup)) {
            for(int i = 0; i < this.groups.length; i++) {
                this.groups[i].setVisible(i == newGroup);
            }
        }
    }
    
    /**
     * Checks if the screen is visible
     *
     * @param i the screen.
     * @return true if screen is visible & vice versa
     */
    private boolean isGroupVisible(int i) {
        if(i < this.groups.length && i >= 0) {
            return this.groups[i].isVisible();
        }
        else {
            return false;
        }
    }
    
    /**
     * Shows two screen before switching to another one
     * 
     * @param first the first screen
     * @param second the second screen.
     */
    private void showInterstitial(int first, int second) {
        switchToGroup(first);
        
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
                } 
                catch (InterruptedException e) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(e -> switchToGroup(second));
        new Thread(sleeper).start();
    }
    
    /**
     * Resets the state of the ball and paddle
     *
     * @param startingSpeed the starting speed of the ball object
     */
    private void resetPlayer(double startingSpeed) {
        this.ballLaunched = false;
        
        this.paddle.setTranslateX(0);
        this.ball.setStartingSpeed(startingSpeed);
        this.ball.setTranslateX(0);
        this.ball.setTranslateY(0);
    }
    
    /**
     * Resets the level
     *
     * @param startingSpeed the starting speed of the ball object
     */    
    private void resetLevel(double startingSpeed) {
        this.bricks.reset();
        resetPlayer(startingSpeed);
    }
    
    /**
     * Starts a new game
     */
    private void startGame() {
        showInterstitial(2, 1);
        
        this.gameStarted = true;
    }
    
    /**
     * Checks if the game is running.
     */
    private void stopGame() {
        if(this.gameStarted) {
            this.gameStarted = false;
            
            if(isGroupVisible(1)) {
                switchToGroup(0);
            }
            
            resetLevel(this.initialSpeed);
            setLives(this.maxLives);
            setLevel(1);
        }
        else {
            stop();
            this.stage.close();
        }
    }
    
    /**
     * Increments the player's level
     */
    private void levelUp() {
        if(this.gameStarted) {
            setLevel(this.level + 1);
            
            if(this.level == this.levelCount + 1) {
                showInterstitial(3, 0);
                stopGame();
            }
            else {
                showInterstitial(2, 1);
                resetLevel(this.level + 3);
            }
        }
        else {
            System.out.println("Invalid levelUp() event issued");
        }
    }
    
    /**
     * Decrements the player's level
     */
    private void loseLife() {
        if(this.gameStarted) {
            setLives(this.livesRemaining - 1);

            if(this.livesRemaining > 0) {
                showInterstitial(2, 1);
                resetPlayer(this.level + 3);
            }
            else {
                showInterstitial(4, 0);
                stopGame();
            }
        }
        else {
            System.out.println("Invalid loseLife() event issued");
        }
    }
    
    /**
     * Changes the game state of Brick-Bricker
     *
     * @param KeyEvent the event.
     */
    private void toggleGameState(KeyEvent e) {
        KeyCode currentKey = e.getCode();
        
        switch(currentKey) {
            case SPACE:
                if(!this.gameStarted && !isGroupVisible(3)) {
                    startGame();
                }

                else if(this.gameStarted && !this.ballLaunched && !isGroupVisible(2)) {
                    this.ballLaunched = true;
                }
                break;
            case ESCAPE:
                if(isGroupVisible(0) || isGroupVisible(1)) {
                    stopGame();
                }
                break;

        }
    }

}
