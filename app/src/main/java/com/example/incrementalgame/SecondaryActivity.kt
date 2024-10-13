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

class SecondaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameState.initialize(this)

        setContent {
            val sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
            val darkMode = sharedPreferences.getBoolean("dark_mode", false)

            IncrementalGameTheme(darkTheme = darkMode) {
                UpgradeScreen()
            }
        }
    }
}

@Composable
fun UpgradeScreen() {
    val context = LocalContext.current
    var xp by remember { mutableIntStateOf(GameState.xp) }
    var upgrade1Cost by remember { mutableIntStateOf(GameState.upgrade1Cost) }
    var upgrade2Cost by remember { mutableIntStateOf(GameState.upgrade2Cost) }
    var level by remember { mutableIntStateOf(GameState.level) }

    val backgroundColor = MaterialTheme.colorScheme.background

    LaunchedEffect(Unit) {
        snapshotFlow { GameState.xp }.collect { xp = it }
        snapshotFlow { GameState.upgrade1Cost }.collect { upgrade1Cost = it }
        snapshotFlow { GameState.upgrade2Cost }.collect { upgrade2Cost = it }
        snapshotFlow { GameState.level }.collect { level = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Upgrades", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Current XP: $xp", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (xp >= upgrade1Cost) {
                GameState.xp -= upgrade1Cost
                GameState.xpPerClick += 1
                GameState.upgrade1Cost = (GameState.upgrade1Cost * 1.5).toInt()
                GameState.level += 1
                xp = GameState.xp
                upgrade1Cost = GameState.upgrade1Cost
                GameState.save()
            }
        }) {
            Text("Upgrade 1: Increase XP per Click (Cost: $upgrade1Cost)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (xp >= upgrade2Cost) {
                GameState.xp -= upgrade2Cost
                GameState.passiveXpPerSecond += 1
                GameState.upgrade2Cost = (GameState.upgrade2Cost * 1.5).toInt()
                GameState.level += 1
                xp = GameState.xp
                upgrade2Cost = GameState.upgrade2Cost
                GameState.save()
            }
        }) {
            Text("Upgrade 2: Passive XP per Second (Cost: $upgrade2Cost)")
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
