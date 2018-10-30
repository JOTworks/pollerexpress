package com.shared.models.interfaces;

import com.shared.exceptions.CommandFailed;

public interface ICommand
{
	public Object execute() throws CommandFailed;
}
