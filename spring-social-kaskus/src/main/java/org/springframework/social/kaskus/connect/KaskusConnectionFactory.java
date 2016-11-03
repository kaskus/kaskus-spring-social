package org.springframework.social.kaskus.connect;

import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.kaskus.api.Kaskus;

public class KaskusConnectionFactory extends OAuth1ConnectionFactory<Kaskus> {

    public KaskusConnectionFactory(String consumerKey, String consumerSecret, String apiUrl) {
        super("kaskus", new KaskusServiceProvider(consumerKey, consumerSecret, apiUrl), new KaskusAdapter());
    }
}
