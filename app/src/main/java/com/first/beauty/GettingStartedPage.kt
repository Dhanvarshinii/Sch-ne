package com.first.beauty

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GettingStartedPage(navController: NavController) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val tips = listOf(
        "Stay connected with your team anytime.",
        "Manage your projects effortlessly.",
        "Track your tasks with one swipe."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(count = 4, state = pagerState, modifier = Modifier.weight(1f)) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_default),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                if (page < 3) {
                    Text(
                        text = tips[page],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                } else {
                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(0.8f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "Get Started")
                    }
                }
            }
        }

        // Pager Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colorScheme.primary
        )
    }
}
