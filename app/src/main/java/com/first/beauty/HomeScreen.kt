package com.first.beauty

import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


// Home Screen [1]
@Composable
fun HomeScreen(userName: String) {
    val dailyMessages = listOf(
        "Drink plenty of water today!",
        "Don’t forget your sunscreen ☀️",
        "Take 5 minutes to relax your mind 🧘‍♀️",
        "Try a gentle face massage tonight 💆‍♀️"
    )
    val dayIndex = remember {
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.DAY_OF_YEAR) % dailyMessages.size
    }
    val messageOfTheDay = dailyMessages[dayIndex]


    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Text(
                text = "Good morning, $userName 🌞",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        item {
            DailyMessageCard(messageOfTheDay)
        }

        item {
            QuickRoutineTracker()
        }

        item {
            Text(
                text = "Featured",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        item {
            FeaturedCard("💡 Tip of the Day", "Clean your pillowcases weekly to avoid breakouts.")
        }

        item {
            FeaturedCard("🛍️ Recommended Products", "Try the CeraVe Hydrating Cleanser today!")
        }

        item {
            FeaturedCard("📝 Shopping List", "1. Toner\n2. Sunscreen\n3. Night Cream")
        }

        item {
            MoodTrackerCard()
        }
    }
}


@Composable
fun DailyMessageCard(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("✨ Daily Message", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun QuickRoutineTracker() {
    val routineSteps = listOf("Cleanser", "Toner", "Moisturizer", "Sunscreen")
    val completedSteps = remember { mutableStateMapOf<String, Boolean>() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🧴 Quick Routine Tracker", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            routineSteps.forEach { step ->
                val checked = completedSteps[step] ?: false
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { completedSteps[step] = it }
                    )
                    Text(text = step)
                }
            }
        }
    }
}

@Composable
fun FeaturedCard(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun MoodTrackerCard() {
    var selectedMood by remember { mutableStateOf("") }
    val moods = listOf("😊", "😐", "😢", "😡")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🧠 How are you feeling today?", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                moods.forEach { mood ->
                    Text(
                        text = mood,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { selectedMood = mood },
                        style = if (selectedMood == mood) MaterialTheme.typography.bodyLarge.copy(color = Color.Blue)
                        else MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(userName = "Test1")
}
