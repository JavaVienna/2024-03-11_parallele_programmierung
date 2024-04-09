// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.a_introduction;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class B_SingleThread extends AbstractComputation {

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    positionSet.add(90);
    positionSet.add(180);
    positionSet.add(270);
  }

  @Override
  public void run() {
    for (int i = 0; i < getArraySize(); i++) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }
      computeIndex(i);
    }
  }
}
