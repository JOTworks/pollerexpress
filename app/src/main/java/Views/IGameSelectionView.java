package Views;

import com.pollerexpress.models.GameInfo;

public interface IGameSelectionView {

    void changeLobbyView();

    void changeCreateGameView();

    void renderGames(GameInfo[] gameinfoList);

    void disableGame(int gameListIndex);
    void enableGame(int gameListIndex);
}
