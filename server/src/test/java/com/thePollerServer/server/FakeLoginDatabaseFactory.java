package com.thePollerServer.server;

import com.shared.models.interfaces.IDatabaseFacade;
import com.thePollerServer.utilities.IDatabaseFactory;

public class FakeLoginDatabaseFactory implements IDatabaseFactory
{
    @Override
    public IDatabaseFacade createDatabaseFacade()
    {
        return new FakeLoginDatabaseFacade();
    }
}
