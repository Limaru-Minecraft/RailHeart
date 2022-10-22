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
    // Clear yellow signal
    this.track[i] ^= 0b01L << (signalPos << 1L);
    // Insert train onto track
    this.track[i] |= 0b10L << (signalPos << 1L);
  }
  
//  public void advanceTrains() {
//    long carry = 0L; // Initialise carry
//    for (int i = 0; i < track.length; i++) {
//      // Register carry-over from previous section
//      track[i] |= carry;
//      // Get this section's carry-over for the next section
//      carry = (track[i] & -4611686018427387904L) >>> 62;
//      // Main advance
//      track[i] <<= 2;
//    }
//  }
  
  /**
   * Advances a train by 1 signal
   * @param signalPos Current signal position
   */
  public void advanceTrain(int signalPos) {
    // Make current position yellow (xx10 -> xx01).
    this.track[signalPos >> 5] ^= 0b11L << ((signalPos & 31) << 1L);
    // Make next position red (00xx -> 10xx)
    this.track[signalPos >> 5] |= 0b10L << ((++signalPos & 31) << 1L);
  }
  
  /**
   * Updates all yellow signal positions
   */
  public void updateYellows() {
    // Test for reds
    long[] clone = track.clone();  // copy red signals on track
    for (int i = 0; i < track.length; i++) {
      clone[i] &= 0b10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010L;
    }
    // Offset tester array by 2 to check whether the previous signal is also red
    for (int i = 0; i < track.length-1; i++) {
      clone[i] >>= 2;
      clone[i] |= (clone[i+1] & 0b10) << 62;
    } clone[track.length-1] >>= 2;
    // Remove setter positions that are red
    for (int i = 0; i < track.length; i++) {
      clone[i] ^= track[i];
    }
    // Insert yellows
    for (int i = 0; i < track.length-1; i++) {
      clone[i] >>= 1;
      clone[i] |= (clone[i+1] & 1) << 63;
      track[i] |= clone[i];
    } clone[track.length-1] >>= 1;
    track[track.length-1] |= clone[track.length-1];
  }
  
  /**
   * Updates all green signal positions
   */
  public void updateGreens() {
  
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
