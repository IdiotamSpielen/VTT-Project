package handlers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FeedbackHandler {
    private final Text feedbackText;

    public FeedbackHandler(Text feedbackText) {
        this.feedbackText = feedbackText;
    }

    public void displaySuccess(String spellName) {
        feedbackText.setText("Spell saved as " + spellName);
        feedbackText.setFill(Color.GREEN);
        fadeOutFeedbackText();
    }

    public void displayError() {
        feedbackText.setText("Failed to save Spell. Check log for further information.");
        feedbackText.setFill(Color.RED);
        fadeOutFeedbackText();
    }

    public void pointOutIncompetence() {
        feedbackText.setText("Failed to save Spell. Check your inputs and try again.");
        feedbackText.setFill(Color.RED);
        fadeOutFeedbackText();
    }

    private void fadeOutFeedbackText() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), feedbackText);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.5));
        pauseTransition.setOnFinished(event -> fadeTransition.play());

        pauseTransition.play();
    }

    public Text getFeedbackText() {return feedbackText;}
}
