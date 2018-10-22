package thePollerExpress.Development;

import com.shared.models.Command;
import java.util.Arrays;


/**
 * Jack
 */
public class MethodBuilder {

    public static Command[] parse(String inputMethods) throws Exception {

        String[] Methods = inputMethods.split("\\;");
        Command[] Commands = new Command[Methods.length];

        for(int i=0;i<Commands.length;i++){
            Commands[i] = parseMethod(Methods[i],i);
        }

        return null;
    }

    public static Command parseMethod(String inputMethod,int i) throws Exception {

        String _className;
        String _methodName;
        Class<?>[] _paramTypes;
        Object[] _paramValues;

        String[] section = inputMethod.split("\\.");

        //syntax checking
        if(section.length<3)
            throw new Exception("command "+i+" is missing a .'s");
        if(section.length>3)
            throw new Exception("command "+i+" has to many .'s");
        _className = section[0];
        _methodName = section[1];

        String[] strParams = inputMethod.split("\\,");
        _paramTypes = new Class<?>[strParams.length];
        _paramValues = new Object[strParams.length];

        for(int j=0;j<strParams.length;j++){
            String[] paramSection = strParams[j].split("\\:");

            if(paramSection.length!=2)
                throw new Exception("command"+i+":param"+j+" the : is weird");

            _paramTypes[j] = Class.forName(paramSection[0]);
            _paramValues[j] = paramSection[1];
        }

        return new Command(_className,_methodName,_paramTypes,_paramValues);
    }


}
