package com.example.pennydrop.types

import com.example.pennydrop.game.AI

/**
 * Player class is different from the newPlayer.kt class.
 *  this class intends to tracking player's
 *  status while the game is on( currently rolling, pennies left e.t.c)
 *  attributes : playerName; isHuman; selectedAI
 */

data class Player(
    val playerName:String="",
    val isHuman:Boolean = true,
    val selectedAI: AI? = null
) {
    var pennies: Int = defaultPennyCount
    var isRolling:Boolean = false

    fun addPennies(count: Int=1){
        // increment penny
        pennies += count
    }

    /**
     * penniesLeft() is straightforward it checks if a user's
     * penny count is greater than zero
     */
    fun penniesLeft(subtractPenny: Boolean = false) = (pennies -(if(subtractPenny) 1 else 0)) > 0

    companion object{
        const val defaultPennyCount = 10
    }
}
