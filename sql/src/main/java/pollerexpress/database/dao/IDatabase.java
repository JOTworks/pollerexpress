package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;

import java.sql.Connection;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.TrainCardDao;

public interface IDatabase
{
    Connection getConnection();
}
