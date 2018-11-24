
package pollerexpress.database.dao;

import com.shared.models.Color;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.shared.exceptions.database.DataAlreadyInDatabaseException;
import com.shared.exceptions.database.DataNotFoundException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.states.GameState;

public class GameDao {
    IDatabase _db;
    public static final String PARAMS = "GAME_ID, GAME_NAME, MAX_PLAYERS, CURRENT_PLAYERS, ACTIVE_PLAYER, SUBSTATE";
    public static final String PARAMS_INSERT = "?,?,?,?,?,?";
    public static final String SELECT_ALL_GAME_INFO = "SELECT *\n FROM  GAMES";
    public static final String SELECT_GAME = " SELECT *\n FROM GAMES \n WHERE GAME_ID = ?";
    public static final String SELECT_ALL_JOINABLE_GAME_INFO = "SELECT *\n FROM GAMES \n WHERE MAX_PLAYERS < CURRENT_PLAYERS";
    public static final String CREATE_NEW_GAME = "INSERT INTO GAMES("+ PARAMS+") \nVALUES("+PARAMS_INSERT+")";
    private String turn;

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
            stmnt.setString( 5, null );
            stmnt.setString( 6, null );
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

                List<Player> players = getPlayers(gi);
                //create the game
                Game game = new Game(gi);//TODO load more data
                game.setPlayers(players);

                /// todo: which one is correct
                //if(rs.getString("SUBSTATE") != null && rs.getString("SUBSTATE").equals("")) {
                if(rs.getString("SUBSTATE") != null) {

                    GameState state = new GameState(rs.getString("ACTIVE_PLAYER"), GameState.State.valueOf(rs.getString("SUBSTATE")));
                    game.setGameState(state);
                }
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
            throw new DataNotFoundException(e.getMessage());
            //throw new DataNotFoundException(id, "GAMES");//TODO change error handling.
        }
        catch(SQLException e)
        {
            throw new DataNotFoundException(e.getMessage());
            //throw new DataNotFoundException(id, "GAMES");
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
                games.add(new GameInfo(rs.getString("GAME_ID"), rs.getString("GAME_NAME"), rs.getInt("MAX_PLAYERS"), rs.getInt("CURRENT_PLAYERS")));

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
            List<Player> players = getPlayers(game);
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
                                            "WHERE USER_NAME = ? and GAME_ID != ?";
    public static final String GAME_ADD = "UPDATE GAMES\n" +
            "SET CURRENT_PLAYERS = CURRENT_PLAYERS + 1 \n"+
            "WHERE GAME_ID = ? and CURRENT_PLAYERS < MAX_PLAYERS";
    public boolean joinGame(Player user, GameInfo info)
    {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(ADD_PLAYER);
            stmnt.setString(1,info.getId() );
            stmnt.setString(2,user.name );
            stmnt.setString(3,info.getId());
            int result = stmnt.executeUpdate();
            stmnt.close();

            if(result != 1) return false;

            stmnt = _db.getConnection().prepareStatement(GAME_ADD);
            stmnt.setString(1,info.getId() );
            result = stmnt.executeUpdate();
            stmnt.close();
            if(result != 1) return false;
            //TODO make sure there is room in the game....



        }
        catch(SQLException e)
        {
            e.printStackTrace();
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

    public static final String UPDATE_TURN = "UPDATE GAMES\n" +
            "SET ACTIVE_PLAYER = ?\n" +
            "WHERE GAME_ID = ?";
    public void updateTurn(String player_name, GameInfo gi) throws DatabaseException {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE_TURN);
            stmnt.setString(1,player_name );
            stmnt.setString(2,gi.getId() );
            stmnt.execute();
            stmnt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static final String UPDATE_SUBSTATE = "UPDATE GAMES\n" +
            "SET SUBSTATE = ?\n" +
            "WHERE GAME_ID = ?";
    public void updateSubState(GameState.State state, GameInfo gi) throws DatabaseException {
        try
        {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE_SUBSTATE);
            stmnt.setString(1,state.name() );
            stmnt.setString(2,gi.getId() );
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
    private List<Player> getPlayers(GameInfo info) throws DatabaseException
    {
        try {
            List<Player> players = new ArrayList<>();
            PreparedStatement stmnt = this._db.getConnection().prepareStatement("SELECT USER_NAME, GAME_ID, COLOR\nFROM USERS\nWHERE GAME_ID = ?");
            stmnt.setString(1, info.getId());
            ResultSet rs = stmnt.executeQuery();

            HashSet<Player> set_p = new HashSet<>();
            HashSet<Color.PLAYER> integers = new HashSet<>();
            int i = 0;
            while(rs.next()) {
                Player p = new Player(rs.getString("USER_NAME"), rs.getString("GAME_ID"));
                int temp = rs.getInt("COLOR");
                if(temp == 0) set_p.add(p);
                else integers.add( Color.convertIndexToColor(temp) );
                p.setColor(Color.convertIndexToColor(temp));
                players.add(p);
            }

            for(Player p: set_p)
            {
                for(int j = 0; j < 5; ++ j)
                {
                    Color.PLAYER c = Color.convertIndexToColor(j);
                    if(!integers.contains(c))
                    {
                        p.setColor(c);
                    }
                    continue;
                }
            }

            rs.close();
            return players;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }


    /**
     * Abby
     * Untested
     * @return The state of the game
     * @throws DatabaseException
     */
    public GameState.State getSubState(GameInfo info) throws DatabaseException {
        try
        {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement(SELECT_GAME);
            stmnt.setString(1, info.getId());
            ResultSet rs = stmnt.executeQuery();


            if (rs.next()) {
                return GameState.State.valueOf(rs.getString("SUBSTATE"));
            }
        } catch (SQLException var4)
        {
            return null;
        }
        return null;
    }

    public String getTurn(GameInfo info) {
        try
        {
            PreparedStatement stmnt = this._db.getConnection().prepareStatement(SELECT_GAME);
            stmnt.setString(1, info.getId());
            ResultSet rs = stmnt.executeQuery();


            if (rs.next()) {
                return rs.getString("ACTIVE_PLAYER");
            }
        } catch (SQLException var4)
        {
            return null;
        }
        return null;
    }
}
