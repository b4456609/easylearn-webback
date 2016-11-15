package soselab.easyelarn.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("easylearn-pack")
public interface PackClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/version", consumes = "application/json; charset=UTF-8")
    void updateVersion(@RequestBody String version);
}
