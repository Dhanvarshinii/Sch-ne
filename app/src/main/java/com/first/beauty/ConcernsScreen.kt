package com.first.beauty

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ConcernScreen [3]
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ConcernsScreen() {
    var selectedConcerns by remember { mutableStateOf(mutableSetOf<String>()) }
    var allergies by remember { mutableStateOf(TextFieldValue("")) }
    val concernsList = listOf(
        "Acne", "Dry Skin", "Oily Skin", "Hyperpigmentation",
        "Redness/Sensitivity", "Dark Circles", "Fine Lines/Wrinkles",
        "Sun Damage", "Enlarged Pores"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title
        Text("Tell us about your skin concerns & allergies", fontSize = 20.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(12.dp))

        // Multi-Selection for Skin Concerns
        concernsList.forEach { concern ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedConcerns.contains(concern)) Color(0xFF80CBC4) else Color.LightGray)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(concern, fontSize = 16.sp)
                IconButton(
                    onClick = {
                        selectedConcerns = selectedConcerns.toMutableSet().apply {
                            if (contains(concern)) remove(concern) else add(concern)
                        }
                    }
                ) {
                    if (selectedConcerns.contains(concern)) {
                        Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.Green)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Allergy Input
        val focusManager = LocalFocusManager.current
        Text("Allergies (Optional)", fontSize = 18.sp, color = Color.Black)
        BasicTextField(
            value = allergies,
            onValueChange = { allergies = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save & Update Button
        Button(
            onClick = { /* Save User Preferences */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688))
        ) {
            Text("Save & Update", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dynamic Recommendations Based on Selection
        if (selectedConcerns.isNotEmpty()) {
            Text("Recommended Routine & Ingredients", fontSize = 20.sp, color = Color.Black)

            selectedConcerns.forEach { concern ->
                RecommendationSection(concern)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Suggestions
        Text("Personalized Product Recommendations", fontSize = 20.sp, color = Color.Black)
        Text("💡 Based on your concerns, we recommend dermatologist-approved products.", fontSize = 14.sp)
        // (You can populate products dynamically from API)

        Spacer(modifier = Modifier.height(16.dp))

        // Dermatologist Advice & Articles
        Text("Dermatologist Advice & Articles", fontSize = 20.sp, color = Color.Black)
        Text("📖 How to reduce redness naturally", fontSize = 16.sp)
        Text("💡 The best anti-aging ingredients", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Community Q&A (Optional)
        Text("Community & Q&A", fontSize = 20.sp, color = Color.Black)
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
    ConcernsScreen()
}