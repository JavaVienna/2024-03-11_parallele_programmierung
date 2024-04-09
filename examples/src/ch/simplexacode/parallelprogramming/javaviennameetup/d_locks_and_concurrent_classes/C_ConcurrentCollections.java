// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.d_locks_and_concurrent_classes;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class C_ConcurrentCollections {

  private static final long LOOP_COUNT_MAX = 50_000_000;

  private static final int THREAD_COUNT = 8;

  // only updated but never queried for demonstration purposes
  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
//  private final SortedSet<Short> set = Collections.synchronizedSortedSet(new TreeSet<>());
    private final SortedSet<Short> set = new ConcurrentSkipListSet<>();

  public static void main(String[] args) {
    new C_ConcurrentCollections().run();
  }

  public void run() {
    ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);

    long startTimeNanos = System.nanoTime();

    for (int threadNumber = 1; threadNumber <= THREAD_COUNT; threadNumber++) {
      service.execute(this::addElementsToSet);
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

    long endTimeNanos = System.nanoTime();

    System.out.println("Finished");
    System.out.format("Time: %.2f s", (endTimeNanos - startTimeNanos) / 1E9);
  }

  private void addElementsToSet() {
    for (long i = 1; i < LOOP_COUNT_MAX / THREAD_COUNT; i++) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }

      set.add((short) ThreadLocalRandom.current().nextInt(Short.MAX_VALUE + 1));
    }
  }
}
