package com.example.incrementalgame

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.incrementalgame.ui.theme.IncrementalGameTheme

class HelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
            val darkMode = sharedPreferences.getBoolean("dark_mode", false)

            IncrementalGameTheme(darkTheme = darkMode) {
                HelpScreen()
            }
        }
    }
}

@Composable
fun HelpScreen() {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Help", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("How to Play:", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // I added some extra information to the help screen.

        Text("1. Click the button to earn XP.")
        Text("2. Use XP to buy upgrades.")
        Text("3. Dark mode is available in the settings menu.")
        Text("4. Reset progress in the settings menu.")
        Text("5. Have fun!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Back")
        }
    }
}
