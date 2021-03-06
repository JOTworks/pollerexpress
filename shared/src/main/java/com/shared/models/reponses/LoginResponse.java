package com.shared.models.reponses;

import com.shared.models.Authtoken;
import com.shared.models.GameInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginResponse implements Serializable
{
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
