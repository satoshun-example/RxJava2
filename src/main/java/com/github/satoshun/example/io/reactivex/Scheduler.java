package com.github.satoshun.example.io.reactivex;

import com.github.satoshun.example.io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public abstract class Scheduler {

  public Disposable scheduleDirect(Runnable run) {
    return scheduleDirect(run, 0, TimeUnit.MICROSECONDS);
  }

  private Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
    Worker w = createWorker();
    return w.schedule(run, delay, unit);
  }

  public abstract Worker createWorker();


  public static abstract class Worker implements Disposable {

    public Disposable schedule(Runnable run) {
      return schedule(run, 0, TimeUnit.SECONDS);
    }

    public abstract Disposable schedule(Runnable run, long delay, TimeUnit unit);
  }
}
