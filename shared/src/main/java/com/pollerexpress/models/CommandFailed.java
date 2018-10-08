package com.pollerexpress.models;

public class CommandFailed extends Exception
{
    public String methodName;
    public CommandFailed(String methodName)
    {
        super("Could not run " + methodName);
        this.methodName = methodName;
    }
}
