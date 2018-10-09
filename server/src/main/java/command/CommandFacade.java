package command;

import com.pollerexpress.CommandsExtensions;
import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.Player;
import com.pollerexpress.server.commands.SetupService;
import com.pollerexpress.server.homeless.Factory;

import pollerexpress.database.DatabaseFacade;

public class CommandFacade {

    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() {

    }
    public static void joinGame(Player player, GameInfo info) throws CommandFailed, DatabaseException {
        SetupService SS = new SetupService();
        SS.createGame(player, info);

        CommandManager CM = CommandManager._instance();

        //adds load command
        Class<?>[] types = {Game.class};
        IDatabaseFacade DF = Factory.createDatabaseFacade();
        Object[] params= {DF.getGame(info)};
        Command command = new Command(CommandsExtensions.clientSide+"ClientSetupService","loadGame",types,params);
        CM.addCommand(command,player);

        //adds join command
        Class<?>[] types2 = {Player.class, GameInfo.class};
        Object[] params2 = {player, info};
        Command command2 = new Command(CommandsExtensions.clientSide+"ClientSetupService","joinGame",types,params);
        CM.addCommand(command2);
    }

    public static void createGame(Player player, GameInfo info) throws CommandFailed, DatabaseException {
        SetupService SS = new SetupService();
        SS.createGame(player, info);

        CommandManager CM = CommandManager._instance();

        //adds create command
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params= {player, info};
        Command command = new Command(CommandsExtensions.clientSide+"ClientSetupService","createGame",types,params);
        CM.addCommand(command);

        //adds load command
        Class<?>[] types2 = {Game.class};
        IDatabaseFacade DF = Factory.createDatabaseFacade();
        Object[] params2 = {DF.getGame(info)};
        Command command2 = new Command(CommandsExtensions.clientSide+"ClientSetupService","loadGame",types2,params2);
        CM.addCommand(command,player);

        //adds join command
        Class<?>[] types3 = {Player.class, GameInfo.class};
        Object[] params3 = {player, info};
        Command command3 = new Command(CommandsExtensions.clientSide+"ClientSetupService","joinGame",types3,params3);
        CM.addCommand(command2);
    }
}
