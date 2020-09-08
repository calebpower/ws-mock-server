/*
 * Copyright (c) 2020 Caleb L. Power. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
