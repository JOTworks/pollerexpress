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
        SS.joinGame(player, info);

        CommandManager CM = CommandManager._instance();

        //adds join command
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params = {player, info};
        Command joinCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","joinGame",types,params);
        CM.addCommand(joinCommand);

        //adds load command 
        Class<?>[] loadTypes = {Game.class};
        IDatabaseFacade DF = Factory.createDatabaseFacade();
        Object[] loadParams= {DF.getGame(info)};
        Command loadCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","loadGame",loadTypes,loadParams);
        CM.addCommand(loadCommand,player);

    }

    public static void createGame(Player player, GameInfo info) throws CommandFailed, DatabaseException {
        SetupService SS = new SetupService();
        SS.createGame(player, info);

        CommandManager CM = CommandManager._instance();

        //adds create command
        Class<?>[] types = {GameInfo.class};
        Object[] params= {info};
        Command createCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","createGame",types,params);
        CM.addCommand(createCommand);

        joinGame(player, info);
    }
}
