package com.github.satoshun.example.io.reactivex.internal.operators.observable;

import com.github.satoshun.example.io.reactivex.Observable;
import com.github.satoshun.example.io.reactivex.ObservableSource;
import com.github.satoshun.example.io.reactivex.Observer;
import com.github.satoshun.example.io.reactivex.Scheduler;
import com.github.satoshun.example.io.reactivex.disposables.Disposable;

public class ObservableSubscribeOn<T> extends Observable<T> {

  private final ObservableSource<T> source;
  private final Scheduler scheduler;

  public ObservableSubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
    this.source = source;
    this.scheduler = scheduler;
  }

  @Override public void subscribeActual(Observer<? super T> observer) {
    SubscribeOnObserver<? super T> wrap = new SubscribeOnObserver<>(observer);
    Disposable disposable = scheduler.scheduleDirect(() -> source.subscribe(wrap));
    wrap.setDisposable(disposable);
  }

  private static final class SubscribeOnObserver<T> implements Observer<T>, Disposable {

    private final Observer<? super T> observer;

    private Disposable schedulerDisposable;
    private Disposable parent;

    private SubscribeOnObserver(Observer<? super T> observer) {
      this.observer = observer;
    }

    @Override public void onSubscribe(Disposable disposable) {
      parent = disposable;
    }

    @Override public void onNext(T t) {
      observer.onNext(t);
    }

    @Override public void onError(Throwable t) {
      observer.onError(t);
    }

    @Override public void onComplete() {
      observer.onComplete();
    }

    @Override public void dispose() {
      schedulerDisposable.dispose();
      parent.dispose();
    }

    @Override public boolean isDispose() {
      return schedulerDisposable.isDispose();
    }

    void setDisposable(Disposable disposable) {
      this.schedulerDisposable = disposable;
    }
  }
}
