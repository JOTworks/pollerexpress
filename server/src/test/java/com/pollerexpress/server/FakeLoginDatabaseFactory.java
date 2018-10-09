package com.pollerexpress.server;

import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.server.homeless.IDatabaseFactory;

public class FakeLoginDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new FakeLoginDatabaseFacade();
    }
}
