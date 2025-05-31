package com.example.eatzy_buyer.ui.screen.verificationOtp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.layout.ContentScale
import com.example.eatzy_buyer.R
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.components.PrimaryButton
// import kotlinx.coroutines.launch // Tidak perlu jika Toast di dalam LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String,
    onNavigateToLogin: () -> Unit,
    viewModel: UserViewModel
) {
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
    // val scope = rememberCoroutineScope() // Tidak perlu
    val verifyOtpState by viewModel.verifyOtpState.collectAsState()
    val resendOtpState by viewModel.resendOtpState.collectAsState()

    val isLoading = verifyOtpState.isLoading || resendOtpState.isLoading

    LaunchedEffect(verifyOtpState) {
        if (verifyOtpState.success) {
            Toast.makeText(context, "OTP berhasil diverifikasi", Toast.LENGTH_SHORT).show()
            onNavigateToLogin()
            viewModel.resetAuthStates() // Reset state setelah sukses
        } else if (verifyOtpState.error != null) {
            Toast.makeText(context, verifyOtpState.error, Toast.LENGTH_SHORT).show()
            // viewModel.clearVerifyOtpError() // Reset spesifik
        }
    }

    LaunchedEffect(resendOtpState) {
        if (resendOtpState.success) {
            Toast.makeText(context, "OTP baru telah dikirim ke email Anda", Toast.LENGTH_SHORT).show()
            viewModel.resetAuthStates() // Reset state setelah sukses (misal untuk flag 'success' di resend)
        } else if (resendOtpState.error != null) {
            Toast.makeText(context, resendOtpState.error, Toast.LENGTH_SHORT).show()
            // viewModel.clearResendOtpError() // Reset spesifik
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_login), // Pastikan drawable ada
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Verifikasi OTP",
                    style = MaterialTheme.typography.headlineMedium // Sesuaikan
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kode OTP telah dikirim ke $email", // Tambahkan info email
                    fontSize = 14.sp,
                    color = Color.Gray // Sesuaikan
                )


                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { if (it.length <= 6) otp = it.filter { char -> char.isDigit() } }, // Contoh: OTP 6 digit
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Kode OTP") },
                        singleLine = true,
                        enabled = !isLoading
                        // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword) // Untuk OTP
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (isLoading && verifyOtpState.isLoading) { // Hanya tampilkan loading untuk verifikasi utama
                    CircularProgressIndicator()
                } else {
                    PrimaryButton(
                        text = "Verifikasi",
                        onClick = {
                            if (otp.isNotBlank()) {
                                viewModel.verifyOtp(email, otp)
                            } else {
                                Toast.makeText(context, "Kode OTP tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading && resendOtpState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Indikator lebih kecil untuk resend
                } else {
                    Text(
                        text = "Tidak menerima OTP? Kirim ulang",
                        color = Color(0xFFF59A2F), // Pertimbangkan Theme Color
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable(enabled = !isLoading) { viewModel.resendOtp(email) }
                    )
                }
            }
        }
    }
}