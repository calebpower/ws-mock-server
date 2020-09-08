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
package com.calebpower.demo.wsmockserver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.calebpower.demo.wsmockserver.api.APIDriver;
import com.calebpower.demo.wsmockserver.ui.TerminalUI;

/**
 * Demonstration of a stand-alone web application that utilizes a JSON-based
 * configuration file, the SparkJava microframework, a custom console prompter,
 * the FreeMarker template engine, and JUnit testing.
 * 
 * @author Caleb L. Power
 */
public class WSMockServer {
  
  private static final int DEFAULT_PORT = 5698;
  private static final String DEFAULT_ROUTE = "/";
  private static final String PORT_PARAM_LONG = "port";
  private static final String PORT_PARAM_SHORT = "p";
  private static final String ROUTE_PARAM_LONG = "route";
  private static final String ROUTE_PARAM_SHORT = "r";
  
  private static APIDriver aPIDriver = null; // the front end
  
  /**
   * Entry-point.
   * 
   * @param args the program arguments
   */
  public static void main(String[] args) {
    try {
      Options options = new Options();
      options.addOption(PORT_PARAM_SHORT, PORT_PARAM_LONG, true,
          "Specifies the server's listening port. Default = " + DEFAULT_PORT);
      options.addOption(ROUTE_PARAM_SHORT, ROUTE_PARAM_LONG, true,
          "Specifies the WebSocket's route. Default = " + DEFAULT_ROUTE);
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse(options, args);
      
      final int port = cmd.hasOption(PORT_PARAM_LONG)
          ? Integer.parseInt(cmd.getOptionValue(PORT_PARAM_LONG)) : DEFAULT_PORT;
      
      final String route = cmd.hasOption(ROUTE_PARAM_LONG)
          ? cmd.getOptionValue(ROUTE_PARAM_SHORT) : DEFAULT_ROUTE;
      
      aPIDriver = new APIDriver(port, route); // configure the front end
      (new Thread(aPIDriver)).start(); // run the front end in a different thread
      
      TerminalUI terminalUI = TerminalUI.build();
      
      // catch SIGTERM
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override public void run() {
          try {
            System.out.println("Closing front end...");
            aPIDriver.halt();
            terminalUI.stop();
            
            Thread.sleep(1000);
            
            System.out.println("Goodbye!");
            Thread.sleep(200);
          } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      });
    } catch(Exception e) {
      System.err.println("Exception thrown: "
          + (e.getMessage() == null ? "Unknown." : e.getMessage()));
    }
  }
}
