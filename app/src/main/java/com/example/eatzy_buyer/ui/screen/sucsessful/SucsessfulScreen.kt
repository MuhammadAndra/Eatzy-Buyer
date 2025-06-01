package com.example.eatzy_buyer.ui.screen.successful

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SuccessfulScreen(navController: NavController,orderId:Int,onNavigateToMyOrder: (orderId:Int) -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Log.d("","$orderId")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
                .clickable { onNavigateToMyOrder(orderId) }
//                .clickable {
//                    navController.navigate("home") {
//                        popUpTo("home") { inclusive = true } // supaya tidak bisa back
//                    }
//                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Success",
                    tint = Color(0xFFFC9824),
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Success!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Congratulations! You have been\nsuccessfully ordered",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "Tap to continue",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSuccessfulScreen() {
    val navController = rememberNavController()
    SuccessfulScreen(navController = navController, orderId = 0, onNavigateToMyOrder = {})
}
