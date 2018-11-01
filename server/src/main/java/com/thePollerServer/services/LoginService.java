package com.thePollerServer.services;

import com.shared.exceptions.database.DatabaseException;
import pollerexpress.database.IDatabaseFacade;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.requests.LoginRequest;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.User;
import com.thePollerServer.utilities.Factory;

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

        //Jack is working right here
        //IDatabaseFacade df = Factory.createDatabaseFacade();
        DatabaseFacade df = new DatabaseFacade();
        try{
            User user = new User(lr.username, lr.password);
            df.register(user);
            return df.login(user);
        }
        catch(DatabaseException e)
        {
            return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", lr.username), null, null));
        }
    }
}
