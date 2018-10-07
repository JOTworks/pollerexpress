package cs340.pollerexpress;

import com.pollerexpress.models.ErrorResponse;
import com.pollerexpress.models.LoginRequest;
import com.pollerexpress.models.LoginResponse;
import com.pollerexpress.models.PollResponse;
import com.pollerexpress.models.Color;
import com.pollerexpress.models.User;

/**
 * SetupFacade absctracts the Model from the Presenters, but also acts as the LoginSetupService
 * it will parse the responces and pass the list of commands to poller to execute,
 * the error to the presenter and update the model for login and register results
 */
public class SetupFacade {

    /**
     *
     * @param userName
     * @param password
     * @return res.getError it will return null on succesful login
     */
    public ErrorResponse login(String userName, String password){
        return loginOrRegister("login",userName,password);
    }

    /**
     *
     * @param userName
     * @param password
     * @return res.getError, it will return null on succesful login
     */
    public ErrorResponse register(String userName, String password){
        return loginOrRegister("register",userName,password);
    }

    /**
     * keeps the code from being duplicated, but lets the presenters call login or regester as
     * sepereate functions.
     * @param requestType
     * @param userName
     * @param password
     * @return res.getError, it will return null on succesful login
     */
    private ErrorResponse loginOrRegister(String requestType, String userName, String password){
        LoginRequest loginReq = new LoginRequest(userName,password);
        ClientCommunicator CC = ClientCommunicator.instance();

        LoginResponse resp = CC.sendLoginRequest(requestType, loginReq);

        if(resp.getError()!=null){
            return resp.getError();
        }

        //update model if no errors
        ClientData CData = ClientData.getInstance();
        CData.setUser(new User(userName,password));
        CData.setAuth(resp.getAuthToken());
        CData.setGameInfoList(resp.getAvailableGames());

        return resp.getError();
    }


    ErrorResponse createGame(String name, int numPLayers, Color.PLAYER userColor) {
        return null;
    }

    ErrorResponse joinGame(String gameId){
        return null;
    }


}
