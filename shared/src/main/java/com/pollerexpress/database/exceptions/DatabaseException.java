package com.pollerexpress.database.exceptions;

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