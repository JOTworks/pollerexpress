package com.pollerexpress.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginServiceTest
{

    @Before
    public void setUp() throws Exception
    {
        Factory.setFactory(new FakeLoginDatabaseFactory());
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void login()
    {

    }

    @Test
    public void register()
    {

    }
}