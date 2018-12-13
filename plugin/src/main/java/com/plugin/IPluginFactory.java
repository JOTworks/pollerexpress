package com.plugin;

import java.io.IOException;

public interface IPluginFactory
{
    IDatabase create() throws IOException;
}
