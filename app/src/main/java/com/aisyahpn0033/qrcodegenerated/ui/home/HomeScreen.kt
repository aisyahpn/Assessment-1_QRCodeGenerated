package com.aisyahpn0033.qrcodegenerated.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aisyahpn0033.qrcodegenerated.QRCodeGenerator
import com.aisyahpn0033.qrcodegenerated.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    // State untuk input teks yang akan diubah menjadi QR code
    var inputText by remember { mutableStateOf("") }

    // State untuk menyimpan gambar bitmap hasil generate QR code
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // State untuk menampilkan atau menyembunyikan dropdown menu di AppBar
    var expanded by remember { mutableStateOf(false) }

    // Fungsi untuk generate QR code dari teks
    fun generateQR() {
        qrBitmap = QRCodeGenerator.generateQRCode(inputText)
    }

    // Scaffold untuk kerangka layout dengan TopAppBar dan isi konten
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        // Navigasi ke halaman About
                        DropdownMenuItem(
                            text = { Text("About") },
                            onClick = {
                                expanded = false
                                navController.navigate(Screen.About.route)
                            }
                        )
                        // Intent untuk membagikan aplikasi
                        DropdownMenuItem(
                            text = { Text("Share") },
                            onClick = {
                                expanded = false
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "Coba aplikasi ini! 🚀")
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Bagikan dengan"))
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Panggil konten utama layar
        HomeScreenContent(
            modifier = Modifier.padding(paddingValues),
            inputText = inputText,
            onInputChange = { inputText = it },
            qrBitmap = qrBitmap,
            onGenerateClick = { generateQR() },
            onLogoutClick = {
                // Saat logout, tampilkan Toast dan arahkan ke halaman login
                Toast.makeText(context, "Logout Berhasil!", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    inputText: String,
    onInputChange: (String) -> Unit,
    qrBitmap: Bitmap?,
    onGenerateClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    // State untuk menyimpan feedback yang dipilih pengguna
    var selectedFeedback by remember { mutableStateOf("") }

    // State scroll agar layar bisa digulir
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState), // Aktifkan scroll vertikal
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Judul halaman
            Text(
                text = "Generate QR Code",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Card input teks untuk QR
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Input teks
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onInputChange,
                        label = { Text("Masukkan teks") },
                        placeholder = { Text("Ketik sesuatu...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    )

                    // Tombol untuk generate QR
                    Button(
                        onClick = onGenerateClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Generate QR Code", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            // Card untuk menampilkan hasil QR
            Card(
                modifier = Modifier
                    .width(220.dp)
                    .height(220.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Jika QR tersedia, tampilkan gambarnya
                    qrBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(170.dp)
                        )
                    } ?: Text(
                        text = "QR belum dibuat", // Jika belum ada QR
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Feedback section: pilih apakah aplikasi bagus atau perlu perbaikan
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Menurut Anda, apakah aplikasi ini bagus?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Pilihan feedback menggunakan RadioButton
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    RadioButton(
                        selected = selectedFeedback == "Bagus",
                        onClick = { selectedFeedback = "Bagus" }
                    )
                    Text("Bagus")

                    RadioButton(
                        selected = selectedFeedback == "Perlu Perbaikan",
                        onClick = { selectedFeedback = "Perlu Perbaikan" }
                    )
                    Text("Perlu Perbaikan")
                }
            }

            // Tombol Logout
            ElevatedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .width(270.dp)
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFFF8A80), // Warna tombol logout
                    contentColor = Color.White
                )
            ) {
                Text("Logout", fontSize = 18.sp)
            }

            // Spacer untuk memberi ruang di bawah saat di-scroll
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Preview tampilan HomeScreenContent (untuk layout editor)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        inputText = "Contoh QR",
        onInputChange = {},
        qrBitmap = null,
        onGenerateClick = {},
        onLogoutClick = {}
    )
}