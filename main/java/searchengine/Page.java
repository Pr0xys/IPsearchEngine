package searchengine;


import java.util.*;


public class Page{

  private String cleanURL;
  private String title;
  private ArrayList<String> wordsArray;
  private Map<String, Integer> wordOccuranceMap;
  private double tfScore;
  private double idfScore;
  private double tfidfScore;
  private double maxScore;
  private double maxTfScore;

  public Page(String URL, String title, ArrayList<String> words,Map<String, Integer> wordOccuranceMap, double score) {
    this.cleanURL = URL;
    this.title = title;
    this.wordsArray = words;
    this.wordOccuranceMap = wordOccuranceMap;
    this.tfScore = score;
    this.tfidfScore = score;
  }

  /** 
   * @return String
   */
  public String getCleanURL() {
      return cleanURL;
  }

  /** 
   * @return String
   */
  public String getTitle() {
      return title;
  }

  /** 
   * @return ArrayList of String
   */
  public ArrayList<String> getWordsArray() {
      return wordsArray;
  }
   
  /** 
   * @return double
   */
  public double getTfScore() {
      return tfScore;
  }

  /** 
   * @param score
   */
  public void setTfScore(double score) {
      this.tfScore =+ score;
  }

  
  /** 
   * @return Map with key of String value of Integer
   */
  public Map<String, Integer> getWordOccuranceMap() {
    return wordOccuranceMap;
  }

  /** 
   * @return double
   */
  public double getTfidfScore() {
      return tfidfScore;
  }
  
  /** 
   * @param tfidfScore
   */
  public void setTfidfScore(double tfidfScore) {
      this.tfidfScore =+ tfidfScore;
  }

  /** 
   * @return double
   */
  public double getIdfScore() {
      return idfScore;
  }

  /** 
   * @param idfScore
   */
  public void setIdfScore(double idfScore) {
      this.idfScore =+ idfScore;
  }
  
  /** 
   * @return double
   */
  public double getMaxScore() {
      return maxScore;
  }
  
  /** 
   * @param maxScore
   */
  public void setMaxScore(double maxScore) {
      this.maxScore = maxScore;
  }
  
  /** 
   * @return double
   */
  public double getMaxTfScore() {
      return maxTfScore;
  }
  
  /** 
   * @param maxTfScore
   */
  public void setMaxTfScore(double maxTfScore) {
      this.maxTfScore = maxTfScore;
  }
}
