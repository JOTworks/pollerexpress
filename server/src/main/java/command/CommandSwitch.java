package command;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.*;
import com.pollerexpress.server.homeless.Factory;

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
        String createMethodName = "joinGame";
        GameInfo gi = db.getGameInfo(oldGi.getId());

        Class<?>[] createTypes = {GameInfo.class};
        Object[] createValues = {gi};
        Command create = new Command(className, createMethodName, createTypes, createValues);

        cm.addToAll(create);

        String joinMethodName = "joinGame";
        Class<?>[] joinTypes = {Player.class, GameInfo.class};
        Object[] joinValues = {db.getPlayer(user), gi};
        Command join = new Command(className, joinMethodName, joinTypes, joinValues);
        this.switchJoinGame(join, user);
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
    private void switchLeaveGame(Command c, String user) throws DatabaseException {
        DatabaseFacade db = (DatabaseFacade) Factory.createDatabaseFacade();
        Object[] oldParams = c.getParamValues();
        GameInfo oldGi = (GameInfo) oldParams[1];
        String className = "ClientSetupService";
        String methodName = "leaveGame";

        //get objects for sender
        GameInfo gi = db.getGameInfo(oldGi.getId());

        //make command for sender
        Class<?>[] senderTypes = {GameInfo.class};
        Object[] senderValues = {gi};
        Command sender = new Command(className, methodName, senderTypes, senderValues);

        cm.addCommand(sender, user);

        //get objects for everyone else
        Player p = db.getPlayer(user);

        //make command for everyone else
        Class<?>[] otherTypes = {Player.class, GameInfo.class};
        Object[] otherValues = {p, gi};
        Command other = new Command(className, methodName, otherTypes, otherValues);

        cm.addToAllExcept(other, user);
    }

    /**
     * Takes the StartGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The StartGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchStartGame(Command c, String user) throws DatabaseException {
        DatabaseFacade db = (DatabaseFacade) Factory.createDatabaseFacade();
        Object[] oldParams = c.getParamValues();
        GameInfo oldGi = (GameInfo) oldParams[0];
        String className = "ClientSetupService";
        String methodName = "startGame";

        //get objects for command
        GameInfo gi = db.getGameInfo(oldGi.getId());

        //make command
        Class<?>[] paramTypes = {GameInfo.class};
        Object[] paramValues = {gi};
        Command send = new Command(className, methodName, paramTypes, paramValues);

        cm.addToAll(send);
    }

    /**
     * Takes the StartGame command executed on the server and converts it
     * into the commands sent back to be executed client-side.
     * @param c The StartGame command to be converted
     * @param user The username of the user who initiated the command
     */
    private void switchDeleteGame(Command c, String user) throws DatabaseException {
        DatabaseFacade db = (DatabaseFacade) Factory.createDatabaseFacade();
        Object[] oldParams = c.getParamValues();
        GameInfo oldGi = (GameInfo) oldParams[0];

        //get objects for leave command
        Player p = db.getPlayer(user);
        GameInfo gi = db.getGameInfo(oldGi.getId());

        //make leave command
        String leaveClass = "SetupService";
        String leaveMethod = "leaveGame";
        Class<?>[] leaveTypes = {GameInfo.class};
        Object[] leaveValues = {gi};
        Command leave = new Command(leaveClass, leaveMethod, leaveTypes, leaveValues);

        this.switchLeaveGame(leave, user);

        //make delete command
        String deleteClass = "ClientSetupService";
        String deleteMethod = "deleteGame";
        Class<?>[] deleteTypes = {GameInfo.class};
        Object[] deleteValues = {gi};
        Command delete = new Command(deleteClass, deleteMethod, deleteTypes, deleteValues);

        cm.addToAll(delete);

    }
}
