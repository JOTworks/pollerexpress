package com.pollerexpress.models;

public interface ICommand {
	public void execute() throws CommandFailed;
}
