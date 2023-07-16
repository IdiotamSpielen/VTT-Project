package handlers;

import javafx.scene.text.Text;

public class FeedbackHandler {

    private final Text feedbackText;

    public FeedbackHandler(Text feedbackText){
        this.feedbackText = feedbackText;
    }

    public void displaySuccess(String spellName){
        feedbackText.setText("Spell saved as " + spellName);
    }
    public void displayError() {
        feedbackText.setText("Failed to save Spell. Check log for further information.");
    }
    public void pointOutIncompetence() {feedbackText.setText("Failed to save Spell. Check your inputs and try again.");}

    public Text getFeedbackText() {
    return feedbackText;}
}
