package presenter;

/**
 * Declares the methods that the create game view
 * will need to be able to call on the create game presenter
 */
public interface ICreateGamePresenter {

    public void setNumOfPlayers(String numOfPlayers);

    public void setUserColor(String userColor);

    public void createGame(String numOfPlayers, String userColor);

    public void okButtonClicked();

    public void cancelButtonClicked();

    public void playerNumSelected();

    public void nameUpdate();

    public void colorPicked();
  
}
