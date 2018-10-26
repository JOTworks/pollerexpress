//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pollerexpress.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.shared.exceptions.database.DatabaseException;
import pollerexpress.database.dao.AuthtokenDao;
import pollerexpress.database.dao.GameDao;
import pollerexpress.database.dao.IDatabase;
import pollerexpress.database.dao.UserDao;

public class Database implements IDatabase
{
    public static final String DEFAULT_DATABASE = "db.sqlite3";
    public static final String DROP_ALL_TABLES = "drop table *";
    public static final String DROP_AUTH_TOKEN = "drop table if exists AUTH_TOKENS";
    public static final String DROP_GAMES_TOKEN = "drop table if exists GAMES";
    public static final String DROP_USERS = "drop table if exists USERS";
    public static final String USER_TABLE = "USERS";
    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS USERS\n ( `USER_NAME` TEXT NOT NULL UNIQUE, `PASSWORD` TEXT NOT NULL, 'GAME_ID' TEXT, PRIMARY KEY(`USER_NAME`) )";
    public static final String CREATE_AUTHTOKEN_TABLE = "CREATE TABLE IF NOT EXISTS AUTH_TOKENS\n ( `AUTH_ID` TEXT NOT NULL PRIMARY KEY UNIQUE, `USER_NAME` TEXT NOT NULL, FOREIGN KEY(`USER_NAME`) REFERENCES `USERS`(`USER_NAME`) )";
    public static final String GAME_TABLE = " GAMES";
    public static final String CREATE_GAME_TABLE = "CREATE TABLE IF NOT EXISTS  GAMES\n ('GAME_ID' TEXT NOT NULL UNIQUE, 'GAME_NAME' TEXT NOT NULL,'MAX_PLAYERS' INT, 'CURRENT_PLAYERS' INT, PRIMARY KEY('GAME_ID') )";
    final String CONNECTION_URL;
    Connection dataConnection;
    UserDao uDao;
    GameDao gDao;
    AuthtokenDao aDao;
    private boolean isOpen;
    String url;

    /**
     * @pre isOpen = true
     * @post return is an open connection that you can pipe data through.
     * @return
     */
    public Connection getConnection() {
        return this.dataConnection;
    }

    /**
     * Creates a database connection to dataBaseName. This must be an existing sqlite database or a new one.
     * @param dataBaseName
     */

    public Database(String dataBaseName) {
        this.CONNECTION_URL = "jdbc:sqlite:";
        this.isOpen = false;
        this.url = CONNECTION_URL + dataBaseName;
        this.dataConnection = null;
        this.uDao = new UserDao(this);
        this.aDao = new AuthtokenDao(this);
        this.gDao = new GameDao(this);
    }

    public Database() {
        this(DEFAULT_DATABASE);
    }

    /**
     * @pre connection is open
     * @post connection is close
     * @post if commit then the database might have changes
     * @post if not commit then the database will have no changes.
     * @param commit
     */
    public void close(boolean commit) {
        if(!this.isOpen)
        {
            return;
        }
        this.isOpen = false;

        try {
            if (commit) {
                this.dataConnection.commit();
            }

            this.dataConnection.close();
            this.dataConnection = null;
            System.out.print("Closed Database Connection\n");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @pre not open
     * @post now connected to the database.
     * @throws DatabaseException
     *
     *
     *
     *
     *
     */

    public void open() throws DatabaseException {
        if (this.isOpen) {
            throw new DatabaseException("Tried to open an open line");
        } else {
            this.isOpen = true;

            try {
                this.dataConnection = DriverManager.getConnection(this.url);
                this.dataConnection.setAutoCommit(false);
                System.out.println("Created a new connection to the database.");
            } catch (SQLException var2) {
                System.out.printf("%s/n", var2.getStackTrace());
                throw new DatabaseException(var2.getSQLState());
            }
        }
    }

    /**
     * Will create all of the necessary datatables for the server.
     * @pre None
     * @post the server has a full functional db at this.url
     * @throws DatabaseException, this occurs if there is no write premission in the intended filepath.
     */
    public void createTables() throws DatabaseException
    {
        try
        {
            PreparedStatement users_stmnt = this.dataConnection.prepareStatement(CREATE_USER_TABLE) ;
            PreparedStatement authtokens_stmnt = this.dataConnection.prepareStatement(CREATE_AUTHTOKEN_TABLE);
            PreparedStatement games_stmnt = this.dataConnection.prepareStatement(CREATE_GAME_TABLE);
            users_stmnt.execute();
            games_stmnt.execute();
            authtokens_stmnt.execute();
            users_stmnt.close();
            games_stmnt.close();
            authtokens_stmnt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteTables() {

        try {
            PreparedStatement Drop_stmnt = this.dataConnection.prepareStatement(DROP_AUTH_TOKEN);
            Drop_stmnt.execute();
            Drop_stmnt.close();
            Drop_stmnt = this.dataConnection.prepareStatement(DROP_USERS);
            Drop_stmnt.execute();
            Drop_stmnt.close();
            Drop_stmnt = this.dataConnection.prepareStatement(DROP_GAMES_TOKEN);
            Drop_stmnt.execute();
            Drop_stmnt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run this method to create the database on the server.
     * @param Argv not currently used
     */
    public static void main(String[] Argv)
    {
        try
        {
            Database db = new Database();
            db.open();

           //todo:make this dleeat  tables  db.deleateTables();
            db.deleteTables();
            db.createTables();
            db.close(true);;
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

    }

    static
    {
        try
        {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public UserDao getUserDao()
    {
        return this.uDao;
    }

    public AuthtokenDao getAuthtokenDao()
    {
        return this.aDao;
    }

    public GameDao getGameDao()
    {
        return this.gDao;
    }
}
