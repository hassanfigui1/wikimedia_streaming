package app.kafka;

import app.payload.WikimediaData;
import app.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatasourceConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatasourceConsumer.class);
    private final WikimediaDataRepository wikimediaDataRepository;

    public KafkaDatasourceConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consume(String message) {
        LOGGER.info("Received and STORED message: " + message);
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(message);
        wikimediaDataRepository.save(wikimediaData);
    }




}
