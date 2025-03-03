package com.example.timetable.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.timetable.components.AppBottomBar
import com.example.timetable.ui.screens.HomeScreen
import com.example.timetable.ui.screens.AppViewModel
import com.example.timetable.ui.screens.SelectionScreen
import com.example.timetable.ui.screens.SelectionScreenType
import kotlinx.serialization.Serializable

@Composable
fun TabNavigation(
    appViewmodel: AppViewModel = hiltViewModel()
) {
    val uiState by appViewmodel.uiState.collectAsState()
    val navController = rememberNavController()

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        bottomBar = {
            AppBottomBar(
                currentNavigationState = uiState.currentNavigation,
                onTeacherClick = { appViewmodel.navigate(navController, NavigationRoute.SelectionScreen(SelectionScreenType.TEACHER)) },
                onStudentClick = { appViewmodel.navigate(navController, NavigationRoute.StudentScreen) },
                onRoomClick = { appViewmodel.navigate(navController, NavigationRoute.SelectionScreen(SelectionScreenType.ROOM)) },
            )
        }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
            startDestination = NavigationRoute.StudentScreen
        ) {
            composable<NavigationRoute.StudentScreen> {
                HomeScreen(
                    uiState = uiState,
                    getDayName = appViewmodel::getDayName,
                    onNextGroup = appViewmodel::onNextGroup
                )
            }

            composable<NavigationRoute.SelectionScreen> {
                val args = it.toRoute<NavigationRoute.SelectionScreen>()

                SelectionScreen(args.selectionScreenType)
            }
        }
    }
}

sealed interface NavigationRoute {
    @Serializable
    data object StudentScreen : NavigationRoute

    @Serializable
    data class SelectionScreen(val selectionScreenType: SelectionScreenType) : NavigationRoute
}
