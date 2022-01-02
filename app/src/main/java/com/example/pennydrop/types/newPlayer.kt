package com.example.pennydrop.types

import android.widget.ToggleButton
import androidx.databinding.ObservableBoolean

data class NewPlayer (
    var playerName : String = "",
    val isHuman:ObservableBoolean = ObservableBoolean(true),
    val canBeRemoved:Boolean = true,
    val canBeToggled: Boolean = true,
    var isIncluded: ObservableBoolean = ObservableBoolean(!canBeRemoved),
    var selectedAIPosition: Int = -1 // track the index

)

