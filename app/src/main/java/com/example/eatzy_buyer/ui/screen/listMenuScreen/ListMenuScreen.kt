package com.example.eatzy_buyer.ui.screen.listMenuScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.eatzy_buyer.data.model.AddOn
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.MenuCategory
import com.example.eatzy_buyer.data.model.OrderItem
import com.example.eatzy_buyer.token
import com.example.eatzy_buyer.ui.components.TopBarSearch
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMenuScreen(
    navController: NavController,
    onNavigateUp: () -> Unit,
    canteenId: Int,
    onNavigateToAddMenu: (idCategoryMenu: Int?, menuId: Int, canteenId: Int, orderId: Int?, orderItemId: Int?, count: Int?, orderItemIds: List<Int>?) -> Unit,
    onNavigateToCart: (orderId:Int) -> Unit,
) {
    val vm: ListMenuViewModel = viewModel()
    val menuCategories by vm.menuCategories.collectAsStateWithLifecycle()
    val canteen by vm.canteen.collectAsStateWithLifecycle()
    val order by vm.order.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var menuIdForBottomSheet by remember { mutableIntStateOf(0) }

    val toastMessage by vm.toastMessage.observeAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
//        vm.fetchUsers()
        vm.fetchMenuCategories(id = canteenId)
        vm.getCanteenById(id = canteenId)
        vm.getOrderByCanteenId(token = token, canteenId = canteenId)
    }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            vm.clearToastMessage()
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    //disini bakal ada pengambilan objek canteen + pengambilan list menu canteen dari viewmodel
    //bawah ini dummy
