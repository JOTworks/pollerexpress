package com.polarplugin.flatdb;

import com.plugin.IDatabase;
import com.plugin.IPluginFactory;
import com.shared.exceptions.database.DatabaseException;

import java.io.IOException;

public class FlatFactory implements IPluginFactory
{

    @Override
    public IDatabase create() throws IOException
    {
        return new FlatDatabase();
    }
}
