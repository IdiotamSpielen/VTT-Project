package idiotamspielen.vttproject.views

import idiotamspielen.vttproject.controllers.SpellCreationController
import idiotamspielen.vttproject.controllers.SpellNotSavedException
import idiotamspielen.vttproject.controllers.InvalidSpellException
import idiotamspielen.vttproject.handlers.FeedbackHandler
import javafx.beans.property.BooleanProperty
import javafx.beans.property.StringProperty
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.layout.ColumnConstraints
import javafx.scene.text.Text
import tornadofx.*

class SpellCreatorView : View() {
    private val controller: SpellCreationController by inject()
    private val feedbackText = Text()
    private val feedbackHandler = FeedbackHandler(feedbackText)

    override val root = borderpane {
        top = createGridPane()
        center = feedbackText
        bottom = createBottomPane()
    }

    init {
        applyStyles()
    }

    private fun applyStyles() {
        root.stylesheets.add("creator.css")
    }

    private fun createGridPane() = gridpane {
        hgap = 10.0
        vgap = 10.0
        padding = insets(10.0)
        columnConstraints.addAll(
            ColumnConstraints().apply { percentWidth = 50.0 },
            ColumnConstraints().apply { percentWidth = 50.0 }
        )

        addRow(0, createTextField(controller.spellName, "Spell Name", 2, true))
        addRow(1, createTextField(controller.castingTime, "Casting Time"), createTextField(controller.range, "Range"))
        addRow(2, createTextField(controller.component, "Components"), createTextField(controller.duration, "Duration"))
        addRow(3, createTextField(controller.ingredients, "Ingredients", 2))
        addRow(4, createDescriptionArea())
        addRow(5, createTextField(controller.level, "Level"), createTextField(controller.school, "School", 1))
        addRow(
            6,
            createCheckbox("Ritual", controller.isRitual),
            createCheckbox("Concentration", controller.isConcentration)
        )
    }

    private fun createTextField(
        property: StringProperty,
        prompt: String,
        columnSpan: Int = 1,
        centered: Boolean = false
    ): TextField {
        return textfield(property).apply {
            promptText = prompt
            addClass("text-field")
            if (centered) addClass("centered-prompt")
        }.gridpaneConstraints { this.columnSpan = columnSpan }
    }

    private fun createDescriptionArea() = textarea(controller.description).apply {
        promptText = "Description"
        isWrapText = true
    }.gridpaneConstraints { columnSpan = 2 }

    private fun createCheckbox(label: String, property: BooleanProperty) = checkbox(label, property)

    private fun createBottomPane() = stackpane {
        alignment = Pos.BOTTOM_CENTER
        button("Create") {
            action { controller.createSpell() }
            action {
                feedbackText.opacity = 1.0
                try {
                    controller.createSpell()
                    feedbackHandler.displayFeedback("Spell saved as ${controller.spellName.get()}", FeedbackHandler.FeedbackType.SUCCESS)
                } catch (e: InvalidSpellException) {
                    feedbackHandler.displayFeedback("Failed to save Spell. Check your input and try again.", FeedbackHandler.FeedbackType.ERROR)
                } catch (e: SpellNotSavedException) {
                    feedbackHandler.displayFeedback("Failed to save Spell. Check log for further information.", FeedbackHandler.FeedbackType.ERROR)
                } catch (e: Exception) {
                    feedbackHandler.displayFeedback("An unexpected error occurred.", FeedbackHandler.FeedbackType.ERROR)
                }
                paddingAll = 10
            }
        }
    }
}