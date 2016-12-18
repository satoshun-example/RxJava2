package com.github.satoshun.example.io.reactivex.internal;

import com.github.satoshun.example.io.reactivex.Observer;
import com.github.satoshun.example.io.reactivex.disposables.Disposable;
import com.github.satoshun.example.io.reactivex.functions.Action;
import com.github.satoshun.example.io.reactivex.functions.Consumer;

import java.util.concurrent.atomic.AtomicReference;

public class LambdaObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {

  private final Consumer<? super T> onNext;
  private final Consumer<? super Throwable> onError;
  private final Consumer<? super Disposable> onSubscribe;
  private final Action onComplete;

  public LambdaObserver(Consumer<? super T> onNext,
                        Consumer<? super Throwable> onError,
                        Action onComplete,
                        Consumer<? super Disposable> onSubscribe) {
    this.onNext = onNext;
    this.onError = onError;
    this.onSubscribe = onSubscribe;
    this.onComplete = onComplete;
  }

  @Override public void onSubscribe(Disposable disposable) {
    set(disposable);
    try {
      onSubscribe.accept(disposable);
    } catch (Exception e) {
      onError(e);
    }
  }

  @Override public void onNext(T t) {
    if (isDispose()) {
      return;
    }
    try {
      onNext.accept(t);
    } catch (Exception e) {
      onError(e);
    }
  }

  @Override public void onError(Throwable t) {
    if (isDispose()) {
      return;
    }
    try {
      onError.accept(t);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override public void onComplete() {
    if (isDispose()) {
      return;
    }
    try {
      onComplete.run();
    } catch (Exception e) {
      onError(e);
    }
  }

  @Override public void dispose() {
    Disposable disposable = getAndSet(null);
    if (disposable != null) {
      disposable.dispose();
    }
  }

  @Override public boolean isDispose() {
    return get() == null;
  }
}
