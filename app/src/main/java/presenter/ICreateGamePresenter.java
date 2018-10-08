package presenter;

/**
 * Declares the methods that the create game view
 * will need to be able to call on the create game presenter
 */
public interface ICreateGamePresenter {

    void setNumOfPlayers(String numOfPlayers);

    void setUserColor(String numOfPlayers);

    public void createGame();

    public void okButtonClicked();

    public void cancelButtonClicked();

    public void playerNumSelected();

    public void nameUpdate();

    public void colorPicked();
  
}
