package com.calebpower.demo.webstandalone.action.page;

import java.util.HashMap;

import com.calebpower.demo.webstandalone.action.HTTPMethod;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Demo page that will be viewed when accessing the web root.
 * 
 * @author Caleb L. Power
 */
public class DemoPage extends Page {
  
  int port;
  
  /**
   * Initializes the index page and set the appropriate HTTP request type.
   * 
   * @param port the backend's listening port 
   */
  public DemoPage(int port) {
    super("/", HTTPMethod.GET); //this page should only be accessible via GET
    this.port = port;
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    HashMap<String, Object> model = new HashMap<String, Object>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("title", "Demo Page");
        put("name", System.getProperty("user.name"));
        put("port", "" + port);
    }};

    return new ModelAndView(model, "demopage.ftl"); //use the index template to generate the output
  }

}
