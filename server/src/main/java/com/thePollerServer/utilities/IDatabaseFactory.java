package com.thePollerServer.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

public interface IDatabaseFactory
{
    IDatabaseFacade createDatabaseFacade();
}
