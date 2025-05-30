package com.example.eatzy_buyer.ui.screen.confirmation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.eatzy_buyer.data.model.Confirmation
import com.example.eatzy_buyer.data.model.ConfirmationItem
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(
    navController: NavController,
    order_id: Int,
    onOrderClick: () -> Unit,
    viewModel: ConfirmationViewModel = viewModel()
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val confirmation by viewModel.confirmation.collectAsState()
    var quantity by remember { mutableStateOf(1) }

    var pickedTime by remember { mutableStateOf<LocalTime?>(null) }
    val timeDialogState = rememberMaterialDialogState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var isNowSelected by remember { mutableStateOf(true) }

    val selectedItem = confirmation?.items?.firstOrNull()
    val unitPrice = selectedItem?.menu_price ?: 0.0
    val totalPrice = unitPrice * quantity

    val orangeColor = Color(0xFFF4A623)
    val unselectedTextColor = Color(0xFF455E84)

    LaunchedEffect(order_id) {
        viewModel.fetchOrderById(order_id)
    }


    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Konfirmasi Pesanan",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF455E84) // atau `unselectedTextColor`
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text("Opsi Pemesanan", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))

            // Pesan Sekarang
            Button(
                onClick = {
                    pickedTime = null
                    isNowSelected = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = if (isNowSelected)
                    ButtonDefaults.buttonColors(
                        containerColor = orangeColor,
                        contentColor = Color.White // <- ini kuncinya
                    )
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = unselectedTextColor
                    ),
                border = if (!isNowSelected) BorderStroke(1.dp, unselectedTextColor) else null
            ) {
                Text(
                    "Pesan Sekarang",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Pesan untuk Nanti
            Button(
                onClick = {
                    isNowSelected = false
                    showBottomSheet = true
                    coroutineScope.launch { bottomSheetState.show() }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = if (!isNowSelected)
                    ButtonDefaults.buttonColors(
                        containerColor = orangeColor,
                        contentColor = Color.White // <- ini juga kuncinya
                    )
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = unselectedTextColor
                    ),
                border = if (isNowSelected) BorderStroke(1.dp, unselectedTextColor) else null
            ) {
                val text = if (pickedTime != null) {
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val endTime = pickedTime!!.plusMinutes(30)
                    "Pesan untuk Nanti: ${pickedTime!!.format(formatter)} - ${endTime.format(formatter)}"
                } else {
                    "Pesan untuk Nanti"
                }

                Text(
                    text,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Rincian Menu", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            selectedItem?.let { item ->
                MenuDetailCard(
                    imageUrl = item.menu_image,
                    title = item.menu_name,
                    subtitle = item.addons.joinToString(", "),
                    price = item.menu_price,
                    quantity = quantity,
                    note = item.note ?: ""
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { /* Tambah pesanan */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF455E84)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF455E84)
                )
            ) {
                Text("Tambah Pesanan")
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Harga", fontWeight = FontWeight.Medium)
                Text(formatRupiah(totalPrice), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.confirmOrder(
                        onSuccess = {
                            Toast.makeText(context, "Pesanan berhasil dikonfirmasi", Toast.LENGTH_SHORT).show()
                            onOrderClick()
                        },
                        onError = {
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text("Pesan", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Batal")
        }
    ) {
        val now = LocalTime.now()
        val minSelectableTime = LocalTime.of(8, 0)
        val maxSelectableTime = LocalTime.of(15, 30)

        // Hitung waktu mulai yang valid (min 5 menit dari sekarang, tapi tidak sebelum 08:00)
        val startTime = if (now.isBefore(minSelectableTime)) {
            minSelectableTime
        } else {
            val roundedNow = now.plusMinutes(5)
            if (roundedNow.isAfter(maxSelectableTime)) maxSelectableTime else roundedNow
        }

        timepicker(
            initialTime = startTime,
            title = "Pilih Waktu",
            timeRange = startTime..maxSelectableTime
        ) {
            pickedTime = it
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pesan untuk", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { timeDialogState.show() },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, Color(0xFF455E84)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF455E84)
                    )
                ) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Color(0xFF455E84)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        pickedTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "Pilih Waktu",
                        color = Color(0xFF455E84)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch { bottomSheetState.hide() }
                        showBottomSheet = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A623))
                ) {
                    Text("Konfirmasi", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuDetailCard(
    imageUrl: String,
    title: String,
    subtitle: String,
    price: Double,
    quantity: Int,
    note: String,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // putih #FFFFFF
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)

                if (subtitle.isNotEmpty()) {
                    Text(subtitle, style = MaterialTheme.typography.bodySmall)
                }

                if (note.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = "Note Icon",
                            modifier = Modifier
                                .size(18.dp)
                                .padding(end = 4.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = note,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Text(formatRupiah(price), fontWeight = FontWeight.Medium)
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFD9D9D9)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun formatRupiah(amount: Double): String {
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
    return "Rp ${formatter.format(amount)}"
}

@Preview(showBackground = true)
@Composable
fun PreviewConfirmationScreen() {
    val navController = rememberNavController()
    ConfirmationScreen(
        navController = navController,
        order_id = 0, // tambahkan order_id supaya kompilasi lancar
        onOrderClick = {}
    )
}