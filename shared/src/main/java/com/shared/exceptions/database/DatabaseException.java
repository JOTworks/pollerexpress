package com.shared.exceptions.database;

import java.io.IOException;

/**
 * GenericDataBase Exception
 */
public class DatabaseException extends IOException
{
    public DatabaseException()
    {
    }

    public DatabaseException(String message)
    {
        super(message);
    }
}