package viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import types.NewPlayer

class PickPlayersViewModel : ViewModel() {
    val players = MutableLiveData<List<NewPlayer>>().apply {
        //this.value = listOf() // this will be some players
        this.value = (1..6).map {
            NewPlayer(
                canBeRemoved = it > 2, // last 4 rows can be removed
                canBeToggled = it > 1 // every player except player 1
            )
        }
    }


}