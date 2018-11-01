package thePollerExpress.Development;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;

import com.shared.models.Player;
import com.shared.models.Route;
import com.shared.models.interfaces.ICommand;
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
    private ClientData CD;


    public MethodCaller(MethodCallerFragment inFragment){
        fragment = inFragment;
        CD = ClientData.getInstance();
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
        switch (s) {
            case "help":
                result.add("getUserName\n" +
                        "getChatMessages\n" +
                        "getGameID\n" +
                        "claimRoute [int i]\n" +
                        "drawVisible [int i]\n" +
                        "setPlayerPoints p points" +
                        "setPlayerTrains p trains" +
                        "getRoutes\n" +

                        "---\n" +
                        "add commands to the methodCaller class\n" +
                        "in the parse funtion, as a case");
                break;
            case "jack":
                CD.getUser().getDestCardOptions().doNothing();
                //result.add( )
                break;
            case "claimRoute":
                claimRoute(result, args);
//            {
//                if (args.length != 2)
//                {
//                    result.add("USAGE: claimRoute routenumber");
//                    break;
//                }
//                final Route r = ClientGameService.claimRoute(CD.getUser(), Integer.valueOf(args[1]));
//                if (r == null) break;
//
//                asyncCommand(new ICommand()
//                {
//                    @Override
//                    public Object execute() throws CommandFailed
//                    {
//                        return new GameFacade().claimRoute(r);
//                    }
//                });
//
//                result.add(String.format("Claimed a %s", r.toString()));
//                }
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
            case "drawVisible":
                if (args.length != 2)
                {
                    result.add("USAGE: drawVisible index");
                    break;
                }
                {
                    final Integer index = Integer.valueOf(args[1]);
                    asyncCommand(new ICommand()
                    {
                        @Override
                        public Object execute() throws CommandFailed
                        {
                            return new GameFacade().drawVisibleCard(index);
                        }
                    });
                }
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

                break;
            case "getGameID":
                if(CD.getGame()!=null)
                    result.add(CD.getGame().getId());
                else
                    result.add("game is null");
                break;
            case "demo":
                runDemo();
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

    private void runDemo() {
        ArrayList<String> result = new ArrayList<String>();
        Player demoPlayer = CD.getGame().getPlayer("jackson");


        fragment.toast("Beginning tour. CHOO CHOO!");
        // test claiming a route
        fragment.toast("Our first stop is claiming a route!");
        claimRoute(result, new String[] {"unused var", "1"});
        fragment.toast(result.toString());


        // face up deck cards can change
        fragment.toast("let's modify the face up deck card!s");
        // hand of current player can change
        fragment.toast("let's modify the hand of the current player!");

        // players points can be changed
        fragment.toast("let's modify the players points!");
        demoPlayer.setPoints(9001);

        // players trains remaining can be changed
        fragment.toast("let's modify the players remaining trains!");
        demoPlayer.setTrainCount(10);

        // turn indicator can be changed
        fragment.toast("let's modify the turn indicator!");



    }

    private void claimRoute(ArrayList<String> result, String args[]) {
        if (args.length != 2)
        {
            result.add("USAGE: claimRoute routenumber");
            return;
        }
        final Route r = ClientGameService.claimRoute(CD.getUser(), Integer.valueOf(args[1]));
        if (r == null)
            return;

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
}
