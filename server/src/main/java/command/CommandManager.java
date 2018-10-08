package command;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Command;
import java.util.*;

public class CommandManager {
	private static CommandManager _instance;
	private CommandSwitch _switch;
	private HashMap<String, Queue<Command>> userCommands;
	
	private CommandManager() {
		userCommands = new HashMap<String, Queue<Command>>();
		_switch = new CommandSwitch();
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
	
	public Queue<Command> addCommand(Command c, String user) throws DatabaseException {
		_switch.switchCommand(c, user);
		Queue<Command> kwayway = userCommands.get(user);
		return kwayway;
	}
	
	protected void addCommandToUser(Command c, String user) {
		Queue kwayway = userCommands.get(user);
		if(kwayway == null) {
			this.addUser(user);
			kwayway = userCommands.get(user);
		}
		kwayway.add(c);
	}

	protected void addToAll(Command c) {
		for(Map.Entry<String, Queue<Command>> uq : userCommands.entrySet()) {
			Queue<Command> kwayway = uq.getValue();
			kwayway.add(c);
		}
	}

	protected void addToAllExcept(Command c, String user) {
		for(Map.Entry<String, Queue<Command>> uq : userCommands.entrySet()) {
			String username = uq.getKey();
			if(username != user) {
				Queue<Command> kwayway = uq.getValue();
				kwayway.add(c);
			}
		}
	}

	private void addUser(String user) {
		Queue<Command> kwayway = new LinkedList<Command>();
		userCommands.put(user, kwayway);
	}
}
