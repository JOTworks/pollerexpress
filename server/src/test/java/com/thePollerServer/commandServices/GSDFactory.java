package com.thePollerServer.commandServices;

public class GSDFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new GSDFacade();
    }
}