//    val canteen = getCanteenById(canteenId)
//    val categoryMenuList = getMenuCategoriesForCanteen(canteen)
//    val order = getUncheckoutOrderByCanteenId(canteenId = canteenId)


    //nanti ambil fungsi dari ViewModel
    fun onAddToFavorite(id:Int) {
        vm.createFavorite(
            token = token,
            id = id
        )
    }

    Scaffold(
        topBar = {
            canteen?.let {
                TopBarSearch(
                    title = it.name,
                    onNavigateUp = onNavigateUp,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    isSearching = isSearching,
                    onToggleSearch = {
                        isSearching = !isSearching
                        if (!isSearching) searchQuery = ""
                    }
                )
            }
        },
//        bottomBar = {
//            BottomNavBar(navController = navController)
//        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = if (order != null) 70.dp else 0.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (searchQuery.isNotBlank()) {
                    val filteredMenus = menuCategories
                        .flatMap { it.menus }
                        .filter { menu ->
                            menu.name.contains(searchQuery, ignoreCase = true)
                        }
                    items(filteredMenus) { menu ->
                        if (menu.isAvailable) {
                            val count =
                                order?.orderItem?.count { it.menuId == menu.id }
                                    ?: 0
                            ElevatedCard(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .background(Color(0XFFFFFFFF))
                                    .fillMaxWidth(),

                                elevation = CardDefaults.cardElevation(3.dp)
                            ) {
                                MenuCard(
                                    modifier = Modifier
                                        .clickable {
                                            if (count != 0) {
                                                showBottomSheet = true
                                                menuIdForBottomSheet = menu.id
                                            } else {
                                                onNavigateToAddMenu(
                                                    null, // karena tidak tahu dari kategori mana
                                                    menu.id,
                                                    canteenId,
                                                    order?.id,
                                                    null,
                                                    null,
                                                    listOf()
                                                )
                                            }
                                        }
                                        .padding(13.dp),
                                    menu = menu,
                                    onNavigateToAddMenu = {
                                        onNavigateToAddMenu(
                                            null,
                                            menu.id,
                                            canteenId,
                                            order?.id,
                                            null,
                                            null,
                                            listOf()
                                        )
                                    },
                                    onAddToFavorite = {
                                        onAddToFavorite(menu.id)

                                    },
                                    count = if (count > 0) count else null,
                                    onIncrement = { showBottomSheet = true },
                                    onDecrement = { }
                                )
                            }
                        }
                    }
                } else {
                    menuCategories.forEach { categoryMenu ->
                        if (categoryMenu.   menus.first().isAvailable) {
                            item {
                                MenuCategoryCard(category = categoryMenu) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        categoryMenu.menus.forEach { menu ->
                                            if (menu.isAvailable) {
                                                var count by remember {
                                                    mutableIntStateOf(
                                                        0
                                                    )
                                                }
                                                if (order != null) {
                                                    count =
                                                        order!!.orderItem.count { orderItem ->
                                                            orderItem.menuId == menu.id
                                                        }
                                                }
                                                MenuCard(
                                                    modifier = Modifier
                                                        .clickable {
                                                            if (count != 0) {
                                                                showBottomSheet = true
                                                                menuIdForBottomSheet =
                                                                    menu.id
                                                            } else {
                                                                onNavigateToAddMenu(
                                                                    categoryMenu.id,
                                                                    menu.id,
                                                                    canteenId,
                                                                    order?.id,
                                                                    null,
                                                                    null,
                                                                    listOf()
                                                                )
                                                            }
                                                        }
                                                        .padding(horizontal = 13.dp)
                                                        .padding(bottom = if (menu == categoryMenu.menus.last()) 13.dp else 0.dp),
                                                    menu = menu,
                                                    onNavigateToAddMenu = {
                                                        onNavigateToAddMenu(
                                                            categoryMenu.id,
                                                            menu.id,
                                                            canteenId,
                                                            order?.id,
                                                            null,
                                                            null,
                                                            listOf()
                                                        )
                                                    },
                                                    onAddToFavorite = { onAddToFavorite(menu.id) },
                                                    count = if (count > 0) count else null,
                                                    onIncrement = {
                                                        showBottomSheet = true
                                                    },
                                                    onDecrement = { if (count > 1) count-- }
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
                        }
                    }
                }

                item {}
            }
            if (order != null && order!!.orderItem.isNotEmpty()) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0XFFFFFFFF))
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp
                            )
                        )
                        .border(
                            1.dp,
                            Color(0xffFC9824),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp
                            )
                        ) // Outline-nya di sini
                        .align(Alignment.BottomCenter),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    GoToCartButton(
                        quantity = order!!.orderItem.size,
                        totalPrice = order!!.totalPrice,
                        onNavigateToCart = { onNavigateToCart(order!!.id) }
                    )
                }
            }

        }
        if (showBottomSheet && order != null) {
            val matchingItems =
                order!!.orderItem.filter { it.menuId == menuIdForBottomSheet }

            OrderModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    menuIdForBottomSheet = 0
                },
                sheetState = sheetState,
                orderItems = matchingItems,
                canteenId = canteenId,
                onClick = {
                    onNavigateToAddMenu(
                        0,
                        menuIdForBottomSheet,
                        canteenId,
                        order?.id,
                        null,
                        null,
                        listOf()
                    )
                    showBottomSheet = false
                },
                onNavigateToAddMenu = { categoryMenuId, menuId, canteenId, orderId, orderItemId, count, orderItemIds ->
                    onNavigateToAddMenu(
                        categoryMenuId,
                        menuId,
                        canteenId,
                        orderId,
                        orderItemId,
                        count,
                        orderItemIds
                    )
                    showBottomSheet = false
                },
                onDeleteOrderItem = { orderItemIds ->
                    Log.d("tes delete", orderItemIds.toString())
                    vm.deleteOrderItemByIds(
                        token = token,
                        orderItemIds = orderItemIds,
                        canteenId = canteenId
                    )
                    if (matchingItems.size == 1) {
                        showBottomSheet = false
                    }
                }
            )
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
        onNavigateToAddMenu = { categoryMenuId: Int?, menuId: Int, canteenId: Int, orderId: Int?, orderItemId: Int?, count, orderItemIds -> },
        onNavigateToCart = {}
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    menu: Menu,
    onNavigateToAddMenu: () -> Unit,
    onAddToFavorite: () -> Unit,
    count: Int? = null,
    onIncrement: (() -> Unit)? = null,
    onDecrement: (() -> Unit)? = null
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
//            val imageUrl = remember { menu.imageUrl }

