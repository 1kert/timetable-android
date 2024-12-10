package com.example.timetable.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable.data.TimetableEvent
import com.example.timetable.components.EventCard

@Composable
fun HomeScreen(
    appViewmodel: HomeScreenViewmodel = hiltViewModel()
) {
    val uiState by appViewmodel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MainAppContent(
                uiState = uiState,
                getDayName = appViewmodel::getDayName,
                onNextGroup = appViewmodel::onNextGroup
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainAppContent(
    uiState: TimetableViewState,
    onNextGroup: () -> Unit,
    getDayName: (List<TimetableEvent>) -> String,
) {
    Text("")

    HorizontalPager(state = rememberPagerState { uiState.events.size }) { page ->
        TimetablePage(
            events = uiState.events[page],
            getDayName = getDayName,
            currentGroup = uiState.selectedGroup,
            onNextGroup = onNextGroup
        )
    }
}

@Composable
fun TimetablePage(
    events: List<TimetableEvent>,
    currentGroup: String,
    getDayName: (List<TimetableEvent>) -> String,
    onNextGroup: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = getDayName(events),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = currentGroup,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable(onClick = onNextGroup)
            )
        }

        events.forEach { event ->
            EventCard(event)
        }
    }
}
