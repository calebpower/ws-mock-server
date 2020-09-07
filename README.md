# Web Standalone Demo for Java

*By Caleb L. Power. Amended September 4, 2020*

The purpose of this demo is to provide inspiration for creating stand-alone web applications in Java. It is not the intent of this project to provide a novice's tutorial for setting up such an application; rather, the content is provided for the Java developer that is already familiar with their (development) surroundings.

The following was the technology stack utilized when developing this code repository.
- Eclipse IDE ([license](https://www.eclipse.org/legal/epl-2.0/))
- FreeMarker template engine ([license](https://freemarker.apache.org/docs/app_license.html))
- FreeMarkerEngine (modified Spark adapter, [license](http://www.apache.org/licenses/LICENSE-2.0))
- Gradle build tool ([license](https://github.com/gradle/gradle/blob/master/LICENSE))
- Gradle Shadow plugin ([license](http://www.apache.org/licenses/LICENSE-2.0))
- JSON API ([license](https://www.json.org/license.html))
- Spark (Java) microframework ([license](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)))

You need Java 11. This project can be tested and compiled with the following command.

`gradle clean test shadowJar`

To run it, just do `java -jar build\libs\web-standalone-demo-java.jar`.

The default port is `4567` so you should be able to see the GUI by going to `http://127.0.0.1:4567`

**This code repository has been released under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).**