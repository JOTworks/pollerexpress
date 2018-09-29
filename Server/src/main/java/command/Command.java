package command;
import java.lang.reflect.*;

public class Command implements ICommand {
	private String _className;
    private String _methodName;
    private Class<?>[] _paramTypes;
    private Object[] _paramValues;
    
    public Command(String className, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
		_className = className;
		_methodName = methodName;
		_paramTypes = paramTypes;
		_paramValues = paramValues;
	}
    
    public Object execute() {
        try {
            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, _paramTypes);
            method.invoke(null, _paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
