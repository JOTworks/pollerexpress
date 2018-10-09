package com.pollerexpress.server.homeless;

import com.pollerexpress.models.IDatabaseFacade;

public interface IDatabaseFactory
{
    IDatabaseFacade createDatabaseFacade();
}
