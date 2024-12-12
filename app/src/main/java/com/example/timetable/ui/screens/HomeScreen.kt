package com.example.timetable.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable.components.AppBottomBar
import com.example.timetable.components.AppTopBar
import com.example.timetable.components.EventCard
import com.example.timetable.data.TimetableEvent

@Composable
fun HomeScreen(
    appViewmodel: HomeScreenViewmodel = hiltViewModel()
) {
    val uiState by appViewmodel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar()
        }
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

    HorizontalPager(
        state = rememberPagerState { uiState.events.size },
        modifier = Modifier.fillMaxSize()
    ) { page ->
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        AppTopBar(
            dateString = getDayName(events),
            groupTag = currentGroup,
            onSettingsClick = {},
            onNextGroup = onNextGroup
        )

        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(events) { event ->
                EventCard(event)
            }

            item {
                Spacer(Modifier)
            }
        }
    }
}
