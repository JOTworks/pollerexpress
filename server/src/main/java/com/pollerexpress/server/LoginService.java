package com.pollerexpress.server;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.reponse.ErrorResponse;
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
        try{
            User user = new User(lr.username, lr.password);
            df.register(user);
            return df.login(user);
        }
        catch(DatabaseException e)
        {
            return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", user.name), null, null));
        }
    }
}
