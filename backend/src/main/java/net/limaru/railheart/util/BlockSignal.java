package net.limaru.railheart.util;

public class BlockSignal {
  // This file uses bitwise programming.
  public long[] track;
  
  /**
   * Initialises the object
   * @param signalCount Total number of signals
   */
  public BlockSignal(int signalCount) {
    this.track = new long[signalCount>>5+1];  // Split track up into 32-signal sections due to Java's long limit
  }
  
  /**
   * Adds a train to the track
   * @param signalPos Signal to add the train to
   */
  public void addTrain(int signalPos) {
    // Get section to insert to
    int i = signalPos >> 5;
    // Get position in section
    signalPos &= 31;
    // Insert train onto track
    this.track[i] |= 0b11L << (signalPos << 1L);
  }
  
  /**
   * Advances a train by 1 signal
   * @param signalPos Current signal position
   */
  public void advanceTrain(int signalPos) {
    // Make current position yellow (xx11 -> xx01).
    this.track[signalPos >> 5] ^= 0b10L << ((signalPos & 31) << 1L);
    // Make next position red (00xx -> 11xx)
    this.track[signalPos >> 5] |= 0b11L << ((++signalPos & 31) << 1L);
  }
  
  /**
   * Updates all yellow signal positions
   */
  public void updateYellows() {
    // Test for reds
    for (int i = 0; i < track.length; i++) {
      long reds = (track[i] &     // Check ones places' bits
                   track[i] >> 1) // Check two places' bits
                     >> 2;        // if both are 1, the ones place will be a 1. Right shift by 2 for insertion
      long carry = (track[i+1] &     // Check ones places' bits
                    track[i+1] >> 1) // Check two places' bits
                      << 62;         // Left shift by 62 for carry-over
      reds = reds | carry;
      // Insert yellows
      track[i] |= reds;
    }
  }
  
  /**
   * Updates all green signal positions
   */
  public void updateGreens() {
    // Test for yellows
    for (int i = 0; i < track.length; i++) {
      long yellows = (~track[i] &     // Check ones places' bits (0)
                       track[i] >> 1) // Check two places' bits (1)
                      >> 1;           // if 01 is found, both places will be 1. Right shift for copying
      yellows |= yellows >> 1;
      long carry = (~track[i+1] &     // Check ones places' bits (0)
                        track[i+1] >> 1) // Check two places' bits (1)
                       >> 1;
      yellows = yellows | carry;
      // Insert greens
      track[i] = ~(~track[i] | yellows);
    }
  }
  
  /**
   * Displays the object as a human-readable string. Format not handled.
   * @return A binary representation of the track as a bunch of longs joined together.
   */
  @Override public String toString() {
    StringBuilder a = new StringBuilder();
    for (long t:track) {
      a.append(Long.toBinaryString(t));
    }
    return a.toString();
  }
}
