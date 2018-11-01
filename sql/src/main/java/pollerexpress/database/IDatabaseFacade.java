package pollerexpress.database;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Authtoken;
import com.shared.models.DestinationCard;
import com.shared.models.Chat;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.TrainCard;
import com.shared.models.User;
import com.shared.models.reponses.LoginResponse;

import java.util.List;

import pollerexpress.database.dao.IDatabase;

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
    List<DestinationCard> drawDestinationCards(Player player, int canDiscard)throws DatabaseException;

    /**
     * Discards a card from a players hand
     * @param player who is discarding
     * @param cards the cards the player wants to discard.
     */
    void discardDestinationCard(Player player, List<DestinationCard> cards) throws DatabaseException;

    /**
     * the number of destination cards a player can discard
     * @param player
     * @return
     */
    int getPlayerDiscards(Player player) throws DatabaseException;
    void makeBank(GameInfo game) throws DatabaseException;

    // Abby
    void chat(Chat chat, GameInfo gameInfo) throws DatabaseException;

    /**
     * gets all the cards in the visible spread.
     * @param info
     * @return
     * @throws DatabaseException
     */
    public TrainCard[] getVisible(GameInfo info) throws DatabaseException;


    TrainCard getVisible(Player p, int i) throws DatabaseException;
    TrainCard drawVisible(Player p, int i) throws DatabaseException;

}

