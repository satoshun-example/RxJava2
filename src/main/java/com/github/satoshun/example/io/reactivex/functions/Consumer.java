package com.github.satoshun.example.io.reactivex.functions;

public interface Consumer<T> {

  void accept(T t) throws Exception;
}
