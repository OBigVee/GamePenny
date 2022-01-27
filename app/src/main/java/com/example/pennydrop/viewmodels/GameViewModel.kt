package com.example.pennydrop.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pennydrop.types.NewPlayer
import com.example.pennydrop.types.Player

class GameViewModel:ViewModel() {
    private var players:List<Player> = emptyList()

    fun startGame(playersForNewGame: List<Player>){
        this.players = playersForNewGame


    }
}