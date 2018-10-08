
package pollerexpress.database.dao;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.pollerexpress.database.exceptions.DataAlreadyInDatabaseException;
import com.pollerexpress.database.exceptions.DataNotFoundException;
import com.pollerexpress.database.exceptions.DatabaseException;

public class GameDao {
    IDatabase _db;
    public static final String PARAMS = "GAME_ID, GAME_NAME, MAX_PLAYERS, CURRENT_PLAYERS";
    public static final String PARAMS_INSERT = "?,?,?,?";
    public static final String SELECT_ALL_GAME_INFO = "SELECT GAME_ID, GAME_NAME, MAX_PLAYERS, CURRENT_PLAYERS\n FROM  GAMES";
    public static final String SELECT_GAME = " SELECT " + PARAMS + "\nWHERE GAME_ID = ?\n From GAMES";
    public static final String SELECT_ALL_JOINABLE_GAME_INFO = "SELECT GAME_ID, GAME_NAME, MAX_PLAYERS, CURRENT_PLAYERS\n WHERE NOT MAX_PLAYERS = CURRENT_PLAYERS \n  FROM GAMES";
    public static final String CREATE_NEW_GAME = "INSERT INTO GAMES("+ PARAMS+") \nVALUES("+PARAMS_INSERT+")";
    public GameDao(IDatabase db) {
        this._db = db;
    }

    /**
     * @pre Game not in database
     * @post Game is now in database.
     * @param game
     */
    public void write(Game game) throws DataAlreadyInDatabaseException
    {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(CREATE_NEW_GAME);
            stmnt.setString( 1, game.getId() );
            stmnt.setString( 2,game.getName() );
            stmnt.setInt( 3,game.getGameInfo().getMaxPlayers() );
            stmnt.setInt( 4,game.getGameInfo().getNumPlayers() );
            stmnt.execute();
            stmnt.close();
        }
        catch(SQLException e)
        {
            throw new DataAlreadyInDatabaseException(game.getId(),"GAMES");
        }
    }

    /**
     *
     * @param id of an active games
     * @return an active game
     * @throws DataNotFoundException
     */
    public Game read(String id) throws DataNotFoundException
    {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_GAME);
            stmnt.setString(1, id);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next())
            {
                //first get the game info for the game.
                GameInfo gi = new GameInfo(rs.getString("GAME_ID"), rs.getString("GAME_NAME"), rs.getInt("MAX_PLAYERS"), rs.getInt("CURRENT_PLAYERS") );
                //get a list of players in the game.
                Player[] players = getPlayers(gi);
                //create the game
                Game game = new Game(gi, players);//TODO load more data
                rs.close();
                stmnt.close();
                return game;
            }
            rs.close();
            stmnt.close();//TODO there refactor to a more correct java way of doing this try block.
            throw new DataNotFoundException(id, "GAMES");
        }
        catch (DatabaseException e)
        {
            throw new DataNotFoundException(id, "GAMES");//TODO change error handling.
        }
        catch(SQLException e)
        {
            throw new DataNotFoundException(id, "GAMES");
        }
    }

    public Game read(GameInfo info) throws DataNotFoundException
    {
        return read(info.getId());
    }

    /**
     * The list of games that can be joine on the server.
     * @pre None
     * @post does not modify the database.
     * @return can return null
     */
    public ArrayList<GameInfo> getJoinableGames()
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
            GameInfo[] array = (GameInfo[])games.toArray(new GameInfo[games.size()]);
            return new ArrayList<GameInfo>(Arrays.asList(array));
        } catch (SQLException var4)
        {
            return null;//maybe return an array of size 0? though that doesn't exist.
        }
    }

    /**
     * The List of all games on ther server.
     * @pre None
     * @post does not modify the database.
     * @return can return null
     */
    public ArrayList<GameInfo> getGames()
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
            rs.close();
            stmnt.close();
            GameInfo[] array = (GameInfo[])games.toArray(new GameInfo[games.size()]);
            return new ArrayList<GameInfo>(Arrays.asList(array));
        } catch (SQLException var4)
        {
            return null;
        }
    }

    public static final String DELETE = "DELETE FROM GAMES WHERE GAME = ?";
    /**
     *
     * @param game
     */
    public void deleteGame(GameInfo game)
    {
        //first delete all player references to a game
        try
        {
            Player[] players = getPlayers(game);
            for(Player player:players)
            {
                leaveGame(player, game);
            }
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DELETE);
            stmnt.setString(1, game.getId());
            stmnt.execute();
            stmnt.close();
        }
        catch(SQLException e)
        {

        }
        catch(DatabaseException e)
        {

        }

        //now delete the game
    }

    public static final String ADD_PLAYER = "UPDATE USERS\n " +
                                            "SET GAME_ID = ?\n" +
                                            "WHERE USER = ?";
    public static final String GAME_ADD = "UPDATE GAMES\n" +
                                                    "SET CURRENT_PLAYERS = CURRENT_PLAYERS + 1\n" +
                                                    "WHERE GAME_ID = ?";
    public boolean joinGame(Player user, GameInfo info)
    {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(ADD_PLAYER);
            stmnt.setString(1,info.getId() );
            stmnt.setString(2,user.name );
            stmnt.execute();
            stmnt.close();

            stmnt = _db.getConnection().prepareStatement(GAME_ADD);
            stmnt.setString(1,info.getId() );
            stmnt.execute();
            stmnt.close();
            //TODO make sure there is room in the game....



        }
        catch(SQLException e)
        {
            return false;
        }
        return true;
    }



    public static final String GAME_REMOVE = "UPDATE GAMES\n" +
            "SET CURRENT_PLAYERS = CURRENT_PLAYERS - 1\n" +
            "WHERE GAME_ID = ?";
    public void leaveGame(Player user, GameInfo info) throws DatabaseException
    {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(ADD_PLAYER);
            stmnt.setString(1,null);
            stmnt.setString(2,user.name );
            stmnt.execute();
            stmnt.close();

            stmnt = _db.getConnection().prepareStatement(GAME_REMOVE);
            stmnt.setString(1,info.getId() );
            stmnt.execute();
            stmnt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    /**
     * Used by read Game and delete game.
     * @param info
     * @return
     */
    private Player[] getPlayers(GameInfo info) throws DatabaseException
    {
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
