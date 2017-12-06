package pl.edu.agh.visca;

public class TimeoutException extends Exception {
    public TimeoutException() {
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(String message) {
        super(message);
    }
}