// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.c_executors_and_pools;

import ch.simplexacode.threadvisualizer.AbstractComputation;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class B_ScheduledExecutor extends AbstractComputation {

  @Override
  public void addSeparatorPositions(Set<Integer> positionSet) {
    // no separators needed
  }

  @Override
  public void run() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    //    service.schedule(this::computeRandomIndex, 5, TimeUnit.SECONDS);
    //    service.scheduleWithFixedDelay(this::computeRandomIndex, 5, 1, TimeUnit.SECONDS);
    service.scheduleAtFixedRate(this::computeRandomIndex, 5, 1, TimeUnit.SECONDS);

    //    service.close(); # Do not close for repeating tasks.
    try {
      if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
        service.shutdownNow();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      service.shutdownNow();
    }
  }

  private void computeRandomIndex() {
    int randomIndex = ThreadLocalRandom.current().nextInt(0, getArraySize());
    System.out.format("Computing index %3d ... ", randomIndex);
    computeIndex(randomIndex);
    System.out.println("Done");
  }
}
