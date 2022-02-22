package searchengine;
import java.util.*;

public class InvertedIndex {

    private HashMap<String, HashSet<Page>> invertedMap;

    public InvertedIndex() {
        invertedMap = new HashMap<String, HashSet<Page>>();
    }
    /**
     * This method loops through a HashSet of Page objects(see @param), and for each index...
     * ... loops through the words of the respective page object's page.getWordsArray().
     * If the field (private HashMap with key String and value of HashSet of Page
     * ... invertedMap) does not contain the index (word) ...
     * ... of page.getWordsArray() the page gets added to a local variable and after put into to class field ...
     * ... as the value and the respective word becomes the key. 
     * This results in a HashMap (the field of the class) that contains as key, all the words in the file ...
     * ... and all the pages that word can be found in, as the value.
     * 
     * @param allPages
     */
    public void createInvertedIndex(HashSet<Page> allPages) {
        HashSet<Page> allPagesInverted = new HashSet<Page>();
        allPagesInverted = allPages;
        for (var page : allPagesInverted) {
            for (String word : page.getWordsArray()) {
                if (invertedMap.get(word) == null) {
                    HashSet<Page> pageHolder = new HashSet<Page>();
                    pageHolder.add(page);
                    invertedMap.put(word, pageHolder);
                } else {
                    invertedMap.get(word).add(page);
                }
            }
        }
    }

    /**
     * 
     * @param searchWord
     * @return HashSet of Page
    */
    public HashSet<Page> searchInvertedIndex(String searchWord){
        
        return invertedMap.get(searchWord);
    }

    /**
     * @return HashMap of key String and value of HashSet of Page
     */
    public HashMap<String, HashSet<Page>> getInvertedMap() {
        return invertedMap;
    }

}