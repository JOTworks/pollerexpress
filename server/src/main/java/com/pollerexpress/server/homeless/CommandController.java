package com.pollerexpress.server.homeless;


import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.PollResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

import command.CommandManager;

@RestController
public class CommandController {


    @PostMapping("/execute")
    public @ResponseBody
    ResponseEntity<PollResponse> execute(
            @RequestHeader("AUTH_TOKEN") String token,
            @RequestHeader("USERNAME") String username,
            @RequestBody Command command) {
        // Best practice would be to return a fail HttpStatus if I got an error.
        try {validateAuth(username, token);} catch (Exception e) {}//TODO: make sure you are returning the right thing if you get a bad response

        Queue<Command> commands = null;
        try
        {
            Command cmd = command.execute();
            //
            CommandManager._instance();
        }
        catch(CommandFailed failed)
        {
            //TODO send error msg somehow.
            //if the command execute fails, still send the poll response.
        }
        return new ResponseEntity<PollResponse>((PollResponse)commands, HttpStatus.OK);
    }



    @PostMapping("/poll")
    public @ResponseBody
    ResponseEntity<PollResponse> poll(
                    @RequestHeader("AUTH_TOKEN") String token,
                    @RequestBody Command command) {

        return ResponseEntity.ok(new PollResponse()); //TODO: implement the poll endpoint
    }

    /**
     * Creates a new Player object
     * @param username a username to validate
     * @param token a token to validate
     *
     * @pre username is not null
     * @pre token is not null
     * @post throws an exception if there are any problems with the database or if the token is not valid
     */
    private void validateAuth(String username, String token) throws Exception {
        Authtoken auth = new Authtoken(username, token);

        Boolean isValidAuth = Factory.createDatabaseFacade().validate(auth);

        if (!isValidAuth)
            throw new SecurityException("Access denied: user " + username + " with token " + token +
                                        "not a valid combination");
        return;
    }
}