package com.shared.exceptions;

import java.io.Serializable;

public class NotImplementedException extends RuntimeException implements Serializable
{
    public String methodName;
    public NotImplementedException(String methodName)
    {
        super(methodName + " is not implemented");
        this.methodName = methodName;
    }
}
