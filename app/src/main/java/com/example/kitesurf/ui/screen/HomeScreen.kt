package com.example.kitesurf.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kitesurf.ui.theme.*
import com.example.kitesurf.ui.viewmodel.*
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    classementViewModel: ClassementViewModel = viewModel(),
    calendrierViewModel: CalendrierViewModel = viewModel(),
    meteoViewModel: MeteoViewModel = viewModel(),
    competitionViewModel: CompetitionViewModel = viewModel(factory = CompetitionViewModelFactory(LocalContext.current))
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Compétitions", "Classement", "Calendrier", "Météo", "Localisation")
    val competitions by competitionViewModel.competitions.collectAsState()
    val error by competitionViewModel.errorMessage.collectAsState()

    // ⚡ Rafraîchissement automatique toutes les 5 minutes
    LaunchedEffect(Unit) {
        while (true) {
            classementViewModel.fetchClassement(1)
            competitionViewModel.fetchCompetitions()
            calendrierViewModel.fetchCalendrier(1)
            meteoViewModel.fetchMeteo()
            kotlinx.coroutines.delay(5 * 60 * 1000) // 5 minutes
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KiteSurf App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueOceanLight,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BlueOceanMedium)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = BlueOceanDark
                    )
                },
                containerColor = BlueOceanLight
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) },
                        selectedContentColor = BlueOceanDark,
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> CompetitionTab(competitionViewModel)
                1 -> ClassementTab(classementViewModel)
                2 -> CalendrierTab(calendrierViewModel)
                3 -> MeteoTab(meteoViewModel)
                4 -> LocalisationTabScreen()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    navController.navigate("video_list_screen")
                }) {
                    Text("Vidéos", color = WhiteSand)
                }
                Button(onClick = {
                    refreshAllData(classementViewModel, competitionViewModel, calendrierViewModel, meteoViewModel)
                }) {
                    Text("Rafraîchir", color = WhiteSand)
                }
            }
        }
    }
}

@Composable
fun CompetitionTab(viewModel: CompetitionViewModel = viewModel()) {
    val competitions by viewModel.competitions.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCompetitions()
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(competitions) { comp ->
            Text("🏆 ${comp.nom} - ${comp.date} à ${comp.localisation}")
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun ClassementTab(viewModel: ClassementViewModel = viewModel()) {
    val classement by viewModel.classement.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchClassement(1)
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(classement) { rider ->
            Text("${rider.rank}. ${rider.name} - ${rider.points} pts")
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
fun CalendrierTab(viewModel: CalendrierViewModel = viewModel()) {
    val calendrier by viewModel.calendrier.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCalendrier(1)
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(calendrier) { event ->
            Text("${event.date_debut} → ${event.date_fin} à ${event.localisation}")
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
fun MeteoTab(viewModel: MeteoViewModel = viewModel()) {
    val meteo by viewModel.meteo.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMeteo()
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(meteo) { m ->
            Text("${m.date} - ${m.temperature}°C - ${m.ventVitesse}km/h ${m.ventDirection} - ${m.condition}")
            Spacer(Modifier.height(6.dp))
        }
    }
}

private fun refreshAllData(
    classementViewModel: ClassementViewModel,
    competitionViewModel: CompetitionViewModel,
    calendrierViewModel: CalendrierViewModel,
    meteoViewModel: MeteoViewModel
) {
    classementViewModel.fetchClassement(1)
    competitionViewModel.fetchCompetitions()
    calendrierViewModel.fetchCalendrier(1)
    meteoViewModel.fetchMeteo()
}