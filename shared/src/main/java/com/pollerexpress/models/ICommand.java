package com.pollerexpress.models;

public interface ICommand {
	public ICommand execute() throws CommandFailed;
}
