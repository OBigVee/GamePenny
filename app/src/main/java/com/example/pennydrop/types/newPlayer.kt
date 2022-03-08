package com.example.pennydrop.types

import android.widget.ToggleButton
import androidx.databinding.ObservableBoolean
import com.example.pennydrop.game.AI
/**
 * NewPlayer class different from the Player.kt class.
 *  this class intends to figure out who's in the game and who they are:
 *  (either human or AI, what's their name or which AI).
 *
 */
data class NewPlayer (
    var playerName : String = "",
    val isHuman:ObservableBoolean = ObservableBoolean(true),
    val canBeRemoved:Boolean = true,
    val canBeToggled: Boolean = true,
    var isIncluded: ObservableBoolean = ObservableBoolean(!canBeRemoved),
    // track the index of selected AI(retrieved from Ai.BasicAI or AI.getBasicAI)
    var selectedAIPosition: Int = -1
){
    fun selectedAI() = if(!isHuman.get()){
        AI.basicAI.getOrNull(selectedAIPosition)
    }else{
        null
    }
    fun toPlayer()=Player(
        /**
         * Gets the player's name or Ai name and
         * then maps a few values from NewPlayer
         */
        if(this.isHuman.get()){
            this.playerName
        }else{
            (this.selectedAI()?.name?:"AI")
        },
        this.isHuman.get(),
        this.selectedAI()
    )

    override fun toString() = listOf(
        "name" to this.playerName,
        "isIncluded" to this.isIncluded.get(),
        "isHuman" to this.isHuman.get(),
        "canBeRemoved" to this.canBeRemoved,
        "canBeToggled" to this.canBeToggled,
        "selectedAI" to (this.selectedAI()?.name ?: "N/A")
    ).joinToString (", ", "NewPlayer(", ")"){
        (property, value)->"$property = $value"
    }
}


