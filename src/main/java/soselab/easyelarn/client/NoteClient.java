package soselab.easyelarn.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("easylearn-note")
public interface NoteClient {
    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json; charset=UTF-8")
    void addNote(@RequestBody String note);
}
