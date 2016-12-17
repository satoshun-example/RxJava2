package com.github.satoshun.example.io.reactivex.internal.operators.observable;

import com.github.satoshun.example.io.reactivex.Observable;
import com.github.satoshun.example.io.reactivex.Observer;

import java.util.concurrent.atomic.AtomicInteger;

public class ObservableJust<T> extends Observable<T> {

  private final T value;

  public ObservableJust(T value) {
    this.value = value;
  }

  @Override public void subscribeActual(Observer<? super T> observer) {
    ScalarDisposable<T> disposable = new ScalarDisposable<>(observer, value);
    disposable.run();
  }


  private static class ScalarDisposable<T> extends AtomicInteger implements Runnable {

    private static final int START = 0;
    private static final int ON_NEXT = 2;
    private static final int ON_COMPLETE = 3;

    private final Observer<? super T> actual;
    private final T value;

    private ScalarDisposable(Observer<? super T> actual, T value) {
      this.actual = actual;
      this.value = value;
    }

    @Override public void run() {
      if (compareAndSet(START, ON_NEXT)) {
        actual.onNext(value);
        if (get() == ON_NEXT) {
          lazySet(ON_COMPLETE);
          actual.onComplete();
        }
      }
    }
  }
}
