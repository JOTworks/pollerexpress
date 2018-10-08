package command;

import com.pollerexpress.models.Command;

public class CommandSwitch {
    public CommandSwitch() {}

    public void switchCommand(Command c, String user) {
        String className = c.getClassName();
        String methodName = c.getMethodName();
        /*
        TODO: refactor once I know what the classes that execute commands actually look like :)
        */
        if(className == "ServerSetupService") {
            if(methodName == "createGame") {
                this.switchCreateGame(c, user);
            } else if(methodName == "joinGame") {
                this.switchJoinGame(c, user);
            }
        }
    }

    private void switchCreateGame(Command c, String user) {
        Object[] oldParams = c.getParamValues();
        //doesn't this like... get added to EVERYONE?
        //do I make a function in the CM that adds it to literally everyone or what? Hmm.
    }

    private void switchJoinGame(Command c, String user) {
        Object[] oldParams = c.getParamValues();

        //give the user who called the game info if necessary? I guess???
        //...or is this another one where the same command is added to EVERYONE'S queue. Hmmmmmmmm.
    }

}
