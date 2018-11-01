package thePollerExpress.Development;

import com.shared.exceptions.CommandFailed;
import com.shared.models.City;
import com.shared.models.Command;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.Player;
import com.shared.models.Point;
import com.shared.models.interfaces.ICommand;

import java.lang.reflect.Method;
import java.util.ArrayList;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.services.ClientGameService;
import thePollerExpress.utilities.AsyncRunner;


/**
 * Jack
 */
public class MethodCaller {

    MethodCallerFragment fragment;

    public MethodCaller(MethodCallerFragment inFragment){
        fragment = inFragment;
    }

    public ArrayList<String> execute(Command[] commands) throws CommandFailed {
//        return commands[0].execute().toString();

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {

            results.add(commands[i].execute().toString());
        }

        return results;
    }


    public ArrayList<String> parse(String s) {
        ArrayList<String> result = new ArrayList<String>();
        ClientData CD = ClientData.getInstance();
        switch (s) {
            case "help":
                result.add("getUserName\n" +
                        "getChatMessages\n" +
                        "getGameID\n" +
                        "claimRoute\n" +
                        "---\n" +
                        "add commands to the methodCaller class\n" +
                        "in the parse funtion, as a case");
                break;
            case "claimRoute":
                ClientGameService.claimRoute(CD.getUser(), 1);
                //result.add( )
                break;
            case "getChatMessages":
                result = CD.getGame().getChatHistory().getChatsAsString();
                break;
            case "getUserName":
                result.add(CD.getUser().getName());
                break;
            case "startGame":
                AsyncRunner startGameTask = new AsyncRunner(fragment);

                startGameTask.execute(new ICommand()
                {
                    @Override
                    public Object execute() throws CommandFailed
                    {
                        return new GameFacade().startGame(ClientData.getInstance().getUser());
                    }
                });

//            case "discardCard":
//                AsyncRunner discardCardTask = new AsyncRunner(fragment);
//
//                startGameTask.execute(new ICommand()
//                {
//                    @Override
//                    public Object execute() throws CommandFailed
//                    {
//                        return new GameFacade().discardDestCard(ClientData.getInstance().getUser(),
//                                new DestinationCard(new City("name", new Point(50)), "North Pole", 50));
//                    }
//                });

                break;
            case "getGameID":
                if(CD.getGame()!=null)
                    result.add(CD.getGame().getId());
                else
                    result.add("game is null");
                break;

            default:
                result.add("that didn't match any commands");
        }
        return result;
    }
}
