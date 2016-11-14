package soselab.easyelarn.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import soselab.easyelarn.dto.UpdateVersionDTO;

@FeignClient("easylearn-pack")
public interface PackClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/version")
    String updateVersion(@RequestBody String version);
}
