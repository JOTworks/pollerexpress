
package pollerexpress.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pollerexpress.database.exceptions.DataNotFoundException;
import pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.Player;
import com.pollerexpress.models.User;

public class UserDao {
    IDatabase _db;
    static final String WRITE = "insert into USERS(USER_NAME, PASSWORD, GAME_ID)\nvalues(?,?, ?)";
    static final String LOGIN = "select USER_NAME, PASSWORD, GAME_ID \nfrom USERS \nwhere USER_NAME = ?";
    public static final String GET_PLAYERS_IN_GAME = "USER_NAME, GAME_ID\nFROM USERS\nWHERE GAME_ID = ?";

    public UserDao(IDatabase db) {
        this._db = db;
    }

    public void write(String name, String password) throws DatabaseException {
        try {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement("insert into USERS(USER_NAME, PASSWORD, GAME_ID)\nvalues(?,?, ?)");
            stmnt.setString(1, name);
            stmnt.setString(2, password);
            stmnt.setString(3, "");
            stmnt.executeUpdate();
            stmnt.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void write(User u) throws DatabaseException
    {
        write(u.name, u.password);
    }

    public User read(String name) throws DataNotFoundException {
        try {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement("select USER_NAME, PASSWORD, GAME_ID \nfrom USERS \nwhere USER_NAME = ?");
            stmnt.setString(1, name);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("USER_NAME"), rs.getString("PASSWORD"), rs.getString("GAME_ID"));
            } else {
                throw new DataNotFoundException(name, "USERS");
            }
        } catch (SQLException var4) {
            return null;
        }
    }

    public void joinGame(User user, GameInfo info) {
    }

    public void leaveGame(User user, GameInfo info) {
    }

    public Player[] getPlayersIngame(GameInfo info) throws DatabaseException {
        try {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement("USER_NAME, GAME_ID\nFROM USERS\nWHERE GAME_ID = ?");
            stmnt.setString(1, info.getId());
            ResultSet rs = stmnt.executeQuery();
            ArrayList players = new ArrayList();

            while(rs.next()) {
                Player p = new Player(rs.getString("USER_NAME"), rs.getString("GAME_ID"));
                players.add(p);
            }

            rs.close();
            return (Player[])players.toArray(new Player[players.size()]);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
