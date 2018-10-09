package presenter;

/**
 * Declares the methods that the create game view
 * will need to be able to call on the create game presenter
 *
 * Doesn't need to implement observer because the create
 * game view is not updated based on changed to models.
 */
public interface ICreateGamePresenter {

    /**
     * Sets the number of players in a game
     * @param numOfPlayers
     */
    public void setNumOfPlayers(String numOfPlayers);

    public void setUserColor(String userColor);

    public void onCreateGameClicked(String numOfPlayers, String userColor);

    public void onBackArrowClicked();
}
