package com.example.pennydrop.game

import com.example.pennydrop.types.Player
import com.example.pennydrop.types.Slot

/**
 * TurnResult contains everything the GameViewModel needs
 * to know after a turn(or for the start of the game).
 */

data class TurnResult(
    val lastRoll: Int? = null,
    val coinChangeCount: Int? = null,
    val previousPlayer: Player? = null,
    val currentPlayer:Player? = null,
    val playerChanged:Boolean = false,
    val turnEnd: TurnEnd? = null,
    val canRoll:Boolean = false,
    val canPass:Boolean = false,
    val clearSlots:Boolean = false,
    val isGameOver:Boolean = false
)

enum class TurnEnd {
    // holds constant values
    Pass, Bust, Win
}