package thePollerExpress.Development;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;
import com.shared.models.Player;
import com.shared.models.Route;
import com.shared.models.interfaces.ICommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

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


    public ArrayList<String> parse(String s, String args[]) {
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
            {
                if (args.length != 2)
                {
                    result.add("USAGE: claimRoute routnumber");
                    break;
                }
                final Route r = ClientGameService.claimRoute(CD.getUser(), Integer.valueOf(args[1]));
                if (r == null) break;

                asyncCommand(new ICommand()
                {
                    @Override
                    public Object execute() throws CommandFailed
                    {
                        return new GameFacade().claimRoute(r);
                    }
                });

                result.add(String.format("Claimed a %s", r.toString()));
                }
                break;

            case "getRoutes":
                for(Route r :CD.getGame().getMap().getRoutes())
                {
                    if(r.getOwner() != null)
                    {
                        result.add(Objects.toString(r.getOwner() ));
                    }
                    else
                    {
                        result.add("null");
                    }
                }
                break;
            case "getChatMessages":
                result = CD.getGame().getChatHistory().getChatsAsString();
                break;
            case "getUserName":
                result.add(CD.getUser().getName());
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
    private void asyncCommand(ICommand c)
    {
        AsyncRunner runner = new AsyncRunner(null);
        runner.execute(c);
    }
}
