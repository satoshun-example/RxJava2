package com.github.satoshun.example.io.reactivex.internal.schedulers;

import com.github.satoshun.example.io.reactivex.Scheduler;

public class NewThreadScheduler extends Scheduler {

  @Override public Worker createWorker() {
    return new NewThreadWorker();
  }
}
