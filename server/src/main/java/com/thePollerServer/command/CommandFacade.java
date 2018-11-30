package com.thePollerServer.command;

import com.shared.exceptions.StateException;
import com.shared.models.Color;
import com.shared.models.EndGameResult;
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

import com.shared.models.Player;
import com.thePollerServer.Model.ServerData;
import com.thePollerServer.Model.ServerGame;
import com.shared.models.ServerPlayer;
import com.thePollerServer.services.GameService;
import com.thePollerServer.services.SetupService;

import java.util.LinkedList;
import java.util.List;

import static com.shared.models.states.GameState.State.NO_ACTION_TAKEN;

public class CommandFacade
{
    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() { }

    private static CommandManager CM = CommandManager._instance();
    private static ServerData model = ServerData.instance();


    /**
     * Done for refactor
     * @param player
     * @param info
     * @throws CommandFailed
     * @throws DatabaseException
     */
    public static void joinGame(Player player, GameInfo info) throws CommandFailed, DatabaseException
    {
        SetupService.joinGame(player, info);
        Player real = model.getGame(info).getPlayer(player).toPlayer();
        //------------------------------add command portion-----------------------------------------
        Class<?>[] loadTypes = {Game.class};
        Object[] loadParams= { (model.getGame(info)).toGame()};
        Command loadCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","loadGame",loadTypes,loadParams);
        CM.addCommand(loadCommand,real);

        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params = {real, model.getGame(info).getGameInfo()};
        Command joinCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","joinGame",types,params);
        CM.addCommand(joinCommand);
    }

    /**
     * Done for refactor
     * @param player
     * @param info
     * @throws CommandFailed
     * @throws DatabaseException
     */
    public static void createGame(Player player, GameInfo info) throws CommandFailed, DatabaseException {
        SetupService.createGame(player, info);

        //------------------------------add command portion-----------------------------------------
        Class<?>[] types = {GameInfo.class};
        Object[] params= {new GameInfo(info)};
        Command createCommand = new Command(CommandsExtensions.clientSide+"ClientSetupService","createGame",types,params);
        CM.addCommand(createCommand);

        joinGame(player, info);
    }

    /**
     * Done for refactor
     * @param p player
     * @param r route
     * @throws DatabaseException
     */

    public static void claimRoute(Player p, Route r, List<TrainCard> cards) throws CommandFailed, DatabaseException
    {
        GameInfo info = model.getMyGame(p);
        Player real = model.getGame(info).getPlayer(p).toPlayer();

        if( (new GameService()).claim(real, r, cards))
        {
            //its verified so...
            r.setOwner(null);//for safety
            Class<?>[] types = {Player.class, Route.class, List.class};
            Object[] params = {real, r, cards};//ok
            Command command = new Command(CommandsExtensions.clientSide + "ClientGameService", "claimRoute", types, params);
            CM.addCommand(command, info);
            setGameState(real);
            initiateEndgameIfEnd(real);//this might fix...
        }
        else
        {
            throw new CommandFailed("claimeRoute");
        }
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
        System.out.println("gameID="+user.getGameId());

        GameInfo info = model.getMyGame(user);
        ServerGame game = model.getGame(info);
        if(game.started) throw new CommandFailed("StartGame");
        game.started = true;
        //------------------------------add command portion-----------------------------------------
        // NOTE: in addition to adding commands, drawTrainCards is also called for each player here

        // setup the game for each person in the game
        setGameState(user);

        {
            Class<?>[] types = {TrainCard[].class};
            Object[] params = { game.getVisibleCards().asArray()};
            Command startGame = new Command(CommandsExtensions.clientSide + "ClientCardService", "setVisibleCards", types, params);
            CM.addCommand(startGame, info);
        }

        {
            Class<?>[] types = {List.class};
            Object[] params = {game.getFakePlayers()};
            Command colors = new Command(CommandsExtensions.clientSide + "ClientSetupService", "setPlayerColors", types, params);
            CM.addCommand(colors, info);
        }


        for(ServerPlayer p :game.getPlayers())
        {
            /* This is meant to call the first train cards.
            * This is in the StartGame method. */
            Player fake= p.toPlayer();
            CommandFacade.drawTrainCards(fake, 4);

            //this maybe should be put into the service, but most of the logic has to deal with commands....
            List<DestinationCard> dlist = game.drawDestinationCards(p, 1) ;
            {
                Class<?>[] types = {Player.class, List.class};//we will see if this works...
                Object[] params = {fake, dlist};//ok
                Command drawDestinationCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawDestinationCards", types, params);
                CM.addCommand(drawDestinationCards, p);
            }

            // next create the command for all other players...
            {
                Class<?>[] types = {Player.class, Integer.class};
                Object[] params = {fake, new Integer(3)};//ok
                Command drawDestinationCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawDestinationCards", types, params);
                CM.addCommand(drawDestinationCards, info);
            }
        }

    }

