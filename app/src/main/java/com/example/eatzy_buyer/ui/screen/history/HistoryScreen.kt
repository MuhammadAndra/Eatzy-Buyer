package com.example.eatzy_buyer.ui.screen.history


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.eatzy_buyer.data.model.AddOn
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.model.OrderBadge
import com.example.eatzy_buyer.data.model.OrderItem
import com.example.eatzy_buyer.data.model.OrderStatus
import com.example.eatzy_buyer.token
import com.example.eatzy_buyer.ui.components.BottomNavBar
import com.example.eatzy_buyer.ui.components.TopBar
import com.example.eatzy_buyer.ui.screen.myOrder.MyOrderItem
import com.example.eatzy_buyer.ui.screen.test.HistoryViewModel
import com.example.eatzy_buyer.ui.screen.test.MyOrderViewModel
import com.example.eatzy_buyer.ui.theme.EatzyOrange
import com.example.eatzy_buyer.ui.theme.HeadingGray
import com.example.eatzy_buyer.ui.theme.HeadingLightGray
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

//private val exampleOrders = listOf(
//    Order(
//        id = 1,
//        buyerId = 1,
//        canteen = Canteen(
//            id = 1,
//            name = "Kantin Pak Muklis"
//        ),
//        status = OrderStatus.FINISHED,
//        orderTime = "2025-05-06 14:23:45",
//        finishedTime = "2025-05-06 14:23:45",
//        scheduleTime = "2025-05-06 14:23:45",
//        estimationTime = 5,
//        totalPrice = 12000.0,
//        orderItem = listOf(
//            OrderItem(
//                id = 2,
//                orderId = 1,
//                details = "Digoreng tidak usah matang",
//                menu = Menu(
//                    id = 2,
//                    name = "Pecel lele",
//                    preparationTime = 7,
//                    imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
//                    isAvailable = true,
//                    addOnCategoryId = emptyList(),
//                    price = 5000.00
//                ),
//                addOns = listOf(
//                    AddOn(
//                        id = 1,
//                        menuId = 2,
//                        name = "Mendoan",
//                        price = 12000.00
//                    ),
//                    AddOn(
//                        id = 2,
//                        menuId = 2,
//                        name = "Cabe",
//                        price = 12000.00
//                    ),
//                )
//            ),
//        )
//    ),
//    Order(
//        id = 1,
//        buyerId = 1,
//        canteen = Canteen(
//            id = 1,
//            name = "Kantin Pak Muklis"
//        ),
//        status = OrderStatus.PROCESSING,
//        orderTime = "2025-05-06 14:23:45",
//        finishedTime = "2025-05-06 14:23:45",
//        scheduleTime = "2025-05-06 14:23:45",
//        estimationTime = 5,
//        totalPrice = 12000.0,
//        orderItem = listOf(
//            OrderItem(
//                id = 1,
//                orderId = 1,
//                details = "Digoreng sampe matang banget tolong jangan sampai ada yang mentah sedikitpun",
//                menu = Menu(
//                    id = 1,
//                    name = "Ayam mbakar wong soloz Ayam mbakar wong soloz",
//                    preparationTime = 4,
//                    imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
//                    isAvailable = true,
//                    addOnCategoryId = emptyList(),
//                    price = 5000.00
//                ),
//                addOns = listOf(
//                    AddOn(
//                        id = 1,
//                        menuId = 1,
//                        name = "Mendoan",
//                        price = 12000.00
//                    ),
//                    AddOn(
//                        id = 2,
//                        menuId = 1,
//                        name = "Cabe",
//                        price = 12000.00
//                    ),
//                )
//            ),
//            OrderItem(
//                id = 2,
//                orderId = 1,
//                details = "Digoreng tidak usah matang",
//                menu = Menu(
//                    id = 2,
//                    name = "Pecel lele",
//                    preparationTime = 7,
//                    imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
//                    isAvailable = true,
//                    addOnCategoryId = emptyList(),
//                    price = 5000.00
//                ),
//                addOns = listOf(
//                    AddOn(
//                        id = 1,
//                        menuId = 2,
//                        name = "Mendoan",
//                        price = 12000.00
//                    ),
//                    AddOn(
//                        id = 2,
//                        menuId = 2,
//                        name = "Cabe",
//                        price = 12000.00
//                    ),
//                )
//            ),
//        )
//    ),
//    Order(
//        id = 1,
//        buyerId = 1,
//        canteen = Canteen(
//            id = 1,
//            name = "Kantin Pak Muklis"
//        ),
//        status = OrderStatus.FINISHED,
//        orderTime = "2025-05-06 14:23:45",
//        finishedTime = "2025-05-06 14:23:45",
//        scheduleTime = "2025-05-06 14:23:45",
//        estimationTime = 5,
//        totalPrice = 12000.0,
//        orderItem = listOf(
//            OrderItem(
//                id = 2,
//                orderId = 1,
//                details = "Digoreng tidak usah matang",
//                menu = Menu(
//                    id = 2,
//                    name = "Pecel lele",
//                    preparationTime = 7,
//                    imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
//                    isAvailable = true,
//                    addOnCategoryId = emptyList(),
//                    price = 5000.00
//                ),
//                addOns = listOf(
//                    AddOn(
//                        id = 1,
//                        menuId = 2,
//                        name = "Mendoan",
//                        price = 12000.00
//                    ),
//                    AddOn(
//                        id = 2,
//                        menuId = 2,
//                        name = "Cabe",
//                        price = 12000.00
//                    ),
//                )
//            ),
//        )
//    ),
//
//    )
//
//private val orders = exampleOrders;


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNavigateToMyOrder: (Int) -> Unit
) {

    val vm: HistoryViewModel = viewModel()
    val orders by vm.orders.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) {
        vm.fetchOrdersByBuyerResponse(token = token)
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        topBar = { TopBar(
            modifier = Modifier,
            title = "History"
        ) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
//                Box(
//                    modifier = Modifier
//                        .matchParentSize()
//
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(color = Color(0xFFE5E5E5)),
//                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                    items(orders) { order ->
                        OrderHistory(onNavigateToMyOrder = onNavigateToMyOrder, order)
                    }
                    item {
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OrderHistory(
    onNavigateToMyOrder: (Int) -> Unit,
    order: Order
) {

    val orderBadge = when (order.status) {
        OrderStatus.CANCELED -> OrderBadge(
            text = "Dibatalkan",
            textColor = Color(0xFFfee2e2),
            backgroundColor = Color(0xFF991b1b),
        )
        OrderStatus.WAITING -> OrderBadge(
            text = "Menunggu Konfirmasi",
            textColor = Color(0xFF1f2937),
            backgroundColor = Color(0xFFf3f4f6),
        )
        OrderStatus.PROCESSING -> OrderBadge(
            text = "Diproses",
            textColor = Color(0xFF92400e),
            backgroundColor = Color(0xFFfef3c7),
        )
        OrderStatus.FINISHED -> OrderBadge(
            text = "Selesai",
            textColor = Color(0xFF065f46),
            backgroundColor = Color(0xFFd1fae5),
        )
        else -> OrderBadge(
            text = "Tidak diketahui",
            textColor = Color(0xFF1f2937),
            backgroundColor = Color(0xFFf3f4f6),
        )
    }

    val orderButtonText = when (order.status) {
        OrderStatus.CANCELED -> "Pesan Lagi"
        OrderStatus.WAITING -> "Lihat"
        OrderStatus.PROCESSING -> "Lihat"
        OrderStatus.FINISHED -> "Pesan Lagi"
        else -> "Pesan Lagi"
    }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column() {
                Text(
                    text = SimpleDateFormat(
                        "dd MMMM yyyy",
                        Locale("id", "ID")
                    ).format(
                        SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()
                        ).parse("2025-05-06 14:23:45")
                    ),
                    fontWeight = FontWeight.Medium,
                    lineHeight = 1.em,
                    color = HeadingGray,
                    fontSize = 13.sp
                )
                Text(
                    text = order.canteen.name,
                    fontWeight = FontWeight.Bold,
                    color = HeadingGray,
                    fontSize = 16.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Label(orderBadge.text, orderBadge.textColor, orderBadge.backgroundColor )
            }

        }

        Column {
            // Group items by a composite key (details + menu + addons)
            val groupedItems = order.orderItem.groupBy { item ->
                // Create a key combining all 3 conditions
                Triple(
                    item.details,
                    item.menu.id,  // Assuming Menu has an ID; adjust if needed
                    item.addOns.sortedBy { it.id }.joinToString(",") { it.id.toString() } // Sort addons for consistency
                )
            }.mapValues { it.value.size } // Count occurrences

            // Display each unique item with its quantity
            groupedItems.forEach { (key, quantity) ->
                val (details, menuId, addonsKey) = key
                // Find the first matching item (all in group have same properties)
                val representativeItem = order.orderItem.first { item ->
                    item.details == details &&
                            item.menu.id == menuId &&
                            item.addOns.sortedBy { it.id }.joinToString(",") { it.id.toString() } == addonsKey
                }
                HistoryItem(representativeItem, quantity)
            }
        }

//        order.orderItem.forEach { orderItem ->
//            HistoryItem(orderItem)
//        }
//                    Spacer(modifier = Modifier.height(8.dp))
//        Column(
//            modifier = Modifier.padding(top = 8.dp),
//            verticalArrangement = Arrangement.spacedBy(0.dp)
//        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = order.orderItem.count().toString() + " Menu",
                        lineHeight = 1.em,
                        color = HeadingGray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Total: " + NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                            .format(order.totalPrice),
                        fontWeight = FontWeight.Medium,
                        color = HeadingGray,
                        fontSize = 16.sp,
                    )
                }

                ElevatedButton(
                    onClick = { onNavigateToMyOrder(order.id) },
                    modifier = Modifier.height(28.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 3.dp,
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EatzyOrange,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = orderButtonText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
//        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Label(text: String, textColor: Color, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            lineHeight = 1.em,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HistoryItem(orderItem: OrderItem, quantity: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GlideImage(
            model = orderItem.menu.imageUrl,
            contentDescription = orderItem.menu.name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
            loading = placeholder({
                Box(
                    modifier = Modifier
                        .background(Color.LightGray)
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(20.dp)
                            .background(EatzyOrange, shape = CircleShape)
                    ) {
                        Text(
                            text = quantity.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 1.em,
                        )
                    }
                    Text(
                        text = orderItem.menu.name,
                        fontSize = 16.sp,
                        lineHeight = 1.em,
                        fontWeight = FontWeight.Medium,
                        color = HeadingGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.width(4.dp))

                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(orderItem.menu.price),
                    fontSize = 16.sp,
                    color = HeadingGray
                )
            }
            Column(
//                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = orderItem.addOns.joinToString(", ") { addon -> addon.name },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = HeadingLightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp),
                        imageVector = Icons.Filled.Assignment,
                        contentDescription = "Deskripsi",
                        tint = HeadingGray
                    )
                    Text(
                        text = orderItem.details,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = HeadingLightGray,
                        lineHeight = 1.em,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
//    OrderHistory(onNavigateToMyOrder = {})
    HistoryScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        onNavigateToMyOrder = {}
    )
}