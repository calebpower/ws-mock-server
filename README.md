# WebSocket Mock Server

*Copyright (c) 2020 Caleb L. Power. All rights reserved.*

## Build

You need Java 11. This project can be tested and compiled with the following command.

`gradlew clean test shadowJar`

## Execution

To run it, just do `java -jar build\libs\ws-mock-server.jar`.

You can optionally specify some command-line arguments.

|Short Param|Long Param|Description        |Default|
|:----------|:---------|:------------------|:------|
|-p         |--port    |The port number    |5698   |
|-r         |--route   |The WebSocket route|/      |

## License

**This code repository has been released under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).**
