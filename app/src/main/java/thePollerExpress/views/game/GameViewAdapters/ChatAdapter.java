package thePollerExpress.views.game.GameViewAdapters;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.shared.models.Chat;
import com.shared.models.GameInfo;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.setup.IGameSelectionPresenter;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Chat> mGameInfoArray;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public ChatAdapter(List<Chat> info, IGameSelectionPresenter presenter)
    {
        mGameInfoArray = info;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cha, parent, false);

        // ...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Chat gameInfo = mGameInfoArray.get(position);

        holder.gameName.setText(mGameInfoArray.get(position).getName());
        holder.minMaxDisplay.setText(
                gameInfo.getNumPlayers() + "/" +
                        gameInfo.getMaxPlayers());
        holder.game = gameInfo;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mGameInfoArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements ViewStub.OnClickListener {
        // each data item is just a string in this case
        public TextView gameName;
        public TextView minMaxDisplay;
        public Button joinButton;
        public GameInfo game;
        private IGameSelectionPresenter presenter;

        public MyViewHolder(ConstraintLayout v, IGameSelectionPresenter presenter) {
            super(v);

            this.presenter = presenter;

            gameName = v.findViewById(R.id.game_name_text);
            minMaxDisplay = v.findViewById(R.id.game_minmax_display_text);
            joinButton = v.findViewById(R.id.join_game_button);

            joinButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if ( v.getId() == joinButton.getId() )
            {
                presenter.joinGame(game);
            }
        }
    }
}