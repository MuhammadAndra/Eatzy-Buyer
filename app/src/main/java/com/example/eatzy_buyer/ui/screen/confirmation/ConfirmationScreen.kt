package com.example.eatzy_buyer.ui.screen.confirmation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(navController: NavController, onOrderClick: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedOption by remember { mutableStateOf("now") }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf<LocalTime?>(null) }
    var quantity by remember { mutableStateOf(1) }

    val timeDialogState = rememberMaterialDialogState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val unitPrice = 12000
    val totalPrice = quantity * unitPrice

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Konfirmasi Pesanan", fontWeight = FontWeight.Bold, color = Color(0xFF455E84))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        selectedOption = "now"
                        pickedDate = LocalDate.now()
                        pickedTime = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "now") Color(0xFFF4A623) else Color(0xFFF5F5F5),
                        contentColor = if (selectedOption == "now") Color.White else Color(0xFF455E84)
                    ),
                    border = if (selectedOption != "now") BorderStroke(1.dp, Color(0xFF455E84)) else null
                ) {
                    Text("Pesan Sekarang", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                }

                val labelText = if (pickedTime != null) {
                    val endTime = pickedTime!!.plusMinutes(30)
                    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
                    "Pesan untuk Nanti - ${pickedTime!!.format(timeFormat)} - ${endTime.format(timeFormat)}"
                } else {
                    "Pesan untuk Nanti"
                }

                Button(
                    onClick = {
                        selectedOption = "later"
                        showBottomSheet = true
                        coroutineScope.launch { bottomSheetState.show() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "later") Color(0xFFF4A623) else Color(0xFFF5F5F5),
                        contentColor = if (selectedOption == "later") Color.White else Color(0xFF455E84)
                    ),
                    border = if (selectedOption != "later") ButtonDefaults.outlinedButtonBorder else null
                ) {
                    Text(labelText, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Rincian Menu", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            MenuDetailCard(
                imageUrl = "https://i0.wp.com/i.gojekapi.com/darkroom/gofood-indonesia/v2/images/uploads/eb33f8da-5098-4153-9e99-8feb74c9d072_Go-Biz_20231128_161650.jpeg?w=250",
                title = "Menu 3",
                subtitle = "Sambal Bawang",
                price = 12000.0,
                quantity = quantity,
                onIncrease = { quantity++ },
                onDecrease = { if (quantity > 1) quantity-- }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { /* Tambah Pesanan */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF455E84))
            ) {
                Text("Tambah Pesanan")
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Harga", fontWeight = FontWeight.Medium)
                Text(formatRupiah(totalPrice.toDouble()), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onOrderClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A623))
            ) {
                Text("Pesan", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("Ok") { /* Waktu dipilih */ }
            negativeButton("Cancel")
        }
    ) {
        val now = LocalTime.now()
        val isToday = pickedDate == LocalDate.now()
        val startTime = if (isToday) {
            now.plusMinutes(1).withSecond(0).withNano(0).coerceAtLeast(LocalTime.of(8, 0))
        } else {
            LocalTime.of(8, 0)
        }
        val endTime = LocalTime.of(15, 30)

        if (startTime > endTime && isToday) {
            Text(
                text = "Tidak ada waktu yang tersedia",
                modifier = Modifier.padding(16.dp),
                color = Color.Black
            )
        } else {
            timepicker(
                initialTime = pickedTime ?: startTime,
                title = "Pilih Waktu",
                timeRange = startTime..endTime
            ) {
                pickedTime = it
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Text("Pesan untuk", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { timeDialogState.show() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color(0xFFFC9824))
                    Spacer(Modifier.width(8.dp))

                    val timeText = pickedTime?.let {
                        val formatter = DateTimeFormatter.ofPattern("HH:mm")
                        val endTime = it.plusMinutes(30)
                        "${it.format(formatter)} - ${endTime.format(formatter)}"
                    } ?: "Pilih Jam"

                    Text(timeText, color = Color(0xFF675E5E))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        coroutineScope.launch { bottomSheetState.hide() }
                        showBottomSheet = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC9824)),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(24.dp)
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
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Medium)
                Text(subtitle, style = MaterialTheme.typography.labelMedium)
                Text(formatRupiah(price), fontWeight = FontWeight.Medium)
            }
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .background(Color(0xFFD9D9D9), RoundedCornerShape(32.dp))
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onDecrease() },
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A623)),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Decrease",
                        tint = Color.White
                    )
                }
                Text(
                    quantity.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { onIncrease() },
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4A623)),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Increase",
                        tint = Color.White
                    )
                }
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
        onOrderClick = {}
    )
}
