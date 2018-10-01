package com.pollerexpress.server;


import com.pollerexpress.models.Command;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {


    @PostMapping("/execute")
    public @ResponseBody
    ResponseEntity<PollResponse> execute(@RequestBody Command command) {
        // Best practice would be to return a fail HttpStatus if I got an error.
        return new ResponseEntity<PollResponse>((PollResponse)command.execute(), HttpStatus.OK);
    }

}