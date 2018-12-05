package com.polarplugin.flatdb.exceptions;

import java.io.IOException;
import java.io.Serializable;

public class DeleteFailedException extends IOException implements Serializable
{


    public DeleteFailedException(String obj)
    {
        super("Unable to delete " + obj);
    }
}
