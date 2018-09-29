package request_response;

import command.Command;

public class ErrorResponse {
	private String message;
	private Exception exception;
	private Command failed_command;
	
	public ErrorResponse(String message, Exception exception, Command failed_command) {
		this.message = message;
		this.exception = exception;
		this.failed_command = failed_command;
	}
}
