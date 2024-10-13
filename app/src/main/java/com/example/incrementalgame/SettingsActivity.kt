package com.example.incrementalgame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.incrementalgame.ui.theme.IncrementalGameTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
            val darkMode = sharedPreferences.getBoolean("dark_mode", false)

            IncrementalGameTheme(darkTheme = darkMode) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    var darkMode by remember { mutableStateOf(sharedPreferences.getBoolean("dark_mode", false)) }

    val backgroundColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Dark Mode", style = MaterialTheme.typography.titleMedium)
        Switch(
            checked = darkMode,
            onCheckedChange = { isChecked ->
                darkMode = isChecked
                with(sharedPreferences.edit()) {
                    putBoolean("dark_mode", darkMode)
                    apply()
                }
                (context as? Activity)?.recreate()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            GameState.reset()
            GameState.save()
            (context as? Activity)?.recreate()
        }) {
            Text("Reset Progress")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Back")
        }
    }
}
