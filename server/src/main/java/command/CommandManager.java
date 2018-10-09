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
	private CommandSwitch _switch;
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
		Queue<Command> kwayway = userCommands.get(user);
		return kwayway;
	}
	
	public void addCommand(Command c, Player user) throws DatabaseException {
		Queue<Command> queue = userCommands.get(user.name);
		queue.add(c);
	}

	public void addCommand(Command c, GameInfo info)
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        Player[] players = df.
    }

}
