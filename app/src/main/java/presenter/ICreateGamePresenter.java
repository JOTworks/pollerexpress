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

    /**
     * Sets the user's color
     */
    public void setUserColor(String color);

    /**
     * Creates the game
     */
    public void createGame();

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
