package thePollerExpress.Development;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;

import java.lang.reflect.Method;


public class MethodCaller {

    MethodCallerFragment fragment;

    public MethodCaller(MethodCallerFragment inFragment){
        fragment = inFragment;
    }

    public String execute(Command[] Commands) throws CommandFailed {
        return Commands[0].execute().toString();
    }


}
