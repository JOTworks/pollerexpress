package Views;

public interface ICreateGameView {

//    void enableCancelButton(Boolean yn);
//    void enableCreateButton(Boolean yn);
//    void highlightColor(Color color);
    void displayError(String errorMessage);
    void changeToSetupGameView();
    void changeToLobbyView();
}
