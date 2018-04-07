package pl.allegro.workshop.hystrix.demo;

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
        // TODO - call remote service through hystrix command
        ResponseEntity<String> response = restTemplate.getForEntity(REMOTE_URL, String.class);

        return response.getBody();
    }
}
