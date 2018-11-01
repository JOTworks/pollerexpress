package com.thePollerServer.utilities;

import pollerexpress.database.IDatabaseFacade;

public interface IDatabaseFactory
{
    IDatabaseFacade createDatabaseFacade();
}
