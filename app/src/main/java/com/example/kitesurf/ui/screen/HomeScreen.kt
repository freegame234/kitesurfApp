package com.example.kitesurf.ui.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kitesurf.ui.theme.*
import com.example.kitesurf.ui.viewmodel.*
import kotlinx.coroutines.delay
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kitesurf.getLastLocation
import com.example.kitesurf.session.clearSession
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    val navigateTo = activity?.intent?.getStringExtra("navigate_to")
    LaunchedEffect(navigateTo) {
        if (navigateTo == "competition") {
            selectedTabIndex = 0 // index de l’onglet "Compétition"
        }
    }


    LaunchedEffect(Unit) {
        while (true) {
            getLastLocation(context) { lat, lon ->
                meteoViewModel.loadWeather(lat, lon)
            }
            delay(5000) // toutes les 5 secondes
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BlueOceanLight,
                drawerContentColor = Color.Black
            ) {
                // En-tête
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BlueOceanDark)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "🏄 KiteSurf App",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Bienvenue !",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                DrawerItem(
                    icon = Icons.Default.Camera,
                    label = "Vidéos",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("video_list_screen") {
                                popUpTo("home_screen") { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    }
                )
                DrawerItem(
                    icon = Icons.Default.Stream,
                    label = "Streaming",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("streaming_screen") {
                                popUpTo("home_screen") { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    }
                )
                DrawerItem(
                    icon = Icons.Default.Logout,
                    label = "Déconnexion",
                    onClick = {
                        scope.launch {
                            clearSession(context)
                            navController.navigate("login_screen") {
                                popUpTo("home_screen") { inclusive = true }
                            }
                        }
                    }
                )

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("KiteSurf App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
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
                            getLastLocation(context) { lat, lon ->
                                meteoViewModel.loadWeather(lat, lon)
                            }
                            classementViewModel.fetchClassement(1)
                            calendrierViewModel.fetchCalendrier(1)
                            competitionViewModel.fetchCompetitions()

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
                            1 -> ClassementTab(classementViewModel, competitionViewModel)
                            2 -> CalendrierTab(calendrierViewModel)
                            3 -> WeatherScreen(meteoViewModel)
                            4 -> LocalisationTabScreen()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DrawerItem(icon: ImageVector, label: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BlueOceanDark
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
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