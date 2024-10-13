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
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameState.initialize(this)

        val sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)

        setContent {
            IncrementalGameTheme(darkTheme = darkMode) {
                IncrementalGameScreen()
            }
        }
    }
}

@Composable
fun IncrementalGameScreen() {
    var xp by remember { mutableIntStateOf(GameState.xp) }
    val level by remember { mutableIntStateOf(GameState.level) }
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            GameState.xp += GameState.passiveXpPerSecond
            xp = GameState.xp
            GameState.save()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("XP: $xp", style = MaterialTheme.typography.headlineMedium)
        Text("Level: $level", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            GameState.xp += GameState.xpPerClick
            xp = GameState.xp
            GameState.save()
        }) {
            Text("Click for XP")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(context, SecondaryActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Upgrades")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(context, HelpActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Help")
        }
    }
}
