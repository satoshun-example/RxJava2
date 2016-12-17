package com.github.satoshun.example.io.reactivex;

import com.github.satoshun.example.io.reactivex.observers.TestObserver;
import org.junit.Test;

public class ObservableTest {

  @Test public void just() throws Exception {
    TestObserver<String> observer = Observable.just("test")
        .test().await();
    observer.assertValues("test");
    observer.assertComplete();
  }
}