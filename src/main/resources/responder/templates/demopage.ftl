<!doctype html>
<!-- Present to prove that SparkJava + FreeMarker templates are functional.
     @author Caleb L. Power -->
<html>
  <head>
    <title>${title}</title>
    <link rel='stylesheet' href='style.css'>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous"></script>
    <script type="text/javascript">
      var ws;
      
      let openWS = function() {
        ws = new WebSocket('ws://127.0.0.1:${port}/socket');
        ws.onopen = function() {
          console.log('Opened WebSocket.');
        };
        ws.onmessage = function(e) {
          let data = JSON.parse(e.data);
          $('#reset').text(data.time);
          console.log(data);
        };
        ws.onclose = function() {
          console.log("WebSocket closed! Trying to reopen...");
          setTimeout(openWS, 3000);
        };
      }
      
      $(document).ready(function() {
        if("WebSocket" in window) {
          console.log("WebSocket is supported by the browser.");
          openWS();
          $('#reset').on('click', function() {
            let time = { "time": 0 };
            ws.send(JSON.stringify(time));
          });
        } else {
          console.log("WebSocket is not supported by the browser.");
        }
      });
    </script>
  </head>
  <body>
    <div class='header'>
      Welcome, ${name}!
    </div>
    <div class='action'>
      Super Awesome Counter: <button id='reset'>Loading...</button> &#8592; Click to Reset :)
    </div>
  </body>
</html>