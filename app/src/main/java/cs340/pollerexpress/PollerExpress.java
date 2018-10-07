package cs340.pollerexpress;

import com.pollerexpress.models.Command;
import com.pollerexpress.models.PollResponse;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class PollerExpress {
	private static int DELAY = 2000;
	Timer timer;

	public PollerExpress() {
		timer = new Timer();
		timer.schedule(new PollTask(), 0, DELAY);
	}

	class PollTask extends TimerTask {
		ClientCommunicator client;

		public PollTask() {
			super();
			client = ClientCommunicator.instance();
		}

		@Override
		public void run() {
			System.out.println("Beep!");

			PollResponse response = client.sendPoll();

			if(response == null) {
				//client communicator didn't work, throw error or something? Idk how to do that though.
			} else if(response.getError() != null) {
				//error handling, throw an error if necessary? Wait, where would that GO???
			} else {
				Queue<Command> commands = response.getCommands();
				for (int i = 0; i < commands.size(); i++) {
					Command command = commands.poll();
					command.execute();
				}
			}
		}
	}
}
