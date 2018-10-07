package com.pollerexpress.models;

import java.lang.reflect.Method;

public class Command implements ICommand {
	private String _className;
    private String _methodName;
    private Class<?>[] _paramTypes;
    private Object[] _paramValues;

    /**
     *
     * @param className
     * @param methodName
     * @param paramTypes
     * @param paramValues
     */
    public Command(String className, String methodName, Class<?>[] paramTypes, Object[] paramValues)
    {
		_className = className;
		_methodName = methodName;
		_paramTypes = paramTypes;
		_paramValues = paramValues;
	}
    
    public Command execute() throws CommandFailed
    {
        try {
            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, _paramTypes);
           return (Command)method.invoke(null, _paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CommandFailed(_methodName);
        }
    }
}
