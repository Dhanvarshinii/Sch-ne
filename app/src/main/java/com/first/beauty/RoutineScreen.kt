package com.first.beauty

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Routine screen [2]
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RoutineScreen() {
    // Get the current date using LocalDate (compatible with API level 24+)
    val currentDate = LocalDate.now() // Get today's date using LocalDate
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Page indicator (optional)
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
            spacing = 4.dp
        )

        // HorizontalPager to swipe between Today, Yesterday, and Tomorrow
        HorizontalPager(
            count = 3, // 3 pages: Yesterday, Today, Tomorrow
            state = pagerState,
            modifier = Modifier.fillMaxHeight(0.9f)
        ) { page ->
            val pageDate = when (page) {
                0 -> {
                    currentDate.minusDays(1) // Calculate yesterday
                }
                1 -> { // Today
                    currentDate // Use today's date
                }
                else -> { // Tomorrow
                    currentDate.plusDays(1) // Calculate tomorrow
                }
            }

            // Format the date to show as a string
            val formattedPageDate = pageDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))

            // Display the log for the respective date
            RoutinePage(formattedPageDate)
        }
    }
}

@Composable
fun RoutinePage(formattedDate: String) {
    var expanded by remember { mutableStateOf(false) }  // Track if the dropdown is visible
    var fabBounds by remember { mutableStateOf(Rect.Zero) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log for $formattedDate",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add the content for the log (could be an editable text or any other UI component)
        Text(
            text = "Add your routine details here...",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Add a button to enter a routine, or any other content
        Button(onClick = { /* Handle the button click */ }) {
            Text(text = "Add Routine")
        }
    }

    // Floating Action Button (FAB) to show dropdown
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
                .onGloballyPositioned { layoutCoordinates ->
                    fabBounds = layoutCoordinates.boundsInWindow()
                }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }

        // Dropdown menu above the FAB
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(initialOffsetY = { 40 }) + expandVertically(),
            exit = slideOutVertically(targetOffsetY = { 40 }),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 90.dp) // Adjust this to position above the FAB
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp)
                    .width(200.dp)
            ) {
                DropdownItem(text = "Add an entry")
                DropdownItem(text = "Edit an entry")
            }
        }
    }
}


@Composable
fun DropdownItem(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.Transparent)
            .padding(3.dp),
        color = Color.Black
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewRoutineScreen() {
    RoutineScreen()
}