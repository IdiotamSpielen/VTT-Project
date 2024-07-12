package idiotamspielen.vttproject.handlers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

public class MouseEventHandler implements EventHandler<MouseEvent> {
    private double[] initialPosition;
    private Node grabbedNode;

    @Override
    public void handle(@NotNull MouseEvent event) {
        Node node = (Node) event.getSource();

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            initialPosition = new double[]{event.getSceneX(), event.getSceneY()};
            grabbedNode = node;
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (grabbedNode != null) {
                double deltaX = event.getSceneX() - initialPosition[0];
                double deltaY = event.getSceneY() - initialPosition[1];

                grabbedNode.setTranslateX(grabbedNode.getTranslateX() + deltaX);
                grabbedNode.setTranslateY(grabbedNode.getTranslateY() + deltaY);

                // update saved position for next drag-event
                initialPosition = new double[]{event.getSceneX(), event.getSceneY()};
            }
        }
    }
}
