package com.thePollerServer.Model;

import com.plugin.models.ServerGame;
import com.shared.models.Authtoken;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.reponses.LoginResponse;
import com.thePollerServer.Server;
import com.thePollerServer.utilities.PersistenceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.IIOException;

public class ServerData
{
    HashMap<String, User> users= new HashMap<>();
    HashMap<String, ServerGame> games = new HashMap<>();
    HashMap<String, GameInfo> playersToGame = new HashMap<>();
    private ServerData()
    {

    }
    private static ServerData _instance = new ServerData();

    public static ServerData instance()
    {
        return _instance;
    }
    public User getUser(String id)
    {
        return users.get(id);
    }
    public boolean addUser(User u)
    {
        if(users.containsKey(u.getName()))
        {
            return false;
        }
        users.put(u.getName(), u);
        return true;
    }

    public boolean addGame(ServerGame game)
    {
        games.put(game.getId(), game);
        return true;
    }

    public void addGames(List<ServerGame> games)
    {
            for(ServerGame game: games)
            {
                addGame(game);
            }
    }
    public void addUsers(List<User> users)
    {
        for(User u: users)
        {
            addUser(u);
        }
    }

    public List<GameInfo> getGames()
    {
        List<GameInfo> ginfos = new ArrayList<>();
        for(ServerGame game : games.values())
        {
            ginfos.add(game.getGameInfo());
        }
        return ginfos;
    }

    public ServerGame getGame(GameInfo info)
    {
        try
        {
            return games.get(info.getId());
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public ServerGame getGame(Player p)
    {
        try
        {
            return games.get(getMyGame(p).getId());
        }
        catch (Exception e)
        {
            return null;
        }
    }


    public GameInfo getMyGame(Player p)
    {
        return playersToGame.get(p.getName());
    }
    public boolean joinGame(Player p, GameInfo info)
    {
        ServerGame game = games.get(info.getId());
        if(game == null)
        {
            for(GameInfo temp: getGames())
            {
                System.out.print(temp.toString() + "\n");
            }
        }
        boolean could_join = game.join(p);
        if(could_join)
        {
            playersToGame.put(p.getName(), info);
        }
        return could_join;
    }

    public List<ServerGame> getServerGames()
    {
        return new ArrayList<>(games.values());
    }
    public boolean createGame(Player p, GameInfo info)
    {
        ServerGame game = new ServerGame(info);
        games.put(info.getId(), game);
        System.out.print(game.getNumPlayers());
        System.out.println(" - num players");
        return true;
    }

    public LoginResponse login(User user)
    {
        User real = users.get(user.getName());
        if(real == null || !real.password.equals(user.password))
        {
            return new LoginResponse(null, null, new ErrorResponse("BadLogin",new Exception() ,null));
        }
        else
        {
            LoginResponse response = new LoginResponse(new Authtoken(user), new ArrayList<>(getGames()), null);
            return response;
        }
    }

    public void setUserGame(Player p, ServerGame game)
    {
        playersToGame.put(p.getName(), game.getGameInfo());
    }

    public void removeGame(ServerGame game)
    {
        for(Player p: game.getPlayers())
        {
            playersToGame.remove(p.getName());
        }
        games.remove(game.getId());
        try
        {
            new PersistenceProvider(Server.getDelta()).deleteGame(game);
        }
        catch(IOException e)
        {
            //do nothing
        }
    }

}
