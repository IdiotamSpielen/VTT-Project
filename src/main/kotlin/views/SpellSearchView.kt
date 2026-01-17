package views

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
import controllers.SpellSearchController

@Composable
fun SpellSearchView(controller: SpellSearchController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 4.dp
            ) {
                OutlinedTextField(
                    value = controller.searchInput,
                    onValueChange = { controller.searchInput = it },
                    placeholder = { Text("Enter spell name to search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .onKeyEvent { event ->
                            if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                                controller.handleSearch()
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(onClick = { controller.handleSearch() }) {
                    Text("Display Spells")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (controller.spellName.isEmpty()) {
                // Spell List View
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(controller.spellList) { spellName ->
                        Text(
                            text = spellName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    controller.handleSpellSelection(spellName)
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
                        text = controller.spellName,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = "Casting Time", value = controller.castingTime, modifier = Modifier.weight(1f))
                        DetailBox(label = "Range", value = controller.range, modifier = Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = "Components", value = controller.components, modifier = Modifier.weight(1f))
                        DetailBox(label = "Duration", value = controller.duration, modifier = Modifier.weight(1f))
                    }

                    DetailBox(label = "Ingredients", value = controller.ingredients, modifier = Modifier.fillMaxWidth())

                    Surface(
                        elevation = 2.dp,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Description", style = MaterialTheme.typography.caption)
                            Text(controller.descArea, style = MaterialTheme.typography.body2)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = "Level", value = controller.level, modifier = Modifier.weight(1f))
                        DetailBox(label = "School", value = controller.school, modifier = Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DetailBox(label = "Ritual", value = controller.ritual, modifier = Modifier.weight(1f))
                        DetailBox(label = "Concentration", value = controller.concentration, modifier = Modifier.weight(1f))
                    }

                    TextButton(
                        onClick = { controller.spellName = "" },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Back to list")
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