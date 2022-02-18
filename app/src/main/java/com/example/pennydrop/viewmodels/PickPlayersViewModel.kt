package com.example.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pennydrop.types.NewPlayer

class PickPlayersViewModel:ViewModel() {
    //players holds a list of new players
    val players = MutableLiveData<List<NewPlayer>>().apply{
        // this will be some players
        // create a range of value from 1-6 and map the values
        // each to a new instance
        this.value = (1..6).map{
            NewPlayer(
                // conditional logic to parse logic to the pick player screen
                canBeRemoved = it > 2, // last 4 players can be remove
                canBeToggled = it > 1 // only last 5 players can be toggled
            )
        }
    }

}