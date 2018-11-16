package thePollerExpress.facades;

import com.shared.models.Chat;
import com.shared.models.Command;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.PollResponse;
import com.shared.models.Route;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.utilities.CommandsExtensions;

import java.sql.Timestamp;
import java.util.List;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.models.ClientData;

public class GameFacade {

    public ClientData CData;
    public GameFacade() {
        CData = ClientData.getInstance();
    }
    private ClientCommunicator CC = ClientCommunicator.instance();

    public PollResponse startGame(User user){
        Class<?>[] types = {User.class};
        Object[] params= {user};
        Command startGame = new Command(CommandsExtensions.serverSide+ "CommandFacade","startGame",types,params);
        return sendCommand( startGame );
    }

    public PollResponse drawDestCard(){
        Class<?>[] types = {Player.class};
        Object[] params= {CData.getUser()};
        Command drawDest= new Command(CommandsExtensions.serverSide+ "CommandFacade","drawDestinationCards",types,params);;
        return sendCommand( drawDest );
    }

//    public PollResponse drawDestCards() {
//        Class<?>[] types = {Player.class};
//        Object[] values = {CData.getUser()};
//        Command command = new Command(CommandsExtensions.serverSide +"CommandFacade","drawDestCards",types,values);
//        return sendCommand(command);
//    }

    public PollResponse drawTrainCardFromDeck() {
        Class<?>[] types = {Player.class};
        Object[] values = {CData.getUser()};
        Command command = new Command(CommandsExtensions.serverSide +"CommandFacade","drawTrainCardFromDeck",types,values);
        return sendCommand(command);
    }

    public PollResponse drawVisibleCard(Integer integer)
    {
        Class<?>[] types = {Player.class, Integer.class};
        Object[] values = {CData.getUser(), integer};
        Command command = new Command(CommandsExtensions.serverSide +"CommandFacade","drawVisible",types,values);
        return sendCommand(command);
    }

    public PollResponse discardDestCard(List<DestinationCard> destCards){
        Class<?>[] types = {Player.class, List.class};
        Object[] params= {CData.getUser(), destCards};
        Command discardDest = new Command(CommandsExtensions.serverSide+ "CommandFacade","discardDestinationCards",types,params);
        return sendCommand( discardDest );
    }

    public PollResponse claimRoute(Route r) {
        Class<?>[] types = {Player.class, Route.class};
        Object[] values = {CData.getUser(), r};
        Command command = new Command(CommandsExtensions.serverSide +"CommandFacade","claimRoute",types,values);
        return sendCommand(command);
    }

    public PollResponse chat(String message) {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        Chat chat = new Chat(message, timeStamp, ClientData.getInstance().getUser());
        GameInfo gameInfo = ClientData.getInstance().getGame().getGameInfo();
        Class<?>[] types = {Chat.class, GameInfo.class};
        Object[] values = {chat, gameInfo};
        Command chatCommand = new Command(CommandsExtensions.serverSide +"CommandFacade","chat",types,values);
        return sendCommand( chatCommand );
    }

    private PollResponse sendCommand(Command command)
    {
        ClientCommunicator CC = ClientCommunicator.instance().instance();
        PollResponse response = CC.sendCommand(command);
        if(response == null) {
            return new PollResponse(null, new ErrorResponse("cannot connect to server", new Exception(), command) );
        } else if(response.getError()!=null){
            return response;
        }
        return response;
    }
}


