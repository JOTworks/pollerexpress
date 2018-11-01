package thePollerExpress.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.shared.models.Command;
import com.shared.models.PollResponse;
import com.shared.models.interfaces.ICommand;
import com.shared.models.reponses.ErrorResponse;

import thePollerExpress.communication.PollerExpress;
import thePollerExpress.views.IPollerExpressView;


/**
 * if you would like to change views after executing a command, you must use setNextView before
 * executing your command. If the nextView is set, then the view will change automatically upon
 * completion of the command
 */
public class AsyncRunner extends AsyncTask<ICommand, Void, PollResponse> {

    private IPollerExpressView currentView;
    private IPollerExpressView nextView;


    public AsyncRunner(IPollerExpressView currentView)
    {
        this.currentView = currentView;
    }

    @Override
    protected PollResponse doInBackground(ICommand... commands)
    {

        PollResponse response;
        try
        {
            Object theRet = commands[0].execute();
            try
            {
                response = (PollResponse) theRet;
            }
            catch (Exception e)
            {
                ErrorResponse error = (ErrorResponse) theRet;
                response = new PollResponse(null, error);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            response = new PollResponse(null, new ErrorResponse(e.getMessage(), e, (Command) commands[0]) );
        }

            return response;
    }

    @Override
    protected void onPostExecute (PollResponse response)
    {

        if(response == null)
        {

        }
        else if (response != null)
        {
            if (response.getError() != null)
            {
                currentView.displayError( response.getError().getMessage() );
                return;
            }


            if(response.getCommands() != null)
            {
                Log.d("tag1", "commands recieved");
                PollerExpress.executeCommands(response.getCommands());
            }
            else
                Log.d("tag2", "NO commands recieved");
        }

        if (nextView != null)
        {
            currentView.changeView(nextView);
        }
    }

    public void setNextView(IPollerExpressView nextView)
    {
        this.nextView = nextView;
    }

}
