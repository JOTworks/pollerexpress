package thePollerExpress.presenters.game.interfaces;

import com.shared.models.EndGameResult;
import com.shared.models.PlayerScore;

import java.util.List;

import thePollerExpress.models.PlayerEndResult;
import thePollerExpress.views.game.EndGameFragment;

public interface IEndGamePresenter {
    void findNewGame();

    List<PlayerEndResult> getEndGameResult();
}
