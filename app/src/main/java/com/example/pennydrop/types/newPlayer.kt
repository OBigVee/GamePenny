package com.example.pennydrop.types

import android.widget.ToggleButton
import androidx.databinding.ObservableBoolean
import com.example.pennydrop.game.AI

data class NewPlayer (
    var playerName : String = "",
    val isHuman:ObservableBoolean = ObservableBoolean(true),
    val canBeRemoved:Boolean = true,
    val canBeToggled: Boolean = true,
    var isIncluded: ObservableBoolean = ObservableBoolean(false),
    // track the index of selected AI(retrieved from Ai.BasicAI or AI.getBasicAI)
    var selectedAIPosition: Int = -1

){
    fun selectedAI() = if(!isHuman.get()){
        AI.basicAI.getOrNull(selectedAIPosition)
    }else{
        null
    }
    fun toPlayer()=Player(
        if(this.isHuman.get()){
            this.playerName
        }else{
            (this.selectedAI()?.name?:"AI")
        },
        this.isHuman.get(),
        this.selectedAI()
    )
}


