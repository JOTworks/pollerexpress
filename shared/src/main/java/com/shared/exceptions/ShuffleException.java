package com.shared.exceptions;

public class ShuffleException extends RuntimeException {
    public String message;
    public ShuffleException(){
        super("There are not enough cards in the deck for this action.");
    }
    public ShuffleException(String message)
    {
        super(message);
        this.message = message;
    }
}
