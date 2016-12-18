package com.github.satoshun.example.io.reactivex.internal.operators.observable;

import com.github.satoshun.example.io.reactivex.Observable;
import com.github.satoshun.example.io.reactivex.ObservableSource;
import com.github.satoshun.example.io.reactivex.Observer;
import com.github.satoshun.example.io.reactivex.Scheduler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ObservableObserveOn<T> extends Observable<T> {

  private final ObservableSource<T> source;
  private final Scheduler scheduler;

  public ObservableObserveOn(ObservableSource<T> source, Scheduler scheduler) {
    this.source = source;
    this.scheduler = scheduler;
  }

  @Override public void subscribeActual(Observer<? super T> observer) {
    source.subscribe(new ObserveOnObserver<>(observer, scheduler.createWorker()));
  }


  private static class ObserveOnObserver<T> extends AtomicInteger implements Observer<T>, Runnable {

    private final Observer<? super T> actual;
    private final Scheduler.Worker worker;

    // todo
    private final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();

    private volatile boolean done;
    private Throwable error;

    private ObserveOnObserver(Observer<? super T> actual, Scheduler.Worker worker) {
      this.actual = actual;
      this.worker = worker;
    }

    @Override public void onNext(T t) {
      queue.offer(t);
    }

    @Override public void onError(Throwable t) {
      done = true;
      error = t;
      schedule();
    }

    @Override public void onComplete() {
      done = true;
      schedule();
    }

    private void schedule() {
      if (getAndIncrement() == 0) {
        worker.schedule(this);
      }
    }

    @Override public void run() {
      drainNormal();
    }

    private void drainNormal() {
      final LinkedBlockingQueue<T> q = queue;
      Observer<? super T> a = this.actual;

      for (; ; ) {
        if (checkTerminated(done, q.isEmpty(), a)) {
          break;
        }
        for (;;) {
          T v = q.poll();
          boolean empty = v == null;
          if (checkTerminated(done, empty, a)) {
            break;
          }
          if (empty) {
            break;
          }
          a.onNext(v);
        }
      }
    }

    private boolean checkTerminated(boolean done, boolean empty, Observer<? super T> a) {
      if (done) {
        Throwable e = error;
        if (empty) {
          if (e != null) {
            a.onError(e);
          } else {
            a.onComplete();
          }
          return true;
        } else if (e != null) {
          queue.clear();
          a.onError(e);
          return true;
        }
      }
      return false;
    }
  }
}
