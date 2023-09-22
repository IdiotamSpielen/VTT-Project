package userInterface.creation;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class CharacterCreationBox {
    private final BorderPane layoutPane;

    public CharacterCreationBox() {
        layoutPane = new BorderPane();

        // Add UI components for character creation here
        // For example, labels, text fields, and buttons
        Button createCharacterButton = new Button("Create Character");
        createCharacterButton.setOnAction(event -> {
        // Handle character creation here
        // You can access the values entered by the user and create a new Character object
        });

        layoutPane.setCenter(createCharacterButton);
    }

    public BorderPane getLayoutPane() {
        return layoutPane;
    }
}
