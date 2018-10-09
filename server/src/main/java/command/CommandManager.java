package command;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.Player;
import com.pollerexpress.server.homeless.Factory;

import java.util.*;

public class CommandManager {
	private static CommandManager _instance;
	private HashMap<String, Queue<Command>> userCommands;
	
	private CommandManager() {
		userCommands = new HashMap<String, Queue<Command>>();
	}
	
	public static CommandManager _instance() {
		if(_instance == null) {
			_instance = new CommandManager();
		}
		return _instance;
	}
	
	public Queue<Command> getUserCommands(String user) {
	    if(userCommands.containsKey(user))
        {
            Queue<Command> kwayway = userCommands.get(user);
            userCommands.put(user, new LinkedList<>() );
            return kwayway;
        }
        return new LinkedList<>();
	}

    /**
     *
     * @param c command to add client side
     * @param user
     */
	public void addCommand(Command c, Player user)
    {
        Queue<Command> queue = null;
        try
        {
            queue = userCommands.get(user.name);
            if(queue == null)
                throw new NullPointerException();
        }
        catch(NullPointerException e)
        {
            queue = new LinkedList<>();
            userCommands.put(user.name, queue);
        }
        finally
        {
            queue.add(c);
        }
	}

    /**
     *
     * @param c
     * @param info
     * @return
     */
	public boolean addCommand(Command c, GameInfo info)
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        Player[] players;
        try
        {
            players = df.getPlayersInGame(info);
            for(Player p: players)
            {
                System.out.print(p.name);
                addCommand(c, p);
            }
            return true;
        }
        catch(DatabaseException e)
        {
            return false;
        }

    }

    public void addCommand(Command c)
    {
        for(Map.Entry<String, Queue<Command> > entry :userCommands.entrySet())
        {
            entry.getValue().add(c);
        }
    }

    public void addPlayer(Player p)
    {
        Queue<Command> queue = userCommands.get(p.name);
        if(queue == null)
        {
            queue = new LinkedList<>();
            userCommands.put(p.name, queue);
        }
    }

}