    public static void drawDestinationCards (Player p) throws StateException, DatabaseException
    {
        GameService gm = new GameService();
        ServerGame game = model.getGame(p);
        if(p instanceof  ServerPlayer)
        {
            System.out.print("THE BUG IS HERE\n");
        }
        //Player real = game.getPlayer(p)
        GameInfo info = game.getGameInfo();
        CommandManager CM = CommandManager._instance();

        List<DestinationCard> dlist = dlist = gm.drawDestinationCards(p);
        {
            //assume a shuffle occured...
            Integer newDeckSize = game.getDestinationDeck().size();
            Class<?>[] types = {Integer.class};
            Object[] params = {newDeckSize};
            Command shuffleDestinationDeck = new Command(CommandsExtensions.clientSide + "ClientCardService", "shuffleDestinationDeck", types, params);
            CM.addCommand(shuffleDestinationDeck, info);
        }


        {
            Class<?>[] types = {Player.class, List.class};//we will see if this works...
            Object[] params = {p, dlist};//ok
            Command drawDestinationCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawDestinationCards", types, params);
            CM.addCommand(drawDestinationCards, p);
        }
        // next create the command for all other players...
        {
            Class<?>[] types = {Player.class, Integer.class};
            Object[] params = {p, new Integer(3)};//ok
            Command drawDestinationCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawDestinationCards", types, params);
            CM.addCommand(drawDestinationCards, info);
        }

        setGameState(p);
    }
    /**
     * Done in my version
     * @param p
     * @param cards
     * @throws CommandFailed
     * @throws DatabaseException
     */

    public static void discardDestinationCards(Player p, List<DestinationCard> cards) throws CommandFailed, DatabaseException
    {
        GameService gm = new GameService();
        boolean discarded = gm.discardDestinationCards(p, cards);

        if (!discarded) {
            throw new CommandFailed("discardDestinationCard");
        }

        //------------------------------add command portion-----------------------------------------
        {
            Class<?>[] types = {Player.class, List.class};
            Object[] params = {p, cards};//ok
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientCardService", "discardDestinationCards", types, params);
            CM.addCommand(cmd, p);
        }

        /*
        {
            Class<?>[] types = {Player.class, Integer.class};
            Object[] params = {p, new Integer(cards.size())};
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientCardService", "discardDestinationCards", types, params);
            CM.addCommand(cmd, model.getMyGame(p));
        }*/

        setGameState(p);
        initiateEndgameIfEnd(p);

    }

    /**
     * Done in my version
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

        //------------------------------add command portion-----------------------------------------
        // rebuild the command and give it to the CommandManager
        Class<?>[] types = {Chat.class, GameInfo.class};
        Object[] params = {chat, gameInfo};
        Command chatCommand = new Command(CommandsExtensions.clientSide+"ClientGameService", "chat", types, params);
        CM.addCommand(chatCommand, gameInfo);
    }

    /**
     *
     * @param p
     * @param i
     * @throws DatabaseException
     */
    public static void drawVisible(Player p, Integer i) throws Exception
    {
        GameInfo info = model.getMyGame(p);
        GameService gameService = new GameService();

        TrainCard card = gameService.drawVisible(p, i);
        TrainCard[] visible = model.getGame(info).getVisibleCards().asArray();

        //------------------------------add command portion-----------------------------------------
        {
            Class<?>[] types = {Player.class, TrainCard.class, TrainCard[].class};
            Object[] params = {p, card, visible};//ok
            Command command = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawVisibleCard", types, params);
            CM.addCommand(command, info);
        }

        setGameState(p);
        initiateEndgameIfEnd(p);
    }

