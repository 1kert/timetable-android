package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.timetable.components.EventsList
import com.example.timetable.data.TimetableEvent
import com.example.timetable.ui.theme.TimetableTheme
import com.example.timetable.ui.theme.eventHorizontalPadding
import com.example.timetable.ui.theme.eventTopPadding

@Composable
fun EventScreen(
    title: String,
    events: List<List<TimetableEvent>>,
    getDateString: (events: List<TimetableEvent>) -> String
) {
    var dateString by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,

                ) {
                    Text(
                        text = dateString,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )

                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { contentPadding ->
        val pagerState = rememberPagerState { events.size }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) { page ->
            LaunchedEffect(pagerState.targetPage) { dateString = getDateString(events[pagerState.targetPage]) }

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = eventTopPadding)
                    .padding(horizontal = eventHorizontalPadding)
            ) {
                EventsList(events[page])
            }
        }
    }
}

@Preview
@Composable
private fun EventScreenPreview() {
    TimetableTheme {
        EventScreen(
            title = "K-209",
            events = listOf(
                listOf(
                    TimetableEvent(
                        name = "Mingi tund",
                        date = "2024-12-17T00:00:00Z",
                        timeStart = "22:00",
                        timeEnd = "22:01",
                        teachers = listOf(),
                        rooms = listOf(),
                        studentGroups = listOf(),
                        singleEvent = false
                    )
                )
            ),
            getDateString = {_ -> "31. November"}
        )
    }
}
