package com.calebpower.demo.webstandalone.action.page;

import com.calebpower.demo.webstandalone.action.HTTPMethod;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Module abstract class for the easy-adding of custom pages.
 * 
 * @author Caleb L. Power
 */
public abstract class Page {
  
  private HTTPMethod[] methods = null;
  private String route = null;
  
  /**
   * Overloaded constructor to set the request type and the route.
   * 
   * @param route the route
   * @param methods the HTTP methods that can access the route
   */
  public Page(String route, HTTPMethod... methods) {
    this.methods = methods;
    this.route = route;
  }
  
  /**
   * Retrieve the HTTP method types for this route.
   * 
   * @return array of type HTTPMethod
   */
  public HTTPMethod[] getHTTPMethods() {
    return methods;
  }
  
  /**
   * Retrieve the route for the module.
   * 
   * @return String representing the route to be used for the module.
   */
  public String getRoute() {
    return route;
  }
  
  /**
   * The actions that will be carried out for all routes.
   * 
   * @param request REST request
   * @param response REST response
   * @return ModelAndView containing the HTTP response (often in JSON)
   */
  public ModelAndView action(Request request, Response response) {
    return customAction(request, response);
  }
  
  /**
   * The action in question for the particular module.
   * 
   * @param request REST request
   * @param response REST response
   * @return ModelAndView containing the HTTP response (often in JSON)
   */
  public abstract ModelAndView customAction(Request request, Response response);
  
}