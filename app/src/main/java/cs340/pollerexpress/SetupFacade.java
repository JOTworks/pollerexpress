package cs340.pollerexpress;

import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.reponse.ErrorResponse;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.request.LoginRequest;
import com.pollerexpress.reponse.LoginResponse;
import com.pollerexpress.models.Player;
import com.pollerexpress.models.PollResponse;
import com.pollerexpress.models.Color;
import com.pollerexpress.models.User;

import java.util.Queue;

/**
 * SetupFacade absctracts the Model from the Presenters, but also acts as the LoginSetupService
 * it will parse the responces and pass the list of commands to poller to execute,
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
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError()!=null){
            return response.getError();
        }

        //update model if no errors
        ClientData CData = ClientData.getInstance();
        CData.setUser(new User(loginReq.getUsername(), loginReq.getPassword()));
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
    public ErrorResponse createGame(String name, int numPlayers, Color.PLAYER userColor) {

        GameInfo info = new GameInfo(null, name,numPlayers,1);
        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params= {ClientData.getInstance().getUser(), info};
        Command joinGameCommand = new Command("SetupService","createGame",types,params);

        PollResponse response = CC.sendCommand(joinGameCommand);

        if(response == null) {
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError() != null) {
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }

        return null;
    }

    /**
     *
     * @param player, info
     * @return res.getError, it will be null on succesful register
     */
    public ErrorResponse joinGame(Player player, GameInfo info){
        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params= {player, info};
        Command joinGameCommand = new Command("SetupService","joinGame",types,params);

        PollResponse response = CC.sendCommand(joinGameCommand);

        if(response == null) {
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError() != null) {
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }

        return null;
    }

    /**
     *
     * @param gameName
     * @return res.getError, it will be null on succesful join
     */
    public ErrorResponse startGame(String gameName){
        /**todo:
         *  check if i pass the correct params to the server side setupService startgame()
         */
        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {String.class};
        Object[] params= {gameName};
        Command joinGameCommand = new Command("SetupService","StartGame",types,params);

        PollResponse response = CC.sendCommand(joinGameCommand);

        if(response == null) {
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError() != null) {
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }

        return null;
    }

    /**
     *
     * @param gameName
     * @return res.getError, it will be null on succesful join
     */
    public ErrorResponse leaveGame(){

        ClientCommunicator CC = ClientCommunicator.instance();
        Class<?>[] types = {Player.class, GameInfo.class};

        Object[] params= {ClientData.getInstance().getUser(),};
        Command joinGameCommand = new Command("SetupService","leaveGame",types,params);

        PollResponse response = CC.sendCommand(joinGameCommand);

        if(response == null) {
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError() != null) {
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }

        return null;
    }

    private void executeCommands(Queue<Command> commands){
        for (int i = 0; i < commands.size(); i++) {
            Command command = commands.poll();
            try {
                command.execute();
            } catch (CommandFailed commandFailed) {
                commandFailed.printStackTrace();
            }
        }
    }
}
