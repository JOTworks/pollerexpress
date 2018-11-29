package com.thePollerServer.server;

public class FakeLoginDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new FakeLoginDatabaseFacade();
    }
}
