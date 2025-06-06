package com.example.kitesurf.ui.screen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kitesurf.getLastLocation
import com.example.kitesurf.ui.theme.*
import com.example.kitesurf.ui.viewmodel.*
import kotlinx.coroutines.delay
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.example.kitesurf.domaine.model.Position
import com.example.kitesurf.network.RetrofitInstance
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    classementViewModel: ClassementViewModel = viewModel(),
    calendrierViewModel: CalendrierViewModel = viewModel(),
    meteoViewModel: WeatherViewModel = viewModel(),
    competitionViewModel: CompetitionViewModel = viewModel(factory = CompetitionViewModelFactory(LocalContext.current))
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val scope = rememberCoroutineScope()

    // Rafraîchissement auto toutes les 10s
    LaunchedEffect(Unit) {
        while (true) {
            classementViewModel.fetchClassement(1)
            competitionViewModel.fetchCompetitions()
            calendrierViewModel.fetchCalendrier(1)
            delay(10_000L)
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
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BlueOceanMedium)
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    isRefreshing = true
                    scope.launch {
                        refreshAllData(
                            classementViewModel,
                            competitionViewModel,
                            calendrierViewModel,
                            meteoViewModel // même si météo est géré dans WeatherScreen, c’est bien de prévoir
                        )
                        delay(1000)
                        isRefreshing = false
                    }
                }
            ) {
                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
                    }
                ) { targetIndex ->
                    when (targetIndex) {
                        0 -> CompetitionTab(competitionViewModel)
                        1 -> ClassementTab(classementViewModel)
                        2 -> CalendrierTab(calendrierViewModel)
                        3 -> WeatherScreen(meteoViewModel)
                        4 -> LocalisationTabScreen()
                    }
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(calendrier) { event ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${event.date_debut} → ${event.date_fin}",
                        style = MaterialTheme.typography.titleMedium,
                        color = BlueOceanDark
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = event.localisation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val weather by viewModel.weather.collectAsState()

    LaunchedEffect(Unit) {
        getLastLocation(context) { lat, lon ->
            viewModel.loadWeather(lat, lon)
        }
    }
    WeatherWidget(weather)
}

@Composable
fun LocalisationTabScreen() {
    var positions by remember { mutableStateOf<List<Position>>(emptyList()) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(48.0, 2.0), 5f)
    }

    LaunchedEffect(Unit) {
        try {
            positions = RetrofitInstance.api.getPositions()
            Log.d("LocalisationTab", "Positions reçues: ${positions.size}")
        } catch (e: Exception) {
            Log.e("LocalisationTab", "Erreur chargement positions", e)
        }
    }

    LaunchedEffect(positions) {
        if (positions.isNotEmpty()) {
            val avgLat = positions.map { it.latitude }.average()
            val avgLng = positions.map { it.longitude }.average()
            val update = CameraUpdateFactory.newLatLngZoom(LatLng(avgLat, avgLng), 12f)
            cameraPositionState.animate(update)
        }
    }

    // NE PAS utiliser fillMaxSize ici
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(), // Ici c'est ok car on limite par weight
                cameraPositionState = cameraPositionState,
            ) {
                positions
                    .filter { it.latitude != null && it.longitude != null }
                    .forEach { position ->
                        Marker(
                            state = MarkerState(position = LatLng(position.latitude, position.longitude)),
                            title = "Kitesurfer #${position.kitesurfer_id}",
                            snippet = "Dernière mise à jour: ${position.timestamp}"
                        )
                    }
            }
        }
    }
}

private fun refreshAllData(
    classementViewModel: ClassementViewModel,
    competitionViewModel: CompetitionViewModel,
    calendrierViewModel: CalendrierViewModel,
    weatherViewModel: WeatherViewModel
) {
    classementViewModel.fetchClassement(1)
    competitionViewModel.fetchCompetitions()
    calendrierViewModel.fetchCalendrier(1)
}