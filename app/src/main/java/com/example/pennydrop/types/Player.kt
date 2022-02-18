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
){
    var pennies: Int = defaultPennyCount
    fun addPennies(count: Int=1){
        pennies += count
    }
    var isRolling:Boolean = false
    companion object{
        const val defaultPennyCount = 10
    }
}
