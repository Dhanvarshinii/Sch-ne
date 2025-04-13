package com.first.beauty

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Home Screen [1]
@Composable
fun HomeScreen() {
    // Content for the Home tab
    // List of daily status cards
    val statusList = listOf(
        "Feeling great today! Your skin is glowing.",
        "Tired, but your beauty routine helped you feel refreshed.",
        "It's a good day for a skincare mask!",
        "You're looking fabulous as always!",
    )

    // LazyColumn for scrollable status boxes
    LazyColumn(modifier = Modifier.padding(25.dp)) {
        items(statusList.size) { index ->
            StatusCard(status = statusList[index])
        }
    }
}

// Home page - cards
@Composable
fun StatusCard(status: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Summary",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = status,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Handle click, maybe navigate to another screen */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "View Details")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}
