package com.example.pennydrop.types

/**
 * Game slot properties which includes:
 * the slot index-> number: Int,
 * whether or not it can be filled -> canBeFilled:Boolean,
 * is slot occupied? -> isFilled:Boolean,
 * lastRolled
 */
data class Slot(
    val number:Int,
    val canBeFilled:Boolean = true,
    var isFilled:Boolean = false,
    var lastRolled:Boolean = false
)

/**
 * clear function is an extension function added to the List type of Slot.kt file
 * But the extension function is defined outside of Slot class.
 * Since it is an extension on Slot class and not part of the Slot class itself,
 * we can put the code anywhere that makes sense.
 * I choose the Slot class and import it anywhere i want to use it.
  */
fun List<Slot>.clear() = this.forEach{ slot ->
    slot.isFilled = false
    slot.lastRolled = false
}
fun List<Slot>.fullSlots(): Int = this.count{
    it.canBeFilled && it.isFilled
}