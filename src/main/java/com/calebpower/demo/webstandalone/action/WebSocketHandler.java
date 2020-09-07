/*
 * Copyright (c) 2019 Axonibyte Innovations, LLC. All rights reserved.
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

package com.calebpower.demo.webstandalone.action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import com.calebpower.demo.webstandalone.domain.Core;

/**
 * Handles interactions via the WebSocket
 * 
 * @author Caleb L. Power
 */
@WebSocket public class WebSocketHandler {
  
  // store sessions if we want to, for example, broadcast a message to all users
  private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
  
  /**
   * Adds a session to the broadcast pool.
   * 
   * @param session the session
   */
  @OnWebSocketConnect public void connected(Session session) {
    sessions.add(session);
  }
  
  /**
   * Removes a session from the broadcast pool.
   * 
   * @param session the session
   * @param statusCode the status code
   * @param reason the reason that the session closed
   */
  @OnWebSocketClose public void closed(Session session, int statusCode, String reason) {
    sessions.remove(session);
  }
  
  /**
   * Handles an incoming message on a WebSocket.
   * 
   * @param session the session
   * @param message the message
   */
  @OnWebSocketMessage public void message(Session session, String message) {
    try {
      JSONObject data = new JSONObject(message);
      int time = data.getInt("time");
      Core.getClock().set(time);
    } catch(JSONException e) {
      System.err.println("Received malformed JSON.");
    }
  }
  
  /**
   * Broadcasts data to all sessions.
   * 
   * @param payload the JSON Object datum
   */
  public static void broadcast(JSONObject payload) {
    for(Session session : sessions) {
      session.getRemote().sendStringByFuture(payload.toString());
    }
  }
  
}
