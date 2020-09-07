package com.calebpower.demo.webstandalone.action;

import java.io.IOException;
import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.ModelAndView;
import spark.TemplateEngine;

/**
 * A driver for the FreeMarker templating engine.
 *
 * @author Alex
 * @author Per Wendel
 * @author Caleb L. Power
 */
public class FreeMarkerEngine extends TemplateEngine {

  private Configuration configuration;

  /**
   * Creates a FreeMarker Engine. This should only be invoked once.
   * 
   * @param templateLocation the location of the template directory.
   */
  public FreeMarkerEngine(String templateLocation) {
    configuration = new Configuration(Configuration.VERSION_2_3_23);
    configuration.setClassForTemplateLoading(FreeMarkerEngine.class, "");
    configuration.setClassForTemplateLoading(getClass(), templateLocation);
  }

  /**
   * Creates a FreeMarkerEngine with the specified configuration. This
   * should only be invoked once.
   *
   * @param configuration a custom FreeMarker configuration
   */
  public FreeMarkerEngine(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override public String render(ModelAndView modelAndView) {
    try {
      StringWriter stringWriter = new StringWriter();
      Template template = configuration.getTemplate(modelAndView.getViewName());
      template.process(modelAndView.getModel(), stringWriter);
      return stringWriter.toString();
    } catch(IOException | TemplateException e) {
      throw new IllegalArgumentException(e);
    }
  }

}