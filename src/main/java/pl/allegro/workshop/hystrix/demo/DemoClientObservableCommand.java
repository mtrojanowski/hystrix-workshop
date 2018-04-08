package pl.allegro.workshop.hystrix.demo;

import org.springframework.web.client.RestTemplate;

// TODO: Extend HystrixObservableCommand and implement
public class DemoClientObservableCommand {

    private final RestTemplate restTemplate;
    private final String url;

    public DemoClientObservableCommand(RestTemplate restTemplate, String url) {
//        super(HystrixCommandGroupKey.Factory.asKey("DemoKey"));
        this.restTemplate = restTemplate;
        this.url = url;
    }

}
