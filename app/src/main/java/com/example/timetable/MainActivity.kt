package com.example.timetable

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable.components.EventCard
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
            MainAppContent(todayEvents = todayEvents, tomorrowEvents = tomorrowEvents)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainAppContent(
    todayEvents: List<TimetableEvent>?,
    tomorrowEvents: List<TimetableEvent>?
) {
    HorizontalPager(
        state = rememberPagerState {
            2
        }
    ) { page ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (page) {
                0 -> {
                    Text(
                        text = "today",
                        style = MaterialTheme.typography.titleLarge
                    )
                    todayEvents?.forEach { event ->
                        EventCard(event)
                    }
                }

                1 -> {
                    Text(
                        text = "tomorrow",
                        style = MaterialTheme.typography.titleLarge
                    )
                    tomorrowEvents?.forEach { event ->
                        EventCard(event)
                    }
                }
            }
        }
    }
}
