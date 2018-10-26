package com.shared.models.interfaces;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Authtoken;
import com.shared.models.ChatMessage;
import com.shared.models.Command;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.models.reponses.LoginResponse;

public interface IDatabaseFacade
{
    LoginResponse login(User user);
    void register(User user) throws DatabaseException;
    Game join(Player player, GameInfo info) throws DatabaseException;
    void leave(Player player, GameInfo info) throws DatabaseException;
    void create(Player player, Game game) throws DatabaseException;
    boolean validate(Authtoken token) throws DatabaseException;
    Game getGame(GameInfo info) throws DatabaseException;
    Player getPlayer(String user) throws DatabaseException;
    GameInfo getGameInfo(String id) throws DatabaseException;
    Player[] getPlayersInGame(GameInfo info) throws DatabaseException;

    // Abby
    void addChat(Command chatCommand) throws DatabaseException;
}
