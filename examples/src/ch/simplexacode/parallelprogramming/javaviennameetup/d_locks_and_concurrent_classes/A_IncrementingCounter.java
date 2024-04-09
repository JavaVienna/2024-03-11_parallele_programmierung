// Copyright © 2019–2024 Christian Heitzmann, SimplexaCode AG
// www.simplexacode.ch
// Version 2024.03

package ch.simplexacode.parallelprogramming.javaviennameetup.d_locks_and_concurrent_classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "java:S101"}) // used and named for demonstration purposes
public final class A_IncrementingCounter {

  private static final long LOOP_COUNT_MAX = 100_000_000;

  private static final int THREAD_COUNT = 8;

  private long counter = 0;

  public static void main(String[] args) {
    new A_IncrementingCounter().run();
  }

  private void run() {
    ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);

    for (int threadNumber = 1; threadNumber <= THREAD_COUNT; threadNumber++) {
      service.execute(this::incrementCounterInLoop);
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

    System.out.format("counter = %,d%n", counter);
  }

  private void incrementCounterInLoop() {
    for (long i = 1; i <= LOOP_COUNT_MAX; i++) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }

      counter++;
    }
  }
}
