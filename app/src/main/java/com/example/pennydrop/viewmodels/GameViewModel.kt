package com.example.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pennydrop.game.GameHandler
import com.example.pennydrop.game.TurnResult
import com.example.pennydrop.types.Player
import com.example.pennydrop.types.Slot
import com.example.pennydrop.types.clear

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
        canRoll.value = true
        canPass.value = false

        slots.value?.clear()
        slots.notifyChange()

            currentTurnText.value = "The game bas begun!\n"
            currentStandingsText.value = generateCurrentStandings(this.players)
    }

    private fun generateCurrentStandings(
        //functions brings in a list of Player, sort it by the player's current
        // penny count, then use the joinToString to bring everything together.
        players: List<Player>,
        headerText: String = "Current Standings:") = players.sortedBy{
            it.pennies }.joinToString(separator = "\n", prefix = "$headerText\n"){
                "\t${it.playerName} - ${it.pennies} pennies"
    }

    fun roll(){
        slots.value?.let { currentSlots ->
            // Comparing against true saves us a null check
            val currentPlayer = players.firstOrNull(){it.isRolling}
            if(currentPlayer != null && canRoll.value == true){
                updateFromGameHandler(
                    GameHandler.roll(players,currentPlayer,currentSlots)
                )
            }
        }
    }

    fun pass(){
        val currentPlayer = players.firstOrNull{it.isRolling}
        if(currentPlayer != null && canPass.value == true){
            updateFromGameHandler(GameHandler.pass(players,currentPlayer))
        }
    }


    private fun updateFromGameHandler(result: TurnResult) {
     if (result.currentPlayer != null){
         currentPlayer.value?.addPennies(result.coinChangeCount?:0)
         currentPlayer.value = result.currentPlayer
         this.players.forEach { player -> player.isRolling = result.currentPlayer == player
         }
     }
        if (result.lastRoll != null){
            slots.value?.let {currentSlots-> updateSlots(result, currentSlots, result.lastRoll)}
        }

        currentTurnText.value = generateTurnText(result)
        currentStandingsText.value = generateCurrentStandings(this.players)

        canRoll.value = result.canRoll
        canPass.value = result.canPass

        if (!result.isGameOver && result.currentPlayer?.isHuman == false){
            canPass.value = false
            canRoll.value = false
        }

    }

    private fun updateSlots(result: TurnResult, currentSlots: List<Slot>, lastRoll: Int) {
       if (result.clearSlots){
           currentSlots.clear()
       }
        currentSlots.firstOrNull{ it.lastRolled }?.apply { lastRolled = false }

        currentSlots.getOrNull(lastRoll - 1)?.also {
                slot -> if (!result.clearSlots && slot.canBeFilled) slot.isFilled = true
            slot.lastRolled = true
        }
    }

    private fun <T> MutableLiveData<List<T>>.notifyChange(){
        // notifyChange automatically send an event to all listeners with an update
        // it is a quick extension function to fire off LiveData event
        this.value = this.value
    }
}


