package ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import controllers.SpellCreationController
import services.LocalizationService
import util.L

@Composable
fun SpellCreatorView(controller: SpellCreationController){

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
            label = { Text(L.SPELL_NAME.t()) },
            modifier = Modifier.fillMaxWidth()
        )

        // Casting Time & Range in einer Reihe
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = controller.castingTime,
                onValueChange = { controller.castingTime = it },
                label = { Text(L.SPELL_CASTTIME.t()) },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = controller.range,
                onValueChange = { controller.range = it },
                label = { Text(L.SPELL_RANGE.t()) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = controller.component,
                onValueChange = { controller.component = it },
                label = { Text(L.SPELL_COMPONENTS.t()) },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = controller.duration,
                onValueChange = { controller.duration = it },
                label = { Text(L.SPELL_DURATION.t()) },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = controller.ingredients,
            onValueChange = { controller.ingredients = it },
            label = { Text(L.SPELL_INGREDIENTS.t()) },
            modifier = Modifier.fillMaxWidth()
        )

        // Description (Großes Feld)
        OutlinedTextField(
            value = controller.description,
            onValueChange = { controller.description = it },
            label = { Text(L.SPELL_DESCRIPTION.t()) },
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = controller.level,
                onValueChange = { controller.level = it },
                label = { Text(L.SPELL_LEVEL.t()) },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = controller.school,
                onValueChange = { controller.school = it },
                label = { Text(L.SPELL_SCHOOL.t()) },
                modifier = Modifier.weight(1f)
            )
        }

        // Checkboxen
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Checkbox(checked = controller.isRitual, onCheckedChange = { controller.isRitual = it })
            Text(L.RITUAL.t())
            Spacer(Modifier.width(16.dp))
            Checkbox(checked = controller.isConcentration, onCheckedChange = { controller.isConcentration = it })
            Text(L.CONCENTRATION.t())
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