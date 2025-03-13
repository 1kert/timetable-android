package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.timetable.data.TimetableEvent
import com.example.timetable.ui.theme.TimetableTheme

@Composable
fun EventScreen(
    title: String,
    events: List<List<TimetableEvent>>
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

                ) {
                    Text(
                        text = dateString
                    )

                    Text(
                        text = title
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding))
    }
}

@Preview
@Composable
private fun EventScreenPreview() {
    TimetableTheme {
        EventScreen(
            title = "some test title",
            events = listOf()
        )
    }
}
