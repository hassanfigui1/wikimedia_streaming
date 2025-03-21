package app.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatasourceConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatasourceConsumer.class);

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consume(String message) {
        LOGGER.info("Received event message: " + message);

    }




}
