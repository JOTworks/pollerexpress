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

    public void createGame(String numOfPlayers, String userColor);

    /**
     * Logic for when ok is clicked
     */
    public void onOkClicked();

    /**
     * Logic for when cancel is clicked
     */
    public void onCancelClicked();

    /**
     * Sets the name of the game
     */
    public void setGameName(String name);

  
}
