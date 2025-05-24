package com.example.eatzy_buyer.ui.screen.myOrder


import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
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
import com.example.eatzy_buyer.data.model.OrderItem
import com.example.eatzy_buyer.data.model.OrderStatus
import com.example.eatzy_buyer.token
import com.example.eatzy_buyer.ui.components.BottomNavBar
import com.example.eatzy_buyer.ui.components.TopBar
import com.example.eatzy_buyer.ui.screen.test.MyOrderViewModel
import com.example.eatzy_buyer.ui.theme.EatzyOrange
import com.example.eatzy_buyer.ui.theme.HeadingGray
import com.example.eatzy_buyer.ui.theme.HeadingLightGray
import java.text.NumberFormat
import java.util.Locale

private val exampleOrders = Order(
    id = 1,
    buyerId = 1,
    canteen = Canteen(
        id = 1,
        name = "Kantin Pak Muklis"
    ),
    status = OrderStatus.PROCESSING,
    orderTime = "2025-05-06 14:23:45",
    finishedTime = "2025-05-06 14:23:45",
    scheduleTime = "2025-05-06 14:23:45",
    estimationTime = 5,
    totalPrice = 12000.0,
    orderItem = listOf(
        OrderItem(
            id = 1,
            orderId = 1,
            details = "Digoreng sampe matang banget tolong jangan sampai ada yang mentah sedikitpun",
            menu = Menu(
                id = 1,
                name = "Ayam mbakar wong soloz Ayam mbakar wong soloz",
                preparationTime = 4,
                imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                addOnCategoryId = emptyList(),
                price = 5000.00
            ),
            addOns = listOf(
                AddOn(
                    id = 1,
                    menuId = 1,
                    name = "Mendoan",
                    price = 12000.00
                ),
                AddOn(
                    id = 2,
                    menuId = 1,
                    name = "Cabe",
                    price = 12000.00
                ),
            )
        ),
        OrderItem(
            id = 2,
            orderId = 1,
            details = "Digoreng tidak usah matang",
            menu = Menu(
                id = 2,
                name = "Pecel lele",
                preparationTime = 7,
                imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                addOnCategoryId = emptyList(),
                price = 5000.00
            ),
            addOns = listOf(
                AddOn(
                    id = 1,
                    menuId = 2,
                    name = "Mendoan",
                    price = 12000.00
                ),
                AddOn(
                    id = 2,
                    menuId = 2,
                    name = "Cabe",
                    price = 12000.00
                ),
            )
        ),
        OrderItem(
            id = 2,
            orderId = 1,
            details = "Digoreng tidak usah matang",
            menu = Menu(
                id = 2,
                name = "Pecel lele",
                preparationTime = 7,
                imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                addOnCategoryId = emptyList(),
                price = 5000.00
            ),
            addOns = listOf(
                AddOn(
                    id = 1,
                    menuId = 2,
                    name = "Mendoan",
                    price = 12000.00
                ),
                AddOn(
                    id = 2,
                    menuId = 2,
                    name = "Cabe",
                    price = 12000.00
                ),
            )
        ),
        OrderItem(
            id = 2,
            orderId = 1,
            details = "Digoreng tidak usah matang",
            menu = Menu(
                id = 2,
                name = "Pecel lele",
                preparationTime = 7,
                imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                addOnCategoryId = emptyList(),
                price = 5000.00
            ),
            addOns = listOf(
                AddOn(
                    id = 1,
                    menuId = 2,
                    name = "Mendoan",
                    price = 12000.00
                ),
                AddOn(
                    id = 2,
                    menuId = 2,
                    name = "Cabe",
                    price = 12000.00
                ),
            )
        ),
        OrderItem(
            id = 2,
            orderId = 1,
            details = "Digoreng tidak usah matang",
            menu = Menu(
                id = 2,
                name = "Pecel lele",
                preparationTime = 7,
                imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                addOnCategoryId = emptyList(),
                price = 5000.00
            ),
            addOns = listOf(
                AddOn(
                    id = 1,
                    menuId = 2,
                    name = "Mendoan",
                    price = 12000.00
                ),
                AddOn(
                    id = 2,
                    menuId = 2,
                    name = "Cabe",
                    price = 12000.00
                ),
            )
        ),
        OrderItem(
            id = 2,
            orderId = 1,
            details = "Digoreng tidak usah matang",
            menu = Menu(
                id = 2,
                name = "Pecel lele",
                preparationTime = 7,
                imageUrl = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                addOnCategoryId = emptyList(),
                price = 5000.00
            ),
            addOns = listOf(
                AddOn(
                    id = 1,
                    menuId = 2,
                    name = "Mendoan",
                    price = 12000.00
                ),
                AddOn(
                    id = 2,
                    menuId = 2,
                    name = "Cabe",
                    price = 12000.00
                ),
            )
        ),
    )
)

