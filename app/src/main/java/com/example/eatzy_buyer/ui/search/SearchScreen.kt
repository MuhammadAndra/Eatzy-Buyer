package com.example.eatzy_buyer.ui.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.token
import com.example.eatzy_buyer.ui.components.TopBar
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import java.text.NumberFormat
import java.util.Locale

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onNavigateToListMenuScreen: (canteenId: Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val vm: SearchViewModel = viewModel()
    val menus by vm.menus.collectAsStateWithLifecycle()
    val rekomendasi = arrayOf("Ayam", "Mie", "Telor")

    val toastMessage by vm.toastMessage.observeAsState()
    val context = LocalContext.current

    fun onAddToFavorite(id: Int) {
        vm.createFavorite(
            token = token,
            id = id
        )
    }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            vm.clearToastMessage()
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { searchQuery }
            .debounce(300)                   // tunggu 500ms tanpa perubahan
            .distinctUntilChanged()         // hanya yang berbeda
            .collectLatest { query ->
                vm.getMenuByQuery(query)
            }
    }
    Scaffold(
        topBar = {
            TopBar(
                title = "Cari",
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                value = searchQuery,
                placeholder = {
                    Text(
                        "Cari menu yang anda inginkan",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                },
                onValueChange = { searchQuery = it },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFF59A2F), // Warna kuning saat fokus
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color(0xFFF59A2F),  // Warna label saat fokus (opsional)
                    cursorColor = Color(0xFFF59A2F)         // Warna kursor saat mengetik (opsional)
                ),
                maxLines = 1
            )
            if (searchQuery.isBlank()) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Text(
                            modifier = Modifier.padding(top = 30.dp),
                            text = "Rekomendasi",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0XFFF59A2F)
                        )
                    }

                    items(rekomendasi) { rekomendasi ->
                        RecommendationCard(
                            modifier = Modifier.clickable {
                                searchQuery = rekomendasi
                            },
                            recommendation = rekomendasi
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.padding(top = 30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(menus) { menu ->
                    ElevatedCard(
                        modifier = Modifier
                            .background(Color(0XFFFFFFFF))
                            .fillMaxWidth()
                            .clickable { onNavigateToListMenuScreen(menu.canteenId) },
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {
                        SearchMenuCard(
                            menu = menu,
                            onAddToFavorite = {
                                onAddToFavorite(
                                    menu.id
                                )
                            }
                        )
                    }
                }
                item {}
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(onNavigateUp = {}, onNavigateToListMenuScreen = {})
}

@Composable
fun SearchMenuCard(
    modifier: Modifier = Modifier,
    menu: Menu,
    onAddToFavorite: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(13.dp)
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menu.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Gambar Menu ${menu.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        text = menu.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(text = menu.canteenName)
                }
                Text(
                    text = NumberFormat
                        .getCurrencyInstance(Locale("in", "ID"))
                        .format(menu.price),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.size(25.dp),
                onClick = onAddToFavorite,
                colors = IconButtonDefaults.iconButtonColors(Color(0XFFFC9824))
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Icon Favorite",
                    tint = Color(0XFFFFFFFF)
                )
            }

            IconButton(
                modifier = Modifier.size(25.dp),
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    Color(
                        0XFFFC9824
                    )
                )
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Icon Add",
                    tint = Color(0XFFFFFFFF)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchMenuCardPreview() {
    SearchMenuCard(
        menu = Menu(
            name = "Ayam Goreng",
            canteenName = "Kantin Bu Henny",
            price = 12000.0
        ),
        onAddToFavorite = {}
    )
}

@Composable
fun RecommendationCard(modifier: Modifier = Modifier, recommendation: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(percent = 50))
            .background(Color(0xFFFAF0E6))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFF2D398)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color(0xFF4B4544)
            )
        }
        Text(recommendation, fontSize = 20.sp)
    }
}

@Preview
@Composable
private fun RecomendationCardPreview() {
    RecommendationCard(recommendation = "Ayam")
}