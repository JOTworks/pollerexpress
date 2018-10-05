package presenter;

import Views.IGameSelectionView;

public class GameSelectionPresenter implements IGameSelectionPresenter {

    private IGameSelectionView view;

    public GameSelectionPresenter(IGameSelectionView view) {
        this.view = view;
    }

    @Override
    public void createGame() {

    }

    @Override
    public void joinGame() {

    }
}
