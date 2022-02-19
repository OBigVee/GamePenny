package com.example.pennydrop.game

import com.example.pennydrop.types.Player
import com.example.pennydrop.types.Slot
import kotlin.random.Random

object GameHandler {
    fun roll(
        players: List<Player>,
        currentPlayer: Player,
        slots: List<Slot>
    ): TurnResult = rollDie().let { lastRoll ->
        slots.getOrNull(lastRoll - 1)?.let { slot ->
            if (slot.isFilled) {
                TurnResult(
                    lastRoll,
                    coinChangeCount = slots.count { it.isFilled },
                    clearSlots = true,
                    turnEnd = TurnEnd.Bust,
                    previousPlayer = currentPlayer,
                    currentPlayer = nextPlayer(players, currentPlayer),
                    playerChanged = true,
                    canRoll = true,
                    canPass = false
                )
            } else {
                TurnResult(
                    lastRoll,
                    currentPlayer = currentPlayer,
                    canRoll = true,
                    canPass = true,
                    coinChangeCount = -1
                )
            }
        }
    } ?: TurnResult(isGameOver = true)


    fun pass(players: List<Player>, currentPlayer: Player, ) = TurnResult(
        previousPlayer = currentPlayer,
        currentPlayer = nextPlayer(players, currentPlayer),
        playerChanged = true,
        turnEnd = TurnEnd.Pass,
        canRoll = true,
        canPass = false
    )

    private fun rollDie(sides: Int = 6) = Random.nextInt(1, sides + 1)

    private fun nextPlayer(
        players: List<Player>,
        currentPlayer: Player,
    ): Player {
        /**
         * get the index of the current player in the list of players
         * figure the next player's index
         * * return the next player
         * */
        val currentIndex = players.indexOf(currentPlayer)
        val nextIndex = (currentIndex + 1) % players.size
        return players[nextIndex]
    }
}