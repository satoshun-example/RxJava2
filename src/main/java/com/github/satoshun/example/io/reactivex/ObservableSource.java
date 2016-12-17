package com.github.satoshun.example.io.reactivex;

public interface ObservableSource<T> {

  void subscribe(Observer<? super T> observer);
}
