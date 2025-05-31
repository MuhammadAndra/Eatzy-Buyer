// ui/screen/profile/ProfileScreen.kt
package com.example.eatzy_buyer.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Pastikan ExperimentalMaterial3Api di-import jika diperlukan oleh komponen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.components.BottomNavBar // Asumsi ada
import com.example.eatzy_buyer.ui.components.PrimaryButton // Mengganti SecondaryButton
import com.example.eatzy_buyer.ui.components.TopNavBar // Asumsi ada

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UserViewModel,
    onNavigateToEdit: () -> Unit,
    onLogout: () -> Unit // Tambahkan callback untuk logout
) {
    val userState by viewModel.userState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchUserDetails()
    }

    LaunchedEffect(userState.error) {
        userState.error?.let { error ->
            // Hanya tampilkan Toast jika error message tidak kosong
            if (error.isNotBlank()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                // Pertimbangkan untuk memanggil fungsi yang spesifik membersihkan error UserState
                // viewModel.clearUserStateError()
                // resetAuthStates() mungkin tidak cocok di sini karena itu untuk state login/register
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopNavBar(
            title = "Profil Saya", // Judul yang lebih sesuai
            onBackClick = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                } else {
                    // Handle jika tidak ada backstack (misalnya, ini adalah root setelah login)
                    // Mungkin navigasi ke halaman utama atau tutup aplikasi
                }
            }
        )

        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 16.dp), // Tambah padding vertikal
                horizontalAlignment = Alignment.CenterHorizontally // Pusatkan konten
            ) {
                if (userState.isLoading && userState.user == null) { // Tampilkan loading hanya jika data user belum ada
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Memuat data profil...", textAlign = TextAlign.Center)
                } else if (userState.user != null) {
                    // Tampilkan detail pengguna
                    Text(
                        text = userState.user?.name ?: "Nama tidak tersedia", // Akses via userState.user?.name
                        style = MaterialTheme.typography.headlineSmall, // Style lebih besar untuk nama
                        modifier = Modifier.padding(top = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = userState.user?.email ?: "Email tidak tersedia", // Akses via userState.user?.email
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray // Warna lebih lembut untuk email
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    PrimaryButton(
                        text = "Edit Profil",
                        onClick = onNavigateToEdit,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton( // Tombol untuk logout
                        text = "Keluar",
                        onClick = {
                            viewModel.logout() // Panggil fungsi logout di ViewModel
                            onLogout()       // Panggil callback untuk navigasi setelah logout
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (userState.error != null && !userState.isLoading) {
                    // Tampilan jika ada error dan tidak sedang loading
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        "Gagal memuat profil.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        userState.error ?: "Terjadi kesalahan tidak diketahui.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(
                        text = "Coba Lagi",
                        onClick = { viewModel.fetchUserDetails() },
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Dorong konten ke atas jika sedikit
            }
        }
    }
}

// Contoh OutlinedButton (jika belum ada di PrimaryButton.kt atau komponen lain)
@Composable
fun OutlinedButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp) // Tinggi standar
    ) {
        Text(text, fontSize = 16.sp)
    }
}