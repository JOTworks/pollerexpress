package pollerexpress.database;

import pollerexpress.database.exceptions.DataNotFoundException;
import pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.ErrorResponse;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;
import com.pollerexpress.models.LoginResponse;

public class DatabaseFacade {
    public DatabaseFacade() {
    }

    /**
     * Log ins an existing user giving them an authtoken to use in their future transactions for this session.
     * @pre user.name user.password is a valid pair in the database
     * @post the return is successful
     * @param user an existing user in the database
     * @return the result of the login attempt
     * @throws DataNotFoundException
     */
    public LoginResponse login(User user)
    {
        try
        {
            Database db = new Database();
            db.open();
            User fromDb = db.getUserDao().read(user.name);
            if (user.password.equals(fromDb.password))
            {
                GameInfo[] info = db.getGameDao().getJoinableGames();
                Authtoken auth = new Authtoken(fromDb);
                fromDb.token = auth;

                db.getAuthtokenDao().write(auth);
                LoginResponse lr = new LoginResponse(auth, info, null);
                return lr;
            }

            db.close(true);
        }
        catch (DataNotFoundException e)
        {
            return new LoginResponse(null,null, new ErrorResponse("Bad Password/user name", null, null));
        }
        catch (DatabaseException e) {
            ;
        }

        return null;
    }

    /**
     * Registers new users in the game.
     * @param user
     * @return
     * @throws DatabaseException
     */
    public LoginResponse register(User user)
    {
        Database db = new Database();
        try
        {
            db.open();
            db.getUserDao().write(user);
            db.close(true);
        }
        catch(DatabaseException e)
        {
            return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", user.name), null, null));
        }
        return this.login(user);
    }
}
