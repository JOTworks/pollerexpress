package pollerexpress.database;

import com.shared.exceptions.database.DatabaseException;

import java.sql.Connection;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.TrainCardDao;

public interface IDatabase
{
    void open() throws DatabaseException;
    void close(boolean commit);
    Connection getConnection();
    DestinationCardDao getDestinationCardDao();
    TrainCardDao getTrainCardDao();
}
