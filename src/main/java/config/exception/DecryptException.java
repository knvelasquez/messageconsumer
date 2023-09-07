package config.exception;

public class DecryptException extends RuntimeException {
    public DecryptException() {
        super("Itś not possible to decrypt message");
    }
}
