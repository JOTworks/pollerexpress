package com.polarplugin.flatdb.exceptions;

import java.io.IOException;
import java.io.Serializable;

public class GameNotFoundException extends IOException implements Serializable
{


    public GameNotFoundException(String id)
    {
        super("Unable to locate game with id " + id);
    }
}
