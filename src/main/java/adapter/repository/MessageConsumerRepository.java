package adapter.repository;

import model.MessageConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageConsumerRepository extends JpaRepository<MessageConsumerEntity, String> {
    MessageConsumerEntity findById(Long id);
}
