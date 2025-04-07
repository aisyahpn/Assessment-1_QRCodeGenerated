package com.aisyahpn0033.qrcodegenerated

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aisyahpn0033.qrcodegenerated.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme { // Pastikan AppTheme ada di package ui.theme
                AppNavigation() // Tidak perlu navController sebagai parameter
            }
        }
    }
}
