package userInterface;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TableTop extends StackPane {

    private Rectangle table;

    public TableTop() {
        // Create a white rectangle to represent the tabletop
        table = new Rectangle();
        table.setFill(Color.BLACK);
        getChildren().add(table);
    }

    public void setDimensions(double tableTopWidth, double tableTopHeight) {
        table.setWidth(tableTopWidth);
        table.setHeight(tableTopHeight);
    }
}