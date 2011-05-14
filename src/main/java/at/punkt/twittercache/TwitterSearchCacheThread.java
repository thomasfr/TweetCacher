/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.punkt.twittercache;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.QueryResult;
import twitter4j.Tweet;

/**
 *
 * @author thomas
 */
public class TwitterSearchCacheThread extends Thread {

    private String query;
    private int sleep = 60000;
    private String fileName = "Tweets.csv";

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getSleep() {
        return this.sleep;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    @Override
    public void run() {
        TwitterSearch search = new TwitterSearch(this.getQuery());
        long lastId = 0;
        long previousSinceId = 0;
        BufferedWriter out = null;
        Random random = new Random();
        long run = 1;
        try {
            out = new BufferedWriter(new FileWriter(fileName, true));
            out.write("id;from_user;iso_language_code;profile_image_url;source;text;to_user_id;created_at;from_user_id;");
            out.newLine();
            while (true) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + ": run: #" + run + "; since_id: " + lastId + ";");

                QueryResult result = search.search(lastId);
                String warning = result.getWarning();
                if (warning != null) {
                    System.out.println(threadName + ": Warning: " + warning);
                }
                lastId = result.getMaxId();
                List<Tweet> tweets = result.getTweets();
                System.out.println(threadName + ": " + tweets.size() + " new Tweet(s)");

                if (!tweets.isEmpty()) {
                    for (Tweet tweet : tweets) {
                        out.write(tweet.getId() + ";"
                                + tweet.getFromUser() + ";"
                                + tweet.getIsoLanguageCode() + ";"
                                + tweet.getProfileImageUrl() + ";"
                                + tweet.getSource().replaceAll("\n", " ") + ";"
                                + "\"" + tweet.getText().replaceAll("\n", " ").replaceAll(";", "\\;") + "\"" + ";"
                                + tweet.getToUser() + ";"
                                + tweet.getCreatedAt() + ";"
                                + tweet.getFromUserId() + ";");
                        out.newLine();
                    }
                    out.flush();
                }
                run++;
                System.out.println(threadName + ": Sleeping for " + Math.round(sleep/1000/60) + " minute(s)");
                System.out.println();
                sleep(sleep);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TwitterSearchCacheThread.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (IOException ex) {
            Logger.getLogger(TwitterSearchCacheThread.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(TwitterSearchCacheThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
