package application;

import adapter.repository.MessageConsumerRepository;
import domain.JsonService;
import model.MessageConsumerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private final DecryptService decryptService;
    private final JsonService jsonService;
    private final MessageConsumerRepository messageConsumerRepository;

    public KafkaListenerService(DecryptService decryptService, JsonService jsonService, MessageConsumerRepository messageConsumerRepository) {
        this.decryptService = decryptService;
        this.jsonService = jsonService;
        this.messageConsumerRepository = messageConsumerRepository;
    }

    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void listenFromQueue(String message) {
        messageConsumerRepository.save(new MessageConsumerEntity(message, 1));
        StringBuilder logMessage = new StringBuilder("Obtained from queue: ")
                .append(message.substring(0, 10))
                .append("...");
        logger.info(logMessage.toString());
    }
}
