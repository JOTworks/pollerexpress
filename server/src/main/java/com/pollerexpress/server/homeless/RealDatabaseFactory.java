package com.pollerexpress.server.homeless;

import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.server.homeless.IDatabaseFactory;

import pollerexpress.database.DatabaseFacade;

public class RealDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new DatabaseFacade();
    }
}
