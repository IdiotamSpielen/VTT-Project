package idiotamspielen.vttproject.handlers

import javafx.animation.FadeTransition
import javafx.animation.PauseTransition
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.util.Duration

class FeedbackHandler(private val feedbackText: Text) {

    fun saveSuccess(spellName: String?) {
        feedbackText.opacity = 1.0
        feedbackText.text = "Spell saved as $spellName"
        feedbackText.fill = Color.GREEN
        fadeOutFeedbackText()
    }

    fun saveError() {
        feedbackText.opacity = 1.0
        feedbackText.text = "Failed to save Spell. Check log for further information."
        feedbackText.fill = Color.RED
        fadeOutFeedbackText()
    }

    fun userdataError() {
        feedbackText.opacity = 1.0
        feedbackText.text = "Failed to save Spell. Check your inputs and try again."
        feedbackText.fill = Color.RED
        fadeOutFeedbackText()
    }

    private fun fadeOutFeedbackText() {
        val fadeTransition = FadeTransition(Duration.seconds(1.0), feedbackText)
        fadeTransition.fromValue = 1.0
        fadeTransition.toValue = 0.0

        val pauseTransition = PauseTransition(Duration.seconds(1.5))
        pauseTransition.onFinished = EventHandler { event: ActionEvent? -> fadeTransition.play() }

        pauseTransition.play()
    }
}
