package pl.allegro.workshop.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DemoClient {
    private static final String REMOTE_URL = "http://localhost:8888/demo";

    private final RestTemplate restTemplate;

    public DemoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String getRemoteData() {
        HystrixCommand<String> command = new DemoClientCommand(restTemplate, REMOTE_URL);

        return command.execute();
    }
}
