package com.shared.models;

import java.io.Serializable;

public class PlayerScore implements Serializable {

    private String playerName;
    private Integer routePoints;
    private Integer destinationPoints;
    private Integer unreachedDestinationPoints;
    private Integer bonusAwardPoints;
    private Integer totalPoints;

    public PlayerScore(String playerName) {
        this.playerName = playerName;
        routePoints = 0;
        destinationPoints = 0;
        unreachedDestinationPoints = 0;
        bonusAwardPoints = 0;
        totalPoints = 0;
    }

    public PlayerScore(String playerName, int routePoints, int destinationPoints, int unreachedDestinationPoints, int bonusAwardPoints) {
        this.playerName = playerName;
        this.routePoints = routePoints;
        this.destinationPoints = destinationPoints;
        this.unreachedDestinationPoints = unreachedDestinationPoints;
        this.bonusAwardPoints = bonusAwardPoints;

        totalPoints = routePoints + destinationPoints + bonusAwardPoints - unreachedDestinationPoints;
    }

    public int getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(int routePoints) {
        this.routePoints = routePoints;
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

    public int getBonusAwardPoints() {
        return bonusAwardPoints;
    }

    public void setBonusAwardPoints(int bonusAwardPoints) {
        this.bonusAwardPoints = bonusAwardPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints() {
        if (routePoints == null || destinationPoints == null ||
                bonusAwardPoints == null || unreachedDestinationPoints == null)
            throw new RuntimeException("points were not all initialized before attempting to calculate totals");

        this.totalPoints =  routePoints + destinationPoints + bonusAwardPoints - unreachedDestinationPoints;
    }
}
