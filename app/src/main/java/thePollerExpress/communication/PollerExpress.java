package thePollerExpress.communication;

import android.app.Activity;
import android.util.Log;

import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.GameInfo;
import com.shared.models.PollResponse;
import java.util.Queue;

import java.util.Timer;
import java.util.TimerTask;

import thePollerExpress.models.ClientData;

/**
 * Welcome to The Poller Express.
 * Sends a poll request to the server via the ClientCommunicator every 2 seconds.
 * Currently prints a "Choo!" every time it polls so you know it's working,
 * when we have the command execution debugged and tested we'll get rid of that.
 */
public class PollerExpress
{

    private static boolean testing = false;
    private static int DELAY = 2000;
    private static Activity main;
    Timer timer;

    public PollerExpress(Activity main)
    {
        PollerExpress.main = main;
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {

            synchronized public void run()
            {
                System.out.println(String.format("%s %d", "CHOO!", ClientData.getInstance().countObservers()));
                if( ClientData.getInstance().getUser() != null )
                {

                }
                PollResponse response = ClientCommunicator.instance().sendPoll();

                if (response == null)
                {
                    //client communicator didn't work, throw error or something? Idk how to do that though.
                    Log.d("PollerExpress", "no response");
                } else if (response.getError() != null)
                {
                    //error handling, throw an error if necessary? Wait, where would that GO???
                } else
                {
                    Queue<Command> commands = response.getCommands();

                    Log.d("PollerExpress", String.format("Got a response of size %d", commands.size()));

                    executeCommands(commands);
                }

            }
        }, DELAY, DELAY);
    }


    public static void executeCommands(final Queue<Command>  commands)
    {
        main.runOnUiThread(new Runnable()
        {
            public void run()
            {

                while (!commands.isEmpty()) //queue, access in while loops, not, for loops;....
                {
                    Log.d("PollerExpress", "something a response");
                    Command command = commands.poll();

                    try
                    {
                        System.out.print("Ran " + command.getMethodName() + "\n");
                        command.execute();
                    }
                    catch (CommandFailed commandFailed)
                    {
                        commandFailed.printStackTrace();
                        //should probably just. start over at this point.
                        //TODO THROW EPIC FAIL EXCEPTION
                    }
                }
            }
        });
    }
}
