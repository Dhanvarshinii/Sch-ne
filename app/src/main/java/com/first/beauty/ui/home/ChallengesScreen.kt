package com.first.beauty.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Challenge(
    val title: String,
    val desc: String,
    val progress: Int,
    val total: Int,
    val tag: String
)

val challengeList = listOf(
    Challenge(
        "No Sugar for 7 Days",
        "Cut refined sugar to reduce inflammation and breakouts.",
        3,
        7,
        "DIET"
    ),
    Challenge(
        "Hydration Challenge",
        "Drink 8 glasses daily for 14 days.",
        9,
        14,
        "WELLNESS"
    ),
    Challenge(
        "No Touching Your Face",
        "Avoid touching your face for 5 days straight.",
        2,
        5,
        "HABIT"
    ),
    Challenge(
        "Pillowcase Change",
        "Change pillowcase every 3 days.",
        0,
        30,
        "HYGIENE"
    )
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChallengesScreen() {

    var currentMonth by remember { mutableStateOf(LocalDate.now()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F8F8)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            item {

                // HEADER
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 22.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    Column {

                        Text(
                            text = currentMonth.format(
                                DateTimeFormatter.ofPattern("MMMM yyyy")
                            ).uppercase(),
                            color = Color.Gray,
                            fontSize = 11.sp,
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Challenges",
                            fontSize = 30.sp,
                            color = Color(0xFF1A1A1A),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color(0xFFF1F1F1))
                            .border(
                                1.dp,
                                Color(0xFFE0E0E0),
                                RoundedCornerShape(100.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "🔥 7-day streak",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // MINI CALENDAR
            item {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(
                            1.dp,
                            Color(0xFFEAEAEA),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    val days = listOf("M", "T", "W", "T", "F", "S", "S")

                    days.forEachIndexed { index, day ->

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = day,
                                fontSize = 10.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (index < 5)
                                            Color.Black
                                        else
                                            Color(0xFFF2F2F2)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "${18 + index}",
                                    color = if (index < 5)
                                        Color.White
                                    else
                                        Color.Gray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // CHALLENGES
            items(challengeList) { challenge ->

                ChallengeCard(challenge)
            }
        }
    }
}

@Composable
fun ChallengeCard(challenge: Challenge) {

    var joined by remember {
        mutableStateOf(challenge.progress > 0)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                if (joined) Color.Black else Color(0xFFEAEAEA)
            )
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color(0xFFF5F5F5))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {

                        Text(
                            text = challenge.tag,
                            fontSize = 10.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = challenge.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF1A1A1A)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = challenge.desc,
                        color = Color.Gray,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { joined = !joined },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (joined) Color.Black else Color.White,
                        contentColor =
                            if (joined) Color.White else Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder,
                    shape = RoundedCornerShape(10.dp)
                ) {

                    Text(
                        if (joined) "✓ In" else "Join",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (joined && challenge.progress > 0) {

                Spacer(modifier = Modifier.height(18.dp))

                HorizontalDivider(color = Color(0xFFF1F1F1))

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "PROGRESS",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "${challenge.progress}/${challenge.total} days",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = challenge.progress.toFloat() / challenge.total,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    color = Color.Black,
                    trackColor = Color(0xFFF0F0F0)
                )
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
