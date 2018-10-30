package thePollerExpress.facades;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Chat;
import com.shared.models.Command;
import com.shared.models.GameInfo;
import com.shared.models.PollResponse;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.utilities.CommandsExtensions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Queue;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.communication.PollerExpress;
import thePollerExpress.models.ClientData;

public class GameFacade {

    public ClientData CData;

    public GameFacade() {
        CData = ClientData.getInstance();
    }


    public PollResponse startGame(User user){

        ClientCommunicator CC = ClientCommunicator.instance();

        Class<?>[] types = {User.class};
        Object[] params= {user};
        Command startGame = new Command(CommandsExtensions.serverSide+ "CommandFacade","startGame",types,params);
        PollResponse response = CC.sendCommand(startGame);


        if(response == null)
        {
            return new PollResponse(null, new ErrorResponse("cannot connect to server",null,null) );
        }

        return response;
    }

    public PollResponse discardDestCard(User user, Integer myInteger){ //TODO: this is not what should be here (which is why I have myInteger as a placeholder)

        ClientCommunicator CC = ClientCommunicator.instance();

        Class<?>[] types = {User.class, Integer.class};
        Object[] params= {user, myInteger};
        Command startGame = new Command(CommandsExtensions.serverSide+ "CommandFacade","discardDestCard",types,params);
        PollResponse response = CC.sendCommand(startGame);


        if(response == null)
        {
            return new PollResponse(null, new ErrorResponse("cannot connect to server", new Exception(), startGame) );
        } else if(response.getError()!=null){
            return response;
        }

        return response;
    }

    public PollResponse chat(String message)
    {

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
        if(response == null)
        {
            return new PollResponse(null, new ErrorResponse("cannot connect to server", new Exception(), command) );
        } else if(response.getError()!=null){
            return response;
        }

        return response;
    }
}


