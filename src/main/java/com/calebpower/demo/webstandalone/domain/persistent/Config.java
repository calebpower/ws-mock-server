package com.calebpower.demo.webstandalone.domain.persistent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Pulls information from the configuration file and turns the data
 * into a JSON object for easy data retrieval.
 * 
 * @author Caleb L. Power
 */
public class Config {

  private static final int SPARK_PORT = 4567; //here just for purposes of the demo
  
  private static File file = null; //the file for possible reloading later
  private static JSONObject data = null; //the actual JSON data
  
  /**
   * Null constructor for an empty config.
   */
  public Config() { }
  
  /**
   * Creates the configuration object by specifying the filename.
   * 
   * @param fileName the name and location of the configuration file on the disk
   */
  public Config(String fileName) {
    file = new File(fileName);
  }
  
  
  /**
   * Loads the configuration file into memory. One might utilize this method by
   * invoking <code>Config c = new Config("/file").load()</code>.
   * 
   * @return the Config object if loading was successful and
   *         <code>null</code> if loading was unsuccessful
   * @throws Exception if the file could not be read
   */
  public Config load() throws Exception {
    try {
      Scanner scanner = new Scanner(file);
      String raw = new String();
      while(scanner.hasNext()) raw += scanner.nextLine(); //read the file
      scanner.close(); //close the input stream
      data = new JSONObject(raw); //parse the raw data as JSON
    } catch(FileNotFoundException | JSONException e) {
      data = new JSONObject();
      throw new Exception(e);
    }
    
    return this;
  }
  
  /**
   * Grab the entire data object... this is just an example.
   * 
   * @return JSONObject the data (will be null if invoked before
   * the <code>load()</code> method).
   */
  public JSONObject getData() {
    return data;
  }
  
  /**
   * For demonstration purposes; grabs the web port defined earlier,
   * for use by the SparkJava microframework.
   * 
   * @return int representing the port that Spark will open to
   */
  public int getSparkPort() {
    return SPARK_PORT;
  }
  
}
