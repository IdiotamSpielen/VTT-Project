package ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import viewmodels.SpellSearchViewmodel
import utils.L

@Composable
fun SpellSearchView(viewmodel: SpellSearchViewmodel) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 4.dp
            ) {
                OutlinedTextField(
                    value = viewmodel.searchInput,
                    onValueChange = {
                        viewmodel.searchInput = it
                        viewmodel.handleSearch()
                    },
                    placeholder = { Text(L.SEARCH_PLACEHOLDER.t()) },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .onKeyEvent { event ->
                            if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                                viewmodel.handleSearch()
                                true
                            } else {
                                false
                            }
                        },
                    singleLine = true
                )
            }
        },
        bottomBar = {
            if (viewmodel.selectedSpell == null) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(onClick = { viewmodel.handleSearch() }) {
                        Text(L.SEARCH_BUTTON.t())
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val currentSpell = viewmodel.selectedSpell

            if (currentSpell == null) {
                // Spell List View
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewmodel.searchResults) { spell ->
                        Text(
                            text = spell.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewmodel.selectSpell(spell)
                                }
                                .padding(16.dp),
                            style = MaterialTheme.typography.body1
                        )
                        Divider()
                    }
                }
            } else {
                // Spell Details View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = currentSpell.name,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = L.SPELL_CASTTIME.t(), value = currentSpell.castingTime, modifier = Modifier.weight(1f))
                        DetailBox(label = L.SPELL_RANGE.t(), value = currentSpell.range, modifier = Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = L.SPELL_COMPONENTS.t(), value = currentSpell.components, modifier = Modifier.weight(1f))
                        DetailBox(label = L.SPELL_DURATION.t(), value = currentSpell.duration, modifier = Modifier.weight(1f))
                    }

                    currentSpell.ingredients?.let { ingredients ->
                        DetailBox(
                            label = L.SPELL_INGREDIENTS.t(),
                            value = ingredients,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Surface(
                        elevation = 2.dp,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(L.SPELL_DESCRIPTION.t(), style = MaterialTheme.typography.caption)
                            Text(currentSpell.description, style = MaterialTheme.typography.body2)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = L.SPELL_LEVEL.t(), value = currentSpell.level.toString(), modifier = Modifier.weight(1f))
                        DetailBox(label = L.SPELL_SCHOOL.t(), value = currentSpell.school, modifier = Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = L.RITUAL.t(), value = if(currentSpell.ritual) L.YES.t() else L.NO.t(), modifier = Modifier.weight(1f))
                        DetailBox(label = L.CONCENTRATION.t(), value = if (currentSpell.concentration) L.YES.t() else L.NO.t(), modifier = Modifier.weight(1f))
                    }

                    TextButton(
                        onClick = { viewmodel.clearSelection() },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(L.SEARCH_BACK.t())
                    }
                }
            }
        }
    }
}

@Composable
fun DetailBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        elevation = 2.dp,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.caption)
            Text(value, style = MaterialTheme.typography.body1)
        }
    }
}