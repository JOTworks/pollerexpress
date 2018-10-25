package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;

import java.sql.Connection;

public interface IDatabase
{
    void open() throws DatabaseException;
    void close(boolean commit);
    Connection getConnection();
    DestinationCardDao getDestinationCardDao();
}
