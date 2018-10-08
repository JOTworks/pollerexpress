package presenter;

import Views.IGameSelectionView;
import cs340.pollerexpress.SetupFacade;

public class GameSelectionPresenter implements IGameSelectionPresenter {

    private IGameSelectionView view;
    private SetupFacade facade;

    public GameSelectionPresenter(IGameSelectionView view) {
        this.view = view;
        facade = new SetupFacade();
    }

    /**
     * This method contains the logic for what happens when the
     * user clickes "create game"
     * @ the user should go the the createGame view
     */
    @Override
    public void createGame() {

        view.changeCreateGameView();
    }

    /**
     * The method contains the logic for what happens when the user clicks "joinGame"
     * Should this have the game id as a parameter?
     * @pre there are less than five players in the game
     * @post the user should go the game lobby
     */
    @Override
    public void joinGame() {


        view.changeLobbyView();

        //update the number of players in that game
        updatePlayerNumber();
    }

    /**
     * This method updates the number of players in a game
     */
    private void updatePlayerNumber() {

        //I'm not quite sure how to do this.
        //I need to look at the UML and figure out how to
        //access and update a particular game (or gameinfo)
    }
}
