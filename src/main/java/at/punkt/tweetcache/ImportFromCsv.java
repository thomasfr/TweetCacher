package at.punkt.tweetcache;

import at.punkt.tweetcache.domain.Tweet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.document.mongodb.MongoTemplate;

/**
 *
 * @author Thomas Fritz <fritz@punkt.at>
 */
public class ImportFromCsv {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/bootstrap.xml");
        MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

        if (args.length <= 0) {
            System.out.println("No arguments");
            System.exit(-1);
        }
        BufferedReader rd;
        String line;

        String fileName = args[0];


        try {
            rd = new BufferedReader(new FileReader(fileName));
            while ((line = rd.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 9) {
                    continue;
                }
                try {
                    Tweet tweet = new Tweet();
                    if (!"null".equals(parts[0])) {
                        tweet.setid(Long.parseLong(parts[0]));
                    }
                    if (!"null".equals(parts[1])) {
                        tweet.setFromUser(parts[1]);
                    }
                    tweet.setIsoLanguageCode(parts[2]);
                    tweet.setProfileImageUrl(parts[3]);
                    tweet.setSource(parts[4]);
                    tweet.setText(parts[5]);
                    if (!"null".equals(parts[6])) {
                        tweet.setToUserId(Long.parseLong(parts[6]));
                    }
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
                        tweet.setCreatedAt(df.parse(parts[7]));
                    } catch (ParseException ex) {
                        Logger.getLogger(ImportFromCsv.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tweet.setFromUserId(Long.parseLong(parts[8]));
                    System.out.println(tweet);
                    mongoTemplate.insert(tweet);
                } catch (NumberFormatException ex) {
                    continue;
                }
            }
            rd.close();
        } catch (final IOException e) {
            System.out.println("Error reading file");
        }
    }
}
