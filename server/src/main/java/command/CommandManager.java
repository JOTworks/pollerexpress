package command;

import com.pollerexpress.models.Command;

import java.util.*;

public class CommandManager {
	private static CommandManager _instance;
	private HashMap<Integer, Queue<Command>> userCommands;
	
	private CommandManager() {
		userCommands = new HashMap<Integer, Queue<Command>>();
	}
	
	public CommandManager _instance() {
		if(_instance == null) {
			_instance = new CommandManager();
		}
		return _instance;
	}
	
	public Queue<Command> getUserCommands(int id) {
		//
		return null;
	}
	
	public void addCommand(Command c) {
		//
	}
	
	protected void addCommandToUser(Command c, int id) {
		//
	}

	private void addUser(int id) {
		//
	}
}
