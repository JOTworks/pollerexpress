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

public class CommandFacade {

    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() {

    }
    public static void joinGame(Player player, GameInfo info) throws CommandFailed
    {
        SetupService SS = new SetupService();
        SS.joinGame(player, info);

        /**todo:
         * send commands to the command manager
         */
    }

    public static void createGame(Player player, GameInfo info) throws CommandFailed
    {
        SetupService SS = new SetupService();
        SS.createGame(player, info);

        CommandManager CM = CommandManager._instance();

        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params= {player, info};

        Command command = new Command(CommandsExtensions.clientSide+"ClientSetupService","createGame",);
        CM.addCommand(command);
    }
}
