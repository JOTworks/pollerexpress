package com.thePollerServer.Model;

import com.shared.models.Authtoken;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.reponses.LoginResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerData
{
    HashMap<String, User> users= new HashMap<>();
    HashMap<GameInfo, ServerGame> games = new HashMap<>();
    HashMap<String, GameInfo> playersToGame = new HashMap<>();
    private ServerData()
    {

    }
    private static ServerData _instance = new ServerData();

    public static ServerData instance()
    {
        return _instance;
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
        games.put(game.getGameInfo(), game);
        return true;
    }

    public List<GameInfo> getGames()
    {
        return new ArrayList<>(games.keySet());
    }

    public ServerGame getGame(GameInfo info)
    {
        return games.get(info);
    }

    public ServerGame getGame(Player p)
    {
        return games.get(getMyGame(p));
    }


    public GameInfo getMyGame(Player p)
    {
        return playersToGame.get(p.getName());
    }
    public boolean joinGame(Player p, GameInfo info)
    {
        ServerGame game = games.get(info);
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

    public boolean createGame(Player p, GameInfo info)
    {
        ServerGame game = new ServerGame(info);
        games.put(info, game);
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

}
