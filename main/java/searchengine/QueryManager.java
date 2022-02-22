package searchengine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class QueryManager {
  private InvertedIndex invertedIndex;
  private TFIDFCalculator tfidfCalculator;
  private HashMap<String, HashSet<Page>> invertedMap = new HashMap<String, HashSet<Page>>();
  private HashSet<Page> allPages;

  public QueryManager(HashSet<Page> allPages) {
    invertedIndex = new InvertedIndex();
    tfidfCalculator = new TFIDFCalculator();
    invertedIndex.createInvertedIndex(allPages);
    invertedMap = invertedIndex.getInvertedMap();
    this.allPages = allPages;
  }

  /**
   * This method seperates the information contained in the @param searchTerms
   * The seperation process is a two-step process. 
   * First: The method seperates @param searchTerms by whitespace(s) and adds each seperations into ...
   * ... a variable defined as a String array. The method then loops through this array and adds ...
   * ... each index to a variable defined as List of String. If a respective index of String array ...
   * ...contains "or", the List of String variable gets added to another variable defined as 
   * ...List of List of String and the List of String variable gets recreated as an empty list. 
   * Second: The loop continues follow the same logic as "First" untill the String array is empty.
   * 
   * @param searchTerms
   * @return List of List of String
   */
  public List<List<String>> fillFinaListSeparatedByOr(String searchTerms) {
    List<List<String>> finalListSebaratedByOr = new ArrayList<>();
    List<String> smallListSeparatedByOr = new ArrayList<>();
    try {
      String wholeString = URLDecoder.decode(searchTerms, "UTF-8");
      String[] tempArray = wholeString.split("\\s+");
      for (int i = 0; i < tempArray.length; i++) {
        String tempString = tempArray[i].trim();
        if (!(tempString.equalsIgnoreCase("or"))) {
          smallListSeparatedByOr.add(tempString);
        } else {
          finalListSebaratedByOr.add(smallListSeparatedByOr);
          smallListSeparatedByOr = new ArrayList<>();
        }
      }
      finalListSebaratedByOr.add(smallListSeparatedByOr);

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return finalListSebaratedByOr;
  }

  /**
   * This method loops over the @param finalListSeperatorByOr defined as List of List of String.
   * For each List of String in @param finalListSeperatorByOr the method creates two local variables...
   * ... repectivly defined as HashMap with key of Page and value  of Integer...
   * ... and HashSet of Page. For each index of List of String...
   * ... the method then loops over each String. If the inverted index contains the key defined by the...
   * ... index String, then the HashSet of Page value of that String in the inverted index gets stored ...
   * ... in a local variable defined as HashSet of Page.
   * After this information is stored, the method executes another for each loop over the pages, stored...
   * ... in the local variable defined as HashSet of Page, this loop calculates and sets each of the pages...
   * ... tfIdfScore and tfScore and adds this information to the result variable defined as List of Page ...
   * ... before the method returns the result variable, the sortResult() method executes to sort the...
   * ... @return List of Page.
   * 
   * @param finalListSeparatedByOr
   * @return List of Page 
   */
  public List<Page> search(List<List<String>> finalListSeparatedByOr) {
    HashSet<Page> result = new HashSet<Page>();

    for (List<String> smalLists : finalListSeparatedByOr) {
      HashMap<Page, Integer> placeHolderMap = new HashMap<Page, Integer>();
      HashSet<Page> subResultSet = new HashSet<>();
      for (String word : smalLists) {
        if (invertedMap.containsKey(word)) {
          HashSet<Page> pages = invertedMap.get(word);
          for (Page page : pages) {
            double currentScore = page.getTfidfScore();
            double currentTfScore = page.getTfScore();
            page.setTfidfScore(currentScore + tfidfCalculator.tfIdf(page, allPages, invertedIndex, word));
            page.setTfScore(currentTfScore + tfidfCalculator.tf(page, word));
            if (!(placeHolderMap.containsKey(page))) {
              placeHolderMap.put(page, 1);
            } else {
              int tempOccurrance = placeHolderMap.get(page);
              tempOccurrance++;
              placeHolderMap.put(page, tempOccurrance);
            }
          }
        }
      }
      for (Page key : placeHolderMap.keySet()) {
        if (placeHolderMap.get(key) == smalLists.size()) {
          subResultSet.add(key);
          if (key.getTfidfScore() > key.getMaxScore()) {
            key.setMaxScore(key.getTfidfScore());
          }
          if (key.getTfScore() > key.getMaxTfScore()) {
            key.setMaxTfScore(key.getTfScore());
          }
        }
        key.setTfidfScore(0);
        result.addAll(subResultSet);
      }
    }
    return sortResult(result);  
  }

  /**
   * This method sorts @param result by comparing the MaxScore of each pages that gets compared.
   * The result of the comparison is stored in a variable defined as List of Page.
   * The method then reverses the order of the List of Page variable and loops through the pages...
   * ... to reset the MaxScore or, if toggled, the maxTfScore, of the pages. 
   * 
   * @param result
   * @return List of Page
   */
  public List<Page> sortResult(HashSet<Page> result) {
    List<Page> arr = new ArrayList<Page>(result);
    if (result.size() != 0){
    Collections.sort(arr, new Comparator<Page>() {
      @Override
      public int compare(Page p1, Page p2) {
        // TOGGLE BETWEEN THESE TO CHANGE TF TFIDF
        return Double.compare(p1.getMaxScore(), p2.getMaxScore());
        // return Double.compare(p1.getMaxTfScore(), p2.getMaxtFScore());
      }
    });
    Collections.reverse(arr);
    for (Page page : arr) {
      page.setMaxScore(0);
      page.setMaxTfScore(0);
    }
  }else{System.out.println("No web page contains the query word");}
    return arr;
  }

  /**
   * This methods executes fillFinalSeperatedByOr( @param searchTerm ) the result of this get stored...
   * ... in a List of List of String variable. The method then uses this variable to execute search().
   * The result of this gets returned as a List of Page by the method.
   * @param searchTerm
   * @return List of Page
   */
  public List<Page> matchingWebsites(String searchTerm) {
    List<List<String>> finalListSeparatedByOr = fillFinaListSeparatedByOr(searchTerm);
    return search(finalListSeparatedByOr);
  }
}
