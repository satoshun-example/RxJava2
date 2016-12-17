package com.github.satoshun.example.io.reactivex;

import org.junit.Test;

public class ObservableTest {

  @Test public void just() throws Exception {
    // todo use test observer
    Observable.just("test")
        .subscribe();
  }
}