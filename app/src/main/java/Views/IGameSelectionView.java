package Views;

import com.pollerexpress.models.GameInfo;

public interface IGameSelectionView {

    void changeCreateGameView();

    /**
     * This method is like a refresh button for the view
     * and should be called in the presenter's update method
     * @param gameinfoList The list of existing games
     */
    void renderGames(GameInfo[] gameinfoList);

    void disableGame(int gameListIndex);
    void enableGame(int gameListIndex);

    void displayError(String errorMessage);

    void changeToLobbyView();

}
