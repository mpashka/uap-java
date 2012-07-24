ua_parser Java Library
======================

This is the Java implementation of [ua-parser](https://github.com/tobie/ua-parser).  
The implementation uses the shared regex patterns and overrides from regexes.yaml.

Usage:
--------
```java
import ua_parser.Parser;
import ua_parser.UserAgent;
import ua_parser.Device;
import ua_parser.OS;

...

  String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

  Parser uaParser = new Parser();
  Client c = uaParser.parse(uaString);

  System.out.println(c.userAgent.family); // => "Mobile Safari"
  System.out.println(c.userAgent.major);  // => "5"
  System.out.println(c.userAgent.minor);  // => "1"

  System.out.println(c.os.family);        // => "iOS"
  System.out.println(c.os.major);         // => "5"
  System.out.println(c.os.minor);         // => "1"

  System.out.println(c.device.family);    // => "iPhone"
  System.out.println(c.device.isMobile);  // => true
  System.out.println(c.device.isSpider);  // => false
```

Build:
------

    mvn package

Author:
-------

  * Steve Jiang [@sjiang](https://twitter.com/sjiang)

  Based on the python implementation by Lindsey Simon and using agent data from BrowserScope