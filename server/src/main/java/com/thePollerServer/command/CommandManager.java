package com.thePollerServer.command;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.thePollerServer.Model.ServerData;

import java.util.*;

public class CommandManager {

	private static CommandManager _instance;
	private HashMap<String, Queue<Command>> userCommands;
	private ServerData model = ServerData.instance();
	private CommandManager()
    {
		userCommands = new HashMap<String, Queue<Command>>();
	}
	private boolean active = true;
	public static CommandManager _instance() {
		if(_instance == null) {
			_instance = new CommandManager();
		}
		return _instance;
	}

    public void setActive(boolean active){
        this.active = active;
    }
    /** The poller gets this queue of commands and
     * uses them to synchronize the app across
     * players' screens.
     * @param user the client app that wants to synchronize
     *             its screen with the other client screens.
     * @return
     */

	public Queue<Command> getUserCommands(String user)
    {

	    if(userCommands.containsKey(user))
        {
            Queue<Command> kwayway = userCommands.get(user);
            userCommands.put(user, new LinkedList<>() );
            //System.out.print(String.format("Command Manager: %s returned queue %d\n", user, kwayway.size()));
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
       addCommand(c, user.getName());
	}


    /**
     *
     * @param c command to add client side
     * @param user
     */
    private void addCommand(Command c, String user)
    {
        if(!active){System.out.print("CommandManager is not active");}

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
            System.out.print(String.format("added %s command to %s\n", c.getMethodName(),user));
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

        System.out.print("adding command to"+ info.getName() +"\n");


        for(Player p: model.getGame(info).getPlayers())
        {
            addCommand(c, p);
        }
        return true;


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
