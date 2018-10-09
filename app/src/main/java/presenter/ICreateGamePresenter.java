package presenter;

/**
 * Declares the methods that the create game view
 * will need to be able to call on the create game presenter
 */
public interface ICreateGamePresenter {

    /**
     * Sets the number of players in a game
     * @param numOfPlayers
     */
    public void setNumOfPlayers(String numOfPlayers);

    public void setUserColor(String userColor);

    public void setGameName(String gameName);

    public void onCreateGameClicked(String numOfPlayers, String userColor);

    public void onBackArrowClicked();
}
