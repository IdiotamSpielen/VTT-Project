package idiotamspielen.vttproject.views

import idiotamspielen.vttproject.controllers.CreationController
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.layout.ColumnConstraints
import tornadofx.*

class SpellCreatorView : View() {
    private val controller: CreationController by inject()

    private fun applyStyles(){
        with(root.stylesheets){
            add("""creator.css""".trimIndent())
        }
    }

    private fun setupPromptField(field: TextField, prompt: String) {
        field.promptText = prompt
        field.addClass("text-field")
    }

    override val root = borderpane {

        val col1 = ColumnConstraints()
            col1.percentWidth = 50.0
        val col2 = ColumnConstraints()
            col2.percentWidth = 50.0
        val grid = gridpane{
            hgap = 10.0
            vgap = 10.0
            padding = insets(10.0)
            columnConstraints.addAll(col1, col2)

            row { //Row 0, contains the Spell Name
                val spellNameField = textfield(controller.spellName)
                setupPromptField(spellNameField, "Spell Name")
                spellNameField.addClass("text-field-centered-prompt")
                add(spellNameField.gridpaneConstraints { columnSpan = 2 })
            }
            row { //Row 1, Contains Casting Time and Range
                val castingTimeField = textfield(controller.castingTime)
                setupPromptField(castingTimeField, "Casting Time")
                add(castingTimeField.gridpaneConstraints { columnSpan = 1 })
                val rangeField = textfield(controller.range)
                setupPromptField(rangeField, "Range")
                add(rangeField.gridpaneConstraints { columnSpan = 1 })
            }
            row { //Row 2, contains components and duration
                val componentsField = textfield(controller.component)
                setupPromptField(componentsField, "Components")
                add(componentsField.gridpaneConstraints { columnSpan = 1 })
                val durationField = textfield(controller.duration)
                setupPromptField(durationField, "Duration")
                add(durationField.gridpaneConstraints { columnSpan = 1 })
            }
            row { //Row 3, contains Ingredients
                val ingredientsField = textfield(controller.ingredients)
                setupPromptField(ingredientsField, "Ingredients")
                add(ingredientsField.gridpaneConstraints { columnSpan = 2 })
            }
            row { //Rows 4 and 5, contain Description
                val descriptionArea = textarea(controller.description)
                descriptionArea.promptText = "Description"
                descriptionArea.isWrapText = true
                add(descriptionArea.gridpaneConstraints { columnSpan = 2 })
            }
            row {//Row 6 contains Level and School
                val levelField = textfield()
                setupPromptField(levelField, "Level")
                levelField.textProperty().addListener { observable, oldValue, newValue ->
                    if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                        controller.level.set(newValue.toIntOrNull() ?: 0)
                    } else {
                        levelField.text = oldValue
                    }
                }
                add(levelField.gridpaneConstraints { columnSpan = 1 })
                val schoolField = textfield(controller.school)
                setupPromptField(schoolField, "School")
                add(schoolField.gridpaneConstraints { columnSpan = 1 })
            }
            row {
                val ritualBox = checkbox("Ritual", controller.isRitual)
                add(ritualBox.gridpaneConstraints { columnSpan = 1 })
                val concentrationBox = checkbox("Concentration", controller.isConcentration)
                add(concentrationBox.gridpaneConstraints { columnSpan = 1 })
            }

        }
        top = grid
        center {
            text {
            }
        }
        bottom {
            stackpane {
                alignment = Pos.BOTTOM_CENTER
                button("Create") {
                    action { controller.createSpell() }
                    paddingAll = 10
                }
            }
        }
    }

    init {
        applyStyles()
    }
}
