package thePollerExpress.facades;

import com.shared.models.Command;
import com.shared.models.PollResponse;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.utilities.CommandsExtensions;

import java.util.List;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.communication.PollerExpress;
import thePollerExpress.models.ClientData;

public class GameFacade {

    public ErrorResponse startGame(){

        ClientCommunicator CC = ClientCommunicator.instance();

        Class<?>[] types = {/*Class.class*/}; //TODO: put the actual class names
        Object[] params= {/*myParams*/};
        Command startGame = new Command(CommandsExtensions.serverSide+ "CommandFacade","joinGame",types,params);
        PollResponse response = CC.sendCommand(startGame);

        if(response == null) {
            return new ErrorResponse("cannot connect to server",null,null);
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError()!=null){
            return response.getError();
        }
        ClientData.getInstance().set(new PollerExpress());
        //update model if no errors
        ClientData CData = ClientData.getInstance();

        //update data

        return response.getError();
    }
}


