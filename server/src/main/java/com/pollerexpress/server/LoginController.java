package com.pollerexpress.server;

import com.pollerexpress.models.LoginRequest;
import com.pollerexpress.models.LoginResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public @ResponseBody
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Best practice would be to return a fail HttpStatus if I got an error.
        return new ResponseEntity<LoginResponse>(
                new LoginService().login(request), HttpStatus.OK);
    }

    //TODO: make register endpoint


    @PostMapping("/register")
    public @ResponseBody
    ResponseEntity<LoginResponse> register(@RequestBody LoginRequest request) {
        // Best practice would be to return a fail HttpStatus if I got an error.
        return new ResponseEntity<LoginResponse>(
                new LoginService().register(request), HttpStatus.OK);
    }
}
