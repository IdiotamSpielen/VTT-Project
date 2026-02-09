package ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodels.SpellCreationViewModel
import utils.L

@Composable
fun SpellCreatorScreen(viewmodel: SpellCreationViewModel){

    val state = viewmodel.uiState

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        userScrollEnabled = true,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewmodel.updateState(state.copy(name = it)) },
                label = { Text(L.SPELL_NAME.t()) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.castingTime,
                    onValueChange = { viewmodel.updateState(state.copy(castingTime = it)) },
                    label = { Text(L.SPELL_CASTTIME.t()) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = state.range,
                    onValueChange = { viewmodel.updateState(state.copy(range = it)) },
                    label = { Text(L.SPELL_RANGE.t()) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.components,
                    onValueChange = { viewmodel.updateState(state.copy(components = it)) },
                    label = { Text(L.SPELL_COMPONENTS.t()) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = state.duration,
                    onValueChange = { viewmodel.updateState(state.copy(duration = it)) },
                    label = { Text(L.SPELL_DURATION.t()) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            OutlinedTextField(
                value = state.ingredients,
                onValueChange = { viewmodel.updateState(state.copy(ingredients = it)) },
                label = { Text(L.SPELL_INGREDIENTS.t()) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = state.description,
                onValueChange = { viewmodel.updateState(state.copy(description = it)) },
                label = { Text(L.SPELL_DESCRIPTION.t()) },
                modifier = Modifier.fillMaxWidth().height(150.dp)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.level,
                    onValueChange = { viewmodel.updateState(state.copy(level = it)) },
                    label = { Text(L.SPELL_LEVEL.t()) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = state.school,
                    onValueChange = { viewmodel.updateState(state.copy(school = it)) },
                    label = { Text(L.SPELL_SCHOOL.t()) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Checkbox(
                    checked = state.isRitual,
                    onCheckedChange = { viewmodel.updateState(state.copy(isRitual = it)) })
                Text(L.RITUAL.t())
                Spacer(Modifier.width(16.dp))
                Checkbox(
                    checked = state.isConcentration,
                    onCheckedChange = { viewmodel.updateState(state.copy(isConcentration = it)) })
                Text(L.CONCENTRATION.t())
            }
        }

        item {
            Text(
                text = viewmodel.feedbackHandler.message,
                color = viewmodel.feedbackHandler.color,
                modifier = Modifier.padding(8.dp).animateContentSize() // Kleiner Bonus: Animiert das Erscheinen
            )
        }
    }
}