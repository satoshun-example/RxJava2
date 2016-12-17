package com.github.satoshun.example.io.reactivex;


import com.github.satoshun.example.io.reactivex.internal.operators.observable.ObservableJust;
import com.github.satoshun.example.io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import com.github.satoshun.example.io.reactivex.observers.TestObserver;

public abstract class Observable<T> implements ObservableSource<T> {

  public static <T> Observable<T> just(T item) {
    return new ObservableJust<>(item);
  }

  public Observable<T> subscribeOn(Scheduler scheduler) {
    return new ObservableSubscribeOn<>(this, scheduler);
  }

  public TestObserver<T> test() {
    TestObserver<T> observer = new TestObserver<>();
    subscribe(observer);
    return observer;
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
