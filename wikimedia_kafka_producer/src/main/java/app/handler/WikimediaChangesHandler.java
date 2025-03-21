package app.handler;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {
        LOGGER.info("Connected to Wikimedia Event Stream.");
        throw new Exception("An error occurred while opening the connection.");

    }

    @Override
    public void onClosed() {
        LOGGER.info("Disconnected from Wikimedia Event Stream.");
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info("event data -> " + messageEvent.getData());
        try{
            kafkaTemplate.send(topic, messageEvent.getData());
        }catch (Exception e){
            throw new Exception("An error occurred while processing the message.");
        }
    }

    @Override
    public void onComment(String comment) throws Exception {
        LOGGER.info("Received comment: {}", comment);
        throw new Exception("An error occurred while processing the comment.");

    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error in Wikimedia event stream", throwable);
    }
}
