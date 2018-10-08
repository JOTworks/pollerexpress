package command;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.*;
import com.pollerexpress.server.Factory;

import pollerexpress.database.DatabaseFacade;

public class CommandSwitch {
    private CommandManager cm;

    public CommandSwitch() {
        cm = CommandManager._instance();
    }

    public void switchCommand(Command c, String user) throws DatabaseException{
        String className = c.getClassName();
        String methodName = c.getMethodName();

        if(className == "SetupService") {
            if(methodName == "createGame") {
                this.switchCreateGame(c, user);
            } else if(methodName == "joinGame") {
                this.switchJoinGame(c, user);
            }
        }
    }

    /**
     * Takes the CreateGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The CreateGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchCreateGame(Command c, String user) throws DatabaseException {
        DatabaseFacade db = (DatabaseFacade) Factory.createDatabaseFacade();
        Object[] oldParams = c.getParamValues();
        GameInfo oldGi = (GameInfo) oldParams[1];

        String className = "ClientSetupService";
        String methodName = "joinGame";
        Class<?>[] paramTypes = {GameInfo.class};
        Object[] paramValues = {db.getGameInfo(oldGi.getId())};
        Command send = new Command(className, methodName, paramTypes, paramValues);

        cm.addToAll(send);
    }

    /**
     * Takes the JoinGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The JoinGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchJoinGame(Command c, String user) throws DatabaseException {
        DatabaseFacade db = (DatabaseFacade) Factory.createDatabaseFacade();
        Object[] oldParams = c.getParamValues();
        GameInfo oldGi = (GameInfo) oldParams[1];
        String className = "ClientSetupService";
        String methodName = "joinGame";

        //get objects for sender
        Game g = db.getGame(oldGi);

        //make command for sender
        Class<?>[] senderTypes = {Game.class};
        Object[] senderValues = {g};
        Command sender = new Command(className, methodName, senderTypes, senderValues);
        cm.addCommand(sender, user);

        //get objects for everyone else
        GameInfo gi = db.getGameInfo(oldGi.getId());
        Player p = db.getPlayer(user);

        //make command for everyone else
        Class<?>[] otherTypes = {Player.class, GameInfo.class};
        Object[] otherValues = {p, gi};
        Command other = new Command(className, methodName, otherTypes, otherValues);
        cm.addToAllExcept(other, user);
    }

    /**
     * Takes the LeaveGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The LeaveGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchLeaveGame(Command c, String user) {
        //
    }

    /**
     * Takes the StartGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The StartGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchStartGame(Command c, String user) {
        //
    }

    /**
     * Takes the StartGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The StartGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchDeleteGame(Command c, String user) {
        //
    }
}
