package searchengine;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WebServer {

  private static final int PORT = 8080;
  private static final int BACKLOG = 0;
  protected HttpServer server;
  private HashSet<Page> allPages;

  public WebServer() {
    allPages = new HashSet<Page>();
  }

  public WebServer(int PORT, String hmm) throws IOException {
    allPages = new HashSet<Page>();
  }

  /**
   * The fillPages method is divided into several steps.
   * List of String lines returns the lines from the given file @param, 
   * through a for loop and stores them into List of List of Strings pages.
   * The list of pages is reverse sorted.
   * We store the each URL within a String cleanURL with a substring method so as to eliminate the "*PAGE"
   * Title is stored in a String title, the words in the page are stored in a ArrayList of String wordsArray.
   * For each page, a score of type double is stored, 
   * as well as a wordOccuranceMap that stores a Map with key String and value of Integer,
   * result of countWords method defined below. 
   * A new Page object is then instantiated and stored in a HashSet with all the above mentioned parameters.
   * 
   * @param filename with the String datatype 
   * @throws IOException
   */
  public void fillPages(String filename) throws IOException {
    List<String> lines = new ArrayList<>();
    List<List<String>> pages = new ArrayList<>();
    lines = Files.readAllLines(Paths.get(filename));
    var lastIndex = lines.size();
    for (var i = lines.size() - 1; i >= 0; --i) {
      if (lines.get(i).startsWith("*PAGE")) {
        pages.add(lines.subList(i, lastIndex));
        lastIndex = i;
      }
    }
    Collections.reverse(pages);

    for (var page : pages) {
      if (page.size() > 3) {
        String cleanURL = page.get(0).substring(6, page.get(0).length());
        String title = page.get(1);
        ArrayList<String> wordsArray = new ArrayList<String>(page.subList(2, page.size()));
        double score = 0;
        Map<String, Integer> wordOccuranceMap = countWords(wordsArray);
        allPages.add(new Page(cleanURL, title, wordsArray, wordOccuranceMap, score));
      }
    }
  }

  /**
   * The method loops through the wordsArray list and puts the values in a HashMap.
   * The method checks to see if the word already exists in the Map. If it does, 
   * then the word will have an increment in the value
   * and because it is a HashMap, the word won't be duplicated. 
   * The method keeps track of how many times the word occures.
   * 
   * @param wordsArray
   * @return Map with key String, and value Integer
   */
  public Map<String, Integer> countWords(List<String> wordsArray) {
    Map<String, Integer> wordOccuranceMap = new HashMap<String, Integer>();
    for (String word : wordsArray) {
      if (!wordOccuranceMap.containsKey(word)) {
        wordOccuranceMap.put(word, 1);
      } else {
        int value = wordOccuranceMap.get(word);
        wordOccuranceMap.put(word, value + 1);
      }

    }
    return wordOccuranceMap;
  }

  /**
   * Added on top of the initial code, the fillPages method is called here 
   * and gets the filename param. The SearchManager is also instantiated 
   * when the server is created and it retrieves allPages hash set of Page as a parameter.
   * 
   * @throws IOException
   */
  public void createServer() throws IOException {
    int port = WebServer.PORT;
    var filename = Files.readString(Paths.get("config.txt")).strip();
    fillPages(filename);
    FileManager fileManager = new FileManager();
    SearchManager searchManager = new SearchManager(allPages);

    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    server.createContext(
        "/",
        io -> searchManager.respond(
            io,
            200,
            "text/html",
            fileManager.getFile("web/index.html")));
    server.createContext("/search", io -> searchManager.searchURI(io));
    server.createContext(
        "/favicon.ico",
        io -> searchManager.respond(
            io,
            200,
            "image/x-icon",
            fileManager.getFile("web/favicon.ico")));
    server.createContext(
        "/code.js",
        io -> searchManager.respond(
            io,
            200,
            "application/javascript",
            fileManager.getFile("web/code.js")));
    server.createContext(
        "/style.css",
        io -> searchManager.respond(
            io,
            200,
            "text/css",
            fileManager.getFile("web/style.css")));
    server.start();
    displayServerMessage(port);
  }

  /**
   * @param port PORT=8080
   */
  public void displayServerMessage(int port) {
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");
  }

  /**
   * @return hashset of Page
   */
  public HashSet<Page> getAllPages() {
    return allPages;
  }

}
