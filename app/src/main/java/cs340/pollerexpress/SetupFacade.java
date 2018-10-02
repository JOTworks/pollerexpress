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
    ErrorResponse login(String userName, String password){

        LoginRequest loginReq = new LoginRequest(userName,password);
        ClientCommunicator CC = ClientCommunicator.instance();


            LoginResponse resp = CC.sendLoginRequest("login", loginReq);

            //update model
            ClientData CData = ClientData.getInstance();

            CData.setAuth(resp.getAuthToken());
            //CData.setGameInfoList(resp.getAvailableGames());

            return resp.getError();
    }



    ErrorResponse register(String userName, String password){
        return null;
    }

    ErrorResponse createGame(String name, int numPLayers, Color.PLAYER userColor) {
        return null;
    }

    ErrorResponse joinGame(String gameId){
        return null;
    }


}
