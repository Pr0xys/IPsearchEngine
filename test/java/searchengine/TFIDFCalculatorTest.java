package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


public class TFIDFCalculatorTest {
    @Test
    void testTf() throws IOException {
        ArrayList<String> words = new ArrayList<String>();
        words.add("word1");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("word1", 1);
        double score = 1;
        Page page = new Page("http://page1.com", "title1", words, map, score);
        TFIDFCalculator test = new TFIDFCalculator();
        String searchTerms = "word1";
        test.tf(page, searchTerms);

        double d = 1.0;

        assertEquals(d, test.tf(page, searchTerms));  
        
    }   


        
    @Test
    void testTfIdf() throws IOException {
        WebServer webServer = new WebServer();
        webServer.createServer();
        webServer.fillPages(Files.readString(Paths.get("config-test.txt")).strip());
        ArrayList<String> words = new ArrayList<String>();
        words.add("word1");
        words.add("word2");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("word1", 1);
        map.put("word2", 1);

        double score = 0.5;
        Page page = new Page("http://page1.com", "title1", words, map, score);
        InvertedIndex testindex = new InvertedIndex();
        testindex.createInvertedIndex(webServer.getAllPages());
        TFIDFCalculator test = new TFIDFCalculator();
        String searchTerms = "word1";
        double d = 0.5;

        assertEquals(d, test.tfIdf(page, webServer.getAllPages(), testindex, searchTerms));
        webServer.server.stop(0);
        webServer = null;
    }

    @Test
    void testIdf() throws IOException {
        WebServer webServer = new WebServer();
        webServer.createServer();
        ArrayList<String> words = new ArrayList<String>();
        words.add("word1");
        words.add("word2");
   
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("word1", 1);
        map.put("word2", 1);

        double score = 0.5;
        Page page = new Page("http://page1.com", "title1", words, map, score);
        InvertedIndex testindex = new InvertedIndex();
        testindex.createInvertedIndex(webServer.getAllPages());
        TFIDFCalculator test = new TFIDFCalculator();
        String searchTerms = "word1";
        double d = 1;

        assertEquals(d, test.idf(webServer.getAllPages(), page, testindex, searchTerms));
        webServer.server.stop(0);
        webServer = null;
    }



}
