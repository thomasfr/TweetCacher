package at.punkt.tweetcache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoFactoryBean;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mongodb.Mongo;

/**
 * 
 * @author Thomas Fritz <fritz@punkt.at>
 */
@Configuration
public class AppConfig {

    public @Bean
    MongoTemplate mongoTemplate(Mongo mongo) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, "test", "Tweets");
        return mongoTemplate;
    }

    /*
     * Factory bean that creates the Mongo instance
     */
    public @Bean
    MongoFactoryBean mongo() {
        MongoFactoryBean mongo = new MongoFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

    public @Bean
    TwitterSearch twitterSearch() {
        return new TwitterSearch();
    }

    public @Bean
    TweetSearchCacher mongoTest(TwitterSearch search) {
        TweetSearchCacher cacher = new TweetSearchCacher();
        cacher.setSearch(search);
        return cacher;
    }
}
