package com.github.satoshun.example.io.reactivex.disposables;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class FutureDisposable extends AtomicReference<Future<?>> implements Disposable {

  public FutureDisposable(Future<?> value) {
    super(value);
  }

  @Override public void dispose() {
    Future<?> future = getAndSet(null);
    if (future != null) {
      future.cancel(true);
    }
  }

  @Override public boolean isDispose() {
    return get() == null;
  }
}