    public static void setColor(Player p, Color.PLAYER color)
    {
        ServerGame game = model.getGame(p);
        ServerPlayer realP = game.getPlayer(p);
        realP.setColor(color);
    }

    private static void setGameState(Player p) throws DatabaseException
    {
        {
            GameInfo info = model.getMyGame(p);

            Class<?>[] types = {GameState.class};
            Object[] params = {model.getGame(info).getGameState()};
            Command cmd = new Command(CommandsExtensions.clientSide + "ClientGameService", "setGameState", types, params);
            CM.addCommand(cmd, info);
        }
    }

    /**
     * Updated for my version.
     * @param p
     * @param number
     * @throws CommandFailed
     * @throws DatabaseException
     */
    public static void drawTrainCards(Player p, int number) throws CommandFailed, DatabaseException
    {
        GameInfo info = model.getMyGame(p);

        ServerGame game = model.getGame(info);
        ServerPlayer realPlayer = game.getPlayer(p);
        List<TrainCard> tList = new LinkedList<>();
        for(int i =0; i < number; ++i)
        {
             tList.add(game.drawTrainCard(realPlayer));
        }

        //check we actually got all of the train cards we wanted
        if(tList.size() != number) {
            throw new CommandFailed("drawTrainCards", "it could not draw correct number of cards");
        }

        //------------------------------add command portion-----------------------------------------
        //give command to actual player
        for(TrainCard card : tList)
        {
            Class<?>[] types = {TrainCard.class};
            Object[] params = {card};
            Command drawTrainCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawTrainCard", types, params);
            CM.addCommand(drawTrainCards, p);
        }

        //give altered command to everyone else in the game

        {
            for(TrainCard card : tList)
            {
                Class<?>[] types = {Player.class};
                Object[] params = {p};
                Command drawTrainCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawTrainCard", types, params);
                CM.addCommand(drawTrainCards, info);
            }
        }
    }

    public static void drawTrainCard(Player p) throws Exception
    {
        System.out.println("I'm in DRAW TRAIN CARD!!!");
        GameService gm = new GameService();
        GameInfo info = model.getMyGame(p);

        TrainCard card = gm.drawTrainCard(p);

        //double check there's a real card :)
        if(card == null) {
            throw new CommandFailed("drawTrainCard", "no card to draw");
        }

        System.out.println("I am trying to actually tell people what. card. to draw.");//mad?

        //------------------------------add command portion-----------------------------------------
        //give command to actual player
        {
            Class<?>[] types = {card.getClass()};
            Object[] params = {card};
            Command drawTrainCard = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawTrainCard", types, params);
            CM.addCommand(drawTrainCard, p);
        }

        //give altered command to everyone else in the game
        {
            Class<?>[] types = {Player.class};
            Object[] params = {p};
            Command drawTrainCards = new Command(CommandsExtensions.clientSide + "ClientCardService", "drawTrainCard", types, params);
            CM.addCommand(drawTrainCards, info);
        }

        setGameState(p);
        // if we successfully draw a card and the state is NO_ACTION_TAKEN then we check for endgame
        initiateEndgameIfEnd(p);
    }


    private static void initiateEndgameIfEnd(Player p)  throws CommandFailed, DatabaseException {

            GameInfo info = model.getMyGame(p);
            ServerGame game = model.getGame(p);
            GameService gm = new GameService();
            // set a default value to check against later
            EndGameResult gameResult = null;

            if (game.getGameState().getState() == NO_ACTION_TAKEN)
                gameResult = gm.checkForEndGame(p);
        System.out.println("checking end game");
        //------------------------------add command portion-----------------------------------------
        if (gameResult != null) {
                Class<?>[] types = {EndGameResult.class};
                Object[] params = {gameResult};
                Command endGameCommand = new Command(CommandsExtensions.clientSide + "ClientGameService", "endGame", types, params);
                CM.addCommand(endGameCommand, info);
            }
    }


}
