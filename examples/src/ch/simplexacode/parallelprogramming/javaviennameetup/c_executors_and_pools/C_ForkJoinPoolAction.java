// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.c_executors_and_pools;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.concurrent.RecursiveAction;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class C_ForkJoinPoolAction extends RecursiveAction {

  private final int indexMinInclusive;

  private final int indexMaxExclusive;

  private final transient AbstractComputation computation;

  public C_ForkJoinPoolAction(
      int indexMinInclusive, int indexMaxExclusive, AbstractComputation computation) {
    this.indexMinInclusive = indexMinInclusive;
    this.indexMaxExclusive = indexMaxExclusive;
    this.computation = computation;
  }

  @Override
  protected void compute() {
    if (Thread.currentThread().isInterrupted()) {
      return;
    }

    if (indexMinInclusive == (indexMaxExclusive - 1)) { // base case
      computation.computeIndex(indexMinInclusive);
    } else if (indexMinInclusive < (indexMaxExclusive - 1)) { // recursive case
      int indexMid = (indexMinInclusive + indexMaxExclusive) / 2;
      invokeAll(
          new C_ForkJoinPoolAction(indexMinInclusive, indexMid, computation),
          new C_ForkJoinPoolAction(indexMid, indexMaxExclusive, computation));
    } // Do nothing if indexMinInclusive ≥ indexMaxExclusive.
  }
}
