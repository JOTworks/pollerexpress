package com.thePollerServer.services;

import com.plugin.models.ServerGame;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.requests.LoginRequest;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.User;
import com.thePollerServer.Model.ServerData;
import com.thePollerServer.Server;
import com.thePollerServer.command.CommandFacade;
import com.thePollerServer.command.CommandManager;
import com.thePollerServer.utilities.PersistenceProvider;
import java.io.IOException;

public class LoginService
{
    private ServerData model = ServerData.instance();
    public LoginResponse login(LoginRequest lr)
    {
        User u = new User(lr.username, lr.password);
        LoginResponse response = model.login(u);
        ServerGame game = model.getGame(u);
        if(game != null)
        {
            if(!CommandManager._instance().getUserHasResync(u))
            {
                CommandFacade.recync(u);
            }
        }
        return response;
    }

    public LoginResponse register(LoginRequest lr) throws IOException {
        //currently all error correction is handled down the line at by the facade.
        //the service is just parsing the data.

        //Jack is working right here
        //IDatabaseFacade df = Factory.createDatabaseFacade();

        User user = new User(lr.username, lr.password);

        if(model.addUser(user))
        {
            try
            {
                new PersistenceProvider(Server.getDelta()).register(user);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return model.login(user);
        }

        return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", lr.username), null, null));

    }
}
