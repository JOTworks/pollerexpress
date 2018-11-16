package com.thePollerServer.command;

import com.shared.models.Color;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.Chat;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.User;
import com.shared.models.states.GameState;
import com.shared.utilities.CommandsExtensions;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import pollerexpress.database.IDatabaseFacade;
import com.shared.models.Player;
import com.thePollerServer.services.GameService;
import com.thePollerServer.services.SetupService;
import com.thePollerServer.utilities.Factory;

import java.util.List;

public class CommandFacade
{

    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() { }

    public static void joinGame(Player player, GameInfo info) throws CommandFailed, DatabaseException
    {
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
     * * TODO add list of train cards to arg.
     * @param p player
     * @param r route
     * @throws DatabaseException
     */
    public static void claimRoute(Player p, Route r) throws DatabaseException
    {
        ///do nothing but
        CommandManager CM = CommandManager._instance();
        IDatabaseFacade df = Factory.createDatabaseFacade();

        GameInfo info = df.getGameInfo(p.getGameId());
        //TODO verify that a route can be cclaimed. in the future this will take a bunch of train cards

        //its verified so...
        Class<?>[] types = {Player.class, Route.class};
        Object[] params = {p, r};
        Command command = new Command(CommandsExtensions.clientSide + "ClientGameService", "claimRoute", types, params);
        CM.addCommand(command, info);
    }
    /**
     * initializes the state for each player, draws cards, and initializes the bank. TODO: consider putting some of this into a service
     *
     * @param user
     * @throws CommandFailed
     * @throws DatabaseException
     */
    public static void startGame(User user) throws CommandFailed, DatabaseException
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        System.out.println("gameID="+user.getGameId());
        GameInfo info = df.getGameInfo(user.getGameId());

        CommandManager CM = CommandManager._instance();
        df.makeBank(info);
        df.setPreGameState(info.getNumPlayers(), df.getGameInfo(user.getGameId()));

        /*-----------------------------------------------------*/
        //jack thinks you need to actualy send the change state command a this point.
        /*-----------------------------------------------------*/

        setColor(user, user.getColor());

        Game game = df.getGame(info);

        // set the game state for each person in the game
        {
            Class<?>[] types = {TrainCard[].class};
            Object[] params = { df.getVisible(info) };                                                  //TODO: rename to setVisibleCards
            Command startGame = new Command(CommandsExtensions.clientSide + "ClientGameService", "startGame", types, params);
            CM.addCommand(startGame, info);
        }


        {
            Class<?>[] types = {game.getPlayers().getClass()};
            Object[] params = {game.getPlayers()};
            Command colors = new Command(CommandsExtensions.clientSide + "ClientSetupService", "setPlayerColors", types, params);
            CM.addCommand(colors, info);
        }

        for(Player p :game.getPlayers())
        {




            /* This is meant to call the first train cards.
            * This is in the StartGame method. */
            CommandFacade.drawTrainCards(p, 4);


            //this maybe should be put into the service, but most of the logic has to deal with commands....
            List<DestinationCard> dlist = df.drawDestinationCards(p, 1) ;
            {
                Class<?>[] types = {Player.class, dlist.getClass()};//we will see if this works...
                Object[] params = {p, dlist};
                Command drawDestinationCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawDestinationCards", types, params);
                CM.addCommand(drawDestinationCards, p);
            }

            // next create the command for all other players...
            {
                Class<?>[] types = {Player.class, Integer.class};
                Object[] params = {p, new Integer(3)};
                Command drawDestinationCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawDestinationCards", types, params);
                CM.addCommand(drawDestinationCards, info);
            }
        }

        {
            Class<?>[] types = {GameState.class};
            Object[] params = {df.getGameState(df.getGameInfo(user.getGameId()))};
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientGameService", "setGameState", types, params);
            CM.addCommand(cmd, df.getGameInfo(df.getPlayer(user.name).gameId));
        }
    }

    public static void discardDestinationCards(Player p, List<DestinationCard> cards) throws CommandFailed, DatabaseException
    {
        GameService gm = new GameService();
        boolean discarded = gm.discardDestinationCards(p, cards);
        // if it is a new player's turn, update the gameState to give control to the next player
        gm.updateGameState(p);
        if (!discarded) {
            throw new CommandFailed("discardDestinationCard");
        }
        IDatabaseFacade df = Factory.createDatabaseFacade();
        CommandManager CM = CommandManager._instance();

        {
            Class<?>[] types = {Player.class, List.class};
            Object[] params = {p, cards};
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientCardService", "discardDestinationCards", types, params);
            CM.addCommand(cmd, p);
        }

        {
            Class<?>[] types = {Player.class, Integer.class};
            Object[] params = {p, new Integer(cards.size())};
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientCardService", "discardDestinationCards", types, params);
            CM.addCommand(cmd, df.getGameInfo(df.getPlayer(p.name).gameId));
        }

        {
            Class<?>[] types = {GameState.class};
            Object[] params = {df.getGameState(df.getGameInfo(p.getGameId()))};
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientGameService", "setGameState", types, params);
            CM.addCommand(cmd, df.getGameInfo(df.getPlayer(p.name).gameId));
        }
    }

    /**
     * Abby
     * (DONE) The ExecuteHandler will call this method.
     * This methods sends the chat along to the database,
     * rebuilds the command and adds it to CommandManager.
     * @param chat
     * @param gameInfo
     */
    public static void chat(Chat chat, GameInfo gameInfo) throws DatabaseException
    {

        // send the chat along to the database
        GameService gameService = new GameService();
        gameService.chat(chat, gameInfo);

        // rebuild the command and give it to the CommandManager
        Class<?>[] types = {Chat.class, GameInfo.class};
        Object[] params = {chat, gameInfo};
        Command chatCommand = new Command(CommandsExtensions.clientSide+"ClientGameService", "chat", types, params);
        CommandManager._instance().addCommand(chatCommand, gameInfo);
    }


    /**
     *
     * @param p
     * @param i
     * @throws DatabaseException
     */
    public static void drawVisible(Player p, Integer i) throws DatabaseException
    {
        try
        {
            GameService.Triple result = new GameService().drawVisible(p, i);

            Class<?>[] types = {Player.class, TrainCard.class, Integer.class, TrainCard[].class};
            Object[] params = {p, result.card, Integer.valueOf(result.drawsLeft),result.visible  };
            Command command = new Command(CommandsExtensions.clientSide+"ClientCardService", "drawVisibleCard", types, params);
            CommandManager._instance().addCommand(command, result.info);
        }
        finally
        {

        }
    }

    public static void setColor(Player p, Color.PLAYER color)
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        df.setColor(p, Color.getIndex(color));
    }


    public static void drawTrainCards(Player p, int number) throws DatabaseException
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        CommandManager CM = CommandManager._instance();

        Game game = df.getGame(df.getGameInfo(p.getGameId()));
        List<TrainCard> tList = df.drawTrainCards(p, number);

        //give command to actual player
        for(TrainCard card : tList)
        {
            Class<?>[] types = {card.getClass()};
            Object[] params = {card};
            Command drawTrainCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawTrainCard", types, params);
            CM.addCommand(drawTrainCards, p);
        }

        //give altered command to everyone else in the game
        for(Player player : game.getPlayers())
        {
            //if(!p.equals(player))
            {
                for(TrainCard card : tList)
                {
                    Class<?>[] types = {player.getClass()};
                    Object[] params = {player};
                    Command drawTrainCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawTrainCard", types, params);
                    CM.addCommand(drawTrainCards, p);
                }
            }
        }
    }
}
