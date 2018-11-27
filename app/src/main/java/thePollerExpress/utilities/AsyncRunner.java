package thePollerExpress.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.shared.models.Command;
import com.shared.models.PollResponse;
import com.shared.models.interfaces.ICommand;
import com.shared.models.reponses.ErrorResponse;

import thePollerExpress.communication.PollerExpress;
import thePollerExpress.views.IPollerExpressView;


/************************************************************************************************************
 * AsyncRunner: created by Nate
 *
 * used to run asynctasks by the project.
 *
 * Can auto switch views after a success.
 ***********************************************************************************************************/
public class AsyncRunner extends AsyncTask<ICommand, Void, PollResponse> {

    private IPollerExpressView currentView; //the view that is being used to run this asyntask
    private IPollerExpressView nextView; //the view to switch to after a successful completion, can be null


    /**
     * @pre currentView is not null.
     * @param currentView the view running this
     */
    public AsyncRunner(IPollerExpressView currentView)
    {
        this.currentView = currentView;
    }

    /**
     * @pre none
     * @post after execute is called, and runs successfully the view will switch
     * @param nextView the view to swithc to
     */
    public void setNextView(IPollerExpressView nextView)
    {
        this.nextView = nextView;
    }

    /**
     * Runs the tasks asynchronously
     * @pre
     * @post
     * @param commands
     * @return used internally
     */
    @Override
    protected PollResponse doInBackground(ICommand... commands)
    {
        PollerExpress.setCanRun(false);
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

        PollerExpress.setCanRun(true);
        if(response == null)
        {

        }
        else if (response != null)
        {
            if (response.getError() != null)
            {
                if(currentView == null) return;
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


}
