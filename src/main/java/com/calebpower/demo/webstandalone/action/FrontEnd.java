package com.calebpower.demo.webstandalone.action;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.patch;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;
import static spark.Spark.stop;
import static spark.Spark.webSocket;

import com.calebpower.demo.webstandalone.action.page.DemoPage;
import com.calebpower.demo.webstandalone.action.page.Page;

/**
 * Front end view; manages all pages and directs traffic to those pages.
 * 
 * @author Caleb L. Power
 */
public class FrontEnd implements Runnable {
  
  private static final String RESPONDER_STATIC_FOLDER = "responder/static";
  private static final String RESPONDER_TEMPLATE_FOLDER = "/responder/templates";
  
  private int port; //the port that the front end should run on
  private FreeMarkerEngine freeMarkerEngine = null; //the FreeMarker engine
  private Page pages[] = null; //the pages that will be accessible
  
  /**
   * Opens the specified external port so as to launch the front end.
   * 
   * @param port the port by which the front end will be accessible
   */
  public FrontEnd(int port) {
    
    this.port = port;
    
    if(freeMarkerEngine == null) freeMarkerEngine = new FreeMarkerEngine(RESPONDER_TEMPLATE_FOLDER);
    
    pages = new Page[] {
        new DemoPage(port)
      };
    
    staticFiles.location(RESPONDER_STATIC_FOLDER); //relative to the root of the classpath
    
  }

  /**
   * Runs the front end in a separate thread so that it can be halted externally.
   */
  @Override public void run() {
    webSocket("/socket", WebSocketHandler.class); // initialize WebSocket
    port(port);

    before((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*"); // XXX this is generally bad, but we'll allow it for the demo
      response.header("Access-Control-Allow-Methods", "DELETE, POST, GET, PATCH, PUT, OPTIONS");
      response.header("Access-Control-Allow-Headers",
          "Content-Type, "
            + "Access-Control-Allow-Headers, "
            + "Access-Control-Allow-Origin, "
            + "Access-Control-Allow-Methods, "
            + "Authorization, "
            + "X-Requested-With");
      response.header("Access-Control-Expose-Headers", "Content-Type, Content-Length");
      response.header("Content-Type", "text/html"); 
    });
    
    options("/*", (request, response)-> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if(accessControlRequestHeaders != null)
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if(accessControlRequestMethod != null)
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);

      return "OK";
    });
    
    for(Page page : pages) { // iterate through initialized pages and determine the appropriate HTTP request types
      for(HTTPMethod method : page.getHTTPMethods()) {
        switch(method) {
        case DELETE:
          delete(page.getRoute(), page::action, freeMarkerEngine);
          break;
        case GET:
          get(page.getRoute(), page::action, freeMarkerEngine);
          break;
        case PATCH:
          patch(page.getRoute(), page::action, freeMarkerEngine);
          break;
        case POST:
          post(page.getRoute(), page::action, freeMarkerEngine);
          break;
        case PUT:
          put(page.getRoute(), page::action, freeMarkerEngine);
          break;
        }
      }
    }
  }
  
  /**
   * Stops the web server.
   */
  public void halt() {
    stop();
  }
  
}
