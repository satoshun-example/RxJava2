package com.github.satoshun.example.io.reactivex;


import com.github.satoshun.example.io.reactivex.internal.operators.observable.ObservableJust;

public abstract class Observable<T> {
  public static <T> Observable<T> just(T item) {
    return new ObservableJust<>(item);
  }

  public void subscribe() {
    subscribe(new Observer<T>() {
      @Override public void onNext(T t) {
      }

      @Override public void onError(Throwable t) {
      }

      @Override public void onComplete() {
      }
    });
  }

  public void subscribe(Observer<? super T> observer) {
    subscribeActual(observer);
  }

  public abstract void subscribeActual(Observer<? super T> observer);
}
