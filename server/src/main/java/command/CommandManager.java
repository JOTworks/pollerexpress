package command;

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
	
	public Queue<Command> addCommand(Command c, String user) {
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

	protected void addCommandToAllUsers(Command c) {
		for(Map.Entry<String, Queue<Command>> uq : userCommands.entrySet()) {
			// String user = uq.getKey();
			Queue<Command> kwayway = uq.getValue();
			kwayway.add(c);
		}
	}

	private void addUser(String user) {
		Queue<Command> kwayway = new LinkedList<Command>();
		userCommands.put(user, kwayway);
	}
}
