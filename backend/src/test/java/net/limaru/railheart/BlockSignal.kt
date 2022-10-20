package net.limaru.railheart

@OptIn(ExperimentalUnsignedTypes::class)
internal class BlockSignal(numberOfSignals: Int) {
  @OptIn(ExperimentalUnsignedTypes::class)
  var track: ULongArray

  init {
    track = ULongArray(numberOfSignals / 32 + 1)
  }

  @JvmOverloads
  fun addTrain(signalNo: Int = 0) {
    track[0] = track[0] or (2UL shl (signalNo shl 1))
  }

  fun advanceTrains() {
    for (i in track.size - 1 downTo 1) {
      track[i] = track[i] shl 2
      var lastSignalOfPreviousBlock = track[i - 1] and (-4611686018427387904L).toULong()
      //System.out.println(Long.toBinaryString(lastSignalOfPreviousBlock));
      lastSignalOfPreviousBlock = lastSignalOfPreviousBlock shr 62
      //System.out.println(Long.toBinaryString(lastSignalOfPreviousBlock));
      track[i] = track[i] or lastSignalOfPreviousBlock
    }
  }

  fun printTrack() {
    for (l in track) {
      println(l)
    }
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val b = BlockSignal(48)
      println("31 029 8 7 6 5 4 3 2 1 019 8 7 6 5 4 3 2 1 0 9 8 7 6 5 4 3 2 1 0")
      b.printTrack()
      b.addTrain(31)
      b.advanceTrains()
      b.advanceTrains()
      println("31 029 8 7 6 5 4 3 2 1 019 8 7 6 5 4 3 2 1 0 9 8 7 6 5 4 3 2 1 0")
      b.printTrack()
    }
  }
}