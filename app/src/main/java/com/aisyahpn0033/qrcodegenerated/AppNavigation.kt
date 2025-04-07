package com.aisyahpn0033.qrcodegenerated

// Import Composable & Navigasi dari Jetpack Compose
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Import screen-screen dari folder UI
import com.aisyahpn0033.qrcodegenerated.ui.auth.LoginScreen
import com.aisyahpn0033.qrcodegenerated.ui.auth.RegisterScreen
import com.aisyahpn0033.qrcodegenerated.ui.splash.SplashScreen
import com.aisyahpn0033.qrcodegenerated.ui.home.HomeScreen
import com.aisyahpn0033.qrcodegenerated.ui.about.AboutScreen

// Fungsi utama navigasi aplikasi
@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Membuat NavController untuk mengatur navigasi

    // NavHost: tempat semua route didefinisikan, dimulai dari splash screen
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        // Rute ke SplashScreen
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        // Rute ke LoginScreen
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        // Rute ke RegisterScreen
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }


        // Rute ke AboutScreen
        composable(Screen.About.route) {
            AboutScreen(navController)
        }

        // Rute ke HomeScreen (dengan parameter userName)
        composable(Screen.Home.route) { backStackEntry ->
            // Mengambil argument jika ada, tapi tidak digunakan di sini
            backStackEntry.arguments?.getString("userName") ?: "Guest"
            HomeScreen(navController)
        }
    }
}

// Sealed class berisi semua screen dan route-nya
sealed class Screen(val route: String) {

    // Splash screen route
    data object Splash : Screen("splash")

    // Login screen route
    data object Login : Screen("login")

    // Register screen route
    data object Register : Screen("register")

    // Home screen route, dengan parameter userName (opsional di sini)
    data object Home : Screen("home/{userName}")

    // About screen route
    data object About : Screen("about")
}