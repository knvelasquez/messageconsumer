package domain;

public interface JsonService {
    <T> T mapToObject(String json, Class<T> type);
}
