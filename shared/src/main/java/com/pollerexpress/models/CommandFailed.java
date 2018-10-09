package com.pollerexpress.models;

import java.io.Serializable;

public class CommandFailed extends Exception implements Serializable
{
    public String methodName;
    public CommandFailed(String methodName)
    {
        super("Could not run " + methodName);
        this.methodName = methodName;
    }
}
