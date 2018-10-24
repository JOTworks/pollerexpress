package pollerexpress.database.dao;

import java.sql.Connection;

public interface IDatabase
{
    Connection getConnection();
    DestinationCardDao getDestinationCardDao();
}
