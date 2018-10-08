package presenter;

import com.pollerexpress.models.GameInfo;

import java.util.ArrayList;

/**
 * This class is responsible for defining
 * methods the GameSelectionView can call
 * on the GameSelectionPresenter
 */
public interface IGameSelectionPresenter {

    /** Logic for when "create game" button is clicked
     * @post view changes to create Game View*/
    public void createGame();

    /**
     * This method contains logic for when the "join game"
     * button is clicked
     * @pre there are less than five players in the game
     * @post the user should go the the game lobby
     * @param gameIndex The index of the game in the list
     *                  of games the user could join
     */
    public void joinGame(int gameIndex);

    /** Gets the list of games for the view to display */
    public ArrayList<GameInfo> getGameList();
}
