package searchengine;

import java.util.HashSet;


public class TFIDFCalculator {


    public TFIDFCalculator() {
    }

    /**
     * This method calculates the Term Frequency (tf) of String searchTerm @param.
     * The tf is the result of dividing the amount of time the searchTerm appears on a page ... 
     * ... and the size of page. 
     * This result is both stored in the respective page's tfScore field and returned by the method. 
     * 
     * @param page
     * @param searchTerm
     * @return double
     */
    public double tf(Page page, String searchTerm) {
        double pageScore;
        double pageFrq = page.getWordOccuranceMap().get(searchTerm);
        double pageSize = page.getWordsArray().size();
        pageScore = pageFrq / pageSize;
        page.setTfScore(pageScore);
        return page.getTfScore();
    }

    /**
     * This method calculates the inverted document frequency (idf), to measure the amount of information ...
     * ... @param searchterm has. If it is rare or common across all pages. 
     * The idf is the result of the logarithm of the number of elements in @param allPages divided with ...
     * ... number of element of pages where the searchTerm is present: using .getInvertedMap() ...
     * ... .get(searchTerm).size() on @param invertedIndex . 
     * The result of this method is stored in the field of @param page and returned by the mehtod. 
     * 
     * @param allPages
     * @param page
     * @param invertedIndex
     * @param searchTerm
     * @return double
     */
    public double idf(HashSet<Page> allPages, Page page, InvertedIndex invertedIndex, String searchTerm) {
        double pageScore;
        double something = 1;
        double allPageSize = allPages.size();
        double matchingSize = invertedIndex.getInvertedMap().get(searchTerm).size();

        pageScore = Math.log(allPageSize / matchingSize);

        if (pageScore >= 0) {
            pageScore += something;
        }

        page.setIdfScore(pageScore);
        return page.getIdfScore();
    }

    /**
     * This method uses the tf() and idf() methods to create the tfScore and idfScore and stores these ...
     * ... results in local variables. These local variables are then used to calculate the ...
     * ... term frequency - inverse document frequency by multiplication. 
     * The result of the method is stored in field of @param page and returned by the method.  
     * 
     * @param page
     * @param allPages
     * @param invertedIndex
     * @param searchTerm
     * @return double
     */
    public double tfIdf(Page page, HashSet<Page> allPages, InvertedIndex invertedIndex, String searchTerm) {
        double pageScore;
        double tfScore = tf(page, searchTerm);
        double idfScore = idf(allPages, page, invertedIndex, searchTerm);
        pageScore = tfScore * idfScore;
        page.setTfidfScore(pageScore);
        return page.getTfidfScore();
    }
}