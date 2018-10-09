package com.pollerexpress.models;

import com.pollerexpress.reponse.ErrorResponse;

import java.io.Serializable;
import java.util.Queue;

public class PollResponse implements Serializable
{
    private Queue<Command> commands;
    private ErrorResponse error;

    public PollResponse() {}

    /**
     *
     * @param commands
     * @param error
     */
    public PollResponse(Queue<Command> commands, ErrorResponse error) {
        this.commands = commands;
        this.error = error;
    }

    public Queue<Command> getCommands() { return commands; }

    public ErrorResponse getError() { return error; }

    public void setCommands(Queue<Command> commands) { this.commands = commands; }

    public void setError(ErrorResponse error) { this.error = error; }
}
