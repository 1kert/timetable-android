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
import com.example.timetable.components.EventCard
import com.example.timetable.data.TimetableEvent
import com.example.timetable.ui.theme.TimetableTheme

@Composable
fun EventScreen(
    title: String,
    events: List<List<TimetableEvent>>,
    getDateString: (events: List<TimetableEvent>) -> String
) {
    var dateString by remember { mutableStateOf("some date string") } // todo: should be empty

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
        HorizontalPager(
            state = rememberPagerState { events.size },
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) { page ->
            LaunchedEffect(Unit) {
                dateString = getDateString(events[page])
            }

            Column {
                val todayEvents = events[page]
                todayEvents.forEach {
                    EventCard(it)
                }
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