//            GlideImage(
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(RoundedCornerShape(10.dp)),
//                model = imageUrl,
//                contentScale = ContentScale.Crop,
//                contentDescription = "Gambar ${menu.name}",
//                loading = placeholder {
//                    Box(
//                        modifier = Modifier
//                            .background(Color.White)
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(color = Color(0XFFFC9824))
//                    }
//                },
//            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menu.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Gambar Menu",
                contentScale = ContentScale.Crop,
//                loading = {
//                    Box(
//                        modifier = Modifier
//                            .background(Color.White)
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(color = Color(0XFFFC9824))
//                    }
//                },
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {
                Text(
                    text = menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = NumberFormat
                        .getCurrencyInstance(Locale("in", "ID"))
                        .format(menu.price),
                    fontSize = 14.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
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
            if (count != null && onIncrement != null && onDecrement != null) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .border(1.dp, Color(0xFFFC9824), CircleShape)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$count",
                        color = Color(0xFFFC9824),
                        fontSize = 12.sp
                    )
                }
            } else {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = onNavigateToAddMenu,
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
}

@Preview(showBackground = true)
@Composable
private fun MenuCardPreview() {
    MenuCard(
        menu = Menu(
            id = 0,
            name = "Ayam Ungkep",
            imageUrl = "https://kecipir.id/cdn/shop/files/Pecelayamgoreng.jpg?v=1712221576",
            price = 13000.0,
            isAvailable = true,
            addOnCategoryId = listOf(0, 1, 2),
            preparationTime = 15
        ),
        onNavigateToAddMenu = {},
        onAddToFavorite = {},
        count = 1,
        onIncrement = {},
        onDecrement = {}
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
        Column(
//            modifier = Modifier.padding(13.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 13.dp, top = 7.dp),
                text = category.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            content()
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
                    isAvailable = true,
                    imageUrl = "https://img-global.cpcdn.com/recipes/3bff3fa797ed6480/400x400cq70/photo.jpg",
                    addOnCategoryId = listOf(0, 1, 2),
                    preparationTime = 15

                ),
                Menu(
                    id = 1,
                    name = "Ayam Crispy",
                    price = 13000.0,
                    isAvailable = true,
                    imageUrl = "https://img-global.cpcdn.com/recipes/3bff3fa797ed6480/400x400cq70/photo.jpg",
                    addOnCategoryId = listOf(0, 1, 2),
                    preparationTime = 15

                ),
                Menu(
                    id = 2,
                    name = "Ayam Crispy",
                    price = 13000.0,
                    isAvailable = true,
                    imageUrl = "https://img-global.cpcdn.com/recipes/3bff3fa797ed6480/400x400cq70/photo.jpg",
                    addOnCategoryId = listOf(0, 1, 2),
                    preparationTime = 15

                )
            )
        )
    ) {}

}

@Composable
fun GoToCartButton(
    modifier: Modifier = Modifier,
    onNavigateToCart: () -> Unit,
    quantity: Int,
    totalPrice: Double
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Filled.ShoppingBasket,
            contentDescription = "Shopping Basket Icon",
            tint = Color(0XFFFC9824)
        )
        Text(
            text = "$quantity Menu Pesanan",
            fontSize = 13.sp,
            color = Color(0XFF675E5E)
        )
        Button(
            onClick = onNavigateToCart,
            colors = ButtonDefaults.buttonColors(Color(0XFFFC9824))
        ) {
            Text(
                NumberFormat.getCurrencyInstance(
                    Locale(
                        "in",
                        "ID"
                    )
                ).format(totalPrice)
            )
        }
    }
}

