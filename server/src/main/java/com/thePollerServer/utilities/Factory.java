package com.thePollerServer.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

public class Factory
{
    static IDatabaseFactory _idf = new RealDatabaseFactory();//default db

    public static void setFactory(IDatabaseFactory idf)
    {
        _idf = idf;
    }

    public static IDatabaseFacade createDatabaseFacade()
    {
        return _idf.createDatabaseFacade();
    }
}
