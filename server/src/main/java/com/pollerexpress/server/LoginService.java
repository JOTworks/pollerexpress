package com.pollerexpress.server;

import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.LoginRequest;
import com.pollerexpress.models.LoginResponse;
import com.pollerexpress.models.User;

import pollerexpress.database.Database;
import pollerexpress.database.DatabaseFacade;

public class LoginService
{
    public LoginResponse login(LoginRequest lr)
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        return df.login(new User(lr.username, lr.password));
    }

    public LoginResponse register(LoginRequest lr)
    {
        //currently all error correction is handled down the line at by the facade.
        //the service is just parsing the data.
        IDatabaseFacade df = Factory.createDatabaseFacade();
        return df.register(new User(lr.username, lr.password));
    }
}
