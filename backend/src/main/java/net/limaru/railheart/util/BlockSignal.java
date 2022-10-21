package net.limaru.railheart.util;

public class BlockSignal {
  // This file uses bitwise programming.
  public long[] track;
  
  public BlockSignal(int signalCount) {
    this.track = new long[signalCount>>5+1];  // Split track up into 32-signal sections due to Java's long limit
  }
  
  public void addTrain(int signalPos) {
    // Get section to insert to
    int i = signalPos / 32;
    // Get position in section
    signalPos %= 32;
    // Insert train onto track
    this.track[i] |= 2L << (signalPos << 1L);
  }
  
  public void advanceTrains() {
    long carry = 0L; // Initialise carry
    for (int i = 0; i < track.length; i++) {
      // Register carry-over from previous section
      track[i] |= carry;
      // Get this section's carry-over for the next section
      carry = (track[i] & -4611686018427387904L) >>> 62;
      // Main advance
      track[i] <<= 2;
    }
  }
  public void updateYellows() {
    // Test for reds
    long[] reds = track.clone();  // copy red signals on track
    for (int i = 0; i < track.length; i++) {
      reds[i] &= 0b10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010L;
    }
    // Offset tester array by 2 to check whether the previous signal is also red
    for (int i = 0; i < track.length-1; i++) {
      reds[i] >>= 2;
      reds[i] |= (reds[i+1] & 0b10) << 62;
    } reds[track.length-1] >>= 2;
    // Remove setter positions that are red
    for (int i = 0; i < track.length; i++) {
      reds[i] ^= track[i];
    }
    // Insert yellows
    for (int i = 0; i < track.length-1; i++) {
      reds[i] >>= 1;
      reds[i] |= (reds[i+1] & 1) << 63;
      track[i] |= reds[i];
    } reds[track.length-1] >>= 1;
    track[track.length-1] |= reds[track.length-1];
  }
  
  @Override public String toString() {
    StringBuilder a = new StringBuilder();
    for (long t:track) {
      a.append(Long.toBinaryString(t));
    }
    return a.toString();
  }
}
