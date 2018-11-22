package com.shared.exceptions;

public class StateException extends RuntimeException {
    private String attemptedAction;
    private String actualState;
    public StateException(String action, String state) {
        super("Cannot " + action + " in state " + state);
    }
}
