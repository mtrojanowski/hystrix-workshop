package pl.allegro.workshop.hystrix.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rx.Emitter;
import rx.Observable;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Future;

@Component
public class DemoClient {
    private static final String REMOTE_URL = "http://localhost:8888/demo";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final RestTemplate restTemplate;

    public DemoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO: configure thread pool and then semaphore
    @HystrixCommand(
        groupKey = "myGroup",
        commandKey = "myCommandGetRemoteData",
        fallbackMethod = "getRemoteDataFallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "8000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),

//            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD")
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "3")

        },
        threadPoolKey = "myWorkshopPool",
//        threadPoolProperties = {
//            @HystrixProperty(name="coreSize", value="3")
//        },
        ignoreExceptions = { HttpClientErrorException.class }
    )
    String getRemoteData() {
        ResponseEntity<String> response = restTemplate.getForEntity(REMOTE_URL, String.class);

        return response.getBody();
    }

    @HystrixCommand(
        groupKey = "myGroup",
        commandKey = "myCommandGetRemoteData",
        fallbackMethod = "getRemoteDataFallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
        }
    )
    Future<String> getFutureRemoteData() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                ResponseEntity<String> response = restTemplate.getForEntity(REMOTE_URL, String.class);
                return response.getBody();
            }
        };
    }

    String getRemoteDataFallback() {
        return "Couldn't get data from remote :(";
    }


    @HystrixCommand(
        groupKey = "myGroup",
        commandKey = "myCommandGetRemoteData",
        fallbackMethod = "getRemoteDataObservableFallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
        },
        observableExecutionMode = ObservableExecutionMode.LAZY
    )
    Observable<String> getObservableData() {
        return Observable.create((subscriber -> {
            ResponseEntity<String> response = restTemplate.getForEntity(REMOTE_URL, String.class);
            subscriber.onNext(response.getBody());
        }), Emitter.BackpressureMode.DROP);
    }

    Observable<String> getRemoteDataObservableFallback() {
        return Observable.just("Couldn't observe data :(");
    }
}
