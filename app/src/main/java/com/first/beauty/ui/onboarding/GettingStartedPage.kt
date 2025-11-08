package com.first.beauty.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.first.beauty.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GettingStartedPage(navController: NavController) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    // 🌸 Onboarding pages (title + description + image)
    val pages = listOf(
        Triple(
            "Beauty begins with consistency.",
            "Schöne helps you build a self-care routine that fits your lifestyle — simple, personal, and empowering.",
            R.drawable.oboard1 // replace with your illustration
        ),
        Triple(
            "Your routine, your way.",
            "Track your skincare, haircare, and wellness steps effortlessly. Stay on top of what makes you feel your best — every day.",
            R.drawable.wellness // replace with your illustration
        ),
        Triple(
            "See your glow grow.",
            "Celebrate your progress with insights and reminders that keep you motivated to stay consistent.",
            R.drawable.glow // replace with your illustration
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = pages.size + 1, // +1 for the Get Started page
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (page < pages.size) {
                    val (title, description, imageRes) = pages[page]

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Onboarding Image",
                        modifier = Modifier
                            .size(220.dp)
                            .padding(bottom = 24.dp)
                    )

                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = description,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                } else {
                    // Last page: Get Started button
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo_default),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(160.dp)
                            .padding(bottom = 32.dp)
                    )
                    Text(
                        text = "Welcome to Schöne",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your beauty journey begins here.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(0.8f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Get started!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // 🌷 Pager Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = Color.LightGray
        )
    }
}
