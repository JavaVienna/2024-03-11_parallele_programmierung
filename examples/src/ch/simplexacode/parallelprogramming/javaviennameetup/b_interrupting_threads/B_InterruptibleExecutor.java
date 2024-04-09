// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.b_interrupting_threads;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class B_InterruptibleExecutor extends AbstractComputation {

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    positionSet.add(180);
  }

  @Override
  public void run() {
    ExecutorService service = Executors.newFixedThreadPool(2);
    service.execute(this::computeLeft);
    service.execute(this::computeRight);

    service.close(); // service.shutdown() prior to Java 19
    try {
      if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
        service.shutdownNow();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      service.shutdownNow();
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
