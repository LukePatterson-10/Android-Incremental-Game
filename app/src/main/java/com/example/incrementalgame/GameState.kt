package com.example.incrementalgame

import android.content.Context
import android.content.SharedPreferences

object GameState {
    private const val PREFS_NAME = "game_prefs"
    private const val XP_KEY = "xp"
    private const val LEVEL_KEY = "level"
    private const val UPGRADE1_COST_KEY = "upgrade1_cost"
    private const val UPGRADE2_COST_KEY = "upgrade2_cost"
    private const val XP_PER_CLICK_KEY = "xp_per_click"
    private const val PASSIVE_XP_PER_SECOND_KEY = "passive_xp_per_second"

    var xp: Int = 0
    var level: Int = 1
    var upgrade1Cost: Int = 10
    var upgrade2Cost: Int = 100
    var xpPerClick: Int = 1
    var passiveXpPerSecond: Int = 0

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        load()
    }

    fun save() {
        with(sharedPreferences.edit()) {
            putInt(XP_KEY, xp)
            putInt(LEVEL_KEY, level)
            putInt(UPGRADE1_COST_KEY, upgrade1Cost)
            putInt(UPGRADE2_COST_KEY, upgrade2Cost)
            putInt(XP_PER_CLICK_KEY, xpPerClick)
            putInt(PASSIVE_XP_PER_SECOND_KEY, passiveXpPerSecond)
            apply()
        }
    }

    private fun load() {
        xp = sharedPreferences.getInt(XP_KEY, 0)
        level = sharedPreferences.getInt(LEVEL_KEY, 1)
        upgrade1Cost = sharedPreferences.getInt(UPGRADE1_COST_KEY, 10)
        upgrade2Cost = sharedPreferences.getInt(UPGRADE2_COST_KEY, 100)
        xpPerClick = sharedPreferences.getInt(XP_PER_CLICK_KEY, 1)
        passiveXpPerSecond = sharedPreferences.getInt(PASSIVE_XP_PER_SECOND_KEY, 0)
    }

    fun reset() {
        xp = 0
        level = 1
        upgrade1Cost = 10
        upgrade2Cost = 100
        xpPerClick = 1
        passiveXpPerSecond = 0
        save()
    }
}
