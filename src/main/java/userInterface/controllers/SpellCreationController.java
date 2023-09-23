package userInterface.controllers;

import com.sun.istack.NotNull;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SpellCreationController {
    @FXML
    private TextField spellNameTF;

    @FXML
    private Label spellNameL;

    public void initialize() {
        configureLabelAM(spellNameL);
        configureListeners(spellNameTF, spellNameL);
    }

    private void configureListeners(@NotNull TextField tf, @NotNull Label l) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
        // Hide the label if text is not empty
        l.setVisible(newValue.isEmpty());
        });

        l.addEventFilter(MouseEvent.ANY, event -> {
        tf.fireEvent(event);
        event.consume();
        });
    }
    private void configureLabelAM(Label l) {
        StackPane.setAlignment(l, Pos.CENTER_LEFT);
        StackPane.setMargin(l, new Insets(0, 0, 0, 8));
    }
}
