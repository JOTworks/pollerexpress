package com.pollerexpress.server;

import com.pollerexpress.models.IDatabaseFacade;

public interface IDatabaseFactory
{
    IDatabaseFacade createDatabaseFacade();
}
