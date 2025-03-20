package app;

import app.kafka.WikimediaChangesProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaKafkaProducerApplication implements CommandLineRunner {

    private final WikimediaChangesProducer  wikimediaChangesProducer;
    public WikimediaKafkaProducerApplication(WikimediaChangesProducer wikimediaChangesProducer) {
        this.wikimediaChangesProducer = wikimediaChangesProducer;
    }
    public static void main(String[] args) {
        SpringApplication.run(WikimediaKafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.wikimediaChangesProducer.sendMessage();
    }
}
