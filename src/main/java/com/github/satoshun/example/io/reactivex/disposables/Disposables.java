package com.github.satoshun.example.io.reactivex.disposables;

import java.util.concurrent.Future;

public class Disposables {

  public static Disposable fromFuture(Future<?> future) {
    return new FutureDisposable(future);
  }
}
