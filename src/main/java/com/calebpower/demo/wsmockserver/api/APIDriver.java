package com.calebpower.demo.wsmockserver.api;

import static spark.Spark.init;
import static spark.Spark.port;
import static spark.Spark.stop;
import static spark.Spark.webSocket;

/**
 * Front end view; manages all pages and directs traffic to those pages.
 * 
 * @author Caleb L. Power
 */
public class APIDriver implements Runnable {
  
  private int port; // the port that the front end should run on
  private String wsRoute; // the route that the WebSocket should open on
  
  /**
   * Opens the specified external port so as to launch the front end.
   * 
   * @param port the port by which the front end will be accessible
   * @param wsRoute the route that the WebSocket should open on
   */
  public APIDriver(int port, String wsRoute) {
    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
    System.out.printf("Launching websocket at ws://127.0.0.1:%1$d%2$s\n", port, wsRoute);
    this.port = port;
    this.wsRoute = wsRoute;
  }

  /**
   * Runs the front end in a separate thread so that it can be halted externally.
   */
  @Override public void run() {
    webSocket(wsRoute, WebSocketHandler.class); // initialize WebSocket
    port(port);
    init();
  }
  
  /**
   * Stops the web server.
   */
  public void halt() {
    stop();
  }
  
}
