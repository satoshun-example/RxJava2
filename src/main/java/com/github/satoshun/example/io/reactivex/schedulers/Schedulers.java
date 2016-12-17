package com.github.satoshun.example.io.reactivex.schedulers;

import com.github.satoshun.example.io.reactivex.Scheduler;
import com.github.satoshun.example.io.reactivex.internal.schedulers.NewThreadScheduler;

public final class Schedulers {

  private static final Scheduler NEW_THREAD;

  static {
    NEW_THREAD = new NewThreadScheduler();
  }

  public static Scheduler newThread() {
    return NEW_THREAD;
  }
}
