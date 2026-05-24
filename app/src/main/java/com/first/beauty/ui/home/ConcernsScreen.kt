package com.first.beauty.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ===============================
// BLACK & WHITE CONCERNS SCREEN
// ===============================

private val PrimaryBlack = Color(0xFF1A1A1A)
private val SoftBlack = Color(0xFF2A2A2A)
private val LightGray = Color(0xFFF7F7F7)
private val BorderGray = Color(0xFFE8E8E8)
private val TextGray = Color(0xFF8C8C8C)

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ConcernsScreen(
    userConcerns: List<String>,
    userAllergies: String = ""
) {

    val concernData = listOf(
        Triple(
            "Dry Skin",
            listOf("Hyaluronic Acid", "Ceramides", "Squalane"),
            listOf("Alcohol toners", "Harsh exfoliants")
        ),
        Triple(
            "Hyperpigmentation",
            listOf("Vitamin C", "Alpha Arbutin", "Licorice Extract"),
            listOf("Harsh scrubs", "Irritants")
        ),
        Triple(
            "Oily Skin",
            listOf("Niacinamide", "Clay", "Salicylic Acid"),
            listOf("Heavy oils", "Comedogenic products")
        ),
        Triple(
            "Acne",
            listOf("Benzoyl Peroxide", "Niacinamide", "Tea Tree"),
            listOf("Pore clogging creams", "Heavy fragrances")
        )
    )

    var activeTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
            .verticalScroll(rememberScrollState())
    ) {

        // ================= HEADER =================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 22.dp, vertical = 24.dp)
        ) {

            Text(
                text = "PERSONALIZED",
                color = TextGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Your Skin Profile",
                fontSize = 30.sp,
                color = PrimaryBlack,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }

        // ================= ALLERGIES CARD =================

        if (userAllergies.isNotBlank()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryBlack
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "⚠",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.padding(6.dp))

                    Column {

                        Text(
                            text = "YOUR ALLERGIES",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 10.sp,
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = userAllergies,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // ================= CONCERN CHIPS =================

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            concernData.forEachIndexed { index, concern ->

                val isSelected = activeTab == index

                Surface(
                    modifier = Modifier.wrapContentWidth(),
                    shape = RoundedCornerShape(100.dp),
                    color = if (isSelected) PrimaryBlack else Color.White,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) PrimaryBlack else BorderGray
                    ),
                    onClick = {
                        activeTab = index
                    }
                ) {

                    Text(
                        text = concern.first,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        color = if (isSelected) Color.White else TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // ================= MAIN CONTENT CARD =================

        val selectedConcern = concernData[activeTab]

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, BorderGray)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // BEST INGREDIENTS

                Text(
                    text = "BEST INGREDIENTS",
                    color = TextGray,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    selectedConcern.second.forEach { ingredient ->

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(LightGray)
                                .border(
                                    1.dp,
                                    BorderGray,
                                    RoundedCornerShape(100.dp)
                                )
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = ingredient,
                                color = PrimaryBlack,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // AVOID SECTION

                Text(
                    text = "AVOID",
                    color = TextGray,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    selectedConcern.third.forEach { avoid ->

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    TextGray.copy(alpha = 0.4f),
                                    RoundedCornerShape(100.dp)
                                )
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = avoid,
                                color = TextGray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // ================= PRODUCT RECOMMENDATIONS =================

        SectionTitle("RECOMMENDED FOR YOU")

        RecommendationCard(
            emoji = "🧴",
            title = "CeraVe Hydrating Cleanser",
            subtitle = "Great for dry skin · Fragrance-free"
        )

        RecommendationCard(
            emoji = "✨",
            title = "The Ordinary Niacinamide",
            subtitle = "Helps brighten and reduce oiliness"
        )

        // ================= ARTICLES =================

        SectionTitle("ARTICLES")

        ArticleCard("How to reduce redness naturally")
        ArticleCard("The best anti-aging ingredients")
        ArticleCard("Morning vs Night: What order matters")

        Spacer(modifier = Modifier.height(30.dp))

        // ================= COMMUNITY =================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, BorderGray)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "COMMUNITY & Q&A",
                    fontSize = 11.sp,
                    color = TextGray,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Have questions about skincare, ingredients, or routines?",
                    fontSize = 14.sp,
                    color = SoftBlack,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlack
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "Ask Community",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

// ===============================
// SECTION TITLE
// ===============================

@Composable
fun SectionTitle(title: String) {

    Text(
        text = title,
        modifier = Modifier.padding(
            start = 18.dp,
            top = 6.dp,
            bottom = 10.dp
        ),
        color = TextGray,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}

// ===============================
// RECOMMENDATION CARD
// ===============================

@Composable
fun RecommendationCard(
    emoji: String,
    title: String,
    subtitle: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, BorderGray)
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(LightGray)
                    .padding(14.dp)
            ) {

                Text(
                    text = emoji,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.padding(6.dp))

            Column {

                Text(
                    text = title,
                    color = PrimaryBlack,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

// ===============================
// ARTICLE CARD
// ===============================

@Composable
fun ArticleCard(title: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, BorderGray)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                color = SoftBlack,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "→",
                color = TextGray,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConcernScreen() {

    ConcernsScreen(
        userConcerns = listOf(
            "Dry Skin",
            "Hyperpigmentation"
        ),
        userAllergies = "Peanuts · Parabens"
    )
}