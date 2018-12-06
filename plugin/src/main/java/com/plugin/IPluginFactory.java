package com.plugin;

import com.shared.exceptions.database.DatabaseException;

public interface IPluginFactory
{
    IDatabase create() throws DatabaseException;
}
