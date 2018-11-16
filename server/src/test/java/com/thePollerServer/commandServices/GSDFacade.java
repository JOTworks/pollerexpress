package com.thePollerServer.commandServices;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Authtoken;
import com.shared.models.Chat;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import pollerexpress.database.IDatabaseFacade;

import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.states.GameState;

import java.util.List;

public class GSDFacade implements IDatabaseFacade
{

    @Override
    public LoginResponse login(User user)
    {
        return null;
    }

    @Override
    public void register(User user) throws DatabaseException
    {

    }

    @Override
    public Game join(Player player, GameInfo info) throws DatabaseException
    {
        return null;
    }

    @Override
    public void leave(Player player, GameInfo info) throws DatabaseException
    {

    }

    @Override
    public void create(Player player, Game game) throws DatabaseException
    {

    }

    @Override
    public boolean validate(Authtoken token) throws DatabaseException
    {
        return false;
    }

    @Override
    public Game getGame(GameInfo info) throws DatabaseException
    {
        return null;
    }

    @Override
    public Player getPlayer(String user) throws DatabaseException
    {
        return null;
    }

    @Override
    public GameInfo getGameInfo(String id) throws DatabaseException
    {
        return null;
    }

    @Override
    public Player[] getPlayersInGame(GameInfo info) throws DatabaseException
    {
        return new Player[0];
    }

    @Override
    public List<DestinationCard> drawDestinationCards(Player player, int canDiscard) throws DatabaseException
    {
        return null;
    }

    @Override
    public void discardDestinationCard(Player player, List<DestinationCard> cards) throws DatabaseException
    {
        //do nothing.
    }

    @Override
    public int getPlayerDiscards(Player player) throws DatabaseException
    {
        if(player.getName().equals("Torsten") )
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public void makeBank(GameInfo game) throws DatabaseException {

    }

    @Override
    public void chat(Chat chat, GameInfo gameInfo) throws DatabaseException {
        // no implementation necessary
    }

    @Override
    public TrainCard[] getVisible(GameInfo info) throws DatabaseException {
        return new TrainCard[0];
    }

    @Override
    public TrainCard getVisible(Player p, int i) throws DatabaseException {
        return null;
    }

    @Override
    public TrainCard drawVisible(Player p, int i) throws DatabaseException {
        return null;
    }

    @Override
    public void setColor(Player p, int i) {

    }

    @Override
    public List<TrainCard> drawTrainCards(Player p, int number) throws DatabaseException {
        return null;
    }

    @Override
    public void setPreGameState(int numPlayers, GameInfo gameInfo) throws DatabaseException {

    }

    @Override
    public void setGameState(GameState gameState, GameInfo gameInfo) throws DatabaseException {

    }

    @Override
    public void setGameState(GameState.State state, GameInfo gameInfo) throws DatabaseException {

    }

    @Override
    public void updatePreGameState(GameInfo gameInfo) throws DatabaseException {

    }

    @Override
    public GameState getGameState(GameInfo gameInfo) throws DatabaseException {
        return null;
    }

}
