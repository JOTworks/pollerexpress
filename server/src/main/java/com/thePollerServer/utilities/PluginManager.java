package com.thePollerServer.utilities;

import com.plugin.IDatabase;
import com.plugin.IPluginFactory;
import com.shared.exceptions.database.DatabaseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Scanner;
//plugins.config
public class PluginManager
{
    private HashMap<String, PluginData> plugin;
    private String pluginPath = "plugins";
    public PluginManager(String config_path)
    {
        plugin = new HashMap<>();
        try
        {
            Scanner line_by_line = new Scanner(new FileReader(config_path));
            line_by_line.useDelimiter("\n");
            int lc = 0;
            while(line_by_line.hasNext())
            {
                String line = line_by_line.next();
                Scanner tokenizer = new Scanner(line);
                tokenizer.useDelimiter("\\s*,\\s*");
                PluginData data = new PluginData();
                String name = tokenizer.next();
                data.jarPath = tokenizer.next();
                data.classPath = tokenizer.next();

                //quick sanity check to see if it could be a valid plugin.
                if(name.equals("") || data.jarPath.equals("") || data.classPath.equals(""))
                {
                    System.out.print("INVALID CONFIG ON LINE " + String.valueOf(lc) + "\n");
                }
                else
                {
                    plugin.put(name, data);
                }
                lc += 1;
            }
        }
        catch(IOException e)
        {
            //todo throw an error
            e.printStackTrace();
        }
    }

    public IPluginFactory getPluginFactory(String name)
    {
        try
        {
            PluginData plugin = this.plugin.get(name);
            System.out.print(plugin.jarPath +"\n");
            File myJar = new File(this.pluginPath, plugin.jarPath);
            System.out.println(myJar);
            URL pluginURL = myJar.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{pluginURL});
            Class<? extends IPluginFactory> messagePluginClass = (Class<IPluginFactory>) loader.loadClass(plugin.classPath);
            return messagePluginClass.getDeclaredConstructor(null).newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
        /*
        return new IPluginFactory()
        {
            @Override
            public IDatabase create() throws DatabaseException
            {
                throw new DatabaseException("No Database being used");
            }
        };//maybe create a generic plugin that does nothing? is an option*/
    }
}