@Preview
@Composable
private fun GoToCartButtonPreview() {
    GoToCartButton(onNavigateToCart = {}, quantity = 1, totalPrice = 12000.0)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    orderItems: List<OrderItem>,
    canteenId: Int,
    onClick: () -> Unit,
    onNavigateToAddMenu: (categoryMenuId: Int?, menuId: Int, canteenId: Int, orderId: Int?, orderItemId: Int?, count: Int?, orderItemIds: List<Int>?) -> Unit,
    onDeleteOrderItem: (orderItemIds: List<Int>) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        val groupedOrderItems = remember(orderItems) {
            orderItems.groupBy { it.menuId to it.details to it.addOns.sortedBy { addOn -> addOn.id } }
        }
        LazyColumn {
            groupedOrderItems.forEach { (key, itemsWithSameKey) ->
                val orderItemRepresentative = itemsWithSameKey.first()
                val orderItemIds = itemsWithSameKey.map { it.id }
                val count = itemsWithSameKey.size
                item {
                    OrderItemCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        orderItem = orderItemRepresentative,
                        count = count,
                        onDeleteOrderItem = { onDeleteOrderItem(orderItemIds) }
                    )
                }
            }
        }
        ElevatedButton(
            onClick = onClick,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 18.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.elevatedButtonColors(
                Color(
                    0xFFFC9824
                )
            )
        ) {
            Text(
                text = "Tambah Lagi",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun OrderModalBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    OrderModalBottomSheet(
        onDismissRequest = { showBottomSheet = false },
        sheetState = sheetState,
        orderItems = listOf(

        ),
        onClick = {},
        onNavigateToAddMenu = { categoryMenuId, menuId, canteenId, orderId, orderItemId, count, orderItemIds -> },
        canteenId = 1,
        onDeleteOrderItem = {}
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrderItemCard(
    modifier: Modifier = Modifier,
    orderItem: OrderItem,
    count: Int?,
    onDeleteOrderItem: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp),
            model = orderItem.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "Gambar ${orderItem.menuName}",
//            loading = placeholder {
//                Box(
//                    modifier = Modifier
//                        .background(Color.White)
//                        .fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = Color(0XFFFC9824))
//                }
//            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = orderItem.menuName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = orderItem.addOns.joinToString(", ") { it.name },
                fontSize = 10.sp,
            )
        }

        Box(
            modifier = Modifier
                .size(25.dp)
                .border(1.dp, Color(0xFFFC9824), CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$count", color = Color(0xFFFC9824), fontSize = 12.sp)
        }
//        IconButton(
//            modifier = Modifier
//                .border(
//                    width = 1.dp,
//                    color = Color(0xFFFC9824),
//                    shape = CircleShape
//                )
//                .size(25.dp),
//            onClick = {},
//            colors = IconButtonDefaults.iconButtonColors(
//                Color(0XFFFFFFFF)
//            )
//        ) {
//            Text(text = "$count", color = Color(0xFFFC9824))
//        }

        IconButton(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFFC9824),
                    shape = CircleShape
                )
                .size(25.dp),
            onClick = onDeleteOrderItem,
            colors = IconButtonDefaults.iconButtonColors(
                Color(0XFFFFFFFF)
            )
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Filled.Delete,
                contentDescription = "Icon Favorite",
                tint = Color(0xFFFC9824)
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun OrderItemCardPreview() {
    OrderItemCard(
        orderItem = OrderItem(
            id = 1,
            orderId = 1,
            menuId = 1,
            menu = Menu(name = "Ayam Crispy"),
            addOns = listOf(
                AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi"), AddOn(name = "Sambal Matah"),
                AddOn(name = "Telur Dadar"),
                AddOn(name = "Nasi")
            ),
            details = null
        ),
        count = 1,
        onDeleteOrderItem = {}
    )

}