// ui/screen/profile/EditProfile.kt
package com.example.eatzy_buyer.ui.screen.editProfile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.components.BottomNavBar // Asumsi ada
import com.example.eatzy_buyer.ui.components.PrimaryButton // Mengganti SecondaryButton untuk konsistensi atau sesuaikan
import com.example.eatzy_buyer.ui.components.TopNavBar // Asumsi ada
// Mengganti EmailTextField jika itu spesifik untuk email

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavController, viewModel: UserViewModel) {
    val userState by viewModel.userState.collectAsState()
    val context = LocalContext.current

    // Inisialisasi username dari userState.user?.name
    // Dijalankan sekali saat komposisi awal atau saat userState.user berubah signifikan
    var username by remember(userState.user?.name) {
        mutableStateOf(userState.user?.name ?: "")
    }

    // Untuk mengambil detail pengguna saat layar pertama kali dimuat
    LaunchedEffect(Unit) {
        viewModel.fetchUserDetails()
    }

    // Observer untuk error dan keberhasilan update
    // Kita butuh cara untuk tahu update berhasil dari ViewModel, misal dengan flag success
    // Atau dengan cara membandingkan nilai lama dan baru setelah userState diperbarui
    // Untuk saat ini, kita fokus pada penanganan error. Sukses akan ditangani setelah popBackStack.

    LaunchedEffect(userState.error) {
        userState.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            // Pertimbangkan untuk menambahkan fungsi clearUserStateError() di ViewModel
            // viewModel.clearUserStateError()
        }
    }

    // LaunchedEffect untuk menangani navigasi setelah update berhasil.
    // Ini perlu state yang jelas dari ViewModel yang menandakan update sukses.
    // Jika UserState di-emit ulang dengan data baru tanpa error, itu bisa jadi indikasi.
    // Namun, ini bisa kompleks jika ada error lain yang mungkin terjadi.
    // Cara sederhana: jika userState.user.name berubah dan userState.error null.
    // Ini akan lebih baik jika ada state khusus untuk status update.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopNavBar(
            title = "Edit Profil",
            onBackClick = { navController.popBackStack() }
        )

        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Pusatkan konten
            ) {
                Spacer(modifier = Modifier.height(32.dp)) // Beri jarak lebih di atas

                OutlinedTextField( // Ganti dengan OutlinedTextField atau komponen TextField Anda
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nama") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !userState.isLoading // Nonaktifkan saat loading
                )

                Spacer(modifier = Modifier.height(24.dp))
                // Jika ingin menampilkan email (tidak bisa diedit misalnya)
                OutlinedTextField(
                    value = userState.user?.email ?: "Memuat...",
                    onValueChange = { /* Email tidak diubah di sini */ },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = false, // Email tidak bisa diedit
                )


                Spacer(modifier = Modifier.weight(1f)) // Dorong tombol ke bawah

                if (userState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    PrimaryButton( // Ganti dengan PrimaryButton atau Button Anda
                        text = "Simpan Perubahan",
                        onClick = {
                            if (username.isNotBlank()) {
                                viewModel.updateUser(username)
                                // Logika toast dan navigasi sebaiknya bergantung pada hasil update dari ViewModel
                                // Untuk sementara, kita asumsikan akan ada update pada userState
                                // yang akan memicu LaunchedEffect lain atau navigasi.
                                // Atau, ViewModel bisa mengirim event navigasi.
                                // Solusi sementara: Navigasi setelah memanggil update,
                                // dan biarkan Toast error yang menangani jika gagal.
                                Toast.makeText(context, "Perubahan sedang disimpan...", Toast.LENGTH_SHORT).show()
                                navController.popBackStack() // Mungkin terlalu cepat, idealnya tunggu konfirmasi sukses
                            } else {
                                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}