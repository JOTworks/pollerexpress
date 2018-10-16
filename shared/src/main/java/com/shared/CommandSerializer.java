package com.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shared.models.Command;

public class CommandSerializer {

    public String CommandToString(Command command){
        Gson gson = new Gson();
        String json = gson.toJson(command);
        return json;
    }
    public Command StringToCommand(String json){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Command command = gson.fromJson(json, Command.class);
        return command;
    }
}
