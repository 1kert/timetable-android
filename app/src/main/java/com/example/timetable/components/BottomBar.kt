package com.example.timetable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetable.R
import com.example.timetable.ui.NavigationRoute
import com.example.timetable.ui.screens.SelectionScreenType
import com.example.timetable.ui.theme.TimetableTheme
import com.example.timetable.ui.theme.navigationIconSelected

@Composable
fun AppBottomBar(
    currentNavigationState: NavigationRoute,
    onTeacherClick: () -> Unit,
    onStudentClick: () -> Unit,
    onRoomClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(42.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            NavigationButton(
                icon = painterResource(R.drawable.ic_student),
                isSelected = currentNavigationState == NavigationRoute.StudentScreen,
                onClick = onStudentClick
            )

            NavigationButton(
                icon = painterResource(R.drawable.ic_teacher),
                isSelected = currentNavigationState == NavigationRoute.SelectionScreen(SelectionScreenType.TEACHER),
                onClick = onTeacherClick
            )

            NavigationButton(
                icon = painterResource(R.drawable.ic_room),
                isSelected = currentNavigationState == NavigationRoute.SelectionScreen(SelectionScreenType.ROOM),
                onClick = onRoomClick
            )
        }
    }
}

@Composable
fun NavigationButton(
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Image(
            painter = icon,
            contentDescription = null,
            colorFilter = if (isSelected) ColorFilter.tint(navigationIconSelected) else null,
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        )
    }
}

@Preview
@Composable
private fun AppBottomBarPreview() {
    TimetableTheme {
        AppBottomBar(
            onTeacherClick = {},
            onStudentClick = {},
            onRoomClick = {},
            currentNavigationState = NavigationRoute.StudentScreen
        )
    }
}
