package com.shared.exceptions;

import java.io.Serializable;

public class CommandFailed extends Exception implements Serializable
{
    public String methodName;
    public String reason;
    public CommandFailed(String methodName)
    {
        super("Could not run " + methodName);
        this.methodName = methodName;
    }
    public CommandFailed(String methodName, String reason)
    {
        super("Could not run " + methodName + " because " + reason);
        this.methodName = methodName;
        this.reason = reason;
    }
}
