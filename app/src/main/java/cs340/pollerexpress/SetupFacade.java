package cs340.pollerexpress;

import com.pollerexpress.models.ErrorResponse;
import com.pollerexpress.models.LoginRequest;
import com.pollerexpress.models.LoginResponse;
import com.pollerexpress.models.PollResponse;
import com.pollerexpress.models.Color;
public class SetupFacade {

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    LoginResponse login(String userName, String password){

        LoginRequest loginReq = new LoginRequest(userName,password);
        ClientCommunicator CC = ClientCommunicator.instance();

        try {
            LoginResponse resp = CC.sendLoginRequest("login", loginReq);

            //update model
            resp.getAuthToken();
            resp.getAvailableGames();

            return resp;
        }
        catch(Exception e){
            return new LoginResponse(null,null,new ErrorResponse("from SetupFacade",e,null));
        }
    }

    LoginResponse register(String userName, String password){
        return null;
    }


    PollResponse createGame(String name, int numPLayers, Color.PLAYER userColor) {
        return null;
    }

    PollResponse joinGame(String gameId){
        return null;
    }
}
