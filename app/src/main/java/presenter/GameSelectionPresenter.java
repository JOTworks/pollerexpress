package presenter;

import Views.IGameSelectionView;

public class GameSelectionPresenter implements IGameSelectionPresenter {

    private IGameSelectionView view;

    public GameSelectionPresenter(IGameSelectionView view) {
        this.view = view;
    }

    /**
     * What happens when the user clicks "createGame"?
     */
    @Override
    public void createGame() {

    }

    /**
     * What happens when the user clicks "joinGame"?
     * @pre there are less than five players in the game
     * @post the user should be in the game
     */
    @Override
    public void joinGame() {

        view.changeLobbyView();
    }

    /**
     * This method updates the number of players in a game
     */
    @Override
    public void updatePlayerNumber() {

    }
}
