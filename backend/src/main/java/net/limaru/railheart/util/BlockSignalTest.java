package net.limaru.railheart.util;

import static org.junit.jupiter.api.Assertions.*;


class BlockSignalTest {
  private BlockSignal bs0;  // 1 item in track
  private BlockSignal bs1;  // 2 items in track
  private BlockSignal bs2;  // 3 items in track
  private BlockSignal bse;  // edge case
  
  @org.junit.jupiter.api.BeforeEach
  void setUp() {
    bs0 = new BlockSignal(16);
    bs1 = new BlockSignal(48);
    bs2 = new BlockSignal(72);
    bse = new BlockSignal(64);
  }
  
  @org.junit.jupiter.api.Test
  void addTrain() {
    bs0.addTrain(8);  // normal
    bs0.addTrain(0);  // edge
    bs0.addTrain(16);  // edge
  
    bs1.addTrain(40);  // normal
    bs1.addTrain(0);  // edge
    bs1.addTrain(48);  // edge
  
    bs2.addTrain(40);  // normal
    bs2.addTrain(32);  // edge
    bs2.addTrain(64);  // edge
    
    bse.addTrain(14);  // normal
  }
  
  @org.junit.jupiter.api.Test
  void advanceTrains() {
    bs0.advanceTrains();
    bs1.advanceTrains();
    bs2.advanceTrains();
    bse.advanceTrains();
  
    bs0.advanceTrains();
    bs1.advanceTrains();
    bs2.advanceTrains();
    bse.advanceTrains();  // edge
  }
  
  @org.junit.jupiter.api.Test
  void updateYellows() {
    bs0.updateYellows();
    bs1.updateYellows();
    bs2.updateYellows();
    bse.updateYellows();
  }
  
  @org.junit.jupiter.api.AfterEach
  void tearDown() {
    System.out.println(bs0.toString());
    System.out.println(bs1.toString());
    System.out.println(bs2.toString());
    System.out.println(bse.toString());
  }
}