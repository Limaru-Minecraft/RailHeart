package net.limaru.railheart.util;

import org.jetbrains.annotations.NotNull;


public class BlockSignal implements Signal {
  // This file uses bitwise programming.
  public long[] track;
  
  /**
   * Initialises the object
   * @param signalCount Total number of signals
   */
  public BlockSignal (int signalCount) {
    this.track = new long[(signalCount + 31) / 32];  // Split track up into 32-signal sections due to Java's long limit
  }
  
  public static BlockSignal fromString(String binaryString) {
    BlockSignal bs = new BlockSignal(binaryString.length() / 2);
    long[] longArray = new long[binaryString.length() / 2];
    for (int i = 0; i < binaryString.length(); i += 64) {
      String s = binaryString.substring(i, Math.min(binaryString.length(), i+64));
      longArray[binaryString.length() / 2 - i - 1] = Long.parseLong(s, 2);
    }
    bs.setTrack(longArray);
    return bs;
  }

  public void setTrack (long @NotNull [] track) {
    this.track = track;
  }

  @Override
  public long @NotNull [] getTrack () {
    return this.track;
  }
  
  @Override
  public void addTrain (int signalPos) {
    // Get section
    int i = signalPos >> 5;
    // Get position in section
    signalPos &= 31;
    // Insert train onto track
    this.track[i] |= 0b11L << (signalPos << 1L);
    // Update yellows
    updateYellows();
  }
  
  @Override
  public short getSignal (int signalPos) {
    // Get section
    int i = signalPos >> 5;
    // Get position in section
    signalPos &= 31;
    // Insert train onto track
    return (short) (this.track[i] >> (signalPos << 1L) & 0b11L);
  }
  
  @Override public void removeTrain(int signalPos) {
    // Get section
    int i = signalPos >> 5;
    // Get position in section
    signalPos &= 31;
    // Remove train from track
    this.track[i] = ~(~this.track[i] | 0b11L << (signalPos << 1L));
    // Update yellows
    updateYellows();
  }
  
  @Override
  public void advanceTrain (int signalPos) {
    // Make current position yellow (xx11 -> xx01).
    this.track[signalPos >> 5] ^= 0b10L << ((signalPos & 31) << 1L);
    // Make next position red (00xx -> 11xx)
    this.track[signalPos >> 5] |= 0b11L << ((++signalPos & 31) << 1L);
  }
  
  /**
   * Updates all yellow signal positions
   */
  public void updateYellows () {
    // Test for reds
    for (int i = 0; i < track.length; i++) {
      long reds = (track[i] &     // Check ones places' bits
                   track[i] >> 1) // Check two places' bits
                     >> 2;        // if both are 1, the ones place will be a 1. Right shift by 2 for insertion
      long carry = i < track.length - 1 ?
                   (track[i+1] &     // Check ones places' bits
                    track[i+1] >> 1) // Check two places' bits
                      << 62 : 0;     // Left shift by 62 for carry-over
      reds |= carry;
      // Insert yellows
      track[i] |= reds;
    }
  }
  
  /**
   * Updates all green signal positions
   */
  public void updateGreens () {
    /*
      ~(~track[i] >> 3  &  ~track[i] >> 1  &  track[i]);
    // 0101->1010->xxx1   xx01->xx10->xxx1   xxx1 = 1
    // 1001->0110->xxx0   xx01->xx10->xxx1   xxx1 = 0
    // 0110->1001->xxx1   xx10->xx01->xxx0   xxx0 = 0
    // 0100->1011->xxx1   xx00->xx11->xxx1   xxx0 = 0
     */
    // Test for yellows
    for (int i = 0; i < track.length; i++) { 
      long carry = i < track.length - 1 ?
                   ~(~track[i] >> 3 & ~track[i] >> 1 & track[i])
                   << 60 : 0;
      long yellows = ~(~track[i] >> 3 & ~track[i] >> 1 & track[i]);
      yellows |= carry;
      track[i] = ~(~track[i] | yellows);
    }
  }
  
  @Override public @NotNull String toString () {
    StringBuilder a = new StringBuilder();
    for (int i = track.length-1; i >= 0; i--) {
      a.append(Long.toBinaryString(track[i]));
    }
    return a.toString().replaceAll("^0+", "");
  }
}
