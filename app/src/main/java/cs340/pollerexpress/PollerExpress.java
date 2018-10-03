package cs340.pollerexpress;

import android.os.Handler;

import com.pollerexpress.models.Command;
import com.pollerexpress.models.PollResponse;

import java.util.Queue;

public class PollerExpress {
	private static int DELAY = 2000;

	public PollerExpress() {
		final Handler handler = new Handler();
		int delay = 1000; //milliseconds
		final PollerExpress polar = this;

		handler.postDelayed(new Runnable(){
			public void run(){
				polar.Poll();
				handler.postDelayed(this, DELAY);
			}
		}, delay);
	}
	
	public void Poll(){
		ClientCommunicator client = ClientCommunicator.instance();
		PollResponse response = client.sendPoll();

		if(response.getError() != null) {
			//error handling, throw an error if necessary? Wait, where would that GO???
		}

		Queue<Command> commands = response.getCommands();
		for(int i = 0; i < commands.size(); i++) {
			Command command = commands.poll();
			command.execute();
		}
	}
}
