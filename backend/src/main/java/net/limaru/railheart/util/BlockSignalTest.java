package net.limaru.railheart.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BlockSignalTest {
  BlockSignal b;
  
  @BeforeEach
  void setUp() {
    b = new BlockSignal(319);  // over-testing is good
  }
  
  @AfterEach
  void tearDown() {
  }
  
  @Test
  void fromString() {
    b = new BlockSignal(33);
    b.addTrain(0);
    BlockSignal c = BlockSignal.fromString("000000011");
    assertEquals(b.toString(), c.toString());
  }
  
  @Test
  void addTrainAt0() {
    b.addTrain(0);
    assertEquals("11", b.toString());
    b.addTrain(30);
    assertEquals("11010000000000000000000000000000000000000000000000000000000011", b.toString());
    b.addTrain(31);
    assertEquals("1111010000000000000000000000000000000000000000000000000000000011", b.toString());
    b.addTrain(32);
    assertEquals("111111010000000000000000000000000000000000000000000000000000000011", b.toString());
  }
  
  @Test
  void advanceTrain() {
    b.addTrain(0);
    b.advanceTrain(0);
    assertArrayEquals(new long[] {0b1101L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L}, b.getTrack());
    b.addTrain(31);
    b.advanceTrain(31);
    assertArrayEquals(new long[] {0b0100_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_1101L, 2L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L}, b.getTrack());
  }
  
  @Test
  void updateYellows() {
  }
  
  @Test
  void updateGreens() {
  }
}