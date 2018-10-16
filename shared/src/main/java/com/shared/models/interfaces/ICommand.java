package com.shared.models.interfaces;

import com.shared.exceptions.CommandFailed;

public interface ICommand {
	public void execute() throws CommandFailed;
}
