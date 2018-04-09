package pl.allegro.workshop.hystrix.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import rx.Observable;

@Component
public class DemoService {
    private final DemoClient demoClient;

    private final static Logger logger = LoggerFactory.getLogger("DemoService");

    public DemoService(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    public String getRemoteData() throws Exception {
        String data;
        try {
            data = demoClient.getRemoteData();
        } catch (HttpClientErrorException e) { // This is not how it should be done ;)
            data = "No data...";
        }

        return data;
    }


    public String getFutureRemoteData() throws Exception {
        return demoClient.getFutureRemoteData().get();
    }

    Observable<String> getObservableData() {
        return demoClient.getObservableData();
    }
}
