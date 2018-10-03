
package pollerexpress.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pollerexpress.database.exceptions.DataNotFoundException;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.Player;

public class GameDao {
    IDatabase _db;
    public static final String SELECT_ALL_GAME_INFO = "SELECT GAME_ID, GAME_NAME, MAX_PLAYERS, CURRENT_PLAYERS\n FROM  GAMES";
    public static final String SELECT_ALL_JOINABLE_GAME_INFO = "SELECT GAME_ID, GAME_NAME, MAX_PLAYERS, CURRENT_PLAYERS\n WHERE NOT MAX_PLAYERS = CURRENT_PLAYERS \n  FROM GAMES";

    public GameDao(IDatabase db) {
        this._db = db;
    }

    /**
     *
     * @param game
     */
    public void write(Game game)
    {
    }

    /**
     *
     * @param id of an active game
     * @return an active game
     * @throws DataNotFoundException
     */
    public Game read(String id) throws DataNotFoundException
    {
        return null;
    }

    /**
     * The list of games that can be joine on the server.
     * @pre None
     * @post does not modify the database.
     * @return can return null
     */
    public GameInfo[] getJoinableGames()
    {
        try
        {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement(SELECT_ALL_JOINABLE_GAME_INFO);
            ResultSet rs = stmnt.executeQuery();
            ArrayList games = new ArrayList();

            while(rs.next())
            {
                if (rs.getInt("CURRENT_PLAYERS") < rs.getInt("MAX_PLAYERS"))
                {
                    games.add(new GameInfo(rs.getString("GAME_ID"), rs.getString("GAME_NAME"), rs.getInt("CURRENT_PLAYERS"), rs.getInt("MAX_PLAYERS")));
                }
            }

            return (GameInfo[])games.toArray(new GameInfo[games.size()]);
        } catch (SQLException var4)
        {
            return null;
        }
    }

    /**
     * The List of all games on ther server.
     * @pre None
     * @post does not modify the database.
     * @return can return null
     */
    public GameInfo[] getGames()
    {
        try
        {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement(SELECT_ALL_GAME_INFO);
            ResultSet rs = stmnt.executeQuery();
            ArrayList games = new ArrayList();

            while(rs.next())
            {
                if (rs.getInt("CURRENT_PLAYERS") < rs.getInt("MAX_PLAYERS"))
                {
                    games.add(new GameInfo(rs.getString("GAME_ID"), rs.getString("GAME_NAME"), rs.getInt("CURRENT_PLAYERS"), rs.getInt("MAX_PLAYERS")));
                }
            }

            return (GameInfo[])games.toArray(new GameInfo[games.size()]);
        } catch (SQLException var4)
        {
            return null;
        }
    }

    /**
     *
     * @param game
     */
    public void deleteGame(Game game)
    {
    }

    /**
     * this might be better in userDao. I am not sure, or maybe i need a PlayerDao.
     * @param gameInfo
     * @return
     */
    public Player[] getPlayers(GameInfo gameInfo) {
        return null;
    }
}
