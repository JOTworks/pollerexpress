package thePollerExpress.presenters.game;

import com.shared.exceptions.NotImplementedException;
import com.shared.models.PlayerScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import thePollerExpress.models.ClientData;
import thePollerExpress.models.PlayerEndResult;
import thePollerExpress.presenters.game.interfaces.IEndGamePresenter;
import thePollerExpress.views.game.EndGameFragment;
import thePollerExpress.views.game.interfaces.IEndGameView;

public class EndGamePresenter implements IEndGamePresenter {

    private IEndGameView view;
    private ClientData clientData;

    public EndGamePresenter(EndGameFragment endGameFragment) {
        view = endGameFragment;
        clientData = ClientData.getInstance();
    }

    @Override
    public void findNewGame() {
        throw new NotImplementedException("findNewGame()");
    }

    @Override
    public List<PlayerEndResult> getEndGameResult()
    {
        List<PlayerEndResult> results = new ArrayList<>();
        System.out.println("in gER");
        List<PlayerScore> scores = clientData.getGameResult().getPlayerScores();
        Collections.sort(scores, new Comparator<PlayerScore>()
        {
            @Override
            public int compare(PlayerScore x, PlayerScore y)
            {
                int result = x.getTotalPoints() - y.getTotalPoints();
                if(result == 0)
                {
                    return x.getDestinationPoints()- y.getDestinationPoints();
                }
                return result;
            }
        });
        for(PlayerScore score: scores)
        {
            System.out.println("getting End GameResult");
            results.add(new PlayerEndResult(score.getPlayerName(), String.valueOf(score.getTotalPoints()),
                    String.valueOf(score.getDestinationPoints()),String.valueOf(score.getUnreachedDestinationPoints()),
                    String.valueOf(score.getRoutePoints()), String.valueOf(score.getLongestRouteScore()), String.valueOf(score.getBonusPoints())));
        }

        return results;
    }
}
