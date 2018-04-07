package pl.allegro.workshop.hystrix.demo;

public class DemoException extends RuntimeException {
    public DemoException(String message) {
        super(message);
    }
}
