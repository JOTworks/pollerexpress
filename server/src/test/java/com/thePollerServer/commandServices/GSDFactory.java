package com.thePollerServer.commandServices;

import pollerexpress.database.IDatabaseFacade;
import com.thePollerServer.utilities.IDatabaseFactory;

public class GSDFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new GSDFacade();
    }
}
