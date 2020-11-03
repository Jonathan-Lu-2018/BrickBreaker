package application;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A BrickBreakerMenuIteam class extending from Pane that modifies the menu items
 * of the home page.
 * @param text the text display
 * @param shadow the shadow effect
 * @param blur the blur effect
 *
 */
public class BrickBreakerMenuItem extends Pane {

	private Text text;

    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 3);
    
    /**
     * This method sets the fill from a polygon when a button is pressed on the menu item.
     * When a button is pressed, the color opacity is set to 75 otherwise it is set to 
     * 25. A shadow effect is applied when a button is hovered otherwise it will have a blur effect.
     * The text uses the font DroidSerif.
     * @param name the names of the menu item
     */
    public BrickBreakerMenuItem(String name) {
    	Polygon bg = new Polygon(
                0, 0,
                200, 0,
                215, 15,
                200, 30,
                0, 30
        );
        bg.setStroke(Color.color(1, 1, 1, 0.75));
        bg.setEffect(new GaussianBlur());

        bg.fillProperty().bind(
                Bindings.when(pressedProperty())
                        .then(Color.color(0, 0, 0, 0.75))
                        .otherwise(Color.color(0, 0, 0, 0.25))
        );

        text = new Text(name);
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFont(Font.loadFont(Main.class.getResource("img/DroidSerif-Regular.ttf").toExternalForm(), 14));
        text.setFill(Color.WHITE);

        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );

        getChildren().addAll(bg, text);
    }
    
    /**
     * Creates a callback action when the mouse gets click.
     * @param action the action that occurs
     */
    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }
}
