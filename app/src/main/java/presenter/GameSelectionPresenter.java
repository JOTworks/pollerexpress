package presenter;

import java.util.ArrayList;
import java.util.List;

import Views.IGameSelectionView;
import cs340.pollerexpress.SetupFacade;

/**
 * (Compiling but not fully operational)
 * Responsible for implementing logic for gameselectionview . . . I think
 */
public class GameSelectionPresenter implements IGameSelectionPresenter {

    private IGameSelectionView view;
    private SetupFacade facade;

    public GameSelectionPresenter(IGameSelectionView view) {
        this.view = view;
        facade = new SetupFacade();
    }

    /**
     * (DONE!) This method contains the logic for what happens when the
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
//        updatePlayerNumber(gameID);
    }

    /** Gets the list of games for the view to display */
    @Override
    public List getGameList() {

        List gameList = new ArrayList();

        /* if not from the setupFacade, where would the
        presenter get the list of games?

        Client data has a "getGameInfoList" method,
        but I believe that just returns a list of games
        that the user is already in, not all of the games
        that it's possible for the user to join.
         */

//        gameList = facade.getGameList();

        return gameList;
    }


    /**
     * This method updates the number of players in a game
     */
    private void updatePlayerNumber(String gameID) {

        //I'm not quite sure how to do this.
        //I need to look at the UML and figure out how to
        //access and update a particular game (or gameinfo)
    }
}
