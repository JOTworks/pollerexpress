package com.thePollerServer.server;

import pollerexpress.database.IDatabaseFacade;

public class FakeLoginDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new FakeLoginDatabaseFacade();
    }
}
