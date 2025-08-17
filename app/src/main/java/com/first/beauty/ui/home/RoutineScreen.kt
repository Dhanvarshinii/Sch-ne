package com.first.beauty.ui.home

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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material3.Card
import androidx.compose.foundation.shape.RoundedCornerShape
import java.time.DayOfWeek


// Routine screen [2]

@Composable
fun RoutineScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        RoutineContent()
    } else {
        Text("This feature requires Android 8.0 (API 26) or higher.")
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RoutineContent() {
    // Get the current date using LocalDate (compatible with API level 24+)
    val currentDate = LocalDate.now() // Get today's date using LocalDate
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = Color.Gray,
            spacing = 6.dp
        )
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier.fillMaxHeight(0.9f)
        ) { page ->
            val pageDate = when (page) {
                0 -> currentDate.minusDays(1)
                1 -> currentDate
                else -> currentDate.plusDays(1)
            }

            val formattedDate = pageDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
            RoutinePage(pageDate, formattedDate)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutinePage(date: LocalDate, formattedDate: String) {
    var fabExpanded by remember { mutableStateOf(false) }
    val isNoSkincareDay = date.dayOfWeek == DayOfWeek.SUNDAY // You can later make this editable

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Routine for $formattedDate",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (isNoSkincareDay) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Text(
                    text = "It's your no-skincare day! Let your skin breathe.",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF8D6E63)
                )
            }
        }

        RoutineSection("Morning Routine")
        RoutineSection("Afternoon Routine")
        RoutineSection("Night Routine")
        RoutineSection("Special Care Routine")

        Spacer(modifier = Modifier.height(40.dp))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { fabExpanded = !fabExpanded },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Expand")
        }

        AnimatedVisibility(
            visible = fabExpanded,
            enter = slideInVertically(initialOffsetY = { 40 }) + expandVertically(),
            exit = slideOutVertically(targetOffsetY = { 40 }),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 90.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .width(200.dp)
            ) {
                DropdownItem(text = "Add Entry")
                DropdownItem(text = "Edit Routine")
                DropdownItem(text = "Reset Day")
            }
        }
    }
}

@Composable
fun RoutineSection(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "No routine added yet.", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Add logic */ }) {
                Text("Add $title")
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
            .padding(12.dp),
        color = Color.Black
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewRoutineScreen() {
    RoutineScreen()
}