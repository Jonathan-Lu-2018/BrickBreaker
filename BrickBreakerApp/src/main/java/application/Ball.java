
package application;

import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.geometry.Bounds;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * A Ball class that handles the animation of the ball
 */
public class Ball {
    
    private Circle ball;
    private double dx;
    private double dy;
    private Bricks bricks;
    private Paddle paddle;
    
    private List<Levels> lossListeners = new ArrayList<Levels>();
    
    /**
     * A constructor that creates the ball object.
     *
     * @param ball the ball 
     * @param bricks the bricks
     * @param paddle the paddle.
     * @param speed the speed of the ball.
     */
    public Ball(Circle ball, Bricks bricks, Paddle paddle, double speed) {
        this.ball = ball;
        this.bricks = bricks;
        this.paddle = paddle;
        setStartingSpeed(speed);
    }
    
    /**
     * Another constructor that initializes the speed of the ball.
     *
     * @param ball the ball
     * @param bricks the bricks.
     * @param paddle the paddle.
     */
    public Ball(Circle ball, Bricks bricks, Paddle paddle) {
        this(ball, bricks, paddle, 4);
    }
    
    /**
     * Sets the starting speed of the ball object
     *
     * @param speed the speed of the ball object
     */
    public void setStartingSpeed(double speed) {
        this.dx = (new Random()).nextBoolean() ? speed : -speed;
        this.dy = -speed;
    }
    
    /**
     * Keeps track of when the player loses
     *
     * @param newListener tracker for when the player loses
     */
    public void addLossListener(Levels newListener) {
        this.lossListeners.add(newListener);
    }
    
    /**
     * Animates the ball when it comes in contact with a brick.
     */
    public void animate() {
        this.ball.setTranslateX(this.ball.getTranslateX() + dx);
        this.ball.setTranslateY(this.ball.getTranslateY() + dy);
        
        Bounds bounds = this.ball.getBoundsInParent();
        Pane canvas = (Pane)this.ball.getParent();
        final boolean atTopBorder = bounds.getMinY() <= 0;
        final boolean atRightBorder = bounds.getMaxX() >= canvas.getWidth();
        final boolean atBottomBorder = bounds.getMaxY() >= canvas.getHeight();
        final boolean atLeftBorder = bounds.getMinX() <= 0;
        
        final int atBrick = this.bricks.checkCollision(this.ball);
        final int atPaddle = this.paddle.checkCollision(this.ball);
        
        if(atLeftBorder || atRightBorder || atBrick == -1) dx *= -1;
        if(atTopBorder || atBrick == 1 || atPaddle == 1) dy *= -1;
        
        if(atBottomBorder) {
            for(Levels ls : this.lossListeners) {
                ls.handleLevelingEvent();
            }
        }
    }
    
    /**
     * Gets the ball's x coordinate
     *
     * @return the ball's x coordinate
     */
    public double getTranslateX() { 
        return this.ball.getTranslateX(); 
    }

    /**
     * Gets the ball's y coordinate
     *
     * @return the ball's y coordinate
     */    
    public double getTranslateY() { 
        return this.ball.getTranslateY(); 
    }
 
     /**
     * Sets the ball's x coordinate
     *
     * @param x the ball's x coordinate
     */   
    public void setTranslateX(double x) { 
        this.ball.setTranslateX(x); 
    }
    
    /**
     * Sets the ball's y coordinate
     *
     * @param y the ball's y coordinate
     */     
    public void setTranslateY(double y) { 
        this.ball.setTranslateY(y); 
    }
    
    /**
     * Gets the Circle object of the ball
     *
     * @return the Circle object of the ball.
     */
    public Circle getNode() {
        return this.ball;
    }
    
}
