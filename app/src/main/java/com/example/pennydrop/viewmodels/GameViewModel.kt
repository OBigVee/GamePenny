package com.example.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennydrop.game.GameHandler
import com.example.pennydrop.game.TurnEnd
import com.example.pennydrop.game.TurnResult
import com.example.pennydrop.types.Player
import com.example.pennydrop.types.Slot
import com.example.pennydrop.types.clear
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel:ViewModel() {
    private var players:List<Player> = emptyList()

    val slots = MutableLiveData(
        (1..6).map{
            // number
            // canBeFilled
            // isFilled
            // lastRolled
            slotNum -> Slot(slotNum, slotNum != 6)}
    )

    val currentPlayer = MutableLiveData<Player?>()

    val canRoll = MutableLiveData(false)
    val canPass = MutableLiveData(false)
    val currentTurnText = MutableLiveData("")
    val currentStandingsText = MutableLiveData("")

    private var clearText = false

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
        // [ similar to taking in a Collection of some kind and generates a String using each item.]
        players: List<Player>,
        headerText: String = "Current Standings:") = players.sortedBy{
            it.pennies }.joinToString(separator = "\n", prefix = "$headerText\n"){
                "\t${it.playerName} - ${it.pennies} pennies"
    }

    fun roll(){
        slots.value?.let { currentSlots ->
            // Comparing against true saves us a null check
            val currentPlayer = players.firstOrNull{it.isRolling}
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
        /**
         * add/remove the currentPlayer pennies,
         * mark the proper player as rolling,
         * update the slots,
         * generate turnText,
         * generate standingsText,
         * change canRoll flags
         * change canPass flags,
         * and finally handle game-over.
         */
     if (result.currentPlayer != null){
         currentPlayer.value?.addPennies(result.coinChangeCount?:0)
         currentPlayer.value = result.currentPlayer
         this.players.forEach { player -> player.isRolling = result.currentPlayer == player
         }
     }
        if (result.lastRoll != null){
            slots.value?.let {currentSlots -> updateSlots(result, currentSlots, result.lastRoll)}
        }

        currentTurnText.value = generateTurnText(result)
        currentStandingsText.value = generateCurrentStandings(this.players)

        canRoll.value = result.canRoll
        canPass.value = result.canPass

        if (!result.isGameOver && result.currentPlayer?.isHuman == false){
            canPass.value = false
            canRoll.value = false
            playAITurn()
        }

    }

    private fun playAITurn(){
        /**
         * Function using coroutines
         * it helps to make an async call without freezing up the UI for the user.
         * in this case of this app, it delays for a second between Ai moves to make the game feel real.
         *
         */
        viewModelScope.launch {
           delay(1000)
            slots.value?.let{
                currentSlots -> val currentPlayer = players.firstOrNull{it.isRolling}

                if(currentPlayer != null && !currentPlayer.isHuman){
                    GameHandler.playAITurn(
                        players,
                        currentPlayer,
                        currentSlots,
                        canPass.value == true
                    )?.let{ result ->
                        updateFromGameHandler(result)
                    }
                }
            }
        }
    }

    private fun generateTurnText(result: TurnResult): String{
        /**
         *  gives users info about the current turn, how a turn ends
         *  and a summary of scores when a game ends
         *  Method has 3 primary function:
         *  1) clear out existing text if the previous event was the end of a turn.
         *  2) flag if the nxt set of text should be on a cleared view
         *  3) display the next set of text.
         */
        if (clearText) currentTurnText.value = ""
        clearText = result.turnEnd !=null

        val currentText = currentTurnText.value?:""
        val currentPlayerName = result.currentPlayer?.playerName?:"???"
        return when{
            // TurnResult-based logic and text
            result.isGameOver -> """
            |Game Over!
            |$currentPlayerName is the winner!
            |
            |${generateCurrentStandings(this.players, "Final SCores:\n")}
            }}
            """.trimMargin()

            result.turnEnd == TurnEnd.Bust ->
                "${ohNoPhrases.shuffled().first()
                } ${result.previousPlayer?.playerName} rolled a ${result.lastRoll
                }.  They collected ${result.coinChangeCount}" +
                        " pennies for a total of ${result.previousPlayer?.pennies}.\n$currentText"

            result.turnEnd == TurnEnd.Pass -> "${result.previousPlayer?.playerName} passed. " +
                    " They currently have ${result.previousPlayer?.pennies} pennies.\n$currentText"

            result.lastRoll != null -> "$currentPlayerName rolled a ${result.lastRoll}.\n$currentText"
            //result.turnEnd == TurnEnd.Bust -> //playerbusted got some points
           //result.turnEnd == TurnEnd.Pass -> // player passed result.lastRoll != null -> "$currentText\n$currentPlayerName rolled a ${result.lastRoll}"
            else -> ""
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
        slots.notifyChange()
    }
    private val ohNoPhrases = listOf(
        "Oh no!",
        "Bummer!",
        "Dang.",
        "Whoops.",
        "Ah, fiddlesticks.",
        "Oh, kitty cats.",
        "Piffle.",
        "Well, crud.",
        "Ah, cinnamon bits.",
        "Ooh, bad luck.",
        "Shucks!",
        "Woopsie daisy.",
        "Noo o!",
        "Aw, rats and bats.",
        "Blood and thunder!",
        "Gee whillikins.",
        "Well that's disappointing.",
        "I find your lack of luck disturbing.",
        "That stunk, huh?",
        "Uff da."
    )

    private fun <T> MutableLiveData<List<T>>.notifyChange(){
        // notifyChange automatically send an event to all listeners with an update
        // it is a quick extension function to fire off LiveData event
        this.value = this.value
    }
}


