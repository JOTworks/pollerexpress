package com.pollerexpress.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public @ResponseBody
    ResponseEntity<String> test(
            @RequestHeader("AUTH_TOKEN") String world,
            @RequestBody String hi) {

        String response;
        if ((hi + world).equals("Helloworld"))
            response = "Hello spaceman!";
        else
            response = "I'm so sad and alone";

        return ResponseEntity.ok(response);
    }
}
