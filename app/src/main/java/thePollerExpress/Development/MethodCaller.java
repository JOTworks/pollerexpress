package thePollerExpress.Development;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;

import java.lang.reflect.Method;

import thePollerExpress.views.setup.development.MethodCallerFragment;

/**
 * Jack
 */
public class MethodCaller {

    MethodCallerFragment fragment;

    public MethodCaller(MethodCallerFragment inFragment){
        fragment = inFragment;
    }

    public String execute(Command[] Commands) throws CommandFailed {
        return Commands[0].execute().toString();
    }


}
