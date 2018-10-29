package thePollerExpress.facades;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;
import com.shared.models.PollResponse;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.utilities.CommandsExtensions;

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


    public ErrorResponse startGame(User user){

        ClientCommunicator CC = ClientCommunicator.instance();

        Class<?>[] types = {User.class};
        Object[] params= {user};
        Command startGame = new Command(CommandsExtensions.serverSide+ "CommandFacade","startGame",types,params);
        PollResponse response = CC.sendCommand(startGame);


        if(response == null) {
            return new ErrorResponse("cannot connect to server",null,null);
            //client communicator didn't work, throw error or something? Idk how to do that though.
        } else if(response.getError()!=null){
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }

        return response.getError();
    }

    public ErrorResponse discardDestCard(User user, Integer myInteger){ //TODO: this is not what should be here (which is why I have myInteger as a placeholder)

        ClientCommunicator CC = ClientCommunicator.instance();

        Class<?>[] types = {User.class, Integer.class};
        Object[] params= {user, myInteger};
        Command startGame = new Command(CommandsExtensions.serverSide+ "CommandFacade","discardDestCard",types,params);
        PollResponse response = CC.sendCommand(startGame);


        if(response == null) {
            return new ErrorResponse("cannot connect to server",null,null); //TODO: the communicator should populate the response with an error saying it could not connect (It might do that already in which case this line would be redundant
        } else if(response.getError()!=null){
            return response.getError();
        } else {
            executeCommands(response.getCommands());
        }

        return response.getError();
    }



    /**
     * This method currently does not return any errors if there is a failure
     * @param commands
     */
    private void executeCommands(Queue<Command> commands){
        while(!commands.isEmpty())
        {
            Command command = commands.poll();
            try {
                command.execute();
            } catch (CommandFailed commandFailed) {
                commandFailed.printStackTrace();
            }
        }
    }
}


