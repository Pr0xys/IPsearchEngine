package searchengine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;

public class SearchManager {
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private QueryManager queryManager;

  public SearchManager(HashSet<Page> allPages) {
    queryManager = new QueryManager(allPages);
  }

  /**
   * This method creates a local variable and stores into it, a List of Page that matches...
   * ... the @param searchTerm
   * This result is returned by the method
   * 
   * @param searchTerm
   * @return List of Page
   */
  public List<Page> search(String searchTerm) {
    List<Page> result = new ArrayList<Page>();
    result = queryManager.matchingWebsites(searchTerm);
    return result;
  }

  /**
   * @param io
   */
  public void searchURI(HttpExchange io) {
    var searchTerm = io.getRequestURI().getRawQuery().toLowerCase().split("=")[1];
    var response = new ArrayList<String>();
    List<Page> resultPageSet = search(searchTerm);
    for (var page : resultPageSet) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
          page.getCleanURL(), page.getTitle()));
    }
    var bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  /**
   * @param io
   * @param code
   * @param mime
   * @param response
   */
  public void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }
}
