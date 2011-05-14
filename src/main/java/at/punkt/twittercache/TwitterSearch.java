/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.punkt.twittercache;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author thomas
 */
public class TwitterSearch {

    private String searchQuery;
    private int resultsPerPage = 100;

    public TwitterSearch(String query) {
        this.searchQuery = query;
    }

    public QueryResult search() {
        return this.doSearch(0);
    }

    public QueryResult search(long sinceId) {
        return this.doSearch(sinceId);
    }

    private QueryResult doSearch(long sinceId) {
        Twitter twitter = new TwitterFactory().getInstance();
        QueryResult result = null;
        try {
            Query query = new Query(this.searchQuery);
            if(sinceId > 0) {
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
