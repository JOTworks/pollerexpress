package com.shared.models.interfaces;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Authtoken;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.models.reponses.LoginResponse;

import java.util.List;

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

    /**
     * Draws a card to players hand.
     * @param player player who is drawing
     * @param canDiscard number of cards that the player may discard
     * @return
     */
    List<DestinationCard> drawDestinationCards(Player player, int canDiscard);

    /**
     * Discards a card from a players hand
     * @param player who is discarding
     * @param cards the cards the player wants to discard.
     */
    void DiscardDestinationCard(Player player, List<DestinationCard> cards);

}