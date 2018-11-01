package thePollerExpress.facades;

import android.app.Activity;

import com.shared.utilities.CommandsExtensions;
import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.GameInfo;
import com.shared.models.requests.LoginRequest;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.Player;
import com.shared.models.PollResponse;
import com.shared.models.Color;
import com.shared.models.User;

import java.util.Queue;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.models.ClientData;
import thePollerExpress.communication.PollerExpress;

/**
 * Who was in charge of this class?
 * SetupFacade abstracts the Model from the Presenters, but also acts as the LoginSetupService
 * it will parse the responses and pass the list of commands to poller to execute,
 * the error to the presenter and update the model for login and register results
 */
public class SetupFacade {

    /**
     *
     * @return res.getError it will return null on succesful login
     */
    public ErrorResponse login(LoginRequest request){

        return loginOrRegister("login", request);
    }

    /**
     *
     * @return res.getError, it will return null on succesful login
     */
    public ErrorResponse register(LoginRequest request){
        return loginOrRegister("register", request);
    }

    /**
     * keeps the code from being duplicated, but lets the presenters call login or regester as
     * sepereate functions.
     * @param requestType
     * @return res.getError, it will return null on succesful login
     */
    public  ErrorResponse loginOrRegister(String requestType, LoginRequest loginReq){

        ClientCommunicator CC = ClientCommunicator.instance();

        LoginResponse response = CC.sendLoginRequest(requestType, loginReq);

        if(response == null) {
            return new ErrorResponse("cannot connect to server",null,null);
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError()!=null){
            return response.getError();
        }

        //update model if no errors
        ClientData CData = ClientData.getInstance();
        CData.setUser( new User(loginReq.getUsername(), "" ) );
        CData.setAuth(response.getAuthToken());
        CData.setGameInfoList(response.getAvailableGames());
        return response.getError();
    }


    /*------------------------------------------------------------------------------------------------------------------------*/
    /*----------------These are all the methods that create commands and send them to the ClientComunicator--------------------*/
    /*------------------------------------------------------------------------------------------------------------------------*/
    /**
     *
     * @param name
     * @param numPlayers
     * @param userColor
     * @return res.getError, it will be null on succesful Login
     */
    public PollResponse createGame(String name, int numPlayers, Color.PLAYER userColor) {

        GameInfo info = new GameInfo(name,numPlayers); //max players 1.... 111111
        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params= {ClientData.getInstance().getUser(), info };

        Command createGameCommand = new Command(CommandsExtensions.serverSide +"CommandFacade","createGame",types,params);

        PollResponse response = CC.sendCommand(createGameCommand);

        if(response == null) {
            //client communicator didn't work, throw error or something? Idk how to do that though.
        }

        return response;
    }

    /**
     *
     * @param player, info
     * @return res.getError, it will be null on succesful register
     */
    public PollResponse joinGame(Player player, GameInfo info){
        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params= {player, info};
        Command joinGameCommand = new Command(CommandsExtensions.serverSide+ "CommandFacade","joinGame",types,params);

        PollResponse response = CC.sendCommand(joinGameCommand);

        if(response == null) {
            response = new PollResponse(null, new ErrorResponse("Could not contact server", new Exception(),joinGameCommand) );
        }

        return response;
    }

    /**
     *
     * @param
     * @return res.getError, it will be null on succesful join
     */
    public PollResponse leaveGame(){

        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {Player.class, GameInfo.class};


        /*Object[] params= {ClientData.getInstance().getUser(), };
       Command joinGameCommand = new Command("CommandFacade","leaveGame",types,params);

       PollResponse response = CC.sendCommand(joinGameCommand);

        if(response == null) {
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError() != null) {
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }
        */
        return null;
    }


}
