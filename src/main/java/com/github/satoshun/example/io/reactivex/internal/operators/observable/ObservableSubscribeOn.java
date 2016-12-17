package com.github.satoshun.example.io.reactivex.internal.operators.observable;

import com.github.satoshun.example.io.reactivex.Observable;
import com.github.satoshun.example.io.reactivex.ObservableSource;
import com.github.satoshun.example.io.reactivex.Observer;
import com.github.satoshun.example.io.reactivex.Scheduler;

public class ObservableSubscribeOn<T> extends Observable<T> {

  private final ObservableSource<T> source;
  private final Scheduler scheduler;

  public ObservableSubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
    this.source = source;
    this.scheduler = scheduler;
  }

  @Override public void subscribeActual(Observer<? super T> observer) {
    scheduler.scheduleDirect(() -> source.subscribe(observer));
  }
}
