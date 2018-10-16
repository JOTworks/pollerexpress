package com.shared.models.reponses;


import com.shared.models.Command;

import java.io.Serializable;

public class ErrorResponse implements Serializable
{
	private String message;
	private Exception exception;
	private Command failed_command;

	private ErrorResponse()
	{

	}

	public ErrorResponse(String message, Exception exception, Command failed_command) {
		this.message = message;
		this.exception = exception;
		this.failed_command = failed_command;
	}

	public String getMessage() {
		return message;
	}

	public Exception getException() {
		return exception;
	}

	public Command getFailed_command() {
		return failed_command;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public void setFailed_command(Command failed_command) {
		this.failed_command = failed_command;
	}
}
