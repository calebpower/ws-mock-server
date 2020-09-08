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
package com.calebpower.demo.wsmockserver.ui;

import java.util.Scanner;

import com.calebpower.demo.wsmockserver.api.WebSocketHandler;

/**
 * The terminal UI.
 * 
 * TODO
 * - create a top panel that shows the messages
 * - create a small bottom bar that lets the user send from the server
 * 
 * @author Caleb L. Power
 */
public class TerminalUI implements Runnable {
  
  private Thread thread = null;
  
  /**
   * Builds the terminal UI.
   * 
   * @return an instance of the TerminalUI
   */
  public static TerminalUI build() {
    TerminalUI terminalUI = new TerminalUI();
    terminalUI.thread = new Thread(terminalUI);
    terminalUI.thread.setDaemon(true);
    terminalUI.thread.start();
    return terminalUI;
  }
  
  @Override public void run() {
    try(Scanner keyboard = new Scanner(System.in)) { 
      while(!thread.isInterrupted()) {
        WebSocketHandler.broadcast(keyboard.nextLine());
      }
    } catch(Exception e) { }
  }
  
  /**
   * Stops the thread.
   */
  public void stop() {
    if(thread != null) thread.interrupt();
  }
}
