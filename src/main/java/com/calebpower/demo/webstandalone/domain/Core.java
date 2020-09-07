package com.calebpower.demo.webstandalone.domain;

import com.calebpower.demo.webstandalone.action.FrontEnd;
import com.calebpower.demo.webstandalone.domain.persistent.Config;

/**
 * Demonstration of a stand-alone web application that utilizes a JSON-based
 * configuration file, the SparkJava microframework, a custom console prompter,
 * the FreeMarker template engine, and JUnit testing.
 * 
 * @author Caleb L. Power
 */
public class Core {
  
  private static Clock clock = null; // the clock
  private static Config config = null; // configurations and settings
  private static FrontEnd frontEnd = null; // the front end
  
  /**
   * Entry-point.
   * 
   * @param args the program arguments
   */
  public static void main(String[] args) {
    try {
      config = args.length == 1 ? new Config(args[0]).load() : new Config(); // load the config
    } catch(Exception e) {
      System.err.println("Exception thrown when loading configuration file.");
      if(e.getMessage() != null)
        System.err.printf("Additional information: %1$s\n", e.getMessage());
    }
    
    System.out.println("Launching front end...");
    clock = Clock.build();
    frontEnd = new FrontEnd(config.getSparkPort()); // configure the front end
    (new Thread(frontEnd)).start(); // run the front end in a different thread
    
    // catch SIGTERM
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() {
        try {
          System.out.println("Closing front end...");
          frontEnd.halt();
          clock.stop();
          
          Thread.sleep(1000);
          
          System.out.println("Goodbye!");
          Thread.sleep(200);
        } catch(InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
  }
  
  /**
   * Retrieves the clock.
   * 
   * @return the clock
   */
  public static Clock getClock() {
    return clock;
  }
}
