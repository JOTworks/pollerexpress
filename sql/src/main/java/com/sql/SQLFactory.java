package com.sql;

import com.plugin.IDatabase;
import com.plugin.IPluginFactory;
import com.shared.exceptions.database.DatabaseException;

public class SQLFactory implements IPluginFactory
{
    public IDatabase create() throws DatabaseException
    {
        return new SQLDatabase();
    }
}
