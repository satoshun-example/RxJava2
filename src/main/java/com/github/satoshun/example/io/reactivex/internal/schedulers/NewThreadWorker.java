package com.github.satoshun.example.io.reactivex.internal.schedulers;

import com.github.satoshun.example.io.reactivex.Scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NewThreadWorker extends Scheduler.Worker {

  private final ScheduledExecutorService executor;

  public NewThreadWorker() {
    this.executor = Executors.newScheduledThreadPool(1);
  }

  @Override public void schedule(Runnable run, long delay, TimeUnit unit) {
    ScheduledFuture<?> f = executor.schedule(run, delay, unit);
  }
}
