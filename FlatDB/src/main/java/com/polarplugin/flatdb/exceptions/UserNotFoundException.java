package com.polarplugin.flatdb.exceptions;

import java.io.IOException;
import java.io.Serializable;

public class UserNotFoundException extends IOException implements Serializable
{


    public UserNotFoundException(String username)
    {
        super("Unable to locate user with name " + username);
    }
}
