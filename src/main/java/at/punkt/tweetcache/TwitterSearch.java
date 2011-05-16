package at.punkt.tweetcache;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author Thomas Fritz <fritz@punkt.at>
 */
public class TwitterSearch {

    private String searchQuery;
    private int resultsPerPage = 100;

    public QueryResult search() {
        return this.doSearch(0);
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public QueryResult search(long sinceId) {
        return this.doSearch(sinceId);
    }

    private QueryResult doSearch(long sinceId) {
        Twitter twitter = new TwitterFactory().getInstance();
        QueryResult result = null;
        try {
            Query query = new Query(this.searchQuery);
            if (sinceId > 0) {
                query.setSinceId(sinceId);
            }
            query.setRpp(resultsPerPage);
            result = twitter.search(query);
        } catch (TwitterException te) {
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        return result;
    }
}
