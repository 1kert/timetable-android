package com.example.timetable.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.timetable.ui.screens.NavigationState

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navHostController, NavigationState.Student.toString()) {
        composable(route = NavigationState.Student.toString()) {

        }

        composable(route = NavigationState.Teacher.toString()) {

        }

        composable(route = NavigationState.Room.toString()) {

        }
    }
}
