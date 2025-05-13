package com.example.kitesurf.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset // Importez tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kitesurf.ui.theme.BlueOceanLight // Supposons que vous ayez BlueOceanLight couleur définie dans ui.theme
import com.example.kitesurf.ui.theme.BlueOceanMedium // Supposons que vous ayez BlueOceanMedium couleur définie dans ui.theme
import com.example.kitesurf.ui.theme.BlueOceanDark  // Supposons que vous ayez BlueOceanDark couleur définie dans ui.theme
import com.example.kitesurf.ui.theme.WhiteSand      // Supposons que vous ayez WhiteSand couleur définie dans ui.theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    // État pour gérer l'onglet sélectionné
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Liste des titres des onglets
    val tabs = listOf("Compétitions Actuelles", "Résultats Passés", "Calendrier", "Météo", "Localisation")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KiteSurf App") },
                // Couleur de l'AppBar pour le thème océan (supposons que BlueOceanLight est défini)
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueOceanLight,
                    titleContentColor = Color.Black // Ou une autre couleur pour le texte
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                // Ajout d'un arrière-plan pour le thème océan
                .background(BlueOceanMedium) // Supposons que BlueOceanMedium est défini
        ) {
            // Section des onglets
            TabRow(
                selectedTabIndex = selectedTabIndex,
                // Couleur de l'indicateur de l'onglet (supposons que BlueOceanDark est défini)
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = BlueOceanDark // Couleur de l'indicateur
                    )
                },
                // Couleur de fond de la rangée d'onglets (supposons que BlueOceanLight est défini)
                containerColor = BlueOceanLight
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) },
                        // Couleur du contenu de l'onglet (texte)
                        selectedContentColor = BlueOceanDark, // Couleur pour l'onglet sélectionné
                        unselectedContentColor = Color.Gray // Couleur pour les onglets non sélectionnés
                    )
                }
            }

            // Contenu de l'onglet actuellement sélectionné
            when (selectedTabIndex) {
                0 -> CurrentCompetitionsScreen() // N'a pas besoin du ViewModel
                1 -> PastResultsScreen()       // N'a pas besoin du ViewModel
                2 -> CalendarScreen()         // N'a pas besoin du ViewModel
                3 -> MeteoScreen()
                4 -> LocalisationScreen()
            }

            // Votre bouton d'origine (vous pouvez le laisser ou le déplacer)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Vous pouvez supprimer le texte affichant les données du ViewModel
                // Text(text = "Data from ViewModel: ${viewModel.data.value}")

                Button(
                    onClick = {
                        val videoId = "pNrdJAyTMZc" // The ID of the YouTube video
                        navController.navigate("youtube_streaming_screen/$videoId")
                    },
                    // ...
                ) {
                    Text("Streaming", color = WhiteSand)
                }
            }
        }
    }
}

// --- Composables pour le contenu des onglets (avec du texte factice) ---

@Composable
fun CurrentCompetitionsScreen(
    // Supprimez le paramètre ViewModel si vous ne l'utilisez pas
    // viewModel: CompetitionViewModel
) {
    // Affichez simplement du texte factice pour l'instant
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Contenu de l'onglet 'Compétitions Actuelles'",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "Liste des compétitions à venir...",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
        // Vous pouvez ajouter un indicateur de chargement factice si vous voulez
        // CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun PastResultsScreen(
    // Supprimez le paramètre ViewModel si vous ne l'utilisez pas
    // viewModel: CompetitionViewModel
) {
    // Affichez simplement du texte factice pour l'instant
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Contenu de l'onglet 'Résultats Passés'",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "Affichage des résultats passés...",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun CalendarScreen(
    // Supprimez le paramètre ViewModel si vous ne l'utilisez pas
    // viewModel: CompetitionViewModel
) {
// Affichez simplement du texte factice pour l'
    Text("Aucune Base de données disponible")
}

@Composable
fun MeteoScreen(
) {
// Affichez simplement du texte factice pour l'
    Text("Aucune Base de données disponible")
}

@Composable
fun LocalisationScreen(
) {
// Affichez simplement du texte factice pour l'
    Text("Aucune Base de données disponible")

}