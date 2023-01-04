package net.limaru.railheart.util

interface Signal {
  var track: LongArray

  /**
   * Adds a train to the track
   * @param signalPos Signal to add the train to
   */
  fun addTrain(signalPos: Int)

  /**
   * Advances a train by 1 signal
   * @param signalPos Current signal position
   */
  fun advanceTrain(signalPos: Int)

  /**
   * Removes a train from the track
   * @param signalPos Signal to add the train to
   */
  fun removeTrain(signalPos: Int)

  /**
   * Gets the signal type at a certain position
   * NOTE: Short because Canadian signals can have up to 216 aspects (432 if another bit is used to indicate dwarf)
   */
  fun getSignal(signalPos: Int): Short

  /**
   * Displays the object as a human-readable string. Format not handled.
   * @return A binary representation of the track as a bunch of longs joined together.
   */
  override fun toString(): String
}