package command;

import java.util.*;

public class CommandManager {
	private static CommandManager _instance;
	private HashMap<Integer, Queue<Command>> user_commands;
	
	private CommandManager() {
		user_commands = new HashMap<Integer, Queue<Command>>();
	}
	
	public CommandManager _instance() {
		if(_instance == null) {
			_instance = new CommandManager();
		}
		return _instance;
	}
	
	public void addUser(int id) {
		//
	}
	
	public Queue<Command> getUserCommands(int id) {
		//
		return null;
	}
	
	public void addCommand(Command c) {
		//
	}
	
	private void addCommandToUser(Command c, int id) {
		//
	}
}
