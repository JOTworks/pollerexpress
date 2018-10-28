package com.thePollerServer.command;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.Player;
import com.thePollerServer.utilities.Factory;

import java.util.*;

/**
<<<<<<< HEAD
 *
=======
 * What does CommandManager do?
>>>>>>> origin/serverChatBranch
 */
public class CommandManager {

	private static CommandManager _instance;
	private HashMap<String, Queue<Command>> userCommands;

    /**
     * Abby
     * This is a list of all the chatCommands
     * which we want because...because...because why? */
	private ArrayList<Command> chatCommands;
	
	private CommandManager() {
		userCommands = new HashMap<String, Queue<Command>>();
	}
	
	public static CommandManager _instance() {
		if(_instance == null) {
			_instance = new CommandManager();
		}
		return _instance;
	}

    /** The poller gets this queue of commands and
     * uses them to synchronize the app across
     * players' screens.
     * @param user the client app that wants to synchronize
     *             its screen with the other client screens.
     * @return
     */
	public Queue<Command> getUserCommands(String user) {
	    if(userCommands.containsKey(user))
        {
            Queue<Command> kwayway = userCommands.get(user);
            userCommands.put(user, new LinkedList<>() );
            return kwayway; // that's queue, to you
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
     * @param c command to add client side
     * @param user
     */
    private void addCommand(Command c, String user)
    {
        Queue<Command> queue = null;
        try
        {
            queue = userCommands.get(user);
            if(queue == null)
                throw new NullPointerException();
        }
        catch(NullPointerException e)
        {
            queue = new LinkedList<>();
            userCommands.put(user, queue);
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

    /**
     * Adds a command to any user registered on the app
     * This is for commands like createGame, not for
     * commands like chat.
     * @param c
     */
    public void addCommand(Command c)
    {
        for(Map.Entry<String, Queue<Command> > entry :userCommands.entrySet())
        {
            String key = entry.getKey();
            System.out.print("added Command "+c.getMethodName() +" to user " + key + "\n");
            addCommand(c, entry.getKey());
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
