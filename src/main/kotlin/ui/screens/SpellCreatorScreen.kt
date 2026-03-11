package ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodels.SpellCreationViewmodel
import utils.L
import viewmodels.SpellUiState

@Composable
fun SpellCreatorView(viewmodel: SpellCreationViewmodel){

    val state = viewmodel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create New Spell", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewmodel.updateState(state.copy(name = it)) },
            label = { Text(L.SPELL_NAME.t()) },
            modifier = Modifier.fillMaxWidth()
        )

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

        OutlinedTextField(
            value = state.ingredients,
            onValueChange = { viewmodel.updateState(state.copy(ingredients = it)) },
            label = { Text(L.SPELL_INGREDIENTS.t()) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.description,
            onValueChange = { viewmodel.updateState(state.copy(description = it)) },
            label = { Text(L.SPELL_DESCRIPTION.t()) },
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )

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

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Checkbox(checked = state.isRitual, onCheckedChange = { viewmodel.updateState(state.copy(isRitual = it)) })
            Text(L.RITUAL.t())
            Spacer(Modifier.width(16.dp))
            Checkbox(checked = state.isConcentration, onCheckedChange = { viewmodel.updateState(state.copy(isConcentration = it)) })
            Text(L.CONCENTRATION.t())
        }

        Text(
            text = viewmodel.feedbackHandler.message,
            color = viewmodel.feedbackHandler.color,
            modifier = Modifier.padding(8.dp).animateContentSize()
        )

        Button(
            onClick = { viewmodel.createSpell() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Spell")
        }
    }
}