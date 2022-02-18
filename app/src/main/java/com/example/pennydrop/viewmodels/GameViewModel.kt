package com.example.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pennydrop.types.Player
import com.example.pennydrop.types.Slot

class GameViewModel:ViewModel() {
    private var players:List<Player> = emptyList()

    val slots = MutableLiveData(
        (1..6).map{
            slotNum -> Slot(slotNum, slotNum != 6)}
    )

    val currentPlayer = MutableLiveData<Player?>()

    val canRoll = MutableLiveData(false)
    val canPass = MutableLiveData(false)

    val currentTurnText = MutableLiveData("")
    val currentStandingsText = MutableLiveData("")

    fun startGame(playersForNewGame: List<Player>){
        /**
         * startGame brings in the list of player and gets a game set up.
         */
        this.players = playersForNewGame
        this.currentPlayer.value = this.players.firstOrNull().apply {
            this?.isRolling = true
        }
        this.canRoll.value = true
    }

    fun roll(){

    }

    fun pass(){

    }
}