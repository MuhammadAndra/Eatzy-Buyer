package com.example.eatzy_buyer.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.eatzy_buyer.ui.components.BottomNavBar
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.token

@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToListMenuScreen: (canteenId: Int) -> Unit

) {
    val vm: HomeViewModel = viewModel()
    val canteens by vm.canteens.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
//        vm.fetchUsers()
        vm.fetchCanteens(token = token)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Greetings(username = "Elma")
            SearchButton(onNavigateToSearchScreen = { onNavigateToSearchScreen() })
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp), // atur tinggi agar 2 baris terlihat
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(canteens) { canteen ->
                    CanteenCard(
                        modifier = Modifier.clickable {
                            onNavigateToListMenuScreen(canteen.id)
                        },
                        canteen = canteen,
                    )
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        onNavigateToSearchScreen = {}, onNavigateToListMenuScreen = {})
}

@Composable
fun Greetings(username: String) {
    Column {
        Text("Hey $username!", fontSize = 16.sp)
        Text("Selamat Datang!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview(modifier: Modifier = Modifier) {
    Greetings(username = "Elma")
}

@Composable
fun SearchButton(onNavigateToSearchScreen: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(50.dp),

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToSearchScreen() },
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 13.dp, horizontal = 30.dp),
            text = "Cari menu atau toko...",
            fontSize = 14.sp,
            color = Color(0XFF8F8F8F)
        )
    }
}

@Preview
@Composable
private fun SearchButtonPreview() {
    SearchButton(onNavigateToSearchScreen = {})
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CanteenCard(modifier: Modifier = Modifier, canteen: Canteen) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(142.dp),
            contentScale = ContentScale.Crop,
            model = canteen.imageUrl,
            contentDescription = canteen.name,
            loading = placeholder {
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0XFFFC9824))
                }
            }
        )
        Column(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(text = canteen.name, fontSize = 15.sp)
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0XFFFC9824)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CanteenCardPreview() {
    CanteenCard(
        canteen = Canteen(
            id = 0,
            name = "Kantin Bu Ninik",
            imageUrl = "https://cdn.pixabay.com/photo/2017/05/07/08/56/pancakes-2291908_1280.jpg",
            isOpen = true,
//            menuCategoryId = listOf(1, 2, 3)
        ),
//        onNavigateToListMenuScreen = {}
    )
}

@Composable
fun CanteenList(
    modifier: Modifier = Modifier,
    canteens: List<Canteen>,
    onNavigateToListMenuScreen: (canteenId: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), // atur tinggi agar 2 baris terlihat
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(canteens) { canteen ->
            CanteenCard(
                modifier = Modifier.clickable {
                    onNavigateToListMenuScreen(canteen.id)
                },
                canteen = canteen,
            )
        }
    }
}