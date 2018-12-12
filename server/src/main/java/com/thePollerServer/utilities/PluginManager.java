package com.thePollerServer.utilities;

import com.plugin.ICommandDao;
import com.plugin.IDatabase;
import com.plugin.IGameDao;
import com.plugin.IPluginFactory;
import com.plugin.IUserDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.plugin.models.ServerGame;
import com.shared.models.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
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

        return new IPluginFactory()
        {
            @Override
            public IDatabase create() throws DatabaseException
            {
                return new IDatabase()
                {
                    @Override
                    public IUserDao getUserDao()
                    {
                        return new IUserDao()
                        {
                            @Override
                            public User getUser(String username) throws IOException
                            {
                                return null;
                            }

                            @Override
                            public List<User> getAllUsers() throws IOException {
                                return null;
                            }

                            @Override
                            public void addUser(User user) throws IOException
                            {

                            }

                            @Override
                            public void updateUser(User user) throws IOException
                            {

                            }
                        };
                    }

                    @Override
                    public IGameDao getGameDao()
                    {
                        return new IGameDao()
                        {
                            @Override
                            public ServerGame getGame(String id) throws IOException
                            {
                                return null;
                            }

                            @Override
                            public List<ServerGame> getAllGames() throws IOException
                            {
                                return null;
                            }

                            @Override
                            public void addGame(ServerGame game) throws IOException
                            {

                            }

                            @Override
                            public void updateGame(ServerGame game) throws IOException
                            {

                            }

                            @Override
                            public void deleteGame(ServerGame game) throws IOException
                            {

                            }
                        };
                    }

                    @Override
                    public ICommandDao getCommandDao()
                    {
                        return new ICommandDao()
                        {
                            @Override
                            public List<Command> getCommands(String gameId) throws IOException
                            {
                                return null;
                            }

                            @Override
                            public void addCommand(Command c, String gameId) throws IOException
                            {

                            }

                            @Override
                            public void removeCommands(String gameId) throws IOException
                            {

                            }
                        };
                    }

                    @Override
                    public void startTransaction() throws IOException
                    {

                    }

                    @Override
                    public void endTransaction(boolean commit) throws IOException
                    {

                    }

                    @Override
                    public void resetDatabase() throws IOException
                    {

                    }
                };
            }
        };//maybe create a generic plugin that does nothing? is an option*/
    }
}
