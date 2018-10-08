package com.pollerexpress.models;

import java.util.ArrayList;

public class LoginResponse {
	Authtoken authToken;
	ArrayList<GameInfo> availableGames;
	ErrorResponse error;

	public LoginResponse() {
	}

	public LoginResponse(Authtoken authToken, ArrayList<GameInfo> availableGames, ErrorResponse error) {
		this.authToken = authToken;
		this.availableGames = availableGames;
		this.error = error;
	}

	public Authtoken getAuthToken() {
		return authToken;
	}

	public ArrayList<GameInfo> getAvailableGames() {
		return availableGames;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setAuthToken(Authtoken authToken) {
		this.authToken = authToken;
	}

	public void setAvailableGames(ArrayList<GameInfo> availableGames) {
		this.availableGames = availableGames;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}
}
