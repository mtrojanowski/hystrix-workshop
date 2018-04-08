package pl.allegro.workshop.hystrix.demo;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DemoConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // TODO: Add servlet configuration
    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixStreamServlet() {
        return new ServletRegistrationBean<>(new HystrixMetricsStreamServlet(), "/hystrix.stream");
    }
}
