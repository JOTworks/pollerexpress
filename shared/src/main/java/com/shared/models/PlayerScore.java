package com.shared.models;

import java.io.Serializable;

public class PlayerScore implements Serializable {

    private String playerName;
    private Integer routePoints;
    private Integer destinationPoints;
    private Integer unreachedDestinationPoints;
    private Integer bonusPoints;
    private Integer longestRouteScore;
    private Integer totalPoints;

    public PlayerScore(String playerName) {
        this.playerName = playerName;
        routePoints = 0;
        destinationPoints = 0;
        unreachedDestinationPoints = 0;
        bonusPoints = 0;
        longestRouteScore = 0;
        totalPoints = 0;
    }

    public PlayerScore(String playerName, int routePoints, int destinationPoints, int unreachedDestinationPoints, int longestRouteScore) {
        this.playerName = playerName;
        this.routePoints = routePoints;
        this.destinationPoints = destinationPoints;
        this.unreachedDestinationPoints = unreachedDestinationPoints;
        this.longestRouteScore = longestRouteScore;

        setTotalPoints();
    }

    public int getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(int routePoints) {
        this.routePoints = routePoints;
    }
    public void addRoutePoints(int points)
    {
        this.routePoints+= points;
    }

    public int getDestinationPoints() {
        return destinationPoints;
    }

    public void setDestinationPoints(int destinationPoints) {
        this.destinationPoints = destinationPoints;
    }

    public int getUnreachedDestinationPoints() {
        return unreachedDestinationPoints;
    }

    public void setUnreachedDestinationPoints(int unreachedDestinationPoints) {
        this.unreachedDestinationPoints = unreachedDestinationPoints;
    }

    public int getLongestRouteScore() {
        return longestRouteScore;
    }

    public void setLongestRouteScore(int longestRouteScore) {
        this.longestRouteScore = longestRouteScore;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTotalPoints() {
        if (routePoints == null || destinationPoints == null ||
                bonusPoints == null || unreachedDestinationPoints == null)
            throw new RuntimeException("points were not all initialized before attempting to calculate totals");

        this.totalPoints =  routePoints + destinationPoints + bonusPoints - unreachedDestinationPoints;
    }
}
