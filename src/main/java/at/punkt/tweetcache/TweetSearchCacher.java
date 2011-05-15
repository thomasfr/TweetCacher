/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.punkt.tweetcache;

import com.mongodb.DBCollection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Order;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Sort;
import twitter4j.QueryResult;
import twitter4j.Tweet;

/**
 *
 * @author thomas
 */
public class TweetSearchCacher {

    private int sleep = 60000;
    private TwitterSearch search;
    @Autowired
    private MongoTemplate mongoTemplate;

    public TwitterSearch getSearch() {
        return search;
    }

    public void setSearch(TwitterSearch search) {
        this.search = search;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getSleep() {
        return this.sleep;
    }

    public void run() {
        long lastId = 0;
        long run = 1;
        while (true) {
            try {
                if (mongoTemplate.collectionExists("Tweets")) {
                    Query query = new Query();
                    Sort sort = query.sort();
                    sort.on("_id", Order.ASCENDING);
                    query.limit(1);
                    at.punkt.tweetcache.domain.Tweet lastTweet = mongoTemplate.findOne(query, at.punkt.tweetcache.domain.Tweet.class);
                    if (lastTweet != null) {
                        lastId = lastTweet.getid();
                    }
                }

                System.out.println("run: #" + run + "; since_id: " + lastId + ";");

                QueryResult result = search.search(lastId);
                String warning = result.getWarning();
                if (warning != null) {
                    System.out.println("Warning: " + warning);
                }
                lastId = result.getMaxId();
                List<Tweet> tweets = result.getTweets();
                System.out.println(tweets.size() + " new Tweet(s)");

                if (!tweets.isEmpty()) {
                    mongoTemplate.insertList(tweets);
                }
                run++;
                System.out.println("Sleeping for " + Math.round(sleep / 1000 / 60) + " minute(s)");
                System.out.println();
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(TweetSearchCacher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
