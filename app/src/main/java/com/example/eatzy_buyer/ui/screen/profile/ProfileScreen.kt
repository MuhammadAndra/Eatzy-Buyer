package com.example.eatzy_buyer.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.eatzy_buyer.ui.components.BottomNavBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.eatzy_buyer.ui.components.TopNavBar

@Preview
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}
@Composable
fun ProfileScreen(navController: NavController, onNavigateToEdit: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize().background(Color.White)
    ) {
        TopNavBar(
            title = "Profil",
            onBackClick = { navController.popBackStack() }
        )

        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Andrew",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "andrew@gmail.com",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { onNavigateToEdit() },
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Color(0xFFF59A2F)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFF59A2F)),
                    ) {
                        Text(text = "Edit")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Tombol Keluar
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "Keluar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
