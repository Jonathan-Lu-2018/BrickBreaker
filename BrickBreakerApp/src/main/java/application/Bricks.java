
package application;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Region;

/**
 * A Bricks class that handles the brick objects
 */
public class Bricks {
    
    private GridPane bricks;
    private List<Node> brickList;
    private int[][] damage;
    private int bricksCleared;
    
    private final List<String> damageStyles = Arrays.asList("damage-1", "damage-2", "damage-3");
    private List<Levels> winListeners = new ArrayList<Levels>();

    /**
     * Constructor of the Bricks class that initializes the brick objects
     * @param bricks the bricks
     */
    public Bricks(GridPane bricks) {
        this.bricks = bricks;
        this.brickList = bricks.getChildren();
        this.damage = new int[4][6];
    }
    
    /**
     * Keeps track of when all the bricks are cleared
     * @param newListener tracker when all the bricks are cleared
     */
    public void addWinListener(Levels newListener) {
        this.winListeners.add(newListener);
    }
    
    /**
     * Checks for a collision between the ball and brick
     * @param ball the ball object
     * @return 1 for a horizontal hit, -1 for a vertical hit, and 0 for no hit
     */
    public int checkCollision(Circle ball) {
        Bounds ballBounds = ball.getBoundsInParent();
        final double ballMinX = ballBounds.getMinX();
        final double ballMinY = ballBounds.getMinY();
        final double ballMaxX = ballBounds.getMaxX();
        final double ballMaxY = ballBounds.getMaxY();
        
        final boolean atBricksTop = ballMinY >= this.bricks.getLayoutY() - ball.getRadius() * 2;
        final boolean atBricksBottom = ballMaxY <= (this.bricks.getLayoutY() + this.bricks.getHeight()) + ball.getRadius() * 2;
        
        if (atBricksTop && atBricksBottom) {
            for(int i = this.brickList.size() - 1; i >= 0; i--) {
                Region brick = (Region)this.brickList.get(i);
                if(!brick.isVisible()) {
                    continue;
                }
                
                Bounds brickBounds = brick.getBoundsInParent();
                final double brickMinX = this.bricks.getLayoutX() + brickBounds.getMinX();
                final double brickMinY = this.bricks.getLayoutY() + brickBounds.getMinY();
                final double brickMaxX = brickMinX + brickBounds.getWidth();
                final double brickMaxY = brickMinY + brickBounds.getHeight();
                
                final boolean insideX = ballMaxX >= brickMinX && ballMinX <= brickMaxX;
                final boolean insideY = ballMaxY >= brickMinY && ballMinY <= brickMaxY;
                
                if(insideX && insideY) {
                    final boolean atTop = ballMinY < brickMinY;
                    final boolean atBottom = ballMaxY > brickMaxY;
                    final boolean atLeft = ballMinX < brickMinX;
                    final boolean atRight = ballMaxX > brickMaxX;
                    
                    if(atTop || atBottom) {
                        increaseDamage(brick);
                        return 1;
                    }
                    if(atLeft || atRight) {
                        increaseDamage(brick);
                        return -1;
                    }
                }
            }
        }
        
        return 0;
    }
    
    /** 
     * Increments the damage level of the bricks
     * @param brick the bricks.
     */
    public void increaseDamage(Region brick) {
        List<String> styles = brick.getStyleClass();
        int row = Integer.valueOf(styles.get(0).substring(4));
        int col = Integer.valueOf(styles.get(1).substring(4));
        
        this.damage[row][col]++;
        if(this.damage[row][col] == 3) {
            brick.setVisible(false);
            this.bricksCleared++;
            
            if(isCleared()) {
                for(Levels ls : winListeners) {
                    ls.handleLevelingEvent();
                }
            }
        }
        
        styles.removeAll(this.damageStyles);
        styles.add("damage-" + this.damage[row][col]);
        
        System.out.println(Arrays.toString(styles.toArray()));
    }
    
    /**
     * Checks to see if all bricks are cleared
     * @return true for cleared bricks & vice versa
     */
    public boolean isCleared() {
        System.out.println(bricksCleared + " bricks cleared");
        return this.bricksCleared == this.brickList.size();
    }
    
    /**
     * Resets the brick objects
     */
    public void reset() {
        for(Node brick : this.brickList) {
            brick.setVisible(true);
            brick.getStyleClass().removeAll(this.damageStyles);
        }
        
        this.damage = new int[4][6];
        this.bricksCleared = 0;
    }
    
}
