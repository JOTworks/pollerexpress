package com.shared.models;

import com.shared.exceptions.CommandFailed;
import com.shared.models.interfaces.ICommand;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
Thi class is in the models package because we will
 eventually have a list of commands in the database.
* */
public class Command implements ICommand, Serializable
{
	private String _className;
    private String _methodName;
    private Class<?>[] _paramTypes;
    private Object[] _paramValues;

    public Command() {
    }

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
    

    public void execute() throws CommandFailed
    {
        try
        {
            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, _paramTypes);
            method.invoke(null, _paramValues);
           return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CommandFailed(_methodName);
        }
    }

    public void set_className(String _className) {
        this._className = _className;
    }

    public void set_methodName(String _methodName) {
        this._methodName = _methodName;
    }

    public void set_paramTypes(Class<?>[] _paramTypes) {
        this._paramTypes = _paramTypes;
    }

    public void set_paramValues(Object[] _paramValues) {
        this._paramValues = _paramValues;
    }

    public String getClassName() { return _className; }

    public String getMethodName() { return _methodName; }

    public Class<?>[] getParamTypes() { return _paramTypes; }

    public Object[] getParamValues() { return _paramValues; }
}
