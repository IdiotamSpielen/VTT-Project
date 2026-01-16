package services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory



class FeedbackHandler() {
    private val logger = LoggerFactory.getLogger(FeedbackHandler::class.java)

    var message by mutableStateOf("")
    var color by mutableStateOf(Color.Transparent)

    private var fadeJob: Job? = null

    enum class FeedbackType(val color: Color) {
        SUCCESS(Color(0xFF2E7D32)),
        ERROR(Color.Red),
        WARNING(Color(0xFFFFA000)),
        INFO(Color.Blue)
    }

    fun displayFeedback(text: String, type: FeedbackType) {
        when (type) {
            FeedbackType.SUCCESS -> logger.info(text)
            FeedbackType.ERROR -> logger.error(text)
            FeedbackType.WARNING -> logger.warn(text)
            FeedbackType.INFO -> logger.info(text)
        }

        message = text
        color = type.color

        fadeJob?.cancel()
        fadeJob = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            message = ""
        }
    }
}