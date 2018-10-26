package thePollerExpress.utilities;

import android.os.AsyncTask;

import com.shared.models.Command;
import com.shared.models.interfaces.ICommand;
import com.shared.models.reponses.ErrorResponse;

import thePollerExpress.views.IPollerExpressView;


/**
 * if you would like to change views after executing a command, you must use setNextView before
 * executing your command. If the nextView is set, then the view will change automatically upon
 * completion of the command
 */
public class AsyncRunner extends AsyncTask<ICommand, Void, ErrorResponse> {

    private IPollerExpressView currentView;
    private IPollerExpressView nextView;


    public AsyncRunner(IPollerExpressView currentView) {
        this.currentView = currentView;
    }

    @Override
    protected ErrorResponse doInBackground(ICommand... commands) {

        ErrorResponse errorResponse;
        try {
            errorResponse = (ErrorResponse) commands[0].execute();
        } catch (Exception e) {
            e.printStackTrace();
            errorResponse = new ErrorResponse("command failed", e, (Command) commands[0]); //TODO: Error response should depend on ICommand so we don't have to cast here
        }

            return errorResponse;
    }

    @Override
    protected void onPostExecute (ErrorResponse response) {

        if(response != null) {
            currentView.displayError("unable to join this game");
        }
        else if (nextView != null){
            currentView.changeView(nextView);
        }
    }

    public void setNextView(IPollerExpressView nextView) {
        this.nextView = nextView;
        nextView = null; //reset nextView
    }

}
