module idiotamspielen.vttproject{
        requires javafx.controls;
        requires javafx.fxml;
        requires com.fasterxml.jackson.databind;
        requires org.jetbrains.annotations;
        requires kotlin.stdlib;
        requires tornadofx;

        exports idiotamspielen.vttproject to javafx.graphics, tornadofx;
        exports idiotamspielen.vttproject.controllers to javafx.fxml, tornadofx;
        exports idiotamspielen.vttproject.handlers to javafx.fxml, tornadofx;
        exports idiotamspielen.vttproject.creators to javafx.fxml, tornadofx;
        exports idiotamspielen.vttproject.classifications to javafx.fxml, com.fasterxml.jackson.databind, tornadofx;
        exports idiotamspielen.vttproject.userInterface to javafx.fxml, tornadofx;
        exports idiotamspielen.vttproject.views to javafx.fxml, tornadofx;
        opens idiotamspielen.vttproject to javafx.fxml, tornadofx;
        opens idiotamspielen.vttproject.controllers to javafx.fxml, tornadofx;
        opens idiotamspielen.vttproject.handlers to javafx.fxml, tornadofx;
        opens idiotamspielen.vttproject.creators to javafx.fxml, tornadofx;
        opens idiotamspielen.vttproject.classifications to javafx.fxml, com.fasterxml.jackson.databind;
        opens idiotamspielen.vttproject.userInterface to javafx.fxml, tornadofx;
        opens idiotamspielen.vttproject.views to javafx.fxml, tornadofx;
}