package com.github.satoshun.example.io.reactivex.observers;

import com.github.satoshun.example.io.reactivex.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestObserver<T> implements Observer<T> {

  private final List<T> values;
  private final List<Throwable> errors;
  private final CountDownLatch done;
  private long completions;

  public TestObserver() {
    this.values = new ArrayList<>();
    this.errors = new ArrayList<>();
    this.done = new CountDownLatch(1);
  }

  public TestObserver<T> await() throws InterruptedException {
    if (done.getCount() == 0) {
      return this;
    }
    done.await();
    return this;
  }

  public TestObserver<T> assertValues(T... t) {
    if (values.size() != t.length) {
      throw fail("Value count differs");
    }
    for (int i = 0; i < values.size();i++) {
      T value = values.get(i);
      T item = t[i];
      if (!value.equals(item)) {
        throw fail("Values at position " + i + " differ");
      }
    }
    return this;
  }

  public TestObserver<T> assertComplete() {
    if (completions == 0) {
      throw fail("Not completed");
    }
    if (completions > 1) {
      throw fail("Multiple Completions:" + completions);
    }
    return this;
  }

  private AssertionError fail(String message) {
    // fixme
    return new AssertionError(message);
  }

  @Override public void onNext(T t) {
    values.add(t);
  }

  @Override public void onError(Throwable t) {
    try {
      errors.add(t);
    } finally {
      done.countDown();
    }
  }

  @Override public void onComplete() {
    try {
      completions++;
    } finally {
      done.countDown();
    }
  }
}
