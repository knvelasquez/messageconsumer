package application;

import adapter.repository.MessageConsumerRepository;
import model.MessageConsumerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {
    private final MessageConsumerRepository messageConsumerRepository;

    public MessageConsumerService(MessageConsumerRepository messageConsumerRepository) {
        this.messageConsumerRepository = messageConsumerRepository;
    }

    public Page<MessageConsumerEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageConsumerRepository.findAll(pageable);
    }
}
