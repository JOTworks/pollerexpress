package com.pollerexpress.server;

import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.request.LoginRequest;
import com.pollerexpress.reponse.LoginResponse;
import com.pollerexpress.models.User;

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
