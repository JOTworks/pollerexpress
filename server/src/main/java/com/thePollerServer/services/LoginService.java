package com.thePollerServer.services;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.requests.LoginRequest;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.User;
import com.thePollerServer.utilities.Factory;

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
            return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", lr.username), null, null));
        }
    }
}
