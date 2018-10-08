package com.pollerexpress.server;

import com.pollerexpress.models.IDatabaseFacade;

import pollerexpress.database.DatabaseFacade;

public class RealDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new DatabaseFacade();
    }
}
