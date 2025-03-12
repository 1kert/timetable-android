package com.example.timetable.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.timetable.components.AppBottomBar
import com.example.timetable.ui.screens.*
import kotlinx.serialization.Serializable

@Composable
fun TabNavigation(
    appViewmodel: AppViewModel = hiltViewModel()
) {
    val uiState by appViewmodel.timetableState.collectAsStateWithLifecycle()
    val teacherState by appViewmodel.teacherState.collectAsStateWithLifecycle()
    val roomState by appViewmodel.roomState.collectAsStateWithLifecycle()
    val roomEvents by appViewmodel.roomEvents.collectAsStateWithLifecycle()
    val teacherEvents by appViewmodel.teacherEvents.collectAsStateWithLifecycle()
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

                SelectionScreen(
                    selectionScreenType = args.selectionScreenType,
                    onInfoCardClick = { screenType, uuid, title ->
                        navController.navigate(NavigationRoute.EventScreen(
                            title = title,
                            uuid = uuid,
                            screenType = screenType
                        ))
                    }, // todo: navigation to teacher or room timetable screen
                    teacherState = teacherState,
                    roomState = roomState
                )
            }

            composable<NavigationRoute.EventScreen> {
                val args = it.toRoute<NavigationRoute.EventScreen>()

                LaunchedEffect(Unit) {
                    when (args.screenType) {
                        SelectionScreenType.ROOM -> appViewmodel.getRoomEvents(args.uuid)
                        SelectionScreenType.TEACHER -> appViewmodel.getTeacherEvents(args.uuid)
                    }
                }

                EventScreen(
                    title = args.title,
                    events = when(args.screenType) {
                        SelectionScreenType.ROOM -> roomEvents
                        SelectionScreenType.TEACHER -> teacherEvents
                    }
                )
            }
        }
    }
}

sealed interface NavigationRoute {
    @Serializable
    data object StudentScreen : NavigationRoute

    @Serializable
    data class SelectionScreen(val selectionScreenType: SelectionScreenType) : NavigationRoute

    @Serializable
    data class EventScreen(
        val uuid: String,
        val screenType: SelectionScreenType,
        val title : String
    ): NavigationRoute
}
