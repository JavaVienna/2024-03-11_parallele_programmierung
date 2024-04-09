// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.a_introduction;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class A_FirstSteps extends AbstractComputation {

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    positionSet.add(90);
    positionSet.add(180);
    positionSet.add(270);
  }

  @Override
  public void run() {
    computeIndex(100);
    computeIndex(200);
    computeIndex(300);
  }
}
