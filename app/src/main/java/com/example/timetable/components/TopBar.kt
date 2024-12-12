package com.example.timetable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import com.example.timetable.ui.theme.TimetableTheme

@Composable
fun AppTopBar(
    dateString: String,
    groupTag: String,
    onSettingsClick: () -> Unit,
    onNextGroup: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = dateString,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = groupTag,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable(onClick = onNextGroup)
            )
        }

        IconButton(onClick = onSettingsClick) {
            Image(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TimetableTheme {
        AppTopBar(
            dateString = "31. november",
            groupTag = "TA-70",
            onSettingsClick = {},
            onNextGroup = {}
        )
    }
}
