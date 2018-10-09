package com.pollerexpress.server;

import com.pollerexpress.models.IDatabaseFacade;

public class FakeLoginDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new FakeLoginDatabaseFacade();
    }
}
