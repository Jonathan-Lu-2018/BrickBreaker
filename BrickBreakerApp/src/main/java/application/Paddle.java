
package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.geometry.Bounds;

/**
 * A Paddle class that handles the animation of the paddle object
 */
public class Paddle {
    
    private Rectangle paddle;
    
    /**
     * A constructor that creates the paddle object
     * @param paddle the paddle object
     */
    public Paddle(Rectangle paddle) {
        this.paddle = paddle;
    }
    
    /**
     * Moves the paddle from left to right
     * @param dx the x position
     * @return true if paddle moved & vice versa
     */
    public boolean animate(double dx) {
        Bounds bounds = this.paddle.getBoundsInParent();
        final boolean atLeftBorder = bounds.getMinX() + dx <= 0;
        final boolean atRightBorder = bounds.getMaxX() + dx >= ((Pane)this.paddle.getParent()).getWidth();
        
        if (!atLeftBorder && !atRightBorder) {
            this.paddle.setTranslateX(this.paddle.getTranslateX() + dx);
            return true;
        }
        else {
            return false;
        }
    }
    
    /** 
     * Checks if there is a collision between the paddle and ball
     * @param ball the ball object
     * @return 1 for collision &  0 for vice versa
     */
    public int checkCollision(Circle ball) {
        Bounds ballBounds = ball.getBoundsInParent();
        Bounds paddleBounds = this.paddle.getBoundsInParent();
        
        final double insideY = ballBounds.getMaxY() - paddleBounds.getMinY();
        
        if(ballBounds.intersects(paddleBounds)) {
            if(insideY > 3) {
                ball.setTranslateY(-(insideY - 3));
            }
            
            return 1;
        }
        
        return 0;
    }   
    
    /**
     * Gets the paddle's x coordinate
     * @return the paddle's x coordinate
     */
    public double getTranslateX() { 
        return this.paddle.getTranslateX(); 
    }
    
    /**
     * Sets the paddle's x coordinate
     * @param x the paddle's x coordinate
     */   
    public void setTranslateX(double x) { 
        this.paddle.setTranslateX(x); 
    }
    
    /**
     * Gets the Rectangle object of the paddle
     * @return the Rectangle object of the paddle
     */
    public Rectangle getNode() {
        return this.paddle;
    }
    
}
