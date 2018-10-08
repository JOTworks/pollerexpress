package com.pollerexpress.server;


import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.PollResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

@RestController
public class CommandController {


    @PostMapping("/execute")
    public @ResponseBody
    ResponseEntity<PollResponse> execute(@RequestBody Command command) {
        // Best practice would be to return a fail HttpStatus if I got an error.
        Queue<Command> commands = null;
        try
        {
            Command cmd = command.execute();
            //
        }
        catch(CommandFailed failed)
        {
            //TODO send error msg
        }
        return new ResponseEntity<PollResponse>((PollResponse)commands, HttpStatus.OK);
    }

}