package thePollerExpress.Development;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;

import java.lang.reflect.Method;
import java.util.ArrayList;

import thePollerExpress.models.ClientData;


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
                        "---\n" +
                        "add commands to the methodCaller class\n" +
                        "in the parse funtion, as a case");
                break;
            case "getChatMessages":
                result = CD.getMessageList();
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
}
