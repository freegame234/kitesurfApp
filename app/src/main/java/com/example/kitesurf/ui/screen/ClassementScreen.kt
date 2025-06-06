package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kitesurf.ui.viewmodel.ClassementViewModel
import com.example.kitesurf.ui.viewmodel.CompetitionViewModel
import com.example.kitesurf.ui.viewmodel.CompetitionViewModelFactory
import com.example.kitesurf.domaine.model.Kitesurfer
import com.example.kitesurf.nationalityToFlag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassementTab(
    classementViewModel: ClassementViewModel = viewModel(),
    competitionViewModel: CompetitionViewModel = viewModel(factory = CompetitionViewModelFactory(LocalContext.current))
) {
    val competitions by competitionViewModel.competitions.collectAsState()
    val classement by classementViewModel.classement.collectAsState()

    var selectedCompetitionId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        competitionViewModel.fetchCompetitions()
    }

    LaunchedEffect(selectedCompetitionId) {
        selectedCompetitionId?.let {
            classementViewModel.fetchClassement(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = competitions.firstOrNull { it.id == selectedCompetitionId }?.nom
                    ?: "Sélectionnez une compétition",
                onValueChange = {},
                label = { Text("Compétition") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                competitions.forEach { comp ->
                    DropdownMenuItem(
                        text = { Text(comp.nom) },
                        onClick = {
                            selectedCompetitionId = comp.id
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (classement.isEmpty()) {
            Text(
                text = "Aucun classement disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(classement) { rider ->
                    val flag = nationalityToFlag(rider.nationalite ?: "")
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${rider.rank}.", style = MaterialTheme.typography.titleMedium)
                            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                                Text(
                                    text = "$flag ${rider.name}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${rider.points} pts",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}