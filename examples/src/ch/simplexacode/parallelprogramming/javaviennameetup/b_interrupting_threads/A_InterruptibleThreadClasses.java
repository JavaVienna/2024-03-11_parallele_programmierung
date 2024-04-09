// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.b_interrupting_threads;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class A_InterruptibleThreadClasses extends AbstractComputation {

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    positionSet.add(180);
  }

  @Override
  public void run() {
    Thread leftThread = new Thread(this::computeLeft);
    Thread rightThread = new Thread(this::computeRight);
    leftThread.start();
    rightThread.start();

    try {
      leftThread.join();
      rightThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      leftThread.interrupt();
      rightThread.interrupt();
      return;
    }

    System.out.println("Finished");
  }

  private void computeLeft() {
    for (int i = 0; i < getArraySize() / 2; i++) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }
      computeIndex(i);
    }
  }

  private void computeRight() {
    for (int i = getArraySize() / 2; i < getArraySize(); i++) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }
      computeIndex(i);
    }
  }
}
