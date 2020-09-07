package com.calebpower.demo.webstandalone.domain;

import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import com.calebpower.demo.webstandalone.action.WebSocketHandler;

/**
 * Just a clock that counts from 0 to 999.
 * 
 * @author Caleb L. Power
 */
public class Clock implements Runnable {
  
  private AtomicInteger counter = new AtomicInteger();
  private Thread thread = null;
  
  private Clock() { }
  
  /**
   * Builds and starts the clock.
   * 
   * @return the clock, already running
   */
  public static Clock build() {
    Clock clock = new Clock();
    clock.thread = new Thread(clock);
    clock.thread.setDaemon(true);
    clock.thread.start();
    return clock;
  }
  
  /**
   * Sets the clock.
   * 
   * @param time a number from 0 to 999
   */
  public void set(int time) {
    counter.set(time < 0 ? 0 : time % 1000);
    broadcast();
  }
  
  private void broadcast() {
    JSONObject json = new JSONObject()
        .put("time", counter.get());
    WebSocketHandler.broadcast(json);
  }
  
  /**
   * Stops the clock.
   */
  public void stop() {
    if(thread != null) thread.interrupt();
  }
  
  @Override public void run() {
    try {
      while(!thread.isInterrupted()) {
        long time = System.currentTimeMillis();
        do {
          Thread.sleep(100L);
        } while(System.currentTimeMillis() - 1000L < time);
        if(counter.incrementAndGet() == 1000) counter.set(0);
        broadcast();
      }
    } catch(InterruptedException e) { }
  }
  
}
