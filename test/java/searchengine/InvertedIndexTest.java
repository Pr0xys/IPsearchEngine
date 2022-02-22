package searchengine;

import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class InvertedIndexTest {

  @Test
  void testCreateInvertedIndex() throws IOException {
    WebServer webServer = new WebServer();
    webServer.fillPages(Files.readString(Paths.get("config-test.txt")).strip());
    InvertedIndex indexUnderTest = new InvertedIndex();
    indexUnderTest.createInvertedIndex(webServer.getAllPages());
    var test = indexUnderTest.getInvertedMap().size();
    assertEquals(3, test);
    
  }

  @Test
  void testSearchInvertedIndex() throws IOException {
    WebServer webServer = new WebServer();
    webServer.fillPages(Files.readString(Paths.get("config-test.txt")).strip());
    InvertedIndex invertedIndex = new InvertedIndex();
    invertedIndex.createInvertedIndex(webServer.getAllPages());
    HashMap<String, HashSet<Page>> invertedMap = new HashMap<String, HashSet<Page>>();
    invertedMap = invertedIndex.getInvertedMap();
    String searchTerm = "word1";
    invertedIndex.searchInvertedIndex(searchTerm);
    assertEquals(2, invertedMap.get(searchTerm).size());
   
  }
}
