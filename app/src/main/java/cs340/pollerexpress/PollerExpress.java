package cs340.pollerexpress;

import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.PollResponse;
import java.util.Queue;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Welcome to The Poller Express.
 * Sends a poll request to the server via the ClientCommunicator every 2 seconds.
 * Currently prints a "Beep!" every time it polls so you know it's working,
 * when we have the command execution debugged and tested we'll get rid of that.
 */
public class PollerExpress {

	private static int DELAY = 2000;
	Timer timer;

	public PollerExpress() {
		timer = new Timer();
	}

	/**
	 * Starts the timer, polls the server every 2 seconds.
	 * No parameters, no return.
	 */
	public void run() {
		timer.schedule(new PollTask(), 0, DELAY);
	}

	class PollTask extends TimerTask {
		ClientCommunicator client;

		PollTask() {
			super();
			client = ClientCommunicator.instance();
		}

		@Override
		public void run() {
			System.out.println("CHOO!");

			PollResponse response = client.sendPoll();

			if(response == null) {
				//client communicator didn't work, throw error or something? Idk how to do that though.
			} else if(response.getError() != null) {
				//error handling, throw an error if necessary? Wait, where would that GO???
			} else {
				Queue<Command> commands = response.getCommands();
				for (int i = 0; i < commands.size(); i++) {
					Command command = commands.poll();

					try {
						command.execute();
					} catch (CommandFailed commandFailed) {
						commandFailed.printStackTrace();
						//should probably just. start over at this point.
						//THROW EPIC FAIL EXCEPTION
					}
				}
			}
		}
	}
}
