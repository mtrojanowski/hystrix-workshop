package pl.allegro.workshop.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import java.util.concurrent.Future;

@Component
public class DemoClient {
    private static final String REMOTE_URL = "http://localhost:8888/demo";

    private final RestTemplate restTemplate;

    public DemoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO create command using annotation
    Future<String> getRemoteData() {
        HystrixCommand<String> command = new DemoClientCommand(restTemplate, REMOTE_URL);

        return command.queue();
    }

    Observable<String> getObservableData() {
        HystrixObservableCommand<String> command = new DemoClientObservableCommand(restTemplate, REMOTE_URL);

        return command.toObservable();
    }
}
