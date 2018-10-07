package command;

import com.pollerexpress.models.Command;
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
	
	public Queue<Command> getUserCommands(String username) {
		//
		return null;
	}
	
	public void addCommand(Command c) {
		//
	}
	
	protected void addCommandToUser(Command c, String username) {
		//
	}

	private void addUser(String username) {
		//
	}
}
