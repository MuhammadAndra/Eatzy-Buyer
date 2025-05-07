package com.example.eatzy_buyer.ui.screen.listMenuScreen

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.model.getCanteenById
import com.example.eatzy_buyer.data.model.getMenuCategoriesForCanteen
import com.example.eatzy_buyer.ui.components.BottomNavBar
import com.example.eatzy_buyer.ui.components.TopBarSearch


@Composable
fun ListMenuScreen(
    navController: NavController,
    onNavigateUp: () -> Unit,
    canteenId: Int,
    onNavigateToAddMenu: (idCategoryMenu: Int, idMenu: Int) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    //disini bakal ada pengambilan objek canteen + pengambilan list menu canteen dari viewmodel
    //bawah ini dummy
    val canteen = getCanteenById(canteenId)
    val categoryMenuList = getMenuCategoriesForCanteen(canteen)

    //nanti ambil fungsi dari ViewModel
    fun onAddToFavorite() {}

    Scaffold(
        topBar = {
            TopBarSearch(
                title = canteen.name,
                onNavigateUp = onNavigateUp,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                isSearching = isSearching,
                onToggleSearch = {
                    isSearching = !isSearching
                    if (!isSearching) searchQuery = ""
                }
            )
        },
//        bottomBar = {
//            BottomNavBar(navController = navController)
//        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                categoryMenuList.forEach { categoryMenu ->
                    item {
                        MenuCategoryCard(category = categoryMenu) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                categoryMenu.menus.forEach { menu ->
                                    MenuCard(
                                        modifier = Modifier.clickable {
                                            onNavigateToAddMenu(
                                                categoryMenu.id,
                                                menu.id
                                            )
                                        },
                                        menu = menu,
                                        onNavigateToAddMenu = {
                                            onNavigateToAddMenu(
                                                categoryMenu.id,
                                                menu.id
                                            )
                                        },
                                        onAddToFavorite = { onAddToFavorite() }
                                    )
                                    if (menu != categoryMenu.menus.last()) {
                                        HorizontalDivider(
                                            thickness = 0.2.dp,
                                            color = Color(0XFF000000)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ListMenuScreenPreview() {
    ListMenuScreen(
        navController = rememberNavController(),
        onNavigateUp = {},
        canteenId = 0,
        onNavigateToAddMenu = { idCategoryMenu: Int, idMenu: Int -> }
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    menu: Menu,
    onNavigateToAddMenu: () -> Unit,
    onAddToFavorite: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = menu.url,
                contentDescription = "Gambar ${menu.name}"
            )
            Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {
                Text(
                    text = menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "Rp${menu.price}",
                    fontSize = 14.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.size(25.dp),
                onClick = onAddToFavorite,
                colors = IconButtonDefaults.iconButtonColors(Color(0XFFFC9824))
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Icon Favorite",
                    tint = Color(0XFFFFFFFF)
                )
            }
            IconButton(
                modifier = Modifier.size(25.dp),
                onClick = onNavigateToAddMenu,
                colors = IconButtonDefaults.iconButtonColors(Color(0XFFFC9824))
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
private fun MenuCardPreview() {
    MenuCard(
        menu = Menu(
            id = 0,
            name = "Ayam Ungkep",
            url = "https://kecipir.id/cdn/shop/files/Pecelayamgoreng.jpg?v=1712221576",
            price = 13000.0,
            status = true,
            addOnCategoryId = listOf(0, 1, 2)
        ),
        onNavigateToAddMenu = {},
        onAddToFavorite = {}
    )
}

@Composable
fun MenuCategoryCard(
    modifier: Modifier = Modifier,
    category: MenuCategory,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .background(Color(0XFFFFFFFF))
            .fillMaxWidth(),

        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(modifier = Modifier.padding(13.dp)) {
            Text(text = category.name, fontSize = 12.sp)
            content()
//            LazyColumn {
//                items(category.menus) { menu ->
//                    MenuCard(
//                        menu = menu,
//                        onAddToFavorite = {},
//                        onNavigateToAddMenu = {}
//                    )
//                    if (menu != category.menus[category.menus.size - 1]) {
//                        HorizontalDivider(
//                            thickness = 0.2.dp,
//                            color = Color(0XFF000000)
//                        )
//                    }
//
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryMenuCardPreview() {
    MenuCategoryCard(
        category = MenuCategory(
            id = 0,
            name = "Pilihan Crispy",
            menus = listOf(
                Menu(
                    id = 0,
                    name = "Ayam Crispy",
                    price = 13000.0,
                    status = true,
                    url = "https://img-global.cpcdn.com/recipes/3bff3fa797ed6480/400x400cq70/photo.jpg",
                    addOnCategoryId = listOf(0, 1, 2)
                ),
                Menu(
                    id = 1,
                    name = "Ayam Crispy",
                    price = 13000.0,
                    status = true,
                    url = "https://img-global.cpcdn.com/recipes/3bff3fa797ed6480/400x400cq70/photo.jpg",
                    addOnCategoryId = listOf(0, 1, 2)
                ),
                Menu(
                    id = 2,
                    name = "Ayam Crispy",
                    price = 13000.0,
                    status = true,
                    url = "https://img-global.cpcdn.com/recipes/3bff3fa797ed6480/400x400cq70/photo.jpg",
                    addOnCategoryId = listOf(0, 1, 2)
                )
            )
        )
    ) {}

}