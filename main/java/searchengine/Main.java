package searchengine;

import java.io.IOException;

public class Main {

  public static void main(final String... args) throws IOException {
    WebServer webserver = new WebServer();
    webserver.createServer();
  }
}
