package application;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A BrickBreakerTitle class extending from Pane that modifies 
 * the title text 'Brick Breaker' of the home page.
 * @param text the text display
 * 
 */
public class BrickBreakerTitle extends Pane {
	private Text text;
	
	/**
	 * This method creates a space between every character and letter of the title name.
	 * The text uses the font DroidSerif and applies a drop shadow effect.
	 * @param name the title name
	 */
	public BrickBreakerTitle(String name) {
		String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }

        text = new Text(spread);
        text.setFont(Font.loadFont(Main.class.getResource("img/DroidSerif-Regular.ttf").toExternalForm(), 48));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
	}
	
	/**
	 * Gets the title width
	 * @return the title width
	 */
	public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }
	
	/**
	 * Gets the title height
	 * @return the title height
	 */
	public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}
