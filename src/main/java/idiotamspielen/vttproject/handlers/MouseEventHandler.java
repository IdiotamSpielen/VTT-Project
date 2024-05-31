package idiotamspielen.vttproject.handlers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

public class MouseEventHandler implements EventHandler<MouseEvent> {
    private double[] initialPosition;

    @Override
    public void handle(@NotNull MouseEvent event) {
        Node node = (Node) event.getSource();

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            initialPosition = new double[]{event.getSceneX(), event.getSceneY()};
            System.out.println("image clicked");
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double deltaX = event.getSceneX() - initialPosition[0];
            double deltaY = event.getSceneY() - initialPosition[1];

            node.setTranslateX(node.getTranslateX() + deltaX);
            node.setTranslateY(node.getTranslateY() + deltaY);

            // update saved position for next drag-event
            initialPosition = new double[]{event.getSceneX(), event.getSceneY()};
        }
    }
}
