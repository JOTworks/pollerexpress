package com.thePollerServer.commandServices;

import pollerexpress.database.IDatabaseFacade;

public class GSDFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new GSDFacade();
    }
}
