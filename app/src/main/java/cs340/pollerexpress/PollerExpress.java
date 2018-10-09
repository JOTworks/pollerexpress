package cs340.pollerexpress;

import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.PollResponse;
import java.util.Queue;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
                    Command command = new Command("cs340.pollerexpress.ClientSetupService","createGame",types, params );
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
                } else if(response.getError() != null) {
                    //error handling, throw an error if necessary? Wait, where would that GO???
                } else {
                    Queue<Command> commands = response.getCommands();
                    for (int i = 0; i < commands.size(); i++) {
                        Command command = commands.poll();

                        try {
                            System.out.print("Ran a command\n");
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
