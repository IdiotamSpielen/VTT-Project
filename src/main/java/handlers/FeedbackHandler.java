package java.handlers;

import javafx.scene.text.Text;
import java.creators.SpellCreator;

public class FeedbackHandler {

    private final Text feedbackText;
    private SpellCreator spellCreator;
    private SpellFileHandler spellFileHandler;

    public FeedbackHandler(Text feedbackText, SpellCreator spellCreator){
        this.feedbackText = new Text();
        this.spellCreator = spellCreator;
    }



    public void displaySuccess() {
            if (spellCreator != null) {
        feedbackText.setText("Spell saved as " + spellFileHandler.getName());}
    }
    public void displayError() {
        feedbackText.setText("Failed to save Spell. Check log for further information.");
    }
    public void pointoutIncompetence() {
        feedbackText.setText("Failed to save Spell. Check your inputs and try again.");
    }

    public Text getFeedbackText() {
    return feedbackText;}
}
