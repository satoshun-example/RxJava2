package com.github.satoshun.example.io.reactivex;

import com.github.satoshun.example.io.reactivex.observers.TestObserver;
import com.github.satoshun.example.io.reactivex.schedulers.Schedulers;
import org.junit.Test;

public class ObservableTest {

  @Test public void just() throws Exception {
    TestObserver<String> observer = Observable.just("test")
        .test().await();
    observer.assertValues("test");
    observer.assertComplete();
  }

  @Test
  public void subscribeOn() throws Exception {
    TestObserver<String> observer = Observable.just("test")
        .subscribeOn(Schedulers.newThread())
        .test().await();
    observer.assertValues("test");
    observer.assertComplete();
  }
}
