package com.thePollerServer.services;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.requests.LoginRequest;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.User;
import com.thePollerServer.Model.ServerData;

public class LoginService
{
    private ServerData model = ServerData.instance();
    public LoginResponse login(LoginRequest lr)
    {
        return model.login(new User(lr.username, lr.password));
    }

    public LoginResponse register(LoginRequest lr)
    {
        //currently all error correction is handled down the line at by the facade.
        //the service is just parsing the data.

        //Jack is working right here
        //IDatabaseFacade df = Factory.createDatabaseFacade();

        User user = new User(lr.username, lr.password);
        if(model.addUser(user))
        {
            return model.login(user);
        }

        return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", lr.username), null, null));

    }
}
