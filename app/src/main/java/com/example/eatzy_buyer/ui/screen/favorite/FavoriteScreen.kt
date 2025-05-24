package com.example.eatzy_buyer.ui.screen.favorite

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.model.MenuFavorite
import com.example.eatzy_buyer.token
import com.example.eatzy_buyer.ui.components.BottomNavBar
import com.example.eatzy_buyer.ui.components.TopBarSearch
import com.example.eatzy_buyer.ui.screen.history.HistoryScreen
import com.example.eatzy_buyer.ui.screen.test.FavoriteViewModel
import com.example.eatzy_buyer.ui.screen.test.TestApiViewModel
import com.example.eatzy_buyer.ui.theme.EatzyBlue
import com.example.eatzy_buyer.ui.theme.EatzyCream
import com.example.eatzy_buyer.ui.theme.EatzyOrange
import com.example.eatzy_buyer.ui.theme.HeadingGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

val menusExample = listOf(
    Menu(
        id = 1,
        name = "Ayam mbakar wong soloz Ayam mbakar wong soloz",
        category = MenuCategory(
            id = 1,
            name = "Ayam",
            canteen = Canteen(
                id = 1,
                name = "Kantin Bu Ridok"
            )
        ),
        preparationTime = 4,
        imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
        isAvailable = true,
        addOnCategoryId = emptyList(),
        price = 5000.00
    ),
    Menu(
        id = 2,
        name = "Tempe kecap pak budi masak seger",
        category = MenuCategory(
            id = 1,
            name = "Ayam",
            canteen = Canteen(
                id = 2,
                name = "Kantin Pak Bagus"
            )
        ),
        preparationTime = 7,
        imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
        isAvailable = true,
        addOnCategoryId = emptyList(),
        price = 12000.00
    ),
    Menu(
        id = 1,
        name = "Sop Ayamz pak kremes enak banget",
        category = MenuCategory(
            id = 1,
            name = "Ayam",
            canteen = Canteen(
                id = 3,
                name = "Kantin Pak Bonang"
            )
        ),
        preparationTime = 8,
        imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
        isAvailable = true,
        addOnCategoryId = emptyList(),
        price = 8000.00
    ),
)

val menus = menusExample

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNavigateToOrder: () -> Unit,
) {

    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val vm: FavoriteViewModel = viewModel()
//    val users by vm.users.collectAsStateWithLifecycle()
    val favorites by vm.favorites.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) {
        vm.fetchFavoritesResponse(token = token)
//        Log.d("FavoriteScreen", "Favorites updated: ${favorites.size}")
//        favorites.forEach {
//            Log.d("FavoriteScreen", "Favorite item: $it")
//        }
    }



    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        topBar = {
            TopBarSearch(
                title = "Favorite",
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                isSearching = isSearching,
                onToggleSearch = { isSearching = !isSearching }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            items(favorites) { favorite ->
//                Text(text = "${favorite.name} - ${favorite.price}")
//            }
            item {
                Spacer(modifier = Modifier.height(1.dp))
            }
            items(favorites.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.category?.canteen?.name?.contains(
                            searchQuery,
                            ignoreCase = true
                        ) == true
            }
            ) { favorite ->
                FavoriteItemCard(
                    Modifier,
                    favorite,
                    onNavigateToOrder,
                    vm = vm,
                    coroutineScope = coroutineScope
                )
            }
            item {
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteItemCard(
    modifier: Modifier = Modifier,
    favorite: Menu,
    onNavigateToOrder: () -> Unit,
    vm: FavoriteViewModel,
    coroutineScope: CoroutineScope
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    if (showDeleteConfirmation) {
        DeleteConfirmation(
            onConfirmation = {
                showDeleteConfirmation = false // Close dialog
                coroutineScope.launch {
                    vm.deleteFavorite(token, favorite.id)
                }
            },
            onDismissRequest = { showDeleteConfirmation = false },
            dialogTitle = "Hapus ${favorite.name}?",
            dialogText = "Apakah anda yakin ingin menghapus menu ini dari favorit?",
            icon = Icons.Default.WarningAmber,
        )
    }

    ElevatedCard(
        modifier = Modifier,
        onClick = onNavigateToOrder,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlideImage(
                model = favorite.imageUrl,
                contentDescription = favorite.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                loading = placeholder({
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = EatzyOrange)
                    }
                })
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = favorite.name,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = HeadingGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = favorite.category!!.canteen.name,
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = HeadingGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                        .format(favorite.price),
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = HeadingGray,
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = { onNavigateToOrder },
                    colors = IconButtonDefaults.iconButtonColors(EatzyOrange)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Icon Favorite",
                        tint = Color(0XFFFFFFFF)
                    )
                }
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = { showDeleteConfirmation = true },
                    colors = IconButtonDefaults.iconButtonColors(Color(0XFFFC2433))
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Icon Favorite",
                        tint = Color(0XFFFFFFFF)
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmation(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Peringatan")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = "Hapus",
                    color = Color.Red
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Batal")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FavoritePreview() {
//    OrderHistory(onNavigateToMyOrder = {})
    FavoriteScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        onNavigateToOrder = {}
    )
}