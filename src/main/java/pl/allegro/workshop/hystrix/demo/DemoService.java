package pl.allegro.workshop.hystrix.demo;

import org.springframework.stereotype.Component;

@Component
public class DemoService {
    private final DemoClient demoClient;

    public DemoService(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    public String getRemoteData()
    {
        // TODO - call remote service through hystrix command
        return demoClient.getRemoteData();
    }
}
