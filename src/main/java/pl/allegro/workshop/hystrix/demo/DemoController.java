package pl.allegro.workshop.hystrix.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.lang.invoke.MethodHandles;

@RestController
public class DemoController {

    private final DemoService demoService;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/demo")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String getDemo() throws Exception {
        return demoService.getRemoteData();
    }

    @GetMapping("/observable-demo")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DeferredResult<String> getObservableDemo() {
        Observable<String> obs = demoService.getObservableData();
        DeferredResult<String> result = new DeferredResult<>();

        logger.info("Imma in controller!");
        obs
            .subscribeOn(Schedulers.io())
            .subscribe(result::setResult, result::setErrorResult);

        return result;
    }
}
