package Views.recycleViewAdapters;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pollerexpress.models.GameInfo;

import cs340.pollerexpress.R;
import presenter.IGameSelectionPresenter;

public class GameSelectAdapter extends RecyclerView.Adapter<GameSelectAdapter.MyViewHolder> {
    private GameInfo[] mGameInfoArray;
    private IGameSelectionPresenter presenter;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements ViewStub.OnClickListener {
        // each data item is just a string in this case
        public TextView gameName;
        public TextView minMaxDisplay;
        public Button joinButton;
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
        public void onClick(View v) {
            if (v.getId() == joinButton.getId()) {

                int pos = getAdapterPosition();
                presenter.joinGame(pos);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameSelectAdapter(GameInfo[] myDataset, IGameSelectionPresenter presenter) {
        mGameInfoArray = myDataset;
        this.presenter = presenter;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GameSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_data_view, parent, false);
        //...
        MyViewHolder vh = new MyViewHolder(v, presenter);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        GameInfo gameInfo = mGameInfoArray[position];

        holder.gameName.setText(mGameInfoArray[position].getName());
        holder.minMaxDisplay.setText(
                mGameInfoArray[position].getNumPlayers() + "/" +
                        mGameInfoArray[position].getMaxPlayers());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mGameInfoArray.length;
    }
}