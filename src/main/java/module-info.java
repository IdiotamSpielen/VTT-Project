module idiotamspielen.vttproject{
        requires javafx.controls;
        requires javafx.fxml;
        requires com.fasterxml.jackson.databind;
        requires org.jetbrains.annotations;

        exports idiotamspielen.vttproject to javafx.graphics;
        exports idiotamspielen.vttproject.controllers to javafx.fxml;
        exports idiotamspielen.vttproject.handlers to javafx.fxml;
        exports idiotamspielen.vttproject.creators to javafx.fxml;
        exports idiotamspielen.vttproject.classifications to javafx.fxml, com.fasterxml.jackson.databind;
        exports idiotamspielen.vttproject.userInterface to javafx.fxml;
        opens idiotamspielen.vttproject to javafx.fxml;
        opens idiotamspielen.vttproject.controllers to javafx.fxml;
        opens idiotamspielen.vttproject.handlers to javafx.fxml;
        opens idiotamspielen.vttproject.creators to javafx.fxml;
        opens idiotamspielen.vttproject.classifications to javafx.fxml, com.fasterxml.jackson.databind;
        opens idiotamspielen.vttproject.userInterface to javafx.fxml;
}