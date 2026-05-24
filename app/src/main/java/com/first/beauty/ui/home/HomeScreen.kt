package com.first.beauty.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun HomeScreen(userName: String) {

    val dailyMessages = listOf(
        "Drink plenty of water today!",
        "Don’t forget your sunscreen ☀️",
        "Take 5 minutes to relax your mind 🧘",
        "Try a gentle face massage tonight"
    )

    val dayIndex = remember {
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.DAY_OF_YEAR) % dailyMessages.size
    }

    val messageOfTheDay = dailyMessages[dayIndex]

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F8F8)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            // HEADER
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 22.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {

                    Column {

                        Text(
                            text = "GOOD MORNING",
                            color = Color.Gray,
                            fontSize = 11.sp,
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = userName,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF1A1A1A)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✿",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "🔥 7 days",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // DAILY MESSAGE
            item {
                DailyTipCard(messageOfTheDay)
            }

            // ROUTINE TRACKER
            item {
                LuxuryRoutineTracker()
            }

            // SKIN SNAPSHOT
            item {
                SkinSnapshotSection()
            }

            // RECOMMENDED
            item {
                RecommendationCard()
            }

            // MOOD TRACKER
            item {
                MoodTrackerCard()
            }
        }
    }
}

@Composable
fun DailyTipCard(message: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "DAILY TIP",
                color = Color(0xFFAAAAAA),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                color = Color.White,
                lineHeight = 22.sp,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun LuxuryRoutineTracker() {

    val routineSteps = listOf(
        "Cleanser",
        "Toner",
        "Moisturizer",
        "Sunscreen"
    )

    val completedSteps = remember { mutableStateMapOf<String, Boolean>() }

    val doneCount = completedSteps.values.count { it }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Today's Routine",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )

                Text(
                    text = "$doneCount / ${routineSteps.size}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            LinearProgressIndicator(
                progress = {
                    doneCount.toFloat() / routineSteps.size
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(100.dp)),
                color = Color.Black,
                trackColor = Color(0xFFF0F0F0)
            )

            Spacer(modifier = Modifier.height(18.dp))

            routineSteps.forEach { step ->

                val checked = completedSteps[step] ?: false

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            completedSteps[step] = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.LightGray
                        )
                    )

                    Text(
                        text = step,
                        color =
                            if (checked)
                                Color.LightGray
                            else
                                Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SkinSnapshotSection() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        // Skin Type
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(18.dp),
            border = CardDefaults.outlinedCardBorder()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "SKIN TYPE",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Dry",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // Allergies
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(18.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "ALLERGIES",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Parabens\nPeanuts",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RecommendationCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF4F4F4)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "🧴",
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    text = "RECOMMENDED",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "CeraVe Hydrating Cleanser",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Great for dry skin · Fragrance-free",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun MoodTrackerCard() {

    var selectedMood by remember {
        mutableStateOf("")
    }

    val moods = listOf("😊", "😐", "😢", "😴")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "How are you feeling today?",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                moods.forEach { mood ->

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedMood == mood)
                                    Color.Black
                                else
                                    Color(0xFFF5F5F5)
                            )
                            .border(
                                1.dp,
                                if (selectedMood == mood)
                                    Color.Black
                                else
                                    Color(0xFFE5E5E5),
                                CircleShape
                            )
                            .clickable {
                                selectedMood = mood
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = mood,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(userName = "Varsha")
}