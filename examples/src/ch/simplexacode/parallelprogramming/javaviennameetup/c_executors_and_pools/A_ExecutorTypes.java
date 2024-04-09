// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.c_executors_and_pools;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class A_ExecutorTypes extends AbstractComputation {

  private static final int SUBARRAY_COUNT = 8;

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    positionSet.add(45);
    positionSet.add(90);
    positionSet.add(135);
    positionSet.add(180);
    positionSet.add(225);
    positionSet.add(270);
    positionSet.add(315);
  }

  @Override
  public void run() {
    //    ExecutorService service = Executors.newSingleThreadExecutor();
    //    ExecutorService service = Executors.newFixedThreadPool(2);
    //    ExecutorService service = Executors.newFixedThreadPool(getThreadCount());
    //    ExecutorService service = Executors.newCachedThreadPool();
    ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

    for (int subarrayNumber = 1; subarrayNumber <= SUBARRAY_COUNT; subarrayNumber++) {
      int indexMinInclusive = getArraySize() * (subarrayNumber - 1) / SUBARRAY_COUNT;
      int indexMaxExclusive = getArraySize() * subarrayNumber / SUBARRAY_COUNT;
      //      try {
      //        Thread.sleep(1000);
      //      } catch (InterruptedException e) {
      //        Thread.currentThread().interrupt();
      //        service.shutdownNow();
      //        return;
      //      }
      service.submit(() -> computeSubarray(indexMinInclusive, indexMaxExclusive));
    }

    service.close();
    try {
      if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
        service.shutdownNow();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      service.shutdownNow();
    }
  }

  private void computeSubarray(int indexMinInclusive, int indexMaxExclusive) {
    System.out.format("Computing subarray [%3d, %3d[ ...%n", indexMinInclusive, indexMaxExclusive);
    for (int i = indexMinInclusive; i < indexMaxExclusive; i++) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }
      computeIndex(i);
    }
  }
}
