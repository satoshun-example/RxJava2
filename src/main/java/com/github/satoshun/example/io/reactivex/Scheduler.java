package com.github.satoshun.example.io.reactivex;

import java.util.concurrent.TimeUnit;

public abstract class Scheduler {

  public void scheduleDirect(Runnable run) {
    scheduleDirect(run, 0, TimeUnit.MICROSECONDS);
  }

  private void scheduleDirect(Runnable run, long delay, TimeUnit unit) {
    Worker w = createWorker();
    w.schedule(run, delay, unit);
  }

  public abstract Worker createWorker();


  public static abstract class Worker {

    public void schedule(Runnable run) {
      schedule(run, 0, TimeUnit.SECONDS);
    }

    public abstract void schedule(Runnable run, long delay, TimeUnit unit);
  }
}
