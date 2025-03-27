/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package idiotamspielen.vttproject.views

import javafx.animation.FadeTransition
import javafx.animation.PauseTransition
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.util.Duration

class FeedbackHandler(private val feedbackText: Text) {

    enum class FeedbackType(val color: Color) {
        SUCCESS(Color.GREEN),
        ERROR(Color.RED),
        WARNING(Color.ORANGE),
        INFO(Color.BLUE)
    }

    fun displayFeedback(message: String, type: FeedbackType) {
        feedbackText.text = message
        feedbackText.fill = type.color

        fadeOutFeedbackText()
    }

    private fun fadeOutFeedbackText() {
        val fadeTransition = FadeTransition(Duration.seconds(1.0), feedbackText)
        fadeTransition.fromValue = 1.0
        fadeTransition.toValue = 0.0

        val pauseTransition = PauseTransition(Duration.seconds(1.0))
        pauseTransition.onFinished = EventHandler { event: ActionEvent? -> fadeTransition.play() }

        pauseTransition.play()
    }
}