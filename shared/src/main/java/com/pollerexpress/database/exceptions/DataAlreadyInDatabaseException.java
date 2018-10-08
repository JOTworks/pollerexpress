package com.pollerexpress.database.exceptions;

public class DataAlreadyInDatabaseException extends DatabaseException
{
    public DataAlreadyInDatabaseException(String data, String target)
    {
        super(String.format("%s is already in %s", data, target));
    }
}
