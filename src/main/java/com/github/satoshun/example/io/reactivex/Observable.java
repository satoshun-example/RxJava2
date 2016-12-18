package com.github.satoshun.example.io.reactivex;


import com.github.satoshun.example.io.reactivex.disposables.Disposable;
import com.github.satoshun.example.io.reactivex.functions.Action;
import com.github.satoshun.example.io.reactivex.functions.Consumer;
import com.github.satoshun.example.io.reactivex.internal.LambdaObserver;
import com.github.satoshun.example.io.reactivex.internal.operators.observable.ObservableJust;
import com.github.satoshun.example.io.reactivex.internal.operators.observable.ObservableObserveOn;
import com.github.satoshun.example.io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import com.github.satoshun.example.io.reactivex.observers.TestObserver;

public abstract class Observable<T> implements ObservableSource<T> {

  public static <T> Observable<T> just(T item) {
    return new ObservableJust<>(item);
  }

  public Observable<T> subscribeOn(Scheduler scheduler) {
    return new ObservableSubscribeOn<>(this, scheduler);
  }

  public Observable<T> observeOn(Scheduler scheduler) {
    return new ObservableObserveOn<>(this, scheduler);
  }

  public TestObserver<T> test() {
    TestObserver<T> observer = new TestObserver<>();
    subscribe(observer);
    return observer;
  }

  public Disposable subscribe() {
    return subscribe(t -> {}, e -> {}, () -> {}, d -> {});
  }

  public Disposable subscribe(Consumer<? super T> onNext,
                              Consumer<? super Throwable> onError,
                              Action onComplete,
                              Consumer<? super Disposable> onSubscribe) {
    LambdaObserver<? super T> observer = new LambdaObserver<>(onNext, onError, onComplete, onSubscribe);
    subscribe(observer);
    return observer;
  }

  public void subscribe(Observer<? super T> observer) {
    subscribeActual(observer);
  }

  public abstract void subscribeActual(Observer<? super T> observer);
}
