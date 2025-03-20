package app.kafka;

import app.handler.WikimediaChangesHandler;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;


@Service
public class WikimediaChangesProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage() throws InterruptedException {
        LOGGER.info("Sending Wikimedia changes");
        String topic = "wikimedia_recentchange";
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        try (EventSource eventSource = new EventSource.Builder(eventHandler, URI.create(url)).build()) {
            eventSource.start();
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.error("Error occurred while processing the Wikimedia event stream", e);
        }
    }

}
