package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.JsonService;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperJsonService implements JsonService {
    private final ObjectMapper objectMapper;

    public ObjectMapperJsonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T mapToObject(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
