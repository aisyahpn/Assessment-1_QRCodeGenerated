package com.aisyahpn0033.qrcodegenerated.ui.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aisyahpn0033.qrcodegenerated.R
import com.aisyahpn0033.qrcodegenerated.Screen

// Fungsi utama untuk menampilkan LoginScreen dan mengatur navigasi saat login atau register
@Composable
fun LoginScreen(navController: NavController) {
    LoginScreenContent(
        onLoginSuccess = {
            // Navigasi ke HomeScreen jika login sukses
            navController.navigate(Screen.Home.route)
        },
        onRegisterClick = {
            // Navigasi ke RegisterScreen jika tombol register diklik
            navController.navigate(Screen.Register.route)
        }
    )
}

// Fungsi tanpa parameter untuk keperluan Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        onLoginSuccess = {}, // Dummy lambda
        onRegisterClick = {}
    )
}

// UI utama dari LoginScreen yang bisa dipakai ulang (termasuk di preview)
@Composable
fun LoginScreenContent(
    onLoginSuccess: () -> Unit, // Callback jika login sukses
    onRegisterClick: () -> Unit // Callback jika user ingin register
) {
    val context = LocalContext.current // Mendapatkan context Android saat ini
    var email by remember { mutableStateOf("") } // State untuk input email
    var password by remember { mutableStateOf("") } // State untuk input password
    var emailError by remember { mutableStateOf(false) } // Menandai apakah email salah
    var passwordError by remember { mutableStateOf(false) } // Menandai apakah password salah

    Surface(
        modifier = Modifier.fillMaxSize(), // Menyesuaikan ukuran layar
        color = MaterialTheme.colorScheme.background // Gunakan warna latar dari tema
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Posisikan konten di tengah layar
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = MaterialTheme.shapes.extraLarge, // Sudut membulat
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo aplikasi di atas form login
                    Image(
                        painter = painterResource(id = R.drawable.login_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 16.dp)
                    )

                    // Judul teks selamat datang
                    Text(
                        text = "Welcome Back!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Input email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = "Email Icon")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = emailError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Menampilkan error jika email tidak valid
                    if (emailError) {
                        Text(
                            "Email tidak valid",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                        },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(), // Agar input password jadi bintang
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = passwordError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Menampilkan error jika password kurang dari 6 karakter
                    if (passwordError) {
                        Text(
                            "Password harus lebih dari 6 karakter",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tombol Login
                    Button(
                        onClick = {
                            // Validasi input
                            emailError = email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            passwordError = password.length < 6

                            if (!emailError && !passwordError) {
                                // Jika valid, tampilkan toast dan lanjut ke halaman berikutnya
                                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Login", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Teks untuk berpindah ke halaman register
                    TextButton(onClick = { onRegisterClick() }) {
                        Text("Belum punya akun? Register")
                    }
                }
            }
        }
    }
}