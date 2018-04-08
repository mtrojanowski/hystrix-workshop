package pl.allegro.workshop.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DemoClientCommand extends HystrixCommand<String> {

    private final RestTemplate restTemplate;
    private final String url;

    public DemoClientCommand(RestTemplate restTemplate, String url) {
        super(HystrixCommandGroupKey.Factory.asKey("DemoKey"));
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected String run() throws Exception {
        ResponseEntity<String> response =  restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }

    @Override
    protected String getFallback() {
        return "Couldn't get string from remote!";
    }
}
