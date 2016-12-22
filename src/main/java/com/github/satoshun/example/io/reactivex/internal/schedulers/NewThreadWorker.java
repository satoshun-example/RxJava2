package com.github.satoshun.example.io.reactivex.internal.schedulers;

import com.github.satoshun.example.io.reactivex.Scheduler;
import com.github.satoshun.example.io.reactivex.disposables.Disposable;
import com.github.satoshun.example.io.reactivex.disposables.Disposables;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NewThreadWorker extends Scheduler.Worker {

  private final ScheduledExecutorService executor;

  public NewThreadWorker() {
    this.executor = Executors.newScheduledThreadPool(1);
  }

  @Override public Disposable schedule(Runnable run, long delay, TimeUnit unit) {
    ScheduledFuture<?> f = executor.schedule(run, delay, unit);
    return Disposables.fromFuture(f);
  }

  @Override public void dispose() {
    executor.shutdownNow();
  }

  @Override public boolean isDispose() {
    return executor.isShutdown();
  }
}