//private val order = exampleOrders;

@Composable
fun MyOrderScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    orderId: Int,
    onNavigateup: () -> Unit
) {

    val vm: MyOrderViewModel = viewModel()
    val order by vm.order.collectAsStateWithLifecycle(Order())

    LaunchedEffect(Unit) {
        vm.fetchOrdersByIdResponse(token = token, orderId = orderId)
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        topBar = {
            TopBar(
                modifier = Modifier,
                title = "Pesanan Saya",
                onNavigateUp = onNavigateup
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
//                .fillMaxSize()
        ) {

            MyOrderHeading(order = order)
//            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Text(
                        text = order.canteen.name,
//                    fontWeight = FontWeight.Medium,
                        color = HeadingLightGray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Rincian Menu",
                        fontWeight = FontWeight.Bold,
                        color = HeadingGray,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OrderCard(order = order)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }
}

@Composable
fun OrderCard(modifier: Modifier = Modifier, order: Order) {
    ElevatedCard(
//        modifier = Modifier
//            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))

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
                MyOrderItem(representativeItem, quantity)
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Total: " + NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                .format(order.totalPrice),
            fontWeight = FontWeight.Medium,
            color = HeadingGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun MyOrderHeading(
    order: Order
) {
    val statusMessage = when (order.status) {
        OrderStatus.CANCELED -> "Pesanan Dibatalkan Oleh Penjual"
        OrderStatus.WAITING -> "Menunggu Konfirmasi Penjual"
        OrderStatus.PROCESSING -> "Pesanan Diproses"
        OrderStatus.FINISHED -> "Pesanan Selesai"
        else -> "Order status tidak diketahui"
    }

    val statusProgress = when (order.status) {
        OrderStatus.WAITING -> 1
        OrderStatus.PROCESSING -> 2
        OrderStatus.FINISHED -> 3
        else -> 0
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = statusMessage,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = HeadingGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Estimasi selesai: " + order.estimationTime + " - " + (order.estimationTime + 10) + " menit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = HeadingGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Filled.ReceiptLong,
                contentDescription = "Order",
                tint = if (statusProgress >= 1) EatzyOrange else Color.Gray
            )
            if (statusProgress == 1) {
                LinearProgressIndicator(
                    modifier = Modifier.weight(1f),
                    color = EatzyOrange,
                    trackColor = Color.Gray,
                )
            } else {
                LinearProgressIndicator(
                    progress = if (statusProgress > 1) 1.0f else 0.0f,
                    modifier = Modifier.weight(1f),
                    color = EatzyOrange,
                    trackColor = Color.Gray,
                )
            }
            Icon(
                modifier = Modifier.padding(end = 16.dp, start = 16.dp),
                imageVector = Icons.Filled.SoupKitchen,
                contentDescription = "Order",
                tint = if (statusProgress >= 2) EatzyOrange else Color.Gray
            )
            if (statusProgress == 2) {
                LinearProgressIndicator(
                    modifier = Modifier.weight(1f),
                    color = EatzyOrange,
                    trackColor = Color.Gray,
                )
            } else {
                LinearProgressIndicator(
                    progress = if (statusProgress > 2) 1.0f else 0.0f,
                    modifier = Modifier.weight(1f),
                    color = EatzyOrange,
                    trackColor = Color.Gray,
                )
            }
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Order",
                tint = if (statusProgress >= 3) EatzyOrange else Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyOrderItem(orderItem: OrderItem, quantity: Int) {
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
                            lineHeight = 1.sp
                        )
                    }
                    Text(
                        text = orderItem.menu.name,
                        fontSize = 16.sp,
                        lineHeight = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = HeadingGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.width(4.dp))

                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                        .format(orderItem.menu.price),
                    fontSize = 16.sp,
                    color = HeadingGray
                )
            }
            Column(
//                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = orderItem.addOns.joinToString(", ") { it.name },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = HeadingLightGray,
                    maxLines = 2,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyOrderPreview() {
    MyOrderScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        onNavigateup = {},
        orderId = 1,
    )
}