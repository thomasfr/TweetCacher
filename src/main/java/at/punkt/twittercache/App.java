package at.punkt.twittercache;

/**
 *
 */
public class App extends Thread {

    private static String defaultQuery = "@semwebcompany";

    //Display a message, preceded by the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    public static void main(String[] args) throws InterruptedException {
        String query = defaultQuery;
        int sleep = 60000;
        if (args.length >= 1) {
            query = args[0];
        }

        TwitterSearchCacheThread thread = new TwitterSearchCacheThread();
        thread.setDaemon(true);

        if (args.length >= 2) {
            String fileName = args[1];
            thread.setFileName(args[1]);
            thread.setName(fileName);
        }

        if(args.length >= 3) {
            sleep = Integer.parseInt(args[2]);
            if(sleep < 1) {
                sleep = 1;
            }
            // milliseconds
            sleep = sleep*1000*60;
        }
        
        thread.setSleep(sleep);
        thread.setQuery(query);

        thread.start();
        threadMessage("Using query: " + query);
        while (thread.isAlive()) {
        }
        threadMessage("Exiting!");
        System.exit(0);
    }
}
