package Views.recycleViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.Player;

import cs340.pollerexpress.R;
import presenter.IGameSelectionPresenter;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {
    private Player[] playerArray;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView playerName;

        public MyViewHolder(TextView v) {
            super(v);

            playerName = v.findViewById(R.id.poller_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlayerAdapter(Player[] myDataset) {
        playerArray = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlayerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_poller_theme, parent, false);
        //...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.playerName.setText(playerArray[position].getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return playerArray.length;
    }
}