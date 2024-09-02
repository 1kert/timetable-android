package com.example.timetable

import android.app.Application
import android.content.pm.LauncherApps
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable.ui.theme.TimetableTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimetableTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(
    appViewmodel: AppViewmodel = hiltViewModel()
) {
    val uiState by appViewmodel.uiState.collectAsState()
    val todayEvents = uiState.getOrNull(0)
    val tomorrowEvents = uiState.getOrNull(1)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                todayEvents?.forEach { event ->
                    val rooms = event.rooms?.map { it.roomCode }?.joinToString(", ")
                    Text(
                        text = "${event.name} $rooms [${event.timeStart}-${event.timeEnd}]",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(38.dp))

                tomorrowEvents?.forEach { event ->
                    val rooms = event.rooms?.map { it.roomCode }?.joinToString(", ")
                    Text(
                        text = "${event.name} $rooms [${event.timeStart}-${event.timeEnd}]",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
