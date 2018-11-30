package com.shared.exceptions;

import java.io.Serializable;

public class NoCardToDrawException extends Exception implements Serializable
{
    public String methodName;
    public NoCardToDrawException(String cardType)
    {
        super("cannot draw " + cardType + " because there are no more in the deck");
        this.methodName = methodName;
    }
}
