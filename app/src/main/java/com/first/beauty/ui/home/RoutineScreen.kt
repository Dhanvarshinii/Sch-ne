package com.first.beauty.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RoutineScreen() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LuxuryRoutineScreen()
    } else {
        Text("Requires Android 8+")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LuxuryRoutineScreen() {

    val currentDate = LocalDate.now()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F8F8)
    ) {

        Box {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {

                // HEADER
                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 22.dp, vertical = 24.dp)
                    ) {

                        Text(
                            text = currentDate.format(
                                DateTimeFormatter.ofPattern("MMMM dd, yyyy")
                            ).uppercase(),
                            color = Color.Gray,
                            fontSize = 11.sp,
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Your Routines",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = Color.Black
                        )
                    }
                }

                // NO SKINCARE DAY
                if (currentDate.dayOfWeek == DayOfWeek.SUNDAY) {

                    item {

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
                                    text = "REST DAY",
                                    color = Color.Gray,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 2.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "It's your no-skincare day. Let your skin breathe naturally today.",
                                    color = Color.White,
                                    lineHeight = 22.sp,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // ROUTINES
                item {
                    RoutineExpandableCard(
                        emoji = "🌅",
                        title = "Morning Routine",
                        steps = listOf(
                            "Cleanser",
                            "Vitamin C Serum",
                            "Moisturizer",
                            "SPF 50"
                        )
                    )
                }

                item {
                    RoutineExpandableCard(
                        emoji = "☀️",
                        title = "Afternoon Routine",
                        steps = emptyList()
                    )
                }

                item {
                    RoutineExpandableCard(
                        emoji = "🌙",
                        title = "Night Routine",
                        steps = listOf(
                            "Oil Cleanser",
                            "Retinol",
                            "Night Cream"
                        )
                    )
                }

                item {
                    RoutineExpandableCard(
                        emoji = "✦",
                        title = "Special Care",
                        steps = emptyList()
                    )
                }
            }

            // FLOATING ACTION BUTTON
            FloatingRoutineMenu()
        }
    }
}

@Composable
fun RoutineExpandableCard(
    emoji: String,
    title: String,
    steps: List<String>
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
                expanded = !expanded
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {

        Column {

            // HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF5F5F5)),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = emoji,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text =
                                if (steps.isEmpty())
                                    "No steps yet"
                                else
                                    "${steps.size} steps",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            // EXPANDED CONTENT
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 18.dp)
                ) {

                    HorizontalDivider(
                        color = Color(0xFFF2F2F2)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (steps.isNotEmpty()) {

                        steps.forEachIndexed { index, step ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = String.format("%02d", index + 1),
                                    color = Color.LightGray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = step,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }

                    } else {

                        Text(
                            text = "No steps added yet",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                Color(0xFFE4E4E4),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "+ Add Step",
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingRoutineMenu() {

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // MENU
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically { it / 2 } + fadeIn(),
            exit = slideOutVertically { it / 2 } + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 90.dp)
        ) {

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .border(
                        1.dp,
                        Color(0xFFEAEAEA),
                        RoundedCornerShape(18.dp)
                    )
                    .width(210.dp)
            ) {

                FloatingMenuItem("Add Entry")
                FloatingMenuItem("Edit Routine")
                FloatingMenuItem("Reset Day")
            }
        }

        // FAB
        FloatingActionButton(
            onClick = {
                expanded = !expanded
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            containerColor = Color.Black,
            contentColor = Color.White,
            shape = CircleShape
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
fun FloatingMenuItem(text: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {

        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewRoutineScreen() {
    RoutineScreen()
}