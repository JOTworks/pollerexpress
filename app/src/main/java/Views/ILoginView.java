package Views;

public interface ILoginView {

    public void disableLogin();
    public void enableLogin();
    public void disableRegister();
    public void enableRegister();

    public void changeToGameSelectionView();
    public void displayError(String errorMessage);
}
