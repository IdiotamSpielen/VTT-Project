package views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import controllers.SpellCreationController

@Composable
fun SpellCreatorView(controller: SpellCreationController){
    var feedbackMessage by remember { mutableStateOf("") }
    var feedbackColor by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Macht die Seite scrollbar
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create New Spell", style = MaterialTheme.typography.h5)

        // Spell Name
        OutlinedTextField(
            value = controller.spellName,
            onValueChange = { controller.spellName = it },
            label = { Text("Spell Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Casting Time & Range in einer Reihe
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = controller.castingTime,
                onValueChange = { controller.castingTime = it },
                label = { Text("Casting Time") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = controller.range,
                onValueChange = { controller.range = it },
                label = { Text("Range") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = controller.component,
                onValueChange = { controller.component = it },
                label = { Text("Component") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = controller.duration,
                onValueChange = { controller.duration = it },
                label = { Text("Duration") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = controller.ingredients,
            onValueChange = { controller.ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth()
        )

        // Description (Großes Feld)
        OutlinedTextField(
            value = controller.description,
            onValueChange = { controller.description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = controller.level,
                onValueChange = { controller.level = it },
                label = { Text("Level") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = controller.school,
                onValueChange = { controller.school = it },
                label = { Text("School") },
                modifier = Modifier.weight(1f)
            )
        }

        // Checkboxen
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Checkbox(checked = controller.isRitual, onCheckedChange = { controller.isRitual = it })
            Text("Ritual")
            Spacer(Modifier.width(16.dp))
            Checkbox(checked = controller.isConcentration, onCheckedChange = { controller.isConcentration = it })
            Text("Concentration")
        }

        // Feedback & Button
        Text(
            text = controller.feedbackHandler.message,
            color = controller.feedbackHandler.color,
            modifier = Modifier.padding(8.dp).animateContentSize() // Kleiner Bonus: Animiert das Erscheinen
        )

        Button(
            onClick = { controller.createSpell() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Spell")
        }
    }
}

//
//class SpellCreatorView : View("Spell Creator") {
//    private val controller: SpellCreationController by inject()
//    private val feedbackText = Text()
//    private val feedbackHandler = FeedbackHandler(feedbackText)
//
//    override val root = borderpane {
//        top = createGridPane()
//        center = feedbackText
//        bottom = createBottomPane()
//    }
//
//    init {
//        importStylesheet("/styles/spell_creator.css")
//    }
//
//    private fun createGridPane() = gridpane {
//        hgap = 10.0
//        vgap = 10.0
//        padding = insets(10.0)
//        columnConstraints.addAll(
//            ColumnConstraints().apply { percentWidth = 50.0 },
//            ColumnConstraints().apply { percentWidth = 50.0 }
//        )
//
//        addRow(0, createTextField(controller.spellName, "Spell Name", 2, true))
//        addRow(1, createTextField(controller.castingTime, "Casting Time"), createTextField(controller.range, "Range"))
//        addRow(2, createTextField(controller.component, "Components"), createTextField(controller.duration, "Duration"))
//        addRow(3, createTextField(controller.ingredients, "Ingredients", 2))
//        addRow(4, createDescriptionArea())
//        addRow(5, createTextField(controller.level, "Level"), createTextField(controller.school, "School", 1))
//        addRow(
//            6,
//            createCheckbox("Ritual", controller.isRitual),
//            createCheckbox("Concentration", controller.isConcentration)
//        )
//    }
//
//    private fun createTextField(
//        property: StringProperty,
//        prompt: String,
//        columnSpan: Int = 1,
//        centered: Boolean = false
//    ): TextField {
//        return textfield(property).apply {
//            promptText = prompt
//            addClass("text-field")
//            if (centered) addClass("centered")
//        }.gridpaneConstraints { this.columnSpan = columnSpan }
//    }
//
//    private fun createDescriptionArea() = textarea(controller.description).apply {
//        promptText = "Description"
//        isWrapText = true
//    }.gridpaneConstraints { columnSpan = 2 }
//
//    private fun createCheckbox(label: String, property: BooleanProperty) = checkbox(label, property)
//
//    private fun createBottomPane() = stackpane {
//        paddingAll = 15
//        alignment = Pos.BOTTOM_CENTER
//        button("Create") {
//            addClass("create-button")
//            action {
//                feedbackText.opacity = 1.0
//                try {
//                    controller.createSpell()
//                    feedbackHandler.displayFeedback("Spell saved as ${controller.spellName.get()}", FeedbackHandler.FeedbackType.SUCCESS)
//                } catch (e: InvalidSpellException) {
//                    feedbackHandler.displayFeedback("Failed to save Spell. Check your input and try again.", FeedbackHandler.FeedbackType.ERROR)
//                } catch (e: SpellNotSavedException) {
//                    feedbackHandler.displayFeedback("Failed to save Spell. Check log for further information.", FeedbackHandler.FeedbackType.ERROR)
//                } catch (e: Exception) {
//                    feedbackHandler.displayFeedback("An unexpected error occurred.", FeedbackHandler.FeedbackType.ERROR)
//                    println(e.message)
//                }
//            }
//            paddingAll = 10
//        }
//    }
//}