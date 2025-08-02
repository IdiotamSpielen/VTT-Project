module idiotamspielen.vttproject{
    requires kotlin.stdlib;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;
    requires tornadofx;
    requires org.slf4j;

    exports idiotamspielen.vttproject to javafx.graphics, tornadofx, org.slf4j;
    exports idiotamspielen.vttproject.controllers to javafx.fxml, tornadofx, org.slf4j;
    exports idiotamspielen.vttproject.services to javafx.fxml, tornadofx, org.slf4j;
    exports idiotamspielen.vttproject.views to javafx.fxml, tornadofx, org.slf4j;
    opens idiotamspielen.vttproject to javafx.fxml, tornadofx, org.slf4j;
    opens idiotamspielen.vttproject.controllers to javafx.fxml, tornadofx, org.slf4j;
    opens idiotamspielen.vttproject.services to javafx.fxml, tornadofx, org.slf4j;
    opens idiotamspielen.vttproject.views to javafx.fxml, tornadofx, org.slf4j;
}