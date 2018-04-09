package pl.allegro.workshop.hystrix.demo;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import rx.Emitter;
import rx.Observable;

public class DemoClientObservableCommand extends HystrixObservableCommand<String> {

    private final RestTemplate restTemplate;
    private final String url;

    private static final Logger logger = LoggerFactory.getLogger("DemoClientObservableCommand");

    public DemoClientObservableCommand(RestTemplate restTemplate, String url) {
        super(HystrixCommandGroupKey.Factory.asKey("DemoKey"));
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(subscriber -> {
            logger.info("About to try get string from remote!");
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            subscriber.onNext(response.getBody());
        }, Emitter.BackpressureMode.DROP);
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        logger.info("We're in fallback!");
        return Observable.just("Couldn't get string from observable remote!");
    }
}
