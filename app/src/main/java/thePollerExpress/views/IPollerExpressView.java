package thePollerExpress.views;

public interface IPollerExpressView {

    void displayError(String errorMessage);

    void changeView(IPollerExpressView view);
}
