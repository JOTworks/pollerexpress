package com.thePollerServer.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

import pollerexpress.database.DatabaseFacade;

public class RealDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new DatabaseFacade();
    }
}
