package searchengine;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.junit.jupiter.api.Test;

public class QueryManagerTest {


    @Test
    void testFillFinaListSeparatedByOrWithOneOr() throws IOException{


        WebServer webServer = new WebServer();
        webServer.fillPages(Files.readString(Paths.get("config-test.txt")).strip());
        QueryManager test = new QueryManager(webServer.getAllPages());
        String searchTerms = "word1 word2 or word3";
        assertEquals(2, test.fillFinaListSeparatedByOr(searchTerms).size());


    }

    @Test
    void testFillFinaListSeparatedByOrWithoutOr() throws IOException{

        WebServer webServer = new WebServer();
        webServer.fillPages(Files.readString(Paths.get("config-test.txt")).strip());
        QueryManager test = new QueryManager(webServer.getAllPages());
        String searchTerms = "word1 word2 word3";
        assertEquals(1, test.fillFinaListSeparatedByOr(searchTerms).size());

    }


    @Test
    void testSearch() throws IOException {

        WebServer webServer = new WebServer();
        webServer.fillPages(Files.readString(Paths.get("config-test.txt")).strip());
        QueryManager test = new QueryManager(webServer.getAllPages());
        List<List<String>> finalListSeparatedByOr = new ArrayList<List<String>>();
        List<String>listOfFinalListSeparatedByOr = new ArrayList<String>();
        List<String>anoterListOfFinalListSeparatedByOr = new ArrayList<String>();
        List<String>andAnoterListOfFinalListSeparatedByOr = new ArrayList<String>();

        listOfFinalListSeparatedByOr.add("word3");
        andAnoterListOfFinalListSeparatedByOr.add("word5");
        anoterListOfFinalListSeparatedByOr.add("word4");
        anoterListOfFinalListSeparatedByOr.add("word2");
   
        finalListSeparatedByOr.add(listOfFinalListSeparatedByOr);
        finalListSeparatedByOr.add(anoterListOfFinalListSeparatedByOr);
        finalListSeparatedByOr.add(andAnoterListOfFinalListSeparatedByOr);


        assertEquals(1, test.search(finalListSeparatedByOr).size());

    }


    private void assertTrue(boolean b, boolean c) {
    }

    @Test
    void testSortResult() throws IOException {
        
        WebServer webServer = new WebServer();
        QueryManager test = new QueryManager(webServer.getAllPages());
        ArrayList<String> words = new ArrayList<String>();
        words.add("word1");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("word1", 1);
        double score = 0.6;
        Page page = new Page("http://page1.com", "title1", words, map, score);
        HashSet<Page> testHashset = new HashSet<Page>();
        testHashset.add(page);
        boolean c = test.sortResult(testHashset) instanceof List<?>;
        boolean b = true;
        assertTrue(b, c);

    }




}
