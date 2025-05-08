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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.eatzy_buyer.data.model.Addon
import com.example.eatzy_buyer.data.model.Canteen
import com.example.eatzy_buyer.data.model.Menu
import com.example.eatzy_buyer.data.model.Order
import com.example.eatzy_buyer.data.model.OrderItem
import com.example.eatzy_buyer.ui.components.BottomNavBar
import java.text.NumberFormat
import java.util.Locale

val EatzyOrange = Color(0xFFFC9824)
val HeadingGray = Color(0xFF675E5E)
val HeadingLightGray = Color(0xFF8F8F8F)

private val exampleOrders = Order(
    id = 1,
    buyerId = 1,
    canteen = Canteen(
        id = 1,
        name = "Kantin Pak Muklis"
    ),
    orderStatus = "waiting",
    orderTime = "2025-05-06 14:23:45",
    orderFinishedTime = "2025-05-06 14:23:45",
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
                preparationTime = "14:23:45",
                menuImage = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                menuPrice = 5000.00
            ),
            addons = listOf(
                Addon(
                    id = 1,
                    menuId = 1,
                    addonName = "Mendoan",
                    addonPrice = 12000.00
                ),
                Addon(
                    id = 2,
                    menuId = 1,
                    addonName = "Cabe",
                    addonPrice = 12000.00
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
                preparationTime = "14:23:45",
                menuImage = "https://foodish-api.com/images/pizza/pizza46.jpg",
                isAvailable = true,
                menuPrice = 5000.00
            ),
            addons = listOf(
                Addon(
                    id = 1,
                    menuId = 2,
                    addonName = "Mendoan",
                    addonPrice = 12000.00
                ),
                Addon(
                    id = 2,
                    menuId = 2,
                    addonName = "Cabe",
                    addonPrice = 12000.00
                ),
            )
        ),
    )
)

private val orders = exampleOrders;

val statusMessage = when (orders.orderStatus) {
    "canceled" -> "Pesanan Dibatalkan Oleh Penjual"
    "waiting" -> "Menunggu Konfirmasi Penjual"
    "processing" -> "Pesanan Diproses"
    "finished" -> "Pesanan Selesai"
    else -> "Order status tidak diketahui"
}

val statusProgress = when (orders.orderStatus) {
    "waiting" -> 1
    "processing" -> 2
    "finished" -> 3
    else -> 0
}

@Composable
fun MyOrderScreen(modifier: Modifier = Modifier, navController: NavController) {

    Scaffold(bottomBar = { BottomNavBar(navController) }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            MyOrderHeading()
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = orders.canteen.name,
                    fontWeight = FontWeight.Medium,
                    color = HeadingLightGray
                )
                Text(
                    text = "Rincian Menu",
                    fontWeight = FontWeight.Bold,
                    color = HeadingGray
                )
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

                    orders.orderItem.forEach { orderItem ->
                        MyOrderItem(orderItem)
                    }
//                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(orders.totalPrice),
                        fontWeight = FontWeight.Medium,
                        color = HeadingGray,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun MyOrderHeading() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = statusMessage,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = HeadingGray
        )
        Text(
            text = "Estimasi selesai: " + orders.estimationTime + " - " + (orders.estimationTime + 5) + " menit",
            fontSize = 12.sp,
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
fun MyOrderItem(orderItem: OrderItem) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max).padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GlideImage(
            model = orderItem.menu.menuImage,
            contentDescription = "Burger",
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
            modifier = Modifier.weight(1f).fillMaxHeight(),
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
                            text = "1",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 1.sp
                        )
                    }
                    Text(
                        text = orderItem.menu.name,
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = HeadingGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Spacer(Modifier.width(4.dp))

                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(orderItem.menu.menuPrice),
                    fontSize = 13.sp,
                    color = HeadingGray
                )
            }
            Column(
//                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = orderItem.addons.joinToString(", ") { addon -> addon.addonName },
                    fontSize = 12.sp,
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
                        modifier = Modifier.size(20.dp).padding(end = 4.dp),
                        imageVector = Icons.Filled.Assignment,
                        contentDescription = "Description",
                        tint = HeadingGray
                    )
                    Text(
                        text = orderItem.details,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = HeadingLightGray,
                        lineHeight = 12.sp,
                        maxLines = 2,
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
        navController = rememberNavController()
    )
}