package com.example.eatzy_buyer.ui.screen.cart


import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.eatzy_buyer.data.model.Cart
import com.example.eatzy_buyer.ui.components.BottomNavBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = viewModel(),
    onCheckoutClick: (Cart) -> Unit
) {
    // Observe carts from ViewModel
    val carts by viewModel.carts.collectAsState()


    // Fetch carts from API once when this screen is launched
    LaunchedEffect(Unit) {
        viewModel.fetchCartsFromApi()
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavBar(navController) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Keranjang Saya",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF455E84)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(carts) { cart ->
                    CartCard(
                        cart = cart,
                        onCheckoutClick = {
                            onCheckoutClick(cart)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartCard(
    cart: Cart,
    onCheckoutClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = "Kantin Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp),
                    tint = Color(0xFFFC9824)
                )
                Text(
                    text = cart.canteen_name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            cart.items.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GlideImage(
                        model = item.menu_image,
                        contentDescription = "Item Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFC9824)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${item.quantity}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = item.menu_name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (item.addons.isNotEmpty()) {
                            Text(
                                text = item.addons.joinToString(separator = ", "),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        if (!item.note.isNullOrEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Assignment,
                                    contentDescription = "Note Icon",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(end = 4.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = item.note,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        Text(
                            formatRupiah(item.menu_price.toDouble()),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total: ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = formatRupiah(cart.total_price),
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = onCheckoutClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC9824))
                ) {
                    Text("Checkout")
                }
            }
        }
    }
}

fun formatRupiah(amount: Double): String {
    val formatter = NumberFormat.getNumberInstance(java.util.Locale("in", "ID"))
    return "Rp ${formatter.format(amount)}"
}

@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    val navController = rememberNavController()
    CartScreen(navController = navController, onCheckoutClick = {
    })
}
