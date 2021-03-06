package at.punkt.tweetcache;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Thomas Fritz <fritz@punkt.at>
 */
public class App extends Thread {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/bootstrap.xml");

        int sleep = 60000;
        String query = "";
        if (args.length <= 0 || args.length > 2) {
            System.out.println("Arguments Missing. Usage:");
            System.out.println("\"[QUERY]\" [TIMEOUT_IN_MINUTES]");
            System.exit(-1);
        } else if (args.length >= 1) {
            query = args[0];
        }
        if (args.length >= 2) {
            sleep = Integer.parseInt(args[1]);
            if (sleep < 1) {
                sleep = 1;
            }
            // milliseconds
            sleep = sleep * 1000 * 60;
        }

        TwitterSearch search = context.getBean(TwitterSearch.class);
        search.setSearchQuery(query);
        TweetSearchCacher searchCacher = context.getBean(TweetSearchCacher.class);
        searchCacher.setSearch(search);
        searchCacher.setSleep(sleep);

        System.out.println("Using query: " + query);
        searchCacher.run();
        System.out.println("Exiting!");
        System.exit(0);
    }
}
