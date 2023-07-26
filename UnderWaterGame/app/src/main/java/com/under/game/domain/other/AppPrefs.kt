package com.under.game.domain.other

import android.content.Context

class AppPrefs(context: Context) {
    val sp = context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

    fun getBalance(): Int = sp.getInt("BALANCE", 0)
    fun setBalance(value: Int) = sp.edit().putInt("BALANCE", getBalance() + value).apply()

    fun isDifficultyBought(value: Difficulty): Boolean {
        return when (value) {
            Difficulty.EASY -> sp.getBoolean("LOW", true)
            Difficulty.NORMAL -> sp.getBoolean("NORMAL", false)
            Difficulty.MEDIUM -> sp.getBoolean("MEDIUM", false)
            Difficulty.HARD -> sp.getBoolean("HARD", false)
        }
    }

    fun buyDifficulty(value: Difficulty) {
        when (value) {
            Difficulty.EASY -> sp.edit().putBoolean("LOW", true).apply()
            Difficulty.NORMAL -> sp.edit().putBoolean("NORMAL", true).apply()
            Difficulty.MEDIUM -> sp.edit().putBoolean("MEDIUM", true).apply()
            Difficulty.HARD -> sp.edit().putBoolean("HARD", true).apply()
        }
    }
}