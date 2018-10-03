package com.pollerexpress.server;

import com.pollerexpress.models.LoginRequest;
import com.pollerexpress.models.LoginResponse;
import com.pollerexpress.models.User;

import pollerexpress.database.DatabaseFacade;

public class LoginService
{
    public LoginResponse login(LoginRequest lr)
    {
        DatabaseFacade df = new DatabaseFacade();
        return df.login(new User(lr.username, lr.password));
    }

    public LoginResponse register(LoginRequest lr)
    {
        //currently all error correction is handled down the line at by the facade.
        //the service is just parsing the data.
        DatabaseFacade df = new DatabaseFacade();
        return df.register(new User(lr.username, lr.password));
    }
}
