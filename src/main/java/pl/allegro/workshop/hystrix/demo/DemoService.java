package pl.allegro.workshop.hystrix.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rx.Observable;

@Component
public class DemoService {
    private final DemoClient demoClient;

    private final static Logger logger = LoggerFactory.getLogger("DemoService");

    public DemoService(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    public String getRemoteData() throws Exception {
        logger.info("Service running!");
        return demoClient.getRemoteData().get();
    }

    Observable<String> getObservableData() {
        return demoClient.getObservableData();
    }
}
