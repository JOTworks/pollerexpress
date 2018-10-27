package com.thePollerServer.command;

import com.shared.models.Chat;
import com.shared.utilities.CommandsExtensions;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.Player;
import com.thePollerServer.commandServices.GameService;
import com.thePollerServer.commandServices.SetupService;
import com.thePollerServer.utilities.Factory;

public class CommandFacade {

    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() { }

    public static void joinGame(Player player, GameInfo info) throws CommandFailed, DatabaseException {
        SetupService SS = new SetupService();
        SS.joinGame(player, info);

        CommandManager CM = CommandManager._instance();

        Class<?>[] loadTypes = {Game.class};
        IDatabaseFacade DF = Factory.createDatabaseFacade();
        Object[] loadParams= {DF.getGame(info)};
        Command loadCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","loadGame",loadTypes,loadParams);
        CM.addCommand(loadCommand,player);

        //adds join command
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params = {player, DF.getGameInfo(info.getId())};
        Command joinCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","joinGame",types,params);
        CM.addCommand(joinCommand);
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

    /**
     * Abby
     * (DONE) The ExecuteHandler will call this method.
     * This methods sends the chat along to the database,
     * rebuilds the command and adds it to CommandManager.
     * @param chat
     * @param gameInfo
     */
    public static void chat(Chat chat, GameInfo gameInfo) throws DatabaseException {

        // send the chat along to the database
        GameService gameService = new GameService();
        gameService.chat(chat, gameInfo);

        // rebuild the command and give it to the CommandManager

        Class<?>[] types = {Chat.class, GameInfo.class};
        Object[] params = {chat, gameInfo};

        Command chatCommand = new Command(CommandsExtensions.clientSide+"GameService",
                "chat",
                types,
                params);

        CommandManager commandManager = CommandManager._instance();
        commandManager.addCommand(chatCommand);
    }
}
