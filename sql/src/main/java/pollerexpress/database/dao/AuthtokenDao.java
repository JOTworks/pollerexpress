package pollerexpress.database.dao;



import com.pollerexpress.models.Authtoken;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthtokenDao
{

    public static String AUTH_TOKEN_TABLE = "AUTH_TOKENS";
    IDatabase _db;

    public AuthtokenDao(IDatabase db)
    {
        this._db = db;
    }


    public static String WRITE_TOKEN = "INSERT INTO " + AUTH_TOKEN_TABLE +"(AUTH_ID,USER_NAME)\n" +
            "values(?,?)";
    /**
     *
     * @param token
     */
    public void write(Authtoken token)
    {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(WRITE_TOKEN);
            stmnt.setString(0, token.getToken());
            stmnt.setString(1,token.getUserName());
            stmnt.execute();
            stmnt.close();
        }
        catch(SQLException e)
        {
            //todo handl errors, prolly pass something up the food chain.
        }
    }

    public boolean validate(Authtoken token) {
            return true;
        }

    public void delete(Authtoken token)
    {

    }
}

