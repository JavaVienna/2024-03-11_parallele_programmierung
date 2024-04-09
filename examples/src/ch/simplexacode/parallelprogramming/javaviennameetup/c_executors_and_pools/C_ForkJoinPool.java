// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.c_executors_and_pools;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class C_ForkJoinPool extends AbstractComputation {

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    positionSet.add(90);
    positionSet.add(180);
    positionSet.add(270);
  }

  @Override
  public void run() {
    ForkJoinPool pool = new ForkJoinPool(getThreadCount());
    pool.execute(new C_ForkJoinPoolAction(0, getArraySize(), this));

    pool.close();
    try {
      if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
        pool.shutdownNow();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      pool.shutdownNow();
    }
  }
}
