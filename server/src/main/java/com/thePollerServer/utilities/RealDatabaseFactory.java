package com.thePollerServer.utilities;

import pollerexpress.database.IDatabaseFacade;

import pollerexpress.database.DatabaseFacade;

public class RealDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new DatabaseFacade();
    }
}
