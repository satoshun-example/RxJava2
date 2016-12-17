package com.github.satoshun.example.io.reactivex;

public interface Observer<T> {
  void onNext(T t);

  void onError(Throwable t);

  void onComplete();

  // todo impl onSubscribe
}
