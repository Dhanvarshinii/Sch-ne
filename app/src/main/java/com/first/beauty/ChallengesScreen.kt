package com.first.beauty

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

// ChallengeScreen [4]
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChallengesScreen() {
    var currentMonth by remember { mutableStateOf(LocalDate.now()) } // Tracks the displayed month
    val daysInMonth = YearMonth.of(currentMonth.year, currentMonth.month).lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentMonth.year, currentMonth.month, 1).dayOfWeek.value % 7
    val streakCount = 7 // Example streak count (Replace with actual data)

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            // Month Navigation + Streak Counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Month")
                }
                Text(
                    text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    style = MaterialTheme.typography.titleLarge
                )
                // Streak Counter on Top Right
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔥 $streakCount-day streak",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
                IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month")
                }
            }
        }

        item {
            // Calendar Grid
            Column {
                // Weekday Labels
                Row(Modifier.fillMaxWidth()) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Days Grid
                val totalCells = firstDayOfMonth + daysInMonth
                val rows = (totalCells / 7) + if (totalCells % 7 == 0) 0 else 1
                Column {
                    for (row in 0 until rows) {
                        Row(Modifier.fillMaxWidth()) {
                            for (col in 0 until 7) {
                                val dayNumber = row * 7 + col - firstDayOfMonth + 1
                                if (dayNumber in 1..daysInMonth) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .background(Color.Transparent),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayNumber.toString(),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Challenges Section
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { Text("30-Day Challenges", style = MaterialTheme.typography.titleMedium) }
        items(5) { index -> ChallengeCard("30-Day Challenge ${index + 1}") }

        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { Text("10-Day Challenges", style = MaterialTheme.typography.titleMedium) }
        items(5) { index -> ChallengeCard("10-Day Challenge ${index + 1}") }
    }
}

@Composable
fun ChallengeCard(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Start Challenge */ }) {
                Text("Start Challenge")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewChallengesScreen() {
    ChallengesScreen()
}
