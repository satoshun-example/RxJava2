package com.github.satoshun.example.io.reactivex;

import com.github.satoshun.example.io.reactivex.disposables.Disposable;

public interface Observer<T> {

  void onSubscribe(Disposable disposable);

  void onNext(T t);

  void onError(Throwable t);

  void onComplete();
}
