package thePollerExpress.communication;

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
	Timer timer;

	public PollerExpress()
    {

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {

            synchronized public void run()
            {
                System.out.println(String.format("%s %d","CHOO!", ClientData.getInstance().countObservers() ) );

                PollResponse response = ClientCommunicator.instance().sendPoll();
                if(testing)
                {
                    Class<?>[] types = {GameInfo.class};
                    Object[] params = {new GameInfo("testin123", 4)};
                    Command command = new Command("ClientSetupService","createGame",types, params );
                    try{
                        command.execute();
                    }
                    catch ( Exception e)
                    {
                        //do nothing
                    }
                }
                if(response == null) {
                    //client communicator didn't work, throw error or something? Idk how to do that though.
                    Log.d("PollerExpress", "no response");
                } else if(response.getError() != null) {
                    //error handling, throw an error if necessary? Wait, where would that GO???
                } else {
                    Log.d("PollerExpress", "got a response");
                    Queue<Command> commands = response.getCommands();
                    while (!commands.isEmpty()) //queue, access in while loops, not, for loops;....
                    {
                        Log.d("PollerExpress", "something a response");
                        Command command = commands.poll();

                        try {
                            System.out.print("Ran "+command.getMethodName()+"\n");
                            command.execute();
                        } catch (CommandFailed commandFailed) {
                            commandFailed.printStackTrace();
                            //should probably just. start over at this point.
                            //THROW EPIC FAIL EXCEPTION
                        }
                    }
                }

            }
        }, DELAY,  DELAY );
    }
}
