package presenter;

import java.util.List;

/**
 * This class is responsible for defining
 * methods the GameSelectionView can call
 * on the GameSelectionPresenter
 */
public interface IGameSelectionPresenter {

    /** Logic for when "create game" button is clicked */
    public void createGame();

    /** Logic for when "join game" button is clicked */
    public void joinGame();

    /** Gets the list of games for the view to display */
    public List getGameList();
}
