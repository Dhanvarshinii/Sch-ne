package com.first.beauty.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ConcernScreen [3]
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ConcernsScreen(userConcerns: List<String>,
                   userAllergies: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title
        Text("Hi! Here’s help for your concerns 👇", fontSize = 20.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))

        if (userConcerns.isNotEmpty()) {
            userConcerns.forEach { concern ->
                RecommendationSection(concern)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            Text("You haven’t selected any concerns yet.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (userAllergies.isNotBlank()) {
            Text("🚫 Your Allergies", fontSize = 18.sp, color = Color.Black)
            Text(userAllergies, fontSize = 16.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Product Suggestions
        Text("🎯 Personalized Product Recommendations", fontSize = 20.sp, color = Color.Black)
        Text("💡 Based on your concerns, here are some helpful products.", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Dermatologist Advice & Articles
        Text("🧠 Advice & Articles", fontSize = 20.sp, color = Color.Black)
        Text("📖 How to reduce redness naturally", fontSize = 16.sp)
        Text("💡 The best anti-aging ingredients", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Community Q&A
        Text("🌐 Community & Q&A", fontSize = 20.sp, color = Color.Black)
        Text("🤔 Have questions? Ask the community!", fontSize = 16.sp)
    }
}
@Composable
fun RecommendationSection(concern: String) {
    val recommendations = mapOf(
        "Dry Skin" to listOf("Hyaluronic Acid", "Ceramides", "Squalane"),
        "Acne" to listOf("Salicylic Acid", "Benzoyl Peroxide", "Niacinamide"),
        "Oily Skin" to listOf("Niacinamide", "Clay Masks", "Oil-Free Moisturizers"),
        "Hyperpigmentation" to listOf("Vitamin C", "Alpha Arbutin", "Licorice Extract")
    )

    val avoidList = mapOf(
        "Dry Skin" to "Avoid: Alcohol-based toners, Harsh exfoliants",
        "Acne" to "Avoid: Heavy oils, Comedogenic ingredients",
        "Oily Skin" to "Avoid: Thick creams, Pore-clogging products",
        "Hyperpigmentation" to "Avoid: Harsh scrubs, Irritants"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFE0F2F1), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text("For $concern:", fontSize = 18.sp, color = Color.Black)
        Text("✅ Best Ingredients: ${recommendations[concern]?.joinToString(", ") ?: "General skincare ingredients"}", fontSize = 16.sp)
        Text("❌ ${avoidList[concern] ?: "Avoid harsh chemicals"}", fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConcernScreen() {
    ConcernsScreen(
        userConcerns = listOf("Dry Skin", "Hyperpigmentation"),
        userAllergies = "Peanuts, Parabens"
    )
}