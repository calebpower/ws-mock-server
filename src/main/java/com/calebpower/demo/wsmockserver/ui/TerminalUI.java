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
