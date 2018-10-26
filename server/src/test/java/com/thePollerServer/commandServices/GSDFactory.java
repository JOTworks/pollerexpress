package com.thePollerServer.commandServices;

import com.shared.models.interfaces.IDatabaseFacade;
import com.thePollerServer.utilities.IDatabaseFactory;

public class GSDFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new GSDFacade();
    }
}
